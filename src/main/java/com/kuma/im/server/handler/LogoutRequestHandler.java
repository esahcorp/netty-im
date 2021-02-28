package com.kuma.im.server.handler;

import com.kuma.im.protocol.packet.LogoutRequestPacket;
import com.kuma.im.protocol.packet.LogoutResponsePacket;
import com.kuma.im.util.SessionUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 退出登录请求处理器
 *
 * @author kuma 2021-02-27
 */
@Slf4j
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket msg) throws Exception {
        SessionUtils.unBindSession(ctx.channel());
        LogoutResponsePacket logoutResponsePacket = new LogoutResponsePacket();
        logoutResponsePacket.setSuccess(true);
        log.info("登出成功!");
        ctx.channel().writeAndFlush(logoutResponsePacket);
    }
}
