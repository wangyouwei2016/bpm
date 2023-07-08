package com.dstz.base.query.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.dstz.base.api.dto.QueryParamDTO;
import com.dstz.base.common.constats.StrPool;
import com.dstz.base.common.constats.ThreadMapKeyConstant;
import com.dstz.base.common.enums.GlobalApiCodes;
import com.dstz.base.common.exceptions.ApiException;
import com.dstz.base.common.utils.CastUtils;
import com.dstz.base.common.utils.ThreadMapUtil;
import com.dstz.base.query.AbPage;
import com.dstz.base.query.AbQueryFilter;
import com.dstz.base.query.ConditionType;
import com.dstz.base.query.QuerySort;
import org.apache.ibatis.session.RowBounds;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * 查询的默认实现
 *
 * @author Jeff
 */
public class DefaultAbQueryFilter implements AbQueryFilter {

    /**
     * 查询参数
     */
    private Map<String, Object> queryParam = null;

    /**
     * 分页信息
     */
    private AbPage page = null;

    /**
     * 查询分组，每组以括号组合条件
     */
    private DefaultQueryFieldGroup queryFieldGroup = null;

    /**
     * 查询排序
     */
    private List<QuerySort> querySortList = null;

    private List<String> selectColumnNames;


    @Override
    public AbPage getPage() {
        return this.page;
    }

    @Override
    public Map<String, Object> generateQuerySql() {
        // 生成whereSQL
        if (CollectionUtil.isNotEmpty(queryParam)) {
            this.queryParam.put(WHERE_SQL, this.queryFieldGroup.getWhereSql());
        }

        // 生成OrderBySql
        if (CollectionUtil.isNotEmpty(this.getQuerySortList())) {
            StringBuilder orderBySql = new StringBuilder();
            for (QuerySort fieldSort : this.getQuerySortList()) {
                orderBySql.append(fieldSort.getColumn()).append(" ").append(fieldSort.getAsc() ? "ASC" : "DESC ").append(",");
            }
            orderBySql.deleteCharAt(orderBySql.length() - 1);

            this.queryParam.put(ORDERBY_SQL, orderBySql.toString());
        }

        return queryParam;
    }

    public DefaultAbQueryFilter() {

    }

    /**
     * 构造函数
     */
    public DefaultAbQueryFilter(QueryParamDTO queryParamDto) {
        initByQueryParamDto(queryParamDto, null, true);
    }

    /**
     * 构造函数
     *
     * @param flag 是否自定转换下划线格式
     */
    public DefaultAbQueryFilter(QueryParamDTO queryParamDto, boolean flag) {
        initByQueryParamDto(queryParamDto, null, flag);
    }

    /**
     * 构造函数
     *
     * @param queryParamDto      查询条件的DTO
     * @param accessQueryFilters 准入的查询条件
     */
    public DefaultAbQueryFilter(@Valid QueryParamDTO queryParamDto, Set<String> accessQueryFilters) {
        initByQueryParamDto(queryParamDto, accessQueryFilters, true);
    }

    /**
     * 构造函数
     *
     * @param queryParamDto      查询条件的DTO
     * @param accessQueryFilters 准入的查询条件
     * @param flag               是否自定转换下划线格式
     */
    public DefaultAbQueryFilter(@Valid QueryParamDTO queryParamDto, Set<String> accessQueryFilters, boolean flag) {
        initByQueryParamDto(queryParamDto, accessQueryFilters, flag);
    }

