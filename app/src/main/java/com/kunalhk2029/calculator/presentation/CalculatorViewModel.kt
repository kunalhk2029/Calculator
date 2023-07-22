package com.kunalhk2029.calculator.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.kunalhk2029.calculator.domain.CalculatorAction
import com.kunalhk2029.calculator.domain.ExpressionWriter

class CalculatorViewModel(
    private val writer: ExpressionWriter = ExpressionWriter(),
    private val currentExpressionEvaluationPreviewWriter: ExpressionWriter = ExpressionWriter(),

) : ViewModel() {

    var expression by mutableStateOf("")
    var currentExpressionEvaluationPreview by mutableStateOf("")

    fun onAction(action: CalculatorAction) {
        writer.processAction(action)
        this.expression = writer.expression
        currentExpressionEvaluationPreviewWriter.expression = this.expression
        currentExpressionEvaluationPreviewWriter.processAction(
            CalculatorAction.Calculate
        )
        this.currentExpressionEvaluationPreview =
            currentExpressionEvaluationPreviewWriter.expression
    }
}