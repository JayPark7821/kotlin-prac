package kr.jay.webfluxcoroutine.exception

import org.springframework.http.HttpStatus
import org.springframework.validation.BindException
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.ResponseStatus
import kotlin.reflect.KProperty

/**
 * InvalidParameter
 *
 * @author jaypark
 * @version 1.0.0
 * @since 1/2/24
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
class InvalidParameter : BindException {
    constructor(request: Any, field: KProperty<*>, code: String = "", message: String = "") :
        super(
            WebDataBinder(request, request::class.simpleName!!).bindingResult
            .apply {
                rejectValue(field.name, code, message)
            }
        )

}
