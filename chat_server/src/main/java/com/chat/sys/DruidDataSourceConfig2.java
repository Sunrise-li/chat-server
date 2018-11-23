package com.chat.sys;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import com.alibaba.druid.pool.DruidDataSource;


/**
 * 多数据源
 * @author ldz
 *
 */
//@Configuration
//@MapperScan(basePackages = {"com.chat.mapper"}, sqlSessionFactoryRef = "twoSqlSessionFactory")
public class DruidDataSourceConfig2 {
	
	@Value("${spring.datasource.druid.url}")  
    private String dbUrl;  
    @Value("${spring.datasource.druid.username}")  
    private String username;  
    @Value("${spring.datasource.druid.password}")  
    private String password;  
    @Value("${spring.datasource.druid.driver-class-name}")  
    private String driverClassName;  
    @Value("${spring.datasource.druid.initial-size}")  
    private int initialSize;  
    @Value("${spring.datasource.druid.min-idle}")  
    private int minIdle;  
    @Value("${spring.datasource.druid.max-active}")  
    private int maxActive;  
    @Value("${spring.datasource.druid.max-wait}")  
    private int maxWait;  
    @Value("${spring.datasource.druid.timeBetweenEvictionRunsMillis}")  
    private int timeBetweenEvictionRunsMillis;  
    @Value("${spring.datasource.druid.minEvictableIdleTimeMillis}")  
    private int minEvictableIdleTimeMillis;  
    @Value("${spring.datasource.druid.validationQuery}")  
    private String validationQuery;  
    @Value("${spring.datasource.druid.testWhileIdle}")  
    private boolean testWhileIdle;  
    @Value("${spring.datasource.druid.testOnBorrow}")  
    private boolean testOnBorrow;  
    @Value("${spring.datasource.druid.testOnReturn}")  
    private boolean testOnReturn;  
    @Value("${spring.datasource.druid.poolPreparedStatements}")  
    private boolean poolPreparedStatements;  
    @Value("${spring.datasource.druid.maxPoolPreparedStatementPerConnectionSize}")  
    private int maxPoolPreparedStatementPerConnectionSize;  
    @Value("${spring.datasource.druid.filters}")  
    private String filters;  
    @Value("${spring.datasource.druid.connectionProperties}")  
    private String connectionProperties;  
    @Value("${spring.datasource.druid.useGlobalDataSourceStat}")  
    private boolean useGlobalDataSourceStat;  
  
    @Bean(name = "two")
    public DataSource dataSource(){
        DruidDataSource datasource = new DruidDataSource();  
        datasource.setUrl(this.dbUrl);  
        datasource.setUsername(username);  
        datasource.setPassword(password);  
        datasource.setDriverClassName(driverClassName);  
        //configuration  
        datasource.setInitialSize(initialSize);  
        datasource.setMinIdle(minIdle);  
        datasource.setMaxActive(maxActive);  
        datasource.setMaxWait(maxWait);  
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);  
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);  
        datasource.setValidationQuery(validationQuery);  
        datasource.setTestWhileIdle(testWhileIdle);  
        datasource.setTestOnBorrow(testOnBorrow);  
        datasource.setTestOnReturn(testOnReturn);  
        datasource.setPoolPreparedStatements(poolPreparedStatements);  
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);  
        datasource.setUseGlobalDataSourceStat(useGlobalDataSourceStat); 
        try {  
            datasource.setFilters(filters);  
        } catch (SQLException e) {  
            System.err.println("druid configuration initialization filter: "+ e);  
        }  
        datasource.setConnectionProperties(connectionProperties);  
        return datasource;  
    }

	@Bean(name = "twoTransactionManager")
    public DataSourceTransactionManager twoTransactionManager(@Qualifier("two") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

	@Bean(name = "twoSqlSessionFactory")
	public SqlSessionFactory twoSqlSessionFactory(@Qualifier("two") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		return factoryBean.getObject();
	}
	@Bean(name = "twoSqlSessionTemplate")
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("twoSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}