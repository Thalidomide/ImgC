package olj.ic.entities;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import olj.ic.engine.ImageEngineUtil;

/**
 * @author Olav Jensen
 * @since 08.apr.2010
 */
public class ImageUnit {

	private final List<ImageComponent> components;

	public ImageUnit(List<ImageComponent> imageComponents) {
		this.components = imageComponents;
		Collections.sort(components, new Comparator<ImageComponent>() {
			@Override
			public int compare(ImageComponent o1, ImageComponent o2) {
				return o1.getPosition() - o2.getPosition();
			}
		});
		checkValidity();
	}

	private void checkValidity() {
		if (components.isEmpty()) {
			throw new RuntimeException("Components cannot be empty");
		}
		List<Integer> indexes = new ArrayList<Integer>(components.size());
		Integer previous = null;
		for (ImageComponent component : components) {
			int currentIndex = component.getPosition();

			if (previous == null) {
				if (currentIndex != 0) {
					throw new RuntimeException("Indexes have to start at 0");
				}
			} else if (currentIndex != previous + 1) {
				throw new RuntimeException("Indexes did not increase by 1! Previous, current: " + previous + ", " + currentIndex);
			}

			if (indexes.contains(currentIndex)) {
				throw new RuntimeException("Cannot have duplicate indexes: " + currentIndex);
			} else {
				indexes.add(currentIndex);
			}
			previous = currentIndex;
		}
	}

	public BufferedImage getImageResult() {
		return ImageEngineUtil.getCalculatedImage(this);
	}

	public ImageComponent getImageComponent(int index) {
		return components.get(index);
	}

	public int getImageComponents() {
		return components.size();
	}

	public String getName() {
		return components.get(0).getName();
	}

	public List<ImageComponent> getComponents() {
		return components;
	}
}
