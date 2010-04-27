package olj.ic.gui;

import java.awt.BorderLayout;
import java.awt.Color;
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

	private List<ImageUnit> units = new ArrayList<ImageUnit>();
	private Panel resultPanel;
	private Label resultLabel;

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

	public void setPairs(List<ImageUnit> units) {
		this.units = units;
		resultPanel.removeAll();
		if (units.isEmpty()) {
			updateResultLabel();
		}

		int index = 0;
		for (ImageUnit unit : units) {
			Color color = index % 2 == 0 ? Constants.EVEN_ROW : Constants.ODD_ROW;
			ImageUnitPanel unitPanel = new ImageUnitPanel(unit, color, index);
			resultPanel.add(unitPanel);
			index++;
		}

		updateResultLabel();
	}

	public List<ImageUnit> getPairs() {
		return units;
	}

	private void updateResultLabel() {
		String text;

		if (units.isEmpty()) {
			text = "No images found.";
		} else {
			text = "Images found: " + units.size() + ".";
		}

		resultLabel.setText(text);
	}
}

