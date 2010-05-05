package olj.ic.work;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import olj.ic.util.Constants;
import olj.ic.util.Manager;

/**
 * @author Olav Jensen
 * @since 09.apr.2010
 */
public class WorkHandler {

	private StatusType currentStatus;

	private final StatusListener listener;
	private int pool = Constants.MIN_WORKING_THREADS;

	private ExecutorService executor = createExecutor();

	private int workRemaining;

	public WorkHandler(StatusListener listener) {
		this.listener = listener;
		setCurrentStatusAndNotifyListenerIfTypeChanged(StatusType.idle);
	}

	public synchronized void doWork(final WorkPackage workPackage) {
		int threads = Manager.get().getEngineSettings().getThreads();
		if (currentStatus == StatusType.idle || pool != threads) {
			pool = threads;
			executor = createExecutor();
		}

		if (workPackage.getWorkList().isEmpty()) {
			return;
		}

		setCurrentStatusAndNotifyListenerIfTypeChanged(StatusType.working);

		workRemaining += workPackage.getWorkList().size();

		startWorkPackage(workPackage);
	}

	private void startWorkPackage(final WorkPackage workPackage) {
		workPackage.start();

		for (final Work work : workPackage.getWorkList()) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						work.executeWork();
					} finally {
						workCompleted(work, workPackage);
					}
				}
			});
		}
	}

	private ExecutorService createExecutor() {
		return Executors.newFixedThreadPool(pool);
	}

	private synchronized void workCompleted(Work work, WorkPackage workPackage) {
		workRemaining--;
		workPackage.completedWork(work);

		if (workRemaining < 0) {
			throw new IllegalStateException("Remaining work = " + workRemaining);
		} else if (workRemaining == 0) {
			setCurrentStatusAndNotifyListenerIfTypeChanged(StatusType.idle);
			executor.shutdown();
			executor = null;
		}
	}

	private void setCurrentStatusAndNotifyListenerIfTypeChanged(StatusType status) {
		if (currentStatus == null || status != currentStatus) {
			listener.statusChanged(status, status.getText());
		}
		currentStatus = status;
	}
}
