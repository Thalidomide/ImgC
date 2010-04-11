package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author Olav Jensen
 * @since 09.apr.2010
 */
public class ButtonPanel extends JPanel {

	private final ButtonPanelListener listener;
	private JButton openFolder;
	private JButton saveImages;
	private JButton settings;

	private boolean saveButtonEnabled;

	public ButtonPanel(ButtonPanelListener listener) {
		this.listener = listener;

		add(getOpenFolder());
		add(getSaveImages());
		add(getSettings());
	}

	public void setButtonsEnabled(boolean enabled) {
		openFolder.setEnabled(enabled);
		saveImages.setEnabled(enabled && saveButtonEnabled);
		settings.setEnabled(enabled);
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
				listener.showSettings();
			}
		});

		return settings;
	}
}
