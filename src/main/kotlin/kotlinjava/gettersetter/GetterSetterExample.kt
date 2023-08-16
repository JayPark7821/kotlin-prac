package kotlinjava.gettersetter

/**
 * GetterSetterExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/16
 */

fun main(){

    // java
    val person :Person = Person()
    person.setName("jay")
    person.setAge(10)
    person.setAddress("seoul")

    println(person.getName())
    println(person.getAge())

    // kotlin
    val person1 :Person = Person()
    person1.name = "jay"
    person1.age = 10

    println(person1.name)
    println(person1.age)

    // 프로퍼티가 없어도 게터 메서드라면 프로퍼티처럼 사용 가능
    println(person1.uuid)

    // setter가 있음에도 오류 컴파일 오류 발생
//    person1.address = "seoul"
    println(person1.myAddress())

}