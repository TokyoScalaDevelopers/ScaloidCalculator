package com.meetup.TokyoScalaDevelopers.ScaloidCalculator

import org.scaloid.common._
import android.graphics.Color

class Calculator extends SActivity {
  onCreate {
    contentView = new SVerticalLayout {

      for(row <- (1 to 9).grouped(3)) {
        this += new SLinearLayout {
          for(num <- row) {
            SButton(s"$num")
              .<<.Weight(1).>>
              .onClick(toast(s"Clicked $num"))
          }
        }
      }

    }
  }
}
