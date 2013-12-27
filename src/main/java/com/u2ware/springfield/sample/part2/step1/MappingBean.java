package com.u2ware.springfield.sample.part2.step1;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.joda.time.DateTime;

import com.u2ware.springfield.config.Springfield;
import com.u2ware.springfield.view.multipart.DownloadBean;

@Springfield(
	methodLevelMapping={
		"*.html",
		"find.json","read.json",
		"find.xml","read.xml",
		"find.xls","read.xls",
		"find.csv","read.csv"
	}
)
@Entity
public @ToString @NoArgsConstructor @AllArgsConstructor class MappingBean implements DownloadBean{

	@Id
	@Getter @Setter private String id;
	@Getter @Setter private String name;
	@Getter @Setter private Integer age;
	@Getter @Setter private Boolean sex;
	
	@Override
	public String getContentName() {
		return new DateTime().toString();
	}
}
