package com.calow.cim.nio.session;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;

import com.calow.cim.nio.constant.CIMConstant;

public class CIMSession implements Serializable {

	private transient static final long serialVersionUID = 1L;
	public transient static String ID = "ID";
	public transient static String HOST = "HOST";

	private transient IoSession session;

	private String gid;// session全局ID
	private Long nid;// session在本台服务器上的ID
	private String deviceId;// 客户端ID (设备号码+应用包名)
	private String host;// session绑定的服务器IP
	private String account;// session绑定的账号
	private String channel;// 终端设备类型
	private String deviceModel;// 终端设备型号
	private Long bindTime;// 登录时间
	private Long heartbeat;// 心跳时间

	public CIMSession(IoSession session) {
		this.session = session;
		this.nid = session.getId();
	}

	public CIMSession() {
		
	}

	public boolean isConnected() {
		if (session != null)
			return session.isConnected();
		return false;
	}

	public boolean write(Object msg) {
		if (session != null) {
			WriteFuture wf = session.write(msg);
			wf.awaitUninterruptibly(5, TimeUnit.SECONDS);
			return wf.isWritten();
		}
		return false;
	}

	public Object getAttribute(String key) {
		if (session != null)
			return session.getAttribute(key);
		return null;
	}

	public void setAttribute(String key, Object value) {
		if (session != null)
			session.setAttribute(key, value);
	}

	public void removeAttribute(String key) {
		if (session != null)
			session.removeAttribute(key);
	}

	public SocketAddress getRemoteAddress() {
		if (session != null)
			return session.getRemoteAddress();
		return null;
	}

	public boolean containsAttribute(String key) {
		if (session != null)
			return session.containsAttribute(key);
		return false;
	}

	public Long getHeartbeat() {
		this.heartbeat = (Long) session.getAttribute(CIMConstant.HEARTBEAT_KEY);
		return heartbeat;
	}

	public void setHeartbeat(Long heartbeat) {
		this.heartbeat = heartbeat;
		setAttribute(CIMConstant.HEARTBEAT_KEY, heartbeat);
	}

	public String getAccount() {
		this.account = (String) session.getAttribute(CIMConstant.SESSION_KEY);
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
		setAttribute(CIMConstant.SESSION_KEY, account);
	}

	public String getDeviceModel() {
		this.deviceModel = (String) session.getAttribute("deviceModel");
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;

		setAttribute("deviceModel", deviceModel);
	}

	public String getDeviceId() {
		this.deviceId = (String) session.getAttribute("deviceId");
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;

		setAttribute("deviceId", deviceId);
	}

	public String getGid() {
		this.gid = (String) session.getAttribute("gid");
		return gid;
	}

	public void setGid(String gid) {

		this.gid = gid;

		setAttribute("gid", gid);
	}

	public String getHost() {
		this.host = (String) session.getAttribute("host");
		return host;
	}

	public void setHost(String host) {
		this.host = host;

		setAttribute("host", host);
	}

	public String getChannel() {
		this.channel = (String) session.getAttribute("channel");
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;

		setAttribute("channel", channel);
	}

	public Long getBindTime() {
		this.bindTime = (Long) session.getAttribute("bindTime");
		return bindTime;
	}

	public void setBindTime(Long bindTime) {
		this.bindTime = bindTime;
		setAttribute("bindTime", bindTime);
	}

	public Long getNid() {
		return nid;
	}

	public void setNid(Long nid) {
		this.nid = nid;
	}

	public boolean isLocalhost() {

		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			return ip.equals(host);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return false;

	}

	public void close(boolean immediately) {
		if (session != null)
			session.close(immediately);
	}
}
