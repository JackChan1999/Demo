package com.meizu.account.pay;

public class PayResultCode {
    public static final int PAY_ERROR_AUTH_ERROR = 4;
    public static final int PAY_ERROR_BAD_REQUEST = 3;
    public static final int PAY_ERROR_CANCEL = 2;
    public static final int PAY_ERROR_EXCEPTION = 101;
    public static final int PAY_ERROR_NETWORK_ERROR = 1;
    public static final int PAY_ERROR_SERVICE_NOT_AVAILABLE = 100;
    public static final int PAY_SUCCESS = 0;

    public static int fixCode(int i) {
        return (i <= 0 || i > 4) ? 101 : i;
    }
}
