/**
 * This code has been taken from 
 * http://impressive-artworx.de/2011/background-image-on-jpanel/
 */
package com.uw.tacoma.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class JBackgroundPanel extends JPanel {
    private static final long serialVersionUID = -8757256876325455801L;
	private BufferedImage img;
	
	public void setBackgroundImagePath(String fileName) {
		// load the background image
		try {
			this.img = (fileName != null) ? ImageIO.read(new File(fileName)) : null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (this.img != null)
		{
			// paint the background image and scale it according to the background image
			g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), this);
		}
	}
}
