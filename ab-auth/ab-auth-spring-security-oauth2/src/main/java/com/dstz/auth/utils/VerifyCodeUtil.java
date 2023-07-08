package com.dstz.auth.utils;

import cn.hutool.core.util.StrUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 验证码工具类
 *
 * @author lightning
 * @since 2022-02-07
 */
public final class VerifyCodeUtil {
    /*定义图片的width*/
    private static int WIDTH = 80;
    /*定义图片的height*/
    private static int HEIGHT = 30;
    /*定义图片上显示的验证码个数*/
    private static int CODECOUNT = 4;
    /*字符间隔*/
    private static final int CHARACETRSPACING = 8;
    /*字体大小*/
    private static final int FONTHEIGHT = 24;
    /*干扰线*/
    private static final int LINENUMBER = 10;
    /*字符垂直位置*/
    private static final int VERTICALPOSITION = 24;
    private static final char[] CODESEQUENCE = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O',
            'P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n',
            'o','p','q','r','s','t','u','v','w','x','y','z','1','2','3','4','5','6','7','8','9','0'};
    /*无参构造器*/
    private VerifyCodeUtil(){}

    /**
     * 生成验证码字符串
     * @param count 验证码字符个数
     * @return 返回验证码字符串
     */
    public static String generateVerifyCode(int count){
        //产生随机数
        Random random = ThreadLocalRandom.current();
        //生成验证码
        StringBuilder randomCode = new StringBuilder();/*StringBuilder可变字符串序列*/
        for (int i = 0; i < count; i++) {
            //获取一位验证码
            String code = String.valueOf(CODESEQUENCE[random.nextInt(CODESEQUENCE.length)]);
            randomCode.append(code);/*字符串拼接*/
        }
        return randomCode.toString();
    }

    /**
     * 生成验证码字符串
     * @return 返回验证码字符串
     */
    public static String generateVerifyCode(){//方法的重载
        return generateVerifyCode(CODECOUNT);//静态方法中只能调用静态方法
    }

    /**
     * 生成验证码图片
     * @param width 验证码图片的宽度，默认95
     * @param  height 验证码图片的高度，默认30
     * @param code 验证码字符串
     * @return 返回创建好的画布对象
     */
    public static BufferedImage outputImage(Integer width, Integer height, String code){
        if(!StrUtil.isEmpty(String.valueOf(width))){//Spring工具包中的方法
            WIDTH = width;//为图片设置宽度
        }
        if(!StrUtil.isEmpty(String.valueOf(height))){
            HEIGHT = height;//为图片设置高度
        }
        if(StrUtil.isEmpty(code)){
            return null;//做这些判断都是为了防止出现空指针异常
        }
        //创建画布对象(不带透明色)
        BufferedImage buffImg = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        //创建画笔对象
        Graphics gd = buffImg.getGraphics();
        //创建生成随机数对象
        Random random = ThreadLocalRandom.current();
        //设置画布背景色(白色)
        gd.setColor(Color.WHITE);
        //根据设置的背景色填充画布
        gd.fillRect(0, 0, WIDTH, HEIGHT);
        //创建字体对象
        Font font = new Font("Arial", Font.BOLD, FONTHEIGHT);
        //给画笔对象设置字体
        gd.setFont(font);
        //给画笔对象设置颜色
        gd.setColor(Color.BLACK);
        //给画布绘制边框
        gd.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);
        //生成验证码
        char[] codes = code.toCharArray();
        for (int i = 0; i < codes.length; i++) {
            //设置颜色
            gd.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            //绘制验证码
            gd.drawString(String.valueOf(codes[i]), CHARACETRSPACING + i * 2 * CHARACETRSPACING, VERTICALPOSITION);
        }
        //设置干扰线颜色(深灰色)
        gd.setColor(Color.DARK_GRAY);
        //干扰线
        for (int i = 0; i < LINENUMBER; i++) {
            //线的起始X坐标
            int startX = random.nextInt(WIDTH);
            //线的起始Y坐标
            int startY = random.nextInt(HEIGHT);
            //线的结束X坐标
            int endX = random.nextInt(VERTICALPOSITION);
            //线的结束Y坐标
            int endY = random.nextInt(VERTICALPOSITION);
            //绘制直线
            gd.drawLine(startX, startY, startX + endX, startY + endY);
        }
        return buffImg;
    }
    /**
     * 输出验证码图片流
     * @param w 宽度
     * @param h 高度
     * @param code 验证码字符串
     * @param os 输出流
     */
    public static void outputImage(Integer w, Integer h, String code, OutputStream os) throws IOException {
        if(StrUtil.isEmpty(code)){
            return;
        }
        // 创建画布对象
        BufferedImage bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        // 创建画笔对象
        Graphics gd = bi.getGraphics();
        // 创建生成随机数对象
        Random random = ThreadLocalRandom.current();
        // 设置画布背景色
        gd.setColor(Color.DARK_GRAY);
        // 根据设置的背景色填充画布
        gd.fillRect(0, 0, WIDTH, HEIGHT);
        // 创建字体对象
        Font font = new Font("Arial", Font.BOLD, FONTHEIGHT);
        // 给画笔对象设置字体
        gd.setFont(font);
        // 给画笔设置颜色
        gd.setColor(Color.BLACK);
        // 给画布绘制边框
        //gd.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);
        // 设置干扰线颜色
        gd.setColor(Color.BLACK);
        // 干扰线
        for (int i = 0; i < LINENUMBER; i++) {
            // 线的起始X坐标
            int startX = random.nextInt(WIDTH);
            // 线的起始Y坐标
            int startY = random.nextInt(HEIGHT);
            // 线的结束X坐标
            int endX = random.nextInt(VERTICALPOSITION);
            // 线的结束Y坐标
            int endY = random.nextInt(VERTICALPOSITION);
            // 绘制直线
            gd.drawLine(startX, startY, startX + endX, startY + endY);
        }
        // 生成验证码
        char[] codes = code.toCharArray();
        for (int i = 0; i < codes.length; i++) {
            // 设置颜色
            gd.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            // 绘制验证码
            gd.drawString(String.valueOf(codes[i]), CHARACETRSPACING + i * 2 * CHARACETRSPACING, VERTICALPOSITION);
        }
        gd.dispose();
        ImageIO.write(bi, "png", os);
    }
    /**
     * 输出验证码图片流
     * @param code 验证码
     * @param os 创建的输出流
     */
    public static void outputImage(String code, OutputStream os) throws IOException {
        outputImage(WIDTH, HEIGHT, code, os);
    }



}
