package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import entities.ImageUnit;
import util.Constants;

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
			ImageUnitPanel unitPanel = new ImageUnitPanel(unit, index);
			unitPanel.setBackground(index % 2 == 0 ? Constants.EVEN_ROW : Constants.ODD_ROW);
			resultPanel.add(unitPanel);
			index++;
		}

		resultPanel.repaint();
		updateResultLabel();
	}

	public List<ImageUnit> getPairs() {
		return units;
	}

	private void updateResultLabel() {
		String text;

		if (units.isEmpty()) {
			text = "No image units found.";
		} else {
			text = "Found " + units.size() + " image units.";
		}

		resultLabel.setText(text);
	}
}

