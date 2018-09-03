package io.vincent.learning.stack.netty.codec.encode.protobuf

import com.google.protobuf.InvalidProtocolBufferException
import org.junit.Test
import java.util.*

/**
 * Created by Vincent on 9/2/18.
 *
 * @author Vincent
 * @since 1.0, 9/2/18
 */
class SearchRequestProtoTest {
    private fun encode(req: SearchRequestProto.ItemSearchRequest): ByteArray {
        return req.toByteArray()
    }

    @Throws(InvalidProtocolBufferException::class)
    private fun decode(body: ByteArray): SearchRequestProto.ItemSearchRequest {
        return SearchRequestProto.ItemSearchRequest.parseFrom(body)
    }

    private fun createSearchReq(): SearchRequestProto.ItemSearchRequest {
        val builder = SearchRequestProto.ItemSearchRequest.newBuilder()

        builder.query = "1"
        builder.userName = "Vincent.Lu"
        builder.itemName = "Netty Book"
        val address = ArrayList<String>()
        address.add("NanJing YuHuaTai")
        address.add("BeiJing LiuLiChang")
        address.add("ShenZhen HongShuLin")
        builder.addAllAddress(address)
        builder.pageNumber = 1
        builder.pageSize = 1
        return builder.build()
    }

    /**
     *
     */
    @Test
    @Throws(InvalidProtocolBufferException::class)
    fun test() {
        val req = createSearchReq()
        println("Before encode : " + req.toString())
        val req2 = decode(encode(req))
        println("After decode : " + req.toString())
        println("Assert equal : --> " + (req2 == req))

    }
}
