package engine;

import java.util.ArrayList;
import java.util.List;

import entities.ImageComponent;
import entities.ImagePair;
import junit.framework.TestCase;

/**
 * @author Olav Jensen
 * @since 09.apr.2010
 */
public class ImageCompositorTest extends TestCase {

	public void testGetImagePairs() {
		List<ImageComponent> components = new ArrayList<ImageComponent>();
		ImageComponent img1L = new ImageComponent(null, "Image 1", true);
		ImageComponent img1R = new ImageComponent(null, "Image 1", false);
		ImageComponent imgSingle = new ImageComponent(null, "SingleImage", true);
		ImageComponent img2L = new ImageComponent(null, "Image 2", true);
		ImageComponent img2R = new ImageComponent(null, "Image 2", false);

		components.add(img1L);
		components.add(img1R);
		components.add(imgSingle);
		components.add(img2L);
		components.add(img2R);

		List<ImagePair> pairs = ImageCompositor.getPairs(components);

		assertEquals(2, pairs.size());

		ImagePair pair = pairs.get(0);
		assertEquals(img1L, pair.getLeftImage());
		assertEquals(img1R, pair.getRightImage());

		pair = pairs.get(1);
		assertEquals(img2L, pair.getLeftImage());
		assertEquals(img2R, pair.getRightImage());
	}
}
