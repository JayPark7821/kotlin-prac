package mvc.filter

import javax.servlet.*
import javax.servlet.annotation.WebFilter


/**
 * CharacterEncodingFilter
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/26/24
 */

@WebFilter("/*")
class CharacterEncodingFilter: Filter {
    override fun init(p0: FilterConfig?) {
        TODO("Not yet implemented")
    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        request?.characterEncoding = Charsets.UTF_8.name()
        response?.characterEncoding = Charsets.UTF_8.name()

        chain?.doFilter(request, response)
    }

    override fun destroy() {
        TODO("Not yet implemented")
    }

}