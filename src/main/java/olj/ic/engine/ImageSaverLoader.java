package olj.ic.engine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

import olj.ic.entities.ImageUnit;
import olj.ic.work.Work;
import olj.ic.util.Manager;
import olj.ic.work.WorkPackage;

/**
 * @author Olav Jensen
 * @since 05.apr.2010
 */
public class ImageSaverLoader {

	public static void saveImageUnits(final List<ImageUnit> units, final String path) {
		List<Work> workList = new ArrayList<Work>(units.size());

		int index = 1;
		for (final ImageUnit unit : units) {
			final int curIndex = index++;
			workList.add(new Work() {
				@Override
				public void executeWork() {
					String filePath = path + "\\" + unit.getName() + ".png";
					Manager.get().getMessageListener().addMessage(" - Saving image " + curIndex + " of " + units.size() + " (" + filePath + ")");
					saveImage(unit.getImageResult(), filePath);
				}
			});
		}
		Manager.get().getWorkHandler().doWork(new WorkPackage("Create images", workList));
	}

	private static void saveImage(BufferedImage image, String filePath) {
		try {
    		File outputfile = new File(filePath);
			outputfile.mkdirs();
			if (!outputfile.exists()) {
				outputfile.createNewFile();
			}
			ImageIO.write(image, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
