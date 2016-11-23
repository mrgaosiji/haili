package com.hl.common.model;



public class RespInfo extends BaseBean{

	/**
	 * 处理结果码，0：成功，其他为失败	 */
	protected int code=0;

	/**
	 * 返回消息
	 */
	protected String message;

	/**
	 * 返回的数据对象，可能为空
	 */
	protected Object data;

	public RespInfo() {
		super();
	}

	public RespInfo(int code, String message, Object data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public RespInfo setValues(int code, String message, Object data){
		this.code = code;
		this.message = message;
		this.data = data;
		return this;
	}
}
