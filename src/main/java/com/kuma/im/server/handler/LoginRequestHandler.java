package com.kuma.im.server.handler;

import com.kuma.im.protocol.packet.LoginRequestPacket;
import com.kuma.im.protocol.packet.LoginResponsePacket;
import com.kuma.im.session.Session;
import com.kuma.im.util.IDUtils;
import com.kuma.im.util.SessionUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 登录请求处理
 *
 * @author kuma 2021-02-26
 */
@Slf4j
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    protected LoginRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) {

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(msg.getVersion());

        if (!valid(msg)) {
            log.info("客户端登录验证失败");
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);
        } else {
            log.info("客户端登录验证成功");
            loginResponsePacket.setSuccess(true);
            // 记录 Session
            String userId = IDUtils.randomId();
            loginResponsePacket.setUserId(userId);
            loginResponsePacket.setUsername(msg.getUsername());
            log.info("[{}}]登录成功", msg.getUsername());
            SessionUtils.bindSession(new Session(userId, msg.getUsername()), ctx.channel());
        }
        ctx.writeAndFlush(loginResponsePacket);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        SessionUtils.unBindSession(ctx.channel());
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
