package com.aar.app.apptuu.features.categorylist;

public class HeaderItem {
    public String title;
    public HeaderItem(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof HeaderItem && (this == obj || this.title.equals(((HeaderItem) obj).title));
    }
}
