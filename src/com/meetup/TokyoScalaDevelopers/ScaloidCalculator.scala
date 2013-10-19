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
              .onClick(toast(s"Clicked $num"))
          }
        }.<<.Weight(3).>>
      }

      this += new SLinearLayout {
        SButton("0").<<.Weight(2).>>
        SButton(".").<<.Weight(1).>>
      }.<<.Weight(3).>>
    }
  }
}
