package com.hl.common.spring;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class HsqldbWebDriverManagerDataSource extends DriverManagerDataSource {

	@Override
	public void setUrl(String url) {
		//jdbc:hsqldb:file:WEB-INF/data/demodb
//		if(url!=null){
//			int idx=url.lastIndexOf(":");
//			if(idx!=-1){
//				String file=url.substring(idx+1);
//				String path=getClass().getResource("/").getFile();
//				url=url.substring(0, idx+1)+path+"../../"+file;
//				System.out.println("dburl:"+url);
//			}
//		}
		super.setUrl(url);
	}

	
}
