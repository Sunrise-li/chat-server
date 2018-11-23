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
			// �󶨶˿�,��ʼ���ս���������
			ChannelFuture future = b.bind(port).sync();
			logger.info("netty��������:[port:" + this.port + " ]");
			// �ȴ�socket�ر�
			future.channel().closeFuture().sync();
		} catch (Exception e) {
			logger.error("�����������쳣--" + e.getMessage());
		}

	}
}
