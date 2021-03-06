package org.lpw.tephra.nio;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author lpw
 */
@Component("tephra.nio.client")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Sharable
public class ClientImpl extends Handler implements Client {
    protected ClientListener listener;
    protected String ip;
    protected int port;
    protected EventLoopGroup group;
    protected Bootstrap bootstrap;

    @Override
    public void connect(ClientListener listener, String ip, int port) {
        this.listener = listener;
        this.ip = ip;
        this.port = port;
        if (group == null)
            group = new NioEventLoopGroup(1);
        if (bootstrap == null)
            bootstrap = new Bootstrap().group(group).channel(NioSocketChannel.class).handler(this);

        if (logger.isInfoEnable())
            logger.info("连接远程服务[{}:{}]。", ip, port);

        bootstrap.connect(ip, port);
    }

    @Override
    public void channelActive(ChannelHandlerContext context) throws Exception {
        listener.connect(nioHelper.put(context));

        if (logger.isInfoEnable())
            logger.info("连接到远程服务[{}:{}]。", ip, port);
    }

    @Override
    public void channelInactive(ChannelHandlerContext context) throws Exception {
        close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) throws Exception {
        logger.warn(cause, "连接远程服务[{}:{}]发生异常！", ip, port);
        if (context != null)
            context.close();
        close();
    }

    @Override
    public void close() {
        if (listener != null)
            listener.disconnect();
        listener = null;

        if (group != null && !group.isShutdown())
            group.shutdownGracefully();

        if (logger.isInfoEnable())
            logger.info("断开远程服务[{}:{}]连接。", ip, port);
    }

    @Override
    protected Listener getListener() {
        return listener;
    }
}
