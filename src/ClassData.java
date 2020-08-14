import java.util.ArrayList;

//  * ClassData contains a model of any particular .class file with th following info: Name, List of fields, List of methods, name of the super class, list of interfaces, list of providers, list of clients
public class ClassData {
    private String name;
    private String superClassName;
    private ArrayList<String> interfaces;
    private String[] fields;
    private String[] methods;
    private ArrayList<String> providers;
    private ArrayList<String> clients;

    // Very important to have a empty constructor for XMLEncoder
    public ClassData(){

    }

    /*  Constructor doesn't have clients! */
    public ClassData(String name, String superClassName, ArrayList<String> interfaces, String[] fields, String[] methods, ArrayList<String> providers){
        this.name = name;
        this.superClassName = superClassName;
        this.interfaces = interfaces;
        this.fields = fields;
        this.methods = methods;
        this.providers = providers;
    }
    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setSuperClassName(String superClassName) {
        this.superClassName = superClassName;
    }

    public void setInterfaces(ArrayList<String> interfaces) {
        this.interfaces = interfaces;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public void setMethods(String[] methods) {
        this.methods = methods;
    }

    public void setProviders(ArrayList<String> providers) {
        this.providers = providers;
    }

    public void setClients(ArrayList<String> clients) {
        this.clients = clients;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getSuperClassName() {
        return superClassName;
    }

    public ArrayList<String> getInterfaces() {
        return interfaces;
    }

    public String[] getFields() {
        return fields;
    }

    public String[] getMethods() {
        return methods;
    }

    public ArrayList<String> getProviders() {
        return providers;
    }

    public ArrayList<String> getClients() {
        return clients;
    }
    public void printProviders(){
        ArrayList<String> temp = getProviders();
        System.out.print("\nProviders: \n");


        if(!temp.isEmpty()){
            for(String s: temp){
                System.out.println("    "+s);
            }
        }
        else{
            System.out.print("    No providers.\n");
        }

    }

    public void printClients(){
        ArrayList<String> temp = getClients();
        System.out.print("Clients: \n");

        if(!temp.isEmpty()){
            for(String s: temp){
                System.out.println("    "+s);
            }
        }
        else{
            System.out.print("    No clients.\n");
        }

    }

    public void printFields(){
        System.out.print("\nFields: ");
        String[] s = getFields();
        for(String m: s){
            System.out.print("\n    "+m);
        }
        //System.out.print("\n    ..... .\n");
    }

    public void printMethods(){
        System.out.print("\nMethods: ");
        String[] s = getMethods();
        for(String m: s){
            System.out.print("\n    "+m);
        }
        //System.out.print("\n    ..... .\n");
    }

    public void printInterfaces(){
        System.out.print("\nInterfaces: ");
        ArrayList<String> interfaces = getInterfaces();
        if(!interfaces.isEmpty()){
            for(String s: interfaces){
                System.out.print(""+s);
            }
        }
        else{
            System.out.print("No interfaces.");
        }

    }

    public void printSuperclass() throws NullPointerException{
        System.out.print("\nSuperclass: ");
        if(getSuperClassName() == null){
            System.out.print("None");
        }
        else{
            System.out.print(getSuperClassName());
        }
    }

    public void printClass(){
        System.out.print("\nClass Details: ");
        System.out.print("\nName: "+getName());
        printSuperclass();
        printInterfaces();
        printFields();
        printMethods();
        printProviders();
        printClients();

    }

    @Override
    public String toString(){
        String result = getName()+"Fields: ";
        return result;
    }
}
