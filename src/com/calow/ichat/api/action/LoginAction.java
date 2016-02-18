package com.calow.ichat.api.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.calow.ichat.common.util.ContextHolder;
import com.calow.ichat.dao.impl.UserDaoImpl;
import com.calow.ichat.entity.User;
import com.calow.ichat.service.UserService;
import com.calow.ichat.service.impl.UserServiceImpl;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class LoginAction extends ActionSupport implements ModelDriven<User> {

	private static final long serialVersionUID = 1L;
	User user = new User();

	@Override
	public User getModel() {
		return user;
	}

	public String login() {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		UserService userService = (UserService) ContextHolder.getBean("userService");
		HashMap<String, Object> datamap = (HashMap<String, Object>) userService.getValidation(user, request);
		postResult(response, datamap);
		return null;
	}

	public void postResult(HttpServletResponse response,
			HashMap<String, Object> datamap) {
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write(new Gson().toJson(datamap));
			pw.flush();
		} catch (IOException e) {
			datamap.put("code", 500);
			e.printStackTrace();
		} finally {
			pw.close();
		}
	}

}
