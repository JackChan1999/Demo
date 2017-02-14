package com.meizu.cloud.pushsdk.notification;

import android.util.Log;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class MPushMessage implements Serializable {
    private static final String TAG = "MPushMessage";
    private String clickType;
    private String content;
    private Map<String, String> extra = new HashMap();
    private String isDiscard;
    private String notifyType;
    private String packageName;
    private Map<String, String> params = new HashMap();
    private String pushType;
    private String taskId;
    private String title;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public String getNotifyType() {
        return this.notifyType;
    }

    public void setNotifyType(String str) {
        this.notifyType = str;
    }

    public Map<String, String> getExtra() {
        return this.extra;
    }

    public void setExtra(Map<String, String> map) {
        this.extra = map;
    }

    public String getClickType() {
        return this.clickType;
    }

    public void setClickType(String str) {
        this.clickType = str;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String str) {
        this.taskId = str;
    }

    public String getPushType() {
        return this.pushType;
    }

    public void setPushType(String str) {
        this.pushType = str;
    }

    public String getIsDiscard() {
        return this.isDiscard;
    }

    public void setIsDiscard(String str) {
        this.isDiscard = str;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public Map<String, String> getParams() {
        return this.params;
    }

    public void setParams(Map<String, String> map) {
        this.params = map;
    }

    public String toString() {
        return "MPushMessage{taskId='" + this.taskId + '\'' + ", pushType='" + this.pushType + '\'' + ", packageName='" + this.packageName + '\'' + ", title='" + this.title + '\'' + ", content='" + this.content + '\'' + ", notifyType='" + this.notifyType + '\'' + ", clickType='" + this.clickType + '\'' + ", isDiscard='" + this.isDiscard + '\'' + ", extra=" + this.extra + ", params=" + this.params + '}';
    }

    public static MPushMessage parsePushMessage(String str, String str2, String str3, String str4) {
        MPushMessage mPushMessage = new MPushMessage();
        try {
            JSONObject jSONObject = new JSONObject(str2);
            mPushMessage.setTaskId(str4);
            mPushMessage.setPushType(str);
            mPushMessage.setContent(jSONObject.getString(PushConstants.CONTENT));
            mPushMessage.setPackageName(str3);
            mPushMessage.setIsDiscard(jSONObject.getString(PushConstants.IS_DISCARD));
            mPushMessage.setTitle(jSONObject.getString("title"));
            mPushMessage.setClickType(jSONObject.getString(PushConstants.CLICK_TYPE));
            jSONObject = jSONObject.getJSONObject(PushConstants.EXTRA);
            if (jSONObject != null) {
                JSONObject jSONObject2 = jSONObject.getJSONObject(PushConstants.PARAMS);
                if (jSONObject2 != null) {
                    mPushMessage.setParams(getParamsMap(jSONObject2));
                    jSONObject.remove(PushConstants.PARAMS);
                }
                mPushMessage.setExtra(getParamsMap(jSONObject));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(TAG, " parsePushMessage " + mPushMessage);
        return mPushMessage;
    }

    private static Map<String, String> getParamsMap(JSONObject jSONObject) {
        Map<String, String> hashMap = new HashMap();
        try {
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                hashMap.put(str, jSONObject.getString(str));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return hashMap;
    }
}
