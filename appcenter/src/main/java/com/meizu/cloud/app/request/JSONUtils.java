package com.meizu.cloud.app.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.meizu.cloud.app.request.model.ResultModel;

public class JSONUtils {
    public static <T> T parseJSONObject(String jsonStr, TypeReference<T> typeRef) {
        T t = null;
        try {
            return JSON.parseObject(jsonStr, (TypeReference) typeRef, new Feature[0]);
        } catch (JSONException e) {
            if ("unclosed string : '".equals(e.getMessage())) {
                return JSON.parseObject(jsonStr.replace("\\'", "'"), (TypeReference) typeRef, new Feature[0]);
            }
            e.printStackTrace();
            return t;
        } catch (Exception e2) {
            e2.printStackTrace();
            return t;
        }
    }

    private static ResultModel<Object> parseResultModel(String jsonStr) {
        try {
            return (ResultModel) JSON.parseObject(jsonStr, new TypeReference<ResultModel<Object>>() {
            }, new Feature[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> ResultModel<T> parseResultModel(String jsonStr, TypeReference<ResultModel<T>> typeRef) {
        try {
            return (ResultModel) JSON.parseObject(jsonStr, (TypeReference) typeRef, new Feature[0]);
        } catch (JSONException e) {
            ResultModel<Object> retryResult = parseResultModel(jsonStr);
            if (retryResult == null) {
                return null;
            }
            ResultModel<T> result = new ResultModel();
            result.setCode(retryResult.getCode());
            result.setMessage(retryResult.getMessage());
            result.setRedirect(retryResult.getRedirect());
            result.setValue(null);
            return result;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
