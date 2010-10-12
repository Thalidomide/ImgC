package olj.ic.engine.modes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import olj.ic.engine.EngineMode;
import olj.ic.engine.modes.util.ImageUtil;
import olj.ic.entities.ImageComponent;
import olj.ic.entities.ImageUnit;
import olj.ic.util.Util;

/**
 * Simple engine for applying the common options without merging or restructuring the image.
 *
 * @author olav
 * @since 12.okt.2010
 */
public class ImageManipulator implements ImageEngine {

    @Override
    public EngineMode getEngineMode() {
        return EngineMode.manipulate;
    }

    @Override
    public BufferedImage getCalculatedImage(ImageUnit imageUnit) {
        if (imageUnit.getComponents().size() != 1) {
            throw new IllegalStateException("Invalid number of components: " + imageUnit.getComponents().size());

        }

        return imageUnit.getComponents().get(0).getImage();
    }

    @Override
    public List<ImageUnit> getImageUnits(File[] files) {
        return ImageUtil.getSingleImageUnitsFromFiles(files);
    }
}
