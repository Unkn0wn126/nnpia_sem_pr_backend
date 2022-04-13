package cz.upce.fei.sem_pr_backend.exception;

public class ExpiredJWTTokenException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ExpiredJWTTokenException(String msg) {
        super(msg);
    }
}
