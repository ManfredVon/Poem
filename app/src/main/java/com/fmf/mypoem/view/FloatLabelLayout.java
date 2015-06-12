package com.fmf.mypoem.view;

/*
 * Copyright (C) 2014 Chris Banes
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

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fmf.mypoem.R;
import com.fmf.mypoem.poem.PoemLog;

/**
 * Layout which an {@link android.widget.EditText} to show a floating label when the hint is hidden
 * due to the user inputting text.
 *
 * @see <a href="https://dribbble.com/shots/1254439--GIF-Mobile-Form-Interaction">Matt D. Smith on Dribble</a>
 * @see <a href="http://bradfrostweb.com/blog/post/float-label-pattern/">Brad Frost's blog post</a>
 */
public final class FloatLabelLayout extends LinearLayout {
    private static final String LF = "\n";
    private static final String LF_REPLACEMENT = "↲";

    private static final long ANIMATION_DURATION = 150;

    // private static final float DEFAULT_PADDING_LEFT_RIGHT_DP = 12f;
    private static final float DEFAULT_PADDING_LEFT_RIGHT_DP = 0f;

    private static final int HORIZONTAL_WEIGHT_LABEL = 1;
    private static final int HORIZONTAL_WEIGHT_EDITTEXT = 2;

    private boolean isSet = false;

    private EditText mEditText;
    private TextView mLabel;

    public FloatLabelLayout(Context context) {
        this(context, null);
    }

    public FloatLabelLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatLabelLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);


        final TypedArray a = context
                .obtainStyledAttributes(attrs, R.styleable.FloatLabelLayout);

        final int sidePadding = a.getDimensionPixelSize(
                R.styleable.FloatLabelLayout_floatLabelSidePadding,
                dipsToPix(DEFAULT_PADDING_LEFT_RIGHT_DP));
        mLabel = new TextView(context);
        mLabel.setPadding(sidePadding, 0, sidePadding, 0);
//        mLabel.setVisibility(INVISIBLE);
        mLabel.setVisibility(GONE);

        mLabel.setTextAppearance(context,
                a.getResourceId(R.styleable.FloatLabelLayout_floatLabelTextAppearance,
                        android.R.style.TextAppearance_Small));

//        addView(mLabel, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        // LinearLayout默认Orientation是HORIZONTAL，即0
        if (HORIZONTAL == getOrientation()) {
            params.gravity = Gravity.CENTER_VERTICAL;
            params.width = 0;
            params.weight = HORIZONTAL_WEIGHT_LABEL;
        } else {

        }
        addView(mLabel, params);

        a.recycle();
    }

    @Override
    public final void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof EditText) {
            // If we already have an EditText, throw an exception
            if (mEditText != null) {
                throw new IllegalArgumentException("We already have an EditText, can only have one");
            }

            // Update the layout params so that the EditText is at the bottom, with enough top
            // margin to show the label
//            final LayoutParams lp = new LayoutParams(params);
//            lp.gravity = Gravity.BOTTOM;
//            lp.topMargin = (int) mLabel.getTextSize();
//            params = lp;

            if (HORIZONTAL == getOrientation()) {
                final LayoutParams lp = new LinearLayout.LayoutParams(params);
                lp.width = 0; // 使用weight
                lp.weight = HORIZONTAL_WEIGHT_EDITTEXT;
                params = lp;
            }

            setEditText((EditText) child);
        }

        // Carry on adding the View...
        super.addView(child, index, params);
    }

    private void setEditText(EditText editText) {
        mEditText = editText;

        // 当失去焦点时，重设hint：setHint(mEditText.getTag());
        // 当mEditText的hint动态改变时，tag无法改变，getTag取到的时最初的hint
//        mEditText.setTag(mEditText.getHint());
        /*
        // Add a TextWatcher so that we know when the text input has changed
        mEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
//                PoemLog.i(this, "afterTextChanged: s="+s);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                PoemLog.i(this, "beforeTextChanged: s=" + s + ", start=" + start + ", count=" + count + ", after=" + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                PoemLog.i(this, "onTextChanged: s=" + s + ", start=" + start+ ", before=" + before + ", count=" + count);
            }
        });
        */

        // Add focus listener to the EditText so that we can notify the label that it is activated.
        // Allows the use of a ColorStateList for the text color on the label
        mEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if (isSet) {
                    mLabel.setActivated(focused);
                    if (focused) {
                        showLabel();

                        mEditText.setTag(mEditText.getHint());
                        mEditText.setHint("");
                    } else {
                        hideLabel();

                        mEditText.setHint((CharSequence) mEditText.getTag());
                    }
                }
            }
        });

    }


    /**
     * @return the {@link android.widget.EditText} text input
     */
    public EditText getEditText() {
        return mEditText;
    }

    /**
     * @return the {@link android.widget.TextView} label
     */
    public TextView getLabel() {
        return mLabel;
    }

    /**
     * Show the label using an animation
     */
    private void showLabel() {
        if (mLabel.getVisibility() != View.VISIBLE) {
            mLabel.setVisibility(View.VISIBLE);
            mLabel.setAlpha(0f);
            mLabel.setTranslationY(mLabel.getHeight());
            mLabel.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(ANIMATION_DURATION)
                    .setListener(null).start();
        }
    }

    /**
     * Hide the label using an animation
     */
    private void hideLabel() {
        if (mLabel.getVisibility() == View.VISIBLE) {
            mLabel.setAlpha(1f);
            mLabel.setTranslationY(0f);
            mLabel.animate()
                    .alpha(0f)
                    .translationY(mLabel.getHeight())
                    .setDuration(ANIMATION_DURATION)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLabel.setVisibility(View.GONE);
                        }
                    }).start();
        }
    }

    /**
     * Helper method to convert dips to pixels.
     */
    private int dipsToPix(float dps) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dps,
                getResources().getDisplayMetrics());
    }

    private CharSequence formatHint(CharSequence hint) {
        return hint.toString().replaceAll(LF, LF_REPLACEMENT);
    }

    public void setTextAndHint(CharSequence text) {
        isSet = true;

        CharSequence hint = text;
        if (VERTICAL == getOrientation()) {
            hint = formatHint(hint);
        }
        mLabel.setText(hint);

        final boolean isEdittextFocused = mEditText.isFocused();
        if ((mLabel.getVisibility() == View.VISIBLE) || isEdittextFocused) {
            mEditText.setTag(text);
            mEditText.setHint("");

            if(isEdittextFocused){
                showLabel();

                mLabel.setActivated(true);
            }
        } else {
            mEditText.setHint(text);
        }

    }

}