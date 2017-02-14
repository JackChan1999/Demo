package com.meizu.cloud.app.request.model;

import com.meizu.cloud.app.request.structitem.AbstractStrcutItem.NotJsonColumn;

public class GameArticleInfo {
    public long ctime;
    public long game_id;
    public long id;
    public String keywords;
    public String preview;
    public String source;
    @NotJsonColumn
    public String thumb_image;
    public String thumb_image_list;
    public String title;
    public String type_tag;
    public String url;
    public long v_cnt;
}
