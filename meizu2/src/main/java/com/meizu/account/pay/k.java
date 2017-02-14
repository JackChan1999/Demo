package com.meizu.account.pay;

import android.os.Bundle;
import android.text.TextUtils;
import com.meizu.account.pay.a.b;

final class k extends b {
    private /* synthetic */ j a;

    k(j jVar) {
        this.a = jVar;
    }

    public final Bundle a(String str) {
        Object handleAccountChange = this.a.e.handleAccountChange(str);
        if (TextUtils.isEmpty(handleAccountChange)) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putString("custom_handle_msg", handleAccountChange);
        return bundle;
    }

    public final Bundle a(String str, String str2) {
        CustomBusinessResult handleCustomBusiness = this.a.e.handleCustomBusiness(str, str2);
        Bundle bundle;
        if (!handleCustomBusiness.mSuccess) {
            bundle = new Bundle();
            bundle.putInt("custom_handle_result", -1);
            bundle.putString("custom_handle_msg", handleCustomBusiness.mErrMsg);
            return bundle;
        } else if (handleCustomBusiness.mTradeOrderInfo != null) {
            this.a.g = handleCustomBusiness.mTradeOrderInfo;
            bundle = handleCustomBusiness.mTradeOrderInfo.toBundle();
            bundle.putInt("custom_handle_result", 1);
            return bundle;
        } else {
            bundle = new Bundle();
            bundle.putInt("custom_handle_result", 2);
            return bundle;
        }
    }
}
