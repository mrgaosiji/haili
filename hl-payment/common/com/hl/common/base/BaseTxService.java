package com.hl.common.base;


import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.hl.common.base.IBaseTxService;
import com.hl.common.base.QueryAdapter;
import com.hl.common.model.PageData;



public class BaseTxService implements IBaseTxService {

	@Resource
	protected JdbcTemplate jdbcTemplate;

	@Resource
	protected HibernateTemplate hibernateTemplate;
	
	public static String getQueryCountSql(String sql) {
		int fromIndex = sql.toLowerCase().lastIndexOf("from");
		int orderIndex = sql.toLowerCase().indexOf("order by");
		if (orderIndex != -1) {
			sql = sql.substring(0, orderIndex);
		}
		return "select count(*) " + sql.substring(fromIndex);
	}
	
	public <T> List<T> queryList(final String hql, final QueryParamsSetter ps){
		return queryList(hql, ps, null);
	}
	
	@SuppressWarnings("unchecked")
    public <T> List<T> queryList(final String hql, final QueryParamsSetter ps,final ListCallback<T> cb){
		return this.hibernateTemplate.executeFind(new HibernateCallback<List<T>>() {

			@Override
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if (ps != null) {
					ps.config(query);
				}
				List<T> data=query.list();
				if(cb!=null){
					cb.doCall(session, data);
				}
				return data;
			}
		});
	}

	public PageData queryPageData(final String hql, final QueryParamsSetter ps,
			final int start, final int limit) {
		return queryPageData(hql,getQueryCountSql(hql), ps, start, limit, null);
	}
	
	public PageData queryPageData(final String hql,final String countSql, final QueryParamsSetter ps,
			final int start, final int limit) {
		return queryPageData(hql,countSql, ps, start, limit, null);
	}
	
	public PageData queryPageData(final String hql, final QueryParamsSetter ps,
			final int start, final int limit, final Callback cb) {
		return queryPageData(hql, getQueryCountSql(hql), ps, start, limit, cb);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PageData queryPageData(final String hql,final String countSql, final QueryParamsSetter ps,
			final int start, final int limit, final Callback cb) {
		return (PageData) this.hibernateTemplate
				.execute(new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						PageData pd = new PageData();
						int startRs = start, limitRs = limit;
						if (start < 0) {
							startRs = 0;
							;
						}
						if (limit < 1) {
							limitRs = 20;
						}
						Query query = session.createQuery(hql);
						query.setFirstResult(startRs);
						query.setMaxResults(limitRs);
						if (ps != null) {
							ps.config(query);
						}
						pd.setData(query.list());
						pd.setPageSize(limitRs);
						pd.setPageIndex(startRs / limitRs + 1);
						query = session.createQuery(countSql);
						if (ps != null) {
							ps.config(query);
						}
						pd.setTotal(((Long) query.uniqueResult()).intValue());
						if (pd != null && cb != null) {
							cb.doCall(session, pd);
						}
						return pd;
					}
				});
	}

	public PageData queryPageData(final String hql, final QueryParamsSetter ps,
			final PageData pd) {
		return queryPageData(hql, ps, pd, null);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PageData queryPageData(final String hql, final QueryParamsSetter qps,
			final PageData pd,final Callback cb) {

		hibernateTemplate.execute(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				int idx = pd.getPageIndex();
				int ps = pd.getPageSize();
				if (idx <= 0) {
					idx = 1;
				}
				if (ps <= 0) {
					ps = 10;
				}
				int start = (idx - 1) * ps;
				Query query = session.createQuery(hql);
				query.setFirstResult(start);
				query.setMaxResults(ps);
				if (qps != null) {
					qps.config(query);
				}
				pd.setData(query.list());

				pd.setPageSize(ps);
				pd.setPageIndex(idx);
				String chql = getQueryCountSql(hql);
				query = session.createQuery(chql);
				if (qps != null) {
					qps.config(query);
				}
				pd.setTotal(((Long) query.uniqueResult()).intValue());
				if (pd != null && cb != null) {
					cb.doCall(session, pd);
				}
				return pd;
			}
		});

		return pd;
	}

	public static final Query queryAdapter = new QueryAdapter();

	public static interface QueryParamsSetter {
		void config(Query q);
	}

	public static interface Callback {
		void doCall(Session s, PageData pd);
	}
	
	public static interface ListCallback<T> {
		void  doCall(Session s, List<T> data);
	}

	public Serializable saveEntity(Object entity) {
		return hibernateTemplate.save(entity);
	}

	public void updateEntity(Object entity) {
		hibernateTemplate.update(entity);
	}

	public void deleteEntity(Object entity) {
		hibernateTemplate.delete(entity);
	}

	public Object loadEntity(Class<?> clz, Serializable id) {
		return hibernateTemplate.get(clz, id);
	}
}
