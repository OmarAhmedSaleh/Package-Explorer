import java.io.*;
import java.util.HashMap;
import java.beans.ExceptionListener;
import java.beans.PersistenceDelegate;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;

//  * Serialization class provides the program with all the necessary methods to save and load files in the XML format using XMLEncoder.
public class Serialization {
    HashMap<String, ClassData> localMap = DataMap.getMap();
    /* Empty Constructor */
    public Serialization() {
    }
    // Saves one ClassData file from the HashMap
    public void serializeOneToXML(String fileName) {
        try{
            ClassData data = DataMap.getMap().get(fileName); // Get the object from HashMap
            String name = data.getName()+ ".xml";
            FileOutputStream fos = new FileOutputStream(name);
            XMLEncoder encoder = new XMLEncoder(fos);
            encoder.setExceptionListener(new ExceptionListener() {
                @Override
                public void exceptionThrown(Exception e) {
                    System.out.println("Exception! :" + e.toString());
                    e.printStackTrace();
                }
            });
            encoder.writeObject(data);
            encoder.close();
            fos.close();
        }
        catch (IOException e){
            System.out.print("Something went wrong during serialization: " + e);
        }
    }

    // Saves a HashMap object to an XML files
    public void serializeMapToXML(String name) {

        try {
            String fileName = name + ".xml";
            FileOutputStream fos = new FileOutputStream(fileName);
            XMLEncoder encoder = new XMLEncoder(fos);
            encoder.setExceptionListener(new ExceptionListener() {
                @Override
                public void exceptionThrown(Exception e) {
                    System.out.println("Exception! :" + e.toString());
                }
            });
            encoder.writeObject(DataMap.getMap()); // Write HashMap from DataMap class
            encoder.close();
            fos.close();
        }

        catch (IOException c){
            System.out.print("Something went wrong during serialization.");
        }
    }

    // Loads an XML file that contains a hash map and then sets its to the current map in the MapData class
    public void deserializeMapFromXML(String name) throws FileNotFoundException {
        String fileName = name+".xml";
        XMLDecoder decoder = new XMLDecoder(
                new BufferedInputStream(
                        new FileInputStream(fileName)));

        HashMap<String, ClassData> newMap = new HashMap<>();
        newMap.put(name , (ClassData) decoder.readObject());
        DataMap.setMap(newMap); // Set to the hash map in DataMap class
        decoder.close();

    }
}

