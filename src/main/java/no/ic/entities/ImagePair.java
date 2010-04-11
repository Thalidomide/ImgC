package entities;

import java.awt.Image;
import java.awt.image.BufferedImage;

import engine.ImageCompositor;
import util.Manager;

/**
 * @author Olav Jensen
 * @since 08.apr.2010
 */
public class ImagePair {

	private final ImageComponent leftImage;
	private final ImageComponent rightImage;

	public ImagePair(ImageComponent imageComponent1, ImageComponent imageComponent2) {
		if (imageComponent1.isLeft() == imageComponent2.isLeft()) {
			throw new RuntimeException("Both components cannot be left/right: " + imageComponent1.isLeft());
		}
		if (imageComponent1.isLeft()) {
			this.leftImage = imageComponent1;
			this.rightImage = imageComponent2;
		} else {
			this.leftImage = imageComponent2;
			this.rightImage = imageComponent1;
		}
	}

	public BufferedImage getComposition() {
		Image imageLeft;
		Image imageRight;
		if (!Manager.get().isLeftRightReversed()) {
			imageLeft = leftImage.getImage();
			imageRight = rightImage.getImage();
		} else {
			imageRight = leftImage.getImage();
			imageLeft = rightImage.getImage();
		}
		return ImageCompositor.composite(imageLeft, imageRight);
	}

	public ImageComponent getLeftImage() {
		return leftImage;
	}

	public ImageComponent getRightImage() {
		return rightImage;
	}

	public String getName() {
		return leftImage.getName();
	}

	public String getImageNames() {
		return leftImage.getFileName() + ", " + rightImage.getFileName();
	}
}
