package engine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import javax.imageio.ImageIO;

import entities.ImageUnit;
import status.MessageListener;
import util.Manager;

/**
 * @author Olav Jensen
 * @since 05.apr.2010
 */
public class ImageSaverLoader {

	public static void saveImageUnits(List<ImageUnit> units, String path) {
		MessageListener messageListener = Manager.get().getMessageListener();
		messageListener.addMessage("*** Start saving images ***");
		long start = System.currentTimeMillis();

		int index = 1;
		for (ImageUnit unit : units) {
			String filePath = path + "\\" + unit.getName() + ".png";
			messageListener.addMessage(" - Saving image " + index++ + " of " + units.size() + " (" + filePath + ")");

			saveImage(unit.getImageResult(), filePath);
		}
		double seconds = (double)(System.currentTimeMillis() - start) / 1000;
		DecimalFormat nf = new DecimalFormat("#.##");

		messageListener.addMessage("*** Finished saving images in: " + nf.format(seconds) + " seconds ***");
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
