package io.vincent.learning.stack.netty.codec.encode.protobuf.server

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.vincent.learning.stack.netty.codec.encode.protobuf.SearchRequestProto
import io.vincent.learning.stack.netty.codec.encode.protobuf.SearchResponseProto

/**
 * Created by Vincent on 2018/8/6.
 * @author Vincent
 * @since 1.0, 2018/8/6
 */
class ProtobufEchoServerHandler : ChannelInboundHandlerAdapter() {

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        val req = msg as SearchRequestProto.ItemSearchRequest

        if ("VincentLu".equals(req.userName, ignoreCase = true)) {
            System.out.println("Service accept client subscribe req : [$req]")
            ctx.writeAndFlush(resp(req.query))
        }
    }

    private fun resp(query: String): SearchResponseProto.ItemSearchResponse? {
        val builder = SearchResponseProto.ItemSearchResponse.newBuilder()
        builder.query = query
        builder.code = "1O1OO200"
        builder.message = "Netty book order succeed, 3 days later, sent to the designated address"
        builder.pageNumber = 1
        builder.pageSize = 10

        return builder.build()
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        cause?.printStackTrace()
        ctx?.close()
    }

}
