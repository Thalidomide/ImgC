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
}
