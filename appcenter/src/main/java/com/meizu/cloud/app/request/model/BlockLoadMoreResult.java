package com.meizu.cloud.app.request.model;

import com.meizu.cloud.app.block.requestitem.SubpagePageConfigsInfo;
import java.util.ArrayList;
import java.util.List;

public class BlockLoadMoreResult<T> {
    public List<T> mData = new ArrayList();
    public String mErrorMsg;
    public boolean mNeedLoadMore = false;
    public String mNextUrl;
    public int mResultCount = 0;
    public SubpagePageConfigsInfo mSubpageConfigInfo;
    public int mTotalCount = 0;
}
