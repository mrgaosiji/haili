package com.hl.web.form;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class CustomerTotalPayInfoForm implements Serializable{

	private List<CustomerForm> customer;

	private List<HistoryAbDetailForm> historyAbstractDetail;
	
	private List<HistoryPayInfoForm> historyOne;
	
	private List<HistoryPayInfoForm> historyThree;
	
	private List<PayInfoForm>  payInfo;
	
	private String companyName;
	
	private String companyCode;
	
	private String companyMerchantNumber;
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyMerchantNumber() {
		return companyMerchantNumber;
	}

	public void setCompanyMerchantNumber(String companyMerchantNumber) {
		this.companyMerchantNumber = companyMerchantNumber;
	}

	public List<CustomerForm> getCustomer() {
		return customer;
	}

	public void setCustomer(List<CustomerForm> customer) {
		this.customer = customer;
	}

	public List<HistoryAbDetailForm> getHistoryAbstractDetail() {
		return historyAbstractDetail;
	}

	public void setHistoryAbstractDetail(
			List<HistoryAbDetailForm> historyAbstractDetail) {
		this.historyAbstractDetail = historyAbstractDetail;
	}

	public List<HistoryPayInfoForm> getHistoryOne() {
		return historyOne;
	}

	public void setHistoryOne(List<HistoryPayInfoForm> historyOne) {
		this.historyOne = historyOne;
	}

	public List<HistoryPayInfoForm> getHistoryThree() {
		return historyThree;
	}

	public void setHistoryThree(List<HistoryPayInfoForm> historyThree) {
		this.historyThree = historyThree;
	}

	public List<PayInfoForm> getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(List<PayInfoForm> payInfo) {
		this.payInfo = payInfo;
	}
	
	

	
	
	
	
	
}
