package com.calow.ichat.cim.push;

import com.calow.cim.nio.mutual.Message;


public class SystemMessagePusher extends DefaultMessagePusher {

	public SystemMessagePusher(){
		super();
	}
	
	@Override
	public void pushMessageToUser(Message MessageMO){
		MessageMO.setSender("system");
		super.pushMessageToUser(MessageMO);
	}
}
