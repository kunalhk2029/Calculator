package com.app.calculator.domain

import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test


class ExpressionParserTest{

    lateinit var parser: ExpressionParser

    @Test
    fun `Expression is properly parsed`(){
        // 1. GIVEN / System in test
        parser = ExpressionParser("3+5-3x4/3")

        // 2. Execute System / DO SOMETHING WITH WHAT'S GIVEN
        val actual = parser.parse()

        // 3. ASSERT EXPECTED == ACTUAL
        val expected = listOf(
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.ADD),
            ExpressionPart.Number(5.0),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(4.0),
            ExpressionPart.Op(Operation.DIVIDE),
            ExpressionPart.Number(3.0),
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `Expression with parentheses is properly parsed`() {
        parser = ExpressionParser("4.43-(4x5)")

        val actual = parser.parse()

        val expected = listOf(
            ExpressionPart.Number(4.43),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Parentheses(ParenthesesType.Opening),
            ExpressionPart.Number(4.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(5.0),
            ExpressionPart.Parentheses(ParenthesesType.Closing),
        )
        assertEquals(expected,actual)
    }

    @Test
    fun `Expression with nested parentheses is properly parsed`() {
        parser = ExpressionParser("4.43-(4x5-(2+5-(3x5)))")

        val actual = parser.parse()

        val expected = listOf(
            ExpressionPart.Number(4.43),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Parentheses(ParenthesesType.Opening),
            ExpressionPart.Number(4.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(5.0),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Parentheses(ParenthesesType.Opening),
            ExpressionPart.Number(2.0),
            ExpressionPart.Op(Operation.ADD),
            ExpressionPart.Number(5.0),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Parentheses(ParenthesesType.Opening),
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(5.0),
            ExpressionPart.Parentheses(ParenthesesType.Closing),
            ExpressionPart.Parentheses(ParenthesesType.Closing),
            ExpressionPart.Parentheses(ParenthesesType.Closing)
        )
        assertEquals(expected,actual)
    }
}