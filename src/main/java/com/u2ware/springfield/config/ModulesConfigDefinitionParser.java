package com.u2ware.springfield.config;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.ejb.HibernatePersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.ReaderContext;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import com.u2ware.springfield.config.Springfield.Strategy;
import com.u2ware.springfield.config.support.Configurer;

public class ModulesConfigDefinitionParser implements BeanDefinitionParser{

	protected final Logger logger = LoggerFactory.getLogger(getClass());


	private void handleError(Exception e, Element source, ParserContext parser) {
		ReaderContext reader = parser.getReaderContext();
		reader.error(e.getMessage(), reader.extractSource(source), e.getCause());
	}
	
	private Set<BeanDefinition> findCandidateComponents(ModulesConfig modulesConfig, ParserContext parser, Class<? extends Annotation> annotationType){
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(annotationType));
		scanner.setResourceLoader(parser.getReaderContext().getResourceLoader());
		return scanner.findCandidateComponents(modulesConfig.getBasePackage());
	}

	private Set<String> beanNames = new HashSet<String>();
	
	public BeanDefinition parse(Element element, ParserContext parser) {
	
		try {
			BeanDefinitionRegistry registry = parser.getRegistry();
			
			//BeanFactoryUtils.beanNamesForTypeIncludingAncestors((ListableBeanFactory)registry, DataSource.class);
			
			ModulesConfig modulesConfig = new ModulesConfig(element, parser);
			
			logger.warn("@Springfield");
			logger.warn("\thttp://u2waremanager.github.io/springfield");
			logger.warn("\t"+modulesConfig.getBasePackage()+" scaning...");

			addJdbcTemplateConfiguration(registry, modulesConfig);

			Set<BeanDefinition> springfieldDefs  = findCandidateComponents(modulesConfig, parser, Springfield.class);
			for (BeanDefinition springfieldDef : springfieldDefs) {

				String targetClassName = springfieldDef.getBeanClassName();
				Class<?> targetClass = ClassUtils.forName(targetClassName, getClass().getClassLoader());

				Springfield targetSpringfield = AnnotationUtils.findAnnotation(targetClass, Springfield.class);
				Class<?> entityClass = targetSpringfield.entity();

				if(! Class.class.equals(entityClass)){
					logger.warn("@Springfield Mvc   : "+targetClassName);
					addConfiguration(registry, modulesConfig, entityClass, targetClass, targetSpringfield);
				}
			}
			
			for (BeanDefinition springfieldDef : springfieldDefs) {

				String targetClassName = springfieldDef.getBeanClassName();
				Class<?> targetClass = ClassUtils.forName(targetClassName, getClass().getClassLoader());

				Springfield targetSpringfield = AnnotationUtils.findAnnotation(targetClass, Springfield.class);
				Class<?> entityClass = targetSpringfield.entity();
				
				if(Class.class.equals(entityClass)){
					logger.warn("@Springfield Mvc   : "+targetClassName);
					addConfiguration(registry, modulesConfig, targetClass, targetClass, targetSpringfield);
				}
			}
			
			addSpringfieldConfigurerConfiguration(registry, modulesConfig);
			addMultipartResolverConfiguration(registry, modulesConfig);
			addMultipartHandlerConfiguration(registry, modulesConfig);

			addSpringfieldContextWebmvcBaseConfiguration(registry, modulesConfig);
			addSpringfieldContextWebmvcSecurityConfiguration(registry, modulesConfig);
			
			
			List<String> r = new ArrayList<String>(beanNames);
			Collections.sort(r);
			logger.warn("\t");
			for(String beanName : r){
				logger.warn("\t"+beanName+" = "+registry.getBeanDefinition(beanName).getBeanClassName());
			}
			logger.warn("\t");
			
			
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			handleError(e, element, parser);
		} 
		return null;
	}
	
	///////////////////////////////////////
	//
	///////////////////////////////////////
	private void registerBeanDefinition(BeanDefinitionRegistry registry, String beanName, BeanDefinition beanDefinition){
		registry.registerBeanDefinition(beanName, beanDefinition);
		beanNames.add(beanName);
	}
	
	private void registerBeanDefinition(BeanDefinitionRegistry registry, String beanName){
		//logger.debug(beanName+" = <<bean name used in registry >>");
	}
	
	
	
	
	///////////////////////////////////////
	//
	///////////////////////////////////////
	private void addConfiguration(BeanDefinitionRegistry registry, ModulesConfig modulesConfig, Class<?> entityClass, Class<?> queryClass, Springfield webapp) throws Exception{

		Strategy strategy = webapp.strategy();
		if(Strategy.NULL.equals(strategy)){
			if(StringUtils.hasText(modulesConfig.getDefaultStrategy())){
				strategy = Strategy.valueOf(modulesConfig.getDefaultStrategy());
			}else{
				strategy = Strategy.JPA;
			}
		}
		
		if(Strategy.HIBERNATE_REPOSITORY_ONLY.equals(strategy)){
			addHibernateRepositoryConfiguration(registry, modulesConfig, entityClass, queryClass, webapp);
			
		}else if(Strategy.JPA_REPOSITORY_ONLY.equals(strategy)){
			addJpaRepositoryConfiguration(registry, modulesConfig, entityClass, queryClass, webapp);

		}else if(Strategy.JDBC_REPOSITORY_ONLY.equals(strategy)){
			addJdbcRepositoryConfiguration(registry, modulesConfig, entityClass, queryClass, webapp);
	
		}else if(Strategy.SQLSESSION_REPOSITORY_ONLY.equals(strategy)){
			addSqlSessionRepositoryConfiguration(registry, modulesConfig, entityClass, queryClass, webapp);
		
		}else if(Strategy.HIBERNATE.equals(strategy)){
			addHibernateRepositoryConfiguration(registry, modulesConfig, entityClass, queryClass, webapp);
			addHibernateServiceConfiguration(registry, modulesConfig, entityClass, queryClass, webapp);
			addEntityInformationConfiguration(registry, modulesConfig, entityClass, queryClass, webapp);
			addEntityValidatorConfiguration(registry, modulesConfig, entityClass, queryClass, webapp);
			addEntityControllerConfiguration(registry, modulesConfig, entityClass, queryClass, webapp);
			
		}else if(Strategy.JPA.equals(strategy)){
			addJpaRepositoryConfiguration(registry, modulesConfig, entityClass, queryClass, webapp);
			addJpaServiceConfiguration(registry, modulesConfig, entityClass, queryClass, webapp);
			addEntityInformationConfiguration(registry, modulesConfig, entityClass, queryClass, webapp);
			addEntityValidatorConfiguration(registry, modulesConfig, entityClass, queryClass, webapp);
			addEntityControllerConfiguration(registry, modulesConfig, entityClass, queryClass, webapp);

		}else if(Strategy.JDBC.equals(strategy)){
			addJdbcRepositoryConfiguration(registry, modulesConfig, entityClass, queryClass, webapp);
			addJdbcServiceConfiguration(registry, modulesConfig, entityClass, queryClass, webapp);
			addEntityInformationConfiguration(registry, modulesConfig, entityClass, queryClass, webapp);
			addEntityValidatorConfiguration(registry, modulesConfig, entityClass, queryClass, webapp);
			addEntityControllerConfiguration(registry, modulesConfig, entityClass, queryClass, webapp);

		}else if(Strategy.SQLSESSION.equals(strategy)){
			addSqlSessionRepositoryConfiguration(registry, modulesConfig, entityClass, queryClass, webapp);
			addSqlSessionServiceConfiguration(registry, modulesConfig, entityClass, queryClass, webapp);
			addEntityInformationConfiguration(registry, modulesConfig, entityClass, queryClass, webapp);
			addEntityValidatorConfiguration(registry, modulesConfig, entityClass, queryClass, webapp);
			addEntityControllerConfiguration(registry, modulesConfig, entityClass, queryClass, webapp);
			
		}else if(Strategy.DTO.equals(strategy)){
			addDummyServiceConfiguration(registry, modulesConfig, entityClass, queryClass, webapp);
			addEntityInformationConfiguration(registry, modulesConfig, entityClass, queryClass, webapp);
			addEntityValidatorConfiguration(registry, modulesConfig, entityClass, queryClass, webapp);
			addEntityControllerConfiguration(registry, modulesConfig, entityClass, queryClass, webapp);
		}	
	}
	

	///////////////////////////////////////
	//
	///////////////////////////////////////
	private BeanDefinition getHibernatePropertiesFor(String dataSourceBeanName, Object source) {

		BeanDefinitionBuilder builder = BeanDefinitionBuilder
				.rootBeanDefinition("com.u2ware.springfield.repository.hibernate.support.HibernateDialectType");
		builder.setFactoryMethod("hibernateProperties");
		builder.addConstructorArgReference(dataSourceBeanName);
		AbstractBeanDefinition bean = builder.getRawBeanDefinition();
		bean.setSource(source);
		return bean;
	}
	private BeanDefinition getSQLTemplatesFor(String dataSourceBeanName, Object source) {
		
		BeanDefinitionBuilder builder = BeanDefinitionBuilder
				.rootBeanDefinition("com.u2ware.springfield.repository.jdbc.support.SQLTemplatesType");
		builder.setFactoryMethod("sqlTemplates");
		builder.addConstructorArgReference(dataSourceBeanName);
		AbstractBeanDefinition bean = builder.getRawBeanDefinition();
		bean.setSource(source);
		return bean;
	}

	private BeanDefinition getEntityManagerBeanDefinitionFor(String entityManagerFactoryBeanName, Object source) {

		BeanDefinitionBuilder builder = BeanDefinitionBuilder
				.rootBeanDefinition("org.springframework.orm.jpa.SharedEntityManagerCreator");
		builder.setFactoryMethod("createSharedEntityManager");
		builder.addConstructorArgReference(entityManagerFactoryBeanName);
		AbstractBeanDefinition bean = builder.getRawBeanDefinition();
		bean.setSource(source);
		return bean;
	}

	///////////////////////////////////////
	//
	///////////////////////////////////////
	private String getDataSourceTxRef(BeanDefinitionRegistry registry, ModulesConfig modulesConfig) {		
	
		String dataSourceRef = modulesConfig.getDataSourceRef();
		String dataSourceTxRef = dataSourceRef+"TransactionManager";
		if(registry.isBeanNameInUse(dataSourceTxRef)) {registerBeanDefinition(registry, dataSourceTxRef); return dataSourceTxRef;}
		
		BeanDefinition dataSourceTxDefinition = BeanDefinitionBuilder
				.rootBeanDefinition("org.springframework.jdbc.datasource.DataSourceTransactionManager")
				.addPropertyReference("dataSource", dataSourceRef)
				.getBeanDefinition();
		this.registerBeanDefinition(registry, dataSourceTxRef, dataSourceTxDefinition);
		
		
		if(StringUtils.hasText(modulesConfig.getDefaultStrategy())){
			if(Strategy.valueOf(modulesConfig.getDefaultStrategy()).equals(Strategy.SQLSESSION)
			|| Strategy.valueOf(modulesConfig.getDefaultStrategy()).equals(Strategy.SQLSESSION_REPOSITORY_ONLY)
			|| Strategy.valueOf(modulesConfig.getDefaultStrategy()).equals(Strategy.JDBC)
			|| Strategy.valueOf(modulesConfig.getDefaultStrategy()).equals(Strategy.JDBC_REPOSITORY_ONLY)
			){
				addSpringfieldContextRepositoryBaseConfiguration(registry, modulesConfig, dataSourceTxRef);
			}
		}
		
		return dataSourceTxRef;
	}
	
	private String getDataSourceTxTemplateRef(BeanDefinitionRegistry registry, ModulesConfig modulesConfig) {		

		String dataSourceRef = modulesConfig.getDataSourceRef();
		String dataSourceTxRef = getDataSourceTxRef(registry, modulesConfig);
		String dataSourceTxTemplateRef = dataSourceRef+"TransactionTemplate";
		if(registry.isBeanNameInUse(dataSourceTxTemplateRef)) {registerBeanDefinition(registry, dataSourceTxTemplateRef); return dataSourceTxTemplateRef;}

		BeanDefinition dataSourceTxTemplateRefDefinition = BeanDefinitionBuilder
				.rootBeanDefinition("org.springframework.transaction.support.TransactionTemplate")
				.addConstructorArgReference(dataSourceTxRef)
				.getBeanDefinition();
		this.registerBeanDefinition(registry, dataSourceTxTemplateRef, dataSourceTxTemplateRefDefinition);
		
		return dataSourceTxTemplateRef;
	}

	///////////////////////////////////////
	//
	///////////////////////////////////////
	private void addJdbcTemplateConfiguration(BeanDefinitionRegistry registry, ModulesConfig modulesConfig) {		
	
		String dataSourceRef = modulesConfig.getDataSourceRef();
		String jdbcTemplateRef = dataSourceRef+"JdbcTemplate";
		if(registry.isBeanNameInUse(jdbcTemplateRef)) {registerBeanDefinition(registry, jdbcTemplateRef); return ;}
		
		BeanDefinition jdbcTemplateDefinition = BeanDefinitionBuilder
				.rootBeanDefinition("org.springframework.jdbc.core.JdbcTemplate")
				.addConstructorArgReference(dataSourceRef)
				.getBeanDefinition();
		this.registerBeanDefinition(registry, jdbcTemplateRef, jdbcTemplateDefinition);
	}
	
	
	///////////////////////////////////////
	//
	///////////////////////////////////////
	private String getSqlSessionFactoryRef(BeanDefinitionRegistry registry, ModulesConfig modulesConfig) throws Exception{

		if(StringUtils.hasText(modulesConfig.getSqlSessionFactoryRef())){
			return modulesConfig.getSqlSessionFactoryRef();
			
		}else{
			String dataSourceRef = modulesConfig.getDataSourceRef();
			String sqlSessionFactoryRef = dataSourceRef+"Mybatis";
			
			String[] packagesToScan = new String[]{modulesConfig.getBasePackage()};
			//logger.trace("add mapperLocations : "+StringUtils.arrayToCommaDelimitedString(mapperLocations));
			
			if(registry.isBeanNameInUse(sqlSessionFactoryRef)) {
				BeanDefinition savedBean = registry.getBeanDefinition(sqlSessionFactoryRef);
				String[] savedValue = (String[])
						savedBean.getPropertyValues().getPropertyValue("packagesToScan").getValue();
				if(savedValue != null){
					//logger.trace("saved mapperLocations : "+StringUtils.arrayToCommaDelimitedString(savedMapperLocations));
					
					String[] newValue = new String[savedValue.length+packagesToScan.length];
					for(int i = 0; i < savedValue.length; i++){
						newValue[i] = savedValue[i];
					}
					for(int i = 0; i < packagesToScan.length; i++){
						newValue[i + packagesToScan.length] = packagesToScan[i];
					}
					packagesToScan = newValue;
				}
				registry.removeBeanDefinition(sqlSessionFactoryRef);
			}
			//logger.trace("new mappingLocations : "+StringUtils.arrayToCommaDelimitedString(mapperLocations));
			
			BeanDefinition beanDefinition = BeanDefinitionBuilder
					.rootBeanDefinition("com.u2ware.springfield.repository.sqlsession.SqlSessionFactoryBean")
					.addPropertyReference("dataSource", dataSourceRef)
					.addPropertyValue("packagesToScan", packagesToScan)
					.addPropertyValue("mapperLocationsPatterns", new String[]{"/**/*.sqlsession.xml"})
					.getBeanDefinition();

			this.registerBeanDefinition(registry, sqlSessionFactoryRef, beanDefinition);
			return sqlSessionFactoryRef;
		}
	}
	
	
	///////////////////////////////////////
	//
	///////////////////////////////////////
	private String getSessionFactoryRef(BeanDefinitionRegistry registry, ModulesConfig modulesConfig) {
		
		if(StringUtils.hasText(modulesConfig.getSessionFactoryRef())){
			return modulesConfig.getSessionFactoryRef();

		}else{
			String dataSourceRef = modulesConfig.getDataSourceRef();
			String sessionFactoryRef = dataSourceRef+"Hibernate";
			
			String[] packages = new String[]{modulesConfig.getBasePackage()};
			//logger.trace("add packages : "+StringUtils.arrayToCommaDelimitedString(packages));
			
			if(registry.isBeanNameInUse(sessionFactoryRef)) {

				BeanDefinition savedBean = registry.getBeanDefinition(sessionFactoryRef);

				String[] savedPackages = (String[])
						savedBean.getPropertyValues().getPropertyValue("packagesToScan").getValue();
				//logger.trace("saved packages : "+StringUtils.arrayToCommaDelimitedString(savedPackages));

				if(savedPackages != null){
					
					String[] newPackage = new String[packages.length+savedPackages.length];
					
					for(int i = 0; i < savedPackages.length; i++){
						newPackage[i] = savedPackages[i];
					}
					for(int i = 0; i < packages.length; i++){
						newPackage[i + savedPackages.length] = packages[i];
					}
					packages = newPackage;
				}
				registry.removeBeanDefinition(sessionFactoryRef);
			}
			//logger.trace("new packages : "+StringUtils.arrayToCommaDelimitedString(packages));

			BeanDefinition beanDefinition = BeanDefinitionBuilder
					.rootBeanDefinition("org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean")
					.addPropertyReference("dataSource", dataSourceRef)
					.addPropertyValue("packagesToScan", packages)
					.addPropertyValue("annotatedPackages", packages)
					.addPropertyValue("hibernateProperties", getHibernatePropertiesFor(dataSourceRef, modulesConfig.getSource()))
					.getBeanDefinition();
			
			this.registerBeanDefinition(registry, sessionFactoryRef, beanDefinition);
			return sessionFactoryRef;
		}
	}
	
	private String getSessionFactoryTxRef(BeanDefinitionRegistry registry, ModulesConfig modulesConfig) {

		String sessionFactoryRef = getSessionFactoryRef(registry, modulesConfig);
		String sessionFactoryTxRef = sessionFactoryRef+"TransactionManager";
		if(registry.isBeanNameInUse(sessionFactoryTxRef)) {registerBeanDefinition(registry, sessionFactoryTxRef); return sessionFactoryTxRef;}
		
		BeanDefinition sessionFactoryTxDefinition = BeanDefinitionBuilder
				.rootBeanDefinition("org.springframework.orm.hibernate3.HibernateTransactionManager")
				.addPropertyReference("sessionFactory", sessionFactoryRef)
				.getBeanDefinition();
		
		this.registerBeanDefinition(registry, sessionFactoryTxRef, sessionFactoryTxDefinition);
		
		if(StringUtils.hasText(modulesConfig.getDefaultStrategy())){
			if(Strategy.valueOf(modulesConfig.getDefaultStrategy()).equals(Strategy.HIBERNATE)
			|| Strategy.valueOf(modulesConfig.getDefaultStrategy()).equals(Strategy.HIBERNATE_REPOSITORY_ONLY)
			){
				addSpringfieldContextRepositoryBaseConfiguration(registry, modulesConfig, sessionFactoryTxRef);
			}
		}
		return sessionFactoryTxRef;
	}
	
	private String getSessionFactoryTxTemplateRef(BeanDefinitionRegistry registry, ModulesConfig modulesConfig) {

		String sessionFactoryRef = getSessionFactoryRef(registry, modulesConfig);
		String sessionFactoryTxRef = getSessionFactoryTxRef(registry, modulesConfig);
		String sessionFactoryTxTemplateRef = sessionFactoryRef+"TransactionTemplate";
		if(registry.isBeanNameInUse(sessionFactoryTxTemplateRef)) {registerBeanDefinition(registry, sessionFactoryTxTemplateRef); return sessionFactoryTxTemplateRef;}

		BeanDefinition sessionFactoryTxTemplateDefinition = BeanDefinitionBuilder
				.rootBeanDefinition("org.springframework.transaction.support.TransactionTemplate")
				.addConstructorArgReference(sessionFactoryTxRef)
				.getBeanDefinition();
		this.registerBeanDefinition(registry, sessionFactoryTxTemplateRef, sessionFactoryTxTemplateDefinition);
		
		return sessionFactoryTxTemplateRef;
	}
	
	
	///////////////////////////////////////
	//
	///////////////////////////////////////
	private String getEntityManagerFactoryRef(BeanDefinitionRegistry registry, ModulesConfig modulesConfig) throws Exception{
		
		if(StringUtils.hasText(modulesConfig.getEntityManagerFactoryRef())){
			return modulesConfig.getEntityManagerFactoryRef();
		}else{
			String dataSourceRef = modulesConfig.getDataSourceRef();
			String entityManagerFactoryRef = dataSourceRef+"Jpa";


			String[] packages = new String[]{modulesConfig.getBasePackage()};
			//logger.trace("add packages : "+StringUtils.arrayToCommaDelimitedString(packages));
			
			if(registry.isBeanNameInUse(entityManagerFactoryRef)) {
		
				BeanDefinition savedBean = registry.getBeanDefinition(entityManagerFactoryRef);
				String[] savedPackages = (String[])
						(savedBean.getPropertyValues().getPropertyValue("packagesToScan").getValue());
				//logger.trace("saved packages : "+StringUtils.arrayToCommaDelimitedString(savedPackages));

				if(savedPackages != null){
					
					String[] newPackage = new String[packages.length+savedPackages.length];
					
					for(int i = 0; i < savedPackages.length; i++){
						newPackage[i] = savedPackages[i];
					}
					for(int i = 0; i < packages.length; i++){
						newPackage[i + savedPackages.length] = packages[i];
					}
					packages = newPackage;
				}
				registry.removeBeanDefinition(entityManagerFactoryRef);
			}
			//logger.trace("new packages : "+StringUtils.arrayToCommaDelimitedString(packages));
			
			BeanDefinition beanDefinition = BeanDefinitionBuilder
					.rootBeanDefinition("org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean")
					.addPropertyReference("dataSource", dataSourceRef)
					.addPropertyValue("persistenceUnitName", entityManagerFactoryRef)
					.addPropertyValue("persistenceProviderClass", HibernatePersistence.class)
					.addPropertyValue("packagesToScan", packages)
					.addPropertyValue("jpaProperties", getHibernatePropertiesFor(dataSourceRef, modulesConfig.getSource()))
					.getBeanDefinition();
			
			this.registerBeanDefinition(registry, entityManagerFactoryRef, beanDefinition);
			return entityManagerFactoryRef;
		}
	}
	
	private String getEntityManagerFactoryTxRef(BeanDefinitionRegistry registry, ModulesConfig modulesConfig) throws Exception{
		
		String emfRef = getEntityManagerFactoryRef(registry, modulesConfig);
		String emfTxRef = emfRef+"TransactionManager";
		if(registry.isBeanNameInUse(emfTxRef)) {registerBeanDefinition(registry, emfTxRef); return emfTxRef;}
	
		BeanDefinition emfTxDefinition = BeanDefinitionBuilder
				.rootBeanDefinition("org.springframework.orm.jpa.JpaTransactionManager")
				.addPropertyReference("entityManagerFactory", emfRef)
				.getBeanDefinition();
		
		this.registerBeanDefinition(registry, emfTxRef, emfTxDefinition);

		addSpringfieldContextRepositoryBaseConfiguration(registry, modulesConfig, emfTxRef);
		
		return emfTxRef;
	}
	
	
	private String getEntityManagerFactoryTxTemplateRef(BeanDefinitionRegistry registry, ModulesConfig modulesConfig) throws Exception{
	
		String emfRef = getEntityManagerFactoryRef(registry, modulesConfig);
		String emfTxRef = getEntityManagerFactoryTxRef(registry, modulesConfig);
		String emfTxTemplateRef = emfRef+"TransactionTemplate";
		if(registry.isBeanNameInUse(emfTxTemplateRef)) {registerBeanDefinition(registry, emfTxTemplateRef); return emfTxTemplateRef;}

		
		BeanDefinition emfTxTemplateDefinition = BeanDefinitionBuilder
				.rootBeanDefinition("org.springframework.transaction.support.TransactionTemplate")
				.addConstructorArgReference(emfTxRef)
				.getBeanDefinition();
		this.registerBeanDefinition(registry, emfTxTemplateRef, emfTxTemplateDefinition);
		
		return emfTxTemplateRef;
	}
	
	
	
	///////////////////////////////////////////////
	//
	///////////////////////////////////////////////
	private void addJdbcRepositoryConfiguration(BeanDefinitionRegistry registry, ModulesConfig modulesConfig, Class<?> entityClass, Class<?> queryClass, Springfield webapp) {
		
		getDataSourceTxTemplateRef(registry, modulesConfig);

		String beanName = ClassUtils.getShortNameAsProperty(entityClass)+"Repository";
		if(registry.isBeanNameInUse(beanName)) {registerBeanDefinition(registry, beanName); return ;}
		
		String dataSourceRef = modulesConfig.getDataSourceRef();
		String jdbcTemplateRef = dataSourceRef+"JdbcTemplate";
		
		BeanDefinition beanDefinition = BeanDefinitionBuilder
				.rootBeanDefinition("com.u2ware.springfield.repository.jdbc.JdbcRepository")
				.addConstructorArgValue(entityClass)
				.addConstructorArgReference(jdbcTemplateRef)
				.addPropertyValue("dialect", getSQLTemplatesFor(dataSourceRef, modulesConfig.getSource()))
				.getBeanDefinition();
		
		this.registerBeanDefinition(registry, beanName, beanDefinition);
	}
	

	private void addSqlSessionRepositoryConfiguration(BeanDefinitionRegistry registry, ModulesConfig modulesConfig, Class<?> entityClass, Class<?> queryClass, Springfield webapp) throws Exception{

		getDataSourceTxTemplateRef(registry, modulesConfig);

		String beanName = ClassUtils.getShortNameAsProperty(entityClass)+"Repository";
		if(registry.isBeanNameInUse(beanName)) {registerBeanDefinition(registry, beanName); return ;}
	
		String sqlSessionFactoryRef = getSqlSessionFactoryRef(registry, modulesConfig);
		
		BeanDefinition beanDefinition = BeanDefinitionBuilder
				.rootBeanDefinition("com.u2ware.springfield.repository.sqlsession.SqlSessionRepository")
				.addConstructorArgValue(entityClass)
				.addConstructorArgReference(sqlSessionFactoryRef)
				.getBeanDefinition();
		
		this.registerBeanDefinition(registry, beanName, beanDefinition);
	}


	private void addHibernateRepositoryConfiguration(BeanDefinitionRegistry registry, ModulesConfig modulesConfig, Class<?> entityClass, Class<?> queryClass, Springfield webapp) {

		getSessionFactoryTxTemplateRef(registry, modulesConfig);
		
		String beanName = ClassUtils.getShortNameAsProperty(entityClass)+"Repository";
		if(registry.isBeanNameInUse(beanName)) {registerBeanDefinition(registry, beanName); return ;}

		String sessionFactoryRef = getSessionFactoryRef(registry, modulesConfig);
		
		BeanDefinition beanDefinition = BeanDefinitionBuilder
				.rootBeanDefinition("com.u2ware.springfield.repository.hibernate.HibernateRepository")
				.addConstructorArgValue(entityClass)
				.addConstructorArgReference(sessionFactoryRef)
				.getBeanDefinition();
		
		this.registerBeanDefinition(registry, beanName, beanDefinition);
	}
	
	private void addJpaRepositoryConfiguration(BeanDefinitionRegistry registry, ModulesConfig modulesConfig, Class<?> entityClass, Class<?> queryClass, Springfield webapp) throws Exception{

		getEntityManagerFactoryTxTemplateRef(registry, modulesConfig);

		String beanName = ClassUtils.getShortNameAsProperty(entityClass)+"Repository";
		if(registry.isBeanNameInUse(beanName)) {registerBeanDefinition(registry, beanName); return ;}

		getEntityManagerFactoryTxRef(registry, modulesConfig);
		String entityManagerFactoryRef = getEntityManagerFactoryRef(registry, modulesConfig);
		BeanDefinition entityManagerFactoryValue = getEntityManagerBeanDefinitionFor(entityManagerFactoryRef, modulesConfig.getSource());
		
		BeanDefinition beanDefinition = BeanDefinitionBuilder
				.rootBeanDefinition("com.u2ware.springfield.repository.jpa.JpaRepository")
				.addConstructorArgValue(entityClass)
				.addConstructorArgValue(entityManagerFactoryValue)
				.getBeanDefinition();
		
		this.registerBeanDefinition(registry, beanName, beanDefinition);
	}
	
	
	////////////////////////////////////////////////////////////////////////
	//
	////////////////////////////////////////////////////////////////////////
	private void addDummyServiceConfiguration(BeanDefinitionRegistry registry, ModulesConfig modulesConfig, Class<?> entityClass, Class<?> queryClass, Springfield webapp) throws Exception{
		String beanName = ClassUtils.getShortNameAsProperty(queryClass)+"Service";
		if(registry.isBeanNameInUse(beanName)) {registerBeanDefinition(registry, beanName); return ;}
		
		BeanDefinition beanDefinition = BeanDefinitionBuilder
				.rootBeanDefinition("com.u2ware.springfield.service.EntityServiceImpl")
				.getBeanDefinition();
		
		this.registerBeanDefinition(registry, beanName, beanDefinition);
	}
	
	private void addJdbcServiceConfiguration(BeanDefinitionRegistry registry, ModulesConfig modulesConfig, Class<?> entityClass, Class<?> queryClass, Springfield webapp) throws Exception{

		String beanName = ClassUtils.getShortNameAsProperty(queryClass)+"Service";
		if(registry.isBeanNameInUse(beanName)) {registerBeanDefinition(registry, beanName); return ;}

		String repositoryRef = ClassUtils.getShortNameAsProperty(entityClass)+"Repository";
		String txTemplateRef = getDataSourceTxTemplateRef(registry, modulesConfig);
		
		BeanDefinition beanDefinition = BeanDefinitionBuilder
				.rootBeanDefinition("com.u2ware.springfield.service.EntityServiceImpl")
				.addConstructorArgReference(txTemplateRef)
				.addConstructorArgReference(repositoryRef)
				.getBeanDefinition();
		
		this.registerBeanDefinition(registry, beanName, beanDefinition);
	}
	
	private void addSqlSessionServiceConfiguration(BeanDefinitionRegistry registry, ModulesConfig modulesConfig, Class<?> entityClass, Class<?> queryClass, Springfield webapp) throws Exception{
		
		String beanName = ClassUtils.getShortNameAsProperty(queryClass)+"Service";
		if(registry.isBeanNameInUse(beanName)) {registerBeanDefinition(registry, beanName); return ;}

		String repositoryRef = ClassUtils.getShortNameAsProperty(entityClass)+"Repository";
		String txTemplateRef = getDataSourceTxTemplateRef(registry, modulesConfig);
		
		BeanDefinition beanDefinition = BeanDefinitionBuilder
				.rootBeanDefinition("com.u2ware.springfield.service.EntityServiceImpl")
				.addConstructorArgReference(txTemplateRef)
				.addConstructorArgReference(repositoryRef)
				.getBeanDefinition();
		
		this.registerBeanDefinition(registry, beanName, beanDefinition);
	}

	private void addHibernateServiceConfiguration(BeanDefinitionRegistry registry, ModulesConfig modulesConfig, Class<?> entityClass, Class<?> queryClass, Springfield webapp) throws Exception{

		String beanName = ClassUtils.getShortNameAsProperty(queryClass)+"Service";
		if(registry.isBeanNameInUse(beanName)) {registerBeanDefinition(registry, beanName); return ;}

		String repositoryRef = ClassUtils.getShortNameAsProperty(entityClass)+"Repository";
		String txTemplateRef = getSessionFactoryTxTemplateRef(registry, modulesConfig);
		
		BeanDefinition beanDefinition = BeanDefinitionBuilder
				.rootBeanDefinition("com.u2ware.springfield.service.EntityServiceImpl")
				.addConstructorArgReference(txTemplateRef)
				.addConstructorArgReference(repositoryRef)
				.getBeanDefinition();
		
		this.registerBeanDefinition(registry, beanName, beanDefinition);
	}


	private void addJpaServiceConfiguration(BeanDefinitionRegistry registry, ModulesConfig modulesConfig, Class<?> entityClass, Class<?> queryClass, Springfield webapp) throws Exception{


		String beanName = ClassUtils.getShortNameAsProperty(queryClass)+"Service";
		if(registry.isBeanNameInUse(beanName)) {registerBeanDefinition(registry, beanName); return ;}

		String repositoryRef = ClassUtils.getShortNameAsProperty(entityClass)+"Repository";
		String txTemplateRef = getEntityManagerFactoryTxTemplateRef(registry, modulesConfig);
		
		BeanDefinition beanDefinition = BeanDefinitionBuilder
				.rootBeanDefinition("com.u2ware.springfield.service.EntityServiceImpl")
				.addConstructorArgReference(txTemplateRef)
				.addConstructorArgReference(repositoryRef)
				.getBeanDefinition();
		
		this.registerBeanDefinition(registry, beanName, beanDefinition);
	}
	
	
	///////////////////////////////////////
	//
	///////////////////////////////////////
	private void addEntityInformationConfiguration(BeanDefinitionRegistry registry, ModulesConfig modulesConfig, Class<?> entityClass, Class<?> queryClass, Springfield webapp) throws Exception{

		String beanName = ClassUtils.getShortNameAsProperty(queryClass)+"Information";
		if(registry.isBeanNameInUse(beanName)) {registerBeanDefinition(registry, beanName); return ;}
		
		String topLevelMapping = webapp != null ? webapp.topLevelMapping() : "";
		if(! StringUtils.hasText(topLevelMapping)){
			String root = ClassUtils.convertClassNameToResourcePath(modulesConfig.getBasePackage());
			topLevelMapping = ClassUtils.classPackageAsResourcePath(queryClass).replaceAll(root, "");
		}
		
		String[] methodLevelMapping = webapp.methodLevelMapping();
		String[] identity = webapp.identity();
		String attributesCSV = webapp.attributesCSV();

		//logger.info(beanName+ " "+attributesCSV);
		
		BeanDefinition beanDefinition = BeanDefinitionBuilder
				.rootBeanDefinition("com.u2ware.springfield.domain.EntityInformation")
				.addConstructorArgValue(modulesConfig.getBasePackage())
				.addConstructorArgValue(entityClass)
				.addConstructorArgValue(queryClass)
				.addConstructorArgValue(topLevelMapping)
				.addConstructorArgValue(methodLevelMapping)
				.addConstructorArgValue(identity)
				.addConstructorArgValue(attributesCSV)

				.getBeanDefinition();

		this.registerBeanDefinition(registry, beanName, beanDefinition);
	}

	
	///////////////////////////////////////
	//
	///////////////////////////////////////
	private void addEntityValidatorConfiguration(BeanDefinitionRegistry registry, ModulesConfig modulesConfig, Class<?> entityClass, Class<?> queryClass, Springfield webapp) throws Exception{

		String beanName = ClassUtils.getShortNameAsProperty(queryClass)+"Validator";
		if(registry.isBeanNameInUse(beanName)) {registerBeanDefinition(registry, beanName); return ;}
		
		BeanDefinition beanDefinition = BeanDefinitionBuilder
				.rootBeanDefinition("com.u2ware.springfield.validation.EntityValidatorImpl")
				.getBeanDefinition();

		this.registerBeanDefinition(registry, beanName, beanDefinition);
	}

	
	
	///////////////////////////////////////
	//
	///////////////////////////////////////
	private void addEntityControllerConfiguration(BeanDefinitionRegistry registry, ModulesConfig modulesConfig, Class<?> entityClass, Class<?> queryClass, Springfield webapp) throws Exception{


		String beanName = ClassUtils.getShortNameAsProperty(queryClass)+"Controller";
		if(registry.isBeanNameInUse(beanName)) {registerBeanDefinition(registry, beanName); return ;}

		//logger.info(beanName);
		String informationName = ClassUtils.getShortNameAsProperty(queryClass)+"Information";
		String serviceName = ClassUtils.getShortNameAsProperty(queryClass)+"Service";
		String validatorName = ClassUtils.getShortNameAsProperty(queryClass)+"Validator";
		
		BeanDefinition beanDefinition = BeanDefinitionBuilder
				.rootBeanDefinition("com.u2ware.springfield.controller.EntityControllerImpl")
				.addConstructorArgReference(informationName)
				.addConstructorArgReference(serviceName)
				.addConstructorArgReference(validatorName)
				.getRawBeanDefinition();
				
		this.registerBeanDefinition(registry, beanName, beanDefinition);
	}
	
	///////////////////////////////////////
	//
	///////////////////////////////////////
	private void addSpringfieldConfigurerConfiguration(BeanDefinitionRegistry registry, ModulesConfig modulesConfig) {
		String beanName = Configurer.BEAN_NAME;
		if(registry.isBeanNameInUse(beanName)) {registry.removeBeanDefinition(beanName);}

		BeanDefinitionBuilder builder = BeanDefinitionBuilder
				.rootBeanDefinition("com.u2ware.springfield.config.support.Configurer")
				.addPropertyValue("basePackage", modulesConfig.getBasePackage());
		if(StringUtils.hasText(modulesConfig.getPropertiesRef())){
			builder.addPropertyReference("properties", modulesConfig.getPropertiesRef());
		}
		BeanDefinition beanDefinition = builder.getRawBeanDefinition();

		this.registerBeanDefinition(registry, beanName, beanDefinition);
	}
	private String getSpringfieldConfigurerValue(String key){
		return "#{"+Configurer.BEAN_NAME+"['"+key+"']}";
	}

	
	
	///////////////////////////////////////
	//
	///////////////////////////////////////
	private void addMultipartResolverConfiguration(BeanDefinitionRegistry registry, ModulesConfig modulesConfig) {
		
		String beanName = "filterMultipartResolver";
		if(registry.isBeanNameInUse(beanName)) {registry.removeBeanDefinition(beanName);}

		BeanDefinition beanDefinition = BeanDefinitionBuilder
				.rootBeanDefinition("org.springframework.web.multipart.commons.CommonsMultipartResolver")
				.addPropertyValue("maxUploadSize", getSpringfieldConfigurerValue(Configurer.MULTIPART_SIZE) )
				.getRawBeanDefinition();
				
		this.registerBeanDefinition(registry, beanName, beanDefinition);
	}
	
	private void addMultipartHandlerConfiguration(BeanDefinitionRegistry registry, ModulesConfig modulesConfig) {
		
		String beanName = "springfieldMultipartFileHandler";
		if(registry.isBeanNameInUse(beanName)) {registry.removeBeanDefinition(beanName);}

		BeanDefinition beanDefinition = BeanDefinitionBuilder
				.rootBeanDefinition("com.u2ware.springfield.support.multipart.MultipartFileHandlerImpl")
				.addPropertyValue("directory", getSpringfieldConfigurerValue(Configurer.MULTIPART_LOCATION))
				.getRawBeanDefinition();
				
		this.registerBeanDefinition(registry, beanName, beanDefinition);
	}

	private void addSpringfieldContextWebmvcBaseConfiguration(BeanDefinitionRegistry registry, ModulesConfig modulesConfig) {

		String beanName = "springfieldContextWebmvcBase";
		if(registry.isBeanNameInUse(beanName)) {registry.removeBeanDefinition(beanName);}

		BeanDefinition beanDefinition = BeanDefinitionBuilder
				.rootBeanDefinition("com.u2ware.springfield.config.support.ContextWebmvcBase")
				.addPropertyReference("configurer", Configurer.BEAN_NAME)
				.getRawBeanDefinition();
				
		this.registerBeanDefinition(registry, beanName, beanDefinition);
	}
	private void addSpringfieldContextWebmvcSecurityConfiguration(BeanDefinitionRegistry registry, ModulesConfig modulesConfig) {

		String beanName = "springfieldContextWebmvcSecurity";
		if(registry.isBeanNameInUse(beanName)) {registry.removeBeanDefinition(beanName);}

		BeanDefinition beanDefinition = BeanDefinitionBuilder
				.rootBeanDefinition("com.u2ware.springfield.config.support.ContextWebmvcSecurity")
				.addPropertyReference("configurer", Configurer.BEAN_NAME)
				.getRawBeanDefinition();
				
		this.registerBeanDefinition(registry, beanName, beanDefinition);
	}

	
	private void addSpringfieldContextRepositoryBaseConfiguration(BeanDefinitionRegistry registry, ModulesConfig modulesConfig, String annotationDrivenTransactionManagerRef) {
		String beanName = "springfieldContextRepositoryBase";
		if(registry.isBeanNameInUse(beanName)) {registry.removeBeanDefinition(beanName);}

		BeanDefinition beanDefinition = BeanDefinitionBuilder
				.rootBeanDefinition("com.u2ware.springfield.config.support.ContextRepositoryBase")
				.addPropertyReference("annotationDrivenTransactionManager", annotationDrivenTransactionManagerRef)
				.getRawBeanDefinition();
				
		this.registerBeanDefinition(registry, beanName, beanDefinition);
	}
	
	
}
	



