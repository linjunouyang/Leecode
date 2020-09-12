import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Intuitively, start from every cell and try to build a word in the dictionary.
 * Backtracking (dfs) is the powerful way to exhaust every possible ways.
 * Apparently, we need to do pruning when current character is not in any word.
 *
 * How do we instantly know the current character is invalid? HashMap?
 * How do we instantly know what's the next valid character? LinkedList?
 * But the next character can be chosen from a list of characters. "Mutil-LinkedList"?
 *
 * Combing them, Trie is the natural choice.
 *
 * Trie:
 *
 * Search
 * def search(self, word):
 *         node = self.root
 *         for w in word:
 *             node = node.children.get(w)
 *             if not node:
 *                 return False
 *         return node.isWord
 *
 *
 * Why 'toCharArray' is faster than 'charAt' in this problem?
 * charAt bound-checks (but those can be optimized out), w
 * while toCharArray pays for spare memory allocation.
 * When VM is unable to eliminate bounds-check toCharArray might be faster.
 * For more info see slides 74-78 here.
 *
 */
public class _0212WordSearchII {
    /**
     * 1. Backtracking with Trie
     *
     * https://leetcode.com/problems/word-search-ii/solution/
     *
     * if we know that there does not exist any match of word in the dictionary for a given prefix,
     * then we would not need to further explore certain direction.
     *
     * Trie vs HashTable:
     *
     * if we do not use Trie to keep the prefixes of words, we then could extract all prefixes from each word and store in the hashtable, e.g. the prefixes for the word oath would be "o", "oa", "oat" and "oath".
     * Though it is not the case here, sometimes it could be faster to use the hashtable instead of Trie, here is another similar problem called Word Squares.
     *
     *
     * Some optimization tricks:
     * 1. Keep words in the trie
     * a. no need to pass the prefix as backtracking parameter -> speed up recursive call
     * b. no need to reconstruct the matched word from prefix
     *
     * 2. Backtrack along the nodes in Trie.
     * - One could use Trie simply as a dictionary to quickly find the match of words and prefixes,
     * i.e. at each step of backtracking, we start all over from the root of the Trie. This could work.
     *
     * - However, a more efficient way would be to traverse the Trie together with the progress of backtracking,
     * i.e. at each step of backtracking(TrieNode), the depth of the TrieNode corresponds to the length of the prefix that we've matched so far.
     * This measure could lift your solution out of the bottom %5% of submissions.
     *
     * 3. Gradually prune the nodes in Trie during the backtracking.
     * - the time complexity of the overall algorithm sort of depends on the size of the Trie.
     * For a leaf node in Trie, once we traverse it (i.e. find a matched word),
     * we would no longer need to traverse it again.
     * As a result, we could prune it out from the Trie.
     *
     * - Gradually, those non-leaf nodes could become leaf nodes later,
     * since we trim their children leaf nodes.
     * In the extreme case, the Trie would become empty,
     * once we find a match for all the words in the dictionary.
     * This pruning measure could reduce up to 50\%50% of the running time for the test cases of the online judge.
     *
     * 4. Remove the matched words from the Trie:
     * - In the problem, we are asked to return all the matched words, rather than the number of potential matches.
     * Therefore, once we reach certain Trie node that contains a match of word, we could simply remove the match from the Trie.
     *
     * - As a side benefit, we do not need to check if there is any duplicate in the result set.
     * As a result, we could simply use a list instead of set to keep the results,
     * which could speed up the solution a bit.
     *
     * Time: O(M (4 * 3^min(L-1, M))
     * M: number of cells
     * L: word max length
     *
     * - It is tricky is calculate the exact number of steps that a backtracking algorithm would perform.
     * We provide a upper bound of steps for the worst scenario for this problem. T
     * he algorithm loops over all the cells in the board, therefore we have M as a factor in the complexity formula.
     * It then boils down to the maximum number of steps we would need for each starting cell
     *
     * - Assume the maximum length of word is LL, starting from a cell, initially we would have at most 4 directions to explore.
     * Assume each direction is valid (i.e. worst case), during the following exploration,
     * we have at most 3 neighbor cells (excluding the cell where we come from) to explore.
     * As a result, we would traverse at most 4 * 3^(L−1) cells during the backtracking exploration.
     *
     * - One might wonder what the worst case scenario looks like.
     * Well, here is an example. Imagine, each of the cells in the board contains the letter a,
     * and the word dictionary contains a single word ['aaaa']. Voila.
     * This is one of the worst scenarios that the algorithm would encounter.
     *
     * - So with trie, this solution has same complexity as Word Search
     *
     * - Someone correct me if I'm missing something, but this seems wrong to me.
     * In the naive approach, you iterate through the word list, and for each word you iterate through every cell, and for each cell you run DFS.
     * Note importantly that only in the DFS do you iterate through the word length of every word,
     * in the outermost loop I described you are just iterating through the number of words.
     * So, I believe the runtime would be O(m * n * num_words * 4^wl).
     * But, in fact this is wrong too I think because if you have a decently sized word 4^wl may be larger than m * n,
     * but DFS can only go to m * n maximum iterations, so I think runtime is in fact O(m * n * num_words * min(4^wl, m * n)).
     *
     * Now, how does this change for a trie?
     * In the worst case, the words will share NO prefixes, and so traversing the tree = traversing the list of words, and thus the runtime is EXACTLY the same.
     *
     * The runtime does get better in the best case, if all the words share the same prefix until their last letter,
     * in which case you only have to iterate through one word basically and you get a runtime of O(m * n * min(4^wl, m * n)).
     * However, this is slightly wrong because to create the trie you need to iterate through wl * num_words,
     * so the runtime is actually O(m * n * min(4^wl, m * n) + wl * num_words).
     * (Indeed, this factor was in the other runtimes too but it was insignificant and thus dropped there.)
     *
     * Space: O(n)
     * n: total number of letters in the dictionary
     *
     * 1. Trie data structure we build.
     * In the worst case where there is no overlapping of prefixes among the words,
     * the Trie would have as many nodes as the letters of all words.
     *
     * 2. one might keep a copy of words in the Trie as well.
     * As a result, we might need 2N space for the Trie.
     *
     * 3. Recursion call stack: O(min(M, L))
     *
     */
    class TrieNode {
        HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
        String word = null;
        public TrieNode() {}
    }

