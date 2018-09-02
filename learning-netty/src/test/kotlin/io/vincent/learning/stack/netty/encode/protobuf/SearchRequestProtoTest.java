package io.vincent.learning.stack.netty.encode.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 9/2/18.
 *
 * @author Vincent
 * @since 1.0, 9/2/18
 */
public class SearchRequestProtoTest {
	private static byte[] encode(SearchRequestProto.ItemSearchRequest req) {
		return req.toByteArray();
	}

	private static SearchRequestProto.ItemSearchRequest decode(byte[] body) throws InvalidProtocolBufferException {
		return SearchRequestProto.ItemSearchRequest.parseFrom(body);
	}

	private static SearchRequestProto.ItemSearchRequest createSearchReq() {
		SearchRequestProto.ItemSearchRequest.Builder builder =
				SearchRequestProto.ItemSearchRequest.newBuilder();

		builder.setQuery("1");
		builder.setUserName("Vincent.Lu");
		builder.setItemName("Netty Book");
		List<String> address = new ArrayList<>();
		address.add("NanJing YuHuaTai");
		address.add("BeiJing LiuLiChang");
		address.add("ShenZhen HongShuLin");
		builder.addAllAddress(address);
		builder.setPageNumber(1);
		builder.setPageSize(1);
		return builder.build();
	}

	/**
	 *
	 */
	@Test
	public void test() throws InvalidProtocolBufferException {
		SearchRequestProto.ItemSearchRequest req = createSearchReq();
		System.out.println("Before encode : " + req.toString());
		SearchRequestProto.ItemSearchRequest req2 = decode(encode(req));
		System.out.println("After decode : " + req.toString());
		System.out.println("Assert equal : --> " + req2.equals(req));

	}
}
