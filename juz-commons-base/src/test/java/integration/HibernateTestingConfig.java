package integration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.juz.common.config.ScanPersistencePackages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;

import static com.google.common.collect.Iterables.toArray;
import static com.google.common.collect.Sets.newHashSet;

@EnableTransactionManagement
@Configuration
class HibernateTestingConfig {

	@Autowired
	private Collection<ScanPersistencePackages> scanPackages;

	// @formatter:off
	@Bean(destroyMethod = "close")
	DataSource dataSource(
			@Value("${db.url}") String url,
			@Value("${db.user}") String user,
			@Value("${db.password}") String password,
			@Value("${db.dataSourceClass:org.h2.jdbcx.JdbcDataSource}") String dataSourceClassName,
			@Value("${db.maximumPoolSize:10}") int maxPoolSize,
			@Value("${db.idleTimeoutSec:60}") int idleTimeoutSec
	) throws PropertyVetoException {
		// @formatter:on
		HikariConfig config = new HikariConfig();
		config.setMaximumPoolSize(maxPoolSize);
		config.setDataSourceClassName(dataSourceClassName);
		config.addDataSourceProperty("URL", url);
		config.addDataSourceProperty("user", user);
		config.addDataSourceProperty("password", password);
		config.setIdleTimeout(idleTimeoutSec * 1000);
		config.setAutoCommit(false);
		return new HikariDataSource(config);
	}

	// @formatter:off
	@Bean
	Properties hibernateProperties(
			@Value("${hibernate.dialect}") String dialect,
			@Value("${hibernate.hbm2ddl.auto}") String hbm2ddl,
			@Value("${hibernate.show_sql}") boolean showSql,
			@Value("${db.fetch:0}") int fetchSize
	) {
		// @formatter:on

		Properties properties = new Properties();
		properties.put("hibernate.dialect", dialect);
		properties.put("hibernate.show_sql", showSql);

		properties.put("hibernate.hbm2ddl.auto", hbm2ddl);
		properties.put("hibernate.jdbc.fetch_size", fetchSize);
		properties.put("hibernate.order_updates", true);
		properties.put("hibernate.cache.use_second_level_cache", true);
		properties.put("hibernate.cache.use_query_cache", true);
		properties.put("hibernate.cache.region.factory_class", SingletonEhCacheRegionFactory.class.getName());
		properties.put("hibernate.ejb.naming_strategy", ImprovedNamingStrategy.class.getName());
		properties.put("org.hibernate.envers.audit_table_prefix", "AUDIT_");
		properties.put("org.hibernate.envers.audit_table_suffix", "");

		properties.put("jadira.usertype.autoRegisterUserTypes", "true");
		properties.put("jadira.usertype.databaseZone", "jvm");

		return properties;
	}

	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, @Qualifier("hibernateProperties") Properties properties) {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan(packagesToScan());
		entityManagerFactoryBean.setJpaProperties(properties);
		return entityManagerFactoryBean;
	}

//	@Bean
//	public SessionFactory sessionFactory(DataSource dataSource, @Qualifier("hibernateProperties") Properties properties) throws Exception {
//
//		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
//		sessionFactoryBean.setDataSource(dataSource);
//		sessionFactoryBean.setPackagesToScan(packagesToScan());
//		sessionFactoryBean.setHibernateProperties(properties);
//		sessionFactoryBean.afterPropertiesSet();
//		return sessionFactoryBean.getObject();
//	}

//	@Bean
//	public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
//		return new HibernateTransactionManager(sessionFactory);
//	}

	private String[] packagesToScan() {
		Set<String> packages = newHashSet();
		for (ScanPersistencePackages scanPackage : scanPackages) {
			packages.addAll(scanPackage.asStrings());
		}
		return toArray(packages, String.class);
	}
}
