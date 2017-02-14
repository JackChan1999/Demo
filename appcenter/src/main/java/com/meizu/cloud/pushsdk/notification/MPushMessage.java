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
    private Map extra = new HashMap();
    private String isDiscard;
    private String notifyType;
    private String packageName;
    private Map params = new HashMap();
    private String pushType;
    private String taskId;
    private String title;

    private static Map getParamsMap(JSONObject jSONObject) {
        Map hashMap = new HashMap();
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

    public static MPushMessage parsePushMessage(String str, String str2, String str3, String str4) {
        MPushMessage mPushMessage = new MPushMessage();
        try {
            JSONObject jSONObject = new JSONObject(str2);
            mPushMessage.setTaskId(str4);
            mPushMessage.setPushType(str);
            mPushMessage.setContent(jSONObject.getString(PushConstants.CONTENT));
            mPushMessage.setPackageName(str3);
            mPushMessage.setIsDiscard(jSONObject.getString(PushConstants.IS_DISCARD));
            mPushMessage.setTitle(jSONObject.getString(PushConstants.TITLE));
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

    public String getClickType() {
        return this.clickType;
    }

    public String getContent() {
        return this.content;
    }

    public Map getExtra() {
        return this.extra;
    }

    public String getIsDiscard() {
        return this.isDiscard;
    }

    public String getNotifyType() {
        return this.notifyType;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public Map getParams() {
        return this.params;
    }

    public String getPushType() {
        return this.pushType;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setClickType(String str) {
        this.clickType = str;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public void setExtra(Map map) {
        this.extra = map;
    }

    public void setIsDiscard(String str) {
        this.isDiscard = str;
    }

    public void setNotifyType(String str) {
        this.notifyType = str;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public void setParams(Map map) {
        this.params = map;
    }

    public void setPushType(String str) {
        this.pushType = str;
    }

    public void setTaskId(String str) {
        this.taskId = str;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String toString() {
        return "MPushMessage{taskId='" + this.taskId + '\'' + ", pushType='" + this.pushType + '\'' + ", packageName='" + this.packageName + '\'' + ", title='" + this.title + '\'' + ", content='" + this.content + '\'' + ", notifyType='" + this.notifyType + '\'' + ", clickType='" + this.clickType + '\'' + ", isDiscard='" + this.isDiscard + '\'' + ", extra=" + this.extra + ", params=" + this.params + '}';
    }
}
