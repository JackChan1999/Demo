package sdk.meizu.traffic.auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class MzAccountManager {
    private static final Uri ACCOUNT_BASE_URI = Uri.parse("content://com.meizu.account");
    public static final String ACCOUNT_TYPE = "com.meizu.account";
    private static final Uri ACCOUNT_URI = Uri.withAppendedPath(ACCOUNT_BASE_URI, PATH_ACCOUNT);
    public static final String AUTHORITY = "com.meizu.account";
    public static final String PATH_ACCOUNT = "account";
    private static final String SELECTION_WITH_USER_ID = "userId=?";
    private ContentResolver mResolver;

    public MzAccountManager(Context context) {
        this.mResolver = context.getContentResolver();
    }

    public static Account getBaseAccount(Context context) {
        Account[] accountsByType = AccountManager.get(context).getAccountsByType("com.meizu.account");
        if (accountsByType == null || accountsByType.length == 0) {
            return null;
        }
        return accountsByType[0];
    }

    public static boolean isFlymeAccount(Account account) {
        if (account == null || !account.type.equals("com.meizu.account")) {
            return false;
        }
        return true;
    }

    public static final boolean hasFlymeAccount(Context context) {
        return getBaseAccount(context) != null;
    }

    public static MeizuAccountInfo loadMzAccountInfo(Context context) {
        MeizuAccountInfo meizuAccountInfo = null;
        if (getBaseAccount(context) != null) {
            Cursor query = context.getContentResolver().query(ACCOUNT_URI, null, SELECTION_WITH_USER_ID, new String[]{r5.name}, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        meizuAccountInfo = new MeizuAccountInfo(query);
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    if (query != null) {
                        query.close();
                    }
                } catch (Throwable th) {
                    if (query != null) {
                        query.close();
                    }
                }
            }
            if (query != null) {
                query.close();
            }
        }
        return meizuAccountInfo;
    }
}
