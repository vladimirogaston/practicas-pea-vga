package ar.vga.com.mapper;

public class ApplicationException extends RuntimeException {
    private static final String message = "Application exception ";
    public ApplicationException(final String detail) {
        super(detail);
    }
}
