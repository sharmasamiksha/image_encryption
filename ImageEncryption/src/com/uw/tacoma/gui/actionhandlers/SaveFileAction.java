package com.uw.tacoma.gui.actionhandlers;

import java.io.File;

import javax.swing.JFileChooser;

public class SaveFileAction {

    private static String path = "input";
	
	public SaveFileAction(FileSaveListener listener)
	{	
		JFileChooser fileChooser = new JFileChooser(path);
		fileChooser.setDialogTitle("Save image file...");
		int i = fileChooser.showSaveDialog(fileChooser);
		if (i == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			path = file.getAbsolutePath();
			try {
				listener.onFileSaved(file);
			}
			catch (NullPointerException ex) {
				// "Not a valid file input";
			} catch (NumberFormatException ex) {
				// "Not a valid file input";
			}
		}
    }
	
	public interface FileSaveListener {
		void onFileSaved(File file);
	}
}
