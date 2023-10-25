package kr.jay.kopringboottemplate.common.exception

import kotlin.RuntimeException as RuntimeException

/**
 * BusinessException
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/26/23
 */
class BusinessException(
    val errorCodes: ErrorCodes,
    private val args: List<String>,
    override val message: String = if (args.isEmpty()) errorCodes.message else errorCodes.message.format(args),
) : RuntimeException(message)