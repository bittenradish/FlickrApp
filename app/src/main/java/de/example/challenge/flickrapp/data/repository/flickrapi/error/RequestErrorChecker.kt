package de.example.challenge.flickrapp.data.repository.flickrapi.error

import de.example.challenge.flickrapp.data.repository.flickrapi.ResponseCode
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownServiceException

class RequestErrorChecker {
    companion object {
        fun apiErrorCodeHandling(code: String?): ResponseCode {
            if (code == null) {
                return ResponseCode.UNKNOWN_EXCEPTION
            }
            //TODO: Implement all errors (Text of errors needed)
            return when (code) {
                "10" -> ResponseCode.API_UNAVAILABLE
                "100" -> ResponseCode.INVALID_KEY
                "105" -> ResponseCode.SERVICE_UNAVAILABLE
                "106" -> ResponseCode.OPERATION_FAILED
                "116" -> ResponseCode.BAD_URL
                "112" -> ResponseCode.METHOD_NOT_FOUND
                else -> ResponseCode.UNKNOWN_EXCEPTION
            }
        }

        fun responseErrorCodeHandling(responseCode: Int): ResponseCode {
            return when (responseCode / 100) {
                5 -> ResponseCode.SERVER_UNAVAILABLE
                4 -> ResponseCode.BAD_REQUEST
                3 -> ResponseCode.URL_CHANGED
                2 -> ResponseCode.RESPONSE_OK
                else -> ResponseCode.UNKNOWN_EXCEPTION
            }
        }

        fun errorChecker(
//            call: Call<out Any?>, 
            throwable: Throwable?
        ): ResponseCode {
            if(throwable == null)
                return ResponseCode.UNKNOWN_EXCEPTION
            return when (throwable) {
                is SocketTimeoutException -> ResponseCode.SOCKET_TIMEOUT
                is UnknownServiceException -> ResponseCode.UNKNOWN_SERVICE_EXCEPTION
                is SocketException -> ResponseCode.SOCKET_EXCEPTION
                is IOException -> ResponseCode.IO_EXCEPTION
                else ->
//                    if (call.isCanceled) {
//                    ResponseCode.CALL_CANCELLED
//                } else {
                    ResponseCode.UNKNOWN_EXCEPTION
//                }
            }
        }
    }
}