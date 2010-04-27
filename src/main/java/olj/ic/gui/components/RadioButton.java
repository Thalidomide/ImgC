package olj.ic.gui.components;

import javax.swing.JRadioButton;

import olj.ic.util.Constants;

/**
 * @author Olav Jensen
 * @since Apr 27, 2010
 */
public class RadioButton extends JRadioButton {

	public RadioButton(String text) {
		super(text);
		setFont(Constants.NORMAL);
		setForeground(Constants.FONT);
		setBackground(Constants.BACKGROUND);
	}
}
