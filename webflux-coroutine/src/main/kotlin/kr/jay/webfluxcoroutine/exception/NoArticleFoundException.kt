package kr.jay.webfluxcoroutine.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * NoArticleFoundException
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/27/23
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
class NoArticleFoundException(message : String) : RuntimeException(message)