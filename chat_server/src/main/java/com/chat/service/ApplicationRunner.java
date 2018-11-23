package com.chat.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import com.chat.im.NettyServer;

/**
 * springboot初始化时启动
 * @author Administrator
 *
 */
@Component
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner{
	@Value("${nio.netty.port}")
	private int port;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		new NettyServer(port).start();
	}

}
