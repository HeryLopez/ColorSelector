/*
 * Copyright (C) 2017 Hery Lopez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.app.herysapps.colorselector;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.app.herysapps.colorselectorlib.ColorPickerDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Examples of use.
 * <p>
 * Created by Hery Lopez on 05/08/2017.
 * <p>
 * Github:
 * Author: https://github.com/HeryLopez
 * Project: https://github.com/HeryLopez/ColorSelector
 */
public class ExampleActivity extends AppCompatActivity implements ColorPickerDialog.IColorPickerClick {

    // Color selector
    private ColorPickerDialog colorPickerDialog01;
    private ColorPickerDialog colorPickerDialog02;

    // Identifier for de color selector
    private String COLOR_SELECTOR_01 = "colorSelector01";
    private String COLOR_SELECTOR_02 = "colorSelector02";

    private String COLOR_01 = "COLOR_01";
    private String COLOR_02 = "COLOR_02";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        // -----------------------------------------------------------------------------------------
        // Initialize the first ColorSelector dialog
        // -----------------------------------------------------------------------------------------
        List<Integer> colorList01 = new ArrayList<Integer>();
        colorList01.add(0, R.color.red);
        colorList01.add(1, R.color.pink);
        colorList01.add(2, R.color.purple);
        colorList01.add(3, R.color.deep_purple);
        colorList01.add(4, R.color.indigo);
        colorList01.add(5, R.color.blue);
        colorList01.add(6, R.color.light_blue);
        colorList01.add(7, R.color.cyan);
        colorList01.add(8, R.color.teal);
        colorList01.add(9, R.color.green);
        colorList01.add(10, R.color.light_green);
        colorList01.add(11, R.color.lime);
        colorList01.add(12, R.color.yellow);
        colorList01.add(13, R.color.amber);
        colorList01.add(14, R.color.orange);
        colorList01.add(15, R.color.deep_orange);
        colorList01.add(16, R.color.grey);
        colorList01.add(17, R.color.blue_grey);
        colorList01.add(18, R.color.brown);
        colorList01.add(19, R.color.black);

        colorPickerDialog01 = new ColorPickerDialog();
        colorPickerDialog01.setColorSelectorName(COLOR_SELECTOR_01);
        colorPickerDialog01.setTitre("Colors");
        colorPickerDialog01.setColorList(colorList01);
        colorPickerDialog01.setFigure(ColorPickerDialog.SQUARE);

        // Restore color select from saved state
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(COLOR_01)) {
                colorPickerDialog01.setIdSelectedColor(savedInstanceState.getInt(COLOR_01));
            }
        } else {
            colorPickerDialog01.setIdSelectedColor(R.color.light_green);
        }
        (findViewById(R.id.colorSelected01)).setBackgroundColor(ContextCompat.getColor(this, colorPickerDialog01.getIdSelectedColor()));


        // -----------------------------------------------------------------------------------------
        // Initialize the second ColorSelector dialog
        // -----------------------------------------------------------------------------------------
        List<Integer> colorList02 = new ArrayList<Integer>();
        colorList02.add(R.color.red);
        colorList02.add(R.color.pink);
        colorList02.add(R.color.purple);
        colorList02.add(R.color.deep_purple);
        colorList02.add(R.color.indigo);
        colorList02.add(R.color.blue);
        colorList02.add(R.color.light_blue);
        colorList02.add(R.color.cyan);
        colorList02.add(R.color.teal);
        colorList02.add(R.color.green);
        colorList02.add(R.color.light_green);
        colorList02.add(R.color.lime);
        colorList02.add(R.color.yellow);
        colorList02.add(R.color.amber);
        colorList02.add(R.color.orange);

        colorPickerDialog02 = new ColorPickerDialog();
        colorPickerDialog02.setColorSelectorName(COLOR_SELECTOR_02);
        colorPickerDialog02.setColorList(colorList02);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(COLOR_02)) {
                colorPickerDialog02.setIdSelectedColor(savedInstanceState.getInt(COLOR_02));
                (findViewById(R.id.colorSelected02)).setBackgroundColor(ContextCompat.getColor(this, colorPickerDialog02.getIdSelectedColor()));
            }
        }

        // -----------------------------------------------------------------------------------------
        // Buttons
        // -----------------------------------------------------------------------------------------
        (findViewById(R.id.buttonSelector01)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorPickerDialog01.show(getSupportFragmentManager(), "colorPicker");
            }
        });

        (findViewById(R.id.buttonSelector02)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorPickerDialog02.show(getSupportFragmentManager(), "colorPicker");
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (colorPickerDialog01.getIdSelectedColor() != -1)
            outState.putInt(COLOR_01, colorPickerDialog01.getIdSelectedColor());

        if (colorPickerDialog02.getIdSelectedColor() != -1)
            outState.putInt(COLOR_02, colorPickerDialog02.getIdSelectedColor());

        super.onSaveInstanceState(outState);
    }

    @Override
    public void ColorSelected(String colorSelectorName, int color) {

        if (colorSelectorName.equals(COLOR_SELECTOR_01)) {
            if (color != -1) {
                (findViewById(R.id.colorSelected01)).setBackgroundColor(ContextCompat.getColor(this, color));
                //(findViewById(R.id.colorSelected01)).setTag(color);
            } else {
                (findViewById(R.id.colorSelected01)).setBackground(null);
            }
        }

        if (colorSelectorName.equals(COLOR_SELECTOR_02)) {
            if (color != -1) {
                (findViewById(R.id.colorSelected02)).setBackgroundColor(ContextCompat.getColor(this, color));
                //(findViewById(R.id.colorSelected02)).setTag(color);
            } else {
                (findViewById(R.id.colorSelected02)).setBackground(null);
            }
        }
    }
}
