package org.simplesns.simplesns.ui.main.camera.model;

import android.graphics.Bitmap;

public class ImageModifyMenu {
    String text;
    Bitmap symbol;
    int    colorCode;

    public ImageModifyMenu(String text, Bitmap symbol, int colorCode) {
        this.text = text;
        this.symbol = symbol;
        this.colorCode = colorCode;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Bitmap getSymbol() {
        return symbol;
    }

    public void setSymbol(Bitmap symbol) {
        this.symbol = symbol;
    }

    public int getColorCode() {
        return colorCode;
    }

    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }
}
