package com.meizu.cloud.a;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.meizu.cloud.app.request.model.AccountInfoModel;

public class c {
    public static String a(Context context) {
        String userId = c(context);
        String account = null;
        if (!TextUtils.isEmpty(userId)) {
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(Uri.withAppendedPath(Uri.parse("content://com.meizu.account/account"), userId), null, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        account = cursor.getString(cursor.getColumnIndex("flyme"));
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return account == null ? null : account;
    }

    public static String b(Context context) {
        String userId = c(context);
        String phone = null;
        if (!TextUtils.isEmpty(userId)) {
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(Uri.withAppendedPath(Uri.parse("content://com.meizu.account/account"), userId), null, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        phone = cursor.getString(cursor.getColumnIndex("phone"));
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return phone == null ? null : phone;
    }

    public static String c(Context context) {
        Account account = d(context);
        return account == null ? null : account.name;
    }

    public static Account d(Context context) {
        Account[] accounts = AccountManager.get(context.getApplicationContext()).getAccountsByType("com.meizu.account");
        if (accounts == null || accounts.length <= 0) {
            return null;
        }
        return accounts[0];
    }

    public static AccountInfoModel e(Context context) {
        Exception e;
        Throwable th;
        String userId = c(context);
        AccountInfoModel account = null;
        if (!TextUtils.isEmpty(userId)) {
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(Uri.withAppendedPath(Uri.parse("content://com.meizu.account/account"), userId), null, null, null, null);
                if (cursor != null) {
                    AccountInfoModel account2 = new AccountInfoModel();
                    try {
                        account2.userId = userId;
                        while (cursor.moveToNext()) {
                            account2.flyme = cursor.getString(cursor.getColumnIndex("flyme"));
                            account2.phone = cursor.getString(cursor.getColumnIndex("phone"));
                            account2.nickname = cursor.getString(cursor.getColumnIndex("nickName"));
                        }
                        account = account2;
                    } catch (Exception e2) {
                        e = e2;
                        account = account2;
                        try {
                            e.printStackTrace();
                            if (cursor != null) {
                                cursor.close();
                            }
                            return account;
                        } catch (Throwable th2) {
                            th = th2;
                            if (cursor != null) {
                                cursor.close();
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        account = account2;
                        if (cursor != null) {
                            cursor.close();
                        }
                        throw th;
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e3) {
                e = e3;
                e.printStackTrace();
                if (cursor != null) {
                    cursor.close();
                }
                return account;
            }
        }
        return account;
    }
}
