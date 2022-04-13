package cz.upce.fei.sem_pr_backend.exception;

public class MissingJWTTokenException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MissingJWTTokenException(String msg) {
        super(msg);
    }
}
