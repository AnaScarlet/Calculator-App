package edu.towson.cosc431.LAZARENKO.calc
import android.util.Log
import java.math.RoundingMode
import java.security.InvalidKeyException
import java.security.InvalidParameterException
import java.text.DecimalFormat
import kotlin.math.roundToLong

class Data {
    private var numsList:ArrayList<Double> = ArrayList<Double>()
    private var opsList:ArrayList<String> = ArrayList<String>()

    companion object {
        val PLUS: String = "+"
        val MINUS = "-"
        val TIMES = "*"
        val DIVIDE = "/"
    }

    fun addNum(num:String) {
        Log.i("Data in", num)
        val numD = num.toDouble()
        Log.i("Data into list", numD.toString())
        numsList.add(numD)
    }

    fun addOperator(op:String) {
        if( op == PLUS || op == MINUS || op == TIMES || op == DIVIDE)
            opsList.add(op)
    }

    fun remLastOperator() {
        if(opsList.isNotEmpty()) {
            val remStr = opsList.removeAt(opsList.lastIndex)
            Log.i("Data", "An operator was removed")
            if (remStr != "-")
                Log.w("Data", "A non-minus operator was removed!")
        }
        else
            Log.w("Data", "Attempt at operator removal unsuccessful due to empty list")
    }

    fun computeCurrent():Double {
        val numsIterator = numsList.iterator()
        val opsIterator = opsList.iterator()
        var total = 0.0
        while(numsIterator.hasNext() && opsIterator.hasNext()) {
            val num1 = numsIterator.next()
            if (numsIterator.hasNext()) {
                val num2 = numsIterator.next()
                total = when (opsIterator.next()) {
                    PLUS -> num1 + num2
                    MINUS -> num1 - num2
                    TIMES -> num1 * num2
                    DIVIDE -> (num1 / num2)
                    else -> throw InvalidKeyException()
                }
                Log.i("Data", "num1: " + num1.toString() + " num2: " + num2.toString() + " total:" + total.toString())
            }
        }
        return total
    }

    fun clearLists() {
        numsList.clear()
        opsList.clear()
    }
}