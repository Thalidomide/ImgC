package olj.ic.gui.util;

import junit.framework.TestCase;

/**
 * @author Olav Jensen
 * @since May 4, 2010
 */
public class GuiUtilTest extends TestCase {

	public void testAsString() throws Exception {
		assertEquals("1", GuiUtil.asString(1, 1));
		assertEquals("01", GuiUtil.asString(1, 2));
		assertEquals("012", GuiUtil.asString(12, 3));
	}
}
