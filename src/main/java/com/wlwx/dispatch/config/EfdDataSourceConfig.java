package com.wlwx.dispatch.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 手动创建数据源
 */
@Configuration
@MapperScan(basePackages = "com.wlwx.dispatch.mapper.EFDMapper", sqlSessionFactoryRef = "efdDataSqlSessionFactory" )
public class EfdDataSourceConfig {
    @Bean(name = "efdDataSource")
    @ConfigurationProperties("spring.datasource.efdDB")
    public DataSource smsDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "efdDataSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("efdDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:mapper/EFD/*.xml"));
        return sessionFactoryBean.getObject();
    }

    @Bean(name = "efdTransactionManager")
    public DataSourceTransactionManager efdTransactionManager(@Qualifier("efdDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "efdSqlSessionTemplate")
    public SqlSessionTemplate efdSqlSessionTemplate(@Qualifier("efdDataSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
