package com.meetup.TokyoScalaDevelopers.ScaloidCalculator

import org.scaloid.common._
import android.graphics.Color

class Calculator extends SActivity {
  lazy val resultView = new STextView {
    lines = 1
    text = "0"
  }

  onCreate {
    contentView = new SVerticalLayout {
      this += resultView.<<.Weight(4).>>

      for(row <- (1 to 9).grouped(3)) {
        this += new SLinearLayout {
          for(num <- row) {
            SButton(s"$num")
              .<<.Weight(1).>>
              .onClick( pressedNumber(num) )
          }
        }.<<.Weight(3).>>
      }

      this += new SLinearLayout {
        SButton("0").<<.Weight(1).>>.onClick(pressedNumber(0))
        SButton(".").<<.Weight(2).>>.onClick(pressedDecimal())
      }.<<.Weight(3).>>
    }
  }

  def pressedNumber(num: Integer) {
    toast(s"Pressed $num")
  }

  def pressedDecimal() {
    toast("Pressed decimal")
  }
}
