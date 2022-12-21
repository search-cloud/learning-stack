package io.vincent.learning.stack.design.patterns.filterchain.v1

/**
 * Created by Vincent on 9/27/18.
 * @author Vincent
 * @since 1.0, 9/27/18
 */
class MemberFilter : Filter {
    override fun doFilter(messageNeedToFilter: MessageNeedToFilter) {
        println("MemberFilter do ${messageNeedToFilter.name} filter")
    }
}