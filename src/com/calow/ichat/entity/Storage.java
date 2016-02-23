package com.calow.ichat.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
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
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer SId;
	private byte[] SContent;
	private Set<Resource> resources = new HashSet<Resource>(0);

	// Constructors

	/** default constructor */
	public Storage() {
	}

	/** full constructor */
	public Storage(byte[] SContent, Set<Resource> resources) {
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

	@Column(name = "S_Content")
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