package lol.typefast.trie.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/*
 * TODO: figure out if the Trie should be stored as an adjacency list of TrieNodes or if it should be stored as 
 * a network of TrieNodes that directly reference their children via a Map
 */
public class Trie {
    private TrieNode root;
    private List<String> searchResult;
    
    Trie(TrieNode root) {
        this.root = root;
        this.searchResult = new ArrayList<String>(0);
        System.out.println("Trie is built!");
    }

    /*
     * Traverse the tree until the end of the word. If one of the nodes corresponding to a letter in the word does
     * not have the subsequent letter as a child, then this means the word is not in the Trie. After the loop ends, 
     * node corresponds to the last letter of the word. If the node isn't marked as "endOfWord", then the word is 
     * not considered to be in the Trie.
     */
    public boolean checkWord(String word) {
        TrieNode node = root;
        for(int i = 0; i < word.length(); i++) {
            String c = String.valueOf(word.charAt(i));
            if(!node.hasChild(c)) {
                return false;
            }
            node = node.getChild(c);
        }
        return node.endOfWord();
    }

    /*
     * Search through the trie until end of branch is found (or not) and start adding remaining letters.
     * If word is found before reaching end of a branch, it might be in the Trie??
     */
    public void addWord(String word) {
        TrieNode node = root;
        for(int i = 0; i < word.length(); i++) {
            String c = String.valueOf(word.charAt(i));
            if(node.getChild(c) == null) {
                node.addChild(c);
            }
            node = node.getChild(c);
        }
        node.setEndOfWord();
    }
    
    public List<String> getAllWords() {
        this.resetSearchResults();
        this.iterativeDfs(this.root);
        return this.searchResult;
    }

    public int size() {
        Stack<TrieNode> nodeStack = new Stack<>();
        int result = 0;
        for(TrieNode child : this.root.getChildren().values()) {
            nodeStack.push(child);
        }
        while(!nodeStack.empty()) {
            TrieNode node = nodeStack.pop();
            if(node.endOfWord()) {
                result++;
            }
            if(node.getChildren().size() > 0) {
                for(TrieNode child : node.getChildren().values()) {
                    nodeStack.push(child);
                }
            }
        }
        return result;
    }

    private void iterativeDfs(TrieNode start) {
        Stack<TrieNode> nodeStack = new Stack<>();
        Stack<String> pathStack = new Stack<>();
        TrieNode node = start;
        for(TrieNode child : start.getChildren().values()) {
            nodeStack.push(child);
            pathStack.push(child.getLetter());
        }
        while(!nodeStack.empty()) {
            node = nodeStack.pop();
            String result = pathStack.pop();
            if(node.endOfWord()) {
                this.searchResult.add(result);
            }
            if(node.getChildren().size() > 0) {
                for(TrieNode child : node.getChildren().values()) {
                    nodeStack.push(child);
                    pathStack.push(result + child.getLetter());
                }
            }
        }
    }

    /*
     * Search for the prefix and if found, return the node matching the last character of the word. If not found, return null?
     */
    private TrieNode findPrefix(String prefix) {
        TrieNode node = root;
        for(int i = 0; i < prefix.length(); i++) {
            String c = String.valueOf(prefix.charAt(i));
            if(!node.hasChild(c)) {
                return null;
            }
            node = node.getChild(c);
        }
        return node;
    }

    /**
     *  Traverse until prefix found (or not). Return empty if prefix not found
     *  If prefix found, do a DFS on the subtree at that prefix and accumulate a list of words to return
     */
    public List<String> findWordsWithPrefix(String prefix) {
        this.resetSearchResults();
        if(this.checkWord(prefix)) {
            this.searchResult.add(prefix);
        }
        TrieNode endOfPrefix = this.findPrefix(prefix);
        if(endOfPrefix != null) {
            this.prefixDFS(prefix, endOfPrefix);
        }
        System.out.println("Size of search result: " + String.valueOf(this.searchResult.size()));
        return this.searchResult;
    }

    /*
     * Given a prefix, do a DFS on the subtree at node start and accumulate a list of all words
     * matching the prefix. For each word found, concatenate the branch to the prefix and add to the list
     */
    private void prefixDFS(String prefix, TrieNode start) {
        Stack<TrieNode> nodeStack = new Stack<>();
        Stack<String> pathStack = new Stack<>();
        for(TrieNode child : start.getChildren().values()) {
            nodeStack.push(child);
            pathStack.push(child.getLetter());
        }
        while(!nodeStack.empty()) {
            TrieNode node = nodeStack.pop();
            String suffix = pathStack.pop();
            if(node.endOfWord()) {
                this.searchResult.add(prefix + suffix);
            }
            if(node.getChildren().size() > 0) {
                for(TrieNode child : node.getChildren().values()) {
                    nodeStack.push(child);
                    pathStack.push(suffix + child.getLetter());
                }
            }
        }
    }

    private void resetSearchResults() {
        this.searchResult = new ArrayList<String>(0);
    }
}