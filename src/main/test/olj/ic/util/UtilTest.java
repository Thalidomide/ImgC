package olj.ic.util;

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
		assertNotNull(Util.getImageNameWithoutPostfix("My image_l", 0));
		assertNotNull(Util.getImageNameWithoutPostfix("My image _L", 0));

		assertNotNull(Util.getImageNameWithoutPostfix("My image _r", 1));
		assertNotNull(Util.getImageNameWithoutPostfix("My image _R", 1));

		assertNotNull(Util.getImageNameWithoutPostfix("asdf _l ", 0));
		assertNotNull(Util.getImageNameWithoutPostfix("asdf _r ", 1));

		// Not valid files
		assertNull(Util.getImageNameWithoutPostfix("asdf", 0));

		assertNull(Util.getImageNameWithoutPostfix("asdf", 1));
	}

	public void testGetImageNames() {
		assertEquals("Image 1", Util.getImageNameWithoutPostfix("Image 1_l", 0));
		assertEquals("Image 1", Util.getImageNameWithoutPostfix("Image 1 _L", 0));

		assertEquals("Image 1", Util.getImageNameWithoutPostfix("Image 1 _r", 1));
		assertEquals("Image 1", Util.getImageNameWithoutPostfix("Image 1  _R", 1));

		assertEquals("Image 1", Util.getImageNameWithoutPostfix("Image -l- 1", 0));
		assertEquals("Image 1", Util.getImageNameWithoutPostfix("Image -r- 1", 1));

		// Test with new postfix
		Constants.IMAGE_POSTFIXES.get(1).add("[RIGHT]");

		assertEquals("My image", Util.getImageNameWithoutPostfix("My image [RIGHT]", 1));

		assertEquals("My image", Util.getImageNameWithoutPostfix("My [RIGHT] image", 1));

		assertEquals(null, Util.getImageNameWithoutPostfix("My image [RIIGHT]", 1));
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
