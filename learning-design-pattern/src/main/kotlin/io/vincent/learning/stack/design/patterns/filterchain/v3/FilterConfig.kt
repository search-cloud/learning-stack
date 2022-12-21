package io.vincent.learning.stack.design.patterns.filterchain.v3

/**
 * A filter configuration object used by a business context
 * to pass information to a filter during initialization.
 *
 * @see Filter
 * @author Vincent
 * @since 1.0, 11/13/18
 */
interface FilterConfig {
    /**
     * Returns the filter-name of this filter as defined in the deployment
     * descriptor.
     */
    fun getFilterName(): String


    /**
     * Returns a reference to the [Context] in which the caller
     * is executing.
     *
     * @return a [Context] object, used by the caller to
     * interact with its business
     *
     * @see Context
     */
    fun getContext(): Context
}
