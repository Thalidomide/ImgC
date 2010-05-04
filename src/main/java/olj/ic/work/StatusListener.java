package olj.ic.work;

/**
 * @author Olav Jensen
 * @since 09.apr.2010
 */
public interface StatusListener {

	void statusChanged(StatusType statusType, String description);
}
