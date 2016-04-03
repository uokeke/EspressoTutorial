package com.espressotutorial.note;

/**
 * Created by Uche on 2016-04-03.
 */
public class Note {
    private String text;
    private long id;

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
