package org.fatec.findbus.response

class RestResponse<T> {
    private var selfLink: String? = null
    private var data: T? = null
    private var success: Boolean = false
    private var statusCode: Int = 0

    fun getSelfLink(): String? = selfLink
    fun setSelfLink(selfLink: String?) {
        this.selfLink = selfLink
    }

    fun getData(): T? = data
    fun setData(data: T?) {
        this.data = data
    }

    fun isSuccess(): Boolean = success
    fun setSuccess(success: Boolean) {
        this.success = success
    }

    fun getStatusCode(): Int = statusCode
    fun setStatusCode(statusCode: Int) {
        this.statusCode = statusCode
    }
}