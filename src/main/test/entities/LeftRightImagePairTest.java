package entities;

import junit.framework.TestCase;

/**
 * @author Olav Jensen
 * @since 09.apr.2010
 */
public class LeftRightImagePairTest extends TestCase {

	public void testConstructProperImagePair() {
		ImageComponent icL = new ImageComponent(null, "Img", true);
		ImageComponent icR = new ImageComponent(null, "Img", false);

		ImagePair pair = new ImagePair(icL, icR);
		assertEquals(icL, pair.getLeftImage());
		assertEquals(icR, pair.getRightImage());

		pair = new ImagePair(icR, icL);
		assertEquals(icL, pair.getLeftImage());
		assertEquals(icR, pair.getRightImage());

		try {
			new ImagePair(icR, icR);
			fail("Constructed illegal image pair");
		} catch (RuntimeException e) {
			// Expected
		}

	}
}
