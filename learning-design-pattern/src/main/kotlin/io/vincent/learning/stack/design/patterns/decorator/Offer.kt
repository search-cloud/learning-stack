package io.vincent.learning.stack.design.patterns.decorator

import java.io.Serializable

/**
 * Created by Vincent on 9/26/18.
 * @author Vincent
 * @since 1.0, 9/26/18
 */
class Offer(
        /**
         * 商品总价
         */
        var itemTotalPrice: Double = 0.0,
        /**
         * 总运费
         */
        var deliveryPrice: Double = 0.0,
        /**
         * 优惠金额
         */
        var preferentialPrice: Double = 0.0,
        /**
         * 订单总额
         */
        var orderTotalPrice: Double = 0.0
) : Serializable