package com.meizu.cloud.app.block.structitem;

public class SubpageTitleItem extends AbsBlockItem {
    public int id;
    public String name;
    public String recommend_desc;
    public String title_color;
    public String type;

    public SubpageTitleItem(int id, String title_color, String type, String name, String recommend_desc) {
        this.id = id;
        this.title_color = title_color;
        this.type = type;
        this.name = name;
        this.recommend_desc = recommend_desc;
        this.style = 23;
    }
}
