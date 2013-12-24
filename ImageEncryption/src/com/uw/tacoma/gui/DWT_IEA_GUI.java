package com.uw.tacoma.gui;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JButton;

import com.uw.tacoma.DWT_IEA;
import com.uw.tacoma.ImageUtils;
import com.uw.tacoma.gui.actionhandlers.LoadFileAction;
import com.uw.tacoma.gui.actionhandlers.LoadFileAction.ImageType;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.BoxLayout;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DWT_IEA_GUI {

	private JFrame frame;
	
	// Encryption UI
	private JPanel panelBackgroundEncrypt;
	
	private JLabel imgOriginal;
	private JLabel imgCover;
	private JLabel imgWaveletDecomposition;
	private JLabel imgProcessedOriginal;
	private JLabel imgFusion;
	private JLabel imgFinalEncrypted;
	
	private JLabel labelCover;
	private JLabel labelOriginalImage;
	private JLabel labelWaveletDecomposedImage;
	private JLabel labelEncryptedOriginalImage;
	private JLabel labelCombinedImage;
	private JLabel labelFinalEncryptedImage;
	
	// Decryption UI
	private JPanel panelBackgroundDecrypt;
	
	private JLabel imgLoadEncrypted = new JLabel();
	private JLabel imgEncryptedDwt = new JLabel();
	private JLabel imgDecrypted = new JLabel();
	
	private JLabel labelEncryptedImage = new JLabel("Encrypted Image");
	private JLabel labelDwtTransformedEncrypted = new JLabel("DWT Transformed Encrypted Image");
	private JLabel labelDecryptedImage = new JLabel("Decrypted Image");
	
	public void setOriginalImage(Image image) {
		setScaledImage(this.imgOriginal, image);
	}

	public void setCoverImage(Image image) {
		setScaledImage(this.imgCover, image);
	}

	public void setWaveletDecompositionImage(Image image) {
		setScaledImage(this.imgWaveletDecomposition, image);
	}

	public void setProcessedOriginalImage(Image image) {
		setScaledImage(this.imgProcessedOriginal, image);
	}

	public void setFusionImage(Image image) {
		setScaledImage(this.imgFusion, image);
	}

	public void setFinalEncryptedImage(Image image) {
		setScaledImage(this.imgFinalEncrypted, image);
	}
	
	public void setLoadEncryptedImage(Image image) {
		setScaledImage(this.imgLoadEncrypted, image);
    }
	
	public void setEncryptedDwtImage(Image image) {
		setScaledImage(this.imgEncryptedDwt, image);
    }
	
	public void setDecryptedImage(Image image) {
		setScaledImage(this.imgDecrypted, image);
    }

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DWT_IEA_GUI window = new DWT_IEA_GUI();
					window.frame.setVisible(true);
					window.frame.setExtendedState(window.frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DWT_IEA_GUI() {
		
		DWT_IEA dwt = new DWT_IEA(this);
		initialize(dwt);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(final DWT_IEA listener) {
		frame = new JFrame();
		frame.setBounds(100, 100, 1300, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(tabbedPane);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Encrypt", null, panel_1, null);
		
		JPanel panel_3 = new JPanel();
		
		JButton btnLoadImage = new JButton();
		panel_3.add(btnLoadImage);
		btnLoadImage.setAction(new LoadFileAction(listener, ImageType.ORIGINAL));
		btnLoadImage.setText("Load Original Image");
		
		JButton btnCoverImage = new JButton();
		panel_3.add(btnCoverImage);
		btnCoverImage.setAction(new LoadFileAction(listener, ImageType.COVER));
		btnCoverImage.setText("Load Cover Image");
		
		JButton btnEncrypt = new JButton("Encrypt Image");		
		btnEncrypt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.encryptImage();
			}
		});
		
		panel_3.add(btnEncrypt);
		
		panelBackgroundEncrypt = new JBackgroundPanel();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addComponent(panelBackgroundEncrypt, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1269, Short.MAX_VALUE)
						.addComponent(panel_3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1359, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelBackgroundEncrypt, GroupLayout.PREFERRED_SIZE, 576, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
				
		imgOriginal = new JLabel();
		imgCover = new JLabel();
		imgWaveletDecomposition = new JLabel();
		imgFusion = new JLabel();
		imgFinalEncrypted = new JLabel();
		imgProcessedOriginal = new JLabel();
		
		labelCover = new JLabel("Cover Image");
		labelOriginalImage = new JLabel("Original Image");
		labelWaveletDecomposedImage = new JLabel("Wavelet Decomposed Image");
		labelEncryptedOriginalImage = new JLabel("Encrypted Original Image");
		labelCombinedImage = new JLabel("Combined DWT Image");
		labelFinalEncryptedImage = new JLabel("Final Encrypted Image");
		
		GroupLayout gl_panel_4 = new GroupLayout(panelBackgroundEncrypt);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_4.createSequentialGroup()
									.addContainerGap()
									.addComponent(imgCover, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE)
									.addGap(52)
									.addComponent(imgWaveletDecomposition, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel_4.createSequentialGroup()
									.addGap(101)
									.addComponent(labelCover)
									.addGap(199)
									.addComponent(labelWaveletDecomposedImage, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)))
							.addGap(70))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(89)
							.addComponent(imgOriginal, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(imgProcessedOriginal, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
							.addGap(138))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(120)
							.addComponent(labelOriginalImage, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(labelEncryptedOriginalImage, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
							.addGap(129)))
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(imgFusion, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE)
							.addGap(35)
							.addComponent(imgFinalEncrypted, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(68, Short.MAX_VALUE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(45)
							.addComponent(labelCombinedImage, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
							.addGap(187)
							.addComponent(labelFinalEncryptedImage, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(133, Short.MAX_VALUE))))
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(imgWaveletDecomposition, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE)
								.addComponent(imgCover, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(labelCover)
								.addComponent(labelWaveletDecomposedImage))
							.addGap(59)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(imgOriginal, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
								.addComponent(imgProcessedOriginal, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(159)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(imgFinalEncrypted, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE)
								.addComponent(imgFusion, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(labelFinalEncryptedImage)
								.addComponent(labelCombinedImage))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(labelOriginalImage)
						.addComponent(labelEncryptedOriginalImage))
					.addContainerGap(82, Short.MAX_VALUE))
		);
		panelBackgroundEncrypt.setLayout(gl_panel_4);
		panel_1.setLayout(gl_panel_1);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Decrypt", null, panel_2, null);
		
		JPanel panel_5 = new JPanel();
		
		JButton btnEncryptedImage = new JButton();
		panel_5.add(btnEncryptedImage);
		btnEncryptedImage.setAction(new LoadFileAction(listener, ImageType.ENCRYPTED));
		btnEncryptedImage.setText("Load Encrypted Image");
		JButton btnDecrypt = new JButton("Decrypt Image");
		btnDecrypt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.decryptImage();
			}
		});
		
		panel_5.add(btnDecrypt);
		
		panelBackgroundDecrypt = new JPanel();
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_5, GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
						.addComponent(panelBackgroundDecrypt, GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE))
					.addGap(10))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(11)
					.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(panelBackgroundDecrypt, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
					.addGap(11))
		);
		
		imgLoadEncrypted = new JLabel();
		imgEncryptedDwt = new JLabel();
		imgDecrypted = new JLabel();
		
		labelEncryptedImage = new JLabel("Encrypted Image");
		labelDwtTransformedEncrypted = new JLabel("DWT Transformed Encrypted Image");
		labelDecryptedImage = new JLabel("Decrypted Image");
		
