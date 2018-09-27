package io.vincent.learning.stack.design.patterns.decorator

import java.io.Serializable

open class Preferential(
        // 优惠价格
        var price: Double = 0.0,
        // 优惠条件
        var condition: Condition? = null,
        // 优惠类型
        var type: Int = Type.NONE.code,
        // 优先级
        var priority: Int = Int.MAX_VALUE,
        // 显示信息
        var display: String = "") : Serializable {

    enum class Type(val code: Int) {
        NONE(0), DISCOUNT(1), COUPON(2),
    }

    override fun toString(): String {
        return "Preferential: {" +
                "price=$price, condition=${condition?.price}, " +
                "type=$type, priority=$priority, display=$display" +
                "}"
    }
}
