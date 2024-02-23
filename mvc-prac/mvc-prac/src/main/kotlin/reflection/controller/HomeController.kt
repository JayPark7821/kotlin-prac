package reflection.controller

import reflection.annotation.Controller
import reflection.annotation.RequestMapping
import reflection.annotation.RequestMethod
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * HomeController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/22/24
 */
@Controller
class HomeController {

    @RequestMapping(value= "/", method = [RequestMethod.GET])
    fun home(request: HttpServletRequest, response: HttpServletResponse) = "home"
}