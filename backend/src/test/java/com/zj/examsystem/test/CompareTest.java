package com.zj.examsystem.test;

import com.zj.examsystem.entity.CompareShortAnswer;
import com.zj.examsystem.service.CompareShortAnswerService;
import com.zj.examsystem.utils.PythonExecute;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class CompareTest extends BaseSpringBootTest {
    @Resource
    private CompareShortAnswerService compareShortAnswerService;

    @Test
    public void test() {
        CompareShortAnswer compareShortAnswer = compareShortAnswerService.findById(11, 50);
        // connect refuse
        for (int i = 1; i < 10; i++) {
            compareShortAnswerService.getCompareList(compareShortAnswer, i * 10);
        }
    }

    @Test
    public void test2() {
        List<String> list = new ArrayList<>();
        list.add(
                "我们在比较事物时，往往会用到“不同”，“一样”，“相似”等词语，这些词语背后都涉及到一个动作——双方的比较。只有通过比较才能得出结论，究竟是相同还是不同。但是万物真的有这么极端的区分吗。在我看来不是的，生活中通过“相似度”这词来描述可度”这词来描述可能会更加准确能会更加准确。比如男人和女人，虽然生理器官和可能思想有些不同同，但也有相同的地方，那就是都是人，就是说相似度不为0，但也有相同的地方，那就是都是人，就是说相似度不为0。头与小草，它们对于虚拟类都是一种实体类，相似度也不为0比如石头与小草，它们对于虚拟类都是一种实体类，相似度也不为0。两个致，相似度就是1句子词和词的顺序都一致，相似度就是1。一个概念可以应用到不同于相同的两个方到不同于相同的两个方面的定义面的定义。了可真谓方便至极了");
        list.add(
                "我们在比较事物时，往往会用到“不同”，“一样”，“相似”等词语，这些词语背后都涉及到一个动作——双方的比较。只有通过相同还是不同比较才能得出结论，究竟是相同还是不同。但是万物真的有这么极端的区分吗。在我看来不是的，生活中通过“相似度是的，生活中通过“相似度”这词来描述可能会更加准确”这词来描述可能会更加准确。比如男人和女人，虽然生理器官和可能思想有些不同，但也有相同的地方，那就是都是人，就是说相似度不为0。比如石头与小草，它们对于虚拟类都是一种实体类，相似度也类都是一种实体类，相似度也不为0不为0。两顺序都一致，相似度就是1个句子词和词的顺序都一致，相似度就是1。一个概念可以应用到不同于相同的两个方面的定义。可真谓方便至极了");
        list.add(
                "我们在比较事物时，往往会用到“不同”，“一样”，“相似”等词语，这些词语背后都涉及到一个动作——双方的比较。只有通过比较才能得出结论，究竟是相同还通过比较才能得出结论，究竟是相同还是不同是不同。但是万物真的有这但是万物真的有这么极端的区分吗么极端的区分吗。在我看来不是的，生活中通过“相似度”这词来描述可能会更加准确。比如男人和女人，虽然生理器官和可能思想有些不同，但也有相同的地方，那就是都是人，就是说相似度不为0。比如石头与小草，它们对于虚拟类都是一种实体类，相似度也不体类，相似度也不为0为0。两个句子词和词的顺序都一致，相似度就是1。一个概念可以应用到不同于相同的不同于相同的两个方面的定义两个方面的定义。可真谓方便至极了便至极了");
        PythonExecute.getArticleSimilarityWithSocket(list,
                "我们在比较事物时，往往会用到“不同”，“一样”，“相似”等词语，这些词语背后都涉及到一个动作——双方的比较。只有通过比较才能得出结论，是不是呢，谁不是呢。但是万物真的有这么按照阿哲？在我看来不是的，生活中通过“相似度”这词来描述可能会更加准确。比如男人和女人，虽然生理器官和可能思想有些不同，但也有相同的地方，相似度也不为0；受打击啊手机打开垃圾死了的绿卡十九日。一个概念可以应用到不同于相同的两个方面的定义。可真谓方便至极了。", 0);
    }
}