//		imageLoadEncrypted.setBackground(Color.BLACK);
//		imageEncryptedDwt.setBackground(Color.BLACK);
//		imageDecrypted.setBackground(Color.BLACK);
		
		
		GroupLayout gl_panelDecryptionBackground = new GroupLayout(panelBackgroundDecrypt);
		gl_panelDecryptionBackground.setHorizontalGroup(
			gl_panelDecryptionBackground.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDecryptionBackground.createSequentialGroup()
					.addGroup(gl_panelDecryptionBackground.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelDecryptionBackground.createSequentialGroup()
							.addContainerGap()
							.addComponent(imgLoadEncrypted, GroupLayout.PREFERRED_SIZE, 512, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_panelDecryptionBackground.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelDecryptionBackground.createSequentialGroup()
									.addGap(49)
									.addComponent(imgEncryptedDwt, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE)
									.addGap(50)
									.addComponent(imgDecrypted, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panelDecryptionBackground.createSequentialGroup()
									.addGap(65)
									.addComponent(labelDwtTransformedEncrypted, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
									.addGap(137)
									.addComponent(labelDecryptedImage, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_panelDecryptionBackground.createSequentialGroup()
							.addGap(216)
							.addComponent(labelEncryptedImage)))
					.addContainerGap(126, Short.MAX_VALUE))
		);
		gl_panelDecryptionBackground.setVerticalGroup(
			gl_panelDecryptionBackground.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDecryptionBackground.createSequentialGroup()
					.addGroup(gl_panelDecryptionBackground.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelDecryptionBackground.createSequentialGroup()
							.addContainerGap()
							.addComponent(imgLoadEncrypted, GroupLayout.PREFERRED_SIZE, 512, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelDecryptionBackground.createSequentialGroup()
							.addGap(153)
							.addGroup(gl_panelDecryptionBackground.createParallelGroup(Alignment.LEADING)
								.addComponent(imgDecrypted, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE)
								.addComponent(imgEncryptedDwt, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_panelDecryptionBackground.createParallelGroup(Alignment.LEADING, false)
								.addComponent(labelDwtTransformedEncrypted, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(labelDecryptedImage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
					.addGap(18)
					.addComponent(labelEncryptedImage)
					.addContainerGap(19, Short.MAX_VALUE))
		);
		panelBackgroundDecrypt.setLayout(gl_panelDecryptionBackground);
		panel_2.setLayout(gl_panel_2);
		panel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{tabbedPane, panel_1, btnLoadImage, btnCoverImage, btnEncrypt, btnDecrypt, btnEncryptedImage}));
		
		toggleStepLabels(false);
	}
	
	public void toggleStepLabels(boolean flag) {
		String filePath = flag ? "images/Background_Encryption_Final.png" : null;
		
		((JBackgroundPanel) this.panelBackgroundEncrypt).setBackgroundImagePath(filePath);
		
		labelWaveletDecomposedImage.setVisible(flag);
		labelEncryptedOriginalImage.setVisible(flag);
		labelCombinedImage.setVisible(flag);
		labelFinalEncryptedImage.setVisible(flag);
	}
	
	private void setScaledImage(JLabel label, Image originalImage) {
	    BufferedImage bufferedImage = ImageUtils.createResizedCopy(
				originalImage,
				label.getWidth(), 
				label.getHeight(), 
				true);
		label.setIcon(new ImageIcon(bufferedImage));
    }
}
