package sdk.meizu.traffic.hybird.exception;

public class NativeParseError extends NativeMethodError {
    public NativeParseError(String str) {
        super(str);
    }

    public NativeParseError(Throwable th) {
        super(th);
    }

    public NativeParseError(String str, Throwable th) {
        super(str, th);
    }
}
