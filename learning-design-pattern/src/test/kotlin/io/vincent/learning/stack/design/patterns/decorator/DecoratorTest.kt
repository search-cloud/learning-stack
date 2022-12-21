package io.vincent.learning.stack.design.patterns.decorator

import org.junit.Test

/**
 * Created by Vincent on 9/26/18.
 *
 * @author Vincent
 * @since 1.0, 9/26/18
 */
class DecoratorTest {

    @Test
    fun getPreferential() {

        val commonDecorator = CommonDecorator()
        val discountDecorator = DiscountDecorator(commonDecorator)
        val couponDecorator = CouponDecorator(discountDecorator)

        val preferential = couponDecorator.getPreferential(
                PreferentialContext(
                        PurchaseOrder(1, Offer(itemTotalPrice = 398.0, orderTotalPrice = 398.0)),
                        Preferential(price = 30.0, condition = Condition(30.0), type = Preferential.Type.DISCOUNT.code)
                )
        )

        val preferential1 = discountDecorator.getPreferential(PreferentialContext(
                PurchaseOrder(1, Offer(itemTotalPrice = 398.0, orderTotalPrice = 398.0)),
                Preferential(price = 30.0, condition = Condition(30.0), type = Preferential.Type.COUPON.code)
        ))


        println(preferential)
        println(preferential1)

    }

}
