package sdk.meizu.traffic.hybird.exception;

public class NativeMethodError extends Exception {
    public NativeMethodError(Throwable th) {
        super(th);
    }

    public NativeMethodError(String str) {
        super(str);
    }

    public NativeMethodError(String str, Throwable th) {
        super(str, th);
    }
}
