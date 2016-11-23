package com.hl.common.model;

import java.util.List;

public class PageData extends RespInfo {

	/**
	 * 第几页
	 */
	private int pageIndex;

	/**
	 * 每页条数
	 */
	private int pageSize;

	/**
	 * 总条数
	 */
	private int total;

	/*
	 * 单页数据
	 */
	// private List<?> data;

	public int getPageIndex() {
		return pageIndex;
	}

	public PageData() {
		super();
	}

	public PageData(int pageIndex, int pageSize) {
		super();
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<?> getData() {
		return (List<?>) data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
