package gui;

import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;

import util.Constants;

/**
 * @author Olav Jensen
 * @since 08.apr.2010
 */
public class PreviewFrame extends JFrame {

	private PreviewPanel panel;

	public PreviewFrame() throws HeadlessException {
		setSize(Constants.DEFAULT_PREVIEW_WIDTH, 600);
		setLocation(300, 0);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		panel = new PreviewPanel();
		getContentPane().add(panel);

		addKeyListener(getKeyListener());
	}

	private KeyAdapter getKeyListener() {
		return new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_R:
						panel.resetView();
						break;
					case KeyEvent.VK_F:
						panel.showFullImage();
						break;
					case KeyEvent.VK_PLUS:
						panel.zoomIn();
						break;
					case KeyEvent.VK_MINUS:
						panel.zoomOut();
						break;
				}

				if (e.getKeyCode() == KeyEvent.VK_R) {
					panel.resetView();
				}
			}
		};
	}

	public void displayImage(Image... images) {
		panel.setImages(images);
		int imgHeight = panel.getVisibleImageHeight();
		int framePanelHeightDif = getHeight() - panel.getHeight();
		int heightToSet = imgHeight + framePanelHeightDif;
		int screenHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		if (heightToSet > screenHeight) {
			heightToSet = screenHeight;
		}
		setSize(Constants.DEFAULT_PREVIEW_WIDTH, heightToSet);
	}
}
