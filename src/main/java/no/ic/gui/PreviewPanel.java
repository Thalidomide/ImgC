package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;

import util.Constants;

/**
 * @author Olav Jensen
 * @since 08.apr.2010
 */
public class PreviewPanel extends JPanel {

	private Image[] images;

	private int xPos = 0;
	private int yPos = 0;

	private int xPosLast = 0;
	private int yPosLast = 0;
	private double zoom = 1;
	private boolean dragging;

	public PreviewPanel() {
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				dragged(e.getX(), e.getY());
			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				dragging = false;
			}
		});
	}

	private void dragged(int x, int y) {
		if (dragging) {
			xPos += x - xPosLast;
			yPos += y - yPosLast;
		}

		xPosLast = x;
		yPosLast = y;
		dragging = true;

		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if (images != null) {
			int x = xPos;
			for (Image image : images) {
				int drawWidth = (int) (image.getWidth(this) * zoom);
				int drawHeight = (int) (image.getHeight(this) * zoom);
				g.drawImage(image, x, yPos, drawWidth, drawHeight, this);
				x += drawWidth;
			}
		}
	}

	public void setImages(Image[] images) {
		this.images = images;

		adjustZoom();
	}

	public void showFullImage() {
		zoom = 1;
		repaint();
	}

	public void resetView() {
		xPos = 0;
		yPos = 0;
		adjustZoom();
	}

	public void zoomIn() {
		zoom *= Constants.ZOOM_FACTOR;
		repaint();
	}

	public void zoomOut() {
		zoom /= Constants.ZOOM_FACTOR;
		repaint();
	}

	public int getVisibleImageHeight() {
		int height = 0;
		for (Image image : images) {
			int currentHeight = image.getHeight(this);
			if (currentHeight > height) {
				height = currentHeight;
			}
		}
		return (int) (height * zoom);
	}

	private void adjustZoom() {
		int totalImgWidth = 0;
		for (Image image : images) {
			totalImgWidth += image.getWidth(this);
		}

		zoom = (double)getWidth() / totalImgWidth;

		repaint();
	}
}
