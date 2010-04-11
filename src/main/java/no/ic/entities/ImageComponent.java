package entities;

import java.awt.Image;
import java.io.File;

import util.Util;

/**
 * @author Olav Jensen
 * @since 08.apr.2010
 */
public class ImageComponent implements Comparable<ImageComponent> {

	private final File imageFile;
	private final String name;
	private final boolean left;

	public ImageComponent(File imageFile, String name, boolean left) {
		if (name == null) {
			throw new RuntimeException("Name cannot be null");
		}
		this.imageFile = imageFile;
		this.name = name;
		this.left = left;
	}

	public File getImageFile() {
		return imageFile;
	}

	public Image getImage() {
		return Util.loadImage(imageFile);
	}

	public String getName() {
		return name;
	}

	public String getFileName() {
		return imageFile.getName();
	}

	public boolean isLeft() {
		return left;
	}

	public int compareTo(ImageComponent o) {
		return name.compareTo(o.name);
	}
}
