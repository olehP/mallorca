package com.mallorca;

public class TestMain {

	// private static final int IMG_WIDTH = 520;
	// private static final int IMG_HEIGHT = 270;
	// private static final double PERFECT_RATIO = ((double) IMG_WIDTH) / IMG_HEIGHT;
	//
	// public static void main(String[] args) throws IOException {
	// createImage("visit rock of gibraltar", "D:\\work\\projects\\mallorca\\rock.jpg", "rsult.png");
	// }
	//
	// private static void createImage(String text, String imageUrl, String resultImagePath) throws IOException {
	// if (text.length() > 40) {
	// throw new RuntimeException("Sorry, but text is too long(max 40 symbols are allowed)");
	// }
	// if (text.length() < 28) {
	// addOneLineText(text, imageUrl, resultImagePath);
	// } else {
	// String[] splited = text.split(" ");
	// StringBuilder firstLine = new StringBuilder(splited[0]);
	// StringBuilder secondLine = new StringBuilder("");
	// int index = 1;
	// while (index < splited.length && firstLine.length() + splited[index].length() < 27) {
	// firstLine.append(" ");
	// firstLine.append(splited[index]);
	// index++;
	// }
	// if (index < splited.length) {
	// secondLine.append(splited[index]);
	// index++;
	// }
	// while (index < splited.length) {
	// secondLine.append(" ");
	// secondLine.append(splited[index]);
	// index++;
	// }
	// if (firstLine.length() > 27 || secondLine.length() > 27) {
	// throw new RuntimeException("Sorry but text has wrong format, please try another");
	// }
	// addTwoLineText(firstLine.toString(), secondLine.toString(), imageUrl, resultImagePath);
	// }
	//
	// }
	//
	// private static void addTwoLineText(String firstLine, String secondLine, String imageUrl, String resultImagePath)
	// throws IOException {
	// BufferedImage first = addShadow(firstLine);
	// BufferedImage second = addShadow(secondLine);
	// File file = new File(imageUrl);
	// BufferedImage source = ImageIO.read(file);
	// int type = source.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : source.getType();
	// source = cropImageToStandartSize(source);
	// source = resizeImage(source, type, IMG_WIDTH, IMG_HEIGHT);
	// Graphics g = source.getGraphics();
	// g.drawImage(first, IMG_WIDTH / 2 - first.getWidth() / 2 + 7, IMG_HEIGHT / 2 - 55, null);
	// g.drawImage(second, IMG_WIDTH / 2 - second.getWidth() / 2 + 7, IMG_HEIGHT / 2 - 10, null);
	// ImageIO.write(source, "png", new File(resultImagePath));
	// }
	//
	// private static BufferedImage cropImageToStandartSize(BufferedImage image) {
	// double imageRatio = ((double) image.getWidth()) / image.getHeight();
	// if (Math.abs(imageRatio - PERFECT_RATIO) < 0.001) {
	// return image;
	// }
	// if (imageRatio < PERFECT_RATIO) {
	// return image.getSubimage(0, (image.getHeight() - (int) (image.getWidth() / PERFECT_RATIO)) / 2, image.getWidth(),
	// (int) (image.getWidth() / PERFECT_RATIO));
	// }
	//
	// return image.getSubimage((image.getWidth() - (int) (image.getHeight() * PERFECT_RATIO)) / 2, 0, (int)
	// (image.getHeight() * PERFECT_RATIO), image.getHeight());
	//
	// }
	//
	// private static void addOneLineText(String text, String imageUrl, String resultImagePath) throws IOException {
	// BufferedImage image = addShadow(text);
	// File file = new File(imageUrl);
	// BufferedImage source = ImageIO.read(file);
	// int type = source.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : source.getType();
	// source = cropImageToStandartSize(source);
	// source = resizeImage(source, type, IMG_WIDTH, IMG_HEIGHT);
	// Graphics g = source.getGraphics();
	// g.drawImage(image, source.getWidth() / 2 - image.getWidth() / 2 + 7, source.getHeight() / 2 - image.getHeight() /
	// 2, null);
	// ImageIO.write(source, "png", new File(resultImagePath));
	// }
	//
	// private static BufferedImage addShadow(String text) throws IOException {
	// BufferedImage image = convertTextToGraphic(text, new Font("Arial", Font.PLAIN, 45));
	// // write BufferedImage to file
	// image = applyShadow(image, 1, Color.BLACK, 1.0f);
	// image = applyShadow(image, 7, Color.BLACK, 1.0f);
	// return image;
	// }
	//
	// private static BufferedImage resizeImage(BufferedImage originalImage, int type, int width, int height) {
	// BufferedImage resizedImage = new BufferedImage(width, height, type);
	// Graphics2D g = resizedImage.createGraphics();
	// g.drawImage(originalImage, 0, 0, width, height, null);
	// g.dispose();
	//
	// return resizedImage;
	// }
	//
	// public static BufferedImage convertTextToGraphic(String text, Font font) {
	//
	// BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	// Graphics2D g2d = img.createGraphics();
	//
	// g2d.setFont(font);
	// FontMetrics fm = g2d.getFontMetrics();
	// int width = fm.stringWidth(text);
	// int height = fm.getHeight();
	// g2d.dispose();
	//
	// img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	//
	// g2d = img.createGraphics();
	// g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
	// g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	// g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
	// g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
	// g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
	// g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	// g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	// g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
	// g2d.setFont(font);
	// fm = g2d.getFontMetrics();
	// g2d.setColor(Color.WHITE);
	// g2d.drawString(text, 0, fm.getAscent());
	// g2d.dispose();
	// return img;
	// }
	//
	// public static void applyQualityRenderingHints(Graphics2D g2d) {
	// g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
	// g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	// g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
	// g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
	// g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
	// g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	// g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	// g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
	// }
	//
	// public static BufferedImage createCompatibleImage(int width, int height) {
	// return createCompatibleImage(width, height, Transparency.TRANSLUCENT);
	// }
	//
	// public static BufferedImage createCompatibleImage(int width, int height, int transparency) {
	// BufferedImage image = new BufferedImage(width, height, 2);
	// // getGraphicsConfiguration().createCompatibleImage(width, height, transparency);
	// image.coerceData(true);
	// return image;
	// }
	//
	// public static BufferedImage createCompatibleImage(BufferedImage image) {
	// return createCompatibleImage(image, image.getWidth(), image.getHeight());
	// }
	//
	// public static BufferedImage createCompatibleImage(BufferedImage image, int width, int height) {
	// return getGraphicsConfiguration().createCompatibleImage(width, height, image.getTransparency());
	// }
	//
	// public static GraphicsConfiguration getGraphicsConfiguration() {
	// return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	// }
	//
	// public static BufferedImage generateMask(BufferedImage imgSource, Color color, float alpha) {
	// int imgWidth = imgSource.getWidth();
	// int imgHeight = imgSource.getHeight();
	//
	// BufferedImage imgBlur = createCompatibleImage(imgWidth, imgHeight);
	// Graphics2D g2 = imgBlur.createGraphics();
	// applyQualityRenderingHints(g2);
	//
	// g2.drawImage(imgSource, 0, 0, null);
	// g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, alpha));
	// g2.setColor(color);
	//
	// g2.fillRect(0, 0, imgSource.getWidth(), imgSource.getHeight());
	// g2.dispose();
	//
	// return imgBlur;
	// }
	//
	// public static BufferedImage generateBlur(BufferedImage imgSource, int size, Color color, float alpha) {
	// GaussianFilter filter = new GaussianFilter(size);
	//
	// int imgWidth = imgSource.getWidth();
	// int imgHeight = imgSource.getHeight();
	//
	// BufferedImage imgBlur = createCompatibleImage(imgWidth, imgHeight);
	// Graphics2D g2 = imgBlur.createGraphics();
	// applyQualityRenderingHints(g2);
	//
	// g2.drawImage(imgSource, 0, 0, null);
	// g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, alpha));
	// g2.setColor(color);
	//
	// g2.fillRect(0, 0, imgSource.getWidth(), imgSource.getHeight());
	// g2.dispose();
	//
	// imgBlur = filter.filter(imgBlur, null);
	//
	// return imgBlur;
	// }
	//
	// public static BufferedImage applyShadow(BufferedImage imgSource, int size, Color color, float alpha) throws
	// IOException {
	// BufferedImage shadow = generateShadow(imgSource, size, color, alpha);
	// Graphics g = shadow.getGraphics();
	// g.drawImage(imgSource, size, size, null);
	//
	// return shadow;
	// }
	//
	// public static BufferedImage generateShadow(BufferedImage imgSource, int size, Color color, float alpha) {
	// int imgWidth = imgSource.getWidth() + (size * 2);
	// int imgHeight = imgSource.getHeight() + (size * 2);
	//
	// BufferedImage imgMask = createCompatibleImage(imgWidth, imgHeight);
	// Graphics2D g2 = imgMask.createGraphics();
	// applyQualityRenderingHints(g2);
	// g2.drawImage(imgSource, size, 0, null);
	// g2.dispose();
	//
	// // ---- Blur here ---
	// BufferedImage imgGlow = generateBlur(imgMask, (size * 2), color, alpha);
	// imgGlow = resizeImage(imgGlow, 2, imgGlow.getWidth() + size * 2, imgGlow.getHeight() + size * 2);
	// return imgGlow;
	// }
	//
	// public static Image applyMask(BufferedImage sourceImage, BufferedImage maskImage) {
	// return applyMask(sourceImage, maskImage, AlphaComposite.DST_IN);
	// }
	//
	// public static BufferedImage applyMask(BufferedImage sourceImage, BufferedImage maskImage, int method) {
	// BufferedImage maskedImage = null;
	// if (sourceImage != null) {
	//
	// int width = maskImage.getWidth(null);
	// int height = maskImage.getHeight(null);
	//
	// maskedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	// Graphics2D mg = maskedImage.createGraphics();
	//
	// int x = (width - sourceImage.getWidth(null)) / 2;
	// int y = (height - sourceImage.getHeight(null)) / 2;
	//
	// mg.drawImage(sourceImage, x, y, null);
	// mg.setComposite(AlphaComposite.getInstance(method));
	//
	// mg.drawImage(maskImage, 0, 0, null);
	//
	// mg.dispose();
	// }
	// return maskedImage;
	// }
}
