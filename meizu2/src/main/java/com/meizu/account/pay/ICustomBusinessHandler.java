package com.meizu.account.pay;

public interface ICustomBusinessHandler {
    String handleAccountChange(String str);

    CustomBusinessResult handleCustomBusiness(String str, String str2);
}
