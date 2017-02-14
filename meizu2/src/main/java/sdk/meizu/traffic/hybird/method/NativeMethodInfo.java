package sdk.meizu.traffic.hybird.method;

import android.text.TextUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import org.json.JSONArray;
import org.json.JSONObject;
import sdk.meizu.traffic.hybird.exception.NativeMethodError;
import sdk.meizu.traffic.hybird.exception.NativeParseError;

public class NativeMethodInfo {
    private Method method;
    private Object[] parameters;
    private INativeInterface target;

    public NativeMethodInfo(INativeInterface iNativeInterface, Method method) {
        this.target = iNativeInterface;
        this.method = method;
    }

    public void invoke(String str, String str2) {
        try {
            parseParameters(str, str2);
            if (this.parameters == null || this.parameters.length == 0) {
                this.method.invoke(this.target, new Object[0]);
            } else {
                this.method.invoke(this.target, this.parameters);
            }
        } catch (Throwable e) {
            throw new NativeMethodError(e);
        } catch (Throwable e2) {
            throw new NativeMethodError(e2);
        } catch (Throwable e22) {
            throw new NativeMethodError(e22);
        }
    }

    private void parseParameters(String str, String str2) {
        int i;
        Class[] parameterTypes = this.method.getParameterTypes();
        Annotation[][] parameterAnnotations = this.method.getParameterAnnotations();
        int length = parameterTypes.length;
        this.parameters = new Object[length];
        if (hasCallBack(str2)) {
            this.parameters[length - 1] = str2;
            i = length - 1;
        } else {
            i = length;
        }
        if (i > 0) {
            JSONObject jSONObject = new JSONObject(str);
            Annotation[] annotationArr = new Annotation[i];
            for (int i2 = 0; i2 < i; i2++) {
                Class cls = parameterTypes[i2];
                Annotation[] annotationArr2 = parameterAnnotations[i2];
                if (annotationArr2 == null || annotationArr2.length == 0) {
                    this.parameters[i2] = str;
                } else {
                    int length2 = annotationArr2.length;
                    int i3 = 0;
                    while (i3 < length2) {
                        Annotation annotation = annotationArr2[i3];
                        if (annotation != null) {
                            Class annotationType = annotation.annotationType();
                            if (annotationType != Parameter.class) {
                                throw new NativeParseError("The Annotation Type of the Parameter can't be " + annotationType.getSimpleName());
                            }
                            String value = ((Parameter) annotation).value();
                            if (!jSONObject.has(value)) {
                                this.parameters[i2] = null;
                            } else if (cls == String.class) {
                                this.parameters[i2] = jSONObject.getString(value);
                            } else if (cls == Boolean.TYPE) {
                                this.parameters[i2] = Boolean.valueOf(jSONObject.getBoolean(value));
                            } else if (cls == JSONObject.class) {
                                this.parameters[i2] = jSONObject.getJSONObject(value);
                            } else if (cls == JSONArray.class) {
                                this.parameters[i2] = jSONObject.getJSONArray(value);
                            }
                            i3++;
                        } else {
                            throw new NativeParseError("The Annotation Type of the Parameter required!");
                        }
                    }
                    continue;
                }
            }
        }
    }

    private boolean hasCallBack(String str) {
        if (TextUtils.isEmpty(str) || "undefined".equals(str)) {
            return false;
        }
        return true;
    }
}
