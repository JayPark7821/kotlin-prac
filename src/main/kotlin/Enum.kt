enum class PaymentStatus(val label: String) :Payable{
    UNPAID("미지급") {
        override fun isPayable(): Boolean = true
    },
    PAID("지급완료"){
        override fun isPayable(): Boolean = false
    },
    FAILED("지급실패"){
        override fun isPayable(): Boolean = false
    },
    REFUNDED("환불"){
        override fun isPayable(): Boolean = false
    },
    ;

//    abstract fun isPayable():Boolean
}

interface Payable{
    fun isPayable():Boolean
}

fun main(){
    println(PaymentStatus.UNPAID.label)

    if (PaymentStatus.UNPAID.isPayable()) {
        println("지급 가능")
    }

    val paymentStatus = PaymentStatus.valueOf("PAID")
    println(paymentStatus.label)
    if(paymentStatus == PaymentStatus.PAID){
        println("지급 완료")
    }

    for (status in PaymentStatus.values()) {
        println("[${status.name}] ${status.label} : ${status.ordinal}")
    }
}