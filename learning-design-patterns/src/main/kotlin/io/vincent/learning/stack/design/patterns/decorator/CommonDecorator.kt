package io.vincent.learning.stack.design.patterns.decorator

/**
 * Created by Vincent on 9/26/18.
 * @author Vincent
 * @since 1.0, 9/26/18
 */
class CommonDecorator : PreferentialComponent {
    override fun getPreferential(preferentialContext: PreferentialContext): Preferential {
        // 默认没有优惠
        return Preferential()
    }
}