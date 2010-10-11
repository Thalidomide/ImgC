package olj.ic.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.HeadlessException;
import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import olj.ic.engine.ImageEngineUtil;
import olj.ic.engine.ImageSaverLoader;
import olj.ic.entities.ImageUnit;
import olj.ic.gui.components.Panel;
import olj.ic.util.Constants;
import olj.ic.util.Manager;
import olj.ic.util.Util;
import olj.ic.work.MessageListener;
import olj.ic.work.StatusListener;
import olj.ic.work.StatusType;
import olj.ic.work.Work;
import olj.ic.work.WorkHandler;
import olj.ic.work.WorkPackage;

/**
 * @author Olav Jensen
 * @since 08.apr.2010
 */
public class MainFrame extends JFrame implements ButtonPanelListener, StatusListener, MessageListener, SettingsListener,
		ImageTransferHandlerListener {

	private StatusPanel statusPanel;
	private ImageUnitsResultPanel resultPanel;
	private ButtonPanel buttonPanel;

	private Panel content;

	private ViewMode viewMode;
	private File[] selectedFilesOrDirectory;

	public MainFrame() throws HeadlessException {
		setSize(Constants.SIZE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(0, 0);
        setTitle("Image merger (v " + Constants.VERSION + ")");

		setTransferHandler(new ImageTransferHandler(this));

		setupGui();

		Manager.get().setMessageListener(this);
		Manager.get().setStatusHandler(new WorkHandler(this));
		Manager.get().setImageObserver(this);

		setVisible(true);
	}

	private void setupGui() {
		Panel mainContent = new Panel(new BorderLayout());

		statusPanel = new StatusPanel();
		resultPanel = new ImageUnitsResultPanel();
		buttonPanel = new ButtonPanel(this);
		SettingsPanel settingsContent = new SettingsPanel(this);

		mainContent.add(buttonPanel, BorderLayout.NORTH);
		mainContent.add(resultPanel, BorderLayout.CENTER);
		mainContent.add(statusPanel, BorderLayout.SOUTH);

		content = new Panel(new CardLayout());
		getContentPane().add(content);

		content.add(mainContent, ViewMode.main.name());
		content.add(settingsContent, ViewMode.settings.name());

		showMainView();
	}

	@Override
	public void openFolder() {
		final JFileChooser fileChooser = Util.getImageFileChooser();

		int action = fileChooser.showDialog(MainFrame.this, "Open");

		if (action == JFileChooser.APPROVE_OPTION) {
			openFiles(fileChooser.getSelectedFiles());
		}
	}

	@Override
	public void openFiles(final File[] files) {
		Work work = new Work() {

			@Override
			public void executeWork() {
				selectedFilesOrDirectory = files;
				loadImagesFromDirectory();
			}
		};

		Manager.get().getWorkHandler().doWork(new WorkPackage("Scan folder for images", work));
	}

	@Override
	public void storeImages() {
		if (selectedFilesOrDirectory == null || selectedFilesOrDirectory.length == 0) {
			throw new IllegalStateException("There are not any files selected!");
		}

		File file = selectedFilesOrDirectory[0];
		String currentPath = isSelectedDirectory() ? file.getAbsolutePath() : file.getParent();

		String savePath = currentPath + "\\" + Manager.get().getEngineSettings().getEngineMode();
		if (Manager.get().getEngineSettings().isLeftRightReversed()) {
			savePath += "Reversed";
		}

		ImageSaverLoader.saveImageUnits(resultPanel.getImageUnits(), savePath);
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
		if (selectedFilesOrDirectory != null) {
			loadImagesFromDirectory();
		}
		showMainView();
	}

	@Override
	public void cancelSettings() {
		showMainView();
	}

	private void loadImagesFromDirectory() {
		File[] files = isSelectedDirectory() ? selectedFilesOrDirectory[0].listFiles() : selectedFilesOrDirectory;
		List<ImageUnit> imageUnits = ImageEngineUtil.getImageUnits(files);
		resultPanel.setPairs(imageUnits);
		buttonPanel.setSaveButtonEnabled(!imageUnits.isEmpty());
	}

	private boolean isSelectedDirectory() {
		return selectedFilesOrDirectory.length == 1 && selectedFilesOrDirectory[0].isDirectory();
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
