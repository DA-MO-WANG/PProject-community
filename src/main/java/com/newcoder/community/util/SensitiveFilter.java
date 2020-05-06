package com.newcoder.community.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class SensitiveFilter {
    //记录日志
    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);
    //替换符
    private static final String REPLACEMENT = "***";
    //根节点
    private TrieNode rootNode = new TrieNode();
    //内部类前缀树
    private class TrieNode {
        //底节点标识
       private boolean isKeywordEnd = false;
       //每一个节点都是map结构
       private Map<Character,TrieNode> subNodes = new HashMap<>();
       public boolean isKeywordEnd() {
           return isKeywordEnd;
       }
       public void setKeywordEnd(boolean keywordEnd) {
           isKeywordEnd = keywordEnd;
       }
       public void addSubNode(Character c , TrieNode node) {
           subNodes.put(c , node);
       }
       public TrieNode getSubNode(Character c) {
           return subNodes.get(c);
       }

    }
    //这个敏感期初始化方法
    @PostConstruct
    public void init() {
        try (
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ) {
            String keyword;
            while ((keyword = reader.readLine()) != null) {
                // 添加到前缀树
                this.addKeyword(keyword);
            }
        } catch (IOException e) {
            logger.error("加载敏感词文件失败: " + e.getMessage());
        }
    }
    private void addKeyword(String keyword) {
        TrieNode tempNode = rootNode;
        for (int i = 0; i< keyword.length();i++) {
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);
            if (subNode == null) {
                subNode = new TrieNode();
                tempNode.addSubNode(c,subNode);
            }
            tempNode = subNode;
            if (i == keyword.length() -1) {
                tempNode.setKeywordEnd(true);
            }

        }
    }
    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        TrieNode tempNode = rootNode;
        int first = 0;
        int last = 0;
        StringBuilder sb = new StringBuilder();
        while (last < text.length()) {
            char c = text.charAt(last);
            if (isSymbol(c)) {
                if (tempNode == rootNode) {
                    sb.append(c);
                    first++;
                }
                last++;
                continue;
            }
            tempNode = tempNode.getSubNode(c);
            if (tempNode == null) {
                sb.append(text.charAt(first));
                last = ++first;
                tempNode = rootNode;
            } else if (tempNode.isKeywordEnd) {
                sb.append(REPLACEMENT);
                first = ++last;
                tempNode = rootNode;
            } else {
                last++;
            }

        }
        sb.append(text.substring(first));
        return sb.toString();
    }

    public boolean isSymbol(Character c) {
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);

    }
}

