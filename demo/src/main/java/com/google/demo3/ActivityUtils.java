package com.google.demo3;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/2 00:26
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class ActivityUtils {
    public static void doAsync(final Context pContext, final int pTitleResID, final int
            pMessageResID, final Callable pCallable, final Callback pCallback) {
        ActivityUtils.doAsync(pContext, pTitleResID, pMessageResID, pCallable, pCallback, null,
                false);
    }

    public static void doAsync(final Context pContext, final CharSequence pTitle, final
    CharSequence pMessage, final Callable pCallable, final Callback pCallback) {
        ActivityUtils.doAsync(pContext, pTitle, pMessage, pCallable, pCallback, null, false);
    }

    public static void doAsync(final Context pContext, final int pTitleResID, final int
            pMessageResID, final Callable pCallable, final Callback pCallback, final boolean
            pCancelable) {
        ActivityUtils.doAsync(pContext, pTitleResID, pMessageResID, pCallable, pCallback, null,
                pCancelable);
    }

    public static void doAsync(final Context pContext, final CharSequence pTitle, final
    CharSequence pMessage, final Callable pCallable, final Callback pCallback, final boolean
            pCancelable) {
        ActivityUtils.doAsync(pContext, pTitle, pMessage, pCallable, pCallback, null, pCancelable);
    }

    public static void doAsync(final Context pContext, final int pTitleResID, final int
            pMessageResID, final Callable pCallable, final Callback pCallback, final Callback
            pExceptionCallback) {
        ActivityUtils.doAsync(pContext, pTitleResID, pMessageResID, pCallable, pCallback,
                pExceptionCallback, false);
    }

    public static void doAsync(final Context pContext, final CharSequence pTitle, final
    CharSequence pMessage, final Callable pCallable, final Callback pCallback, final Callback
            pExceptionCallback) {
        ActivityUtils.doAsync(pContext, pTitle, pMessage, pCallable, pCallback,
                pExceptionCallback, false);
    }

    public static void doAsync(final Context pContext, final int pTitleResID, final int
            pMessageResID, final Callable pCallable, final Callback pCallback, final Callback
            pExceptionCallback, final boolean pCancelable) {
        ActivityUtils.doAsync(pContext, pContext.getString(pTitleResID), pContext.getString
                (pMessageResID), pCallable, pCallback, pExceptionCallback, pCancelable);
    }

    public static void doAsync(final Context pContext, final CharSequence pTitle, final
    CharSequence pMessage, final Callable pCallable, final Callback pCallback, final Callback
            pExceptionCallback, final boolean pCancelable) {
        new AsyncTask() {
            private ProgressDialog mPD;
            private Exception mException = null;

            @Override
            public void onPreExecute() {
                this.mPD = ProgressDialog.show(pContext, pTitle, pMessage, true, pCancelable);
                if (pCancelable) {
                    this.mPD.setOnCancelListener(new OnCancelListener() {
                        @Override
                        public void onCancel(final DialogInterface pDialogInterface) {
                            pExceptionCallback.onCallback(new CancelledException());
                            pDialogInterface.dismiss();
                        }
                    });
                }
                super.onPreExecute();
            }

            @Override
            public T doInBackground(final Void... params) {
                try {
                    return pCallable.call();
                } catch (final Exception e) {
                    this.mException = e;
                }
                return null;
            }

            @Override
            public void onPostExecute(final T result) {
                try {
                    this.mPD.dismiss();
                } catch (final Exception e) {
                    Log.e("Error", e.toString());
                }
                if (this.isCancelled()) {
                    this.mException = new CancelledException();
                }
                if (this.mException == null) {
                    pCallback.onCallback(result);
                } else {
                    if (pExceptionCallback == null) {
                        if (this.mException != null)
                            Log.e("Error", this.mException.toString());
                    } else {
                        pExceptionCallback.onCallback(this.mException);
                    }
                }
                super.onPostExecute(result);
            }
        }.execute((Void[]) null);
    }

    public static void doProgressAsync(final Context pContext, final int pTitleResID, final
    ProgressCallable pCallable, final Callback pCallback) {
        ActivityUtils.doProgressAsync(pContext, pTitleResID, pCallable, pCallback, null);
    }

    public static void doProgressAsync(final Context pContext, final int pTitleResID, final
    ProgressCallable pCallable, final Callback pCallback, final Callback pExceptionCallback) {
        new AsyncTask() {
            private ProgressDialog mPD;
            private Exception mException = null;

            @Override
            public void onPreExecute() {
                this.mPD = new ProgressDialog(pContext);
                this.mPD.setTitle(pTitleResID);
                this.mPD.setIcon(android.R.drawable.ic_menu_save);
                this.mPD.setIndeterminate(false);
                this.mPD.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                this.mPD.show();
                super.onPreExecute();
            }

            @Override
            public T doInBackground(final Void... params) {
                try {
                    return pCallable.call(new IProgressListener() {
                        @Override
                        public void onProgressChanged(final int pProgress) {
                            onProgressUpdate(pProgress);
                        }
                    });
                } catch (final Exception e) {
                    this.mException = e;
                }
                return null;
            }

            @Override
            public void onProgressUpdate(final Integer... values) {
                this.mPD.setProgress(values[0]);
            }

            @Override
            public void onPostExecute(final T result) {
                try {
                    this.mPD.dismiss();
                } catch (final Exception e) {
                    Log.e("Error", e.getLocalizedMessage());
                                        /* Nothing. */
                }
                if (this.isCancelled()) {
                    this.mException = new CancelledException();
                }
                if (this.mException == null) {
                    pCallback.onCallback(result);
                } else {
                    if (pExceptionCallback == null) {
                        Log.e("Error", this.mException.getLocalizedMessage());
                    } else {
                        pExceptionCallback.onCallback(this.mException);
                    }
                }
                super.onPostExecute(result);
            }
        }.execute((Void[]) null);
    }

    public static void doAsync(final Context pContext, final int pTitleResID, final int
            pMessageResID, final AsyncCallable pAsyncCallable, final Callback pCallback, final
    Callback pExceptionCallback) {
        final ProgressDialog pd = ProgressDialog.show(pContext, pContext.getString(pTitleResID),
                pContext.getString(pMessageResID));
        pAsyncCallable.call(new Callback() {
            @Override
            public void onCallback(final T result) {
                try {
                    pd.dismiss();
                } catch (final Exception e) {
                    Log.e("Error", e.getLocalizedMessage());
                                        /* Nothing. */
                }
                pCallback.onCallback(result);
            }
        }, pExceptionCallback);
    }

    public static class CancelledException extends Exception {
        private static final long serialVersionUID = -78123211381435595L;
    }
}
