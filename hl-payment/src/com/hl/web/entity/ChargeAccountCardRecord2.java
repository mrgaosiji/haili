package com.hl.web.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Table;


import com.hl.common.model.BaseBean;


/**
 * ChargeAccountCardRecord2 entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="charge_account_card_record2")
public class ChargeAccountCardRecord2 extends BaseBean implements java.io.Serializable {

    // Constructors
	
	private String closeBillId;
    private Double chargeFee;
    private Integer status;
    private String orderId;
    private String createTime;
    private String bz;
    private String meter_number;
    private String address;

    /** default constructor */
    public ChargeAccountCardRecord2() {
    }

	/** minimal constructor */
    public ChargeAccountCardRecord2(String closeBillId, String ls, Double chargeFee, Integer status) {
    	 this.closeBillId = closeBillId;
         this.chargeFee = chargeFee;
         this.status = status;     
    }
    
    /** full constructor */
    public ChargeAccountCardRecord2(String closeBillId, String ls, Double chargeFee, Integer status, String orderId, String createTime, String bz) {
    	 this.closeBillId = closeBillId;
         this.chargeFee = chargeFee;
         this.status = status;
         this.orderId = orderId;
         this.createTime = createTime;
         this.bz = bz;    
    }
    
    @Id 
    @Column(name="CloseBill_ID", unique=true, nullable=false, length=36)

    public String getCloseBillId() {
        return this.closeBillId;
    }
    
    public void setCloseBillId(String closeBillId) {
        this.closeBillId = closeBillId;
    }
    
    @Column(name="METER_NUMBER", length=50)
    public String getMeter_number() {
		return meter_number;
	}
    
    @Column(name="ADDRESS", length=150)
    public String getAddress() {
		return address;
	}
    
 
    
    
    @Column(name="CHARGE_FEE", nullable=false, precision=18)
    public Double getChargeFee() {
        return this.chargeFee;
    }
    
    public void setChargeFee(Double chargeFee) {
        this.chargeFee = chargeFee;
    }
    
    @Column(name="STATUS", nullable=false)
    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    @Column(name="ORDER_ID", length=36)
    public String getOrderId() {
        return this.orderId;
    }
    
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    @Column(name="CREATE_TIME", length=40)
    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="BZ", length=40)
    public String getBz() {
        return this.bz;
    }
    
    public void setBz(String bz) {
        this.bz = bz;
    }
    
    public void setAddress(String address) {
		this.address = address;
	}

	

	public void setMeter_number(String meter_number) {
		this.meter_number = meter_number;
	}
   
}
