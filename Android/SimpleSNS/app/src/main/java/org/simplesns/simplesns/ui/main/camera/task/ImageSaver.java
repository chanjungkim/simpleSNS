package org.simplesns.simplesns.ui.main.camera.task;

import android.media.Image;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;

/**
 * Saves a JPEG {@link Image} into the specified {@link File}.
 */
public class ImageSaver implements Runnable {

    /**
     * The JPEG image
     */
    private final Image mImage;
    /**
     * The file we save the image into.
     */
    private final File mFile;

    public ImageSaver(Image image, File file) {
        mImage = image;
        mFile = file;
    }

    @Override
    public void run() {
        ByteBuffer buffer = mImage.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);

        // 코린이 - FileOutputStream implements autocloseable, try with resources supports more than min sdk 19
        try (FileOutputStream output = new FileOutputStream(mFile)) {
            output.write(bytes);
        } catch (Exception e) {
            Log.e("ImageSaver", e.getMessage());
        } finally {
            mImage.close();
        }
    }
}