package executor;

public  class YError {
    public Exception getException() {
        return exception;
    }

    private Exception exception;

    public int getErrorCode() {
        return errorCode;
    }

    public YError setErrorCode(int errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    private int errorCode = -1;

    public YError(Exception exception) {
        this.exception = exception;
    }

}