package io.vincent.learning.stack.design.patterns.decorator

/**
 * Created by Vincent on 9/26/18.
 * @author Vincent
 * @since 1.0, 9/26/18
 */
class DiscountDecorator(override var preferentialComponent: PreferentialComponent) : PreferentialComponentDecorator(preferentialComponent) {

    override fun getPreferential(preferentialContext: PreferentialContext): Preferential {
        var preferential = super.getPreferential(preferentialContext)
        val price = preferential.price
        if (preferentialContext.preferential.type == Preferential.Type.DISCOUNT.code
                && preferentialContext.preferential.condition!!.price != 0.0
                && preferentialContext.purchaseOrder.offer.itemTotalPrice >= preferentialContext.preferential.price) {
            preferential = preferentialContext.preferential
            preferential.price = preferentialContext.purchaseOrder.offer.itemTotalPrice - price - preferentialContext.preferential.price
        }
        return preferential
    }
}