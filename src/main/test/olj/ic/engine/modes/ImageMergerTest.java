package olj.ic.engine.modes;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import olj.ic.entities.ImageComponent;
import olj.ic.entities.ImageUnit;

/**
 * @author Olav Jensen
 * @since 09.apr.2010
 */
public class ImageMergerTest extends TestCase {

	public void testGetImagePairs() {
		ImageMerger imageMerger = new ImageMerger();

		List<ImageComponent> components = new ArrayList<ImageComponent>();
		ImageComponent img1L = new ImageComponent(null, "Image 1", 0);
		ImageComponent img1R = new ImageComponent(null, "Image 1", 1);
		ImageComponent imgSingle = new ImageComponent(null, "SingleImage", 0);
		ImageComponent img2L = new ImageComponent(null, "Image 2", 0);
		ImageComponent img2R = new ImageComponent(null, "Image 2", 1);

		components.add(img1L);
		components.add(img1R);
		components.add(imgSingle);
		components.add(img2L);
		components.add(img2R);

		List<ImageUnit> units = imageMerger.getPairs(components);

		assertEquals(2, units.size());

		ImageUnit unit = units.get(0);
		assertEquals(img1L, unit.getImageComponent(0));
		assertEquals(img1R, unit.getImageComponent(1));

		unit = units.get(1);
		assertEquals(img2L, unit.getImageComponent(0));
		assertEquals(img2R, unit.getImageComponent(1));
	}
}
