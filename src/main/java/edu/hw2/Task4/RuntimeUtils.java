package edu.hw2.Task4;

public final class RuntimeUtils {

    public static CallingInfo callingInfo() {
        var throwable = new Throwable();
        var stackTrace = throwable.getStackTrace();
        var traceElement = stackTrace[1];
        return new CallingInfo(traceElement.getClassName(), traceElement.getMethodName());
    }

    private RuntimeUtils() {
    }

}
