package ivan.takeout.common;

/**
 * @author Maximilian_Li
 */
public class ServiceException extends RuntimeException {
    public ServiceException(String errMsg) {
        super(errMsg);
    }
}
