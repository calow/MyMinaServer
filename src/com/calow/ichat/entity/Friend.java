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
 * Friend entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "friend", catalog = "cim")
public class Friend implements java.io.Serializable {

	// Fields

	/**
	 * 用户好友表
	 */
	private static final long serialVersionUID = 1L;
	private Integer FId;
	private Friendgroup friendgroup;
	private User userByFUserId;
	private User userByFFriendId;
	private String FName;

	// Constructors

	/** default constructor */
	public Friend() {
	}

	/** minimal constructor */
	public Friend(Friendgroup friendgroup, User userByFUserId,
			User userByFFriendId) {
		this.friendgroup = friendgroup;
		this.userByFUserId = userByFUserId;
		this.userByFFriendId = userByFFriendId;
	}

	/** full constructor */
	public Friend(Friendgroup friendgroup, User userByFUserId,
			User userByFFriendId, String FName) {
		this.friendgroup = friendgroup;
		this.userByFUserId = userByFUserId;
		this.userByFFriendId = userByFFriendId;
		this.FName = FName;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "F_ID", unique = true, nullable = false)
	public Integer getFId() {
		return this.FId;
	}

	public void setFId(Integer FId) {
		this.FId = FId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "F_FriendGroupID", nullable = false)
	public Friendgroup getFriendgroup() {
		return this.friendgroup;
	}

	public void setFriendgroup(Friendgroup friendgroup) {
		this.friendgroup = friendgroup;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "F_UserID", nullable = false)
	public User getUserByFUserId() {
		return this.userByFUserId;
	}

	public void setUserByFUserId(User userByFUserId) {
		this.userByFUserId = userByFUserId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "F_FriendID", nullable = false)
	public User getUserByFFriendId() {
		return this.userByFFriendId;
	}

	public void setUserByFFriendId(User userByFFriendId) {
		this.userByFFriendId = userByFFriendId;
	}

	@Column(name = "F_Name", length = 30)
	public String getFName() {
		return this.FName;
	}

	public void setFName(String FName) {
		this.FName = FName;
	}

}