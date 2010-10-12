package olj.ic.entities;

import java.util.Arrays;

import junit.framework.TestCase;

/**
 * @author Olav Jensen
 * @since 09.apr.2010
 */
public class ImageUnitTest extends TestCase {

	public void testConstructProperImagePair() {
		ImageComponent icL = new ImageComponent(null, "Img", 0);
		ImageComponent icR = new ImageComponent(null, "Img", 1);

		ImageUnit unit = new ImageUnit(Arrays.asList(icL, icR));
		assertEquals(icL, unit.getImageComponent(0));
		assertEquals(icR, unit.getImageComponent(1));

		unit = new ImageUnit(Arrays.asList(icR, icL));
		assertEquals(icL, unit.getImageComponent(0));
		assertEquals(icR, unit.getImageComponent(1));

		try {
			new ImageUnit(Arrays.asList(icR, icR));
			fail("Constructed illegal image unit");
		} catch (RuntimeException e) {
			// Expected
		}

		try {
			new ImageUnit(Arrays.asList(new ImageComponent(null, "A", 0), new ImageComponent(null, "B", 2)));
			fail("Constructed illegal image unit");
		} catch (RuntimeException e) {
			// Expected
		}

		try {
			new ImageUnit(Arrays.asList(new ImageComponent(null, "A", 1), new ImageComponent(null, "B", 2)));
			fail("Constructed illegal image unit");
		} catch (RuntimeException e) {
			// Expected
		}
	}
}
