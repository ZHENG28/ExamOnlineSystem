package com.zj.examsystem.utils;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * 随机生成汉字或随机生成中文姓名
 */
public abstract class AutoNameUtil {
    private final static String[] CHINESE_SURNAME = {
            "李", "王", "张", "刘", "陈",
            "杨", "赵", "黄", "周", "吴",
            "徐", "孙", "胡", "朱", "高",
            "林", "何", "郭", "马", "罗",
            "梁", "宋", "郑", "谢", "韩",
            "唐", "冯", "于", "董", "萧",
            "程", "曹", "袁", "邓", "许",
            "傅", "沈", "曾", "彭", "吕",
            "苏", "卢", "蒋", "蔡", "贾",
            "丁", "魏", "薛", "叶", "阎",
            "余", "潘", "杜", "戴", "夏",
            "钟", "汪", "田", "任", "姜",
            "范", "方", "石", "姚", "谭",
            "廖", "邹", "熊", "金", "陆",
            "郝", "孔", "白", "崔", "康",
            "毛", "邱", "秦", "江", "史",
            "顾", "侯", "邵", "孟", "龙",
            "万", "段", "漕", "钱", "汤",
            "尹", "黎", "易", "常", "武",
            "乔", "贺", "赖", "龚", "文",
    };

    private final static String[] LETTER = {
            "a", "b", "c", "d", "e",
            "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o",
            "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z",
    };

    /**
     * 随机获取姓
     */
    public static String surname() {
        return CHINESE_SURNAME[new Random().nextInt(CHINESE_SURNAME.length - 1)];
    }

    /**
     * 获取N位常用字
     */
    public static String name(int len) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < len; i++) {
            String str = null;
            // 定义高低位
            int highPos, lowPos;
            Random random = new Random();
            //获取高位值
            highPos = (176 + Math.abs(random.nextInt(39)));
            //获取低位值
            lowPos = (161 + Math.abs(random.nextInt(93)));
            byte[] b = new byte[2];
            b[0] = (Integer.valueOf(highPos).byteValue());
            b[1] = (Integer.valueOf(lowPos).byteValue());
            //转成中文
            try {
                str = new String(b, "GBK");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            ret.append(str);
        }
        return ret.toString();
    }

    /**
     * 随机获取中文姓名
     */
    public static String getChineseName() {
        return surname() + name(2);
    }

    /**
     * 随机获取英文姓名
     */
    public static String getEnglishName() {
        StringBuilder ret = new StringBuilder();
        int length = new Random().nextInt(20 - 3) + 3;
        for (int i = 0; i < length; i++) {
            ret.append(LETTER[new Random().nextInt(LETTER.length - 1)]);
        }
        return ret.toString();
    }
}

