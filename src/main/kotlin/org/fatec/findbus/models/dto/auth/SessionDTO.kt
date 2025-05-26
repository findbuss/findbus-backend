package org.fatec.findbus.models.dto.auth

class SessionDTO() {
    private var login: String? = null
    private var token: String? = null

    fun getLogin(): String? {
        return login
    }

    fun setLogin(login: String?) {
        this.login = login
    }

    fun getToken(): String? {
        return token
    }

    fun setToken(token: String?) {
        this.token = token
    }
}
