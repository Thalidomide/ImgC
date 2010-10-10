package olj.ic.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

import olj.ic.gui.components.Button;
import olj.ic.gui.components.Panel;
import olj.ic.util.Constants;

/**
 * @author Olav Jensen
 * @since 09.apr.2010
 */
public class ButtonPanel extends Panel {

	private final ButtonPanelListener listener;
	private Button openFolder;
	private Button saveImages;
	private Button settings;

	private boolean saveButtonEnabled;

	public ButtonPanel(ButtonPanelListener listener) {
		this.listener = listener;
		setBackground(Constants.BACKGROUND);

		//TODO Add label displaying mode..

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
		openFolder = new Button("Open files/folder");
		openFolder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				listener.openFolder();
			}
		});
		return openFolder;
	}

	private JButton getSaveImages() {
		saveImages = new Button("Save images");
		saveImages.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.storeImages();
			}
		});
		saveImages.setEnabled(false);

		return saveImages;
	}

	private JButton getSettings() {
		settings = new Button("Settings");
		settings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				listener.showSettings();
			}
		});

		return settings;
	}
}
