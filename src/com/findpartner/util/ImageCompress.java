package com.findpartner.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

public class ImageCompress {

	public static void CompressByPercent(File srcImg, String disImg,
			double percent) throws IOException {
		BufferedImage image = ImageIO.read(srcImg);
		Thumbnails.of(image).scale(percent).toFile(disImg);
	}
	
	public static void CompressByPercent(File srcImg, String disImg
			) throws IOException {
		BufferedImage image = ImageIO.read(srcImg);
		Thumbnails.of(image).scale(0.4f).toFile(disImg);
	}

}
