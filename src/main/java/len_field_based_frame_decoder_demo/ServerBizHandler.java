package len_field_based_frame_decoder_demo;

import com.sun.deploy.util.ArrayUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.regex.*;

/**
 * Implement the calculation logic and send the result back to client
 */
@ChannelHandler.Sharable
public class ServerBizHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private static final Pattern p = Pattern.compile("\\D+");

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        String expression = msg.toString(CharsetUtil.UTF_8);
        String[] args = p.split(expression);
        Matcher m = p.matcher(expression);
        m.find();

        char op = expression.charAt(m.start());
        int arg1 = Integer.parseInt(args[0]);
        int arg2 = Integer.parseInt(args[1]);
        int result;

        switch (op) {
            case '+':
                result = arg1 + arg2;
                break;
            case '-':
                result = arg1 - arg2;
                break;
            case '*':
                result = arg1 * arg2;
                break;
            case '/':
                result = arg1 / arg2;
                break;
            default:
                result = -99999;
                break;
        }

        ByteBuf output = Unpooled.copyInt(result);
        ctx.writeAndFlush(output);

    }
}
