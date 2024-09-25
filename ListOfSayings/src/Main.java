public class Main {
    public static void main(String[] args) {
        //Tree for testing, hard coded phrases 
        RedBlackTree tree = new RedBlackTree();
        
        // Inserting hardcoded Hawaiian phrases with their English translations
        tree.insert("Aloha", "Love, affection, greeting, salutation; Hello! Good-bye!");
        tree.insert("Malama", "To take care of, preserve, protect, maintain");
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
    }
}