package com.kunalhk2029.calculator.domain

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ExpressionWriterTest {

    private lateinit var writer: ExpressionWriter

    @Before
    fun setUp() {
        writer = ExpressionWriter()
    }

    @Test
    fun `Initial parentheses parsed`() {
        writer.processAction(CalculatorAction.Parenthesis)
        writer.processAction(CalculatorAction.Number(10))
        writer.processAction(CalculatorAction.Op(Operation.ADD))
        writer.processAction(CalculatorAction.Number(20))
        writer.processAction(CalculatorAction.Parenthesis)

        assertThat(writer.expression).isEqualTo("(10+20)")
    }

    @Test
    fun `Closing parentheses at the start not parsed`() {
        writer.processAction(CalculatorAction.Parenthesis)
        writer.processAction(CalculatorAction.Parenthesis)
        assertThat(writer.expression).isEqualTo("((")
    }

    @Test
    fun `Parentheses around a number are parsed`() {
        writer.processAction(CalculatorAction.Parenthesis)
        writer.processAction(CalculatorAction.Number(100))
        writer.processAction(CalculatorAction.Parenthesis)
        assertThat(writer.expression).isEqualTo("(100)")
    }

    @Test
    fun `result of calculation assigned`() {
        writer.processAction(CalculatorAction.Number(100))
        writer.processAction(CalculatorAction.Op(Operation.ADD))
        writer.processAction(CalculatorAction.Number(500))
        writer.processAction(CalculatorAction.Calculate)
        assertThat(writer.expression).isEqualTo("600.0")
    }
}