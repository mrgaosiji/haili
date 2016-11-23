package com.hl.web.entity;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.hl.common.model.BaseBean;



/**
 * The persistent class for the MM_ROLE database table.
 * 
 */
@Entity
@Table(name="MM_ROLE")
public class MmRole extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false, length=32)
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="ADD_TIME", nullable=false)
	private Date addTime;

	private String description;

	@Column(precision=3,name="ROLE_LEVEL")
	private Long level;

	@Column(nullable=false,name="NAME")
	private String name;

	//uni-directional many-to-many association to Function
    @ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="MM_ROLE_FUNC_REL"
		, joinColumns={
			@JoinColumn(name="ROLE_ID", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="FUNCTION_ID", nullable=false)
			}
		)
	private List<MmFunction> functions;

    public MmRole() {
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getLevel() {
		return this.level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MmFunction> getFunctions() {
		return this.functions;
	}

	public void setFunctions(List<MmFunction> functions) {
		this.functions = functions;
	}
	
}