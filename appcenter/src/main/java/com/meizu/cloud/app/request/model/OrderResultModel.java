package com.meizu.cloud.app.request.model;

import com.meizu.cloud.app.request.model.DownloadInfo.DownloadUrlEx;
import java.util.List;

public class OrderResultModel {

    public static class HadPay {
        public String digest;
        public String download_url;
        public List<DownloadUrlEx> download_urls;
        public String license;
        public String package_name;
        public boolean paid;
        public long size;
        public int verify_mode;
        public int version_code;
    }

    public static class NoPay<T extends Receipt> {
        public boolean paid;
        public T receipt;
    }

    public static class ProductHadPay {
        public String license;
        public int status;
    }

    public static class ProductNoPay<T extends Receipt> {
        public T receipt;
        public int status;
    }

    public static class Receipt {
        public String body;
        public String ext_content;
        public String notify_url;
        public String out_trade_no;
        public String partner;
        public String pay_accounts;
        public String sign;
        public String sign_type;
        public String subject;
        public String total_fee;
    }
}
