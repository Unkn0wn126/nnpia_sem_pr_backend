package cz.upce.fei.sem_pr_backend.exception;

public class UnauthorizedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnauthorizedException(String msg) {
        super(msg);
    }
}
