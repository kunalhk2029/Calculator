package com.kunalhk2029.calculator.domain

class ExpressionWriter {

    private val allDigits = "0123456789"
    var expression = ""

    fun processAction(action: CalculatorAction) {
        when (action) {
            CalculatorAction.Clear -> {
                expression = ""
            }
            CalculatorAction.Delete -> {
                expression = expression.dropLast(1)
            }
            CalculatorAction.Calculate -> {
                val parser = ExpressionParser(prepareForCalculation())
                val evaluator = ExpressionEvaluator(parser.parse())
                expression=evaluator.evaluate().toString()
            }
            CalculatorAction.Decimal -> {
                if (canEnterDecimal()) {
                    expression += "."
                }
            }
            CalculatorAction.Parenthesis -> {
                processParenthesis()
            }

            is CalculatorAction.Number -> {
                expression += action.number
            }
            is CalculatorAction.Op -> {
                if (canEnterOperarion(action.operation)) {
                    expression += action.operation.symbol
                }
            }
        }
    }

    private fun prepareForCalculation(): String {
        val newExpression = expression.dropLastWhile {
            it in "$operationSymbols(."
        }
        if (newExpression.isEmpty()) {
            return "0"
        }
        return newExpression
    }

    private fun processParenthesis() {
        val openingParenthesisCount = expression.count { it == '(' }
        val closingParenthesisCount = expression.count { it == ')' }
        expression += when {
            expression.isEmpty() ||
                    expression.last() in "$operationSymbols(" -> "("
            expression.last() in "$allDigits)" &&
                    openingParenthesisCount == closingParenthesisCount -> return
            else -> ")"
        }
    }

    private fun canEnterDecimal(): Boolean {
        if (expression.isEmpty() || expression.last() in "${operationSymbols}.()")
            return false

        return !expression.takeLastWhile {
            it in "$allDigits."
        }.contains(".")
    }

    private fun canEnterOperarion(operation: Operation): Boolean {
        return if (operation == Operation.ADD || operation == Operation.SUBTRACT) {
            expression.isEmpty() || expression.last() in "${operationSymbols}$allDigits()"
        } else expression.isNotEmpty() && expression.last() in "$allDigits)"
    }
}