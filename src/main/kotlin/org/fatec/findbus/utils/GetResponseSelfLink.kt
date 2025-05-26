package org.fatec.findbus.utils

import org.springframework.web.servlet.support.ServletUriComponentsBuilder

object GetResponseSelfLink {
    @JvmStatic
    fun getSelfLink(): String {
        return ServletUriComponentsBuilder.fromCurrentRequest()
            .toUriString()
    }
}
