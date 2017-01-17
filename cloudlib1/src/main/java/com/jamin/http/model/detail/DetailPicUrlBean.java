package com.jamin.http.model.detail;

/**
 * Created by alvin on 17/1/17.
 */

public  class DetailPicUrlBean {
    /**
     * pic_title : 儒略历
     * id : 1
     * url : http://images.juheapi.com/history/1_1.jpg
     */

    private String pic_title;
    private int id;
    private String url;

    public String getPic_title() {
        return pic_title;
    }

    public void setPic_title(String pic_title) {
        this.pic_title = pic_title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
