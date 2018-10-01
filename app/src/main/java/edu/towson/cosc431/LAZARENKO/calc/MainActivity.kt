package edu.towson.cosc431.LAZARENKO.calc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Double

class MainActivity : AppCompatActivity() {

    var myData:Data = Data()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val numListener = NumberButtonListener()
        b_0.setOnClickListener(numListener)
        b_1.setOnClickListener(numListener)
        b_2.setOnClickListener(numListener)
        b_3.setOnClickListener(numListener)
        b_4.setOnClickListener(numListener)
        b_5.setOnClickListener(numListener)
        b_6.setOnClickListener(numListener)
        b_7.setOnClickListener(numListener)
        b_8.setOnClickListener(numListener)
        b_9.setOnClickListener(numListener)
        b_dot.setOnClickListener(numListener)
        b_clr.setOnClickListener(numListener)

        val opListener = operationButtonListener()
        b_add.setOnClickListener(opListener)
        b_sub.setOnClickListener(opListener)
        b_mul.setOnClickListener(opListener)
        b_div.setOnClickListener(opListener)
        b_eq.setOnClickListener(opListener)
    }

    inner class NumberButtonListener: View.OnClickListener {

        override fun onClick(view: View?) {
            if(result.text.toString() != "0") {
                result.text = when (view?.id) {
                    b_0.id -> String.format(resources.getString(R.string.format_zero), result.text)
                    b_1.id -> String.format(resources.getString(R.string.format_one), result.text)
                    b_2.id -> String.format(resources.getString(R.string.format_two), result.text)
                    b_3.id -> String.format(resources.getString(R.string.format_three), result.text)
                    b_4.id -> String.format(resources.getString(R.string.format_four), result.text)
                    b_5.id -> String.format(resources.getString(R.string.format_five), result.text)
                    b_6.id -> String.format(resources.getString(R.string.format_six), result.text)
                    b_7.id -> String.format(resources.getString(R.string.format_seven), result.text)
                    b_8.id -> String.format(resources.getString(R.string.format_eight), result.text)
                    b_9.id -> String.format(resources.getString(R.string.format_nine), result.text)
                    b_dot.id -> String.format(resources.getString(R.string.format_dot_before), result.text)
                    b_clr.id -> resources.getText(R.string.zero)
                    else -> "Invalid number selected"
                }
            }
            else {
               result.text = when (view?.id) {
                    b_0.id -> resources.getText(R.string.zero)
                    b_1.id -> resources.getText(R.string.one)
                    b_2.id -> resources.getText(R.string.two)
                    b_3.id -> resources.getText(R.string.three)
                    b_4.id -> resources.getText(R.string.four)
                    b_5.id -> resources.getText(R.string.five)
                    b_6.id -> resources.getText(R.string.six)
                    b_7.id -> resources.getText(R.string.seven)
                    b_8.id -> resources.getText(R.string.eight)
                    b_9.id -> resources.getText(R.string.nine)
                    b_dot.id -> resources.getText(R.string.dot)
                    b_clr.id -> resources.getText(R.string.zero)
                    else -> "Invalid number selected"
                }
            }
        }
    }

    inner class operationButtonListener: View.OnClickListener {

        override fun onClick(view: View?) {
            when (view?.id) {
                b_add.id ->  {
                    myData.addOperator(Data.PLUS)
                    doNum(false)
                    result.text = String.format(resources.getString(R.string.format_add), result.text)
                }
                b_sub.id -> {
                    myData.addOperator(Data.PLUS)  // subtraction is taken care of by the negative sign attached to a number
                    doNum(true)
                    result.text = String.format(resources.getString(R.string.format_sub), result.text)
                }
                b_mul.id -> {
                    myData.addOperator(Data.TIMES)
                    doNum(false)
                    result.text = String.format(resources.getString(R.string.format_mul), result.text)
                }
                b_div.id -> {
                    myData.addOperator(Data.DIVIDE)
                    doNum(false)
                    result.text = String.format(resources.getString(R.string.format_div), result.text)
                }
                b_eq.id -> {
                    try {
                        doNum(false)
                        val total = myData.computeCurrent()
                        var totalStr = total.toString()
                        Log.i("Total", totalStr)
                        val pInteger = Regex("""\d*\.0*""")
                        val pNegInteger = Regex("-\\d*\\.0*")
                        if (pInteger.matches(totalStr) || pNegInteger.matches(totalStr))
                            result.text = total.toInt().toString()
                        else
                            result.text = totalStr
                        myData.clearLists()
                        //myData.addNum(total.toString())
                    } catch (e:Exception) {
                        result.text = "The key was invalid..."
                        for (strE in e.stackTrace)
                            Log.e("Exception stack strace", strE.toString())
                    }
                }
                else -> result.text = "Invalid operation selected"      // should never happen
            }
        }

        private fun doNum(isSub:Boolean) {
            val strNum = result.text.toString()
            Log.i("Result", strNum)

            val decimalPattern = Regex("(\\d*\\.\\d*)$")
            val decimalStr = decimalPattern.find(strNum)

            val intPattern = Regex("(\\d*)$")
            val intStr = intPattern.find(strNum)

            val negDecimalPattern = Regex("(-\\d*\\.\\d*)$")
            val negDecimalStr = negDecimalPattern.find(strNum)

            val negIntPattern = Regex("(-\\d*)$")
            val negIntStr = negIntPattern.find(strNum)

            var lastNum = "0"
            try {
                if (isSub) {
                    if(strNum == "0") {  // when user is trying to enter a negative number first
                        Log.w("OPERATOR", "Parsing the first number as a negative number")
                        myData.remLastOperator()
                        return
                    }
                }
                if (negDecimalStr != null) {
                    lastNum = negDecimalStr.value
                    Log.i("INFO lastNum", lastNum)
                    if (lastNum != "")
                        myData.addNum(lastNum)
                    else
                        myData.remLastOperator()
                        Log.w("OPERATOR", "Last thing selected was an operator")
                } else if (negIntStr != null) {
                    lastNum = negIntStr.value
                    Log.i("INFO lastNum", lastNum)
                    if (lastNum != "")
                        myData.addNum(lastNum)
                    else
                        myData.remLastOperator()
                        Log.w("OPERATOR", "Last thing selected was an operator")
                } else if (decimalStr != null) {
                    lastNum = decimalStr.value
                    Log.i("INFO lastNum", lastNum)
                    if (lastNum != "")
                        myData.addNum(lastNum)
                    else
                        Log.w("OPERATOR", "Last thing selected was an operator")
                } else if (intStr != null) {
                    lastNum = intStr.value
                    Log.i("INFO lastNum", lastNum)
                    if (lastNum != "")
                        myData.addNum(lastNum)
                    else
                        Log.w("OPERATOR", "Last thing selected was an operator")
                } else
                    Log.w("OPERATOR", "This doesn't happen...")

            } catch (e:Exception) {
                for (strE in e.stackTrace)
                    Log.e("Exception stack strace", strE.toString())
                result.text = "Input parsing error :("
                return
            }

        }

    }

}
