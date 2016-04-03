package com.espressotutorial.ui.list;

/**
 * Created by Uche on 2016-04-03.
 */
class ListItem {
    private String text;
    private long id;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return getText();
    }
}
