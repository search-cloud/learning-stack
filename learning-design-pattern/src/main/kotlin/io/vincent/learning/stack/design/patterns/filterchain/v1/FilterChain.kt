package io.vincent.learning.stack.design.patterns.filterchain.v1

/**
 * Created by Vincent on 9/26/18.
 * @author Vincent
 * @since 1.0, 9/26/18
 */
class FilterChain {

    private val filters = mutableListOf<Filter>()

    fun addFilter(filter: Filter): FilterChain {
        this.filters.add(filter)
        return this
    }

    fun doFilter(messageNeedToFilter: MessageNeedToFilter)
            = filters.forEach { it.doFilter(messageNeedToFilter) }

}