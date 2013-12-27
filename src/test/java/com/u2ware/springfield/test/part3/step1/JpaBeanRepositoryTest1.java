package com.u2ware.springfield.test.part3.step1;

import junit.framework.Assert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.u2ware.springfield.domain.PaginationRequest;
import com.u2ware.springfield.sample.part3.step1.JpaBean;
import com.u2ware.springfield.sample.part3.step1.JpaBeanComponent;
import com.u2ware.springfield.test.AbstractRootContextTest;

public class JpaBeanRepositoryTest1 extends AbstractRootContextTest{

	@Autowired 
	private JpaBeanComponent jpaBeanComponent;


	private JpaBean[] createEntities(){
		JpaBean[] r = new JpaBean[10];
		for(int i = 1 ; i < 10 ; i++){
			r[i] = new JpaBean(
					"id"+i , 
					i % 2 == 0 ? "XX"+i+"YY" : "AA"+i+"BB", 
					i % 2 == 0 ? null : i,  
					i % 2 == 0);
		}		
		return r;
	}
	
	
	@Test
	public void testAll() throws Exception{
		jpaBeanComponent.resetAll(createEntities());

		Pageable pageable = new PaginationRequest(0, 100);
		Page<JpaBean> page = null;
		
		
		page = jpaBeanComponent.findAll(new FindBy(), pageable);
		Assert.assertEquals(9 , page.getTotalElements());

		
		page = jpaBeanComponent.findAll(new FindById(null), pageable);
		Assert.assertEquals(9 , page.getTotalElements());

		page = jpaBeanComponent.findAll(new FindById("id1"), pageable);
		Assert.assertEquals(1 , page.getTotalElements());
		
		page = jpaBeanComponent.findAll(new FindByIdIsNot(null), pageable);
		Assert.assertEquals(9 , page.getTotalElements());

		page = jpaBeanComponent.findAll(new FindByIdIsNot("id1"), pageable);
		Assert.assertEquals(8 , page.getTotalElements());
		
		page = jpaBeanComponent.findAll(new FindByAgeBetween(null), pageable);
		Assert.assertEquals(9 , page.getTotalElements());

		page = jpaBeanComponent.findAll(new FindByAgeBetween(new Integer[]{2 , 8}), pageable);
		Assert.assertEquals(3 , page.getTotalElements());

		
		page = jpaBeanComponent.findAll(new FindByAgeGreaterThan(null), pageable);
		Assert.assertEquals(9 , page.getTotalElements());

		page = jpaBeanComponent.findAll(new FindByAgeGreaterThan(2), pageable);
		Assert.assertEquals(4 , page.getTotalElements());
		
		page = jpaBeanComponent.findAll(new FindByAgeGreaterThanEqual(null), pageable);
		Assert.assertEquals(9 , page.getTotalElements());


		page = jpaBeanComponent.findAll(new FindByAgeGreaterThanEqual(3), pageable);
		Assert.assertEquals(4 , page.getTotalElements());
	
		page = jpaBeanComponent.findAll(new FindByAgeGreaterThanEqual(null), pageable);
		Assert.assertEquals(9 , page.getTotalElements());


		page = jpaBeanComponent.findAll(new FindByAgeIsNull(null), pageable);
		Assert.assertEquals(4 , page.getTotalElements());

		page = jpaBeanComponent.findAll(new FindByAgeIsNull(3), pageable);
		Assert.assertEquals(9 , page.getTotalElements());
		
		page = jpaBeanComponent.findAll(new FindByAgeIsNotNull(null), pageable);
		Assert.assertEquals(9 , page.getTotalElements());

		page = jpaBeanComponent.findAll(new FindByAgeIsNotNull(3), pageable);
		Assert.assertEquals(5 , page.getTotalElements());

		page = jpaBeanComponent.findAll(new FindByNameStartingWith(null), pageable);
		Assert.assertEquals(9 , page.getTotalElements());
		
		page = jpaBeanComponent.findAll(new FindByNameStartingWith("X"), pageable);
		Assert.assertEquals(4 , page.getTotalElements());

		page = jpaBeanComponent.findAll(new FindByNameEndingWith(null), pageable);
		Assert.assertEquals(9 , page.getTotalElements());


		page = jpaBeanComponent.findAll(new FindByNameEndingWith("Y"), pageable);
		Assert.assertEquals(4 , page.getTotalElements());

		page = jpaBeanComponent.findAll(new FindByNameContaining(null), pageable);
		Assert.assertEquals(9 , page.getTotalElements());
		
		page = jpaBeanComponent.findAll(new FindByNameContaining("A"), pageable);
		Assert.assertEquals(5 , page.getTotalElements());
		

		page = jpaBeanComponent.findAll(new FindByNameLike(null), pageable);
		Assert.assertEquals(9 , page.getTotalElements());
		
		page = jpaBeanComponent.findAll(new FindByNameLike("%1%"), pageable);
		Assert.assertEquals(1 , page.getTotalElements());
		
		page = jpaBeanComponent.findAll(new FindBySexIsTrue(null), pageable);
		Assert.assertEquals(9 , page.getTotalElements());
		
		page = jpaBeanComponent.findAll(new FindBySexIsTrue(true), pageable);
		Assert.assertEquals(4 , page.getTotalElements());

		page = jpaBeanComponent.findAll(new FindBySexIsTrue(false), pageable);
		Assert.assertEquals(9 , page.getTotalElements());
		
		page = jpaBeanComponent.findAll(new FindBySexIsFalse(null), pageable);
		Assert.assertEquals(9 , page.getTotalElements());
		
		page = jpaBeanComponent.findAll(new FindBySexIsFalse(true), pageable);
		Assert.assertEquals(9 , page.getTotalElements());

		page = jpaBeanComponent.findAll(new FindBySexIsFalse(false), pageable);
		Assert.assertEquals(5 , page.getTotalElements());
		
		jpaBeanComponent.resetAll();
	}
	