    private void initByQueryParamDto(QueryParamDTO queryParamDto, Set<String> accessQueryFilters, boolean flag) {
        //查询条件初始化
        queryParam = MapUtil.newHashMap(queryParamDto.getQueryParam().size() + 5, false);
        queryFieldGroup = new DefaultQueryFieldGroup();

        // 转换查询列
        this.selectColumnNames = CollUtil.map(queryParamDto.getColumnNames(), s -> {
            String to = StrUtil.removeSuffix(NamingCase.toUnderlineCase(s), StrPool.DOLLAR);
            return s.endsWith("$") ? to : to + "_";
        }, true);

        this.initQueryCondition(queryParamDto.getQueryParam(), accessQueryFilters, flag);

        // 排序条件初始化，QueryDTO 设计仅支持一个排序
        if (StrUtil.isNotEmpty(queryParamDto.getSortColumn())) {
            this.querySortList = Collections.singletonList(
                    //决定是否增加下划线
                    new QuerySort(flag ? handleDbFiledName(queryParamDto.getSortColumn()) : queryParamDto.getSortColumn(),
                            StrUtil.equalsIgnoreCase(QuerySort.ASC, queryParamDto.getSortOrder())
                    ));
        }
        //如果是否分页为false，则不分页。 分页对象不存在或者为true则分页
        if (null != queryParamDto.getEnablePage() && !queryParamDto.getEnablePage()) {
            this.page = null;
        } else {
            boolean searchCount = ObjectUtil.defaultIfNull(queryParamDto.getSearchCount(), Boolean.TRUE);
            if (queryParamDto.getOffset() != null && queryParamDto.getLimit() != null) {
                RowBounds rowBounds = new RowBounds(queryParamDto.getOffset(), queryParamDto.getLimit());
                this.page = new AbPage(rowBounds, searchCount);
            } else {
                long currentPage = ObjectUtil.defaultIfNull(queryParamDto.getCurrentPage(), 1L);
                long pageSize = ObjectUtil.defaultIfNull(queryParamDto.getPageSize(), 10L);
                this.page = new AbPage(currentPage, pageSize, searchCount);
                // 分页设置
            }
        }
    }


    /**
     * 处理页进来的请求参数。
     *
     * <pre>
     * 	1.参数字段命名规则。
     * 	a:参数名称^参数类型+条件 eg：a$VEQ 则表示，a字段是varchar类型，条件是eq $后第一个参数为数据类型
     * 	b:参数名字^参数类型  eg：b$V则表示，b字段是varchar类型 用于sql拼参数
     * 	2.在这里构建的逻辑都是and逻辑。
     * 3.参数类型:V :字符串 varchar N:数字number D:日期date
     * 条件参数 枚举：QueryOP
     *
     * </pre>
     *
     * @param accessQueryFilters
     * @param queryConditionGroupParam
     * @param accessQueryFilters
     * @param flag                     是否转换下划线 默认为转换
     */
    private void initQueryCondition(Map<String, Object> queryConditionGroupParam, Set<String> accessQueryFilters, boolean flag) {
        // 设置动态允许查询参数，兼容数据权限设置字段查询
        if (CollUtil.isNotEmpty(accessQueryFilters)) {
            Optional.ofNullable(ThreadMapUtil.get(ThreadMapKeyConstant.QueryFilter.ACCESS_QUERY_FILTERS))
                    .<Set<String>>map(CastUtils::cast)
                    .filter(CollUtil::isNotEmpty)
                    .map(accessQueryFilters::addAll);
        }
        queryConditionGroupParam.forEach((key, val) -> {
            // 是否queryFilter约定入参
            if (val == null || !StrUtil.contains(key, StrPool.DOLLAR)) {
                return;
            }

            // 值为空跳过查询条件
            if (ObjectUtil.isEmpty(val)) {
                return;
            }

            // 参数中不能存在空格中断
            if (key.indexOf(StrPool.C_SPACE) != -1) {
                throw new ApiException(GlobalApiCodes.PARAMETER_INVALID.formatMessage("参数不合法{},{}", key, val));
            }

            String[] aryParamKey = key.split("\\" + StrPool.DOLLAR);
            if (aryParamKey.length != 2) {
                return;
            }

            // 参数出现了不允许的字段
            if (CollectionUtil.isNotEmpty(accessQueryFilters) && !accessQueryFilters.contains(aryParamKey[0])) {
                throw new ApiException(GlobalApiCodes.PARAMETER_UNALLOWED.formatDefaultMessage(aryParamKey[0]));
            }


            // 转换成 下划线的，数据库的字段
            String fieldName = flag ? NamingCase.toUnderlineCase(aryParamKey[0]).concat(StrPool.UNDERLINE) : aryParamKey[0];
            String condition = aryParamKey[1];//截取条件
            String groupKey = "";
            if (condition.contains("_OR")) {//组标识
                groupKey = condition.split("_")[1];
                condition = condition.substring(0, condition.indexOf("_"));
            }


            ConditionType conditionType = null;
            if (condition.length() > 1) {
                conditionType = ConditionType.formKey(condition.substring(1));
            }

            Object nativeValue = handelValue(conditionType, val, condition);

            if (ConditionType.LESS.equals(conditionType) || ConditionType.LESS_EQUAL.equals(conditionType)) {
                fieldName = fieldName + "-end";
            }
            this.queryParam.put(fieldName.replace(CharUtil.DOT, CharUtil.UNDERLINE), nativeValue);

            // 部分条件为null，仅仅将参数放进去自行使用
            if (conditionType != null) {
                this.queryFieldGroup.addQueryField(groupKey, fieldName, conditionType, nativeValue);
            }
        });
    }

