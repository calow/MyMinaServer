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
 * User entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user", catalog = "cim")
public class User implements java.io.Serializable {

	// Fields

	/**
	 * 用户表
	 */
	private static final long serialVersionUID = 1L;
	private Integer UId;
	private Userstate userstate;
	private String ULoginId;
	private String UNickName;
	private String UPassWord;
	private String USignture;
	private Set<Friendgroup> friendgroups = new HashSet<Friendgroup>(0);
	private Set<Group> groups = new HashSet<Group>(0);
	private Set<Message> messages = new HashSet<Message>(0);
	private Set<Groupusers> groupuserses = new HashSet<Groupusers>(0);
	private Set<Messageset> messagesets = new HashSet<Messageset>(0);
	private Set<Friend> friendsForFFriendId = new HashSet<Friend>(0);
	private Set<Friend> friendsForFUserId = new HashSet<Friend>(0);

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(Userstate userstate, String ULoginId, String UNickName,
			String UPassWord) {
		this.userstate = userstate;
		this.ULoginId = ULoginId;
		this.UNickName = UNickName;
		this.UPassWord = UPassWord;
	}

	/** full constructor */
	public User(Userstate userstate, String ULoginId, String UNickName,
			String UPassWord, String USignture, Set<Friendgroup> friendgroups,
			Set<Group> groups, Set<Message> messages,
			Set<Groupusers> groupuserses, Set<Messageset> messagesets,
			Set<Friend> friendsForFFriendId, Set<Friend> friendsForFUserId) {
		this.userstate = userstate;
		this.ULoginId = ULoginId;
		this.UNickName = UNickName;
		this.UPassWord = UPassWord;
		this.USignture = USignture;
		this.friendgroups = friendgroups;
		this.groups = groups;
		this.messages = messages;
		this.groupuserses = groupuserses;
		this.messagesets = messagesets;
		this.friendsForFFriendId = friendsForFFriendId;
		this.friendsForFUserId = friendsForFUserId;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "U_ID", unique = true, nullable = false)
	public Integer getUId() {
		return this.UId;
	}

	public void setUId(Integer UId) {
		this.UId = UId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "U_UserState", nullable = false)
	public Userstate getUserstate() {
		return this.userstate;
	}

	public void setUserstate(Userstate userstate) {
		this.userstate = userstate;
	}

	@Column(name = "U_LoginID", nullable = false, length = 20)
	public String getULoginId() {
		return this.ULoginId;
	}

	public void setULoginId(String ULoginId) {
		this.ULoginId = ULoginId;
	}

	@Column(name = "U_NickName", nullable = false, length = 20)
	public String getUNickName() {
		return this.UNickName;
	}

	public void setUNickName(String UNickName) {
		this.UNickName = UNickName;
	}

	@Column(name = "U_PassWord", nullable = false, length = 20)
	public String getUPassWord() {
		return this.UPassWord;
	}

	public void setUPassWord(String UPassWord) {
		this.UPassWord = UPassWord;
	}

	@Column(name = "U_Signture", length = 100)
	public String getUSignture() {
		return this.USignture;
	}

	public void setUSignture(String USignture) {
		this.USignture = USignture;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Friendgroup> getFriendgroups() {
		return this.friendgroups;
	}

	public void setFriendgroups(Set<Friendgroup> friendgroups) {
		this.friendgroups = friendgroups;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Group> getGroups() {
		return this.groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Groupusers> getGroupuserses() {
		return this.groupuserses;
	}

	public void setGroupuserses(Set<Groupusers> groupuserses) {
		this.groupuserses = groupuserses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Messageset> getMessagesets() {
		return this.messagesets;
	}

	public void setMessagesets(Set<Messageset> messagesets) {
		this.messagesets = messagesets;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userByFFriendId")
	public Set<Friend> getFriendsForFFriendId() {
		return this.friendsForFFriendId;
	}

	public void setFriendsForFFriendId(Set<Friend> friendsForFFriendId) {
		this.friendsForFFriendId = friendsForFFriendId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userByFUserId")
	public Set<Friend> getFriendsForFUserId() {
		return this.friendsForFUserId;
	}

	public void setFriendsForFUserId(Set<Friend> friendsForFUserId) {
		this.friendsForFUserId = friendsForFUserId;
	}

}