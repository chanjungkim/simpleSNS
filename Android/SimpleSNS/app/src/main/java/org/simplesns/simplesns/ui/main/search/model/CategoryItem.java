package org.simplesns.simplesns.ui.main.search.model;

import android.graphics.drawable.Drawable;

public class CategoryItem {
    String name;
    String background_url;
    boolean isSelected;
    // 임시
    Drawable background;
    public CategoryItem(String name, String background_url) {
        this.name = name;
        this.background_url = background_url;
        this.isSelected = isSelected;
    }

    // 임시
    public CategoryItem(String name, Drawable drawable, boolean isSelected){
        this.name = name;
        this.background = drawable;
        this.isSelected = isSelected;
    }


    public String getName() {
        return name;
    }

    public String getBackground_url() {
        return background_url;
    }

    public Drawable getBackground() {
        return background;
    }

    public void setBackground(Drawable background) {
        this.background = background;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
