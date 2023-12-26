package kr.jay.webfluxreactor.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * NoArticleException
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/26/23
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
class NoArticleException(message: String?): RuntimeException(message) {
}