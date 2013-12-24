package com.uw.tacoma.gui.actionhandlers;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

public class LoadFileAction extends AbstractAction {

    private static final long serialVersionUID = 1L;
	private static String path = "input";
	private FileLoadedListener listener;
	private ImageType imageType;
	
	
	public LoadFileAction(FileLoadedListener listener, ImageType imageType) {
		this.listener = listener;
		this.imageType = imageType;
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser(path);
		fileChooser.setDialogTitle("Open image file...");
		int i = fileChooser.showOpenDialog(fileChooser);
		if (i == JFileChooser.OPEN_DIALOG) {
			File file = fileChooser.getSelectedFile();
			path = file.getAbsolutePath();
			try {
				this.listener.onFileLoaded(file, this.imageType);
			}
			catch (NullPointerException ex) {
				// "Not a valid file input";
			} catch (NumberFormatException ex) {
				// "Not a valid file input";
			}
		}
    }
	
	public interface FileLoadedListener {
		void onFileLoaded(File file, ImageType imageType);
	}

	public enum ImageType {
		COVER,
		ORIGINAL,
		ENCRYPTED
	}
}
