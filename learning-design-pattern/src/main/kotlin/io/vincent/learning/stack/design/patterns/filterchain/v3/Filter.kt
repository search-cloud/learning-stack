package io.vincent.learning.stack.design.patterns.filterchain.v3

/**
 * Created by Vincent on 9/26/18.
 * @author Vincent
 * @since 1.0, 9/26/18
 */
interface Filter {

    /**
     * Called by the web container to indicate to a filter that it is
     * being placed into service.
     *
     *
     * The servlet container calls the init
     * method exactly once after instantiating the filter. The init
     * method must complete successfully before the filter is asked to do any
     * filtering work.
     *
     *
     * The web container cannot place the filter into service if the init
     * method either
     *
     *  1. Throws a ServletException
     *  1. Does not return within a time period defined by the web container
     *
     */
    fun init(filterConfig: FilterConfig)

    fun doFilter(request: Request, response: Response, filterChain: FilterChain)

    /**
     * Called by the web container to indicate to a filter that it is being
     * taken out of service.
     *
     *
     * This method is only called once all threads within the filter's
     * doFilter method have exited or after a timeout period has passed.
     * After the web container calls this method, it will not call the
     * doFilter method again on this instance of the filter.
     *
     *
     * This method gives the filter an opportunity to clean up any
     * resources that are being held (for example, memory, file handles,
     * threads) and make sure that any persistent state is synchronized
     * with the filter's current state in memory.
     */
    fun destroy()
}
