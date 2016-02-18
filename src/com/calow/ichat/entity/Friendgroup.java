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
 * Friendgroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "friendgroup", catalog = "cim")
public class Friendgroup implements java.io.Serializable {

	// Fields

	/**
	 * 用户分组表
	 */
	private static final long serialVersionUID = 1L;
	private Integer fgId;
	private User user;
	private String fgName;
	private Set<Friend> friends = new HashSet<Friend>(0);

	// Constructors

	/** default constructor */
	public Friendgroup() {
	}

	/** minimal constructor */
	public Friendgroup(User user, String fgName) {
		this.user = user;
		this.fgName = fgName;
	}

	/** full constructor */
	public Friendgroup(User user, String fgName, Set<Friend> friends) {
		this.user = user;
		this.fgName = fgName;
		this.friends = friends;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "FG_ID", unique = true, nullable = false)
	public Integer getFgId() {
		return this.fgId;
	}

	public void setFgId(Integer fgId) {
		this.fgId = fgId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FG_UserID", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "FG_Name", nullable = false, length = 20)
	public String getFgName() {
		return this.fgName;
	}

	public void setFgName(String fgName) {
		this.fgName = fgName;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "friendgroup")
	public Set<Friend> getFriends() {
		return this.friends;
	}

	public void setFriends(Set<Friend> friends) {
		this.friends = friends;
	}

}