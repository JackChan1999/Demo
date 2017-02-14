package com.meizu.cloud.app.block.structitem;

public class IconTitleItem extends AbsBlockItem {
    public int id;
    public boolean more;
    public String name;
    public String title_color;
    public String title_img;
    public String type;

    public IconTitleItem(int id, boolean more, String title_color, String title_img, String type, String name) {
        this.id = id;
        this.more = more;
        this.title_color = title_color;
        this.title_img = title_img;
        this.type = type;
        this.name = name;
        this.style = 15;
    }
}