    char[][] _board = null;
    ArrayList<String> _result = new ArrayList<String>();

    public List<String> findWords(char[][] board, String[] words) {
        // Step 1). Construct the Trie
        TrieNode root = new TrieNode();
        for (String word : words) {
            TrieNode node = root;

            for (Character letter : word.toCharArray()) {
                if (node.children.containsKey(letter)) {
                    node = node.children.get(letter);
                } else {
                    TrieNode newNode = new TrieNode();
                    node.children.put(letter, newNode);
                    node = newNode;
                }
            }
            node.word = word;  // store words in Trie
        }

        this._board = board;
        // Step 2). Backtracking starting for each cell in the board
        for (int row = 0; row < board.length; ++row) {
            for (int col = 0; col < board[row].length; ++col) {
                if (root.children.containsKey(board[row][col])) {
                    backtracking(row, col, root);
                }
            }
        }

        return this._result;
    }

    private void backtracking(int row, int col, TrieNode parent) {
        Character letter = this._board[row][col];
        TrieNode currNode = parent.children.get(letter);

        // check if there is any match
        if (currNode.word != null) {
            this._result.add(currNode.word);
            currNode.word = null;
        }

        // mark the current letter before the EXPLORATION
        this._board[row][col] = '#';

        // explore neighbor cells in around-clock directions: up, right, down, left
        int[] rowOffset = {-1, 0, 1, 0};
        int[] colOffset = {0, 1, 0, -1};
        for (int i = 0; i < 4; ++i) {
            int newRow = row + rowOffset[i];
            int newCol = col + colOffset[i];
            if (newRow < 0 || newRow >= this._board.length || newCol < 0
                    || newCol >= this._board[0].length) {
                continue;
            }
            if (currNode.children.containsKey(this._board[newRow][newCol])) {
                backtracking(newRow, newCol, currNode);
            }
        }

        // End of EXPLORATION, restore the original letter in the board.
        this._board[row][col] = letter;

        // Optimization: incrementally remove the leaf nodes
        if (currNode.children.isEmpty()) {
            parent.children.remove(letter);
        }
    }

    class TrieNode1 {
        TrieNode1[] next = new TrieNode1[26];
        String word;
    }

    public TrieNode1 buildTrie(String[] words) {
        TrieNode1 root = new TrieNode1();
        for (String w : words) {
            TrieNode1 p = root;
            for (char c : w.toCharArray()) {
                int i = c - 'a';
                if (p.next[i] == null) {
                    p.next[i] = new TrieNode1();
                }
                p = p.next[i];
            }
            p.word = w;
        }
        return root;
    }

    public List<String> findWords11(char[][] board, String[] words) {
        List<String> res = new ArrayList<>();
        TrieNode1 root = buildTrie(words);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs (board, i, j, root, res);
            }
        }
        return res;
    }

    public void dfs(char[][] board, int i, int j, TrieNode1 p, List<String> res) {
        char c = board[i][j];
        if (c == '#' || p.next[c - 'a'] == null) {
            // visited OR no matching in the trie
            return;
        }
        p = p.next[c - 'a'];
        if (p.word != null) {
            // found one
            res.add(p.word);
            // de-duplicate for cases like Board: a, a. Word: a
            // output["a" "a"] expected ["a"]
            p.word = null;
        }

        board[i][j] = '#';
        if (i > 0) {
            dfs(board, i - 1, j ,p, res);
        }
        if (j > 0) {
            dfs(board, i, j - 1, p, res);
        }
        if (i < board.length - 1) {
            dfs(board, i + 1, j, p, res);
        }
        if (j < board[0].length - 1) {
            dfs(board, i, j + 1, p, res);
        }
        board[i][j] = c;
    }
}
