package com.nightswatch.service.exception;

public class IllegalTagNameException extends AbstractRuntimeException {

    private final String tagName;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param tagName that is illegal
     */
    public IllegalTagNameException(String tagName) {
        super("TagName '" + tagName + "' is illegal");
        this.tagName = tagName;
    }

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param tagName that is illegal
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @since 1.4
     */
    public IllegalTagNameException(String tagName, Throwable cause) {
        super("TagName '" + tagName + "' is illegal", cause);
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }
}
