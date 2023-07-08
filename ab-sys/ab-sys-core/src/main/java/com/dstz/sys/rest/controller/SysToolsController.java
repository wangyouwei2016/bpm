package com.dstz.sys.rest.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.common.constats.AbAppRestConstant;
import com.dstz.base.common.enums.GlobalApiCodes;
import com.dstz.base.common.exceptions.BusinessException;
import com.dstz.base.common.utils.ConstantUtil;
import com.dstz.base.common.utils.EnumUtil;
import com.dstz.base.common.utils.HanYuPinyinUtil;
import com.dstz.base.common.utils.JsonUtils;
import com.dstz.base.web.controller.AbBaseController;
import com.fasterxml.jackson.databind.JsonNode;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;

/**
 * @author jinxia.hou
 * @Name SysToolsController
 * @description: 系统工具类
 * @date 2022/2/1410:09
 */
@RestController
@RequestMapping(AbAppRestConstant.SYS_SERVICE_PREFIX + "/tools")
public class SysToolsController extends AbBaseController {
    /**
     * <pre>
     * 根据一个枚举类的路径获取这个枚举的json形式，供前端使用
     * 注意！！如果枚举在类中间，那么路径如下：com.dstz.base.db.model.Column$TYPE
     * </pre>
     *
     * @param path
     * @param listMode
     * @param key ：在非list模式下，以枚举中的指定key来构建map
     * @return
     * @throws Exception
     */
    @RequestMapping("getEnum")
    public ApiResponse getEnum(@RequestParam("path") String path, @RequestParam("listMode") boolean listMode,@RequestParam(name="key",required = false) String key) throws Exception {
        if (listMode) {
            return ApiResponse.success(EnumUtil.toJSONArray(path));
        }
        JsonNode jsonNode = EnumUtil.toJSON(path);
        if(StrUtil.isEmpty(key)) {
        	return ApiResponse.success(jsonNode);
        }
        Map<String, Object> map = new HashMap<>(jsonNode.size());
        JsonUtils.parseObject(jsonNode, Map.class).forEach((k,v)->{
        	Map m = (Map) v;
        	map.put(m.get(key).toString(), v);
        });
        
        return ApiResponse.success(map);
    }

    /**
     * <pre>
     * 根据path(类路径)获取key（字段名）的常量
     * ps:如果key为空，会把path类的全部static final的静态变量获取出来
     * </pre>
     *
     * @param path
     * @param key
     * @return
     * @throws Exception
     */
    @RequestMapping("getConstant")
    public Object getConstant(@RequestParam("path") String path, @RequestParam("key") String key) throws Exception {
        if (StrUtil.isEmpty(key)) {
            return ConstantUtil.get(path);
        }
        return ConstantUtil.get(path, key);
    }

    @RequestMapping("getInterFaceImpls")
    public Object getInterFaceImpls(@RequestParam("classPath") String classPath) throws Exception {
        Class<?> clazz = Class.forName(classPath);
        Map<String, ?> map = SpringUtil.getBeansOfType(clazz);
        return map.values();
    }

    @RequestMapping("getInterFaceImplsResult")
    public ApiResponse<?> getInterFaceImplsResult(@RequestParam("classPath") String classPath) throws Exception {
        Class<?> clazz = Class.forName(classPath);
        Map<String, ?> map = SpringUtil.getBeansOfType(clazz);
        return ApiResponse.success(map.values());
    }

    /**
     * @param chinese
     * @param type    1：全拼 0：首字母拼音
     * @throws Exception
     */
    @PostMapping("pinyin")
    public void pinyin(@RequestBody Map<String, Object> map, HttpServletResponse response) throws Exception {
    	String chinese = (String) map.get("chinese");
    	Integer type = (Integer) map.get("type");
        String result = "";
        if (type == 1) {
            result = HanYuPinyinUtil.getPinyinString(chinese);
        } else {
            result = HanYuPinyinUtil.getFirstLetters(chinese, HanyuPinyinCaseType.LOWERCASE);
        }
        //todo oracle默认映射都大写
        /*if (DbType.ORACLE.equalsWithKey(DbContextHolder.getDbType())) {
            result = result.toUpperCase();// oracle默认映射都大写
        }*/
        response.getWriter().write(result);
    }

    /**
     * 获取当前日期
     *
     * @param format 格式化
     * @return 响应消息
     */
    @RequestMapping("getCurrentTime")
    public ApiResponse<String> getCurrentTime(@RequestParam(value = "format") String format) {
        return ApiResponse.success(DateUtil.format(new Date(), format));
    }

    @RequestMapping("getResultEnum")
    public Object getResultEnum(@RequestParam String path, @RequestParam(required = false, defaultValue = "false") Boolean listMode) throws Exception {
        if (listMode) {
            return ApiResponse.success(EnumUtil.toJSONArray(path));
        }

        return ApiResponse.success(EnumUtil.toJSON(path));
    }

    /**
     * class  present
     *
     * @param classes class name
     * @return is present
     */
    @RequestMapping(value = "/clsPresent", method = RequestMethod.POST)
    public ApiResponse<List<Boolean>> classPresent(@RequestBody List<String> classes) {
        if (CollUtil.isEmpty(classes) || classes.size() > 5) {
            return ApiResponse.fail(GlobalApiCodes.PARAMETER_INVALID.getCode(), GlobalApiCodes.PARAMETER_INVALID.getMessage());
        }
        List<Boolean> resultList = classes.stream().map(item -> ClassUtils.isPresent(item, this.getClass().getClassLoader())).collect(Collectors.toList());
        return ApiResponse.success(resultList);
    }
    
    @RequestMapping("trespass")
    public ApiResponse<String> trespass(@RequestParam String desc) {
        throw new BusinessException(GlobalApiCodes.REMOTE_CALL_ERROR.formatDefaultMessage("非法的访问，访问信息已经被统计！"));
    }
}
