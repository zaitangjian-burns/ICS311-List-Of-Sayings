public class Main {
    public static void main(String[] args) {
        //Tree for testing, hard coded phrases 
        RedBlackTree tree = new RedBlackTree();
        
        // Inserting hardcoded Hawaiian phrases with their English translations
        tree.insert("Aloha", "Love, affection, greeting, salutation; Hello! Good-bye!");
        tree.insert("Malama", "To take care of, to preserve, protect, maintain");
        tree.insert("Mahalo", "Thanks, graditude, respect");
        tree.insert("E komo mai", "Welcome! Enter");
        tree.insert("Pau", "Finished, ended, all done");
        tree.insert("A hui hou", "Until we meet again, goodbye");
        tree.insert("Hana hou", "Encore, do it again");
        tree.insert("Mele Kalikimaka", "Merry Christmas");
        //For the next two, interestingly an Okina is not recognized in VSCode or the console (it returns "?"), we just used a single quotation mark instead
        tree.insert("Pehea 'oe?", "How are you?"); 
        tree.insert("'ono", "Delicious, tasty, savory; to relish, crave");

        // Performing in-order traversal to view the phrases
        System.out.println("In-Order Traversal of the Red-Black Tree:");
        tree.inOrderTraversal();
        
        //Testing the member operation
        System.out.println("Is the word 'Mahalo' in the tree? " + tree.member("Mahalo")); //true

        //Testing the first operation
        System.out.println("First phrase: " + tree.first()); //'A hui hou'

        //Testing the last operation
        System.out.println("Last phrase: " + tree.last());   //'Pehea 'oe'

        //Testing the predecessor operation
        System.out.println("Predecessor of 'Mahalo': " + tree.predecessor("Mahalo")); //Hana hou

        //Testing the successor operation
        System.out.println("Successor of 'Aloha': " + tree.successor("Aloha")); // E komo mai

        //Testing the meHua operation
        System.out.println("Sayings containing 'hou': " + tree.meHua("hou")); //A hui hou, Hana hou

        //Testing the withWord operation
        System.out.println("Sayings containing 'love': " + tree.withWord("Love")); //Aloha
        System.out.println("Sayings containing 'to': " + tree.withWord("to")); //Malama, 'ono
    }
}
