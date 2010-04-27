package olj.ic.gui.components;

import javax.swing.JLabel;

import olj.ic.util.Constants;

/**
 * @author Olav Jensen
 * @since Apr 27, 2010
 */
public class Label extends JLabel {

	public Label(String text) {
		super(text);
		setForeground(Constants.FONT);
		setFont(Constants.NORMAL);
	}
}
