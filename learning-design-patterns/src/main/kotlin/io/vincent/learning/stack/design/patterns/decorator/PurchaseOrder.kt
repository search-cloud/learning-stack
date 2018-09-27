package io.vincent.learning.stack.design.patterns.decorator

import java.io.Serializable

/**
 * Created by Vincent on 9/26/18.
 * @author Vincent
 * @since 1.0, 9/26/18
 */
class PurchaseOrder(
        /**
         * id
         */
        var id: Long? = null,

        /**
         * 价格信息
         */
        var offer: Offer = Offer()
) : Serializable