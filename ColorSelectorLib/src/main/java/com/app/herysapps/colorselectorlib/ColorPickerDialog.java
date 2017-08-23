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
package com.app.herysapps.colorselectorlib;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A minimalist color picker for Android.
 * <p>
 * Created by Hery Lopez on 05/08/2017.
 * <p>
 * Github:
 * <p>
 * Author: <a href="https://github.com/HeryLopez/">https://github.com/HeryLopez</a>
 * <br/>Project:  <a href="https://github.com/HeryLopez/ColorSelector">https://github.com/HeryLopez/ColorSelector</a>
 */
public class ColorPickerDialog extends DialogFragment implements View.OnClickListener {

    // TODO There is an error when: 0) Select a color 1) Open the dialog. 2) While open, change the phone orientation. 3) Select a other color. 4) Reopen the dialog. Error: The indicator shows the color of the step 0.

    private IColorPickerClick mListener;

    private static final String COLOR_SELECTOR_NAME = "COLOR_SELECTOR_NAME";
    private static final String TITRE = "TITRE";
    private static final String FIGURE = "FIGURE";
    private static final String SELECTED_COLOR = "SELECTED_COLOR";
    private static final String COLORS_LIST = "COLORS_LIST";

    public static final int CIRCLE = 1;
    public static final int SQUARE = 2;

    private String mColorSelectorName, mTitre;
    private int mFigure, mBackgroundColor, mIdSelectedColor;
    private List<Integer> mColorList = new ArrayList<>();

    /**
     * A minimalist color picker for Android.
     */
    public ColorPickerDialog() {
        // Default values
        mFigure = CIRCLE;
        mBackgroundColor = R.color.default_background_color;
        mTitre = "";
        mIdSelectedColor = -1;
    }

    /**
     * Set an identifier that allows identifier in ColorPickerClickListener the color selector
     * clicked. (For multiples color selector in screen )
     *
     * @param colorSelectorName identifier of color selector
     */
    public void setColorSelectorName(String colorSelectorName) {
        mColorSelectorName = colorSelectorName;
    }

    /**
     * Set the text for the titre of dialog.
     */
    public void setTitre(String titre) {
        mTitre = titre;
    }

    /**
     * Set the figure for the colors. The default value is {@link #CIRCLE}.
     *
     * @param figure One of {@link #SQUARE} or {@link #CIRCLE}.
     */
    public void setFigure(int figure) {
        mFigure = figure;
    }

    /**
     * Get the color selected.
     *
     * @return Color selected. if there is not a color selected it return -1;
     */
    public int getIdSelectedColor() {

        return mIdSelectedColor;
    }

    /**
     * Set the color selected. Useful for initialize the color in first execution or for restore
     * the value since the instance.
     *
     * @param id Identifier of the color.
     */
    public void setIdSelectedColor(int id) {
        mIdSelectedColor = id;
    }

    /**
     * Set a list of colors IDs for to display.
     * <p>
     * <br/><br/>Example:
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp; List&lt;Integer&gt; colorList = new ArrayList&lt;Integer&gt;();
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp; colorList.add(0, R.color.red);
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp; colorList.add(1, R.color.pink);
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp; colorList.add(2, R.color.purple);
     *
     * @param colorList list of colors in colors.xml
     */
    public void setColorList(List<Integer> colorList) {
        mColorList = colorList;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(COLOR_SELECTOR_NAME, mColorSelectorName);
        outState.putString(TITRE, mTitre);

        outState.putInt(FIGURE, mFigure);
        outState.putInt(SELECTED_COLOR, mIdSelectedColor);

        outState.putIntegerArrayList(COLORS_LIST, (ArrayList<Integer>) mColorList);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Get information the instance if there is.
        if (savedInstanceState != null) {
            mColorSelectorName = savedInstanceState.getString(COLOR_SELECTOR_NAME);
            mTitre = savedInstanceState.getString(TITRE);

            mFigure = savedInstanceState.getInt(FIGURE);
            mIdSelectedColor = savedInstanceState.getInt(SELECTED_COLOR);

            mColorList = savedInstanceState.getIntegerArrayList(COLORS_LIST);
        }

        // Build the AlertDialog
        AlertDialog.Builder builderCurrency = new AlertDialog.Builder(getActivity());
        builderCurrency.setTitle(mTitre);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_color_picker_adapter, null);

