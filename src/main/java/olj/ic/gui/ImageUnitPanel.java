package olj.ic.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import olj.ic.entities.ImageComponent;
import olj.ic.entities.ImageUnit;
import olj.ic.gui.components.CheckBox;
import olj.ic.gui.components.Label;
import olj.ic.gui.components.Panel;
import olj.ic.util.Constants;
import olj.ic.util.Manager;
import olj.ic.work.Work;
import olj.ic.work.WorkPackage;

/**
 * @author Olav Jensen
 * @since 09.apr.2010
 */
public class ImageUnitPanel {

	private final ImageUnit unit;
	private final Color background;
	private CheckBox useImage;
    private Panel contentCheckBox;
    private Panel content;
	private List<Label> labels = new ArrayList<Label>();

	public ImageUnitPanel(final ImageUnit unit, Color background, int index) {
		super();
		this.unit = unit;
		this.background = background;

        contentCheckBox = getPanel();
        content = getPanel();

        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

		addTitle(index);
        addUseImage();
		addImages();
	}

    public void addImageIfActive(List<ImageUnit> units) {
		if (useImage.isSelected()) {
			units.add(unit);
		}
	}

	private void addTitle(int index) {
		Label titleLabel = getLabel((index + 1) + ". image unit: " + unit.getName());
		titleLabel.setFont(Constants.HEADER_2);
		titleLabel.addMouseListener(getMouseListener("Generating preview for " + unit.getName(), new Work() {

			@Override
			public void executeWork() {
				Manager.get().showPreview(unit.getImageResult(), "Showing preview of " + unit.getName());
			}
		}));

		content.add(titleLabel);
	}

    private void addUseImage() {
        useImage = new CheckBox();
		useImage.setBackground(background);
		useImage.setSelected(true);
		useImage.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateLabels();
			}
		});
        contentCheckBox.add(useImage);
    }

	private void addImages() {
		for (final ImageComponent component : unit.getComponents()) {
			final String fileName = component.getFileName();
			Label label = getLabel(fileName);
			label.addMouseListener(getMouseListener(null, new Work() {

				@Override
				public void executeWork() {
					Manager.get().showPreview(component.getImage(), fileName);
				}
			}));
			addWrapped(label);
		}
	}

    public Panel getUseImage() {
        return contentCheckBox;
    }

    public Panel getContent() {
        return content;
    }

    private void addWrapped(Component component) {
		Panel panel = getPanel();
		panel.add(component);
		content.add(panel);
	}

	private Panel getPanel() {
		Panel panel = new Panel();
		panel.setBackground(background);

		return panel;
	}

	private Label getLabel(String text) {
		Label label = new Label(text);
		labels.add(label);
		return label;
	}

	private void updateLabels() {
		for (Label label : labels) {
			label.setActive(useImage.isSelected());
		}
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
