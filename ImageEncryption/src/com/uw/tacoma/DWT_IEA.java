package com.uw.tacoma;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.crypto.SecretKey;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import math.transform.jwave.Transform;
import math.transform.jwave.handlers.DiscreteWaveletTransform;
import math.transform.jwave.handlers.wavelets.Haar02Orthogonal;
import math.transform.jwave.handlers.wavelets.Wavelet;

import com.uw.tacoma.gui.DWT_IEA_GUI;
import com.uw.tacoma.gui.actionhandlers.LoadFileAction.FileLoadedListener;
import com.uw.tacoma.gui.actionhandlers.LoadFileAction.ImageType;
import com.uw.tacoma.gui.actionhandlers.SaveFileAction;
import com.uw.tacoma.gui.actionhandlers.SaveFileAction.FileSaveListener;

public class DWT_IEA implements FileLoadedListener, FileSaveListener {
	
	private static final int COVER_IMAGE_SIZE = 512;
	private static final int ORIGINAL_IMAGE_SIZE = 256;
	
	private static final Wavelet wavelet = new Haar02Orthogonal();
	
	private SecretKey secretKey;
	private DWT_IEA_GUI guiHandle;
	
	private BufferedImage coverImage;
	private BufferedImage originalImage;
	private BufferedImage loadEncryptedImage;
	private BufferedImage finalEncryptedImage;
	
	public DWT_IEA(DWT_IEA_GUI guiHandle) {
	    this.guiHandle = guiHandle;
    }
	
	public void encryptImage() {
	    try {
			// Apply DWT transformation to Cover Image
			double[][] arrFreq = getDwtTransformedImage(this.coverImage);
			byte[] pixelsDwtTransformedImage = convert2DTo1DArray(arrFreq);
			BufferedImage dwtTransformedImage = getImageFromByte(
					pixelsDwtTransformedImage, 
					this.coverImage.getWidth(), 
					this.coverImage.getHeight(),
					this.coverImage.getType());
			guiHandle.setWaveletDecompositionImage(dwtTransformedImage);
			
			// Encrypt Original Image
			secretKey = EncryptDecryptUtils.generateKey();
			byte pixelsOriginal[] = ((DataBufferByte) this.originalImage.getRaster().getDataBuffer()).getData();
			byte pixelsOriginalEncrypted[] = EncryptDecryptUtils.encryptByteArrayWithKey(pixelsOriginal, secretKey.getEncoded() );
			BufferedImage encryptedOriginalImage = getImageFromByte(
					pixelsOriginalEncrypted, 
					this.originalImage.getWidth(), 
					this.originalImage.getHeight(),
					this.originalImage.getType());
			guiHandle.setProcessedOriginalImage(encryptedOriginalImage);
			
		   	// Hide encrypted image inside HH band
			int halfLength = arrFreq.length / 2;
			for(int i=halfLength; i < arrFreq.length; i++) {
				for(int j=halfLength; j < arrFreq[i].length; j++) {
					arrFreq[i][j] = pixelsOriginalEncrypted[(i - halfLength) * halfLength + j - halfLength];
				}
			}
			
			byte[] pixelsCombinedDwtImage = convert2DTo1DArray(arrFreq);
			BufferedImage combinedDwtImage = getImageFromByte(
					pixelsCombinedDwtImage, 
					this.coverImage.getWidth(), 
					this.coverImage.getHeight(),
					this.coverImage.getType());
			guiHandle.setFusionImage(combinedDwtImage);
			
			// Apply inverse 
			double[][] arrTime = getInverseDwtTransformedImage(arrFreq);
			byte pixelsInvertedDwt[] = convert2DTo1DArray(arrTime);
			finalEncryptedImage = getImageFromByte(
					pixelsInvertedDwt, 
					this.coverImage.getWidth(), 
					this.coverImage.getHeight(), 
					this.coverImage.getType());
		    guiHandle.setFinalEncryptedImage(finalEncryptedImage);
		    guiHandle.toggleStepLabels(true);
		    
		    new SaveFileAction(this);
	    } catch (Exception ex) {
	    	System.out.print(ex.toString());
	    }
	}
	
