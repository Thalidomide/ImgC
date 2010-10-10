package olj.ic.gui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.TransferHandler;

/**
 * @author Olav Jensen
 * @since Oct 10, 2010
 */
public class ImageTransferHandler extends TransferHandler {

	private final ImageTransferHandlerListener listener;

	public ImageTransferHandler(ImageTransferHandlerListener listener) {
		this.listener = listener;
	}

	@Override
	public boolean canImport(TransferSupport support) {
		return true;
	}

	@Override
	public boolean importData(TransferSupport support) {
		for (DataFlavor dataFlavor : support.getDataFlavors()) {
			if (dataFlavor.isFlavorJavaFileListType()) {

				try {
					openFiles(support);
				} catch (UnsupportedFlavorException e) {
					e.printStackTrace(); //TODO Implement
				} catch (IOException e) {
					e.printStackTrace(); //TODO Implement
				}
			}
		}

		return super.importData(support);
	}

	private void openFiles(TransferSupport support) throws UnsupportedFlavorException, IOException {
		List<File> files = (List<File>) support.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
		File[] filesAr = new File[files.size()];

		int i = 0;
		for (File file : files) {
			filesAr[i++] = file;
		}

		listener.openFiles(filesAr);
	}
}
