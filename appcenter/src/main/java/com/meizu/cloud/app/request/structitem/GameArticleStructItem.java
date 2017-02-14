package com.meizu.cloud.app.request.structitem;

import android.os.Parcel;
import com.meizu.cloud.app.request.structitem.AbstractStrcutItem.NotJsonColumn;
import com.meizu.cloud.download.c.a.b;

public class GameArticleStructItem extends AbstractStrcutItem {
    public static final b<GameArticleStructItem> CREATOR = new b<GameArticleStructItem>() {
        public GameArticleStructItem create() {
            return new GameArticleStructItem();
        }
    };
    public long ctime;
    public long game_id;
    public long id;
    public String keywords;
    public String preview;
    public String source;
    @NotJsonColumn
    public String[] thumb_image;
    public String thumb_image_list;
    public String title;
    public String type_tag;
    public long v_cnt;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    public void readFromParcel(Parcel src) {
    }
}
