import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Map;

//  * ClassLoader class is responsible for finding all of the .class files in the directory and storing it's info in a HashMap of the DataMap class
public class ClassLoader {

    /* Empty Constructor */
    ClassLoader(){}
    // Main method that loads all .class files and fills up a HashMap
    public void loadPackage(String path){
        File file = new File(path);
        // filter files in the directory based on .class extension
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.toLowerCase().endsWith(".class");
            }
        };
        // .class files were found
        if(file.listFiles(filter).length != 0){
            for(File classFile: file.listFiles(filter)){
                String pathWithoutExtension = classFile.getName().replace(".class","");
                DataMap.getMap().put(pathWithoutExtension, null);
            }
            DataMap.fillClassNames(); // Very important to fill ClassNames Array before setting up providers and clients

            // Build preliminary HashMap: No clients
            for(File classFile: file.listFiles(filter)){
                String fileName = classFile.getName().replace(".class", "");

                Class c = loadClass(fileName,path);
                String className = loadName(c);
                String superClassName = loadSuperClassName(c);
                ArrayList<String> classInterface = loadInterfaces(c);
                String[] classFields = loadFields(c);
                String[] classMethods = loadMethods(c);
                ArrayList<String> providers = loadProviders(c);
                ClassData newClass = new ClassData(className,superClassName,classInterface,classFields,classMethods,providers);
                DataMap.getMap().put(className,newClass); // put it in the map
            }

            // Iterate HashTable and insert clients for each object
            for (Map.Entry<String, ClassData> entry : DataMap.getMap().entrySet()) { // traverse HashMap to get every ClassData
                ArrayList<String> clients = loadClients(entry.getValue()); // Create and load clients list
                entry.getValue().setClients(clients); // add clients list to the ClassData object
            }
        }
        // no .class files were found
        else{
            System.out.print("\nNo class files were found at the path! You can load an XML file through option 4.\n");
        }

    }
    // loads the class from provided path
    private Class loadClass(String name, String path){
        Class nil = null;
        try{
            String url = "file://" + path + "/";
            URL classUrl = new URL(url);
            URLClassLoader ucl = new URLClassLoader(new URL[]{classUrl});
            return ucl.loadClass(name);
        } catch (MalformedURLException | ClassNotFoundException e) {
            System.out.print("No class files were found at the path! You can load an XML file through option 4");
        }
        return nil;
    }
    // Returns fields of a given class
    private String[] loadFields(Class c){
        Field[] fields = c.getDeclaredFields();
        String[] results = new String[fields.length];
        int i = 0;
        for(Field field: fields){
            results[i] = field.getName() + " : " + field.getType().getSimpleName();
            i++;
        }
        return results;
    }

    // Returns methods of a given class
    private String[] loadMethods(Class c) {
        Method[] methods = c.getDeclaredMethods();
        String[] results = new String[methods.length];
        int i = 0;
        for (Method method: methods){
            int m = method.getModifiers();
            String mod = Modifier.toString(m);
            results[i] = method.getName()+": "+mod;
            i++;
        }
        return results;
    }

    // Returns a class name of a given class
    private String loadName(Class c){
        return c.getName();
    }

    // Returns a name of a super class of a given class
    private String loadSuperClassName(Class c) {
        String result = null;
        try {
            result = c.getSuperclass().getSimpleName();
        }
        catch (NullPointerException e){
            System.out.println("Error while Geting superclass name");
        }
        return result;
    }

    // returns names of interfaces of a given class
    private ArrayList<String> loadInterfaces(Class c){
        Class[] interfaces = c.getInterfaces();
        ArrayList<String> result = new ArrayList<>(interfaces.length);
        for(Class cl: interfaces){
            result.add(cl.getName());
        }
        return result;
    }

    // Returns a list of Providers of class c i.e. list of classes that class c depends on.
    private ArrayList<String> loadProviders(Class c){
        ArrayList<String> classNames = DataMap.getClassNames(); // list of names of all .class files in dir
        ArrayList<String> types = new ArrayList<>(); // list of types of every fields
        ArrayList<String> providers = new ArrayList<>(); // return list

        Field[] fields = c.getDeclaredFields(); // list of fields of the of the class c


        for(Field field: fields){ // Traverse through fields of class c: add the type of each field to types array list
            String type = field.getType().getSimpleName();
            types.add(type);
        }

        for(String str: types){ // Traverse types array list
            for(String cl: classNames){ // Traverse list of class names: if type equals to the one of class names -> add to providers list
                if(str.equals(cl)){
                    providers.add(str);
                }
            }
        }
        return providers;
    }

    // Returns a list of clients of ClassData object i.e. This will be a list of classes that depends on this class.
    private ArrayList<String> loadClients(ClassData classData){

        String myName = classData.getName(); // The name of the class
        ArrayList<String> clients = new ArrayList<>(); // return list

        for (Map.Entry<String, ClassData> entry : DataMap.getMap().entrySet()) { // traverse HashMap for every ClassData object

            String entryName = entry.getValue().getName(); // temp variable to hold the name of the class
            ArrayList<String> providers = entry.getValue().getProviders(); // temp variable to hold Providers of the current class

            for(String provider: providers){ // traverse providers to see if there is a match for the current class name
                if(provider.equals(myName)){
                    clients.add(entryName); // match
                }
            }
        }
        return clients;
    }
}
