package mvc.repository

import mvc.model.User

/**
 * UserRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/25/24
 */
class UserRepository {
    companion object {
        private val users = mutableMapOf<String, User>()

        fun save(user: User) {
            users[user.id] = user
        }

        fun findAll(): List<User> {
            return users.values.toList()
        }
    }


}