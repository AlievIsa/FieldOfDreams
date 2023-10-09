package com.example.fieldofdreams

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitTests {
    @Test
    fun getWordTest() {
        val (word, meaning) = Service.Words().getWord()
        assertNotNull(word)
        assertNotNull(meaning)
    }

    @Test
    fun getEncryptedWordTest() {
        val word = "Проверка"
        val result = Service.Words().getEncryptedWord(word)
        val expected = "________"
        assertEquals(expected, result)
    }

    @Test
    fun checkLetterTest() {
        val word = "Проверка"
        val letterOne = 'о'
        val letterTwo = 'у'
        val resultOne = Service.Words().checkLetter(word, letterOne)
        val resultTwo = Service.Words().checkLetter(word, letterTwo)
        assertEquals(true, resultOne)
        assertEquals(false, resultTwo)
    }

    @Test
    fun openLetterTest() {
        val word = "Проверка"
        val encryptedWord = "________"
        val letter = 'р'
        val result = Service.Words().openLetter(encryptedWord, word, letter)
        val expected = "_р___р__"
        assertEquals(expected, result)

    }

    @Test
    fun checkWordIsDecryptedTest() {
        val wordOne = "Проверка"
        val wordTwo = "Про_ерка"
        val resultOne = Service.Words().checkWordIsDecrypted(wordOne)
        val resultTwo = Service.Words().checkWordIsDecrypted(wordTwo)
        assertEquals(true, resultOne)
        assertEquals(false, resultTwo)
    }
}