	public @NoArgsConstructor class FindBy{
	}
	
	public @NoArgsConstructor @AllArgsConstructor class FindById{
		@Getter @Setter private String id;
	}
	public @NoArgsConstructor @AllArgsConstructor class FindByIdIsNot{
		@Getter @Setter private String id;
	}
	
	public @NoArgsConstructor @AllArgsConstructor class FindByAgeBetween{
		@Getter @Setter private Integer[] age;
	}
	public @NoArgsConstructor @AllArgsConstructor class FindByAgeGreaterThan{
		@Getter @Setter private Integer age;
	}
	public @NoArgsConstructor @AllArgsConstructor class FindByAgeGreaterThanEqual{
		@Getter @Setter private Integer age;
	}
	public @NoArgsConstructor @AllArgsConstructor class FindByAgeLessThan{
		@Getter @Setter private Integer age;
	}
	public @NoArgsConstructor @AllArgsConstructor class FindByAgeLessThanEqual{
		@Getter @Setter private Integer age;
	}
	public @NoArgsConstructor @AllArgsConstructor class FindByAgeIsNull{
		@Getter @Setter private Integer age;
	}
	public @NoArgsConstructor @AllArgsConstructor class FindByAgeIsNotNull{
		@Getter @Setter private Integer age;
	}
	
	public @NoArgsConstructor @AllArgsConstructor class FindByNameStartingWith{
		@Getter @Setter private String name;
	}
	public @NoArgsConstructor @AllArgsConstructor class FindByNameEndingWith{
		@Getter @Setter private String name;
	}
	public @NoArgsConstructor @AllArgsConstructor class FindByNameContaining{
		@Getter @Setter private String name;
	}
	public @NoArgsConstructor @AllArgsConstructor class FindByNameLike{
		@Getter @Setter private String name;
	}
	public @NoArgsConstructor @AllArgsConstructor class FindByNameNotLike{
		@Getter @Setter private String name;
	}
	
	public @NoArgsConstructor @AllArgsConstructor class FindBySexIsTrue{
		@Getter @Setter private Boolean sex;
	}
	public @NoArgsConstructor @AllArgsConstructor class FindBySexIsFalse{
		@Getter @Setter private Boolean sex;
	}
}