package com.dstz;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.CrossOrigin;

import cn.hutool.extra.spring.EnableSpringUtil;

/**
 * <pre>
 * 应用程序主入口
 * 作者:jeff
 * 邮箱:jeff@agilebpm.cn
 * 日期:2020年1月17日
 * 版权: 深圳市大世同舟信息科技有限公司
 * </pre>
 */
@EnableSpringUtil
@SpringBootApplication
@EnableAsync
@CrossOrigin
public class AbSpringBootApp extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AbSpringBootApp.class, args);
    }
}
