package com.calow.ichat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Toolversion entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "toolversion", catalog = "cim")
public class Toolversion implements java.io.Serializable {

	// Fields

	/**
	 * 工具版本表
	 */
	private static final long serialVersionUID = 1L;
	private Integer tvId;
	private Tool tool;
	private Storage storage;
	private String tvDescription;
	private Integer tvSize;
	private String tvFormat;

	// Constructors

	/** default constructor */
	public Toolversion() {
	}

	/** minimal constructor */
	public Toolversion(Integer tvId, Tool tool, Storage storage,
			Integer tvSize, String tvFormat) {
		this.tvId = tvId;
		this.tool = tool;
		this.storage = storage;
		this.tvSize = tvSize;
		this.tvFormat = tvFormat;
	}

	/** full constructor */
	public Toolversion(Integer tvId, Tool tool, Storage storage,
			String tvDescription, Integer tvSize, String tvFormat) {
		this.tvId = tvId;
		this.tool = tool;
		this.storage = storage;
		this.tvDescription = tvDescription;
		this.tvSize = tvSize;
		this.tvFormat = tvFormat;
	}

	// Property accessors
	@Id
	@Column(name = "TV_ID", unique = true, nullable = false)
	public Integer getTvId() {
		return this.tvId;
	}

	public void setTvId(Integer tvId) {
		this.tvId = tvId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TV_ToolID", nullable = false)
	public Tool getTool() {
		return this.tool;
	}

	public void setTool(Tool tool) {
		this.tool = tool;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TV_Storage", nullable = false)
	public Storage getStorage() {
		return this.storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}

	@Column(name = "TV_Description", length = 100)
	public String getTvDescription() {
		return this.tvDescription;
	}

	public void setTvDescription(String tvDescription) {
		this.tvDescription = tvDescription;
	}

	@Column(name = "TV_Size", nullable = false)
	public Integer getTvSize() {
		return this.tvSize;
	}

	public void setTvSize(Integer tvSize) {
		this.tvSize = tvSize;
	}

	@Column(name = "TV_Format", nullable = false, length = 20)
	public String getTvFormat() {
		return this.tvFormat;
	}

	public void setTvFormat(String tvFormat) {
		this.tvFormat = tvFormat;
	}

}