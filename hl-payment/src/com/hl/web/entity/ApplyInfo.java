package com.hl.web.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.hl.common.model.BaseBean;


@Entity
@Table(name="APPLY_INFO")
public class ApplyInfo extends BaseBean implements Serializable  {
	
	private String id;
	private String systemName;//框架名称	
	private String systemChn;//框架单元中文名
	private String tableName; //表英文名
	private String objectChn; //表中文名
	private String nodeId;    //安全节点号
	private String loadType;  //加载类型
	private String playOnFileType;//归档方式
	private String organizationType;//机构属性 BR0、BR11、BR2、BR38、BR39、BR39+01N、BR41
	private String frequency;//供数频率
	private String applyFirstDate;//数据开始日期
	private String applyLastDate;//数据结束日期
	private String applyStartDate;//数据生效日期
	private String saveCircle;//数据保存月数
	private String fileType;//数据文件类型
	private String addSpace;//增量文件大小
	private String introduction;//说明
	private String status;//状态值
	private String detailFieldName;//明细日期字段名称
	private String allSpace;//主档全量数据大小
	private String sectionRequired;//截面需求
	private String batchNo;//最大批次号
	private String bz1;   //备注字段1
	private String bz2;   //备注字段2
	private String bz3;   //备注字段3
	
	

	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name="APPLY_ID",unique=true,nullable=false,length=64)
	public String getApplyId() {
		return id;
	}
	public void setApplyId(String applyId) {
		this.id = applyId;
	}
	@Column(name="SYSTEM_NAME",length=20)
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	@Column(name="TABLE_NAME",length=20)
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	@Column(name="NODE_ID",length=11)
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	@Column(name="LOAD_TYPE",length=10)
	public String getLoadType() {
		return loadType;
	}
	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}
	@Column(name="APPLY_ON_FILE_TYPE",length=1)
	public String getPlayOnFileType() {
		return playOnFileType;
	}
	public void setPlayOnFileType(String playOnFileType) {
		this.playOnFileType = playOnFileType;
	}
	@Column(name="ORGANIZATION_TYPE",length=15)
	public String getOrganizationType() {
		return organizationType;
	}
	public void setOrganizationType(String organizationType) {
		this.organizationType = organizationType;
	}
	@Column(name="FREQUENCY",length=1)
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	@Column(name="SYSTEMCHN",length=50)
	public String getSystemChn() {
		return systemChn;
	}
	public void setSystemChn(String systemChn) {
		this.systemChn = systemChn;
	}
	
	@Column(name="OBJECTCHN",length=50)
	public String getObjectChn() {
		return objectChn;
	}
	public void setObjectChn(String objectChn) {
		this.objectChn = objectChn;
	}
	
	@Column(name="APPLY_FIRSTDATE",length=20)
	public String getApplyFirstDate() {
		return applyFirstDate;
	}
	public void setApplyFirstDate(String applyFirstDate) {
		this.applyFirstDate = applyFirstDate;
	}
	
	@Column(name="APPLY_LASTDATE",length=20)
	public String getApplyLastDate() {
		return applyLastDate;
	}
	
	public void setApplyLastDate(String applyLastDate) {
		this.applyLastDate = applyLastDate;
	}
	
	@Column(name="APPLY_STARTDATEDATE",length=20)
	public String getApplyStartDate() {
		return applyStartDate;
	}
	public void setApplyStartDate(String applyStartDate) {
		this.applyStartDate = applyStartDate;
	}
	
	@Column(name="SAVE_CIROLE",length=10)
	public String getSaveCircle() {
		return saveCircle;
	}
	public void setSaveCircle(String saveCircle) {
		this.saveCircle = saveCircle;
	}
	@Column(name="FILE_TYPE",length=20)
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	@Column(name="ADD_SPACE",length=128)
	public String getAddSpace() {
		return addSpace;
	}
	public void setAddSpace(String addSpace) {
		this.addSpace = addSpace;
	}
	@Column(name="APPLY_EXPLAIN",length=128)
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	@Column(name="STATUS",length=1)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name="DETAILDATADATE",length=100)
	public String getDetailFieldName() {
		return detailFieldName;
	}
	public void setDetailFieldName(String detailFieldName) {
		this.detailFieldName = detailFieldName;
	}
	
	@Column(name="ALL_SPACE",length=50)
	public String getAllSpace() {
		return allSpace;
	}
	public void setAllSpace(String allSpace) {
		this.allSpace = allSpace;
	}
	
	@Column(name="SECTION_REQUIRE",length=20)
	public String getSectionRequired() {
		return sectionRequired;
	}
	public void setSectionRequired(String sectionRequired) {
		this.sectionRequired = sectionRequired;
	}
	@Column(name="BATCH_NO",length=10)
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	
	@Column(name="BZ1",length=50)
	public String getBz1() {
		return bz1;
	}
	public void setBz1(String bz1) {
		this.bz1 = bz1;
	}
	@Column(name="BZ2",length=50)
	public String getBz2() {
		return bz2;
	}
	public void setBz2(String bz2) {
		this.bz2 = bz2;
	}
	@Column(name="BZ3",length=50)
	public String getBz3() {
		return bz3;
	}
	public void setBz3(String bz3) {
		this.bz3 = bz3;
	}

	
	
	
	public ApplyInfo(String id, String systemName, String tableName,
			String nodeId, String loadType, String playOnFileType,
			String organizationType, String frequency, Date applyFirstdate,
			Date applyLastdate, Date applyStartdate, Integer saveCirole,
			String fileType, String allSpace, String addSpace,
			String applyExplain, String status, String batchNo,
			String sectionRequire, String detaildatadate, String systemchn,
			String objectchn, String bz1, String bz2, String bz3, String bz4) {
		super();
		this.id = id;
		this.systemName = systemName;
		this.tableName = tableName;
		this.nodeId = nodeId;
		this.loadType = loadType;
		this.playOnFileType = playOnFileType;
		this.organizationType = organizationType;
		this.frequency = frequency;

	}
	public ApplyInfo() {
		super();
	}
	public ApplyInfo(String applyId, String systemName, String tableName,
			String nodeId, String loadType, String playOnFileType,
			String organizationType, String frequency, Date applyFirstdate,
			Date applyLastdate, Date applyStartdate, Integer saveCirole,
			String fileType, String allSpace, String addSpace,
			String applyExplain, String status, String batchNo,
			String sectionRequire, String detaildatadate, String systemchn,
			String objectchn) {
		super();
		this.id = applyId;
		this.systemName = systemName;
		this.tableName = tableName;
		this.nodeId = nodeId;
		this.loadType = loadType;
		this.playOnFileType = playOnFileType;
		this.organizationType = organizationType;
		this.frequency = frequency;
		
	}
	public ApplyInfo(String applyId, String status) {
		super();
		this.id = applyId;
	}
}
