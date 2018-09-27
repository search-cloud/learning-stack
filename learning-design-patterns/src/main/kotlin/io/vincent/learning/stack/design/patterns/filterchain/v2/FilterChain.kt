package io.vincent.learning.stack.design.patterns.filterchain.v2

/**
 * Created by Vincent on 9/26/18.
 * @author Vincent
 * @since 1.0, 9/26/18
 */
class FilterChain : Filter {

    private val filters = mutableListOf<Filter>()
    private var filtersIterator = filters.iterator()

    fun addFilter(filter: Filter): FilterChain {
        this.filters.add(filter)
        filtersIterator = filters.iterator()
        return this
    }

    override fun doFilter(request: Request, response: Response, filterChain: FilterChain) {
        if (filtersIterator.hasNext())
            filtersIterator.next().doFilter(request, response, filterChain)
        else
            return
    }

}