package com.zj.examsystem.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zj.examsystem.utils.Const.*;

public class PythonExecute {
    private static final String IP = "localhost";
    private static final int PORT = 9091;

    public static List<String> getSocket(String[] params) {
        List<String> result = new ArrayList<>();
        Socket socket = null;
        try {
            socket = new Socket(IP, PORT);
            PrintStream printStream = new PrintStream(socket.getOutputStream());
            printStream.print(String.join(PYTHON_TEXT_DELIMITER, params));
            printStream.flush();

            BufferedReader inReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String info;
            while ((info = inReader.readLine()) != null) {
                System.out.println(info);
                result.add(info);
            }
            inReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    //    public static List<String> getArticleSimilarity(List<String> oldList, String insert, Integer threshold) {
    //        List<String> result = new ArrayList<>();
    //        String old = String.join(PYTHON_TEXT_DELIMITER, oldList);
    //        Process process;
    //        try {
    //            String filePath = new File("").getAbsolutePath() + "\\src\\main\\resources\\python\\textSimilarity.py";
    //            process = Runtime.getRuntime().exec(new String[]{"python", filePath, "article", old, insert,
    //                    String.valueOf(threshold)});
    //            // 中文乱码问题
    //            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), "gb2312"));
    //            String line;
    //            while ((line = in.readLine()) != null) {
    //                System.out.println(line);
    //                result.add(line);
    //            }
    //            in.close();
    //            process.waitFor();
    //        } catch (IOException | InterruptedException e) {
    //            e.printStackTrace();
    //        }
    //        return result;
    //    }
    //
    //    public static Map<String, int[]> getSentenceSimilarity(String compareText, String text, Integer threshold) {
    //        Map<String, int[]> result = new HashMap<>();
    //        Process process;
    //        try {
    //            String filePath = new File("").getAbsolutePath() + "\\src\\main\\resources\\python\\textSimilarity.py";
    //            process = Runtime.getRuntime().exec(new String[]{"python", filePath, "sentence", text, compareText, String.valueOf(threshold)});
    //            // 中文乱码问题
    //            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), "gb2312"));
    //            String line;
    //            int index = 0;
    //            while ((line = in.readLine()) != null) {
    //                String[] list = line.split("\\" + PYTHON_TEXT_DELIMITER);
    //                int[] res = new int[list.length];
    //                for (int i = 0; i < list.length; i++) {
    //                    res[i] = Integer.parseInt(list[i]);
    //                }
    //                result.put("reply" + ++index, res);
    //            }
    //            in.close();
    //            process.waitFor();
    //        } catch (IOException | InterruptedException e) {
    //            e.printStackTrace();
    //        }
    //        return result;
    //    }

    public static List<String> getArticleSimilarityWithSocket(List<String> oldList, String insert, Integer threshold) {
        String old = String.join(SHORT_ANSWER_QUESTION_LIST_SPLIT, oldList);
        return getSocket(new String[]{"article", old, insert, String.valueOf(threshold), PYTHON_SOCKET_END});
    }

    public static Map<String, int[]> getSentenceSimilarityWithSocket(String compareText, String text, Integer threshold) {
        Map<String, int[]> result = new HashMap<>();
        List<String> ret = getSocket(new String[]{"sentence", text, compareText, String.valueOf(threshold), PYTHON_SOCKET_END});
        int index = 0;
        for (String str : ret) {
            String[] list = str.split("\\" + PYTHON_TEXT_DELIMITER);
            int[] res = new int[list.length];
            for (int i = 0; i < list.length; i++) {
                res[i] = Integer.parseInt(list[i]);
            }
            result.put("reply" + (++index), res);
        }
        return result;
    }
}
