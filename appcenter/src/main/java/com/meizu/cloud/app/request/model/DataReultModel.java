package com.meizu.cloud.app.request.model;

import java.util.List;

public class DataReultModel<T> {
    public List<T> data;
    public boolean more;
    public int recom_type;
    public String recom_ver;
}
