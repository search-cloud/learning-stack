package io.vincent.learning.stack.jvm;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Vincent on 3/1/19.
 *
 * @author Vincent
 * @since 1.0, 3/1/19
 */
@Data
@Slf4j
@ToString
public class Customer {
    private Long id;
    private String name;
    private Integer age;

    public Customer(String name, int age) {
        this.name = name;
        this.age = Math.abs(age/100);
    }

}
