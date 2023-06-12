package com.ecommerce.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;

public class FileUtils {
  private static final String ROOT_PATH = System.getProperty("user.dir");

  public static void storeObject(Object obj, String fileName) {
    File file = Paths.get(ROOT_PATH, fileName).toFile();
    ObjectOutputStream oos = null;

    try {
      oos = new ObjectOutputStream(new FileOutputStream(file));
      oos.writeObject(obj);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (oos != null) {
          oos.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static Object readObject(String fileName) {
    File file = Paths.get(ROOT_PATH, fileName).toFile();
    Object object = null;
    ObjectInputStream ois = null;

    try {
      ois = new ObjectInputStream(new FileInputStream(file));
      object = ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      try {
        if (ois != null) {
          ois.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return object;
  }
}
