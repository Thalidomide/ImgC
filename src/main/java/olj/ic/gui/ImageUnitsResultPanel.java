package olj.ic.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;

import olj.ic.entities.ImageUnit;
import olj.ic.gui.components.Label;
import olj.ic.gui.components.Panel;
import olj.ic.util.Constants;

/**
 * @author Olav Jensen
 * @since 09.apr.2010
 */
public class ImageUnitsResultPanel extends Panel {

	private Panel resultPanel;
	private Label resultLabel;
	private List<ImageUnitPanel> unitPanels = new ArrayList<ImageUnitPanel>();

	public ImageUnitsResultPanel() {
		super(new BorderLayout());

		resultLabel = new Label("No images loaded.");
		resultPanel = new Panel();
		resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
		JScrollPane scroll = new JScrollPane(resultPanel);
		scroll.setAutoscrolls(true);

		Panel resultLabelPanel = new Panel();
		resultLabelPanel.add(resultLabel);

		add(resultLabelPanel, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
	}

	public synchronized void setPairs(List<ImageUnit> units) {
		unitPanels.clear();

		int index = 0;
		for (ImageUnit unit : units) {
			Color color = index % 2 == 0 ? Constants.BACKGROUND_LIGHT : Constants.BACKGROUND_LIGHTER;
			ImageUnitPanel unitPanel = new ImageUnitPanel(unit, color, index);
			unitPanels.add(unitPanel);
			index++;
		}

		updateResultLabel(units);

		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		resultPanel.removeAll();
		for (ImageUnitPanel unitPanel : unitPanels) {
			resultPanel.add(unitPanel);
		}
		resultPanel.repaint();
		validate();
	}

	public List<ImageUnit> getImageUnits() {
		List<ImageUnit> pairs = new ArrayList<ImageUnit>();

		for (ImageUnitPanel unitPanel : unitPanels) {
			unitPanel.addImageIfActive(pairs);
		}

		return pairs;
	}

	private void updateResultLabel(List<ImageUnit> units) {
		String text;

		if (units.isEmpty()) {
			text = "No images found.";
		} else {
			text = "Images found: " + units.size() + ".";
		}

		resultLabel.setText(text);
	}
}

