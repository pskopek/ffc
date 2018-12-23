/**
 * 
 */
package yaf.reporting;

/**
 * @author pskopek
 *
 */
public class DataSetEmptyException extends RuntimeException {
  
  static final long serialVersionUID = -393575645857727766L;
  
  public DataSetEmptyException() {
    super();
  }

  public DataSetEmptyException(String message, Throwable cause) {
    super(message, cause);
  }

  public DataSetEmptyException(String message) {
    super(message);
  }

  public DataSetEmptyException(Throwable cause) {
    super(cause);
  }

}
