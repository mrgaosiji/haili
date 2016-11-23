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
 * The persistent class for the MM_OPERATION_LOG database table.
 * 
 */
@Entity
@Table(name="MM_OPERATION_LOG")
public class MmOperationLog extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id",unique=true, nullable=false, length=32)
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String ids;

	@Column(name="CLIENT_DESC", length=256)
	private String clientDesc;

	@Column(name="FUNC_CODE", nullable=false, length=256)
	private String funcCode;

	@Column(name="NEW_VALUE", length=256)
	private String newValue;

	@Column(name="OLD_VALUE", length=256)
	private String oldValue;

	@Column(name="OP_DESC", nullable=false)
	private String opDesc;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="OP_TIME", nullable=false)
	private Date opTime;

	@Column(name="OP_TYPE", length=1)
	private String opType;

	@Column(name="REF_TABLE", length=128)
	private String refTable;

	private String remark;

	@Column(name="SRC_IP", nullable=false, length=64)
	private String srcIp;

	@Column(name="USER_ID", nullable=false, length=32)
	private String userId;

    public MmOperationLog() {
    }

	public String getId() {
		return this.ids;
	}

	public void setId(String id) {
		this.ids = id;
	}

	public String getClientDesc() {
		return this.clientDesc;
	}

	public void setClientDesc(String clientDesc) {
		this.clientDesc = clientDesc;
	}

	public String getFuncCode() {
		return this.funcCode;
	}

	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}

	public String getNewValue() {
		return this.newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public String getOldValue() {
		return this.oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getOpDesc() {
		return this.opDesc;
	}

	public void setOpDesc(String opDesc) {
		this.opDesc = opDesc;
	}

	public Date getOpTime() {
		return this.opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	public String getOpType() {
		return this.opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getRefTable() {
		return this.refTable;
	}

	public void setRefTable(String refTable) {
		this.refTable = refTable;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSrcIp() {
		return this.srcIp;
	}

	public void setSrcIp(String srcIp) {
		this.srcIp = srcIp;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}