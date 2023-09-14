package lol.typefast.trie.tree;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * TODO:
 * add builder function that instantiates the Trie class
 * add function to load dictionary into memory
 * iterate over the dictionary in the builder function and add all words to the Trie in prefix order
 * return the loaded Trie
 */
public class PrefixTreeBuilder  {
    // private static final String filePath = "test.txt";
    // private static final String filePath = "test.txt";
    // private static final String filePath = "words.txt";
    private static final String filePath = "words_alpha.txt";

    public PrefixTreeBuilder() {
        
    }

    /*
     * Read file line by line
     * Insert word into tree, marking end of word accordingly
     */
    public Trie build() {
        TrieNode root = new TrieNode();
        BufferedReader reader = this.loadFile();
        Trie trie = new Trie(root);
        this.addWordsToTrie(trie, reader);
        return trie;
    }

    private BufferedReader loadFile() {
        InputStream inputStream = PrefixTreeBuilder.class.getClassLoader().getResourceAsStream(filePath);
        if(inputStream != null) {
            return new BufferedReader(new InputStreamReader(inputStream));
        }
        System.err.println("File not found: " + filePath);
        System.exit(1);
        return null;
    }

    private void addWordsToTrie(Trie trie, BufferedReader reader) {
        try {
            String line;
            while((line = reader.readLine()) != null) {
                trie.addWord(line);
            }
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
