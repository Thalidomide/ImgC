package olj.ic.util;

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
    
    public static final String VERSION = "0.0032";

	public static final Dimension SIZE = new Dimension(800, 900);
	public static final int DEFAULT_PREVIEW_WIDTH = 1024;

	public static final double ZOOM_FACTOR = 1.2;
	public static final double ZOOM_MAX = 20;
	public static final double ZOOM_MIN = 0.05;

	static final String REGEX_VALID_IMAGE_FILES = ".+\\.(jpg|JPG|png|PNG|bmp|BMP)";
	public static final int MIN_IMAGE_PARTS = 2;
	public static final int MAX_IMAGE_PARTS = 4;

	public static final int MIN_WORKING_THREADS = 1;
	public static final int MAX_WORKING_THREADS = 8;

	static List<List<String>> IMAGE_POSTFIXES = initializeImagePostFixes();

	public static final Color BACKGROUND = new Color(20, 30, 50);
	public static final Color BACKGROUND_LIGHT = new Color(30, 40, 70);
	public static final Color BACKGROUND_LIGHTER = new Color(60, 80, 140);
	public static final Color BACKGROUND_BUTTON = new Color(30, 40, 90);
	public static final Color BACKGROUND_INPUT = new Color(100, 120, 150);
	public static final Color BACKGROUND_INPUT_ERROR = new Color(100, 20, 10);
	public static final Color FONT = Color.ORANGE;
	public static final Color FONT_DISABLED = new Color(140, 70, 30);

	public static final Font NORMAL = new Font("Verdana", Font.PLAIN, 12);
	public static final Font SMALLER = new Font("Verdana", Font.PLAIN, 10);
	public static final Font HEADER_2 = new Font("Verdana", Font.BOLD, 14);


	private static List<List<String>> initializeImagePostFixes() {
		ArrayList<List<String>> postfixes = new ArrayList<List<String>>(MAX_IMAGE_PARTS - MIN_IMAGE_PARTS);
		postfixes.add(new ArrayList<String>(Arrays.asList("_L", "-l-", "-a-")));
		postfixes.add(new ArrayList<String>(Arrays.asList("_R", "-r-", "-b-")));
		postfixes.add(new ArrayList<String>(Arrays.asList("-c-")));

		for (int i = 0; i < MAX_IMAGE_PARTS; i++) {
			String postfix = "_" + i;

			if (i < postfixes.size()) {
				postfixes.get(i).add(postfix);
			} else {
				postfixes.add(new ArrayList<String>(Arrays.asList(postfix)));
			}
		}

		return postfixes;
	}

}
