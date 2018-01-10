package com.wlwx.dispatch.server;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;


public class ServerProtocolCodecFactory extends DemuxingProtocolCodecFactory {
	public ServerProtocolCodecFactory() {
		super.addMessageDecoder(RequestDecoder.class);
		super.addMessageEncoder(HttpResponseMessage.class, ResponseEncoder.class);
	}
}
