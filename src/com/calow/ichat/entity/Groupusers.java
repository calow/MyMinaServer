package com.calow.ichat.entity;

import java.sql.Timestamp;
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
 * Groupusers entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "groupusers", catalog = "cim")
public class Groupusers implements java.io.Serializable {

	// Fields

	/**
	 * 群组用户表
	 */
	private static final long serialVersionUID = 1L;
	private Integer guId;
	private User user;
	private Group group;
	private Timestamp guCreateTime;

	// Constructors

	/** default constructor */
	public Groupusers() {
	}

	/** full constructor */
	public Groupusers(User user, Group group, Timestamp guCreateTime) {
		this.user = user;
		this.group = group;
		this.guCreateTime = guCreateTime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "GU_ID", unique = true, nullable = false)
	public Integer getGuId() {
		return this.guId;
	}

	public void setGuId(Integer guId) {
		this.guId = guId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GU_UserID", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GU_GroupID", nullable = false)
	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@Column(name = "GU_CreateTime", nullable = false, length = 19)
	public Timestamp getGuCreateTime() {
		return this.guCreateTime;
	}

	public void setGuCreateTime(Timestamp guCreateTime) {
		this.guCreateTime = guCreateTime;
	}

}