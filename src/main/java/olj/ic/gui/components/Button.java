package olj.ic.gui.components;

import javax.swing.JButton;

import olj.ic.util.Constants;

/**
 * @author Olav Jensen
 * @since Apr 27, 2010
 */
public class Button extends JButton {

	public Button(String text) {
		super(text);

		setBackground(Constants.BACKGROUND_BUTTON);
		setForeground(Constants.FONT);
	}
}
