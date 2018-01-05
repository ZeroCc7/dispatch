package com.wlwx.dispatch.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * 手动创建数据源
 */
@Configuration
@MapperScan(basePackages = "com.wlwx.dispatch.mapper.SmsMapper",sqlSessionFactoryRef = "smsSqlSessionFactory")
public class SmsDataSourceConfig {
    @Bean(name = "smsDataSource")
    @ConfigurationProperties("spring.datasource.smsDB")
    @Primary
    public DataSource smsDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "smsSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("smsDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:mapper/Sms/*.xml"));
        return sessionFactoryBean.getObject();
    }


}
