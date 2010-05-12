package olj.ic.gui.components;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import olj.ic.util.Constants;

/**
 * @author Olav Jensen
 * @since 07.mai.2010
 */
public class DoubleTextField extends TextField {

	private boolean optional = true;
	private Double min;
	private Double max;

	public DoubleTextField() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				updateValidStyle();
			}
		});
		updateValidStyle();
	}

	public Double getDouble() {
		String text = getText();
		if ("".equals(text)) {
			return null;
		}

		return Double.parseDouble(text);
	}

	public void setDouble(Double value) {
		super.setText(value != null ? value.toString() : "");
		updateValidStyle();
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	public void setMax(Double max) {
		this.max = max;
	}

	/**
	 * @deprecated use {@link #setDouble(Double)} instead
	 */
	@Override
	@Deprecated
	public void setText(String text) {
		super.setText(text);
	}

	public boolean isValidDouble() {
		Double value;
		try {
			value = getDouble();
		} catch (NumberFormatException e) {
			return false;
		}

		if (value != null) {
			if (min != null && min > value || max != null && max < value) {
				return false;
			}
		}

		return value != null || optional;
	}

	private void updateValidStyle() {
		setBackground(isValidDouble() ? Constants.BACKGROUND_INPUT : Constants.BACKGROUND_INPUT_ERROR);
	}
}
