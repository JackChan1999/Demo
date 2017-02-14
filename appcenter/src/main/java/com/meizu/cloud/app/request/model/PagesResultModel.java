package com.meizu.cloud.app.request.model;

import java.util.List;

public class PagesResultModel<T> {
    public String blocks;
    public boolean more;
    public List<T> nav;
}
