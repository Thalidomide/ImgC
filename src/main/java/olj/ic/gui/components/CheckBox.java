package olj.ic.gui.components;

import javax.swing.JCheckBox;

import olj.ic.util.Constants;

/**
 * @author Olav Jensen
 * @since Apr 27, 2010
 */
public class CheckBox extends JCheckBox {

	public CheckBox() {
		init();
	}
	
	public CheckBox(String text) {
		super(text);
		init();
	}

	private void init() {
		setFont(Constants.NORMAL);
		setForeground(Constants.FONT);
		setBackground(Constants.BACKGROUND);
	}
}
