package org.fatec.findbus.utils

import org.fatec.findbus.models.dto.Line
import org.springframework.stereotype.Component

@Component
class LineMapper {
    companion object {
        fun getTerminalByDirection(line: Line, direction: Int): String {
            return when (direction) {
                1 -> line.mainTerminal
                2 -> line.secondaryTerminal
                else -> throw IllegalArgumentException("Direção inválida: $direction")
            }
        }
    }
}