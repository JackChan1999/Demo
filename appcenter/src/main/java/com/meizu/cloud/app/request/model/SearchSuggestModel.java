package com.meizu.cloud.app.request.model;

import java.util.List;

public class SearchSuggestModel<T> {
    public List<T> apps;
    public List<T> games;
    public List<String> words;
}
