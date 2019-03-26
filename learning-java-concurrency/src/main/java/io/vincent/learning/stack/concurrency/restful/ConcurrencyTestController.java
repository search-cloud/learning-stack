package io.vincent.learning.stack.concurrency.restful;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Vincent on 2/24/19.
 *
 * @author Vincent
 * @since 1.0, 2/24/19
 */
@RestController
@RequestMapping("/concurrency/test")
@Slf4j
public class ConcurrencyTestController {

    @RequestMapping
    @ResponseBody
    public String test() {
        log.debug("concurrency test");
        return "concurrency test";
    }
}
