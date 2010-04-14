package olj.ic.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.HeadlessException;
import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import olj.ic.engine.ImageEngineUtil;
import olj.ic.engine.ImageSaverLoader;
import olj.ic.entities.ImageUnit;
import olj.ic.status.MessageListener;
import olj.ic.status.StatusHandler;
import olj.ic.status.StatusListener;
import olj.ic.status.StatusType;
import olj.ic.status.Work;
import olj.ic.util.Constants;
import olj.ic.util.Manager;

/**
 * @author Olav Jensen
 * @since 08.apr.2010
 */
public class MainFrame extends JFrame implements ButtonPanelListener, StatusListener, MessageListener, SettingsListener {

	private StatusPanel statusPanel;
	private ImageUnitsResultPanel resultPanel;
	private ButtonPanel buttonPanel;

	private JPanel content;

	private ViewMode viewMode;
	private File selectedDirectory;

	public MainFrame() throws HeadlessException {
		setSize(Constants.SIZE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(0, 0);

		JPanel mainContent = new JPanel(new BorderLayout());

		statusPanel = new StatusPanel();
		resultPanel = new ImageUnitsResultPanel();
		buttonPanel = new ButtonPanel(this);
		SettingsPanel settingsContent = new SettingsPanel(this);

		mainContent.add(buttonPanel, BorderLayout.NORTH);
		mainContent.add(resultPanel, BorderLayout.CENTER);
		mainContent.add(statusPanel, BorderLayout.SOUTH);

		content = new JPanel(new CardLayout());
		getContentPane().add(content);

		content.add(mainContent, ViewMode.main.name());
		content.add(settingsContent, ViewMode.settings.name());

		showMainView();

		Manager.get().setMessageListener(this);
		Manager.get().setStatusHandler(new StatusHandler(this));

		setVisible(true);
	}

	@Override
	public void openFolder() {
		final JFileChooser fileChooser = new JFileChooser("C:\\");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int action = fileChooser.showDialog(MainFrame.this, "Open");

		if (action == JFileChooser.APPROVE_OPTION) {
			Work work = new Work() {
				@Override
				public void executeWork() {
					selectedDirectory = fileChooser.getSelectedFile();
					loadImagesFromDirectory();
				}
			};

			Manager.get().getStatusHandler().doWork(StatusType.working, work, "Scanning folder");
		}
	}

	@Override
	public void storeImages() {
		Work storeWork = new Work() {
			@Override
			public void executeWork() {
				String savePath = selectedDirectory.getAbsolutePath() + "\\Composition";
				if (Manager.get().getEngineSettings().isLeftRightReversed()) {
					savePath += "Reversed";
				}

				ImageSaverLoader.saveImageUnits(resultPanel.getPairs(), savePath);
			}
		};

		Manager.get().getStatusHandler().doWork(StatusType.working, storeWork, "Creating and saving images");
	}

	@Override
	public void addMessage(String message) {
		statusPanel.addMessage(message);
	}

	@Override
	public void statusChanged(StatusType statusType, String description) {
		statusPanel.updateStatus(statusType, description);
		buttonPanel.setButtonsEnabled(statusType.isGuiEnabled());
	}

	@Override
	public void showSettings() {
		viewMode = ViewMode.settings;
		updateView();
	}

	@Override
	public void saveSettings() {
		if (selectedDirectory != null) {
			loadImagesFromDirectory();
		}
		showMainView();
	}

	@Override
	public void cancelSettings() {
		showMainView();
	}

	private void loadImagesFromDirectory() {
		File[] files = selectedDirectory.listFiles();
		List<ImageUnit> imageUnits = ImageEngineUtil.getImageUnits(files);
		resultPanel.setPairs(imageUnits);
		buttonPanel.setSaveButtonEnabled(!imageUnits.isEmpty());
	}

	private void showMainView() {
		viewMode = ViewMode.main;
		updateView();
	}

	private void updateView() {
		CardLayout cardLayout = (CardLayout) content.getLayout();
		cardLayout.show(content, viewMode.name());
	}
}
