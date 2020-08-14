import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static String path;
    static ClassLoader classLoader = new ClassLoader();
    static String lastFileName; // Contains the name of the class that was last accessed by the user. Used by saveFile() function
    private static Serialization serializer = new Serialization();

    public static void main(String[] args){
        pathSetter(args); // Set the name of the current path based on the first argument
        load(classLoader, path); // Load all classes in the path directory in the Hash Map of DataMap class
        showMainMenu();
        while(true){
            Scanner reader = new Scanner(System.in);
            String input = reader.next();
            switch(input){
                case "1":
                    printClasses();
                    subMenu();
                    break;
                case "2":
                    System.out.print("\nChoose a class to view: \n\n");
                    printClasses();
                    subMenu();
                    break;
                case "3": saveAllClasses(); // Save a HashMap object into an XML file
                    break;
                case "4": loadSavedFile(); // Load a HashMap object of all classes from the current directory
                    break;
                case "s": saveFile(); // Saves a ClassData object from HashMap into XML file
                    break;
                case "m": showMainMenu(); // Prints main menu
                    break;
                case "q": return; // exit application
            }
        }

    }
    // helper function to print Main Menu
    private static void showMainMenu(){
        System.out.print("\n*** Package Explorer: Main Menu ***");
        System.out.print("\n-----------------------------------");
        System.out.print("\n   1. List all classes\n   2. View a class\n   3. Save all classes\n   4. Load classes info from XML");
        System.out.print("\n\nEnter your choice(1-4) or q to quit the program: ");
    }
    // pathSetter sets a variable path to the correct input
    private static void pathSetter(String[] args){
        if(args.length != 1){
          path = System.getProperty("user.dir");
        }else{
            path = args[0];
        }
    }
    // Helper function that loads all class files into Hash Map of DataMap class using ClassLoader class
    private static void load(ClassLoader loader, String path){

        if(path.equals(System.getProperty("user.dir"))){
            loader.loadPackage(System.getProperty("user.dir")); // Load files from current directory
        }else{
            loader.loadPackage(path); // load Files from the argument
        }
    }

    // Goes through the array list of Class names in DataMap
    private static void printClasses(){
        System.out.print("\nList of classes: ");
        System.out.print("\n----------------\n");

        // Print classes from classNames Array List
        for(int i = 1; i<DataMap.getClassNames().size(); i++){
            System.out.printf("\n%d. %s", i, DataMap.getClassNames().get(i));
        }
        System.out.printf("\n\nEnter (1-%d) to view details or m for main menu: ",DataMap.getClassNames().size()-1);
    }
    // Helper function to print out a sub menu after all classes were displayed
    private static void subMenu(){
        Scanner readerB = new Scanner(System.in);
        String input = readerB.next();

        switch (input){
            case "m": showMainMenu();
                break;
            case "s": saveFile();
                break;
            default : printClass(input);
                break;
        }
    }
    // Prints class information from DataMap hash map based on the numerical input from the user
    private static void printClass(String index){
        int i = Integer.parseInt(index);
        try {
            String className = DataMap.getClassNames().get(i);
            printClassFromMap(className);
            System.out.print("\nEnter s to save or m for Main Menu: ");
        }
        catch (IndexOutOfBoundsException | NullPointerException e){
            System.out.println("\nThis class doesn't exist. Try again!");
            printClasses();
            subMenu();
        }
    }
    // printClassFromMap gets a ClassData object from the HashMap and prints its content using object's internal function
    private static void printClassFromMap(String className){
        ClassData temp = DataMap.getMap().get(className);
        lastFileName = temp.getName();
        temp.printClass();
    }
    // saveFile() function saves the Class Data object from the Hash Map in DataMap class with key value of lastFileName to an XML file in the current directory
    private static void saveFile() {
        serializer.serializeOneToXML(lastFileName);
        System.out.printf("\n\nSaved class information as %s.xml",lastFileName);
        System.out.print("\nFile was written in "+path);
        showMainMenu();
    }
    // saveAllClasses saves the HashMap object from the DataMap class to an XML file
    private static void saveAllClasses() {
        System.out.print("Enter a file name(without extension): ");
        Scanner readerB = new Scanner(System.in);
        String name = readerB.next();
        serializer.serializeMapToXML(name); // Serialize the HashMap object in XML file
        System.out.printf("\nSaved all classes information as %s.xml",name);
        System.out.printf("\nFile was written in %s\n\n",path);
        showMainMenu();
    }
    // loadSavedFile() function asks for the name of the xml file to load; tries to load it and if the file not - an error message will be displayed
    private static void loadSavedFile() {
        System.out.print("Enter a file name to load or m for main menu.");
        System.out.print("\nFile name (no need to provide .xml extension): ");
        Scanner readerB = new Scanner(System.in);
        String name = readerB.next();

        if(name.equals("m")){
        }
        else{
            try {
                serializer.deserializeMapFromXML(name);
                String fullName = name + ".xml";
                System.out.printf("\nFile %s was successfully loaded. Explore!\n",fullName);
                showMainMenu();
            }
            catch (IOException e){
                System.out.println("\nFile not found! Try again: ");
                loadSavedFile();
            }
        }
    }
}
