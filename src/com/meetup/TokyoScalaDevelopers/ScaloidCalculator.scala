package com.meetup.TokyoScalaDevelopers.ScaloidCalculator

import com.github.TokyoScalaDevelopers.CalculatorParser.{CalculatorParser,CalculatorEvaluator,Number}

import org.scaloid.common._
import android.graphics.Color

case class CalculatorState(
  whole: Int = 0,
  decimal: Int = 0,
  hasDecimal: Boolean = false
)

object Helpers {
  def stateToString()(implicit _state: CalculatorState): String = {
    if(_state.hasDecimal) {
      s"${_state.whole}.${_state.decimal}"
    } else {
      s"${_state.whole}"
    }
  }
}

class Calculator extends SActivity {
  lazy val resultView = new STextView {
    lines = 1
    maxLines = 4
    text = "0"
  }

  implicit var state = CalculatorState()
  type QueueType = String
  var queue: List[QueueType] = List()

  onCreate {
    contentView = new SVerticalLayout {
      this += resultView
        .textSize(20.dip)

      this += new SLinearLayout {
        this += new SVerticalLayout {
          for(row <- (1 to 9).grouped(3)) {
            this += new SLinearLayout {
              for(num <- row) {
                SButton(s"$num")
                  .<<.fill.Weight(1).>>
                  .onClick( pressedNumber(num) )
              }
            }.<<.Weight(3).>>
          }

          this += new SLinearLayout {
            SButton("0").<<.fill.Weight(1).>>.onClick(pressedNumber(0))
            SButton(".").<<.fill.Weight(2).>>.onClick(pressedDecimal())
          }.<<.Weight(3).>>
        }.<<.fill.Weight(1).>>

        this += new SVerticalLayout {
          SButton("*").<<.Weight(1).>>
          SButton("-").<<.Weight(1).>>
          SButton("+").<<.Weight(1).>>
          SButton("=").<<.Weight(3).>>
        }.<<.fill.Weight(3).>>
      }.<<.fill.>>

    }
  }

// Pressed buttons

  def pressedAC() {
    queue = List()
    resetState()
  }

  def pressedNumber(num: Integer) {
    if(queue.length == 1) { // If there is only one number in the queue,
      queue = List()        // the user wants to start a new calculation
    }

    if(!state.hasDecimal) {
      state = state.copy(whole = state.whole * 10 + num)
    } else {
      state = state.copy(decimal = state.decimal * 10 + num)
    }

    updateDisplay()
  }

  def pressedDecimal() {
    state = state.copy(hasDecimal = true)

    updateDisplay()
  }

// Display-related functions

  def updateDisplay() {
    resultView text (queue.mkString(" ") + " " + Helpers.stateToString())
  }

// Utility functions

  def resetState() {
    state = CalculatorState()
    updateDisplay()
  }

  def appendCurrentNumber() {
    queue = queue :+ Helpers.stateToString()
    resetState()
  }

  def appendOperation(op: String) {
    def _trimOptionalTrailingOp(queue: List[QueueType]): List[QueueType] = {
      val operators = "+-*/"
      val last = queue.last
      if(operators.contains(last)) {
        queue.init
      } else {
        queue
      }
    }

    if(state.whole == 0 && state.decimal == 0) {
      queue = _trimOptionalTrailingOp(queue) :+ op
    } else {
      appendCurrentNumber()
      queue = queue :+ op
    }

    resultView text queue.mkString(" ")
  }
}
