package com.u2ware.springfield.sample.part5.step1;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.u2ware.springfield.sample.part4.step1.TargetEntity;
import com.u2ware.springfield.validation.EntityValidatorImpl;


@Component
public class CheckBeanValidator extends EntityValidatorImpl<TargetEntity, CheckBean>{

	@Override
	public void create(TargetEntity target, Errors errors) {
		super.create(target, errors); //JSR-303
		logger.debug("Overide create ");
		ValidationUtils.rejectIfEmpty(errors, "password", "errorCode");
	}
}