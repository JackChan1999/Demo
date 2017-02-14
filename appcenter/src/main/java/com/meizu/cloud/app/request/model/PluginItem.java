package com.meizu.cloud.app.request.model;

import com.meizu.cloud.b.a.e;

public class PluginItem {
    public static int CPD_DATA = 2;
    public static int CPT_DATA = 3;
    public static final int DOWNLOADING = 1;
    public static final int DOWNLOAD_FINISH = 4;
    public static final int INSTALLING = 3;
    public static final int PAUSE = 2;
    public static final int PREPARE_DOWNLOAD = 0;
    public String category_name;
    public int download7day_count;
    public int download_count;
    int drawabeId = e.plugin_appdefault_ic;
    public String evaluate_count;
    public String icon;
    public String id;
    boolean isExpolsure = false;
    public Boolean isFromCache = Boolean.valueOf(false);
    boolean isIconError = false;
    String lastIconUrl = "";
    public String name;
    public String package_name;
    public String position_id;
    public int price;
    float progerss;
    public String publisher;
    public String recommend_desc;
    public String request_id;
    public int size;
    public int star;
    int state;
    public int type;
    public String unit_id;
    public String url;
    public String version;
    public int version_code;
    public String version_name;

    public String getLastIconUrl() {
        return this.lastIconUrl;
    }

    public void setLastIconUrl(String lastIconUrl) {
        this.lastIconUrl = lastIconUrl;
    }

    public boolean isIconError() {
        return this.isIconError;
    }

    public void setIconError(boolean iconError) {
        this.isIconError = iconError;
    }

    public String getCategory_name() {
        return this.category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getDownload7day_count() {
        return this.download7day_count;
    }

    public void setDownload7day_count(int download7day_count) {
        this.download7day_count = download7day_count;
    }

    public int getDownload_count() {
        return this.download_count;
    }

    public void setDownload_count(int download_count) {
        this.download_count = download_count;
    }

    public String getEvaluate_count() {
        return this.evaluate_count;
    }

    public void setEvaluate_count(String evaluate_count) {
        this.evaluate_count = evaluate_count;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackage_name() {
        return this.package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getRecommend_desc() {
        return this.recommend_desc;
    }

    public void setRecommend_desc(String recommend_desc) {
        this.recommend_desc = recommend_desc;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStar() {
        return this.star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getVersion_code() {
        return this.version_code;
    }

    public void setVersion_code(int version_code) {
        this.version_code = version_code;
    }

    public String getVersion_name() {
        return this.version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public int getDrawabeId() {
        return this.drawabeId;
    }

    public void setDrawabeId(int drawabeId) {
        this.drawabeId = drawabeId;
    }

    public float getProgerss() {
        return this.progerss;
    }

    public void setProgerss(float progerss) {
        this.progerss = progerss;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUnit_id() {
        return this.unit_id;
    }

    public void setUnit_id(String unit_id) {
        this.unit_id = unit_id;
    }

    public String getRequest_id() {
        return this.request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getPosition_id() {
        return this.position_id;
    }

    public void setPosition_id(String position_id) {
        this.position_id = position_id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isExpolsure() {
        return this.isExpolsure;
    }

    public void setExpolsure(boolean expolsure) {
        this.isExpolsure = expolsure;
    }
}
