package com.meizu.common.renderer.effect.parameters;

import android.util.Log;
import com.meizu.common.renderer.effect.GLRenderManager;
import java.util.Hashtable;

public class BaseParameters {
    private Hashtable<String, Object> mMap;

    public BaseParameters() {
        this.mMap = new Hashtable(10);
    }

    public BaseParameters(BaseParameters baseParameters) {
        this.mMap = new Hashtable(baseParameters.mMap);
    }

    public void copyFrom(BaseParameters baseParameters) {
        this.mMap.clear();
        this.mMap.putAll(baseParameters.mMap);
    }

    public void set(BaseParameters baseParameters) {
        this.mMap = baseParameters.mMap;
    }

    public void set(String str, Object obj) {
        this.mMap.put(str, obj);
    }

    public Object get(String str) {
        return this.mMap.get(str);
    }

    public String getString(String str, String str2) {
        Object obj = this.mMap.get(str);
        if (obj == null) {
            return str2;
        }
        try {
            return (String) obj;
        } catch (ClassCastException e) {
            Log.e(GLRenderManager.TAG, e.getMessage());
            return str2;
        }
    }

    public int getInt(String str, int i) {
        Object obj = this.mMap.get(str);
        if (obj != null) {
            try {
                i = ((Integer) obj).intValue();
            } catch (ClassCastException e) {
                Log.e(GLRenderManager.TAG, e.getMessage());
            }
        }
        return i;
    }

    public float getFloat(String str, float f) {
        Object obj = this.mMap.get(str);
        if (obj != null) {
            try {
                f = ((Float) obj).floatValue();
            } catch (ClassCastException e) {
                Log.e(GLRenderManager.TAG, e.getMessage());
            }
        }
        return f;
    }

    public boolean getBoolean(String str, boolean z) {
        Object obj = this.mMap.get(str);
        if (obj != null) {
            try {
                z = ((Boolean) obj).booleanValue();
            } catch (ClassCastException e) {
                Log.e(GLRenderManager.TAG, e.getMessage());
            }
        }
        return z;
    }

    public void dump() {
        Log.e(GLRenderManager.TAG, "dump: size=" + this.mMap.size());
        for (String str : this.mMap.keySet()) {
            Log.e(GLRenderManager.TAG, "dump: " + str + "=" + this.mMap.get(str));
        }
    }
}
