package kr.jay

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicLong
import kotlin.system.measureTimeMillis

/**
 * CoroutineCost
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/27/23
 */

fun main(){
    val count = AtomicLong()
    measureTimeMillis {
        runBlocking {
            repeat(10_000){
                launch{
                    repeat(100_000){
                        count.incrementAndGet()
                    }
                }
            }
        }
    }.also { println("total time: $it ms") }
}