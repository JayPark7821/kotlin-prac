package kotlinjava.gettersetter

import java.time.LocalDate

/**
 * Student
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/16
 */
class Student {
    @JvmField
    var name: String? = null
    var birthDate: LocalDate? = null

    val age: Int = 10

    var grade: String? = null
        private set

    fun changeGrade(grade: String) {
        this.grade = grade
    }
}