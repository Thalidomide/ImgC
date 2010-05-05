package olj.ic.engine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import olj.ic.entities.ImageUnit;
import olj.ic.util.Manager;

/**
 * @author Olav Jensen
 * @since Apr 12, 2010
 */
public class ImageEngineUtil {

	private static Map<EngineMode, ImageEngine> engines = new HashMap<EngineMode, ImageEngine>();

	public static BufferedImage getCalculatedImage(ImageUnit imageUnit) {
		return getEngine().getCalculatedImage(imageUnit);
	}

	public static List<ImageUnit> getImageUnits(File[] files) {
		if (files == null) {
			return Collections.emptyList();
		}
		return getEngine().getImageUnits(files);
	}

	public static ImageEngine getEngine() {
		EngineMode engineMode = Manager.get().getEngineSettings().getEngineMode();
		ImageEngine engine = engines.get(engineMode);

		if (engine == null) {
			try {
				engine = engineMode.getEngineClass().newInstance();
				engines.put(engineMode, engine);
			} catch (Exception e) {
				throw new RuntimeException("Could not instantiate ic.engine", e);
			}
		}

		return engine;
	}
}
