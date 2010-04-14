package olj.ic.status;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Olav Jensen
 * @since 09.apr.2010
 */
public class StatusHandler {

	private List<Status> statuses = new ArrayList<Status>();
	private Status currentStatus;
	private final Status defaultStatus = new Status(StatusType.idle, true, null);

	private final StatusListener listener;

	public StatusHandler(StatusListener listener) {
		this.listener = listener;
		setCurrentStatusAndNotifyListenerIfTypeChanged(defaultStatus);
	}

	public void doWork(StatusType statusType, final Work work) {
	    doWork(statusType, work, null);
	}

	public void doWork(StatusType statusType, final Work work, String description) {
		final Status status = new Status(statusType, true, description);
		statuses.add(status);

		setCurrentStatusAndNotifyListenerIfTypeChanged(status);

		final Thread workThread = new Thread() {
			@Override
			public void run() {
				super.run();
				try {
					work.executeWork();
				} finally {
					removeStatus(status);
				}
			}
		};
		workThread.start();
	}

	synchronized void removeStatus(Status statusToRemove) {
		boolean existed = statuses.remove(statusToRemove);
		if (!existed) {
			throw new RuntimeException("The ic.status was not present (ic.status: " + statusToRemove.toString() + ")");
		}
		setCurrentStatusAndNotifyListenerIfTypeChanged(getNextStatus());
	}

	private Status getNextStatus() {
		Status status;

		if (statuses.isEmpty()) {
			status = defaultStatus;
		} else {
			status = statuses.get(statuses.size() - 1);
		}

		status.setActive(true);

		return status;
	}

	private void setCurrentStatusAndNotifyListenerIfTypeChanged(Status status) {
		if (status.isActive() && currentStatus == null || status.isDifferent(currentStatus)) {
			listener.statusChanged(status.getType(), status.getDescription());
		}
		if (currentStatus != null) {
			currentStatus.setActive(false);
		}
		currentStatus = status;
	}
}
