package com.example.pmocbr.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class IdGeneratorTest {

    @Test
    fun `generateId should follow PMOC-YYYYMMDD-XXXX format`() {
        val id = IdGenerator.generateId()
        
        // Verifica prefixo
        assertTrue(id.startsWith("PMOC-"))
        
        // Verifica tamanho total (PMOC- + 8 chars data + - + 4 chars random = 18 chars)
        assertEquals(18, id.length)
        
        // Verifica se a data atual esta presente
        val datePart = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
        assertTrue(id.contains(datePart))
        
        // Verifica se tem 2 hifens
        assertEquals(2, id.count { it == '-' })
    }

    @Test
    fun `generateId should generate unique IDs`() {
        val id1 = IdGenerator.generateId()
        val id2 = IdGenerator.generateId()
        
        assertTrue(id1 != id2)
    }
}
