package com.meizu.cloud.app.request.model;

import com.meizu.cloud.app.request.structitem.GameArticleStructItem;
import java.util.List;

public class GameArticleModel {
    public List<GameArticleStructItem> list;
    public boolean more;
    public List<GameArticleTag> tags;
}
