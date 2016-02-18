package com.calow.ichat.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Resource entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "resource", catalog = "cim")
public class Resource implements java.io.Serializable {

	// Fields

	/**
	 * 消息附件信息表
	 */
	private static final long serialVersionUID = 1L;
	private Integer RId;
	private Storage storage;
	private String RName;
	private Integer RSize;
	private Set<Message> messages = new HashSet<Message>(0);

	// Constructors

	/** default constructor */
	public Resource() {
	}

	/** minimal constructor */
	public Resource(Integer RId, Storage storage, String RName, Integer RSize) {
		this.RId = RId;
		this.storage = storage;
		this.RName = RName;
		this.RSize = RSize;
	}

	/** full constructor */
	public Resource(Integer RId, Storage storage, String RName, Integer RSize,
			Set<Message> messages) {
		this.RId = RId;
		this.storage = storage;
		this.RName = RName;
		this.RSize = RSize;
		this.messages = messages;
	}

	// Property accessors
	@Id
	@Column(name = "R_ID", unique = true, nullable = false)
	public Integer getRId() {
		return this.RId;
	}

	public void setRId(Integer RId) {
		this.RId = RId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_StorageID", nullable = false)
	public Storage getStorage() {
		return this.storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}

	@Column(name = "R_Name", nullable = false, length = 20)
	public String getRName() {
		return this.RName;
	}

	public void setRName(String RName) {
		this.RName = RName;
	}

	@Column(name = "R_Size", nullable = false)
	public Integer getRSize() {
		return this.RSize;
	}

	public void setRSize(Integer RSize) {
		this.RSize = RSize;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "resource")
	public Set<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

}