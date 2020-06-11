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
import org.jboss.marshalling.Unmarshaller
import java.io.IOException
import java.io.StreamCorruptedException

/**
 * Created by Vincent on 9/4/18.
 * @author Vincent
 * @since 1.0, 9/4/18
 */
class MarshallingDecoder
/**
 * Creates a new decoder whose maximum object size is `1048576` bytes.
 * If the size of the received object is greater than `1048576` bytes,
 * a [StreamCorruptedException] will be raised.
 *
 * @throws IOException
 */
{
    private val unmarshaller: Unmarshaller = MarshallingCodecFactory.buildUnMarshalling()

    fun decode(byteBuf: ByteBuf): Any {
        val objectSize = byteBuf.readInt()
        val buf = byteBuf.slice(byteBuf.readerIndex(), objectSize)
        val input = ChannelBufferByteInput(buf)
        unmarshaller.use { unmarshaller ->
            unmarshaller.start(input)
            val obj = unmarshaller.readObject()
            unmarshaller.finish()
            byteBuf.readerIndex(byteBuf.readerIndex() + objectSize)
            return obj
        }
    }
}
