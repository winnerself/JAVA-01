package com.geekbang.java;

import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.Base64;

public class TestClassLoader extends ClassLoader {

    public static void main(String[] args) {
        try {
            new TestClassLoader().findClass("Hello").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) {
       /* FileInputStream fis = null;
        try {
            File file = new File(TestClassLoader.class.getResource("/Hello.xlass").getPath());
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = 0;
        byte[] buffer = new byte[1024];
        try {
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = baos.toByteArray();
        String base64 = new String(bytes, Charset.forName("UTF-8"));*/
        File file = new File(TestClassLoader.class.getResource("/Hello.xlass").getPath());
        String base64 = null;
        try {
            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputStream.read(buffer);
            inputStream.close();
            base64 = new BASE64Encoder().encode(buffer).replace("\r\n", "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = Base64.getDecoder().decode(base64);
        byte decodeNum = (byte) 255;
        byte aByte;
        for (int i = 0; i < bytes.length; i++) {
            aByte = (byte) (decodeNum - bytes[i]);
            bytes[i] = aByte;
        }
        return defineClass(name, bytes, 0, bytes.length);
    }
}

