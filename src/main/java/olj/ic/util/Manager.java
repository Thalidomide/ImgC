package olj.ic.util;

import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import olj.ic.engine.EngineSettings;
import olj.ic.gui.PreviewFrame;
import olj.ic.work.MessageListener;
import olj.ic.work.WorkHandler;

/**
 * @author Olav Jensen
 * @since 09.apr.2010
 */
public class Manager {

	private static Manager instance;

	private WorkHandler workHandler;
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

	public WorkHandler getWorkHandler() {
		return workHandler;
	}

	public void setStatusHandler(WorkHandler handler) {
		this.workHandler = handler;
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

	public void logMessage(String message) {
		if (messageListener != null) {
			messageListener.addMessage(message);
		}
	}
}
