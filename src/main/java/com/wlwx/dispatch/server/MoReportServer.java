package com.wlwx.dispatch.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.wlwx.dispatch.util.PublicConstants;
import com.wlwx.dispatch.util.SpringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;



public class MoReportServer {

	private final static Logger logger = LogManager.getLogger(MoReportServer.class);
    public static final String VERSION_STRING = "$Revision: 600461 $ $Date: 2007-12-03 18:55:52 +0900 (?, 03 12? 2007) $";
    private NioSocketAcceptor acceptor;
    private PublicConstants p = (PublicConstants) SpringUtil.getBean("publicConstants");


    public MoReportServer() {
        try {
            acceptor = new NioSocketAcceptor();
            acceptor.setReuseAddress(true);
            acceptor.getFilterChain().addLast(
                    "protocolFilter",
                    new ProtocolCodecFilter(
                            new ServerProtocolCodecFactory()));
            if(logger.isDebugEnabled()){
                acceptor.getFilterChain().addLast("logger", new LoggingFilter());
            }
            acceptor.setHandler(new MoReportServerHandler());
            acceptor.getSessionConfig().setTcpNoDelay(true);
            acceptor.getSessionConfig().setSoLinger(0);
            acceptor.getSessionConfig().setWriteTimeout(5);
            acceptor.getSessionConfig().setKeepAlive(false);
            acceptor.bind(new InetSocketAddress(p.getReportMoPort()));
            logger.info("Mo/Report Server now listening on port " + p.getReportMoPort());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

	public void unbind() {
		acceptor.unbind();
	}
	
	public void bind(){
		try {
			acceptor.bind(new InetSocketAddress(p.getReportMoPort()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
