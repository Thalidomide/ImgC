package engine;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JButton;

import entities.ImageComponent;
import entities.ImagePair;
import util.Util;

/**
 * @author Olav Jensen
 * @since 13.mar.2010
 */
public class ImageCompositor {

	public static BufferedImage composite(Image img1, Image img2) {
		ImageObserver imgObserver = new JButton();//TODO Hmm

		final int width1 = img1.getWidth(imgObserver);
		final int width2 = img2.getWidth(imgObserver);
		final int height1 = img1.getHeight(imgObserver);
		final int height2 = img2.getHeight(imgObserver);

		final int width = width1 + width2;
		final int height = height1 > height2 ? height1 : height2;

		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics graphics = result.getGraphics();
		graphics.drawImage(img1, 0, 0, imgObserver);
		graphics.drawImage(img2, width1, 0, imgObserver);

		return result;
	}

	public static List<ImagePair> getImagePairs(File[] files) {
		List<ImageComponent> imageComponents = getImageComponents(files);

		return getPairs(imageComponents);
	}

	private static List<ImageComponent> getImageComponents(File[] files) {
		List<ImageComponent> imageComponents = new ArrayList<ImageComponent>();

		for (File file : files) {
			String fileNameWithEnding = file.getName();
			if (Util.isValidImageFile(fileNameWithEnding)) {
				String nameWithoutEnding = Util.getFileNameWithoutEnding(fileNameWithEnding);
				boolean validImageComponent = false;
				boolean left = false;
				String name;

				if ((name = Util.getLeftImageName(nameWithoutEnding)) != null) {
					validImageComponent = true;
					left = true;
				} else if ((name = Util.getRightImageName(nameWithoutEnding)) != null) {
					validImageComponent = true;
					left = false;
				}

				if (validImageComponent) {
					ImageComponent component = new ImageComponent(file, name, left);
					imageComponents.add(component);
				}
			}
		}

		return imageComponents;
	}

	static List<ImagePair> getPairs(List<ImageComponent> imageComponents) {
		ArrayList<ImagePair> pairs = new ArrayList<ImagePair>();

		Collections.sort(imageComponents);

		int index = 0;

		while (index < imageComponents.size() - 1) {
			ImageComponent imgCmp1 = imageComponents.get(index);
			ImageComponent imgCmp2 = imageComponents.get(index + 1);

			if (imgCmp1.getName().equals(imgCmp2.getName())) {
				pairs.add(new ImagePair(imgCmp1, imgCmp2));
				index += 2;
			} else {
				index++;
			}
		}

		return pairs;
	}
}
