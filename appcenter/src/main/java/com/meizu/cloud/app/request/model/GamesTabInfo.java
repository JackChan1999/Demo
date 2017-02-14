package com.meizu.cloud.app.request.model;

import java.util.List;

public class GamesTabInfo {
    public List<HotGame> hotGames;
    public long id;
    public String name;

    public class HotGame {
        public String appName;
        public int appid;
        public long categoryId;
        public long id;
    }
}
