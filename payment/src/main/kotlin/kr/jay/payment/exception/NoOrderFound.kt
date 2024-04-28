package kr.jay.payment.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * NoOrderFound
 *
 * @author jaypark
 * @version 1.0.0
 * @since 4/28/24
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
class NoOrderFound(msg: String) : Throwable(msg) {
}