	public void decryptImage() {
		try {
			// Apply DWT transformation to Decrypted Image
			double[][] arrFreq = getDwtTransformedImage(this.loadEncryptedImage);
			byte[] pixelsDwtTransformedEncryptedImage = convert2DTo1DArray(arrFreq);
			BufferedImage dwtTransformedEncryptedImage = getImageFromByte(
					pixelsDwtTransformedEncryptedImage, 
					this.loadEncryptedImage.getWidth(), 
					this.loadEncryptedImage.getHeight(),
					this.loadEncryptedImage.getType());
			guiHandle.setEncryptedDwtImage(dwtTransformedEncryptedImage);
			
			// Extract actual encrypted pixels
			int halfLength = arrFreq.length / 2;
			byte pixelsTransformedEncryptedImage[] = new byte[halfLength * halfLength];
			for(int i = 0; i < halfLength; i++) {
				for(int j = 0; j < halfLength; j++) {
					pixelsTransformedEncryptedImage[i * halfLength + j] = (byte) arrFreq[halfLength + i][halfLength + j];
				}
			}
			
			// Decrypt encrypted key
			byte pixelsOriginalEncrypted[] = EncryptDecryptUtils.decryptByteArrayWithKey(pixelsTransformedEncryptedImage, secretKey.getEncoded() );
			BufferedImage extractedEncryptedImage = getImageFromByte(
					pixelsOriginalEncrypted, 
					halfLength, 
					halfLength,
					this.loadEncryptedImage.getType());
			guiHandle.setDecryptedImage(extractedEncryptedImage);
		} catch (Exception ex) {
			System.out.print(ex.toString());
		}
		
	}

	private BufferedImage getImageFromByte(byte[] data, int width, int height, int type) {
	    BufferedImage image = new BufferedImage(width, 
				height, type);
		image.getRaster().setDataElements(0, 0, width, height, data);
	    return image;
    }

	private double[][] getInverseDwtTransformedImage(double[][] arrFreq) {
	    Transform t = new Transform( new DiscreteWaveletTransform( wavelet ) );
		double[][] arrTime = t.reverse(arrFreq);
	    return arrTime;
    }

	private double[][] getDwtTransformedImage(BufferedImage image) {
		Transform t = new Transform( new DiscreteWaveletTransform( wavelet ) );
	    byte pixelsImage[] = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		double[][] doubles = convert1DTo2DArray(pixelsImage, image.getWidth(), image.getHeight());
		
		// Apply DWT on Cover image
		double[][] arrFreq = t.forward( doubles ); // 2-D DWT forward
	    return arrFreq;
    }

	private byte[] convert2DTo1DArray(double[][] arrTime) {
		if (arrTime.length == 0)
			return new byte[0];
		
		byte[] bytes = new byte[arrTime.length * arrTime[0].length];
		
	    for(int i=0; i < arrTime.length; i++) {
			for(int j=0; j < arrTime[i].length; j++) {
				bytes[i * arrTime.length + j] = (byte) arrTime[i][j] ; 
			}
		}
	    return bytes;
    }

	private double[][] convert1DTo2DArray(byte[] pixelsEncryptedCover, int width, int height) {
	    double[][] doubles = new double[width][height];
		
		for(int i=0; i < doubles.length; i++) {
			for(int j=0; j < doubles[i].length; j++) {
				doubles[i][j] = pixelsEncryptedCover[i * doubles[i].length + j];
			}
		}
		
	    return doubles;
    }

	@Override
    public void onFileLoaded(File file, ImageType imageType) {
		
		switch (imageType)
	    {
	    case COVER:
	    	Image imageCover = new ImageIcon(file.getAbsolutePath()).getImage();
	    	guiHandle.setCoverImage(imageCover);
	    	this.coverImage = ImageUtils.createResizedCopy(imageCover, 
	    			COVER_IMAGE_SIZE, COVER_IMAGE_SIZE, 
	        		true);
	    	break;
	    case ORIGINAL:
	    	Image imageOriginal = new ImageIcon(file.getAbsolutePath()).getImage();
	    	guiHandle.setOriginalImage(imageOriginal);
	    	this.originalImage = ImageUtils.createResizedCopy(imageOriginal, 
	        		ORIGINAL_IMAGE_SIZE, ORIGINAL_IMAGE_SIZE, 
	        		true);
	    	break;
	    case ENCRYPTED:
	    	Image imageEncrypted = new ImageIcon(file.getAbsolutePath()).getImage();
	    	guiHandle.setLoadEncryptedImage(imageEncrypted);
	    	this.loadEncryptedImage = ImageUtils.createResizedCopy(imageEncrypted, 
	    			COVER_IMAGE_SIZE, COVER_IMAGE_SIZE, 
	        		true);
	    	break;
	    }
    }

	@Override
    public void onFileSaved(File file) {
	    try {
	        ImageIO.write(this.finalEncryptedImage, "jpg", file);
        } catch (IOException ex) {
	        System.out.println(ex.toString());
	        ex.printStackTrace();
        }
    }
	
	
}