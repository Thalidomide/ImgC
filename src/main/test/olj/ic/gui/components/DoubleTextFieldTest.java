package olj.ic.gui.components;

import junit.framework.TestCase;

/**
 * @author Olav Jensen
 * @since 07.mai.2010
 */
public class DoubleTextFieldTest extends TestCase {

	public void testGetDouble() throws Exception {
		DoubleTextField field = new DoubleTextField();
		field.setOptional(true);

		assertTrue(field.isValidDouble());

		field.setOptional(false);
		assertFalse(field.isValidDouble());

		field.setMin(10.0);
		field.setDouble(10.0);
		assertTrue(field.isValidDouble());

		field.setDouble(9.9);
		assertFalse(field.isValidDouble());

		field.setMax(20.0);
		field.setDouble(20.0);
		assertTrue(field.isValidDouble());

		field.setDouble(20.1);
		assertFalse(field.isValidDouble());
	}

	public void testIsValidDouble() throws Exception {
		DoubleTextField field = new DoubleTextField();

		assertTrue(field.isValidDouble());

		field.setText("23");
		assertTrue(field.isValidDouble());

		field.setText("23a");
		assertFalse(field.isValidDouble());
	}
}
