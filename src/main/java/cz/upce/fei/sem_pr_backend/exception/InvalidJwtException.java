package cz.upce.fei.sem_pr_backend.exception;

public class InvalidJwtException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidJwtException(String msg) {
        super(msg);
    }
}
