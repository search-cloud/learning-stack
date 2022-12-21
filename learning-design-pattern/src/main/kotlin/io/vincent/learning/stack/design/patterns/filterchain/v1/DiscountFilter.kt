package io.vincent.learning.stack.design.patterns.filterchain.v1

/**
 * Created by Vincent on 9/26/18.
 * @author Vincent
 * @since 1.0, 9/26/18
 */
class DiscountFilter : Filter {
    override fun doFilter(messageNeedToFilter: MessageNeedToFilter) {
        println("DiscountFilter do ${messageNeedToFilter.name} filter")
    }
}