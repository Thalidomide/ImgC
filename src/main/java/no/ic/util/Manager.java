package util;

import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import engine.EngineMode;
import gui.PreviewFrame;
import status.MessageListener;
import status.StatusHandler;

/**
 * @author Olav Jensen
 * @since 09.apr.2010
 */
public class Manager {

	private static Manager instance;

	private boolean leftRightReversed;
	private StatusHandler statusHandler;
	private MessageListener messageListener;
	private PreviewFrame previewFrame;
	private EngineMode engineMode = EngineMode.composite;

	private Manager() {
	}

	public static Manager get() {
		if (instance == null) {
			instance = new Manager();
		}
		return instance;
	}

	public boolean isLeftRightReversed() {
		return leftRightReversed;
	}

	public void setLeftRightReversed(boolean leftRightReversed) {
		this.leftRightReversed = leftRightReversed;
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

	public EngineMode getEngineMode() {
		return engineMode;
	}

	public void setEngineMode(EngineMode engineMode) {
		this.engineMode = engineMode;
	}
}
