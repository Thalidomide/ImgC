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

import entities.ImagePair;
import util.Constants;

/**
 * @author Olav Jensen
 * @since 09.apr.2010
 */
public class ImagePairsResultPanel extends JPanel {

	private List<ImagePair> pairs = new ArrayList<ImagePair>();
	private JPanel resultPanel;
	private JLabel resultLabel;

	public ImagePairsResultPanel() {
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

	public void setPairs(List<ImagePair> pairs) {
		this.pairs = pairs;
		resultPanel.removeAll();
		if (pairs.isEmpty()) {
			updateResultLabel();
		}

		int index = 0;
		for (ImagePair pair : pairs) {
			ImagePairPanel pairPanel = new ImagePairPanel(pair, index);
			pairPanel.setBackground(index % 2 == 0 ? Constants.EVEN_ROW : Constants.ODD_ROW);
			resultPanel.add(pairPanel);
			index++;
		}

		resultPanel.repaint();
		updateResultLabel();
	}

	public List<ImagePair> getPairs() {
		return pairs;
	}

	private void updateResultLabel() {
		String text;

		if (pairs.isEmpty()) {
			text = "No image pairs found.";
		} else {
			text = "Found " + pairs.size() + " image pairs.";
		}

		resultLabel.setText(text);
	}
}

