package com.mallorca.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.jhlabs.image.GaussianFilter;
import com.mallorca.exception.TextOnImageException;

public class TextOnImageUtil {

	private static final int IMG_WIDTH = 520;
	private static final int IMG_HEIGHT = 270;
	private static final double PERFECT_RATIO = ((double) IMG_WIDTH) / IMG_HEIGHT;

	public static void putTextOnImage(String text, String imageUrl, String resultImagePath) {
		if (text.length() > 40) {
			throw new TextOnImageException("Sorry, but text is too long(max 40 symbols are allowed)");
		}
		try {
			if (text.length() < 21) {
				addOneLineText(text, imageUrl, resultImagePath);
			} else {
				String[] splited = text.split(" ");
				StringBuilder firstLine = new StringBuilder(splited[0]);
				StringBuilder secondLine = new StringBuilder("");
				int index = 1;
				while (index < splited.length && firstLine.length() + splited[index].length() < 20) {
					firstLine.append(" ");
					firstLine.append(splited[index]);
					index++;
				}
				if (index < splited.length) {
					secondLine.append(splited[index]);
					index++;
				}
				while (index < splited.length) {
					secondLine.append(" ");
					secondLine.append(splited[index]);
					index++;
				}
				if (firstLine.length() > 20 || secondLine.length() > 20) {
					throw new TextOnImageException("Sorry but text has wrong format, please try another");
				}
				addTwoLineText(firstLine.toString(), secondLine.toString(), imageUrl, resultImagePath);
			}
		} catch (IOException e) {
			throw new TextOnImageException("Sorry, but we cannot put text on your picture please send another", e);
		}

	}

	private static void addTwoLineText(String firstLine, String secondLine, String imageUrl, String resultImagePath) throws IOException {
		BufferedImage first = addShadow(firstLine);
		BufferedImage second = addShadow(secondLine);
		URL url = new URL(imageUrl);
		BufferedImage source = ImageIO.read(url);
		int type = source.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : source.getType();
		source = cropImageToStandartSize(source);
		source = resizeImage(source, type, IMG_WIDTH, IMG_HEIGHT);
		Graphics g = source.getGraphics();
		g.drawImage(first, IMG_WIDTH / 2 - first.getWidth() / 2 + 7, IMG_HEIGHT / 2 - 55, null);
		g.drawImage(second, IMG_WIDTH / 2 - second.getWidth() / 2 + 7, IMG_HEIGHT / 2 - 10, null);
		ImageIO.write(source, "png", new File(resultImagePath));
	}

	private static BufferedImage cropImageToStandartSize(BufferedImage image) {
		double imageRatio = ((double) image.getWidth()) / image.getHeight();
		if (Math.abs(imageRatio - PERFECT_RATIO) < 0.001) {
			return image;
		}
		if (imageRatio < PERFECT_RATIO) {
			return image.getSubimage(0, (image.getHeight() - (int) (image.getWidth() / PERFECT_RATIO)) / 2, image.getWidth(), (int) (image.getWidth() / PERFECT_RATIO));
		}

		return image.getSubimage((image.getWidth() - (int) (image.getHeight() * PERFECT_RATIO)) / 2, 0, (int) (image.getHeight() * PERFECT_RATIO), image.getHeight());

	}

	private static void addOneLineText(String text, String imageUrl, String resultImagePath) throws IOException {
		BufferedImage image = addShadow(text);
		URL url = new URL(imageUrl);
		BufferedImage source = ImageIO.read(url);
		int type = source.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : source.getType();
		source = cropImageToStandartSize(source);
		source = resizeImage(source, type, IMG_WIDTH, IMG_HEIGHT);
		Graphics g = source.getGraphics();
		g.drawImage(image, source.getWidth() / 2 - image.getWidth() / 2 + 7, source.getHeight() / 2 - image.getHeight() / 2, null);
		ImageIO.write(source, "png", new File(resultImagePath));
	}

	private static BufferedImage addShadow(String text) {
		BufferedImage image = convertTextToGraphic(text, new Font("Helvetica", Font.PLAIN, 45));
		// write BufferedImage to file
		image = applyShadow(image, 1, Color.BLACK, 1.0f);
		image = applyShadow(image, 7, Color.BLACK, 1.0f);
		return image;
	}

	private static BufferedImage resizeImage(BufferedImage originalImage, int type, int width, int height) {
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();

		return resizedImage;
	}

	private static BufferedImage convertTextToGraphic(String text, Font font) {

		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();

		g2d.setFont(font);
		FontMetrics fm = g2d.getFontMetrics();
		int width = fm.stringWidth(text);
		int height = fm.getHeight();
		g2d.dispose();

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		g2d = img.createGraphics();
		applyQualityRenderingHints(g2d);
		g2d.setFont(font);
		fm = g2d.getFontMetrics();
		g2d.setColor(Color.WHITE);
		g2d.drawString(text, 0, fm.getAscent());
		g2d.dispose();
		return img;
	}

	private static void applyQualityRenderingHints(Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
	}

	private static BufferedImage createCompatibleImage(int width, int height) {
		return new BufferedImage(width, height, 2);
	}

	private static BufferedImage generateBlur(BufferedImage imgSource, int size, Color color, float alpha) {
		GaussianFilter filter = new GaussianFilter(size);

		int imgWidth = imgSource.getWidth();
		int imgHeight = imgSource.getHeight();

		BufferedImage imgBlur = createCompatibleImage(imgWidth, imgHeight);
		Graphics2D g2 = imgBlur.createGraphics();
		applyQualityRenderingHints(g2);

		g2.drawImage(imgSource, 0, 0, null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, alpha));
		g2.setColor(color);

		g2.fillRect(0, 0, imgSource.getWidth(), imgSource.getHeight());
		g2.dispose();

		imgBlur = filter.filter(imgBlur, null);

		return imgBlur;
	}

	private static BufferedImage applyShadow(BufferedImage imgSource, int size, Color color, float alpha) {
		BufferedImage shadow = generateShadow(imgSource, size, color, alpha);
		Graphics g = shadow.getGraphics();
		g.drawImage(imgSource, size, size, null);

		return shadow;
	}

	private static BufferedImage generateShadow(BufferedImage imgSource, int size, Color color, float alpha) {
		int imgWidth = imgSource.getWidth() + (size * 2);
		int imgHeight = imgSource.getHeight() + (size * 2);

		BufferedImage imgMask = createCompatibleImage(imgWidth, imgHeight);
		Graphics2D g2 = imgMask.createGraphics();
		applyQualityRenderingHints(g2);
		g2.drawImage(imgSource, size, 0, null);
		g2.dispose();

		// ---- Blur here ---
		BufferedImage imgGlow = generateBlur(imgMask, (size * 2), color, alpha);
		imgGlow = resizeImage(imgGlow, 2, imgGlow.getWidth() + size * 2, imgGlow.getHeight() + size * 2);
		return imgGlow;
	}
}
