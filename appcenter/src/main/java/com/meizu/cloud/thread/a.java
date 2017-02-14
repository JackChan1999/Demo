package com.meizu.cloud.thread;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class a extends b {
    private static a a;
    private ExecutorService b = Executors.newCachedThreadPool();
    private LinkedList<d> c = new LinkedList();
    private f d = new f();
    private a e = new a(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        public void a(d task) {
            synchronized (this.a.c) {
                if (!this.a.c.remove(task)) {
                    a.d("clear task cant find task = " + task);
                }
                a.c("rem task, s = " + this.a.c.size());
            }
        }
    };

    public static b a() {
        if (a == null) {
            a = new a();
        }
        return a;
    }

    private a() {
    }

    public c a(Runnable runnable, e observer) {
        d asyncTask;
        synchronized (this.c) {
            asyncTask = new d(runnable, observer, this.e);
            this.c.add(asyncTask);
            c("add task, s = " + this.c.size());
            this.b.execute(asyncTask.d());
        }
        return asyncTask;
    }

    private static void c(String tip) {
        System.out.println("AsyncExecImpl : " + tip);
    }

    private static void d(String tip) {
        System.out.println("AsyncExecImpl : " + tip);
    }
}
