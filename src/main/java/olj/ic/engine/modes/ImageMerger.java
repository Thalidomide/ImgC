package olj.ic.engine.modes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import olj.ic.engine.EngineMode;
import olj.ic.engine.EngineSettings;
import olj.ic.engine.modes.util.ImageUtil;
import olj.ic.entities.ImageComponent;
import olj.ic.entities.ImageUnit;
import olj.ic.util.Constants;
import olj.ic.util.Manager;
import olj.ic.util.Util;

/**
 * @author Olav Jensen
 * @since 13.mar.2010
 */
public class ImageMerger implements ImageEngine {

	@Override
	public EngineMode getEngineMode() {
		return EngineMode.composite;
	}

	@Override
	public BufferedImage getCalculatedImage(ImageUnit imageUnit) {
		EngineSettings engineSettings = Manager.get().getEngineSettings();
		int imageParts = engineSettings.getImageParts();

		if (imageParts != imageUnit.getImageComponents()) {
			throw new RuntimeException("Target image parts did not match image unit parts! Target parts: " + imageParts
					+ ", actual parts: " + imageUnit.getImageComponents());
		}

		BufferedImage[] parts = new BufferedImage[imageParts];
		int width = 0;
		int height = 0;

		for (int i = 0; i < imageParts; i++) {
			int partIndex = engineSettings.isLeftRightReversed() ? imageParts - i - 1 : i;
			BufferedImage image = imageUnit.getImageComponent(partIndex).getImage();

			parts[i] = image;

			width += image.getWidth();
			int imgHeight = image.getHeight();
			if (imgHeight > height) {
				height = imgHeight;
			}
		}

		BufferedImage result = ImageUtil.createNewImage(width, height);
		Graphics graphics = result.getGraphics();
		ImageObserver imgObserver = Manager.get().getImageObserver();
		int x = 0;

		for (BufferedImage part : parts) {
			graphics.drawImage(part, x, 0, imgObserver);
			x += part.getWidth();
		}

		return result;
	}

	@Override
	public List<ImageUnit> getImageUnits(File[] files) {
		List<ImageComponent> imageComponents = getImageComponents(files);

		return getPairs(imageComponents);
	}

	private List<ImageComponent> getImageComponents(File[] files) {
		List<ImageComponent> imageComponents = new ArrayList<ImageComponent>();

		for (File file : files) {
			String fileNameWithEnding = file.getName();
			if (Util.isValidImageFile(fileNameWithEnding)) {
				String nameWithoutEnding = Util.getFileNameWithoutEnding(fileNameWithEnding);
				boolean validImageComponent = false;
				int index = 0;
				String name = null;

				for (int i = 0; i < Constants.MAX_IMAGE_PARTS; i++) {
					if ((name = Util.getImageNameWithoutPostfix(nameWithoutEnding, i)) != null) {
						validImageComponent = true;
						index = i;
						break;
					}
				}

				if (validImageComponent) {
					ImageComponent component = new ImageComponent(file, name, index);
					imageComponents.add(component);
				}
			}
		}

		return imageComponents;
	}

	List<ImageUnit> getPairs(List<ImageComponent> imageComponents) {
		ArrayList<ImageUnit> units = new ArrayList<ImageUnit>();

		Collections.sort(imageComponents);

		int index = 0;
		final int imageParts = Manager.get().getEngineSettings().getImageParts();

		while (index < imageComponents.size() - 1) {
			ImageComponent firstComponent = imageComponents.get(index);
			List<ImageComponent> otherComponents;
			int lastComponentIndex = index + imageParts - 1;
			
			if (lastComponentIndex < imageComponents.size()) {
				otherComponents = imageComponents.subList(index + 1, lastComponentIndex + 1);
			} else {
				break;
			}

			String name = firstComponent.getName();
			boolean valid = true;
			for (ImageComponent otherComponent : otherComponents) {
				if (!name.equals(otherComponent.getName())) {
					valid = false;
					break;
				}
			}
			if (valid) {
				List<ImageComponent> unitComponents = new ArrayList<ImageComponent>(imageParts);
				unitComponents.add(firstComponent);
				unitComponents.addAll(otherComponents);

				units.add(new ImageUnit(unitComponents));
				index += imageParts;
			} else {
				index++;
			}
		}

		return units;
	}
}
