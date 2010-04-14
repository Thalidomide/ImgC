package olj.ic.engine;

/**
 * @author Olav Jensen
 * @since 11.apr.2010
 */
public enum EngineMode {

	composite("Composite", ImageCompositor.class), manipulate("Manipulate", ImageManipulator.class);

	private final String text;
	private final Class<? extends ImageEngine> engineClass;

	EngineMode(String text, Class<? extends ImageEngine> engineClass) {
		this.text = text;
		this.engineClass = engineClass;
	}

	public String getText() {
		return text;
	}

	public Class<? extends ImageEngine> getEngineClass() {
		return engineClass;
	}
}
