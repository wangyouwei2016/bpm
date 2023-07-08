package com.dstz.springboot.autoconfigure.jdbc;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.dstz.base.common.exceptions.OptimisticLockingFieldValueException;
import com.dstz.base.enums.AbDbType;
import com.dstz.base.interceptor.AbQueryFilterInterceptor;
import com.dstz.base.interceptor.MybatisPlusInterceptorCustomizer;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ab mybatis configuration
 *
 * @author wacxhs
 */
@ConditionalOnClass({MybatisPlusAutoConfiguration.class, SqlSessionFactory.class, SqlSessionFactoryBean.class})
@Configuration
public class AbMybatisAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(ObjectProvider<MybatisPlusInterceptorCustomizer> customizerObjectProvider) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.getDbType(AbDbType.currentDbType().getDb())));

        // 乐观锁配置
        OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor = new OptimisticLockerInnerInterceptor();
        // 更新时校验乐观锁字段值
        optimisticLockerInnerInterceptor.setException(new OptimisticLockingFieldValueException("Optimistic field value is null"));
        interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor);
        customizerObjectProvider.forEach(mybatisPlusInterceptorCustomizer -> mybatisPlusInterceptorCustomizer.customize(interceptor));
        return interceptor;
    }

    @ConditionalOnMissingBean
    @Bean
    public AbQueryFilterInterceptor abQueryFilterInterceptor() {
        return new AbQueryFilterInterceptor();
    }
}
