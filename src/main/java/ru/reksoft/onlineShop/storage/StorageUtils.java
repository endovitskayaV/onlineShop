package ru.reksoft.onlineShop.storage;

public class StorageUtils {
    public static final String COMPRESSED_IMAGE_POSTfIX="_compressed";

    public static String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf('.') + 1, filename.length());
    }

    public static String getOriginalFileName(String filename) {
        return filename.substring(0, filename.lastIndexOf('.'));
    }

}
