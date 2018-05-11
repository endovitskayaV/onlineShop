package ru.reksoft.onlineShop.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.reksoft.onlineShop.storage.StorageException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;

import static ru.reksoft.onlineShop.storage.StorageUtils.*;

@Service
public class StorageService {
    private static final String ROOT_LOCATION = "src/main/resources/static/img";

    public String store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        Path path = Paths.get(ROOT_LOCATION).resolve(Long.toString(System.currentTimeMillis()) + '.' + getFileExtension(filename));
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return path.getFileName().toString();

        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }


    public String getCompressedImage(String originalImageName) {
        String originalImageExtension = getFileExtension(originalImageName);
        String compressedImageName = getOriginalFileName(originalImageName) + COMPRESSED_IMAGE_POSTfIX+"." + originalImageExtension;
        File originalImageFile = new File(ROOT_LOCATION + "/" + originalImageName);
        boolean isExtensionPng = originalImageExtension.equals("png");

        try {
            if (isExtensionPng) { //originalImage.png to originalImage.jpg
                originalImageExtension = "jpg";
                compressedImageName = originalImageName.substring(0, originalImageName.lastIndexOf('.')) + "_small." + originalImageExtension;

                BufferedImage oldImage = ImageIO.read(new File(ROOT_LOCATION + "/" + originalImageName));
                BufferedImage newImage = new BufferedImage(oldImage.getWidth(), oldImage.getHeight(), BufferedImage.TYPE_INT_RGB);

                //png to jpg with white background
                newImage.createGraphics().drawImage(oldImage, 0, 0, Color.WHITE, null);

                //save new jpg file
                originalImageName = getOriginalFileName(originalImageName) + ".jpg";
                originalImageFile = new File(ROOT_LOCATION + "/" + originalImageName);
                ImageIO.write(newImage, "jpg", originalImageFile);
            }

            //create output stream
            OutputStream fileOutputStreamCompressedImage = new FileOutputStream(new File(ROOT_LOCATION + "/" + compressedImageName));
            ImageOutputStream compressedImageOutputStream = ImageIO.createImageOutputStream(fileOutputStreamCompressedImage);

            //create image writer based on original file extension
            Iterator<ImageWriter> imageWriterIter = ImageIO.getImageWritersByFormatName(originalImageExtension);
            ImageWriter imageWriter = imageWriterIter.next();
            imageWriter.setOutput(compressedImageOutputStream);

            //set params for image writer
            ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
            imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            imageWriteParam.setCompressionQuality(0.5f);

            BufferedImage originalImage = ImageIO.read(originalImageFile);
            imageWriter.write(null, new IIOImage(originalImage, null, null), imageWriteParam);

            fileOutputStreamCompressedImage.close();
            compressedImageOutputStream.close();
            imageWriter.dispose();

            if (isExtensionPng) {
                //delete originalImage.jpg if originalImage.png was loaded
                originalImageFile.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return compressedImageName;
    }
}
