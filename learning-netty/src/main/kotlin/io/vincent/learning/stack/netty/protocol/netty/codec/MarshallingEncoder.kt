/*
 * Copyright 2013-2018 Lilinfeng.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.vincent.learning.stack.netty.protocol.netty.codec

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandler.Sharable
import org.jboss.marshalling.Marshaller

/**
 * Created by Vincent on 9/4/18.
 * @author Vincent
 * @since 1.0, 9/4/18
 */
@Sharable
class MarshallingEncoder {
    private var marshaller: Marshaller = MarshallingCodecFactory.buildMarshalling()

    fun encode(msg: Any, out: ByteBuf) {
        try {
            val lengthPos = out.writerIndex()
            out.writeBytes(LENGTH_PLACEHOLDER)
            val output = ChannelBufferByteOutput(out)
            marshaller.start(output)
            marshaller.writeObject(msg)
            marshaller.finish()
            out.setInt(lengthPos, out.writerIndex() - lengthPos - 4)
        } finally {
            marshaller.close()
        }
    }

    companion object {
        private val LENGTH_PLACEHOLDER = ByteArray(4)
    }
}
