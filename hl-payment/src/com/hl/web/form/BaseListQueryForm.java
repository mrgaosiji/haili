package com.hl.web.form;

import com.hl.common.model.BaseBean;

public class BaseListQueryForm extends BaseBean {

	/**
	 * datagrid组件传过来的第几页
	 */
	protected int page;
	/**
	 * datagrid组件传过来的每页条数
	 */
	protected int rows;
	
	protected String filter;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	
}
