package olj.ic.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import olj.ic.entities.ImageUnit;
import olj.ic.status.StatusType;
import olj.ic.status.Work;
import olj.ic.util.Constants;
import olj.ic.util.Manager;

/**
 * @author Olav Jensen
 * @since 09.apr.2010
 */
public class ImageUnitPanel extends JPanel {

	private final ImageUnit unit;
	private final Color background;

	public ImageUnitPanel(ImageUnit unit, Color background, int index) {
		super();
		this.unit = unit;
		this.background = background;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(background);

		JLabel titleLabel = new JLabel((index + 1) + ". image unit: " + unit.getName());
		titleLabel.setFont(Constants.HEADER_2);

		addWrapped(titleLabel);
		addWrapped(new JLabel("Images: " + unit.getImageFileNames()));
		addWrapped(getPreviewButton());
	}

	private void addWrapped(Component component) {
		JPanel panel = new JPanel();
		panel.setBackground(background);

		panel.add(component);
		add(panel);
	}

	private JButton getPreviewButton() {
		JButton button = new JButton("Preview");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				Work work = new Work() {
					@Override
					public void executeWork() {
						Manager.get().showPreview(unit.getImageResult(), "Showing preview of " + unit.getName());
					}
				};

				Manager.get().getStatusHandler().doWork(StatusType.working, work, "Generating preview");
			}
		});
		return button;
	}
}
