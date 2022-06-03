# -*- coding: utf-8 -*-
# noinspection PyInterpreter
from sys import argv
from gensim import corpora, models, similarities
import jieba
import re
from collections import defaultdict


def deal_sentence(sentence):
    return (re.split('。', sentence))[:-1]


def deal_text(text):
    ppl = jieba.cut(text)
    # jieba object -> []
    return ' '.join(ppl).split(" ")


def get_article_similarity(material, compare_text, threshold):
    # 1 处理material
    material.insert(0, compare_text)
    texts = [deal_text(t) for t in material]
    # 2-1 计算词频
    frequency = defaultdict(int)
    for text in texts:
        for token in text:
            frequency[token] += 1
    # 2-2 根据词频处理texts
    processed_texts = [[token for token in text if frequency[token] > 1]
                       for text in texts]
    # 3-1 依据语料库corpora，建立字典
    dictionary = corpora.Dictionary(processed_texts)
    # 3-2 转化成稀疏向量
    corpus = [dictionary.doc2bow(text) for text in processed_texts]
    # 4 建立Lsi模型（num_topics最好在[200, 500] - 官方说法）
    lsi = models.LsiModel(corpus, id2word=dictionary, num_topics=400)
    # 5 建立稀疏矩阵
    # 5-1 相似度：待对比文本与库中所有文本的
    # similarity_matrix = similarities.MatrixSimilarity(lsi[corpus])
    # 5-2 相似度：库中所有文本相互之间的对比，得到矩阵
    similarity_matrix = similarities.Similarity('index',
                                                lsi[corpus],
                                                num_features=lsi.num_topics)
    # 6 处理结果
    for i, arr in enumerate(similarity_matrix):
        if i == 0:
            for j in range(len(arr)):
                if j != 0 and arr[j] * 100 >= threshold:
                    print(j, arr[j] * 100)
                    print(material[j])


def get_sentence_similarity(sentence_text, compare_text, threshold):
    # 1 处理material
    material = sentence_text + compare_text
    texts = [deal_text(t) for t in material]
    # 2-1 计算词频
    frequency = defaultdict(int)
    for text in texts:
        for token in text:
            frequency[token] += 1
    # 2-2 根据词频处理texts
    processed_texts = [[token for token in text if frequency[token] > 1]
                       for text in texts]
    # 3-1 依据语料库corpora，建立字典
    dictionary = corpora.Dictionary(processed_texts)
    # 3-2 转化成稀疏向量
    corpus = [dictionary.doc2bow(text) for text in processed_texts]
    # 4 建立Lsi模型（num_topics最好在[200, 500] - 官方说法）
    lsi = models.LsiModel(corpus, id2word=dictionary, num_topics=400)
    # 5 建立稀疏矩阵
    # 5-1 相似度：待对比文本与库中所有文本的
    # similarity_matrix = similarities.MatrixSimilarity(lsi[corpus])
    # 5-2 相似度：库中所有文本相互之间的对比，得到矩阵
    similarity_matrix = similarities.Similarity('index',
                                                lsi[corpus],
                                                num_features=lsi.num_topics)
    # 6 处理结果
    result = []
    for i, arr in enumerate(similarity_matrix):
        if i >= len(sentence_text):
            print(material[i])
            for j in range(len(arr)):
                if j != i and j < len(
                        sentence_text) and arr[j] * 100 >= threshold:
                    if j not in result:
                        result.append(j)
    print(result)


