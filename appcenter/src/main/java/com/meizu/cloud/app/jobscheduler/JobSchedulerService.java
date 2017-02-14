package com.meizu.cloud.app.jobscheduler;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JobSchedulerService extends JobService {
    public static final int[] a = new int[]{101, 102};
    private static JobSchedulerService e;
    private static ArrayList<Runnable> f = new ArrayList();
    private static Object g = new Object();
    private static Object h = new Object();
    private JobScheduler b;
    private Handler c = new Handler(this, Looper.getMainLooper()) {
        final /* synthetic */ JobSchedulerService a;

        public void handleMessage(Message msg) {
            JobParameters jobParameters = msg.obj;
            String id = String.valueOf(jobParameters.getJobId());
            synchronized (this.a.d) {
                if (this.a.d.containsKey(id)) {
                    ((a) this.a.d.get(id)).a.run();
                    if (!((a) this.a.d.get(id)).b) {
                        Log.d("JobSchedulerService", "scheduler job and remove job id: " + jobParameters.getJobId());
                        this.a.d.remove(String.valueOf(jobParameters.getJobId()));
                    }
                }
            }
            this.a.jobFinished(jobParameters, false);
            super.handleMessage(msg);
        }
    };
    private Map<String, a> d;

    private class a {
        public Runnable a;
        public boolean b = false;
        final /* synthetic */ JobSchedulerService c;

        public a(JobSchedulerService jobSchedulerService) {
            this.c = jobSchedulerService;
        }
    }

    public void onCreate() {
        super.onCreate();
        e = this;
        this.d = new HashMap();
        this.b = (JobScheduler) getSystemService("jobscheduler");
    }

    public void onDestroy() {
        e = null;
        super.onDestroy();
    }

    public void a(JobInfo jobInfo, Runnable runnable) {
        String id = String.valueOf(jobInfo.getId());
        Log.d("JobSchedulerService", "Schedule job: " + id);
        a jobRunnable = new a(this);
        jobRunnable.a = runnable;
        jobRunnable.b = jobInfo.isPeriodic();
        synchronized (this.d) {
            if (this.d.containsKey(id)) {
                this.d.remove(id);
            }
            this.d.put(id, jobRunnable);
        }
        this.b.schedule(jobInfo);
    }

    public void a(int jobId) {
        this.b.cancel(jobId);
    }

    public boolean onStartJob(JobParameters jobParameters) {
        Log.d("JobSchedulerService", "onStartJob job: " + jobParameters.getJobId());
        this.c.sendMessage(Message.obtain(this.c, 1, jobParameters));
        return true;
    }

    public boolean onStopJob(JobParameters jobParameters) {
        Log.d("JobSchedulerService", "onStopJob job: " + jobParameters.getJobId());
        this.d.remove(String.valueOf(jobParameters.getJobId()));
        return false;
    }

    public static JobSchedulerService a(Runnable bindedRunnalbe) {
        JobSchedulerService jobSchedulerService;
        synchronized (g) {
            if (e == null && bindedRunnalbe != null) {
                synchronized (f) {
                    f.add(bindedRunnalbe);
                }
            }
            jobSchedulerService = e;
        }
        return jobSchedulerService;
    }

    public static void a(Context context, Class<?> clazz) {
        synchronized (g) {
            if (e == null) {
                ServiceConnection serviceConnection = new ServiceConnection() {
                    public void onServiceConnected(ComponentName className, IBinder iservice) {
                        synchronized (JobSchedulerService.h) {
                            JobSchedulerService.h.notifyAll();
                        }
                        synchronized (JobSchedulerService.f) {
                            Iterator i$ = JobSchedulerService.f.iterator();
                            while (i$.hasNext()) {
                                ((Runnable) i$.next()).run();
                            }
                            JobSchedulerService.f.clear();
                        }
                    }

                    public void onServiceDisconnected(ComponentName className) {
                        synchronized (JobSchedulerService.g) {
                            JobSchedulerService.e = null;
                        }
                        synchronized (JobSchedulerService.h) {
                            JobSchedulerService.h.notifyAll();
                        }
                    }
                };
                context.getApplicationContext().bindService(new Intent(context.getApplicationContext(), clazz), serviceConnection, 1);
            }
        }
    }
}
