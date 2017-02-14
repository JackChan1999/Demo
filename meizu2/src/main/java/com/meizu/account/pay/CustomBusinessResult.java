package com.meizu.account.pay;

public class CustomBusinessResult {
    protected final String mErrMsg;
    protected final boolean mSuccess;
    protected final OutTradeOrderInfo mTradeOrderInfo;

    private CustomBusinessResult(boolean z, OutTradeOrderInfo outTradeOrderInfo, String str) {
        this.mSuccess = z;
        this.mTradeOrderInfo = outTradeOrderInfo;
        this.mErrMsg = str;
    }

    public static CustomBusinessResult constructError(String str) {
        return new CustomBusinessResult(false, null, str);
    }

    public static CustomBusinessResult constructOrder(OutTradeOrderInfo outTradeOrderInfo) {
        return new CustomBusinessResult(true, outTradeOrderInfo, null);
    }

    public static CustomBusinessResult constructPayEnd() {
        return new CustomBusinessResult(true, null, null);
    }
}
