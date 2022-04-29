package com.zj.examsystem.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static com.zj.examsystem.utils.Const.PYTHON_TEXT_DELIMITER;

public class PythonExecute {
    // test
    public static void main(String[] args) {
        Process process;
        try {
            String filePath = new File("").getAbsolutePath() + "\\src\\main\\resources\\python\\textSimilarity.py";
            process = Runtime.getRuntime().exec(new String[]{"python", filePath, "Successful!"});
            // 中文乱码问题
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), "gb2312"));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Object getTextSimilarity(List<String> oldList, String insert, Integer threshold) {
        String old = String.join(PYTHON_TEXT_DELIMITER, oldList);
        System.out.println(old);
        Process process;
        try {
            String filePath = new File("").getAbsolutePath() + "\\src\\main\\resources\\python\\textSimilarity.py";
            process = Runtime.getRuntime().exec(new String[]{"python", filePath, old, insert, PYTHON_TEXT_DELIMITER});
            // 中文乱码问题
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), "gb2312"));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
