package com.hl.web.service;


import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import com.hl.common.base.BaseTxService;
import com.hl.common.base.BaseTxService.Callback;
import com.hl.common.base.BaseTxService.QueryParamsSetter;
import com.hl.common.model.PageData;
import com.hl.web.util.MmToolkit;


@Service("placeService")
public class PlaceFileService extends BaseTxService{
	/**
	 * 
	 * @Title: queryOplog 
	 * @param @return    设定文件 
	 * @return PageData    返回类型 
	 * @throws 
	 * @date Apr 25, 2012 1:44:08 PM
	 */
	public PageData queryPlaceFileApply(int pageIndex, int pageSize){
		final StringBuilder sb = new StringBuilder();
		sb.append(" from ApplyInfo u  where 1=1 ");
		QueryParamsSetter qs = new QueryParamsSetter() {
			@Override
			public void config(Query q) {}
		};
		qs.config(queryAdapter);// 为了生成sql
		//sb.append(" order by u.opTime desc");
        String hql=sb.toString();
		return queryPageData(hql, qs, new PageData(pageIndex,pageSize),new Callback()
		{
			@Override
			public void doCall(Session s, PageData pd) 
			{
				
			}});
	}
	
	/**
	 * 
	 * @Title: queryOplog 
	 * @param @return    设定文件 
	 * @return PageData    返回类型 
	 * @throws 
	 * @date Apr 25, 2012 1:44:08 PM
	 */
	public PageData queryApplyInfo(final String firstdate ,final String enddate ,final String table_name,int pageIndex, int pageSize){
		final StringBuilder sb = new StringBuilder();
		sb.append(" from ApplyInfo u  where 1=1 ");
		QueryParamsSetter qs = new QueryParamsSetter() {
			@Override
			public void config(Query q) {
				if (StringUtils.hasText(firstdate)) {
					sb.append(" and convert(u.bz1,date)>=:bz1 ");
					q.setDate("bz1", MmToolkit.getDateTimeStart(firstdate));
				} 
				if (StringUtils.hasText(enddate)) {
					sb.append(" and to_date(u.bz1.date) <=:to_date(bz1,date)");
					q.setTimestamp("to_date(bz1,date)", MmToolkit.getDateTimeEnd(enddate));
				} 
				if (StringUtils.hasText(table_name)) {
					sb.append(" and u.tableName like :tableName");
					q.setString("tableName", "%"+table_name+"%");
				} 
				
			}
		};
		qs.config(queryAdapter);// 为了生成sql
		sb.append(" order by u.tableName asc");
        String hql=sb.toString();
		return queryPageData(hql, qs, new PageData(pageIndex,pageSize),new Callback(){
			@Override
			public void doCall(Session s, PageData pd) {
				
			}});
	}
	
}
