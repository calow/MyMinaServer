package com.calow.ichat.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Tool entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tool", catalog = "cim")
public class Tool implements java.io.Serializable {

	// Fields

	/**
	 * 工具表
	 */
	private static final long serialVersionUID = 1L;
	private Integer TId;
	private String TName;
	private String TDescription;
	private Set<Toolversion> toolversions = new HashSet<Toolversion>(0);

	// Constructors

	/** default constructor */
	public Tool() {
	}

	/** minimal constructor */
	public Tool(Integer TId, String TName) {
		this.TId = TId;
		this.TName = TName;
	}

	/** full constructor */
	public Tool(Integer TId, String TName, String TDescription,
			Set<Toolversion> toolversions) {
		this.TId = TId;
		this.TName = TName;
		this.TDescription = TDescription;
		this.toolversions = toolversions;
	}

	// Property accessors
	@Id
	@Column(name = "T_ID", unique = true, nullable = false)
	public Integer getTId() {
		return this.TId;
	}

	public void setTId(Integer TId) {
		this.TId = TId;
	}

	@Column(name = "T_Name", nullable = false, length = 20)
	public String getTName() {
		return this.TName;
	}

	public void setTName(String TName) {
		this.TName = TName;
	}

	@Column(name = "T_Description", length = 100)
	public String getTDescription() {
		return this.TDescription;
	}

	public void setTDescription(String TDescription) {
		this.TDescription = TDescription;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tool")
	public Set<Toolversion> getToolversions() {
		return this.toolversions;
	}

	public void setToolversions(Set<Toolversion> toolversions) {
		this.toolversions = toolversions;
	}

}