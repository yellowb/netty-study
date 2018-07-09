package len_field_based_and_protobuf_demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.regex.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import len_field_based_and_protobuf_demo.beans.*;

/**
 * Implement the calculation logic and send the result back to client
 */
@ChannelHandler.Sharable
public class ServerBizHandler extends SimpleChannelInboundHandler<ExpressionProto.Expression> {

    private final static Logger LOG = LoggerFactory.getLogger(ServerBizHandler.class);

    private static final Pattern p = Pattern.compile("\\D+");

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOG.info("Client bye-bye");
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ExpressionProto.Expression msg) throws Exception {

        LOG.info("Receive expression from client: {}", msg.getArg1() + msg.getOp() + msg.getArg2());

        int arg1 = msg.getArg1();
        int arg2 = msg.getArg2();
        String op = msg.getOp();

        int ret;

        switch (op) {
            case "+":
                ret = arg1 + arg2;
                break;
            case "-":
                ret = arg1 - arg2;
                break;
            case "*":
                ret = arg1 * arg2;
                break;
            case "/":
                ret = arg1 / arg2;
                break;
            default:
                ret = -99999;
                break;
        }

        ResultProto.Result result = ResultProto.Result.newBuilder().setResult(ret).build();
        ctx.writeAndFlush(result);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
