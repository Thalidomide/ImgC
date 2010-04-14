package olj.ic.entities;

import java.awt.image.BufferedImage;
import java.io.File;

import olj.ic.util.Util;

/**
 * @author Olav Jensen
 * @since 08.apr.2010
 */
public class ImageComponent implements Comparable<ImageComponent> {

	private final File imageFile;
	private final String name;
	private final int position;

	public ImageComponent(File imageFile, String name, int position) {
		if (name == null) {
			throw new RuntimeException("Name cannot be null");
		}
		this.imageFile = imageFile;
		this.name = name;
		this.position = position;
	}

	public File getImageFile() {
		return imageFile;
	}

	public BufferedImage getImage() {
		return Util.loadImage(imageFile);
	}

	public String getName() {
		return name;
	}

	public String getFileName() {
		return imageFile.getName();
	}

	public int getPosition() {
		return position;
	}

	public int compareTo(ImageComponent o) {
		return name.compareTo(o.name);
	}
}
