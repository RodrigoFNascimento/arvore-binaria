import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

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
     * Prints every node in the tree in the order: left, root, right.
     * @param node Root of the tree.
     * @throws FileNotFoundException
     */
    private static void printEPD(String fileName, Arquivo node) throws FileNotFoundException {
        if (node == null) 
            return; 
  
        /* first recur on left child */
        printEPD(fileName, node.left); 
  
        /* then print the data of node */
        writeToFile(fileName, node.getOutputString()); 
  
        /* now recur on right child */
        printEPD(fileName, node.right); 
    }

    /**
     * Prints every node in the tree in the order: root, left, right.
     * @param node Root of the tree.
     * @throws FileNotFoundException
     */
    private static void printPED(String fileName, Arquivo node) throws FileNotFoundException {
        if (node == null) 
            return; 
  
        /* first print data of node */
        writeToFile(fileName, node.getOutputString() + " "); 
  
        /* then recur on left sutree */
        printPED(fileName, node.left); 
  
        /* now recur on right subtree */
        printPED(fileName, node.right);
    }

    /**
     * Prints every node in the tree in the order: left, right, root.
     * @param node Root of the tree.
     * @throws FileNotFoundException
     */
    private static void printEDP(String fileName, Arquivo node) throws FileNotFoundException {
        if (node == null) 
            return; 
  
        // first recur on left subtree 
        printEDP(fileName, node.left); 
  
        // then recur on right subtree 
        printEDP(fileName, node.right); 
  
        // now deal with the node 
        writeToFile(fileName, node.getOutputString());
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

    /**
     * Writes content to file.
     * @param fileName Name of the file (with extension) to be writen
     * @param content Content to be writen on the file
     * @throws FileNotFoundException
     */
    private static void writeToFile(String fileName, String content) throws FileNotFoundException {

        try(FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println("");
            out.print(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Writes content to file. If the line to be written is the first one,
     * doesn't add a line break before it, otherwise does.
     * @param fileName
     * @param content
     * @param firstLine
     * @throws FileNotFoundException
     */
    private static void writeToFile(String fileName, String content, boolean firstLine) throws FileNotFoundException {

        try(FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            if (!firstLine)
                out.println("");
            out.print(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Empties the content of a file.
     * @param fileName Name of the file.
     * @throws FileNotFoundException
     */
    private static void emptyFile(String fileName) throws FileNotFoundException {
        new PrintWriter(fileName).close();
    }

    public static void main(String[] args) {

        try (FileInputStream inputStream = new FileInputStream(args[0])) {
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Gets the number of files to be inserted
            int n = Integer.parseInt(reader.readLine());

            for (int i = 0; i < n; i++) {
                Arquivo tempArquivo = new Arquivo();
                String line = reader.readLine();

                // Gets the indexes of the two spaces
                int firstSpaceIndex = line.indexOf(" ");
                int secondSpaceIndex = line.indexOf(" ", firstSpaceIndex + 1);

                // Reads the file's info
                tempArquivo.name = line.substring(0, firstSpaceIndex);
                tempArquivo.permission = line.substring(firstSpaceIndex + 1, secondSpaceIndex);
                tempArquivo.size = line.substring(secondSpaceIndex + 1);
                tempArquivo.index = i;

                insert(tempArquivo);
            }

            emptyFile(args[1]);
            writeToFile(args[1], "EPD:", true);
            printEPD(args[1], tree);
            writeToFile(args[1], "PED:");
            printPED(args[1], tree);
            writeToFile(args[1], "EDP:");
            printEDP(args[1], tree);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}