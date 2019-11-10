package com.app.herysapps.colorselector

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable

import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

import com.app.herysapps.colorselectorlib.ColorSelectorDialog
import com.app.herysapps.colorselectorlib.ColorSelectorDialogBuilder
import com.app.herysapps.colorselectorlib.FigureType
import kotlinx.android.synthetic.main.activity_example.*

import java.util.ArrayList

class ExampleActivity : AppCompatActivity(), ColorSelectorDialog.OnDialogColorClickListener {
    private lateinit var colorSelectorDialog1: ColorSelectorDialog
    private lateinit var colorSelectorDialog2: ColorSelectorDialog
    private val COLOR_SELECTOR_01 = "colorSelector01"
    private val COLOR_SELECTOR_02 = "colorSelector02"
    private val COLOR_01 = "COLOR_01"
    private val COLOR_02 = "COLOR_02"
    private val DIALOG_01_IS_VISIBLE = "DIALOG_01_IS_VISIBLE"
    private val DIALOG_02_IS_VISIBLE = "DIALOG_02_IS_VISIBLE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        val builder = ColorSelectorDialogBuilder()
        colorSelectorDialog1 = builder.setOnDialogColorClickListener(this)
                .setColorList(getColorsList())
                .setSelectedColor(R.color.light_green)
                .setFigureType(FigureType.CIRCLE)
                .build()

        colorSelected01.setBackgroundColor(ContextCompat.getColor(this, R.color.light_green))

        val builder2 = ColorSelectorDialogBuilder()
        colorSelectorDialog2 = builder2.setOnDialogColorClickListener(this)
                .setColorList(getColorsList(true))
                .setFigureType(FigureType.SQUARE)
                .build()


        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(COLOR_01)) {
                colorSelectorDialog1.selectedColor = savedInstanceState.getInt(COLOR_01)
                colorSelectorDialog1.selectedColor?.let {
                    colorSelected01.setBackgroundColor(ContextCompat.getColor(this, it))
                }
            }
            if (savedInstanceState.containsKey(DIALOG_01_IS_VISIBLE) && savedInstanceState.getBoolean(DIALOG_01_IS_VISIBLE)) {
                showDialog(colorSelectorDialog1, COLOR_SELECTOR_01)
            }

            if (savedInstanceState.containsKey(COLOR_02)) {
                colorSelectorDialog2.selectedColor = savedInstanceState.getInt(COLOR_02)
                colorSelectorDialog2.selectedColor?.let {
                    colorSelected02.setBackgroundColor(ContextCompat.getColor(this, it))
                }
            }

            if (savedInstanceState.containsKey(DIALOG_02_IS_VISIBLE) && savedInstanceState.getBoolean(DIALOG_02_IS_VISIBLE)) {
                showDialog(colorSelectorDialog2, COLOR_SELECTOR_02)
            }
        }

        buttonSelector01.setOnClickListener { showDialog(colorSelectorDialog1, COLOR_SELECTOR_01) }
        buttonSelector02.setOnClickListener { showDialog(colorSelectorDialog2, COLOR_SELECTOR_02) }
    }

    private fun getColorsList(useAll: Boolean = false) : List<Int>{
        val colorList = ArrayList<Int>()
        colorList.add(0, R.color.red)
        colorList.add(1, R.color.pink)
        colorList.add(2, R.color.purple)
        colorList.add(3, R.color.deep_purple)
        colorList.add(4, R.color.indigo)
        colorList.add(5, R.color.blue)
        colorList.add(6, R.color.light_blue)
        colorList.add(7, R.color.cyan)
        colorList.add(8, R.color.teal)
        colorList.add(9, R.color.green)
        colorList.add(10, R.color.light_green)
        colorList.add(11, R.color.lime)

        if(useAll){
            colorList.add(12, R.color.yellow)
            colorList.add(13, R.color.amber)
            colorList.add(14, R.color.orange)
            colorList.add(15, R.color.deep_orange)
            colorList.add(16, R.color.grey)
            colorList.add(17, R.color.blue_grey)
            colorList.add(18, R.color.brown)
            colorList.add(19, R.color.black)
        }

        return colorList
    }

    private fun showDialog(dialog: ColorSelectorDialog, tag: String) {
        dialog.show(supportFragmentManager, tag)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (colorSelectorDialog1.selectedColor != null)
            outState.putInt(COLOR_01, colorSelectorDialog1.selectedColor!!)

        if (colorSelectorDialog2.selectedColor != null)
            outState.putInt(COLOR_02, colorSelectorDialog2.selectedColor!!)

        outState.putBoolean(DIALOG_01_IS_VISIBLE, colorSelectorDialog1.isDialogVisible)
        outState.putBoolean(DIALOG_02_IS_VISIBLE, colorSelectorDialog2.isDialogVisible)

        super.onSaveInstanceState(outState)
    }

    override fun onColorClick(tagDialog: String, selectedColor: Int?) {
        if (tagDialog == COLOR_SELECTOR_01) {
            if (selectedColor != null) {
                colorSelected01.setBackgroundColor(ContextCompat.getColor(this, colorSelectorDialog1.selectedColor!!))
            } else {
                colorSelected01.background = null
            }
        }

        if (tagDialog == COLOR_SELECTOR_02) {
            if (selectedColor != null) {
                colorSelected02.setBackgroundColor(ContextCompat.getColor(this, selectedColor))
            } else {
                colorSelected02.background = null
            }
        }
    }
}