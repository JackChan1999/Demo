package com.meizu.cloud.app.block.structitem;

public class TitleItem extends AbsBlockItem {
    public String cur_page;
    public int id;
    public boolean is_uxip_exposured;
    public boolean more;
    public String name;
    public int pos_ver;
    public long profile_id;
    public String type;
    public String url;

    public TitleItem(String name, String type, String url, boolean more, int id) {
        this.name = name;
        this.type = type;
        this.url = url;
        this.more = more;
        this.id = id;
        this.style = 0;
    }
}
