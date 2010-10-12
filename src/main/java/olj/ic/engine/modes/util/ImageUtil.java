package olj.ic.engine.modes.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import olj.ic.entities.ImageComponent;
import olj.ic.entities.ImageUnit;
import olj.ic.util.Util;

/**
 * @author Olav Jensen
 * @since Apr 12, 2010
 */
public class ImageUtil {

	public static BufferedImage createNewImage(int width, int heigth) {
		return new BufferedImage(width, heigth, BufferedImage.TYPE_INT_ARGB);
	}

    public static List<ImageUnit> getSingleImageUnitsFromFiles(File[] files) {
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
