package com.relay2.util;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AssumptionViolatedException;
import org.junit.rules.ExternalResource;

public class RequiresMysqlServer  extends ExternalResource {
	
	public List<String> list = new ArrayList<>();
	private int timeout = 30;
	public RequiresMysqlServer(String... hostPorts) {
		for (String hostPort : hostPorts) {
			list.add(hostPort);
		}
	}
	
	public RequiresMysqlServer(List<String> theList) {
		this.list = theList;
	}
	
	@Override
	protected void before() throws Throwable {
		
		for(String hostPort : list) {
			String [] array = hostPort.split(":");
			String host = array[0];
			Integer port  = Integer.valueOf(array[1]);
			try (Socket socket = new Socket()) {
				socket.setTcpNoDelay(true);
				socket.setSoLinger(true, 0);
				socket.connect(new InetSocketAddress(host, port), timeout);
			} catch (Exception e) {
				throw new AssumptionViolatedException(String.format("Seems as Mysql is not running at %s:%s.", host, port), e);
			}

		}
	}


	
}
