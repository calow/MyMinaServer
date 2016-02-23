package com.calow.ichat.entity;

import java.sql.Timestamp;
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
 * Group entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "group", catalog = "cim")
public class Group implements java.io.Serializable {

	// Fields

	/**
	 * 群组表
	 */
	private static final long serialVersionUID = 1L;
	private Integer GId;
	private Resource resource;
	private User user;
	private String GName;
	private Timestamp GCreateTime;
	private Short GType;
	private String GJson;
	private Set<Message> messages = new HashSet<Message>(0);
	private Set<Groupusers> groupuserses = new HashSet<Groupusers>(0);

	// Constructors

	/** default constructor */
	public Group() {
	}

	/** minimal constructor */
	public Group(Resource resource, User user, String GName,
			Timestamp GCreateTime, Short GType, String GJson) {
		this.resource = resource;
		this.user = user;
		this.GName = GName;
		this.GCreateTime = GCreateTime;
		this.GType = GType;
		this.GJson = GJson;
	}

	/** full constructor */
	public Group(Resource resource, User user, String GName,
			Timestamp GCreateTime, Short GType, String GJson,
			Set<Message> messages, Set<Groupusers> groupuserses) {
		this.resource = resource;
		this.user = user;
		this.GName = GName;
		this.GCreateTime = GCreateTime;
		this.GType = GType;
		this.GJson = GJson;
		this.messages = messages;
		this.groupuserses = groupuserses;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "G_ID", unique = true, nullable = false)
	public Integer getGId() {
		return this.GId;
	}

	public void setGId(Integer GId) {
		this.GId = GId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "G_GroupPic", nullable = false)
	public Resource getResource() {
		return this.resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "G_UserID", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "G_Name", nullable = false, length = 20)
	public String getGName() {
		return this.GName;
	}

	public void setGName(String GName) {
		this.GName = GName;
	}

	@Column(name = "G_CreateTime", nullable = false, length = 19)
	public Timestamp getGCreateTime() {
		return this.GCreateTime;
	}

	public void setGCreateTime(Timestamp GCreateTime) {
		this.GCreateTime = GCreateTime;
	}

	@Column(name = "G_Type", nullable = false)
	public Short getGType() {
		return this.GType;
	}

	public void setGType(Short GType) {
		this.GType = GType;
	}

	@Column(name = "G_JSon", nullable = false, length = 50)
	public String getGJson() {
		return this.GJson;
	}

	public void setGJson(String GJson) {
		this.GJson = GJson;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "group")
	public Set<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "group")
	public Set<Groupusers> getGroupuserses() {
		return this.groupuserses;
	}

	public void setGroupuserses(Set<Groupusers> groupuserses) {
		this.groupuserses = groupuserses;
	}

}