package com.hl.web.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import com.hl.common.base.BaseTxService;
import com.hl.common.model.PageData;
import com.hl.common.spring.PropertyCfg;
import com.hl.web.entity.MmFunction;
import com.hl.web.entity.MmRole;
import com.hl.web.entity.MmUser;
import com.hl.web.form.FunctionForm;
import com.hl.web.util.MmToolkit;


/**
 * MMUser操作相关处理
 * 
 * @author 
 * 
 */
@Service("userService")
public class UserService extends BaseTxService {
	
	private static final String MD5_PWD_KEY="mms_md5";
	
	@PropertyCfg("user.create.defaultpassword")
	private String defaultPassword;
	
	public String getDefaultPassword() {
		return defaultPassword;
	}

	public String getMd5Str(String pwd){
		Md5PasswordEncoder encoder=new Md5PasswordEncoder();
		encoder.setEncodeHashAsBase64(false);
		return encoder.encodePassword(pwd, MD5_PWD_KEY);
	}
	

	
	/**
	 * 按用户名（用户ID）查询用户，主要登录用
	 * @param userName
	 * @return
	 */
	public  MmUser loadUser(final String userName){
		if(StringUtils.hasText(userName)){
			return hibernateTemplate.execute(new HibernateCallback<MmUser>() {
				@Override
				public MmUser doInHibernate(Session s)
						throws HibernateException, SQLException {
					MmUser u=(MmUser) s.get(MmUser.class, userName);
					
					if(u!=null){
						Hibernate.initialize(u.getRoles());
						if(u.getRoles()!=null){
							for(MmRole r:u.getRoles()){
								Hibernate.initialize(r.getFunctions());
							}
						}
					}
					return u;
				}
			});
		}

		return null;
	}
/*****************************************************/
	public MmRole loadMmRole(final String id){
		return this.hibernateTemplate.execute(new HibernateCallback<MmRole>(){
			@Override
			public MmRole doInHibernate(Session s)
					throws HibernateException, SQLException {
				MmRole temp = (MmRole)s.get(MmRole.class, id);
				Hibernate.initialize( temp.getFunctions());
				return temp;
			}
			
		});
	}
	@SuppressWarnings("unchecked")
	public List<MmRole> queryRole() {
		List<MmRole> roles= hibernateTemplate.find("from MmRole order by addTime");
		for(MmRole r:roles ){
			r.setFunctions(null);
		}
		return roles;
	}
	@SuppressWarnings("unchecked")
	public List<MmFunction> queryAllFuncs() {
		List<MmFunction> list= hibernateTemplate.find("from MmFunction order by addTime");
		return list;
	}
	/**
	 * 
	 * @Title: getMmRoleByName 
	 * @param @param name
	 * @param @return    设定文件 
	 * @return MmRole    返回类型 
	 * @throws 
	 * @date Apr 23, 2012 3:07:11 PM
	 */
	@SuppressWarnings("unchecked")
	public MmRole getMmRoleByName(String name){
		List<MmRole> list= hibernateTemplate.find("from MmRole where name='"+name+"'");
		if(list!= null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 
	 * @Title: queryFunc 
	 * @param @param pageIndex
	 * @param @param pageSize
	 * @param @return    设定文件 
	 * @return PageData    返回类型 
	 * @throws 
	 * @date Apr 24, 2012 1:46:31 PM
	 */
	public PageData queryFunc(final String parentId,final String name,int pageIndex,int pageSize){
		
		final StringBuilder sb = new StringBuilder();
		sb.append(" from MmFunction u where 1=1 ");
		QueryParamsSetter qs = new QueryParamsSetter() {
			@Override
			public void config(Query q) {
				if (StringUtils.hasText(parentId)) {
					sb.append(" and u.parentId=:parentId");
					q.setString("parentId", parentId);
				}
				if(StringUtils.hasText(name)){
					sb.append(" and u.name like :name ");
					q.setString("name", "%"+name+"%");
				}
			}
		};
		qs.config(queryAdapter);// 为了生成sql
		sb.append(" order by u.addTime desc");
        String hql=sb.toString();
        final UserService us = this;
		return queryPageData(hql, qs, new PageData(pageIndex,pageSize),new Callback(){
			@Override
			public void doCall(Session s, PageData pd) {
				if(pd.getData()!=null){
					List <FunctionForm> data = new ArrayList <FunctionForm>();
					for(Object o:pd.getData()){
						MmFunction func = (MmFunction)o;
						String parentId = func.getParentId(); 
						String parentName = "";
						if(parentId != null && !"".equals(parentId)){
							MmFunction tempP =(MmFunction) us.loadEntity(MmFunction.class, parentId);
							if(tempP!=null)
							   parentName = tempP.getName();
						}
						FunctionForm temp = new FunctionForm();
						temp.setFuncCode(func.getFuncCode());
						temp.setId(func.getId());
						temp.setName(func.getName());
						temp.setOrderNum(func.getOrderNum());
						temp.setParentName(parentName);
						temp.setRemark(func.getRemark());
						temp.setAddTime(func.getAddTime());
						temp.setType(func.getType());
						temp.setIconCls(func.getIconCls());
						data.add(temp);
					}
					pd.setData(data);
				}
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
	public PageData queryOplog(final String funcCode,final String srcIp,final String userId,
			final String opTime,int pageIndex, int pageSize){
		final StringBuilder sb = new StringBuilder();
		sb.append(" from MmOperationLog u  where 1=1 ");
		QueryParamsSetter qs = new QueryParamsSetter() {
			@Override
			public void config(Query q) {
				if (StringUtils.hasText(funcCode)) {
					sb.append(" and u.funcCode like:funcCode");
					q.setString("funcCode", "%"+funcCode+"%");
				} 
				if (StringUtils.hasText(srcIp)) {
					sb.append(" and u.srcIp like :srcIp");
					q.setString("srcIp", "%"+srcIp+"%");
				} 
				if (StringUtils.hasText(userId)) {
					sb.append(" and u.userId=:userId");
					q.setString("userId", userId);
				} 
				if (StringUtils.hasText(opTime)) {
					sb.append(" and u.opTime >=:opTimes");
					q.setTimestamp("opTimes", MmToolkit.getDateTimeStart(opTime));
				} 
				if (StringUtils.hasText(opTime)) {
					sb.append(" and u.opTime <=:opTimee");
					q.setTimestamp("opTimee", MmToolkit.getDateTimeEnd(opTime));
				} 
			}
		};
		qs.config(queryAdapter);// 为了生成sql
		sb.append(" order by u.opTime desc");
        String hql=sb.toString();
		return queryPageData(hql, qs, new PageData(pageIndex,pageSize),new Callback(){
			@Override
			public void doCall(Session s, PageData pd) {
				
			}});
	}

/*****************************************************/
	/**
	 * 分页查找用户
	 * @param userName
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageData queryUser(final String userName,int pageIndex,int pageSize) {
		final StringBuilder sb = new StringBuilder();
		sb.append(" from MmUser u where 1=1 ");
		
		QueryParamsSetter qs = new QueryParamsSetter() {
			@Override
			public void config(Query q) {
				if (StringUtils.hasText(userName)) {
					sb.append(" and u.userId like ? ");
					q.setString(0, "%"+userName+"%");
				} else {

				}
			}
		};
		qs.config(queryAdapter);// 为了生成sql
		sb.append(" order by u.addTime desc ");
        String hql=sb.toString();
		return queryPageData(hql, qs, new PageData(pageIndex,pageSize),new Callback(){

			@Override
			public void doCall(Session s, PageData pd) {
				//处理延迟加载对象，需要的加载，不需要的处置为空，以免生成Json字符串时或其他调用时报错
				if(pd.getData()!=null){
					for(Object o:pd.getData()){
						MmUser u=(MmUser) o;
						Hibernate.initialize(u.getRoles());
//						if(u.getRoles()!=null){
//							for(MmRole r:u.getRoles()){
//								r.setFunctions(null);
//							}
//						}
					}
				}
			}});
	}
	
	@SuppressWarnings("unchecked")
	public List<MmRole> queryAllRoles(){
		List<MmRole> roles= hibernateTemplate.find("from MmRole order by name");
		for(MmRole r:roles ){
			r.setFunctions(null);
		}
		return roles;
	}
	
	public void deleteFunction(MmFunction func){
		jdbcTemplate.execute("DELETE FROM MM_ROLE_FUNC_REL WHERE FUNCTION_ID='"+func.getId()+"'");
		deleteEntity(func);
	}
	
	public void deleteRole(MmRole role){
		jdbcTemplate.execute("DELETE FROM MM_ROLE_FUNC_REL WHERE ROLE_ID='"+role.getId()+"'");
		jdbcTemplate.execute("DELETE FROM MM_USER_ROLE_REL WHERE ROLE_ID='"+role.getId()+"'");
		deleteEntity(role);
	}
}
