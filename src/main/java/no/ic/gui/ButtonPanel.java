package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import util.Manager;

/**
 * @author Olav Jensen
 * @since 09.apr.2010
 */
public class ButtonPanel extends JPanel {

	private final ButtonPanelListener listener;
	private JButton openFolder;
	private JButton saveImages;
	private JButton settings;
	private JCheckBox reversedImageOrder;

	private boolean saveButtonEnabled;

	public ButtonPanel(ButtonPanelListener listener) {
		this.listener = listener;

		add(getOpenFolder());
		add(getSaveImages());
		add(getSettings());
		add(getReversedImageOrder());
	}

	public void setButtonsEnabled(boolean enabled) {
		openFolder.setEnabled(enabled);
		saveImages.setEnabled(enabled && saveButtonEnabled);
		reversedImageOrder.setEnabled(enabled);
	}

	public void setSaveButtonEnabled(boolean enabled) {
		saveButtonEnabled = enabled;
		saveImages.setEnabled(enabled);
	}

	private JButton getOpenFolder() {
		openFolder = new JButton("Open folder");
		openFolder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				listener.openFolder();
			}
		});
		return openFolder;
	}

	private JButton getSaveImages() {
		saveImages = new JButton("Save images");
		saveImages.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				setButtonsEnabled(false);
				listener.storeImages();
			}
		});
		saveImages.setEnabled(false);

		return saveImages;
	}

	private JButton getSettings() {
		settings = new JButton("Settings");
		settings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				SettingsFrame settingsFrame = new SettingsFrame();
				settingsFrame.setVisible(true);
			}
		});

		return settings;
	}

	private JCheckBox getReversedImageOrder() {
		reversedImageOrder = new JCheckBox("Reverse left/right");
		reversedImageOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				Manager.get().setLeftRightReversed(reversedImageOrder.isSelected());
			}
		});
		return reversedImageOrder;
	}
}
