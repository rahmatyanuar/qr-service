package com.rahmat.qr.utility;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

import com.google.common.io.Files;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Component
public class QRGenerator{
	QRCodeWriter qrWriter = new QRCodeWriter();
	
	public byte[] QrByte(String data) throws WriterException, IOException {
		BitMatrix bitStorage = qrWriter.encode(data, BarcodeFormat.QR_CODE, 300, 300);
		//Load QR
		BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitStorage);
		
		// Load logo image
		File file = new File(".\\qr\\gel_logo.jpg");
	    BufferedImage logoImage = ImageIO.read(file);
	    
	    // Calculate the delta height and width between QR code and logo
	    int deltaHeight = qrImage.getHeight() - logoImage.getHeight();
	    int deltaWidth = qrImage.getWidth() - logoImage.getWidth();
	    
	    // Initialize combined image
	    BufferedImage combined = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(), BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = (Graphics2D) combined.getGraphics();
	    
	    // Write QR code to new image at position 0/0
	    g.drawImage(qrImage, 0, 0, null);
	    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	    
	    // Write logo into combine image at position (deltaWidth / 2) and
	    // (deltaHeight / 2). Background: Left/Right and Top/Bottom must be
	    // the same space for the logo to be centered
	    g.drawImage(logoImage, (int) Math.round(deltaWidth / 2), (int) Math.round(deltaHeight / 2), null);
	    
		ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
		try {
//			MatrixToImageWriter.writeToStream(bitStorage, "PNG", pngOutputStream);
			ImageIO.write(combined, "PNG", pngOutputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] pngData = pngOutputStream.toByteArray();
		return pngData;
	}
	
	public String createImageFile() throws IOException {
		byte[] data;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		String fileName = sdf.format(now)+".png";
		File file = new File(".\\qr\\"+fileName);
		try {
			data = this.QrByte("Test 123");
			Files.write(data, file);
			return "OK";
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "FAIL";
	}
}
