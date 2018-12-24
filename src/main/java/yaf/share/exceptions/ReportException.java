/**
 *
 */
package yaf.share.exceptions;

/**
 * @author pskopek
 *
 */
public class ReportException extends Exception {

    static final long serialVersionUID = 140056450906375857L;

    /**
     *
     */
    public ReportException() {
    }

    /**
     * @param message
     */
    public ReportException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public ReportException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public ReportException(String message, Throwable cause) {
        super(message, cause);
    }

}
