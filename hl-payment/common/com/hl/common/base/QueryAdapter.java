package com.hl.common.base;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.Type;

/**配合QueryParamsSetter 生成sql语句，不再用判断空�?
 * @see BaseTxService.QueryParamsSetter
 * 
 *
 */
public class QueryAdapter implements Query {

	@Override
	public int executeUpdate() throws HibernateException {

		return 0;
	}

	@Override
	public String[] getNamedParameters() throws HibernateException {

		return null;
	}

	@Override
	public String getQueryString() {

		return null;
	}

	@Override
	public String[] getReturnAliases() throws HibernateException {

		return null;
	}

	@Override
	public Type[] getReturnTypes() throws HibernateException {

		return null;
	}

	@Override
	public Iterator<?> iterate() throws HibernateException {

		return null;
	}

	@Override
	public List<?> list() throws HibernateException {

		return null;
	}

	@Override
	public ScrollableResults scroll() throws HibernateException {

		return null;
	}

	@Override
	public ScrollableResults scroll(ScrollMode arg0) throws HibernateException {

		return null;
	}

	@Override
	public Query setBigDecimal(int arg0, BigDecimal arg1) {

		return null;
	}

	@Override
	public Query setBigDecimal(String arg0, BigDecimal arg1) {

		return null;
	}

	@Override
	public Query setBigInteger(int arg0, BigInteger arg1) {

		return null;
	}

	@Override
	public Query setBigInteger(String arg0, BigInteger arg1) {

		return null;
	}

	@Override
	public Query setBinary(int arg0, byte[] arg1) {

		return null;
	}

	@Override
	public Query setBinary(String arg0, byte[] arg1) {

		return null;
	}

	@Override
	public Query setBoolean(int arg0, boolean arg1) {

		return null;
	}

	@Override
	public Query setBoolean(String arg0, boolean arg1) {

		return null;
	}

	@Override
	public Query setByte(int arg0, byte arg1) {

		return null;
	}

	@Override
	public Query setByte(String arg0, byte arg1) {

		return null;
	}

	@Override
	public Query setCacheMode(CacheMode arg0) {

		return null;
	}

	@Override
	public Query setCacheRegion(String arg0) {

		return null;
	}

	@Override
	public Query setCacheable(boolean arg0) {

		return null;
	}

	@Override
	public Query setCalendar(int arg0, Calendar arg1) {

		return null;
	}

	@Override
	public Query setCalendar(String arg0, Calendar arg1) {

		return null;
	}

	@Override
	public Query setCalendarDate(int arg0, Calendar arg1) {

		return null;
	}

	@Override
	public Query setCalendarDate(String arg0, Calendar arg1) {

		return null;
	}

	@Override
	public Query setCharacter(int arg0, char arg1) {

		return null;
	}

	@Override
	public Query setCharacter(String arg0, char arg1) {

		return null;
	}

	@Override
	public Query setComment(String arg0) {

		return null;
	}

	@Override
	public Query setDate(int arg0, Date arg1) {

		return null;
	}

	@Override
	public Query setDate(String arg0, Date arg1) {

		return null;
	}

	@Override
	public Query setDouble(int arg0, double arg1) {

		return null;
	}

	@Override
	public Query setDouble(String arg0, double arg1) {

		return null;
	}

	@Override
	public Query setEntity(int arg0, Object arg1) {

		return null;
	}

	@Override
	public Query setEntity(String arg0, Object arg1) {

		return null;
	}

	@Override
	public Query setFetchSize(int arg0) {

		return null;
	}

	@Override
	public Query setFirstResult(int arg0) {

		return null;
	}

	@Override
	public Query setFloat(int arg0, float arg1) {

		return null;
	}

	@Override
	public Query setFloat(String arg0, float arg1) {

		return null;
	}

	@Override
	public Query setFlushMode(FlushMode arg0) {

		return null;
	}

	@Override
	public Query setInteger(int arg0, int arg1) {

		return null;
	}

	@Override
	public Query setInteger(String arg0, int arg1) {

		return null;
	}

	@Override
	public Query setLocale(int arg0, Locale arg1) {

		return null;
	}

	@Override
	public Query setLocale(String arg0, Locale arg1) {

		return null;
	}

	@Override
	public Query setLockMode(String arg0, LockMode arg1) {

		return null;
	}

	@Override
	public Query setLong(int arg0, long arg1) {

		return null;
	}

	@Override
	public Query setLong(String arg0, long arg1) {

		return null;
	}

	@Override
	public Query setMaxResults(int arg0) {

		return null;
	}

	@Override
	public Query setParameter(int arg0, Object arg1) throws HibernateException {

		return null;
	}

	@Override
	public Query setParameter(String arg0, Object arg1)
			throws HibernateException {

		return null;
	}

	@Override
	public Query setParameter(int arg0, Object arg1, Type arg2) {

		return null;
	}

	@Override
	public Query setParameter(String arg0, Object arg1, Type arg2) {

		return null;
	}

	@Override
	public Query setParameterList(String arg0, @SuppressWarnings("rawtypes") Collection arg1)
			throws HibernateException {

		return null;
	}

	@Override
	public Query setParameterList(String arg0, Object[] arg1)
			throws HibernateException {

		return null;
	}

	@Override
	public Query setParameterList(String arg0, @SuppressWarnings("rawtypes") Collection arg1, Type arg2)
			throws HibernateException {

		return null;
	}

	@Override
	public Query setParameterList(String arg0, Object[] arg1, Type arg2)
			throws HibernateException {

		return null;
	}

	@Override
	public Query setParameters(Object[] arg0, Type[] arg1)
			throws HibernateException {

		return null;
	}

	@Override
	public Query setProperties(Object arg0) throws HibernateException {

		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Query setProperties(Map arg0) throws HibernateException {

		return null;
	}

	@Override
	public Query setReadOnly(boolean arg0) {

		return null;
	}

	@Override
	public Query setResultTransformer(ResultTransformer arg0) {

		return null;
	}

	@Override
	public Query setSerializable(int arg0, Serializable arg1) {

		return null;
	}

	@Override
	public Query setSerializable(String arg0, Serializable arg1) {

		return null;
	}

	@Override
	public Query setShort(int arg0, short arg1) {

		return null;
	}

	@Override
	public Query setShort(String arg0, short arg1) {

		return null;
	}

	@Override
	public Query setString(int arg0, String arg1) {

		return null;
	}

	@Override
	public Query setString(String arg0, String arg1) {

		return null;
	}

	@Override
	public Query setText(int arg0, String arg1) {

		return null;
	}

	@Override
	public Query setText(String arg0, String arg1) {

		return null;
	}

	@Override
	public Query setTime(int arg0, Date arg1) {

		return null;
	}

	@Override
	public Query setTime(String arg0, Date arg1) {

		return null;
	}

	@Override
	public Query setTimeout(int arg0) {

		return null;
	}

	@Override
	public Query setTimestamp(int arg0, Date arg1) {

		return null;
	}

	@Override
	public Query setTimestamp(String arg0, Date arg1) {

		return null;
	}

	@Override
	public Object uniqueResult() throws HibernateException {

		return null;
	}

}
