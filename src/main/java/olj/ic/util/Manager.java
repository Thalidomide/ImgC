package olj.ic.util;

import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import olj.ic.engine.EngineSettings;
import olj.ic.gui.PreviewFrame;
import olj.ic.status.MessageListener;
import olj.ic.status.StatusHandler;

/**
 * @author Olav Jensen
 * @since 09.apr.2010
 */
public class Manager {

	private static Manager instance;

	private StatusHandler statusHandler;
	private MessageListener messageListener;
	private PreviewFrame previewFrame;

	private final EngineSettings engineSettings;

	private Manager() {
		engineSettings = new EngineSettings();
	}

	public static Manager get() {
		if (instance == null) {
			instance = new Manager();
		}
		return instance;
	}

	public EngineSettings getEngineSettings() {
		return engineSettings;
	}

	public StatusHandler getStatusHandler() {
		return statusHandler;
	}

	public void setStatusHandler(StatusHandler handler) {
		this.statusHandler = handler;
	}

	public MessageListener getMessageListener() {
		return messageListener;
	}

	public void setMessageListener(MessageListener messageListener) {
		this.messageListener = messageListener;
	}

	public void showPreview(Image image, String title) {
		if (previewFrame == null) {
			previewFrame = new PreviewFrame();
			previewFrame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					previewFrame = null;
				}
			});
		}

		previewFrame.setTitle(title);
		previewFrame.setVisible(true);
		previewFrame.displayImage(image);
	}
}
