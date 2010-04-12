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
	public static final int DEFAULT_PREVIEW_WIDTH = 1024;

	public static final double ZOOM_FACTOR = 1.2;

	static final String REGEX_VALID_IMAGE_FILES = ".+\\.(jpg|JPG|png|PNG|bmp|BMP)";
	public static final int MIN_IMAGE_PARTS = 2;
	public static final int MAX_IMAGE_PARTS = 4;

	static List<List<String>> IMAGE_POSTFIXES = initializeImagePostFixes();
	public static Color EVEN_ROW = new Color(255, 255, 255);

	public static Color ODD_ROW = new Color(200, 220, 255);
	public static Font NORMAL = new Font("Verdana", Font.PLAIN, 12);

	public static Font HEADER_2 = new Font("Verdana", Font.BOLD, 14);


	private static List<List<String>> initializeImagePostFixes() {
		ArrayList<List<String>> postfixes = new ArrayList<List<String>>(MAX_IMAGE_PARTS - MIN_IMAGE_PARTS);
		postfixes.add(new ArrayList<String>(Arrays.asList("_L")));
		postfixes.add(new ArrayList<String>(Arrays.asList("_R")));

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
