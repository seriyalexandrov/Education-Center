package ru.ncedu.grishkova.snake;

import java.awt.image.RGBImageFilter;

public class TransparentImageFilter extends RGBImageFilter {
    public int filterRGB(int x, int y, int nRGB) {
        int nAlpha = 0x80;
        return((nAlpha << 24) | (nRGB & 0xffffff));
    }
}