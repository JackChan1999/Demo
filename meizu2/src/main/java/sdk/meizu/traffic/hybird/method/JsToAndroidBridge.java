package sdk.meizu.traffic.hybird.method;

import android.util.Log;
import android.webkit.JavascriptInterface;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import sdk.meizu.traffic.hybird.exception.NativeMethodError;

public class JsToAndroidBridge {
    private static final String TAG = JsToAndroidBridge.class.getSimpleName();
    private INativeInterface mNativeInterface;
    private Method[] mNativeMethods;
    private final HashMap<String, NativeMethodInfo> mNativeMethodsCache = new LinkedHashMap();

    public JsToAndroidBridge(INativeInterface iNativeInterface) {
        this.mNativeInterface = iNativeInterface;
    }

    @JavascriptInterface
    public void doAndroidAction(String str) {
        doAndroidAction(str, null, null);
    }

    @JavascriptInterface
    public void doAndroidAction(String str, String str2) {
        doAndroidAction(str, str2, null);
    }

    @JavascriptInterface
    public void doAndroidAction(String str, String str2, String str3) {
        try {
            invokeMethod(str, str2, str3);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "function: " + str + " | data: " + str2 + " callback: " + str3 + " exception: " + e.getMessage());
        }
    }

    private void invokeMethod(String str, String str2, String str3) {
        NativeMethodInfo nativeMethodInfo = (NativeMethodInfo) this.mNativeMethodsCache.get(str);
        if (nativeMethodInfo == null) {
            initNativeMethods();
            nativeMethodInfo = generateNativeMethodInfo(str);
            this.mNativeMethodsCache.put(str, nativeMethodInfo);
        }
        nativeMethodInfo.invoke(str2, str3);
    }

    private void initNativeMethods() {
        if (this.mNativeMethods == null) {
            this.mNativeMethods = this.mNativeInterface.getClass().getMethods();
        }
    }

    private NativeMethodInfo generateNativeMethodInfo(String str) {
        NativeMethodInfo nativeMethodInfo = null;
        for (Method method : this.mNativeMethods) {
            if (method.getName().equals(str)) {
                validateNativeMethod(method);
                nativeMethodInfo = new NativeMethodInfo(this.mNativeInterface, method);
                break;
            }
        }
        if (nativeMethodInfo == null) {
            Log.e(TAG, str + " has no defined in native interface");
        }
        return nativeMethodInfo;
    }

    private void validateNativeMethod(Method method) {
        if (method.getAnnotation(NativeMethod.class) == null) {
            throw new NativeMethodError(method.getName() + "can't be invoke by Javascript! @NativeMethod annotation couldn't be found!");
        }
    }
}
