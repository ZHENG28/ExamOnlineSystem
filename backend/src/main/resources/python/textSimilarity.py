# -*- coding: utf-8 -*-
# noinspection PyInterpreter
from sys import argv
from gensim import corpora, models, similarities
import jieba
import re
from collections import defaultdict
import numpy


def deal_text(text):
    ppl = jieba.cut(text)
    # jieba object -> []
    return ' '.join(ppl).split(" ")


def get_similarity(material, compare_text, threshold):
    # 1 处理material
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
    for i, arr in enumerate(similarity_matrix):
        print(material[i])
        print(arr)

    # 6-1 处理待对比文本
    # new_doc = deal_text(compare_text)
    # new_vec = dictionary.doc2bow(new_doc)
    # 6-2 同material比较，获得文本相似度
    # similarity = similarity_matrix[lsi[new_vec]]
    # print(list(enumerate(similarity)))
    # for i in range(len(similarity)):
    #     if similarity[i] * 100 >= threshold:
    #         print(i, similarity[i] * 100)
    #         print(material[i])


if __name__ == "__main__":
    # sentence = (re.split(
    #     '[。；？！]',
    #     "反射星云，以天文学的观点，只是由尘埃组成，单纯的反射附近恒星或星团光线的云气。这些邻近的恒星没有足够的热让云气像发射星云那样因被电离而发光，但有足够的亮度可以让尘粒因散射光线而被看见。因此，反射星云显示出的频率光谱与照亮他的恒星相似。在星云中散射光线的是含碳的微粒（像是钻石尘粒）和其他成分的元素，特别是铁和镍，后二者经常会排列在星系磁场中，造成星光轻微的偏极化（Kaler，1998）。吸纳之，模型你超级大人口减少到了评委利物浦巨大的红色反射星云围绕着。反射星云通常也是恒星形成的场所。所以反射星云通常都是蓝色的。反射星云和发射星云常结合在一起成为弥漫星云，例如猎户座大星云。,反射星云，以天文学的观点，只是由尘埃组成，单纯的反射附近恒星或星团光线的云气。这些邻近的恒星没有足够的热让云气像发射星云那样因被电离而发光，但有足够的亮度可以让尘粒因散射光线而被看见。因此，反射星云显示出的频率光谱与照亮他的恒星相似。在星云中散射光线的是含碳的微粒（像是钻石尘粒）和其他成分的元素，特别是铁和镍，后二者经常会排列在星系磁场中，造成星光轻微的偏极化（Kaler，1998）。已知的反射星云大约有500个，其中最好看的就是围绕在昴宿星团周czcdaeqwgjf通常都是蓝色的。反射星云和发射星云常结合在一起成为弥漫星云，例如猎户座大星云。"
    # ))[:-1]
    # get_similarity(sentence,
    #                (re.split('[。；？！]',
    #                          "反射星云，以天文学的观点，只是由尘埃组成，单纯的反射附近恒星或星团光线的云气。"))[0], 0)

    # article = "反射星云，以天文学的观点，只是由尘埃组成，单纯的反射附近恒星或星团光线的云气。这些邻近的恒星没有足够的热让云气像发射jflsjdfoerwpierpxcl，但有足够的亮度可以让尘粒因散射光线而被看见。因此，反射星云显示出的频率光谱与照亮他的恒星相似。在星云中散射光线的是含碳的微粒（像是钻石尘粒）和其他成分的元素，特别是铁和镍，后二者经常会排列在星系磁场中，造成星光轻微的偏极化（Kaler，1998）。所以反射星云通常都是蓝色的。反射星云和发射星云常结合在一起成为弥漫星云，例如猎户座大星云。,反射星云，以天文学的观点，只是由尘埃组成，单纯的反射附近恒星或星团光线的云气。这些邻近的恒星没有足够的热让云气像发射，但有足够的亮度可以让尘粒因散射光线而被看见。因此，反射星云显示出的频率光谱与照亮他的恒星相似。在星云中散射光线的是含碳的微粒（像是钻石尘粒）和其他成分的元素，特别是铁和镍，后二者经常会排列在星系磁场中，造成星光轻微的偏极化（Kaler，1998）。反射星云和发射星云常结合在一起成为弥漫星云，例如猎户座大星云。,反射星云，以天文学的观点，只是由尘埃组成，单纯的反射附近恒星或星团光线的云气。这些邻近的恒星没有足够的热让云气像发射星云那样因被电离而发光，但有足够的亮度可以让尘粒因散射光线而被看见。因此，反射星云显示出的频率光谱与照亮他的恒星相似。在星云中散射光线的是含碳的微粒（像是钻石尘粒）和其他成分的元素，特别是铁和镍，后二者经常会排列在星系磁场中，造成星光轻微的偏极化（Kaler，1998）。吸纳之，模型你超级大人口减少到了评委利物浦巨大的红色反射星云围绕着。反射星云通常也是恒星形成的场所。所以反射星云通常都是蓝色的。反射星云和发射星云常结合在一起成为弥漫星云，例如猎户座大星云。".split(
    #     ",")
    # get_similarity(
    #     article,
    #     "反射星云，以天文学的观点，只是由尘埃组成，单纯的反射附近恒星或星团光线的云气。这些邻近的恒星没有足够的热让云气像发射星云那样因被电离而发光，但有足够的亮度可以让尘粒因散射光线而被看见。因此，反射星云显示出的频率光谱与照亮他的恒星相似。在星云中散射光线的是含碳的微粒（像是钻石尘粒）和其他成分的元素，特别是铁和镍，后二者经常会排列在星系磁场中，造成星光轻微的偏极化（Kaler，1998）。已知的反射星云大约有500个，其中最好看的就是围绕在昴宿星团周czcdaeqwgjf通常都是蓝色的。反射星云和发射星云常结合在一起成为弥漫星云，例如猎户座大星云。",
    #     0)

    # 1. 读取文档
    article = argv[1].split(argv[3])
    get_similarity(article, argv[2], 0)
