package len_field_based_frame_decoder_demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Send expression to server and receive result.
 */
public class ClientBizHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private final static Logger LOG = LoggerFactory.getLogger(ClientBizHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        int result = msg.readInt();
        LOG.info("Get result from server: {}", result);
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        String expression = "1+2";
        ByteBuf request = Unpooled.copiedBuffer(expression, CharsetUtil.UTF_8);

        ctx.writeAndFlush(request);

//        super.channelActive(ctx);
    }
}