if __name__ == "__main__":
    get_sentence_similarity(
        deal_sentence(
            "我们在比较事物时，往往会用到“不同”，“一样”，“相似”等词语，这些词语背后都涉及到一个动作——双方的比较。只有通过相同还是不同比较才能得出结论，究竟是相同还是不同。但是万物真的有这么极端的区分吗。在我看来不是的，生活中通过“相似度是的，生活中通过“相似度”这词来描述可能会更加准确”这词来描述可能会更加准确。比如男人和女人，虽然生理器官和可能思想有些不同，但也有相同的地方，那就是都是人，就是说相似度不为0。比如石头与小草，它们对于虚拟类都是一种实体类，相似度也类都是一种实体类，相似度也不为0不为0。两顺序都一致，相似度就是1个句子词和词的顺序都一致，相似度就是1。一个概念可以应用到不同于相同的两个方面的定义。可真谓方便至极了。"
        ),
        deal_sentence(
            "我们在比较事物时，往往会用到“不同”，“一样”，“相似”等词语，这些词语背后都涉及到一个动作——双方的比较。只有通过比较才能得出结论，究竟是相同还是不同。但是万物真的有这么极端的区分吗。在我看来不是的，生活中通过“相似度”这词来描述可能会更加准确。比如男人和女人，虽然生理器官和可能思想有些不同，但也有相同的地方理器官和可能思想有些不同，但也有相同的地方，那就是都是人，就是说相似度不为0，那就是都是人，就是说相似度不为0。比如石头与小草，它们对于虚都是一种实体类，相似度也不为0拟类都是一种实体类，相似度也不为0。两个句子词和词的顺序都一致，相似度就是1。一的两个方面的定义个概念可以应用到不同于相同的两个方面的定义。可真谓真谓方便至极了方便至极了。"
        ), 0)

    # article = "反射星云，以天文学的观点，这些邻近的恒星没有足够的热让云气像发射jflsjdfoerwpierpxcl，但有足够的亮度可以让尘粒因散射光线而被看见。因此，反射星云显示出的频率光谱与照亮他的恒星相似。在星云中散射光线的是含碳的微粒（像是钻石尘粒）和其他成分的元素，特别是铁和镍，后二者经常会排列在星系磁场中，造成星光轻微的偏极化（Kaler，1998）。，例如猎户座大星云。,反射星云，以天文学的观点，只是由尘埃组成，单纯的反射附近恒星或星团光线的云气。这些邻近的恒星没有足够的热让云气像发射，但有足够的亮度可以让尘粒因散射光线而被看见。因此，反射星云显示出的频率光谱与照亮他的恒星相似。在星云中散射光线的是含碳的微粒（像是钻石尘粒）和其他成分的元素，特别是铁和镍，后二者经常会排列在星系磁场中，造成星光轻微的偏极化（Kaler，1998）。反射星云和发射星云常结合在一起成为弥漫星云，例如猎户座大星云。,反射星云，以天文学的观点，只是由尘埃组成，单纯的反射附近恒星或星团光线的云气。这些邻近的恒星没有足够的热让云气像发射星云那样因被电离而发光，但有足够的亮度可以让尘粒因散射光线而被看见。因此，反射星云显示出的频率光谱与照亮他的恒星相似。在星云中散射光线的是含碳的微粒（像是钻石尘粒）和其他成分的元素，特别是铁和镍，后二者经常会排列在星系磁场中，造成星光轻微的偏极化（Kaler，1998）。吸纳之，模型你超级大人口减少到了评委利物浦巨大的红色反射星云围绕着。反射星云通常也是恒星形成的场所。所以反射星云通常都是蓝色的。反射星云和发射星云常结合在一起成为弥漫星云，例如猎户座大星云。".split(
    #     ",")
    # get_article_similarity(
    #     article,
    #     "反射星云，以天文学的观点，只是由尘埃组成，单纯的反射附近恒星或星团光线的云气。这些邻近的恒星没有足够的热让云气像发射星云那样因被电离而发光，但有足够的亮度可以让尘粒因散射光线而被看见。因此，反射星云显示出的频率光谱与照亮他的恒星相似。在星云中散射光线的是含碳的微粒（像是钻石尘粒）和其他成分的元素，特别是铁和镍，后二者经常会排列在星系磁场中，造成星光轻微的偏极化（Kaler，1998）。已知的反射星云大约有500个，其中最好看的就是围绕在昴宿星团周czcdaeqwgjf通常都是蓝色的。反射星云和发射星云常结合在一起成为弥漫星云，例如猎户座大星云。",
    #     97)
