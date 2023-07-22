package com.kunalhk2029.calculator.domain

sealed interface CalculatorAction{
    data class Number(val number: Int):CalculatorAction
    data class Op(val operation: Operation):CalculatorAction
    object Clear:CalculatorAction
    object Delete:CalculatorAction
    object Calculate:CalculatorAction
    object Decimal:CalculatorAction
    object Parenthesis:CalculatorAction
}