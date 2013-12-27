package com.u2ware.springfield.config.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Configuration
@EnableTransactionManagement
public class ContextRepositoryBase implements TransactionManagementConfigurer{

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private PlatformTransactionManager annotationDrivenTransactionManager;
		
	public void setAnnotationDrivenTransactionManager(PlatformTransactionManager annotationDrivenTransactionManager) {
		this.annotationDrivenTransactionManager = annotationDrivenTransactionManager;
	}

	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return annotationDrivenTransactionManager;
	}

	/*
	@Bean
	public DataSource springfieldEmbeddedDatasource() throws Exception{
		logger.warn("springfieldEmbeddedDatasource");
		EmbeddedDatabase dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).build();
		return dataSource;
	}
	 */
}