    /**
     * 将 Value 处理下
     *
     * @param conditionType
     * @param strValue
     * @param condition
     * @return
     */
    public Object handelValue(ConditionType conditionType, Object strValue, String condition) {

        Object nativeValue = null;

        // in 类型的直接处理
        switch (conditionType) {
            case IN:
            case NOT_IN:
                return getInValueSql(strValue);

            case LEFT_LIKE:
                return "%" + String.valueOf(strValue);

            case RIGHT_LIKE:
                return String.valueOf(strValue) + "%";

            case LIKE:
                return "%" + String.valueOf(strValue).replace("_", "\\_").replace("%", "%") + "%";
        }

        // 手动添加时无需转换数据类型
        if (condition == null) return strValue;

        String columnType = condition.substring(0, 1);

        // 默认其他的 执行格式操作
        if ("V".equals(columnType)) {
            nativeValue = Convert.toStr(strValue);
        } else if ("N".equals(columnType)) {
            nativeValue = Convert.toNumber(strValue);
        } else if ("D".equals(columnType)) {
            nativeValue = DateUtil.parse(String.valueOf(strValue));
        }

        return nativeValue;
    }

    /**
     * 针对in查询方式，根据传入的value的类型做不同的处理。 value 是 string，则分隔字符串，然后合并为符合in规范的字符串。
     * value 是 list，则直接合并为符合in规范的字符串
     *
     * @return
     */
    private String getInValueSql(Object value) {
        Iterable listValue = null;
        if (value instanceof String) {
            listValue = StrUtil.split((String) value, CharUtil.COMMA);
        } else if (value instanceof Iterable) {
            listValue = (Iterable) value;
        }

        if (CollectionUtil.isEmpty(listValue)) {
            return StrUtil.EMPTY;
        }

        StringBuilder inSqlStr = new StringBuilder();
        for (Object obj : listValue) {
            String valueString = Objects.isNull(obj) ? null : obj.toString();
            if (StrUtil.isNotEmpty(valueString)) {
                inSqlStr.append(CharUtil.SINGLE_QUOTE)
                        .append(valueString)
                        .append(CharUtil.SINGLE_QUOTE)
                        .append(CharUtil.COMMA);
            }
        }
        if (inSqlStr.charAt(inSqlStr.length() - 1) == CharUtil.COMMA) {
            inSqlStr.deleteCharAt(inSqlStr.length() - 1);
        }
        return inSqlStr.toString();
    }

    public Map<String, Object> getQueryParam() {
        return queryParam;
    }

    public void setQueryParam(Map<String, Object> queryParam) {
        this.queryParam = queryParam;
    }

    public DefaultQueryFieldGroup getQueryFieldGroup() {
        return queryFieldGroup;
    }

    public void setQueryFieldGroup(DefaultQueryFieldGroup queryFieldGroup) {
        this.queryFieldGroup = queryFieldGroup;
    }

    public List<QuerySort> getQuerySortList() {
        return querySortList;
    }

    public void setQuerySortList(List<QuerySort> querySortList) {
        this.querySortList = querySortList;
    }

