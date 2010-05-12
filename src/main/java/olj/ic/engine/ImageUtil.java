package olj.ic.engine;

import java.awt.image.BufferedImage;

/**
 * @author Olav Jensen
 * @since Apr 12, 2010
 */
public class ImageUtil {

	static BufferedImage createNewImage(int width, int heigth) {
		return new BufferedImage(width, heigth, BufferedImage.TYPE_INT_ARGB);
	}
}
