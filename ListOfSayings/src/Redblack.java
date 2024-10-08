import java.util.ArrayList;
import java.util.List;

//Node class for the tree
class RedBlack {
    String key;
    String normalizedKey;  // A version of the key which removes the okina (and any other special characters) when considering alphabetical sorting
    String englishTranslation;
    List<String> nonEnglishWords;
    List<String> englishWords;
    RedBlack left, right, parent;
    boolean isRed;

    public RedBlack(String key, String englishTranslation) {
        this.key = key;
        this.normalizedKey = normalizeKey(key);  // Normalize the key
        this.englishTranslation = englishTranslation;
        this.nonEnglishWords = List.of(key.split(" ")); 
        this.englishWords = List.of(englishTranslation.replaceAll("[^\\w\\s]", "").split(" "));         //replaceAll("[^\\w\\s]", "") removes any character that is not a word character, only used for english since nonEnglish does not contain punctuation from the resource used
        this.left = this.right = this.parent = null;
        this.isRed = true;
    }
    // Helper function to normalize the key (removes special characters for sorting)
    private String normalizeKey(String key) {
        return key.replaceAll("[^\\p{L}\\p{N}]", "").toLowerCase();
        // The replaceAll and toLowerCase functions remove non-alphabetic characters and converts to lowercase
    }
}
//Structure for RedBlackTree along with our operations
class RedBlackTree {
    private RedBlack root;
    private final RedBlack TNULL; //Represents a null node, makes it much more concise to check if a node is null

    public RedBlackTree() {
        TNULL = new RedBlack("", "");
        TNULL.isRed = false;
        root = TNULL;
    }
//Main Operations

//Returns a specific saying (member)
public boolean member(String saying) {
    return search(root, normalizeKey(saying)) != null;
    }


private RedBlack search(RedBlack node, String normalizedKey) {
    while(node != TNULL && !normalizedKey.equals(node.normalizedKey)) {
        if(normalizedKey.compareTo(node.normalizedKey) < 0) {
        node = node.left;
        } else {
            node = node.right;
    }
}
  if (node != TNULL) {
      return node;
  } else {
    return null;
  }
}

//Returns the first saying in tree
public String first() {
    RedBlack node = root;
    if (node == TNULL) {
        return null; 
    }
    while (node.left != TNULL) {
        node = node.left; 
    }
    return node.key; 
}

//Returns the last saying in tree
public String last() {
    RedBlack node = root;
    if (node == TNULL) {
        return null; 
    }
    while (node.right != TNULL) {
        node = node.right; 
    }
    return node.key; 
}

//Finds predecessor in tree
public String predecessor (String key) {
    RedBlack node = search (root, normalizeKey(key));
    if (node == TNULL) {
        return null;
    }
    if (node.left != TNULL) {
        return max(node.left).key;
    }
    RedBlack parent = node.parent;
    while (parent != TNULL && node == parent.left) {
        node = parent;
        parent = parent.parent;
    }

//Returns the predecessor or a null
if (parent == TNULL) {
    return null;
} else {
    return parent.key;
  }
    
}

private RedBlack max(RedBlack node) {
    while (node.right != TNULL) {
        node = node.right;
    }
    return node;
}

//Finds successor in tree
public String successor(String key) {
    RedBlack node = search(root, normalizeKey(key));
    if (node == TNULL) {
        return null;
    }
    if (node.right != TNULL) {
        return min(node.right).key;
    }
    RedBlack parent = node.parent;
    while (parent != TNULL && node == parent.right) {
        node = parent;
        parent = parent.parent;
    }

//Returns the successor or a null
if (parent == TNULL) {
    return null;
} else {
    return parent.key;
  }
}
private RedBlack min(RedBlack node) {
    while (node.left != TNULL) {
        node = node.left;
    }
    return node;
}

//Returns sayings whose non-English version contains the given non-English word(meHua)
public List<String> meHua (String word) {
    List<String> results = new ArrayList <>();
    meHua(root, word, results);
    return results;
}

private void meHua(RedBlack node, String word, List<String> results) {
    if (node != TNULL) { 
        
        if (node.nonEnglishWords.contains(word)) {
            results.add(node.key); 
        }
        
        meHua(node.left, word, results);
        meHua(node.right, word, results);
    }
}
//Returns all sayings whose English translation contains the given English word(withWord)
public List<String> withWord (String word) {
    List<String> results = new ArrayList <>();
    withWord(root, word, results);
    return results;
}

private void withWord(RedBlack node, String word, List<String> results) {
    if (node != TNULL) { 
        
        if (node.englishWords.contains(word)) {
            results.add(node.key); 
        }
        
        withWord(node.left, word, results);
        withWord(node.right, word, results);
    }
}

