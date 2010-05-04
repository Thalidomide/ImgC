package olj.ic.work;

import junit.framework.TestCase;
import olj.ic.util.Manager;

/**
 * @author Olav Jensen
 * @since 10.apr.2010
 */
public class WorkHandlerTest extends TestCase implements StatusListener {

	private StatusType statusType;
	private int statusChangedCounter;

	@Override
	protected void setUp() throws Exception {
		statusType = null;
		statusChangedCounter = 0;
	}

	public void testHandlingStatuses() {
		WorkHandler workHandler = new WorkHandler(this);
		class TestWork implements Work {

			private final long workTime;

			TestWork(long workTime) {
				this.workTime = workTime;
			}

			@Override
			public void executeWork() {
				sleep(workTime);
			}
		}

		Manager.get().getEngineSettings().setThreads(1);

		assertEquals(StatusType.idle, statusType);
		assertEquals(1, statusChangedCounter);

		workHandler.doWork(new WorkPackage(null, new TestWork(30)));
		assertEquals(StatusType.working, statusType);
		assertEquals(2, statusChangedCounter);

		workHandler.doWork(new WorkPackage(null, new TestWork(30)));
		assertEquals(StatusType.working, statusType);
		assertEquals(2, statusChangedCounter);

		// Sleep until work #2 is done
		sleep(35);

		// Status should not have been changed after work #2 completed
		assertEquals(StatusType.working, statusType);
		assertEquals(2, statusChangedCounter);

		// Sleep until work #1 is done
		sleep(35);

		// After work #1 completed status should have changed back to idle
		assertEquals(StatusType.idle, statusType);
		assertEquals(3, statusChangedCounter);
	}

	private void sleep(final long sleepTime) {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void statusChanged(StatusType statusType, String description) {
		this.statusType = statusType;
		statusChangedCounter ++;
	}
}
