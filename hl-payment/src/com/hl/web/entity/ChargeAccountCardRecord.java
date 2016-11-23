package com.hl.web.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.hl.common.model.BaseBean;


/**
 * ChargeAccountCardRecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="charge_account_card_record")
public class ChargeAccountCardRecord  extends BaseBean implements java.io.Serializable {

    // Constructors
	private String closeBillId;
    private ProductionOrdering productionOrdering;
    private Double chargeFee;
    private Integer status;
    private String createTime;
    private String bz;
    private String address;
    private String meter_number;
	

    /** default constructor */
    public ChargeAccountCardRecord() {
    }

	/** minimal constructor */
    public ChargeAccountCardRecord(String closeBillId, String ls, Double chargeFee, Integer status) {
    	this.closeBillId = closeBillId;
        this.chargeFee = chargeFee;
        this.status = status;     
    }
    
    /** full constructor */
    public ChargeAccountCardRecord(String closeBillId, ProductionOrdering productionOrdering, String ls, Double chargeFee, Integer status, String createTime, String bz) {
    	 this.closeBillId = closeBillId;
         this.productionOrdering = productionOrdering;
         this.chargeFee = chargeFee;
         this.status = status;
         this.createTime = createTime;
         this.bz = bz;   
    }
    
    @Id 
    @Column(name="CloseBill_ID", unique=true, nullable=false, length=40)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
     public String getCloseBillId() {
        return this.closeBillId;
    }
    
    public void setCloseBillId(String closeBillId) {
        this.closeBillId = closeBillId;
    }
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ORDER_ID")
    public ProductionOrdering getProductionOrdering() {
        return this.productionOrdering;
    }
    
    public void setProductionOrdering(ProductionOrdering productionOrdering) {
        this.productionOrdering = productionOrdering;
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
    
    @Column(name="CREATE_TIME", length=40)
    public String getCreateTime() {
        return this.createTime;
    }
    
    @Column(name="METER_NUMBER", length=50)
    public String getMeter_number() {
		return meter_number;
	}
    
    @Column(name="ADDRESS", length=150)
    public String getAddress() {
		return address;
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
