package com.dstz.cms.core.helper;

import cn.hutool.core.util.StrUtil;
import com.dstz.base.common.exceptions.BusinessException;
import com.dstz.base.common.utils.JsonUtils;
import com.dstz.base.entity.IPersistentEntity;
import com.dstz.base.mapper.AbBaseMapper;
import com.dstz.cms.core.entity.dto.CmsJsonDTO;
import com.dstz.sys.api.SysFileApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Objects;

import static com.dstz.cms.api.constant.CmsConstant.COMMENTS_NUM;
import static com.dstz.sys.api.constant.SysApiCodes.REFLEX_WARNING;

/**
 * CMS系统工具类
 *
 * @author niu
 * @since 2022-03-04
 */
@Service
public class CmsHelper {

    @Autowired
    private AbBaseMapper<?> cmsNotifyMapper, cmsNewsMapper;
    @Autowired
    private SysFileApi sysFileApi;

    /**
     * 更新新闻或公告的评论数量
     *
     * @param commentType 业务类型(公告或者新闻)
     * @param id          业务ID(公告ID或者新闻ID)
     * @param flag        增加数量 true  减少数量 false
     * @return int   改动条数
     */
    public int updateCommentsNum(int commentType, String id, boolean flag) {
        try {
            //根据传参确定mapper类型
            AbBaseMapper<IPersistentEntity> mapper = (AbBaseMapper<IPersistentEntity>) getInformation(commentType);
            //获取新闻或者公告的实体
            IPersistentEntity iPersistentEntity = mapper.selectById(id);
            if (iPersistentEntity == null) {
                return 0;
            }
            int commentsNum;
            commentsNum = Integer.parseInt(Objects.requireNonNull(getGetMethod(iPersistentEntity, COMMENTS_NUM)).toString());
            setValue(iPersistentEntity, iPersistentEntity.getClass(), COMMENTS_NUM, Integer.class,
                    flag ? commentsNum + 1 : commentsNum > 0 ? commentsNum - 1 : 0);
            return mapper.updateById(iPersistentEntity);
        } catch (Exception e) {
            throw new BusinessException(REFLEX_WARNING);
        }
    }

    /**
     * 根据评论类型判断应该调用哪个实现
     *
     * @param commentType 评论类型(0公告, 1新闻)
     * @return 操作父类
     */
    private AbBaseMapper<?> getInformation(int commentType) {
        return commentType == 0 ? cmsNotifyMapper : cmsNewsMapper;
    }

    /**
     * 通过json删除文件
     *
     * @param json 关联得文件Json字符串
     */
    public void deleteFile(String json) {
        if (StrUtil.isNotBlank(json)) {
            sysFileApi.delete(Objects.requireNonNull(JsonUtils.parseArray(json, CmsJsonDTO.class))
                    .stream().map(CmsJsonDTO::getId).toArray(String[]::new));
        }
    }

    /**
     * 根据属性，获取get方法
     *
     * @param ob   对象
     * @param name 属性名
     */
    public static Object getGetMethod(Object ob, String name) throws Exception {
        Method[] m = ob.getClass().getMethods();
        for (int i = m.length - 1; i >= 0; i--) {
            if (("get" + name).equalsIgnoreCase(m[i].getName())) {
                return m[i].invoke(ob);
            }
        }
        return null;
    }

    /**
     * 根据属性，拿到set方法，并把值set到对象中
     *
     * @param obj       对象
     * @param clazz     对象的class
     * @param filedName 需要设置值得属性
     */
    public static void setValue(Object obj, Class<?> clazz, String filedName, Class<?> typeClass, Object value) {
        filedName = removeLine(filedName);
        String methodName = "set" + filedName.substring(0, 1).toUpperCase() + filedName.substring(1);
        try {
            Method method = clazz.getDeclaredMethod(methodName, typeClass);
            method.invoke(obj, getClassTypeValue(typeClass, value));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 通过class类型获取获取对应类型的值
     *
     * @param typeClass class类型
     * @param value     值
     * @return Object
     */
    private static Object getClassTypeValue(Class<?> typeClass, Object value) {
        if (typeClass == int.class || value instanceof Integer) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == short.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == byte.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == double.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == long.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == String.class) {
            if (null == value) {
                return "";
            }
            return value;
        } else if (typeClass == boolean.class) {
            if (null == value) {
                return true;
            }
            return value;
        } else if (typeClass == BigDecimal.class) {
            if (null == value) {
                return new BigDecimal(0);
            }
            return new BigDecimal(value + "");
        } else {
            return typeClass.cast(value);
        }
    }


    /**
     * 处理字符串  如：  abc_dex ---> abcDex
     */
    public static String removeLine(String str) {
        if (null != str && str.contains("_")) {
            int i = str.indexOf("_");
            char ch = str.charAt(i + 1);
            char newCh = (ch + "").substring(0, 1).toUpperCase().toCharArray()[0];
            String newStr = str.replace(str.charAt(i + 1), newCh);
            return newStr.replace("_", "");
        }
        return str;
    }


    /**
     * 获取文件大小字符串
     *
     * @param i 数字
     * @return 转换后的字符串
     */
    public static String getSize(long i) {
        String result = "";
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        DecimalFormat df = new DecimalFormat("#.00");
        if (i >= gb) {
            result = df.format((float) i / gb) + "GB";
        } else if (i >= mb) {
            result = df.format((float) i / mb) + "MB";
        } else if (i >= kb) {
            result = String.format("%.2f", (float) i / kb) + "KB";
        } else {
            result = i + "B";
        }
        return result;
    }

}