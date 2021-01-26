package help;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class XMLHandler {

    public static void serialize(Object serObj, String path) {
        XMLEncoder enc = null;
        try {
            enc = new XMLEncoder(new FileOutputStream(path));

            enc.writeObject( serObj );
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (enc != null)
                enc.close();
        }
    }

    public static Object deserialize(String path) {
        XMLDecoder dec = null;
        Object rObj = null;
        try {
            dec = new XMLDecoder(new FileInputStream(path));
            rObj = dec.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (dec != null)
                dec.close();
        }
        return rObj;
    }

}

