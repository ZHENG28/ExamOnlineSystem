package com.zj.examsystem.utils;

import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Value;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class PythonExecute {
    public static void main(String[] args) {
        Process process;
        try {
            String filePath = new File("").getAbsolutePath() + "\\src\\main\\resources\\python\\textSimilarity.py";
            process = Runtime.getRuntime().exec(new String[]{"python", filePath, "Successful!"});
            // 中文乱码问题
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), "gb2312"));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
