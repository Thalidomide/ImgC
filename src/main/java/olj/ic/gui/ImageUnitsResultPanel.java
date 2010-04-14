package olj.ic.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import olj.ic.entities.ImageUnit;
import olj.ic.util.Constants;

/**
 * @author Olav Jensen
 * @since 09.apr.2010
 */
public class ImageUnitsResultPanel extends JPanel {

	private List<ImageUnit> units = new ArrayList<ImageUnit>();
	private JPanel resultPanel;
	private JLabel resultLabel;

	public ImageUnitsResultPanel() {
		super(new BorderLayout());

		resultLabel = new JLabel("No images loaded.");
		resultPanel = new JPanel();
		resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
		JScrollPane scroll = new JScrollPane(resultPanel);
		scroll.setAutoscrolls(true);

		resultPanel.setBackground(Color.WHITE);

		JPanel resultLabelPanel = new JPanel();
		resultLabelPanel.add(resultLabel);

		add(resultLabelPanel, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
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

