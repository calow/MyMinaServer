package com.calow.cim.nio.mutual;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.calow.cim.nio.mutual.RunToolParam;

public abstract class Tool {
	
	abstract public void act(HttpServletRequest request, HttpServletResponse response);
	
	abstract public Map<Object, Object> toolMain(RunToolParam rtp);
	
	abstract public Map<Object, Object> toolMain(HttpServletRequest request,
			HttpServletResponse response);
}
