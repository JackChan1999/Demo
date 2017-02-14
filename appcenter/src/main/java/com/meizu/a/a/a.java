package com.meizu.a.a;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.meizu.a.a.a.b;
import com.meizu.a.a.a.c;

public abstract class a {
    protected Activity a;
    private c<b> b;
    private Handler c = new Handler(this.a.getMainLooper());

    protected abstract void a();

    protected abstract void a(int i, String str);

    protected abstract void a(Bundle bundle);

    protected abstract void a(b bVar, com.meizu.a.a.a.a aVar) throws RemoteException;

    public a(Activity activity) {
        this.a = activity;
        if (this.a == null) {
            throw new IllegalArgumentException("activity cant be null!");
        }
        this.b = new c(this.a.getApplicationContext(), new com.meizu.a.a.a.c.a<b>(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }

            public /* synthetic */ Object b(IBinder x0) {
                return a(x0);
            }

            public b a(IBinder obj) {
                return com.meizu.a.a.a.b.a.a(obj);
            }
        }, "com.meizu.account.pay.SystemPayService", "com.meizu.account");
    }

    public void b() {
        new AsyncTask<Void, Void, Void>(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }

            protected /* synthetic */ Object doInBackground(Object[] x0) {
                return a((Void[]) x0);
            }

            protected /* synthetic */ void onPostExecute(Object x0) {
                a((Void) x0);
            }

            protected Void a(Void... params) {
                b service = (b) this.a.b.a();
                if (service != null) {
                    try {
                        this.a.a(service, new com.meizu.a.a.a.a.a(this) {
                            final /* synthetic */ AnonymousClass2 a;

                            {
                                this.a = r1;
                            }

                            public void a(final Bundle value) throws RemoteException {
                                this.a.a.a(new Runnable(this) {
                                    final /* synthetic */ AnonymousClass1 b;

                                    public void run() {
                                        this.b.a.a.a(value);
                                        this.b.a.a.c();
                                    }
                                });
                            }

                            public void a(final int errorCode, final String errorMsg) throws RemoteException {
                                this.a.a.a(new Runnable(this) {
                                    final /* synthetic */ AnonymousClass1 c;

                                    public void run() {
                                        this.c.a.a.a(errorCode, errorMsg);
                                        this.c.a.a.c();
                                    }
                                });
                            }

                            public void b(Bundle value) throws RemoteException {
                                if (value.containsKey("intent")) {
                                    this.a.a.a((Intent) value.getParcelable("intent"));
                                }
                            }
                        });
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        this.a.a(new Runnable(this) {
                            final /* synthetic */ AnonymousClass2 a;

                            {
                                this.a = r1;
                            }

                            public void run() {
                                this.a.a.a();
                                this.a.a.c();
                            }
                        });
                    }
                } else {
                    this.a.a(new Runnable(this) {
                        final /* synthetic */ AnonymousClass2 a;

                        {
                            this.a = r1;
                        }

                        public void run() {
                            this.a.a.a();
                            this.a.a.c();
                        }
                    });
                }
                return null;
            }

            protected void a(Void result) {
                this.a.b.b();
            }
        }.execute(new Void[0]);
    }

    private void a(Intent intent) {
        if (this.a == null) {
            Log.e("SystemPayController", "startActivityForService while no activity!");
            return;
        }
        try {
            this.a.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            a(new Runnable(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public void run() {
                    this.a.a();
                    this.a.c();
                }
            });
        }
    }

    private void a(Runnable r) {
        this.c.post(r);
    }

    protected void c() {
        Log.e("SystemPayController", "releaseInfo");
        this.a = null;
    }
}
