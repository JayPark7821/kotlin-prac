/**
 * QueryString
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/19/24
 */
class UserQueryStrings(
    queryStringLine: String,
) {
    val queryStrings: List<QueryString> =
        queryStringLine.split("&").map {
            val keyValue = it.split("=")
            require(keyValue.size == 2) { "QueryString must have key and value" }
            QueryString(keyValue[0], keyValue[1])
        }

    fun getValue(key:String) = this.queryStrings.findLast{
        queryString -> queryString.exists(key)
    }?.value


}