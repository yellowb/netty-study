package len_field_based_and_protobuf_demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import len_field_based_and_protobuf_demo.beans.*;

/**
 * Send expression to server and receive result.
 */
public class ClientBizHandler extends SimpleChannelInboundHandler<ResultProto.Result> {

    private final static Logger LOG = LoggerFactory.getLogger(ClientBizHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

//        ExpressionProto.Expression exp = ExpressionProto.Expression.newBuilder().setArg1(1).setArg2(2).setOp("+").build();
//
//        ctx.writeAndFlush(exp);

        LOG.info("Connected with server {}", ctx.channel().remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ResultProto.Result msg) throws Exception {
        int ret = msg.getResult();

        LOG.info("The result from server is {}", ret);

//        ctx.close();
    }


}
