package olj.ic.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.border.BevelBorder;

import olj.ic.entities.ImageComponent;
import olj.ic.entities.ImageUnit;
import olj.ic.gui.components.Label;
import olj.ic.gui.components.Panel;
import olj.ic.work.Work;
import olj.ic.util.Constants;
import olj.ic.util.Manager;
import olj.ic.work.WorkPackage;

/**
 * @author Olav Jensen
 * @since 09.apr.2010
 */
public class ImageUnitPanel extends Panel {

	private final Color background;

	public ImageUnitPanel(final ImageUnit unit, Color background, int index) {
		super();
		this.background = background;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(background);
		setBorder(new BevelBorder(BevelBorder.LOWERED));

		Label titleLabel = new Label((index + 1) + ". image unit: " + unit.getName());
		titleLabel.setFont(Constants.HEADER_2);
		titleLabel.addMouseListener(getMouseListener("Generating preview for " + unit.getName(), new Work() {

			@Override
			public void executeWork() {
				Manager.get().showPreview(unit.getImageResult(), "Showing preview of " + unit.getName());
			}
		}));
		addWrapped(titleLabel);
		
		for (final ImageComponent component : unit.getComponents()) {
			final String fileName = component.getFileName();
			Label label = new Label(fileName);
			label.addMouseListener(getMouseListener(null, new Work() {

				@Override
				public void executeWork() {
					Manager.get().showPreview(component.getImage(), fileName);
				}
			}));
			addWrapped(label);
		}
	}

	private void addWrapped(Component component) {
		Panel panel = new Panel();
		panel.setBackground(background);

		panel.add(component);
		add(panel);
	}

	private MouseAdapter getMouseListener(final String description, final Work work) {
		return new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				Manager.get().getWorkHandler().doWork(new WorkPackage(description, work));
			}
		};
	}
}
