package engine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import javax.imageio.ImageIO;

import entities.ImagePair;
import status.MessageListener;
import util.Manager;

/**
 * @author Olav Jensen
 * @since 05.apr.2010
 */
public class ImageSaverLoader {

	public static void saveImagePairs(List<ImagePair> pairs, String path) {
		MessageListener messageListener = Manager.get().getMessageListener();
		messageListener.addMessage("*** Start saving images ***");
		long start = System.currentTimeMillis();

		int index = 1;
		for (ImagePair pair : pairs) {
			String filePath = path + "\\" + pair.getName() + ".png";
			messageListener.addMessage(" - Saving image " + index++ + " of " + pairs.size() + " (" + filePath + ")");

			saveImage(pair.getComposition(), filePath);
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
