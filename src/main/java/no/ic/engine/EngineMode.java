package engine;

/**
 * @author Olav Jensen
 * @since 11.apr.2010
 */
public enum EngineMode {

	composite("Composite"), manipulate("Manipulate");

	private final String text;

	EngineMode(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
