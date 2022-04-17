# -*- coding: utf-8 -*-
# noinspection PyInterpreter
from sys import argv
from gensim import corpora, models, similarities
import jieba
import re
from collections import defaultdict


def deal_text(text):
    # 2. 分词（jieba）
    ppl = jieba.cut(text)
    # 3. 整理成指定格式
    return ' '.join(ppl).split(" ")


def get_similarity(material, compare_text, threshold):
    texts = [deal_text(t) for t in material]
    # 4. 计算词频TF-IDF
    frequency = defaultdict(int)
    for text in texts:
        for word in text:
            # 计数词频
            frequency[word] += 1
    # 5. 依据语料库，建立词典
    dictionary = corpora.Dictionary(texts)

    # 6. 将待对比文档转化成稀疏向量（doc2bow）
    new_doc = deal_text(compare_text)
    new_vec = dictionary.doc2bow(new_doc)
    corpus = [dictionary.doc2bow(text) for text in texts]
    tfidf = models.TfidfModel(corpus)
    # 7. 将词频转发为特征数（token2id）
    features = len(dictionary.token2id.keys())
    # 8. 稀疏矩阵相似度
    similarity_matrix = similarities.SparseMatrixSimilarity(
        tfidf[corpus], num_features=features)
    similarity = similarity_matrix[tfidf[new_vec]]
    for sim in similarity:
        if sim * 100 >= threshold:
            i, = numpy.where(similarity == sim)
            print(i[0], sim * 100)
            print(material[i[0]])


if __name__ == "__main__":
    # printValue(argv[1])

    # 1. 读取文档
    text1 = "语料库指经科学取样和加工的大规模电子文本库，其中存放的是在语言的实际使用中真实出现过的语言材料。语料库是语料库语言学研究的基础资源，也是经验主义语言研究方法的主要资源。应用于词典编纂，语言教学，传统语言研究，自然语言处理中基于统计或实例的研究等方面。指经科学取样和加工的大规模电子文本库。借助计算机分析工具，研究者可开展相关的语言理论及应用研究。语料库有多种类型，确定类型的主要依据是它的研究目的和用途，这一点往往能够在语料采集的原则和方式上有所体现。有人曾经把语料库分成四种类型：(1)异质的（Heterogeneous）：没有特定的语料收集原则，广泛收集并原样存储各种语料；(2)同质的（Homogeneous）：只收集同一类内容的语料；(3)系统的（Systematic）：根据预先确定的原则和比例收集语料，使语料具有平衡性和系统性，能够代表某一范围内的语言事实；(4)专用的（Specialized）：只收集用于某一特定用途的语料。除此之外，按照语料的语种，语料库也可以分成单语的（Monolingual）、双语的（Bilingual）和多语的（Multilingual）。按照语料的采集单位，语料库又可以分为语篇的、语句的、短语的。双语和多语语料库按照语料的组织形式，还可以分为平行（对齐）语料库和比较语料库，前者的语料构成译文关系，多用于机器翻译、双语词典编撰等应用领域，后者将表述同样内容的不同语言文本收集到一起，多用于语言对比研究。已经累积了大量各种类型的语料库，如：葡萄牙语料库、面向文本分类研究的中英文新闻分类语料库、路透社文本分类训练语料库、中文文本分类语料库、大开放字幕库OpenSubtitles的多语言平行语料数据（OpenSubtitles Corpus）、《圣经》双语语料库（Bible bilingual corpus）、Short messages service(SMS) corpus（短消息服务（SMS）语料）等。"
    text2 = "大致可分为三类：一是研究双语语料的对齐技术（Alignment），国内外学者就此提出多种策略和方法，已经出现了许多对齐双语或多语语料的程序或工具[Gale 1993]；二是研究双语语料的各种应用，如在基于统计的机器翻译技术[Brown 1990]、基于实例的机器翻译技术[Nagao 1984]，双语词典编纂[Klavans and Tzoukermann 1990]技术中，双语语料库都发挥着十分重要的作用；三是双语语料库的设计、采集、编码和管理问题。比较著名的语料库编码方案有TEI 文本编码标准以及CES标准，两者均基于SGML标记语言研究指不只有一种语言的语料库。分为平行语料库和对照语料库两种。平行语料库指库中的两种或多种文本互相是对方的译文，因此可以用于翻译或者机器翻译研究；对照语料库中两种或多种语言的文本不构成对译关系，只是领域相同，主题相近。通常只能用于两种或多种语言的对比。就前两类研究来说，中国国内做了较多的跟踪研究工作，而对于第三类研究，即双语语料库尤其是涉及汉语的双语语料库的建设、编码和管理研究，探索工作似乎做的相对较少。目前国内最大的语料交换平台是Tmxmall语料商城。"
    article = [text1, text2]
    sentence = (re.split('[。；？！]', text1))[:-1]

    get_similarity(sentence, "语料库指经科学取样和加工的大规模、真实出现过的电子文本库", 0)
