package com.meizu.account.pay;

import android.os.Bundle;
import android.text.TextUtils;

public class OutTradeOrderInfo {
    private static final String KEY_BODY = "body";
    private static final String KEY_EXT_CONTENT = "extContent";
    private static final String KEY_LABEL_1 = "label1";
    private static final String KEY_LABEL_2 = "label2";
    private static final String KEY_NOTIFY_URL = "notifyUrl";
    private static final String KEY_OUT_TRADE = "outTrade";
    private static final String KEY_PARTNER = "partner";
    private static final String KEY_PAY_ACCOUNTS = "payAccounts";
    private static final String KEY_SIGN = "sign";
    private static final String KEY_SIGN_TYPE = "signType";
    private static final String KEY_SUBJECT = "subject";
    private static final String KEY_TOTAL_FEE = "totalFee";
    private String mBody;
    private String mExtContent;
    private String mLabel1;
    private String mLabel2;
    private String mNotifyUrl;
    private String mOutTrade;
    private String mPartner;
    private String mPayAccounts;
    private String mSign;
    private String mSignType;
    private String mSubject;
    private String mTotalFee;

    public static OutTradeOrderInfo fromBundle(Bundle bundle) {
        OutTradeOrderInfo outTradeOrderInfo = new OutTradeOrderInfo();
        outTradeOrderInfo.setExtContent(bundle.getString(KEY_EXT_CONTENT)).setLabel1(bundle.getString(KEY_LABEL_1)).setLabel2(bundle.getString(KEY_LABEL_2)).setBody(bundle.getString(KEY_BODY)).setNotifyUrl(bundle.getString(KEY_NOTIFY_URL)).setOutTrade(bundle.getString(KEY_OUT_TRADE)).setPartner(bundle.getString(KEY_PARTNER)).setPayAccounts(bundle.getString(KEY_PAY_ACCOUNTS)).setSign(bundle.getString(KEY_SIGN)).setSignType(bundle.getString(KEY_SIGN_TYPE)).setSubject(bundle.getString(KEY_SUBJECT)).setTotalFee(bundle.getString(KEY_TOTAL_FEE));
        return (TextUtils.isEmpty(outTradeOrderInfo.getNotifyUrl()) || TextUtils.isEmpty(outTradeOrderInfo.getOutTrade()) || TextUtils.isEmpty(outTradeOrderInfo.getPartner()) || TextUtils.isEmpty(outTradeOrderInfo.getSign()) || TextUtils.isEmpty(outTradeOrderInfo.getSignType()) || TextUtils.isEmpty(outTradeOrderInfo.getSubject()) || TextUtils.isEmpty(outTradeOrderInfo.getTotalFee())) ? null : outTradeOrderInfo;
    }

    public String getBody() {
        return this.mBody;
    }

    public String getExtContent() {
        return this.mExtContent;
    }

    public String getLabel1() {
        return this.mLabel1;
    }

    public String getLabel2() {
        return this.mLabel2;
    }

    public String getNotifyUrl() {
        return this.mNotifyUrl;
    }

    public String getOutTrade() {
        return this.mOutTrade;
    }

    public String getPartner() {
        return this.mPartner;
    }

    public String getPayAccounts() {
        return this.mPayAccounts;
    }

    public String getSign() {
        return this.mSign;
    }

    public String getSignType() {
        return this.mSignType;
    }

    public String getSubject() {
        return this.mSubject;
    }

    public String getTotalFee() {
        return this.mTotalFee;
    }

    public OutTradeOrderInfo setBody(String str) {
        this.mBody = str;
        return this;
    }

    public OutTradeOrderInfo setExtContent(String str) {
        this.mExtContent = str;
        return this;
    }

    public OutTradeOrderInfo setLabel1(String str) {
        this.mLabel1 = str;
        return this;
    }

    public OutTradeOrderInfo setLabel2(String str) {
        this.mLabel2 = str;
        return this;
    }

    public OutTradeOrderInfo setNotifyUrl(String str) {
        this.mNotifyUrl = str;
        return this;
    }

    public OutTradeOrderInfo setOutTrade(String str) {
        this.mOutTrade = str;
        return this;
    }

    public OutTradeOrderInfo setPartner(String str) {
        this.mPartner = str;
        return this;
    }

    public OutTradeOrderInfo setPayAccounts(String str) {
        this.mPayAccounts = str;
        return this;
    }

    public OutTradeOrderInfo setSign(String str) {
        this.mSign = str;
        return this;
    }

    public OutTradeOrderInfo setSignType(String str) {
        this.mSignType = str;
        return this;
    }

    public OutTradeOrderInfo setSubject(String str) {
        this.mSubject = str;
        return this;
    }

    public OutTradeOrderInfo setTotalFee(String str) {
        this.mTotalFee = str;
        return this;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_EXT_CONTENT, getExtContent());
        bundle.putString(KEY_LABEL_1, getLabel1());
        bundle.putString(KEY_LABEL_2, getLabel2());
        bundle.putString(KEY_BODY, getBody());
        bundle.putString(KEY_NOTIFY_URL, getNotifyUrl());
        bundle.putString(KEY_OUT_TRADE, getOutTrade());
        bundle.putString(KEY_PARTNER, getPartner());
        bundle.putString(KEY_PAY_ACCOUNTS, getPayAccounts());
        bundle.putString(KEY_SIGN, getSign());
        bundle.putString(KEY_SIGN_TYPE, getSignType());
        bundle.putString(KEY_SUBJECT, getSubject());
        bundle.putString(KEY_TOTAL_FEE, getTotalFee());
        return bundle;
    }
}