        BuildView(view);

        builderCurrency.setView(view);

        AlertDialog alertCurrency = builderCurrency.create();

        alertCurrency.setCanceledOnTouchOutside(true);
        alertCurrency.setCancelable(true);

        return alertCurrency;
    }


    private void BuildView(View view) {

        ((LinearLayout) view.findViewById(R.id.rootLayout)).removeAllViews();
        view.setBackgroundResource(mBackgroundColor);

        LinearLayout.LayoutParams layoutParamsContainer = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (50 * scale + 0.5f);

        LinearLayout.LayoutParams layoutParamsCircles = new LinearLayout.LayoutParams(pixels, pixels);
        int margin = (int) (4 * scale + 0.5f);
        layoutParamsCircles.setMargins(margin, margin, margin, margin);

        int rows = (mColorList.size() / 4);

        if ((mColorList.size() % 4) != 0) {
            rows = rows + 1;
        }

        int index = 0;

        for (int i = 0; i < rows; i++) {
            LinearLayout lineLayout = new LinearLayout(getContext());
            lineLayout.setGravity(Gravity.CENTER);
            lineLayout.setOrientation(LinearLayout.HORIZONTAL);
            lineLayout.setLayoutParams(layoutParamsContainer);

            for (int y = 0; y < 4; y++) {

                ImageButton imageButton = new ImageButton(getContext());
                imageButton.setLayoutParams(layoutParamsCircles);

                if (index < mColorList.size()) {
                    Integer colorID = mColorList.get(index);
                    int colorARGB = ContextCompat.getColor(getContext(), colorID);

                    imageButton.setTag(colorID);
                    imageButton.setOnClickListener(this);

                    // Circle
                    if (mFigure == CIRCLE) {
                        imageButton.setBackgroundResource(R.drawable.circle);
                        ((GradientDrawable) imageButton.getBackground()).setColor(colorARGB);

                    } else { // Square
                        imageButton.setBackgroundColor(ContextCompat.getColor(getContext(), colorID));
                    }

                    if (mIdSelectedColor == colorID) {
                        // Set the text color according to the brightness of the color
                        if (Color.red(colorARGB) + Color.green(colorARGB) + Color.blue(colorARGB) < 384) {
                            imageButton.setImageResource(R.drawable.ic_selected_white);
                        } else {
                            imageButton.setImageResource(R.drawable.ic_selected_black);
                        }
                    }

                } else {
                    imageButton.setBackground(null);
                }

                lineLayout.addView(imageButton);

                index = index + 1;
            }

            ((LinearLayout) view.findViewById(R.id.rootLayout)).addView(lineLayout);
        }

    }


    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    @Override
    public void onClick(View v) {

        int newColorSelected = (int) v.getTag();

        if (newColorSelected != mIdSelectedColor) {
            mIdSelectedColor = newColorSelected;
        } else {
            // Initial color (Not selected)
            mIdSelectedColor = -1;
        }

        NotifyListener();

        this.getDialog().cancel();
    }


    /**
     * It notifies all classes who are listened.
     */
    private void NotifyListener() {
        mListener.ColorSelected(mColorSelectorName, mIdSelectedColor);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof IColorPickerClick) {
            mListener = (IColorPickerClick) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ColorPickerDialog.IColorPickerClick");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Interface for pattern observer
     */
    public interface IColorPickerClick {
        void ColorSelected(String colorSelectorName, int selectedColor);
    }
}