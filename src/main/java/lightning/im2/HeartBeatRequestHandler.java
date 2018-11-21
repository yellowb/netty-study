package lightning.im2;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lightning.im.HeartBeatRequestPacket;
import lightning.im.HeartBeatResponsePacket;
import lightning.im.LoginUtil;

import java.util.Date;

@ChannelHandler.Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {

    public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket msg) throws Exception {
        System.out.println(new Date() + ": 收到客户端[" + ctx.channel().remoteAddress() +"]心跳包");
        ctx.writeAndFlush(new HeartBeatResponsePacket());
    }
}
