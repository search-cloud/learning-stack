package io.vincent.learning.stack.design.patterns.decorator

/**
 * 可优惠的信息抽象。
 *
 *
 * Created by Vincent on 9/26/18.
 * @author Vincent
 * @since 1.0, 9/26/18
 */
interface PreferentialComponent {



    /**
     * 获取优惠信息。
     */
    fun getPreferential(preferentialContext: PreferentialContext): Preferential
}