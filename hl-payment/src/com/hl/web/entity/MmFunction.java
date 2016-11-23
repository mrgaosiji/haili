package com.hl.web.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.hl.common.model.BaseBean;



/**
 * The persistent class for the MM_FUNCTION database table.
 * 
 */
@Entity
@Table(name="MM_FUNCTION")
public class MmFunction extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false, length=32)
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	@Column(name="PARENT_ID", length=32,nullable=true)
	private String parentId;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="ADD_TIME", nullable=false)
	private Date addTime;

	@Column(name="FUNC_CODE", nullable=true, length=256)
	private String funcCode;

	@Column(name="ICON_CLS", length=16)
	private String iconCls;

	@Column(nullable=false)
	private String name;

	@Column(name="ORDER_NUM", precision=6)
	private Long orderNum;

	private String remark;

	@Column(nullable=false, precision=2)
	private Long type;

    public MmFunction() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getAddTime() {
		return this.addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getFuncCode() {
		return this.funcCode;
	}

	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}

	public String getIconCls() {
		return this.iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getType() {
		return this.type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		if(parentId!=null&&parentId.trim().length()==0){
			parentId=null;
		}
		this.parentId = parentId;
	}

}