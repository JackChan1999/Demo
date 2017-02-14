package com.meizu.cloud.app.downlad;

import com.meizu.cloud.app.b.a.a;
import com.meizu.cloud.download.b.e;
import com.meizu.cloud.download.service.DownloadService;
import com.meizu.cloud.download.service.DownloadTaskInfo;
import com.meizu.cloud.download.service.j;

public class b extends j {
    public b(DownloadService service) {
        super(service);
    }

    protected e a(DownloadTaskInfo info) {
        return new a(this.a.getApplicationContext(), info);
    }
}
