package com.bts.android.drewsproject;

/**
 * @author ZIaDo on 6/23/18.
 */
class Track {

    private int dir;
    private long id;
    private String name;

    public Track(int dir, long id, String name) {
        this.dir = dir;
        this.id = id;
        this.name = name;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
