import java.util.List;
//Node class for the tree
class RedBlack {
    String key;
    String englishTranslation;
    List<String> nonEnglishWords;
    List<String> englishWords;
    RedBlack left, right, parent;
    boolean isRed;

    public RedBlack(String key, String englishTranslation) {
        this.key = key;
        this.englishTranslation = englishTranslation;
        this.nonEnglishWords = List.of(key.split(" ")); //replaceAll("[^\\w\\s]", "") removes any character that is not a word character, only used for english since nonEnglish does not contain punctuation from the resource used

        this.englishWords = List.of(englishTranslation.replaceAll("[^\\w\\s]", "").split(" "));
        this.left = this.right = this.parent = null;
        this.isRed = true;
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
    //Inserts a new phrase into the tree
    public void insert(String key, String englishTranslation) {
        RedBlack node = new RedBlack(key, englishTranslation);
        node.left = node.right = node.parent = TNULL;

        RedBlack parent = null;
        RedBlack current = root;

        while (current != TNULL) {
            parent = current;
            if (node.key.compareTo(current.key) < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        node.parent = parent;

        if (parent == null) {
            root = node;
        } else if (node.key.compareTo(parent.key) < 0) {
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
//Additional Operations (Not including insert as that's essential component of assembling the tree) 



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
    
}



