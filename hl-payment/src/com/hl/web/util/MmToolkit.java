package com.hl.web.util;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.jdom.Element;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.hl.common.web.BaseController;
import com.hl.web.entity.MmOperationLog;
import com.hl.web.entity.MmUser;
import com.hl.web.security.AuthUser;
import com.hl.wxin.common.MD5;

public class MmToolkit {

	public static MmOperationLog createOpLog(HttpServletRequest request) {
		MmOperationLog opLog = new MmOperationLog();
		opLog.setClientDesc(request.getHeader("User-Agent"));
		String url = request.getRequestURI();
		String ctxPath = request.getContextPath();
		if (ctxPath != null && ctxPath.length() > 0) {
			url = url.substring(ctxPath.length());
		}
		if (url.startsWith("/")) {
			url = url.substring(1);
		}
		opLog.setFuncCode(url);
		opLog.setOpTime(new Date());
		opLog.setSrcIp(BaseController.getRemoteAddr(request));
		AuthUser user = (AuthUser) request.getSession().getAttribute(AuthUser.AUTH_USER_SESSION_KEY);
		MmUser mmUser = (MmUser) user.getData();
		opLog.setUserId(mmUser.getUserId());
		return opLog;
	}
	
	public static String formatDateTime(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(format);
		return sdf.format(date);
	}

	public static Date parseDateTimeStr(String dateTimeStr, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(format);
		Date date = null;
		try {
			date = sdf.parse(dateTimeStr);
		} catch (Exception ex) {

		}
		return date;
	}

	public static Date getDateTimeStart(String dateStr) {
		dateStr += " 00:00:00";
		Date date = MmToolkit.parseDateTimeStr(dateStr, "yyyy-MM-dd HH:mm:ss");
		return date;
	}

	public static Date getDateTimeEnd(String dateStr) {
		dateStr += " 23:59:59";
		Date date = MmToolkit.parseDateTimeStr(dateStr, "yyyy-MM-dd HH:mm:ss");
		return date;
	}
	
	/* 
	  * 判断是否为浮点数，包括double和float 
	  * @param str 传入的字符串 
	  * @return 是浮点数返回true,否则返回false 
	*/  
	  public static boolean isDouble(String str) {  
	    Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");  
	    return pattern.matcher(str).matches();  
	  }
	  
	public static String getUserDir() {
		Properties pro = System.getProperties();
		return pro.getProperty("user.dir");
	}
	
	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map doXMLParse(String strxml) throws JDOMException, IOException {
		strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

		if(null == strxml || "".equals(strxml)) {
			return null;
		}
		
		Map m = new HashMap();
		
		InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while(it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if(children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = MmToolkit.getChildrenText(children);
			}
			
			m.put(k, v);
		}
		
		//关闭流
		in.close();
		
		return m;
	}
	
	/**
	 * 获取子结点的xml
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if(!children.isEmpty()) {
			Iterator it = children.iterator();
			while(it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if(!list.isEmpty()) {
					sb.append(MmToolkit.getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * map转成xml
	 * @param arr
	 * @return
	 */
	public static String ArrayToXml(HashMap<String, String> arr) {
		String xml = "<xml>";
		
		Iterator<Entry<String, String>> iter = arr.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			String val = entry.getValue();
			if (IsNumeric(val)) {
				xml += "<" + key + ">" + val + "</" + key + ">";

			} else
//				xml += "<" + key + "><![CDATA[" + val + "]]></" + key + ">";
				xml += "<" + key + ">" + val + "</" + key + ">";
		}

		xml += "</xml>";
		return xml;
	}
	
	public static boolean IsNumeric(String str) {
		if (str.matches("\\d *")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 是否签名正确,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 * @return boolean
	 */
	public static boolean isTenpaySign(String characterEncoding, SortedMap<Object, Object> packageParams, String API_KEY) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			if(!"sign".equals(k) && null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}
		
		sb.append("key=" + API_KEY);
		
		//算出摘要
		String mysign = MD5.MD5Encode(sb.toString(), characterEncoding).toLowerCase();
		String tenpaySign = ((String)packageParams.get("sign")).toLowerCase();
		
		//System.out.println(tenpaySign + "    " + mysign);
		return tenpaySign.equals(mysign);
	}



}
