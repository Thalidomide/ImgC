package olj.ic.gui.components;

import javax.swing.JTextArea;

import olj.ic.util.Constants;

/**
 * @author Olav Jensen
 * @since Apr 27, 2010
 */
public class TextArea extends JTextArea {

	public TextArea() {
		setFont(Constants.SMALLER);
		setForeground(Constants.FONT);
		setBackground(Constants.BACKGROUND_INPUT);
	}

    public void appendText(String text) {
         String oldValue = getText();

        if (!"".equals(oldValue)) {
            oldValue += "\n";
        }
        setText(oldValue + text);
    }
}
