/**
 * QueryString
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/19/24
 */
data class QueryString(
    val key: String,
    val value: String
) {
    fun exists(key: String) = this.key == key
}