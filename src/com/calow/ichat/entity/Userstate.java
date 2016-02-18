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
 * Userstate entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "userstate", catalog = "cim")
public class Userstate implements java.io.Serializable {

	// Fields

	/**
	 * 用户状态表
	 */
	private static final long serialVersionUID = 1L;
	private Integer usId;
	private String usName;
	private Short usValue;
	private Set<User> users = new HashSet<User>(0);

	// Constructors

	/** default constructor */
	public Userstate() {
	}

	/** minimal constructor */
	public Userstate(Short usValue) {
		this.usValue = usValue;
	}

	/** full constructor */
	public Userstate(String usName, Short usValue, Set<User> users) {
		this.usName = usName;
		this.usValue = usValue;
		this.users = users;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "US_ID", unique = true, nullable = false)
	public Integer getUsId() {
		return this.usId;
	}

	public void setUsId(Integer usId) {
		this.usId = usId;
	}

	@Column(name = "US_Name", length = 10)
	public String getUsName() {
		return this.usName;
	}

	public void setUsName(String usName) {
		this.usName = usName;
	}

	@Column(name = "US_Value", nullable = false)
	public Short getUsValue() {
		return this.usValue;
	}

	public void setUsValue(Short usValue) {
		this.usValue = usValue;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userstate")
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}