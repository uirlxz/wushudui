//package com.example.fms.config;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.JpaVendorAdapter;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.Database;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.Map;
//
//
//@Configuration
//@EnableJpaRepositories("com.example.fms.repository")
//public class JpaConfig {
//
//    private static final Logger logger = LoggerFactory.getLogger(JpaConfig.class);
//
//    @Bean
//    public DataSource dataSource(){
//        DruidDataSource ds = new DruidDataSource();
//        ds.setDriverClassName("com.mysql.jdbc.Driver");
//
//        Map<String, String> m = System.getenv();
//        String dbUrl = m.get("db_url");
//        String dbUser = m.get("db_user");
//        String dbPwd = m.get("db_pwd");
//
//        logger.debug("dbUrl = " + dbUrl);
//        logger.debug("dbUser = " + dbUser);
//        logger.debug("dbPwd = " + dbPwd);
//
//        if(dbUrl == null || dbUser == null || dbPwd == null){
//            dbUrl = "localhost:3306/download_test";
//            dbUser = "root";
//            dbPwd = "87986550";
//        }
//
//        ds.setUrl("jdbc:mysql://"+dbUrl+"?createDatabaseIfNotExist=true&characterEncoding=utf8&useSSL=true");
//        ds.setUsername(dbUser);
//        ds.setPassword(dbPwd);
//
//        // 配置初始化大小，最大，最小
//        ds.setInitialSize(10);
//        ds.setMinIdle(10);
//        ds.setMaxActive(50);
//
//        // 配置获取连接等待超时时间
//        ds.setMaxWait(60000);
//
//        // 配置间隔多久进行一次检测，检测需要关闭的空闲连接 单位是毫秒
//        ds.setTimeBetweenEvictionRunsMillis(60000);
//
//        // 一个连接在连接池的最小生存时间，单位是毫秒
//        ds.setMinEvictableIdleTimeMillis(300000);
//
//        ds.setValidationQuery("SELECT 'x'");
//        ds.setTestWhileIdle(true);
//        ds.setTestOnBorrow(false);
//        ds.setTestOnReturn(false);
//
//        // 打开PSCache,并且指定每个连接上PSCache的大小，如果用Oracle，则把poolPreparedStatements配置为true，mysql可以配置为false
//        ds.setPoolPreparedStatements(false);
//        ds.setMaxPoolPreparedStatementPerConnectionSize(20);
//
//        // 配置监控统计拦截器的filters
//        try {
//            ds.setFilters("wall,stat");
//        } catch (SQLException e) {
//            logger.error(e.toString());
//        }
//        return ds;
//    }
//
//    @Bean
//    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
//        return new JpaTransactionManager(emf);
//    }
//
//    @Bean
//    public JpaVendorAdapter jpaVendorAdapter() {
//        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
//        jpaVendorAdapter.setDatabase(Database.MYSQL);
//        jpaVendorAdapter.setGenerateDdl(true);
//        jpaVendorAdapter.setShowSql(false);
//        return jpaVendorAdapter;
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean lemfb = new LocalContainerEntityManagerFactoryBean();
//        HashMap<String, Object> propetys = new HashMap<>();
//        propetys.put("hibernate.namingStrategy", "org.hibernate.cfg.DefaultComponentSafeNamingStrategy");
////        propetys.put("hibernate.format_sql", "true");
//        lemfb.setJpaPropertyMap(propetys);
//        lemfb.setDataSource(dataSource());
//        lemfb.setJpaVendorAdapter(jpaVendorAdapter());
////        lemfb.setPackagesToScan("com.example.entity");
//        lemfb.setPackagesToScan("com.example.model");
//        return lemfb;
//    }
//
//}
