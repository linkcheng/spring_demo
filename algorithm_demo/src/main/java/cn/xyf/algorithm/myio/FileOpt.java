package cn.xyf.algorithm.myio;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class FileOpt {
    private String absPath;
    private File curFile;
    private String parentPath;

    public FileOpt(){}

    public FileOpt(String path) {
        curFile = new File(path);
        if (!curFile.isAbsolute()) {
            absPath = curFile.getAbsolutePath();
            System.out.println(absPath);
            curFile = new File(absPath);
            parentPath = curFile.getParent();
            System.out.println(parentPath);
        }
    }

    public void printSibling() {
        File parentFile = curFile.getParentFile();
        String[] list = parentFile.list();
        System.out.println(Arrays.toString(list));
    }

    public File createFile(String name) {
        File newFile = new File(parentPath+"/"+name);
        try {
            newFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFile;
    }

    public File makeDir(String subPath) {
        File newPath = new File(parentPath+"/"+subPath);
        newPath.mkdir();
        return newPath;
    }


    public static void main(String[] args) {
        FileOpt fileOpt = new FileOpt("./");
        fileOpt.printSibling();

        File test = fileOpt.makeDir("test");
        File hello = fileOpt.createFile("test/hello.txt");

        File old = new File("test/old.txt");
//        InputStreamReader
    }
}
