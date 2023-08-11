import java.util.LinkedList

fun main(){

    // immutable
    val currencyList = listOf("USD", "KRW", "JPY")

    // mutable
    val mutableCurrencyList = mutableListOf<String>()
    mutableCurrencyList.add("USD")
    mutableCurrencyList.add("KRW")
    mutableCurrencyList.add("JPY")

    val mutableCurrencyList2 = mutableListOf<String>().apply {
        add("USD")
        add("KRW")
        add("JPY")
    }

    // immutable set
    val numberSet = setOf(1, 2, 3, 4, 5)

    // mutable set
    val mutableSet = mutableSetOf<Int>().apply {
        add(1)
        add(2)
        add(3)
        add(4)
        add(5)
    }

    // immutable map
    val numberMap = mapOf("one" to 1, "two" to 2, "three" to 3)

    // mutable map
    val mutableMap = mutableMapOf<String, Int>()
    mutableMap["one"] = 1
    mutableMap["two"] = 2
    mutableMap["three"] = 3

    // collection builder
    val numberList = buildList{
        add(1)
        add(2)
        add(3)
    }
    // builder 내부에서는 mutable
    // 반환은 immutable


    // linkedList
    val linkedList = LinkedList<Int>().apply {
        addFirst(3)
        add(2)
        addLast(1)
    }

    // arrayList
    val arrayList = ArrayList<Int>().apply {
        add(1)
        add(2)
        add(3)
    }

    val iterator = currencyList.iterator()
    while (iterator.hasNext()) {
        println(iterator.next())
    }

    println("================")

    for ( currency in currencyList) {
        println(currency)
    }

    println("================")

    currencyList.forEach{
        println(it)
    }

    println("================")

    val lowerList = listOf("a", "b", "c")
    val upperList = mutableListOf<String>()

    for (lowerCase in lowerList) {
        upperList.add(lowerCase.uppercase())
    }

    println(upperList)
    println(lowerList.map{it.uppercase()})

    val upperListMap = lowerList.map { it.uppercase() }
    val filteredList = mutableListOf<String>()
    for (upperCase in upperListMap) {
        if (upperCase == "A" || upperCase == "C"){
            filteredList.add(upperCase)
        }
    }
    println(filteredList)
    println(upperListMap.filter{it == "A" || it == "C"})

    upperListMap
        .asSequence()
        .filter{it == "A" || it == "C"}
        .toList()




}