    public void setPage(AbPage page) {
        this.page = page;
    }

    public static DefaultAbQueryFilter build() {
        DefaultAbQueryFilter filter = buildWithOutPage();
        filter.setPage(new AbPage());
        return filter;
    }

    public static DefaultAbQueryFilter buildWithOutPage() {
        DefaultAbQueryFilter filter = new DefaultAbQueryFilter();
        filter.setQueryParam(MapUtil.newHashMap(10));
        filter.setQueryFieldGroup(new DefaultQueryFieldGroup());
        return filter;
    }

    public static AbQueryFilter buildWithFilter(String fildName, Object value, ConditionType conditionType) {
        return build().addFilter(fildName, value, conditionType);
    }

    public static AbQueryFilter buildWithEqFilter(String fildName, Object value) {
        return build().addFilter(fildName, value, ConditionType.EQUAL);
    }

    @Override
    public AbQueryFilter noPage() {
        this.page = null;
        return this;
    }


    @Override
    public AbQueryFilter addFilter(String fieldName, Object value, ConditionType conditionType) {
        return this.addFilter(fieldName, value, conditionType, Boolean.TRUE, null);
    }

    private AbQueryFilter addFilter(String fieldName, Object value, ConditionType conditionType, Boolean underline, String groupKey) {
        if (StrUtil.isEmpty(fieldName) || value == null) {
            return this;
        }
        // 转换成 下划线的，数据库的字段
        if (underline) {
            fieldName = handleDbFiledName(fieldName);
        }

        Object nativeValue = handelValue(conditionType, value, null);

        // TODO 把 queryFiled 改造成 filedName paramName 两个字段存储
        this.queryParam.put(fieldName.replace(CharUtil.DOT, CharUtil.UNDERLINE), nativeValue);
        this.getQueryFieldGroup().addQueryField(groupKey, fieldName, conditionType, nativeValue);

        return this;
    }

    @Override
    public AbQueryFilter addFilterOriginal(String fieldName, Object value, ConditionType conditionType) {
        return this.addFilter(fieldName, value, conditionType, Boolean.FALSE, null);
    }

    @Override
    public AbQueryFilter addFilter(String fieldName, Object value, ConditionType conditionType, String groupKey) {
        return this.addFilter(fieldName, value, conditionType, Boolean.FALSE, groupKey);
    }

    // 转换成 下划线的，数据库的字段
    private String handleDbFiledName(String filedName) {
        String dbName = NamingCase.toUnderlineCase(filedName);
        // 强制以 _ 结尾
        if (!dbName.endsWith(StrPool.UNDERLINE)) {
            dbName = dbName.concat(StrPool.UNDERLINE);
        }
        return dbName;
    }

    @Override
    public AbQueryFilter likeFilter(String fildName, Object value) {
        return this.addFilter(fildName, value, ConditionType.LIKE);

    }

    @Override
    public AbQueryFilter inFilter(String fildName, Object value) {
        return this.addFilter(fildName, value, ConditionType.IN);
    }

    @Override
    public AbQueryFilter notEqFilter(String fildName, Object value) {
        return this.addFilter(fildName, value, ConditionType.NOT_EQUAL);
    }

    @Override
    public AbQueryFilter addParam(String fildName, Object value) {
        this.getQueryParam().put(fildName, value);
        return this;
    }

    @Override
    public AbQueryFilter clearQuery() {
        this.getQueryParam().clear();
        this.getQuerySortList().clear();
        this.getQueryFieldGroup().getFieldGroupMap().clear();
        return this;
    }


    public static void main(String[] args) {
        AbQueryFilter queryFilter = DefaultAbQueryFilter.buildWithFilter("b.userName", "张三", ConditionType.EQUAL)
                .addFilter("a.userName", "张三,sss", ConditionType.IN);

        System.out.println(queryFilter.generateQuerySql());


    }

    @Override
    public AbQueryFilter eqFilter(String fildName, Object value) {
        return this.addFilter(fildName, value, ConditionType.EQUAL);
    }

    @Override
    public List<String> getSelectColumnNames() {
        return selectColumnNames;
    }
}
