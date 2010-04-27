package olj.ic.gui.components;

import java.awt.LayoutManager;
import javax.swing.JPanel;

import olj.ic.util.Constants;

/**
 * @author Olav Jensen
 * @since Apr 27, 2010
 */
public class Panel extends JPanel {

	public Panel() {
		init();
	}

	public Panel(LayoutManager layout) {
		super(layout);
		init();
	}

	private void init() {
		setBackground(Constants.BACKGROUND);
	}
}
