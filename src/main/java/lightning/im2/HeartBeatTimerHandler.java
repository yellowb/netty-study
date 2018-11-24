package lightning.im2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lightning.im.HeartBeatRequestPacket;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {

    private static final int HEARTBEAT_INTERVAL = 5;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.sendHeartBeatInSchedule(ctx);
    }

    private void sendHeartBeatInSchedule(ChannelHandlerContext ctx) {
        ctx.executor().schedule(new Runnable() {
            @Override
            public void run() {
                if (ctx.channel().isActive()) {
//                    System.out.println(new Date() + ": 向服务器发送心跳包");

                    ctx.writeAndFlush(new HeartBeatRequestPacket());
                    sendHeartBeatInSchedule(ctx);
                }
            }
        }, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }
}
