package com.rutgers.androidchess23;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import controller.GuiGame;


/**
 * Base class for all Activities
 *
 * @author William Chen
 * @author Chijun Sha
 */
public class ActivityBase extends AppCompatActivity {
    public static final String INTENT_DATA_KEY_FILENAME     = "FileName";
    public static final String INTENT_DATA_FILENAME_LAST    = "LastGame";
    //
    private static final String GAME_FILE_EXT = ".cgcs23";
    public static final String TMS_FORMAT = "yyyyMMddHHmmss";


    public static GuiGame guiGame;


    ActivityBase() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public static String serializeAddress(File parentDir, String fileName, Object obj) {
        String filePath = makeFileName(parentDir, fileName);
        String out = "0";
        //
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;
        //
        try {
            fout = new FileOutputStream(filePath);
            out = "1";
            oos = new ObjectOutputStream(fout);
            out = "2";
            oos.writeObject(obj);
            out = "3";
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //
        return out;
    }


    public static Object readObjectFromFile(File parentDir, String fileName) {
        String filePath = parentDir.getPath() + File.separator + fileName;
        Object obj = null;
        //
        FileInputStream fileIn = null;
        ObjectInputStream in = null;
        try {
            fileIn = new FileInputStream(filePath);
            in = new ObjectInputStream(fileIn);
            obj = in.readObject();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileIn != null) {
                try {
                    fileIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //
        return obj;
    }


    public static boolean deleteFile(File parentDir, String fileName) {
        String filePath = parentDir.getPath() + File.separator + fileName;
        //
        File file = new File(filePath);
        //
        return file.delete();
    }


    public static List<String> getListFilesName(File parentDir) {
        List<String> inFiles = new ArrayList<>();
        //
        Queue<File> files = new LinkedList<>();
        //
        files.addAll(Arrays.asList(parentDir.listFiles()));
        while (!files.isEmpty()) {
            File file = files.remove();
            if (file.isDirectory()) {
                files.addAll(Arrays.asList(file.listFiles()));
            }
            else if (file.getName().endsWith(GAME_FILE_EXT)) {
                inFiles.add(file.getName());
            }
        }
        return inFiles;
    }

    public static List<File> getListFiles2(File parentDir) {
        List<File> inFiles = new ArrayList<>();
        //
        Queue<File> files = new LinkedList<>();
        //
        files.addAll(Arrays.asList(parentDir.listFiles()));
        while (!files.isEmpty()) {
            File file = files.remove();
            if (file.isDirectory()) {
                files.addAll(Arrays.asList(file.listFiles()));
            }
            else if (file.getName().endsWith(GAME_FILE_EXT)) {
                inFiles.add(file);
            }
        }
        return inFiles;
    }

    public static String makeFileName(File parentDir, String input) {
        input = input.trim();
        input = input.replaceAll("[^a-zA-Z0-9-]", "_");
        if (input.length()>48) {
            input = input.substring(0,48);
        }
        //
        SimpleDateFormat dateFormat = new SimpleDateFormat(TMS_FORMAT);  //"dd-MM-yyyy HH:mm:ss"
        String tms  = dateFormat.format(new Date());
        //
        return parentDir.getPath() + File.separator + tms + input + GAME_FILE_EXT;
    }


    public static String getTitleFromFileName(String input) {
        String szTemp = input.substring(0, input.length()-GAME_FILE_EXT.length());
        return szTemp.substring(TMS_FORMAT.length());
    }

    public static String getTMSFromFileName(String input) {
        //"yyyyMMddHHmmss"
        return  input.substring(0,  4) + "-" +
                input.substring(4,  6) + "-" +
                input.substring(6,  8) + " " +
                input.substring(8,  10) + ":" +
                input.substring(10, 12) + ":" +
                input.substring(12, 14);
    }
}
