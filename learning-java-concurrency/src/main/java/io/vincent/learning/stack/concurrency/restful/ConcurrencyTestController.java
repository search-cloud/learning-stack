package io.vincent.learning.stack.concurrency.restful;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Vincent on 2/24/19.
 *
 * @author Vincent
 * @since 1.0, 2/24/19
 */
@RestController
@RequestMapping("/concurrecy/test")
public class ConcurrencyTestController {

    @RequestMapping("")
    public void test() {

    }
}
