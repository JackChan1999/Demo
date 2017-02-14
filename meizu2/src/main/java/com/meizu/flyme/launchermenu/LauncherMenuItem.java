package com.meizu.flyme.launchermenu;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import org.json.JSONObject;

public class LauncherMenuItem {
    private String TAG = "LauncherMenuItem";
    private Drawable drawable = null;
    private String icon = null;
    private Intent intent = null;
    private JSONObject mainVarData = null;
    private JSONObject mainVarDataJsonObject = new JSONObject();
    private int priority = -1;
    private JSONObject subVarData = null;
    private JSONObject subVarDataJsonObject = new JSONObject();
    private String tag = null;
    private String type = null;

    public String toString() {
        return "title = " + this.mainVarData + ",subTitle = " + this.subVarData + ",IconFont = " + getIconFont() + ",intent = " + getIntent() + ",priority = " + getPriority() + ",tag = " + getTag();
    }

    public void setIconFont(String str) {
        this.icon = str;
    }

    public String getIconFont() {
        return this.icon;
    }

    public void setCustomIcon(Drawable drawable) {
        this.drawable = drawable;
    }

    public Drawable getCustomIcon() {
        return this.drawable;
    }

    public void setTag(String str) {
        this.tag = str;
    }

    public String getTag() {
        return this.tag;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getType() {
        return this.type;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public Intent getIntent() {
        return this.intent;
    }

    public void setPriority(int i) {
        this.priority = i;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setMainVarData(int i, String... strArr) {
        int i2 = 0;
        this.mainVarDataJsonObject = new JSONObject();
        try {
            this.mainVarDataJsonObject.put("titleId", i);
            if (strArr != null && strArr.length > 0) {
                int length = strArr.length;
                int i3 = 0;
                while (i2 < length) {
                    this.mainVarDataJsonObject.put(PushConstants.CONTENT + i3, strArr[i2]);
                    i3++;
                    i2++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mainVarData = this.mainVarDataJsonObject;
    }

    public void setMainVarData(String str, String... strArr) {
        int i = 0;
        this.mainVarDataJsonObject = new JSONObject();
        try {
            this.mainVarDataJsonObject.put("titleId", str);
            if (strArr != null && strArr.length > 0) {
                int length = strArr.length;
                int i2 = 0;
                while (i < length) {
                    this.mainVarDataJsonObject.put(PushConstants.CONTENT + i2, strArr[i]);
                    i2++;
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mainVarData = this.mainVarDataJsonObject;
    }

    public JSONObject getMainVarData() {
        return this.mainVarData;
    }

    public void setSubVarData(int i, String... strArr) {
        int i2 = 0;
        if (i == -2) {
            Log.d(this.TAG, "the subTitle is null");
            this.subVarData = null;
            return;
        }
        this.subVarDataJsonObject = new JSONObject();
        try {
            this.subVarDataJsonObject.put("subTitleId", i);
            if (strArr != null && strArr.length > 0) {
                int length = strArr.length;
                int i3 = 0;
                while (i2 < length) {
                    this.subVarDataJsonObject.put(PushConstants.CONTENT + i3, strArr[i2]);
                    i3++;
                    i2++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.subVarData = this.subVarDataJsonObject;
    }

    public void setSubVarData(String str, String... strArr) {
        int i = 0;
        if (str == null || str.equals("")) {
            Log.d(this.TAG, "the subTitle is null");
            this.subVarData = null;
            return;
        }
        this.subVarDataJsonObject = new JSONObject();
        try {
            this.subVarDataJsonObject.put("subTitleId", str);
            if (strArr != null && strArr.length > 0) {
                int length = strArr.length;
                int i2 = 0;
                while (i < length) {
                    this.subVarDataJsonObject.put(PushConstants.CONTENT + i2, strArr[i]);
                    i2++;
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.subVarData = this.subVarDataJsonObject;
    }

    public JSONObject getSubVarData() {
        return this.subVarData;
    }
}
