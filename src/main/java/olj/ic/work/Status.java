package olj.ic.work;

import olj.ic.util.Util;

/**
 * @author Olav Jensen
 * @since 10.apr.2010
 */
class Status {

	private final StatusType type;
	private final String description;
	private boolean active;

	Status(StatusType type, boolean active, String description) {
		this.type = type;
		this.active = active;
		this.description = description;
	}

	StatusType getType() {
		return type;
	}

	boolean isActive() {
		return active;
	}

	void setActive(boolean active) {
		this.active = active;
	}

	boolean isDifferent(Status status) {
		if (status == null) {
			return true;
		}

		return type != status.type || Util.stringsAreDifferent(description, status.description);
	}

	@Override
	public String toString() {
		return type.getText() + ", Active: " + active;
	}

	public String getDescription() {
		return description;
	}
}
