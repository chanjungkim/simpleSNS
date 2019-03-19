package org.simplesns.simplesns.ui.main.camera.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.simplesns.simplesns.R;

public class ImageModifyView extends LinearLayout {
    LinearLayout bg;
    ImageView symbol;
    TextView text;

    public ImageModifyView(Context context) {
        super(context);
        initView();
    }

    public ImageModifyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public ImageModifyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        getAttrs(attrs, defStyleAttr);
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.image_modify_view, this, false);
        addView(view);

        bg = findViewById(R.id.bg);
        symbol = findViewById(R.id.symbol);
        text = findViewById(R.id.text);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ImageModifyView);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ImageModifyView, defStyle, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray) {
        int bg_resID = typedArray.getResourceId(R.styleable.ImageModifyView_bg, R.drawable.round_blue);
        bg.setBackgroundResource(bg_resID);

        int symbol_resID = typedArray.getResourceId(R.styleable.ImageModifyView_symbol, R.drawable.lena);
        symbol.setImageResource(symbol_resID);

        String text_string = typedArray.getString(R.styleable.ImageModifyView_text);
        text.setText(text_string);

        typedArray.recycle();
    }

    public void setBg(int bg_resID) {
        bg.setBackgroundResource(bg_resID);
    }

    public void setSymbol(int symbol_resID) {
        symbol.setImageResource(symbol_resID);
    }

    public void setText(String text_string) {
        text.setText(text_string);
    }
}
