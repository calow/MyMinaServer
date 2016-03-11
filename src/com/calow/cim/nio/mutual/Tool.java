package com.calow.cim.nio.mutual;

import java.sql.Connection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.calow.cim.nio.mutual.RunToolParam;

public abstract class Tool {
	
	private Connection connection;
	
	abstract public void act(HttpServletRequest request, HttpServletResponse response);
	
	abstract public Map<Object, Object> toolMain(RunToolParam rtp);
	
	abstract public Map<Object, Object> toolMain(HttpServletRequest request,
			HttpServletResponse response);

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
}
