package lightning.im2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ClientIdleStateHandler extends IdleStateHandler {
    public ClientIdleStateHandler() {
        super(15, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        System.out.println(new Date() + ": 15s内未检测到客户端信息, 断开连接: " + ctx.channel().remoteAddress().toString());
        ctx.channel().close();
    }
}
