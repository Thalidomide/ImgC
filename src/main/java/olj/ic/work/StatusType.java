package olj.ic.work;

/**
 * @author Olav Jensen
 * @since 09.apr.2010
 */
public enum StatusType {
	idle("Idle", true), working("Working", false);

	private final String text;
	private final boolean guiEnabled;

	private StatusType(String text, boolean guiEnabled) {
		this.text = text;
		this.guiEnabled = guiEnabled;
	}

	public String getText() {
		return text;
	}

	public boolean isGuiEnabled() {
		return guiEnabled;
	}
}
