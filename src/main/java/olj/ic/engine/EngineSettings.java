package olj.ic.engine;

import olj.ic.util.Constants;

/**
 * @author Olav Jensen
 * @since Apr 12, 2010
 */
public class EngineSettings {

	private boolean leftRightReversed;
	private EngineMode engineMode = EngineMode.composite;
	private int imageParts = Constants.MIN_IMAGE_PARTS;
	private int threads = Constants.MIN_WORKING_THREADS;
	private double scaleX = 1;
	private double scaleY = 1;

	public boolean isLeftRightReversed() {
		return leftRightReversed;
	}

	public void setLeftRightReversed(boolean leftRightReversed) {
		this.leftRightReversed = leftRightReversed;
	}

	public EngineMode getEngineMode() {
		return engineMode;
	}

	public void setEngineMode(EngineMode engineMode) {
		this.engineMode = engineMode;
	}

	public int getImageParts() {
		return imageParts;
	}

	public void setImageParts(int imageParts) {
		this.imageParts = imageParts;
	}

	public int getThreads() {
		return threads;
	}

	public void setThreads(int threads) {
		this.threads = threads;
	}

	public double getScaleX() {
		return scaleX;
	}

	public void setScaleX(double scaleX) {
		this.scaleX = scaleX;
	}

	public double getScaleY() {
		return scaleY;
	}

	public void setScaleY(double scaleY) {
		this.scaleY = scaleY;
	}
}
