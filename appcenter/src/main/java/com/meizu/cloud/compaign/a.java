package com.meizu.cloud.compaign;

import android.content.Context;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.downlad.d;
import com.meizu.cloud.app.downlad.f.e;
import com.meizu.cloud.app.downlad.f.f;
import com.meizu.cloud.app.downlad.f.i;
import com.meizu.cloud.app.downlad.f.j;
import com.meizu.cloud.compaign.task.BaseTask;
import com.meizu.cloud.compaign.task.ShareTask;
import com.meizu.cloud.compaign.task.app.BaseAppTaskInfo;
import com.meizu.cloud.compaign.task.app.DownloadTask;
import com.meizu.cloud.compaign.task.app.LaunchTask;
import com.meizu.cloud.compaign.task.app.PayTask;
import com.meizu.cloud.compaign.task.app.ReviewTask;
import java.util.ArrayList;
import java.util.List;
import meizu.sdk.compaign.b;
import meizu.sdk.compaign.c;

public class a {
    private static a h;
    private Context a;
    private List<DownloadTask> b;
    private List<ReviewTask> c;
    private List<LaunchTask> d;
    private PayTask e;
    private ShareTask f;
    private c g = new c(this.a);

    private a(Context context) {
        this.a = context.getApplicationContext();
    }

    public static a a(Context context) {
        if (h == null) {
            h = new a(context);
        }
        return h;
    }

    public synchronized BaseTask a(b compaignTask, boolean cache) {
        BaseTask a;
        if (compaignTask != null) {
            long compaignId = compaignTask.getCompaignId();
            long taskId = compaignTask.getTaskId();
            String taskType = compaignTask.getTaskType();
            String taskData = compaignTask.getTaskData();
            if ("download".equals(taskType)) {
                DownloadTask downloadTask = new DownloadTask(compaignId, taskId, taskType, taskData);
                if (downloadTask.isReconizable()) {
                    a = a(downloadTask);
                }
            } else if ("pay".equals(taskType)) {
                PayTask payTask = new PayTask(compaignId, taskId, taskType, taskData);
                if (payTask.isReconizable()) {
                    a = a(payTask);
                }
            } else if ("share".equals(taskType)) {
                ShareTask shareTask = new ShareTask(compaignId, taskId, taskType, taskData);
                if (shareTask.isReconizable()) {
                    a = a(shareTask);
                }
            } else if ("review".equals(taskType)) {
                ReviewTask reviewTask = new ReviewTask(compaignId, taskId, taskType, taskData);
                if (reviewTask.isReconizable()) {
                    a = a(reviewTask);
                }
            } else if ("launch".equals(taskType)) {
                LaunchTask launchTask = new LaunchTask(compaignId, taskId, taskType, taskData);
                if (launchTask.isReconizable()) {
                    a = a(launchTask);
                }
            }
        }
        a = null;
        return a;
    }

    private ReviewTask a(ReviewTask newReviewTask) {
        if (this.c == null) {
            this.c = new ArrayList();
        } else {
            for (ReviewTask reviewTask : this.c) {
                if (reviewTask.isSimilar(newReviewTask)) {
                    return reviewTask;
                }
            }
        }
        this.c.add(newReviewTask);
        return newReviewTask;
    }

    private LaunchTask a(LaunchTask newTask) {
        if (this.d == null) {
            this.d = new ArrayList();
        } else {
            for (LaunchTask launchTask : this.d) {
                if (launchTask.isSimilar(newTask)) {
                    return launchTask;
                }
            }
        }
        this.d.add(newTask);
        return newTask;
    }

    private DownloadTask a(DownloadTask newTask) {
        if (x.d(this.a).a(newTask.getPkgName())) {
            a((BaseTask) newTask);
            return newTask;
        }
        DownloadTask existTask = null;
        if (this.b != null) {
            for (DownloadTask task : this.b) {
                if (newTask.isSimilar(task)) {
                    existTask = task;
                    break;
                }
            }
        }
        this.b = new ArrayList();
        if (existTask != null) {
            return existTask;
        }
        newTask.setStateListener(b());
        d.a(this.a).a(newTask.getStateListener());
        this.b.add(newTask);
        return newTask;
    }

    private e b() {
        return new e(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }

            public void onInstallStateChange(com.meizu.cloud.app.downlad.e wrapper) {
                if (wrapper.f() == f.INSTALL_SUCCESS && this.a.b != null) {
                    for (BaseTask downloadTask : this.a.b) {
                        if (((BaseAppTaskInfo) downloadTask.getTaskInfo()).appId == wrapper.i()) {
                            this.a.a(downloadTask);
                            return;
                        }
                    }
                }
            }
        };
    }

    private PayTask a(PayTask newTask) {
        if (this.e != null) {
            d.a(this.a).b(this.e.getStateListener());
        }
        this.e = newTask;
        this.e.setStateListener(c());
        d.a(this.a).a(this.e.getStateListener());
        return this.e;
    }

    private ShareTask a(ShareTask newTask) {
        this.f = newTask;
        return this.f;
    }

    private i c() {
        return new i(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }

            public void a(com.meizu.cloud.app.downlad.e wrapper) {
                if (wrapper.f() == j.SUCCESS && this.a.e != null && this.a.e.getPkgName().equals(wrapper.g())) {
                    this.a.a(this.a.e);
                    this.a.e = null;
                }
            }
        };
    }

    public synchronized void a(BaseTask task) {
        if (task != null) {
            this.g.a(task.getCompaignId(), task.getTaskId(), null);
            d.a(this.a).b(task.getStateListener());
            if (task instanceof DownloadTask) {
                DownloadTask downloadTask = (DownloadTask) task;
                if (this.b != null) {
                    for (DownloadTask t : this.b) {
                        if (downloadTask.isSimilar(t)) {
                            this.b.remove(t);
                            break;
                        }
                    }
                }
            } else if (task instanceof ReviewTask) {
                ReviewTask reviewTask = (ReviewTask) task;
                if (this.c != null) {
                    for (ReviewTask t2 : this.c) {
                        if (reviewTask.isSimilar(t2)) {
                            this.c.remove(t2);
                            break;
                        }
                    }
                }
            } else if (task instanceof LaunchTask) {
                LaunchTask launchTask = (LaunchTask) task;
                if (this.d != null) {
                    for (LaunchTask t3 : this.d) {
                        if (launchTask.isSimilar(t3)) {
                            this.d.remove(t3);
                        }
                    }
                }
            }
        }
    }

    public PayTask a() {
        return this.e;
    }

    public ReviewTask a(String pkgName) {
        if (this.c != null) {
            for (ReviewTask t : this.c) {
                if (pkgName.equals(t.getPkgName())) {
                    return t;
                }
            }
        }
        return null;
    }

    public LaunchTask b(String pkgName) {
        if (this.d != null) {
            for (LaunchTask t : this.d) {
                if (pkgName.equals(t.getPkgName())) {
                    return t;
                }
            }
        }
        return null;
    }
}
