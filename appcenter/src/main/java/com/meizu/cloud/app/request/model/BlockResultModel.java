package com.meizu.cloud.app.request.model;

import java.util.List;

public class BlockResultModel<T> {
    private List<T> data;
    public int id;
    public boolean more;
    private String name;
    public int recom_type;
    public String recom_ver;
    private String type;
    private String url;

    public void setData(List<T> data) {
        this.data = data;
    }

    public List<T> getData() {
        return this.data;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    public boolean getMore() {
        return this.more;
    }

    public void setRecomType(int recomType) {
        this.recom_type = recomType;
    }

    public int getRecomType() {
        return this.recom_type;
    }

    public void setRecomVer(String recomVer) {
        this.recom_ver = recomVer;
    }

    public String getRecomVer() {
        return this.recom_ver;
    }
}
