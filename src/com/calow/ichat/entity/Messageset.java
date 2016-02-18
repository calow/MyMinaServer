package com.calow.ichat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Messageset entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "messageset", catalog = "cim")
public class Messageset implements java.io.Serializable {

	// Fields

	/**
	 * 消息集合实体类
	 */
	private static final long serialVersionUID = 1L;
	private Integer msId;
	private User user;
	private Message message;
	private Short msState;
	private Short msOffline;

	// Constructors

	/** default constructor */
	public Messageset() {
	}

	/** full constructor */
	public Messageset(User user, Message message, Short msState, Short msOffline) {
		this.user = user;
		this.message = message;
		this.msState = msState;
		this.msOffline = msOffline;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "MS_ID", unique = true, nullable = false)
	public Integer getMsId() {
		return this.msId;
	}

	public void setMsId(Integer msId) {
		this.msId = msId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MS_ToUser", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MS_MessageID", nullable = false)
	public Message getMessage() {
		return this.message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	@Column(name = "MS_State", nullable = false)
	public Short getMsState() {
		return this.msState;
	}

	public void setMsState(Short msState) {
		this.msState = msState;
	}

	@Column(name = "MS_Offline", nullable = false)
	public Short getMsOffline() {
		return this.msOffline;
	}

	public void setMsOffline(Short msOffline) {
		this.msOffline = msOffline;
	}

}