package kr.jay.elasticsearchprac.config.extension

import org.springframework.http.server.reactive.ServerHttpRequest
import java.util.HashMap

/**
 * ServletHttpRequestExtension
 *
 * @author jaypark
 * @version 1.0.0
 * @since 1/2/24
 */

private val mapReqIdToTxId = HashMap<String, String>()

var ServerHttpRequest.txid: String?
    get(){
        return mapReqIdToTxId[this.id]
    }
    set(value){
        if(value == null){
            mapReqIdToTxId.remove(this.id)
        }else{
            mapReqIdToTxId[this.id] = value
        }
    }