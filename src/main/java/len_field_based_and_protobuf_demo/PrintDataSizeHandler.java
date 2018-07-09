package len_field_based_and_protobuf_demo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import io.netty.handler.codec.MessageToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class PrintDataSizeHandler extends MessageToMessageDecoder<ByteBuf> {

    private final static Logger LOG = LoggerFactory.getLogger(PrintDataSizeHandler.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        LOG.info("Got data size: {}", msg.readableBytes());
        out.add(msg.retainedSlice());
    }
}
