package com.meizu.account.pay;

public interface PayListener {
    void onPayResult(int i, OutTradeOrderInfo outTradeOrderInfo, String str);
}
