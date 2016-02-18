package com.calow.ichat.admin.action;

import org.apache.struts2.ServletActionContext;

import com.calow.cim.nio.session.DefaultSessionManager;
import com.calow.ichat.common.util.ContextHolder;
import com.opensymphony.xwork2.ActionSupport;

public class SessionAction extends ActionSupport {

	/**
	 * 后台管理页面action类
	 */
	private static final long serialVersionUID = 1L;

	
	public String list(){
		ServletActionContext.getRequest().setAttribute("sessionList", ((DefaultSessionManager)ContextHolder.getBean("defaultSessionManager")).getSessions());
		return "list";
	}
}
