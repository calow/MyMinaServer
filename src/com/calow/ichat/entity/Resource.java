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
	private String RFormat;
	private Set<User> users = new HashSet<User>(0);
	private Set<Group> groups = new HashSet<Group>(0);
	private Set<Message> messages = new HashSet<Message>(0);

	// Constructors

	/** default constructor */
	public Resource() {
	}

	/** minimal constructor */
	public Resource(Storage storage, String RName, Integer RSize, String RFormat) {
		this.storage = storage;
		this.RName = RName;
		this.RSize = RSize;
		this.RFormat = RFormat;
	}

	/** full constructor */
	public Resource(Storage storage, String RName, Integer RSize,
			String RFormat, Set<User> users, Set<Group> groups,
			Set<Message> messages) {
		this.storage = storage;
		this.RName = RName;
		this.RSize = RSize;
		this.RFormat = RFormat;
		this.users = users;
		this.groups = groups;
		this.messages = messages;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
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

	@Column(name = "R_Format", nullable = false, length = 20)
	public String getRFormat() {
		return this.RFormat;
	}

	public void setRFormat(String RFormat) {
		this.RFormat = RFormat;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "resource")
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "resource")
	public Set<Group> getGroups() {
		return this.groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "resource")
	public Set<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

}