package com.hl.web.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToMany;

import com.hl.common.model.BaseBean;


/**
 * ProductionOrdering2 entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="production_ordering2")
public class ProductionOrdering2 extends BaseBean implements java.io.Serializable {

    // Constructors
	private String id;
    private Integer status;
    private Double totalMoney;
    private String bz;
    private String createTime;
    private String payTime;
    private String gasCode;
    private String clientNumber;

    /** default constructor */
    public ProductionOrdering2() {
    }

	/** minimal constructor */
    public ProductionOrdering2(String id, Integer status, Double totalMoney, String createTime, String payTime, String gasCode, String clientNumber) {
    	this.id = id;
        this.status = status;
        this.totalMoney = totalMoney;
        this.createTime = createTime;
        this.payTime = payTime;
        this.gasCode = gasCode;
        this.clientNumber = clientNumber;   
    }
    
    /** full constructor */
    public ProductionOrdering2(String id, Integer status, Double totalMoney, String bz, String createTime, String payTime, String gasCode, String clientNumber) {
    	 this.id = id;
         this.status = status;
         this.totalMoney = totalMoney;
         this.bz = bz;
         this.createTime = createTime;
         this.payTime = payTime;
         this.gasCode = gasCode;
         this.clientNumber = clientNumber;        
    }
    
    @Id 
    @Column(name="ID", unique=true, nullable=false, length=36)

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    @Column(name="STATUS", nullable=false)

    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    @Column(name="TOTAL_MONEY", nullable=false, precision=10)

    public Double getTotalMoney() {
        return this.totalMoney;
    }
    
    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }
    
    @Column(name="BZ", length=50)

    public String getBz() {
        return this.bz;
    }
    
    public void setBz(String bz) {
        this.bz = bz;
    }
    
    @Column(name="CREATE_TIME", nullable=false, length=40)

    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="PAY_TIME", nullable=false)

    public String getPayTime() {
        return this.payTime;
    }
    
    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }
    
    @Column(name="GAS_CODE", nullable=false, length=10)

    public String getGasCode() {
        return this.gasCode;
    }
    
    public void setGasCode(String gasCode) {
        this.gasCode = gasCode;
    }
    
    @Column(name="CLIENT_NUMBER", nullable=false, length=45)
    public String getClientNumber() {
        return this.clientNumber;
    }
    
    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }
   
}
