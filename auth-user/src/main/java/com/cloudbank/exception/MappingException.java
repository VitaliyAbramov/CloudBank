package com.cloudbank.exception;

/**
 * Class represents exception which throws when mapping was failed
 *
 * @author Vitalii Abramov
 */
public class MappingException extends RuntimeException {
    /**
     * Create instance of MappingException with message
     *
     * @param message the detailed message
     */
    public MappingException(String message) {
        super(message);
    }

    /**
     * Create instance of MappingException with message and cause
     *
     * @param message the detailed message
     * @param cause   exception cause
     */
    public MappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
