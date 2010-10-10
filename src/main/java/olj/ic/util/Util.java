package olj.ic.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * @author Olav Jensen
 * @since 08.apr.2010
 */
public class Util {

	public static BufferedImage loadImage(File file) {
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
			return getTestImage(100, 100);
		}
	}

	public static BufferedImage getTestImage(int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2D = image.createGraphics();
		int r = (int) (Math.random() * 250);
		int g = (int) (Math.random() * 250);
		int b = (int) (Math.random() * 250);
		g2D.setColor(new Color(r, g, b));
		g2D.fillRect(0, 0, width, height);
		g2D.setColor(new Color(255 - r, 255 - g, 255 - b));
		g2D.drawOval(0, 0, width, height);
		g2D.drawLine(0, height, width, 0);
		return image;
	}

	public static String getFileNameWithoutEnding(String nameWithEnding) {
		String[] parts = nameWithEnding.split("\\.");
		return parts[0];
	}

	public static boolean isValidImageFile(String name) {
		return name.matches(Constants.REGEX_VALID_IMAGE_FILES);
	}

	public static String getImageNameWithoutPostfix(String postfixedName, int index) {
		return getTrimmedName(postfixedName, Constants.IMAGE_POSTFIXES.get(index));
	}

	public static boolean stringsAreDifferent(String s1, String s2) {
		return s1 == null && s2 != null || (s1 != null && !s1.equals(s2));
	}

	public static JFileChooser getImageFileChooser() {
		JFileChooser fileChooser = new JFileChooser("C:\\");
        fileChooser.setDialogTitle("Select images or folder containing images");
		fileChooser.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				return f.isDirectory() || isValidImageFile(f.getName());
			}

			@Override
			public String getDescription() {
				return "Supported image files or folder";
			}
		});
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		return fileChooser;
	}

	private static String getTrimmedName(String name, List<String> postfixes) {
		for (String postfix : postfixes) {
			int index = name.toUpperCase().indexOf(postfix.toUpperCase());
			if (index != -1) {
				int index2 = index + postfix.length();
				String part1 = name.substring(0, index);
				String part2 = name.substring(index2);
				if (part1.endsWith(" ") && part2.startsWith(" ")) {
					part1 = part1.trim();
				}
				return (part1 + part2).trim();
			}
		}
		return null;
	}

	public static Vector<Integer> getInterval(int low, int max) {
		Vector<Integer> result = new Vector<Integer>();

		for (int i = low; i <= max; i++) {
			result.add(i);
		}

		return result;
	}
}
