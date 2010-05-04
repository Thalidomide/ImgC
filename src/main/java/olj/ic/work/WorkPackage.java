package olj.ic.work;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import olj.ic.util.Manager;

/**
 * @author Olav Jensen
 * @since May 4, 2010
 */
public class WorkPackage {

	private final String description;
	private final List<Work> workList;
	private final List<Work> completedWorkList;

	private long startTime;

	public WorkPackage(String description, Work work) {
		this(description, Arrays.asList(work));
	}
	
	public WorkPackage(String description, List<Work> workList) {
		this.description = description;
		this.workList = workList;
		completedWorkList = new ArrayList<Work>(workList.size());
	}

	public void start() {
		startTime = System.nanoTime();
		logOnStart();
	}

	public synchronized void completedWork(Work work) {
		if (!workList.contains(work)) {
			throw new IllegalArgumentException("Work " + work + " did not relate to this " + this);
		} else if (completedWorkList.contains(work)) {
			throw new IllegalStateException("Work " + work + " was allready completed");
		}

		completedWorkList.add(work);

		if (completedWorkList.size() == workList.size()) {
			logOnComplete();
		}
	}

	private void logOnStart() {
		if (description != null) {
			Manager.get().logMessage(description + " started.");
		}
	}

	private void logOnComplete() {
		if (description != null) {
			double seconds = (double)(System.nanoTime() - startTime) / 1000000000L;
			DecimalFormat nf = new DecimalFormat("#.###");
			Manager.get().logMessage(description + " finished in: " + nf.format(seconds) + " seconds.");
		}
	}

	public String getDescription() {
		return description;
	}

	public List<Work> getWorkList() {
		return workList;
	}
}
