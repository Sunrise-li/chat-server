package com.chat.im;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
	public static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

	private final int port;

	public NettyServer(int port) {
		this.port = port;
	}

	public void start() {
		ServerBootstrap b = new ServerBootstrap();
		NioEventLoopGroup group = new NioEventLoopGroup();
		try {
			b.group(group).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					System.out.println("initChannel ch:" + ch);
					ch.pipeline()
							.addLast("handler", new MessageHandler());
				}

			}).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);
			// 绑定端口,开始接收进来的连接
			ChannelFuture future = b.bind(port).sync();
			logger.info("netty服务启动:[port:" + this.port + " ]");
			// 等待socket关闭
			future.channel().closeFuture().sync();
		} catch (Exception e) {
			logger.error("服务器启动异常--" + e.getMessage());
		}

	}
}
