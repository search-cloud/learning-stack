package io.vincent.learning.stack.design.patterns.decorator

/**
 * Created by Vincent on 9/26/18.
 * @author Vincent
 * @since 1.0, 9/26/18
 */
abstract class PreferentialComponentDecorator(protected open var preferentialComponent: PreferentialComponent) : PreferentialComponent {

    override fun getPreferential(preferentialContext: PreferentialContext): Preferential {
        return preferentialComponent.getPreferential(preferentialContext)
    }
}