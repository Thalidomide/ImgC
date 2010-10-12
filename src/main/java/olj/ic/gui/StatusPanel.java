package olj.ic.gui;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JScrollPane;

import olj.ic.gui.components.Panel;
import olj.ic.gui.components.TextArea;
import olj.ic.gui.util.GuiUtil;
import olj.ic.work.StatusType;
import olj.ic.util.Constants;

/**
 * @author Olav Jensen
 * @since 09.apr.2010
 */
public class StatusPanel extends Panel {

	private StatusLabelPanel statusLabelPanel;
	private TextArea statusArea;
    private Calendar calendar = new GregorianCalendar();
    private JScrollPane scrollPane;

    public StatusPanel() {
		super(new BorderLayout());

		statusLabelPanel = new StatusLabelPanel();
		statusArea = new TextArea();
		statusArea.setEditable(false);

        scrollPane = new JScrollPane(statusArea);
		scrollPane.setPreferredSize(new Dimension(300, 100));

        add(scrollPane, BorderLayout.CENTER);
        add(statusLabelPanel, BorderLayout.SOUTH);

		updateStatus(StatusType.idle, null);
	}

	public synchronized void addMessage(String message) {
		calendar.setTimeInMillis(System.currentTimeMillis());
		String sTimestamp = timeVal(Calendar.HOUR_OF_DAY, 2) + ":" + timeVal(Calendar.MINUTE, 2) + ":"
				+ timeVal(Calendar.SECOND, 2) + ":" + timeVal(Calendar.MILLISECOND, 3);

		statusArea.appendText("<" + sTimestamp + "> " + message);
        scrollToBottom();
	}

    private void scrollToBottom() {
        statusArea.validate();
        scrollPane.getVerticalScrollBar().setValue(Integer.MAX_VALUE);
    }

    private String timeVal(int value, int length) {
		return GuiUtil.asString(calendar.get(value), length);
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