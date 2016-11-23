package com.hl.common.web;

import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.servlet.view.AbstractView;

public class JsonTextView extends AbstractView {

	private static final Log log = LogFactory.getLog(JsonTextView.class);

	public static final String KEY_BEAN_DATA = "MsgRespView.KEY_BEAN_DATA";

	protected String characterEncoding = "UTF-8";

	private static ObjectMapper jsonObj = new ObjectMapper();
	static{
		jsonObj.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm"));
	}

	public static final String TYPE_REQ_JSON = "json";

	public static final String TYPE_REQ_XML = "xml";

	public JsonTextView() {
		super();
		setContentType("text/plain; charset=UTF-8");
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		
		response.reset();
		response.setContentType(getContentType());
		response.setCharacterEncoding(characterEncoding);
		
		//
		Object bean = model.get(KEY_BEAN_DATA);
		if (bean == null) {
			bean = request.getAttribute(KEY_BEAN_DATA);
		}
		if (bean == null) {
			bean = model;
		}
		String type = this.determineReqType(request, model);
		if (TYPE_REQ_JSON.equalsIgnoreCase(type)) {
			jsonObj.writeValue(response.getWriter(), bean);
		} else if (TYPE_REQ_XML.equalsIgnoreCase(type)) {
			log.error("The 'xml' type for response has not been implemented!");
			throw new RuntimeException("The 'xml' type for response has not been implemented!");
		} else {
			log.error("It is not support type of "+type+" for response !");
			throw new RuntimeException(
					"It is not support type of "+type+" for response !");
		}
	}

	/**
	 * 分析期望请求返回JSON数据还是XML数据
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	private String determineReqType(HttpServletRequest request,
			Map<String, Object> model) {

		return TYPE_REQ_JSON;
	}
}
