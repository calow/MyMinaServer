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
 * Messagetype entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "messagetype", catalog = "cim")
public class Messagetype implements java.io.Serializable {

	// Fields

	/**
	 * 消息类型表
	 */
	private static final long serialVersionUID = 1L;
	private Integer mtId;
	private String mtName;
	private Short mtValue;
	private Set<Message> messages = new HashSet<Message>(0);

	// Constructors

	/** default constructor */
	public Messagetype() {
	}

	/** minimal constructor */
	public Messagetype(Short mtValue) {
		this.mtValue = mtValue;
	}

	/** full constructor */
	public Messagetype(String mtName, Short mtValue, Set<Message> messages) {
		this.mtName = mtName;
		this.mtValue = mtValue;
		this.messages = messages;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "MT_ID", unique = true, nullable = false)
	public Integer getMtId() {
		return this.mtId;
	}

	public void setMtId(Integer mtId) {
		this.mtId = mtId;
	}

	@Column(name = "MT_Name", length = 20)
	public String getMtName() {
		return this.mtName;
	}

	public void setMtName(String mtName) {
		this.mtName = mtName;
	}

	@Column(name = "MT_Value", nullable = false)
	public Short getMtValue() {
		return this.mtValue;
	}

	public void setMtValue(Short mtValue) {
		this.mtValue = mtValue;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "messagetype")
	public Set<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

}