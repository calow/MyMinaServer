package com.calow.ichat.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Storage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "storage", catalog = "cim")
public class Storage implements java.io.Serializable {

	// Fields

	/**
	 * 附件表
	 */
	private static final long serialVersionUID = 1L;
	private Integer SId;
	private byte[] SContent;
	private Set<Resource> resources = new HashSet<Resource>(0);

	// Constructors

	/** default constructor */
	public Storage() {
	}

	/** minimal constructor */
	public Storage(Integer SId, byte[] SContent) {
		this.SId = SId;
		this.SContent = SContent;
	}

	/** full constructor */
	public Storage(Integer SId, byte[] SContent, Set<Resource> resources) {
		this.SId = SId;
		this.SContent = SContent;
		this.resources = resources;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "S_ID", unique = true, nullable = false)
	public Integer getSId() {
		return this.SId;
	}

	public void setSId(Integer SId) {
		this.SId = SId;
	}

	@Column(name = "S_Content", nullable = false)
	public byte[] getSContent() {
		return this.SContent;
	}

	public void setSContent(byte[] SContent) {
		this.SContent = SContent;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "storage")
	public Set<Resource> getResources() {
		return this.resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

}