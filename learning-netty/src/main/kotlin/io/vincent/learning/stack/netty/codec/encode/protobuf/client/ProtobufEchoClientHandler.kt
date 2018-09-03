package io.vincent.learning.stack.netty.codec.encode.protobuf.client

import io.netty.channel.ChannelHandlerAdapter
import io.netty.channel.ChannelHandlerContext
import io.vincent.learning.stack.netty.codec.encode.protobuf.SearchRequestProto
import java.util.*


/**
 * Created by Vincent on 2018/8/6.
 * @author Vincent
 * @since 1.0, 2018/8/6
 */
class ProtobufEchoClientHandler : ChannelHandlerAdapter() {

    override fun channelActive(ctx: ChannelHandlerContext) {
        for (i in 1..10) {
            ctx.write(subReq("query -> $i"))
        }
        ctx.flush()
    }

    private fun subReq(query: String): SearchRequestProto.ItemSearchRequest? {
        val builder = SearchRequestProto.ItemSearchRequest.newBuilder()

        builder.query = query
        builder.userName = "VincentLu"
        builder.itemName = "Netty Book For Protobuf"
        val address = ArrayList<String>()
        address.add("NanJing YuHuaTai")
        address.add("BeiJing LiuLiChang")
        address.add("ShenZhen HongShuLin")
        builder.addAllAddress(address)
        builder.pageNumber = 1
        builder.pageSize = 1

        return builder.build()
    }

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        println("Receive server response : [$msg]")
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext) {
        ctx.flush()
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        cause?.printStackTrace()
        ctx?.close()
    }
}
