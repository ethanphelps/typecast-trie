package lol.typefast.trie.tree;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class TrieNode {
    private Map<String, TrieNode> nodes;
    private String letter; // may not be needed
    private boolean endOfWord;

    public TrieNode() {
        this.nodes = new LinkedHashMap<String, TrieNode>();
        this.endOfWord = false;
    }

    public TrieNode(boolean endOfWord) {
        this.nodes = new LinkedHashMap<String, TrieNode>();
        this.endOfWord = endOfWord;
    }

    public TrieNode(String letter, boolean endOfWord) {
        this.nodes = new LinkedHashMap<String, TrieNode>();
        this.endOfWord = endOfWord;
        this.letter = letter;
    }

    public boolean endOfWord() {
        return this.endOfWord;
    }

    public void setEndOfWord() {
        this.endOfWord = true;
    }

    public String getLetter() {
        return this.letter;
    }

    public void addChild(String letter) {
        TrieNode child = new TrieNode(letter, false);
        this.nodes.put(letter, child);
    }

    @Nullable
    public TrieNode getChild(String letter) {
        return this.nodes.get(letter);
    }

    public boolean hasChild(String letter) {
        return this.nodes.containsKey(letter);
    }

    public Map<String, TrieNode> getChildren() {
        return this.nodes;
    }
}
