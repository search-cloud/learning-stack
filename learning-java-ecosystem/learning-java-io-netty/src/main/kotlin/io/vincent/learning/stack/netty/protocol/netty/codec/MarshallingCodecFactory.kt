package io.vincent.learning.stack.netty.protocol.netty.codec

import org.jboss.marshalling.Marshaller
import org.jboss.marshalling.Marshalling
import org.jboss.marshalling.MarshallingConfiguration
import org.jboss.marshalling.Unmarshaller
import java.io.IOException

/**
 * Created by Vincent on 9/4/18.
 * @author Vincent
 * @since 1.0, 9/4/18
 */
object MarshallingCodecFactory {

    /**
     * 创建Jboss Marshaller
     *
     * @return
     * @throws IOException
     */
    internal fun buildMarshalling(): Marshaller {
        val marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial")
        val configuration = MarshallingConfiguration()
        configuration.version = 5
        return marshallerFactory.createMarshaller(configuration)
    }

    /**
     * 创建Jboss Unmarshaller
     *
     * @return
     * @throws IOException
     */
    internal fun buildUnMarshalling(): Unmarshaller {
        val marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial")
        val configuration = MarshallingConfiguration()
        configuration.version = 5
        return marshallerFactory.createUnmarshaller(configuration)
    }
}
