package status;

import junit.framework.TestCase;

/**
 * @author Olav Jensen
 * @since 10.apr.2010
 */
public class StatusHandlerTest extends TestCase implements StatusListener {

	private StatusType statusType;
	private int statusChangedCounter;

	@Override
	protected void setUp() throws Exception {
		statusType = null;
		statusChangedCounter = 0;
	}

	public void testHandlingStatuses() {
		StatusHandler statusHandler = new StatusHandler(this);
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

		assertEquals(StatusType.idle, statusType);
		assertEquals(1, statusChangedCounter);

		statusHandler.doWork(StatusType.working, new TestWork(50));
		assertEquals(StatusType.working, statusType);
		assertEquals(2, statusChangedCounter);

		statusHandler.doWork(StatusType.working, new TestWork(20));
		assertEquals(StatusType.working, statusType);
		assertEquals(2, statusChangedCounter);

		// Sleep until work #2 is done
		sleep(30);

		// Status should not have been changed after work #2 completed
		assertEquals(StatusType.working, statusType);
		assertEquals(2, statusChangedCounter);

		// Sleep until work #1 is done
		sleep(30);

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
