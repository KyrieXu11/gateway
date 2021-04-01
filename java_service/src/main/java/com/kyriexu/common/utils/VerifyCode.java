package com.kyriexu.common.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Random;

/**
 * @author KyrieXu
 * @since 2020/3/29 20:48
 **/
public class VerifyCode {
    // 定义长宽
    private final int width = 100;
    private final int height = 30;
    // 背景颜色为白色
    private final Color bgColor = new Color(255, 255, 255);
    // 字体的名称
    private final String[] fontFamily = new String[]{"Arial"};
    // 验证码的字符库
    private final String codes = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    // 设置生成的字符数量
    private final int num = 4;
    private final String formatName = "png";
    // 验证码
    private String text;
    // 定义一个随机数,不设置随机种子
    private final Random random = new Random();

    public static void output(VerifyCode code, OutputStream out) throws IOException {
        BufferedImage image = code.getImage();
        OutputStream newOut = new BufferedOutputStream(out);
        ImageIO.write(image, code.formatName, newOut);
    }

    /**
     * 设置RGB三个通道的颜色
     *
     * @return 字体的颜色
     */
    private Color getColor() {
        // 必须要让验证码可以用肉眼看见，所以十进制的值是在[0,149]之间
        int red = random.nextInt(150);
        int greed = random.nextInt(150);
        int blue = random.nextInt(150);
        return new Color(red, greed, blue);
    }

    /**
     * 获取验证码的字体
     *
     * @return 验证码的字体
     */
    private Font getFont() {
        String fontName = fontFamily[random.nextInt(fontFamily.length)];
        // 粗体、斜体等样式
        int style = random.nextInt(3);
        // 字体的尺寸
        int size = random.nextInt(4) + 25;
        return new Font(fontName, style, size);
    }

    /**
     * 随机获取字符
     *
     * @return 随机的字符
     */
    private char getChar() {
        return codes.charAt(random.nextInt(codes.length()));
    }

    /**
     * 创建背景板
     *
     * @return 白色的背景板
     */
    private BufferedImage createImage() {
        // 创建画像
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 创建画笔
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        // 设置背景颜色
        g2.setColor(bgColor);
        // 填充背景
        g2.fillRect(0, 0, width, height);
        return image;
    }

    public BufferedImage getImage() {
        // 获取背景板
        BufferedImage image = createImage();
        // 获取画笔
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++) {
            String s = getChar() + "";
            sb.append(s);
            g2.setColor(getColor());
            g2.setFont(getFont());
            // 画字符的位置
            float x = i * width * 1.0f / num;
            g2.drawString(s, x, height - 8);
        }
        this.text = sb.toString();
        drawLine(image);
        return image;
    }

    /**
     * 画干扰线
     *
     * @param image 图像
     */
    private void drawLine(Image image) {
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        int num = 5;
        for (int i = 0; i < num; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            g2.setColor(getColor());
            // // 画线
            // g2.setStroke(new BasicStroke(1.5f));
            g2.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * 获取验证码
     *
     * @return 生成的验证码
     */
    public String getText() {
        return text;
    }

    public String getBase64() throws IOException {
        BufferedImage image = this.getImage();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(image, formatName, stream);
        return Base64.getEncoder().encodeToString(stream.toByteArray());
    }
}
