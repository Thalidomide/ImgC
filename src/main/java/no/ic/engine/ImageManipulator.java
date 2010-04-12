package engine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;

import entities.ImageComponent;
import entities.ImageUnit;
import util.Manager;
import util.Util;

/**
 * @author Olav Jensen
 * @since Apr 12, 2010
 */
public class ImageManipulator implements ImageEngine {

	@Override
	public EngineMode getEngineMode() {
		return EngineMode.manipulate;
	}

	@Override
	public BufferedImage getCalculatedImage(ImageUnit imageUnit) {
		ImageObserver imgObserver = new JButton();//TODO Hmm
		final int partsCount = Manager.get().getEngineSettings().getImageParts();

		BufferedImage srcImage = imageUnit.getImageComponent(0).getImage();

		int width = srcImage.getWidth();
		int heigth = srcImage.getHeight();

		BufferedImage result = ImageUtil.createNewImage(width, heigth);

		Graphics graphics = result.getGraphics();
		int xStart, xEnd;
		for (int i = 0; i < partsCount; i++) {
			xStart = (int) (((double)i / partsCount) * width);
			xEnd = (int) (((double)(i + 1) / partsCount) * width);
			int widthPart = xEnd - xStart;
			System.out.printf("xStart: %s, xEnd: %s, width: %s%n", xStart, xEnd, widthPart);

			BufferedImage part = srcImage.getSubimage(xStart, 0, widthPart, heigth);
			graphics.drawImage(part, width - widthPart - xStart, 0, imgObserver);
		}

		return result;
	}

	@Override
	public List<ImageUnit> getImageUnits(File[] files) {
		List<ImageUnit> imageComponents = new ArrayList<ImageUnit>();

		for (File file : files) {
			String fileNameWithEnding = file.getName();
			if (Util.isValidImageFile(fileNameWithEnding)) {
				String nameWithoutEnding = Util.getFileNameWithoutEnding(fileNameWithEnding);
				ImageComponent component = new ImageComponent(file, nameWithoutEnding, 0);
				imageComponents.add(new ImageUnit(Arrays.asList(component)));
			}
		}

		return imageComponents;
	}
}
