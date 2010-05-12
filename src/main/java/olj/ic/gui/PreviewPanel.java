package olj.ic.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import olj.ic.gui.components.Panel;
import olj.ic.util.Constants;

/**
 * @author Olav Jensen
 * @since 08.apr.2010
 */
public class PreviewPanel extends Panel {

	private Image image;

	private double xPos = 0;
	private double yPos = 0;

	private double xPosLast = 0;
	private double yPosLast = 0;
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
			xPos += (x - xPosLast) / zoom;
			yPos += (y - yPosLast) / zoom;
		}

		xPosLast = x;
		yPosLast = y;
		dragging = true;

		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		int imgWidth = image.getWidth(this);
		int imgHeight = image.getHeight(this);

		int drawWidth = (int) Math.round(imgWidth * zoom);
		int drawHeight = (int) Math.round(imgHeight * zoom);

		int x = (int) Math.round(getZoomedValue(xPos)) + getWidth() / 2 - drawWidth / 2;
		int y = (int) Math.round(getZoomedValue(yPos)) + getHeight() / 2 - drawHeight / 2;

		drawBackground(g, drawWidth, drawHeight, x, y);

		g.drawImage(image, x, y, drawWidth, drawHeight, this);
	}

	private void drawBackground(Graphics g, int drawWidth, int drawHeight, int x, int y) {
		int borderThickness = 4;
		g.setColor(Constants.BACKGROUND_LIGHTER);
		g.fillRect(x - borderThickness, y - borderThickness, drawWidth + 2 * borderThickness, drawHeight + 2 * borderThickness);
		g.setColor(Color.WHITE);
		g.fillRect(x, y, drawWidth, drawHeight);
	}

	public void setImage(Image image) {
		this.image = image;
		xPos = 0;
		yPos = 0;
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
		checkMaxZoom();
		repaint();
	}

	public void zoomOut() {
		zoom /= Constants.ZOOM_FACTOR;
		checkMinZoom();
		repaint();
	}

	private void checkMaxZoom() {
		if (zoom > Constants.ZOOM_MAX) {
			zoom = Constants.ZOOM_MAX;
		}
	}

	private void checkMinZoom() {
		if (zoom < Constants.ZOOM_MIN) {
			zoom = Constants.ZOOM_MIN;
		}
	}

	public int getVisibleImageHeight() {
		return (int) Math.round(getZoomedValue(image.getHeight(this)));
	}

	private void adjustZoom() {
		int imageWidth = image != null ? image.getWidth(this) : 0;

		zoom = (double)getWidth() / imageWidth;
		checkMaxZoom();

		repaint();
	}

	private double getZoomedValue(double value) {
		return value * zoom;
	}
}
