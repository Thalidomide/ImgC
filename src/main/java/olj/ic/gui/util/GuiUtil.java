package olj.ic.gui.util;

import java.awt.GridBagConstraints;

/**
 * @author Olav Jensen
 * @since May 4, 2010
 */
public class GuiUtil {

	/**
	 * Transforms a specified value into a String with the specified length. If shorter than the length, it will be prefixed with
	 * the required number of zeros.
	 *
	 * @param value the specified value
	 * @param length the specified length
	 * @return the value as a String.
	 */
	public static String asString(int value, int length) {
		String res = value + "";
		while (res.length() < length) {
			res = "0" + res;
		}
		return res;
	}

    public static GridBagConstraints getDefaultGridBagConstraints() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.NORTHEAST;
        constraints.weightx = 1;
        return constraints;
    }
}
