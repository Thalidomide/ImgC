package util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Olav Jensen
 * @since 08.apr.2010
 */
public class Constants {

	public static final Dimension SIZE = new Dimension(500, 700);

	public static final double ZOOM_FACTOR = 1.2;

	static final String REGEX_VALID_IMAGE_FILES = ".+\\.(jpg|JPG|png|PNG|bmp|BMP)";
	static List<String> RIGHT_IMAGE_POSTFIXES = new ArrayList<String>(Arrays.asList("_R"));
	static List<String> LEFT_IMAGE_POSTFIXES = new ArrayList<String>(Arrays.asList("_L"));

	public static Color EVEN_ROW = new Color(255, 255, 255);
	public static Color ODD_ROW = new Color(200, 220, 255);

	public static Font NORMAL = new Font("Verdana", Font.PLAIN, 12);
	public static Font HEADER_2 = new Font("Verdana", Font.BOLD, 14);

}
