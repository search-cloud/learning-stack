package io.vincent.learning.stack.design.patterns.decorator

import io.vincent.learning.stack.design.patterns.filterchain.v1.*
import kotlin.test.Test

/**
 * Created by Vincent on 9/26/18.
 * @author Vincent
 * @since 1.0, 9/26/18
 */
class FilterChainV1Test {

    @Test
    fun testFilterChain() {
        // 第一个过滤链
        val filterChain = FilterChain()
                .addFilter(DiscountFilter())
                .addFilter(RedbagFilter())
                .addFilter(CouponFilter())
                .addFilter(MemberFilter())

        // 开始执行
        filterChain.doFilter(MessageNeedToFilter("Promotion"))
    }
}