import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

class Arquivo {
    public String name;
    public String size;
    public String permission;
    public int index;
    public Arquivo right;
    public Arquivo left;

    public Arquivo() {
        this.name = "";
        this.size = "";
        this.permission = "";
        this.index = 0;
        this.right = null;
        this.left = null;
    }

    public Arquivo(String name, String size, String permission, int index) {
        this.name = name;
        this.size = size;
        this.permission = permission;
        this.index = index;
        this.right = null;
        this.left = null;
    }

    public String getOutputString() {
        String output;
        if ("1".equals(this.size)) {
            output = this.index + " " + this.name + " " + this.permission + " " + this.size + " byte";
        } else {
            output = this.index + " " + this.name + " " + this.permission + " " + this.size + " bytes";
        }

        return output;
    }
}

public class rodrigonascimento_201600155174_arvorebinaria {

    private static Arquivo tree = null;

    /**
     * Prints every node in the tree in the order:
     * left, root, right.
     * @param node Root of the tree.
     */
    private static void printEPD(Arquivo node) {
        if (node == null) 
            return; 
  
        /* first recur on left child */
        printEPD(node.left); 
  
        /* then print the data of node */
        System.out.println(node.getOutputString() + " "); 
  
        /* now recur on right child */
        printEPD(node.right); 
    }

    /**
     * Prints every node in the tree in the order:
     * root, left, right.
     * @param node Root of the tree.
     */
    private static void printPED(Arquivo node) {
        if (node == null) 
            return; 
  
        /* first print data of node */
        System.out.println(node.getOutputString() + " "); 
  
        /* then recur on left sutree */
        printPED(node.left); 
  
        /* now recur on right subtree */
        printPED(node.right);
    }

    /**
     * Prints every node in the tree in the order:
     * left, right, root.
     * @param node Root of the tree.
     */
    private static void printEDP(Arquivo node) {
        if (node == null) 
            return; 
  
        // first recur on left subtree 
        printEDP(node.left); 
  
        // then recur on right subtree 
        printEDP(node.right); 
  
        // now deal with the node 
        System.out.println(node.getOutputString() + " ");
    }

    /**
     * Inserts a new node into {@link #tree}.
     * @param newNode Node that will be added to the tree.
     */
    private static void insert(Arquivo newNode) { 
        tree = insertToBinaryTree(tree, newNode); 
    }
       
    /**
     * Inserts a new node into a binary tree.
     * @param root Root of the tree into which the node will be added.
     * @param newNode Node that will be added to the tree.
     * @return The tree with the added node.
     */
    private static Arquivo insertToBinaryTree(Arquivo root, Arquivo newNode) { 

        // If the tree is empty, return a new node
        if (root == null) { 
            root = newNode; 
            return root; 
        } 

        // Otherwise, recur down the tree
        if (newNode.name.compareTo(root.name) < 0) 
            root.left = insertToBinaryTree(root.left, newNode);
        else if (newNode.name.compareTo(root.name) > 0) 
            root.right = insertToBinaryTree(root.right, newNode);
        else
            if ("rw".equals(root.permission)) {
                newNode.left = root.left;
                newNode.right = root.right;
                root = newNode;
            }

        // return the (unchanged) node pointer
        return root; 
    }

    public static void main(String[] args) {

        try (FileInputStream inputStream = new FileInputStream(args[0])) {
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            int n = Integer.parseInt(reader.readLine());

            for (int i = 0; i < n; i++) {
                Arquivo tempArquivo = new Arquivo();
                String line = reader.readLine();

                int firstSpaceIndex = line.indexOf(" ");
                int secondSpaceIndex = line.indexOf(" ", firstSpaceIndex + 1);

                tempArquivo.name = line.substring(0, firstSpaceIndex);
                tempArquivo.permission = line.substring(firstSpaceIndex + 1, secondSpaceIndex);
                tempArquivo.size = line.substring(secondSpaceIndex + 1);
                tempArquivo.index = i;

                insert(tempArquivo);
            }

            System.out.println("EPD:");
            printEPD(tree);
            System.out.println("PED:");
            printPED(tree);
            System.out.println("EDP:");
            printEDP(tree);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}