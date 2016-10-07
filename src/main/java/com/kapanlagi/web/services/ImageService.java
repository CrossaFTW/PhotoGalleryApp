package com.kapanlagi.web.services;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Crossa on 10/3/2016.
 */
@Component
public class ImageService {
    /**
     * Resize image to given width / height, put 0 to make it proportional
     * @param source source image to be resized
     * @param width desired width, 0 for proportional
     * @param height desired height, 0 for proportional
     * @return java.awt.Image
     */

    public BufferedImage resizeImage(BufferedImage source, int width, int height) {
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();
        int imageType = (source.getTransparency() == Transparency.OPAQUE)
                ? BufferedImage.TYPE_INT_RGB
                : BufferedImage.TYPE_INT_ARGB;

        double xRatio = width > 0 ? (double)width / (double)sourceWidth : 0;
        double yRatio = height > 0 ? (double)height / (double)sourceHeight : 0;

        //pick the largest
        double ratio = width > 0 && height > 0
                ? Math.max( xRatio, yRatio )
                : ( width == 0 ? yRatio : xRatio );

        if (width == 0)
            width = (int)(ratio * (double) sourceWidth);

        if (height == 0)
            height = (int)(ratio * (double) sourceHeight);

        double destWidth = ratio * (double)sourceWidth;
        double destHeight = ratio * (double)sourceHeight;
        double x = destWidth > width ? (destWidth-width)/2 : 0;
        double y = destHeight > height ? (destHeight-height)/2 : 0;

        BufferedImage cropImage = new BufferedImage( (int)destWidth, (int)destHeight, imageType );
        Graphics2D g = cropImage.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage( source, 0, 0, (int)destWidth, (int)destHeight, null  );
        g.dispose();

        BufferedImage destImage = cropImage.getSubimage((int)x, (int)y, width, height);
        return destImage;
    }
}
