package com.meizu.cloud.app.request.structitem;

public class SpecialConfig {
    public String banner;
    public Colors colors;
    public String description;
    public long id;
    public String logo;
    public String title;
    public int url;

    public static class Colors {
        public int actionbar_color;
        public int bg_color;
        public int btn_color;
        public int btn_text_color;
        public int des_text_color;
        public int line_color;
        public int sb_color;
        public int sb_iconcolor;
        public int sb_unselectedcolor;
        public int statusicon_color;
        public int text_color = -16777216;
        public int title_color;
    }
}
