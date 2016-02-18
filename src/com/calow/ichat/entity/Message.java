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
 * Message entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "message", catalog = "cim")
public class Message implements java.io.Serializable {

	// Fields

	/**
	 * 消息表
	 */
	private static final long serialVersionUID = 1L;
	private Integer MId;
	private User user;
	private Resource resource;
	private Messagetype messagetype;
	private Group group;
	private String MContent;
	private Timestamp MCreateTime;
	private Set<Messageset> messagesets = new HashSet<Messageset>(0);

	// Constructors

	/** default constructor */
	public Message() {
	}

	/** minimal constructor */
	public Message(User user, Messagetype messagetype, Group group,
			Timestamp MCreateTime) {
		this.user = user;
		this.messagetype = messagetype;
		this.group = group;
		this.MCreateTime = MCreateTime;
	}

	/** full constructor */
	public Message(User user, Resource resource, Messagetype messagetype,
			Group group, String MContent, Timestamp MCreateTime,
			Set<Messageset> messagesets) {
		this.user = user;
		this.resource = resource;
		this.messagetype = messagetype;
		this.group = group;
		this.MContent = MContent;
		this.MCreateTime = MCreateTime;
		this.messagesets = messagesets;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "M_ID", unique = true, nullable = false)
	public Integer getMId() {
		return this.MId;
	}

	public void setMId(Integer MId) {
		this.MId = MId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "M_UserID", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "M_ResourceID")
	public Resource getResource() {
		return this.resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "M_Type", nullable = false)
	public Messagetype getMessagetype() {
		return this.messagetype;
	}

	public void setMessagetype(Messagetype messagetype) {
		this.messagetype = messagetype;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "M_groupID", nullable = false)
	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@Column(name = "M_Content", length = 100)
	public String getMContent() {
		return this.MContent;
	}

	public void setMContent(String MContent) {
		this.MContent = MContent;
	}

	@Column(name = "M_CreateTime", nullable = false, length = 19)
	public Timestamp getMCreateTime() {
		return this.MCreateTime;
	}

	public void setMCreateTime(Timestamp MCreateTime) {
		this.MCreateTime = MCreateTime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "message")
	public Set<Messageset> getMessagesets() {
		return this.messagesets;
	}

	public void setMessagesets(Set<Messageset> messagesets) {
		this.messagesets = messagesets;
	}

}