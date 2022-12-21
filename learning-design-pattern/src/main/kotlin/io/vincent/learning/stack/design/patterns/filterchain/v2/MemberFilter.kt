package io.vincent.learning.stack.design.patterns.filterchain.v2

/**
 * Created by Vincent on 9/27/18.
 * @author Vincent
 * @since 1.0, 9/27/18
 */
class MemberFilter : Filter {
    override fun doFilter(request: Request, response: Response, filterChain: FilterChain) {
        println("MemberFilter do ${request.name} filter")
        filterChain.doFilter(request, response, filterChain)
        println("MemberFilter do ${response.name} filter")
    }
}