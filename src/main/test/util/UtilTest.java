package util;

import junit.framework.TestCase;

/**
 * @author Olav Jensen
 * @since 08.apr.2010
 */
public class UtilTest extends TestCase {

	public void testIsValidImageFileEnding() {
		assertTrue(Util.isValidImageFile("MyFile.jpg"));
		assertTrue(Util.isValidImageFile("MyFile.JPG"));
		assertTrue(Util.isValidImageFile("My File.JPG"));
		assertTrue(Util.isValidImageFile("image.png"));
		assertTrue(Util.isValidImageFile("image 1.PNG"));
		assertTrue(Util.isValidImageFile("image 1.bmp"));
		assertTrue(Util.isValidImageFile("image 1.BMP"));
		assertTrue(Util.isValidImageFile("image 1 _l.jpg"));

		// Not valid files
		assertFalse(Util.isValidImageFile(""));
		assertFalse(Util.isValidImageFile("No"));
		assertFalse(Util.isValidImageFile("file.txt"));
		assertFalse(Util.isValidImageFile("file.jpge"));
	}

	public void testIsValidLeftRightImage() {
		assertNotNull(Util.getLeftImageName("My image_l"));
		assertNotNull(Util.getLeftImageName("My image _L"));

		assertNotNull(Util.getRightImageName("My image _r"));
		assertNotNull(Util.getRightImageName("My image _R"));

		// Not valid files
		assertNull(Util.getLeftImageName("asdf"));
		assertNull(Util.getLeftImageName("asdf _l "));

		assertNull(Util.getRightImageName("asdf"));
		assertNull(Util.getRightImageName("asdf _Rd"));
	}

	public void testGetImageNames() {
		assertEquals("Image 1", Util.getLeftImageName("Image 1_l"));
		assertEquals("Image 1", Util.getLeftImageName("Image 1 _L"));

		assertEquals("Image 1", Util.getRightImageName("Image 1 _r"));
		assertEquals("Image 1", Util.getRightImageName("Image 1  _R"));

		// Test with new postfix
		Constants.RIGHT_IMAGE_POSTFIXES.add("[RIGHT]");

		assertEquals("My image", Util.getRightImageName("My image [RIGHT]"));

		assertEquals(null, Util.getRightImageName("My image [RIGHT]q"));
	}

	public void testGetFileNameWithoutEnding() {
		assertEquals("Image", Util.getFileNameWithoutEnding("Image.jpg"));
	}

	public void testStringsAreDifferent() {
		assertTrue(Util.stringsAreDifferent(null, ""));
		assertTrue(Util.stringsAreDifferent("", null));
		assertTrue(Util.stringsAreDifferent("A", ""));
		assertTrue(Util.stringsAreDifferent("", "A"));

		assertFalse(Util.stringsAreDifferent(null, null));
		assertFalse(Util.stringsAreDifferent("", ""));
		assertFalse(Util.stringsAreDifferent("asdf", "asdf"));
	}
}
