# -*- coding: utf-8 -*-
# noinspection PyInterpreter
import re
import socket
from collections import defaultdict

import jieba
from gensim import corpora, models, similarities

TEXT_DELIMITER = "|"


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
                    # print(j, arr[j] * 100)
                    client.sendall(str.encode(str(j) + " " + str(arr[j] * 100)) + b'\n')
                    # print(material[j])


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
    # 5 建立稀疏矩阵：对比提供的所有文本，得到矩阵
    similarity_matrix = similarities.Similarity('index',
                                                lsi[corpus],
                                                num_features=lsi.num_topics)
    # 6 处理结果
    textResult = []
    compareResult = []
    for i, arr in enumerate(similarity_matrix):
        if i >= len(sentence_text):
            # print(material[i])
            for j in range(len(arr)):
                if j != i and j < len(
                        sentence_text) and arr[j] * 100 >= threshold:
                    if str(j) not in textResult:
                        textResult.append(str(j))
                    if str(i - len(sentence_text)) not in compareResult:
                        compareResult.append(str(i - len(sentence_text)))
    # 7 发送结果
    client.sendall(str.encode(TEXT_DELIMITER.join(compareResult)) + b'\n')
    client.sendall(str.encode(TEXT_DELIMITER.join(textResult)) + b'\n')


serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
serverSocket.bind(("localhost", 9091))
serverSocket.listen(5)
while True:
    tmp = b''
    # 实时接收信息
    client, clientAddr = serverSocket.accept()
    # 接收数据，一次不一定接收得完
    data = client.recv(1024)
    while not data.endswith(b'over'):
        tmp += data
        data = client.recv(1024)
    tmp += data

    if data.endswith(b'over'):
        tmp = tmp.decode('utf-8')
        params = tmp.split(TEXT_DELIMITER)
        # params: type (delimiter) text compare threshold
        if params[0] == "article":
            article = params[1].split(TEXT_DELIMITER)
            get_article_similarity(article, params[2], int(params[3]))
        elif params[0] == "sentence":
            get_sentence_similarity(deal_sentence(params[1]),
                                    deal_sentence(params[2]), int(params[3]))
    elif data.endswith(b'finish'):
        # 结束标志，必备！
        break
    # 一次请求结束，关闭客户端，以免造成资源浪费
    client.close()

if __name__ == "__main__":
    serverSocket.close()
