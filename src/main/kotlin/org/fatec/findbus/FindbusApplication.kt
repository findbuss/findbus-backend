package org.fatec.findbus

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FindbusApplication

fun main(args: Array<String>) {
    runApplication<FindbusApplication>(*args)
}
