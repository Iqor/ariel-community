/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Layout
 * and open the template in the editor.
 */
package com.igor.utils;

import net.sf.jmimemagic.*;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.regex.PatternSyntaxException;

public final class Utils {


    public static boolean IsNullOrEmpty(final String s) {
        return s == null || s.trim().isEmpty();
    }

    public static void showErrors(Exception ex) {

        System.err.println(ex.getCause());
        System.err.println(ex.getMessage());
        System.err.println(ex.getStackTrace());
    }


    public static File convertFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public static boolean allowedFileSize(File file){
        // Get length of file in bytes
        long fileSizeInBytes = file.length();
        // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
        long fileSizeInKB = fileSizeInBytes / 1024;
        // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        long fileSizeInMB = fileSizeInKB / 1024;

        if (fileSizeInMB > 5) {
            return false;
        }

        return true;
    }

    public static boolean allowedMimeType(File file) throws MagicParseException, MagicException, MagicMatchNotFoundException {

        Magic magic = new Magic();
        MagicMatch match = magic.getMagicMatch(file, false);

        if(match.getExtension().equals("png") || match.getExtension().equals("jpeg") || match.getExtension().equals("jpg"))
            return true;

        return false;
    }


    public static String generateFileHash() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        Random random = new SecureRandom();

        char[] text = new char[30];
        for (int i = 0; i < 30; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(text);
    }

    public static String specialCharacterRemover(String word) {

        String[] realName = word.split(".jpg");
        word = realName[0].replaceAll("[^a-zA-Z]", "");

        return word;
    }

    public static boolean validString(String username){

        try {
            if (username.matches("^[a-zA-Z0-9._-]{7,}$")) {
                return true;
            } else {
                return false;
            }
        } catch (PatternSyntaxException ex) {
            return false;
        }

    }


}
