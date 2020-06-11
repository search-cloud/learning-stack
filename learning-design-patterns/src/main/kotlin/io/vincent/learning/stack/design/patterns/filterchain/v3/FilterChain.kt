package io.vincent.learning.stack.design.patterns.filterchain.v3

/**
 * Created by Vincent on 9/26/18.
 * @author Vincent
 * @since 1.0, 9/26/18
 */
interface FilterChain : Filter {

    fun filters(): MutableList<Filter>

    fun filtersIterator(): Iterator<Filter>

    fun addFilter(filter: Filter): FilterChain

    override fun doFilter(request: Request, response: Response, filterChain: FilterChain) {
        if (filtersIterator().hasNext())
            filtersIterator().next().doFilter(request, response, filterChain)
        else
            return
    }

}
