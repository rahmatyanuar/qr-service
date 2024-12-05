package com.rahmat.qr.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.WriterException;
import com.rahmat.qr.utility.QRGenerator;

@RestController
@CrossOrigin(origins = "*")
public class QrController {
	
	@Autowired
	QRGenerator qr;
	
	@GetMapping("/generateQr")
	public String generateQrImage() throws IOException {
		String status = qr.createImageFile();
		return status;
	}
	
	@GetMapping("/generateMediaType")
	public void generateQrMedia(HttpServletResponse response) throws IOException, WriterException {
		response.setContentType("image/png");
		OutputStream os = response.getOutputStream();
		os.write(qr.QrByte("https://www.google.com"));
		os.flush();
		os.close();	
	}
}