    //Inserts a new phrase into the tree
    public void insert(String key, String englishTranslation) {
        RedBlack node = new RedBlack(key, englishTranslation);
        node.left = node.right = node.parent = TNULL;

        RedBlack parent = null;
        RedBlack current = root;
        String normalizedKey = node.normalizedKey;

        while (current != TNULL) {
            parent = current;
            if (normalizedKey.compareTo(current.normalizedKey) < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        node.parent = parent;

        if (parent == null) {
            root = node;
        } else if (normalizedKey.compareTo(parent.normalizedKey) < 0) {
            parent.left = node;
        } else {
            parent.right = node;
        }

        if (node.parent == null) {
            node.isRed = false;
            return;
        }

        if (node.parent.parent == null) {
            return;
        }

        fixInsert(node);
    }

//Helper Functions
    //Used at the end of rotateRight and rotateLeft. 
    //Sets the parent-child relationship between the parent node of the former root node of the rotated subtree and its new root node.
    private void replaceParentsChild(RedBlack parent, RedBlack oldChild, RedBlack newChild) {
        if (parent == null) {
            root = newChild;
        } else if (parent.left == oldChild) {
            parent.left = newChild;
        } else if (parent.right == oldChild) {
            parent.right = newChild;
        }
    
        if (newChild != TNULL) {
            newChild.parent = parent;
        }
    }

    // Rotates node to the right. This is used when a node has a left child that is red and needs to be moved up.
    private void leftRotate(RedBlack node) {
        RedBlack rightNode = node.right;
        node.right = rightNode.left;
    
        if (rightNode.left != TNULL) {
            rightNode.left.parent = node;
        }
    
        replaceParentsChild(node.parent, node, rightNode);
    
        rightNode.left = node;
        node.parent = rightNode;
    }
    //Makes the right child the new parent of the subtree, while the original node becomes the left child of the new parent.
    private void rightRotate(RedBlack node) {
        RedBlack leftNode = node.left;
        node.left = leftNode.right;
    
        if (leftNode.right != TNULL) {
            leftNode.right.parent = node;
        }
    
        replaceParentsChild(node.parent, node, leftNode);
    
        leftNode.right = node;
        node.parent = leftNode;
    }
    //inOrderTraversal prints each value recursively (good for viewing full tree)
    public void inOrderTraversal() {
        RedBlack current = root;
        inOrderTraversal(current);
    }
    //Allows for inOrderTraversal starting from any node 
    private void inOrderTraversal(RedBlack node) {
        if (node != TNULL) {
            inOrderTraversal(node.left);
            System.out.println("Phrase: " + node.key + " | Translation: " + node.englishTranslation);
            inOrderTraversal(node.right);
        }
    }

    //Fixes the tree to maintain red black properties after insertion
    //Utilizes leftRotate and rightRotate
    private void fixInsert(RedBlack node) {
        RedBlack uncle;
        while (node.parent.isRed) {
            if (node.parent == node.parent.parent.right) {
                uncle = getUncle(node.parent);
    
                if (uncle != null && uncle.isRed) {
                    uncle.isRed = false;
                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rightRotate(node);
                    }
                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;
                    leftRotate(node.parent.parent);
                }
            } else {
                uncle = getUncle(node.parent);
    
                if (uncle != null && uncle.isRed) {
                    uncle.isRed = false;
                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.right) {
                        node = node.parent;
                        leftRotate(node);
                    }
                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;
                    rightRotate(node.parent.parent);
                }
            }
            if (node == root) {
                break;
            }
        }
        root.isRed = false;
    }
    //Retrieves uncle node for fixInsert
    private RedBlack getUncle(RedBlack parent) {
        if (parent == null || parent.parent == null) {
            return null;
        }
    
        if (parent == parent.parent.left) {
            return parent.parent.right;
        } else {
            return parent.parent.left;
        }
    }
    private String normalizeKey(String key) {
        return key.replaceAll("[^\\p{L}\\p{N}]", "").toLowerCase();
        // The replaceAll and toLowerCase functions remove non-alphabetic characters and converts to lowercase
    }
}



