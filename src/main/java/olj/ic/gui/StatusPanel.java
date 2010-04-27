package olj.ic.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JScrollPane;

import olj.ic.gui.components.Panel;
import olj.ic.gui.components.TextArea;
import olj.ic.status.StatusType;
import olj.ic.util.Constants;

/**
 * @author Olav Jensen
 * @since 09.apr.2010
 */
public class StatusPanel extends Panel {

	private StatusLabelPanel statusLabelPanel;
	private TextArea statusArea;

	public StatusPanel() {
		super(new BorderLayout());

		statusLabelPanel = new StatusLabelPanel();
		statusArea = new TextArea();
		statusArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(statusArea);
		scrollPane.setPreferredSize(new Dimension(300, 100));
		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				e.getAdjustable().setValue(e.getAdjustable().getMaximum());
			}
		});

		add(statusLabelPanel, BorderLayout.SOUTH);
		add(scrollPane, BorderLayout.CENTER);

		updateStatus(StatusType.idle, null);
	}

	public void addMessage(String message) {
		statusArea.setText(statusArea.getText() + message + "\n");
	}

	public void updateStatus(StatusType statusType, String description) {
		statusLabelPanel.setStatus(statusType, description);
	}
}

class StatusLabelPanel extends Panel {

	private StatusType statusType;
	private String statusDescription;
	private int height;

	StatusLabelPanel() {
		statusType = StatusType.idle;
		repaint();
		height = 25;
		setPreferredSize(new Dimension(300, height));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		int diameter = 14;
		int x = 20;
		int y = (height - diameter) / 2;

		g.setColor(StatusType.idle == statusType ? Color.GREEN : Color.RED);
		g.fillOval(x, y, diameter, diameter);
		g.setColor(Color.BLACK);
		g.drawOval(x, y, diameter, diameter);

		g.setColor(Constants.FONT);
		Font font = Constants.NORMAL;
		x += diameter + 10;
		y = (height + font.getSize()) / 2;

		g.setFont(font);
		g.drawString(getStatusText(), x, y);
	}

	private String getStatusText() {
		String text = statusType.getText();
		if (statusDescription != null) {
			text += " (" + statusDescription + ")";
		}
		return text;
	}

	public void setStatus(StatusType statusType, String description) {
		this.statusType = statusType;
		this.statusDescription = description;
		repaint();
	}
}