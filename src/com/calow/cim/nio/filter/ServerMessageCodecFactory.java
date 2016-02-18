package com.calow.cim.nio.filter;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class ServerMessageCodecFactory implements ProtocolCodecFactory {

	private final ServerMessageDecoder decoder;

	private final ServerMessageEncoder encoder;

	public ServerMessageCodecFactory() {
		decoder = new ServerMessageDecoder();
		encoder = new ServerMessageEncoder();
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		return decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		return encoder;
	}

}
