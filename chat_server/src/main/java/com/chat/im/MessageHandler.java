package com.chat.im;

import java.net.InetSocketAddress;
import java.util.Map;

import javax.sound.midi.Soundbank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 *  ��Ϣ ������
 * @author Administrator
 *
 */
public class MessageHandler extends SimpleChannelInboundHandler<Object>{

	public static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		//������Ϣ
		ByteBuf buf = (ByteBuf) msg;
		byte[] bytes = new byte[buf.readableBytes()];
		buf.readBytes(bytes);
		System.out.println(new String(bytes,"utf-8"));
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
		String ip = address.getAddress().getHostAddress();
		logger.info("�ͻ��� [ip:"+ip+" ]");
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
