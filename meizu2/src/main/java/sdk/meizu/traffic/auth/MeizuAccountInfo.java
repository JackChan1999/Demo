package sdk.meizu.traffic.auth;

import android.database.Cursor;

public class MeizuAccountInfo {
    public static final String LOCAL_KEY_NICK_NAME = "nickName";
    public static final String LOCAL_KEY_PHONE = "phone";
    public static final String LOCAL_KEY_USER_ID = "userId";
    private String mNickName;
    private String mPhone;
    private String mUserId;

    public MeizuAccountInfo(Cursor cursor) {
        this.mUserId = cursor.getString(cursor.getColumnIndex(LOCAL_KEY_USER_ID));
        this.mNickName = cursor.getString(cursor.getColumnIndex(LOCAL_KEY_NICK_NAME));
        this.mPhone = cursor.getString(cursor.getColumnIndex("phone"));
    }

    public String getUserId() {
        return this.mUserId;
    }

    public String getNickName() {
        return this.mNickName;
    }

    public String getPhone() {
        return this.mPhone;
    }
}
