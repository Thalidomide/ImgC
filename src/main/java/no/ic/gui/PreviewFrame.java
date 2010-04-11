package gui;

import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;

/**
 * @author Olav Jensen
 * @since 08.apr.2010
 */
public class PreviewFrame extends JFrame {

	private PreviewPanel panel;

	public PreviewFrame() throws HeadlessException {
		setSize(800, 600);
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
	}
}
