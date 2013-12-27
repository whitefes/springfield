package example.u2ware.springfield.part1.step3;


import java.util.List;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.u2ware.springfield.domain.EntityPageRequest;
import com.u2ware.springfield.repository.EntityRepository;

import example.u2ware.springfield.part1.FindByIdAndPasswordAndNameAndAddressOrderByNameDesc;
import example.u2ware.springfield.part1.MyQuery;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations="../../application-context.xml")
public class MongodbBeanRepositoryTest {

	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private MongoOperations mongoOperations;

	@Before
	public void before() throws Exception{
		mongoOperations.dropCollection(MongodbBean.class);
		for(int i = 1 ; i < 10 ; i++){
			if(! mongodbBeanRepository.exists(new MongodbBean(i), false)){
				mongodbBeanRepository.create(new MongodbBean(i , "pwd"+i, "name"+i, "addr-"+(10-i)));
			}
		}
	}
	@After
	public void afterMongoOperations() throws Exception {
		mongoOperations.dropCollection(MongodbBean.class);
	}
	
	////////////////////////////////////////////////////
	//
	///////////////////////////////////////////////////
	@Autowired @Qualifier("mongodbBeanRepository")
	private EntityRepository<MongodbBean,Integer> mongodbBeanRepository;

	@Test
	public void testWhere() throws Exception{

		EntityPageRequest pageable = new EntityPageRequest();
		pageable.addSortOrder("address" , 1);

		MongodbBean query = new MongodbBean();
		//param.setId(7);

		long count = mongodbBeanRepository.count(query);
		logger.debug(count);
		
		Page<MongodbBean> page = mongodbBeanRepository.findAll(query, pageable);
		logger.debug(page.getContent().size());
		
		Assert.assertEquals(9 , page.getContent().size());
		Assert.assertEquals(new Integer(9), page.getContent().get(0).getId());
	}
	
	///////////////////////////////////////////
	//
	//////////////////////////////////////////
	@Test
	@Transactional
	public void testWhereByQueryObject1() throws Exception{
		FindByIdAndPasswordAndNameAndAddressOrderByNameDesc query = new FindByIdAndPasswordAndNameAndAddressOrderByNameDesc();
		query.setPassword("pwd7");
		
		List<?> result = mongodbBeanRepository.findAll(query);
		Assert.assertEquals(1, result.size());
	}
	
	@Test
	@Transactional
	public void testWhereByQueryObject2() throws Exception{
		FindByIdAndPasswordAndNameAndAddressOrderByNameDesc query = new FindByIdAndPasswordAndNameAndAddressOrderByNameDesc();
		
		List<?> result = mongodbBeanRepository.findAll(query);
		Assert.assertEquals(9, result.size());
	}

	@Test
	@Transactional
	public void testWhereByQueryObject3() throws Exception{
		MyQuery query = new MyQuery();
		query.setAddress("addr-9");
		
		List<?> result = mongodbBeanRepository.findAll(query);
		Assert.assertEquals(1, result.size());
	}
	
	@Test
	@Transactional
	public void testWhereByQueryObject4() throws Exception{
		MyQuery query = new MyQuery();
		
		List<?> result = mongodbBeanRepository.findAll(query);
		Assert.assertEquals(9, result.size());
	}
	

}
//Users/admin/IDEs/mongodb-osx-x86_64-2.2.2/bin/mongod --dbpath "/Users/admin/IDEs/mongodb-osx-x86_64-2.2.2/bin/data"

