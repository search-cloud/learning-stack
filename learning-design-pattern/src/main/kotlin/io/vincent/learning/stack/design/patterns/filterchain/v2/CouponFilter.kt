package io.vincent.learning.stack.design.patterns.filterchain.v2

/**
 * Created by Vincent on 9/26/18.
 * @author Vincent
 * @since 1.0, 9/26/18
 */
class CouponFilter : Filter {

    override fun doFilter(request: Request, response: Response, filterChain: FilterChain) {
        println("CouponFilter do ${request.name} filter")
        filterChain.doFilter(request, response, filterChain)
        println("CouponFilter do ${response.name} filter")
    }
}