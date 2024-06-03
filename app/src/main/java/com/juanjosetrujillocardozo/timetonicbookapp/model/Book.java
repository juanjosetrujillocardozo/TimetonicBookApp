package com.juanjosetrujillocardozo.timetonicbookapp.model;

public class Book {
    private String name;
    private OwnerPrefs ownerPrefs;

    public String getName() {
        return name;
    }

    public String getCoverImageUrl() {
        return "https://timetonic.com/live/dbi/in/tb/" + ownerPrefs.getOCoverImg();
    }

    class OwnerPrefs {
        private String oCoverImg;

        public String getOCoverImg() {
            return oCoverImg;
        }
    }
}
