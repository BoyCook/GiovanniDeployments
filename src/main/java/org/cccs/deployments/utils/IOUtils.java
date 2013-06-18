package org.cccs.deployments.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import static java.lang.String.format;

/**
 * User: boycook
 * Date: Aug 6, 2010
 * Time: 1:26:28 PM
 */
public final class IOUtils {

    private static final Logger log = LoggerFactory.getLogger(IOUtils.class);

    public static void writeFile(String file, Object obj) {
        try {
            writeFileToDisk(file, obj);
        } catch (IOException e) {
            log.error(format("There was an error writing [%s] to disk", file));
            System.out.println(e.getMessage());
        }
    }

    public static Object readFile(String file) {
        try {
            return readFileFromDisk(file);
        } catch (IOException e) {
            log.error(format("Sorry file [%s] does not exist", file));
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            log.error(format("Sorry file [%s] has unknown class type", file));
            System.out.println(e.getMessage());
        } catch (ClassCastException e) {
            log.error(format("Sorry file [%s] is of wrong class type", file));
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static void writeFileToDisk(String file, Object obj) throws IOException {
        FileOutputStream f = new FileOutputStream(file);
        ObjectOutputStream o = new ObjectOutputStream(f);
        o.writeObject(obj);
    }

    private static Object readFileFromDisk(String file) throws IOException, ClassNotFoundException {
        FileInputStream f = new FileInputStream(file);
        ObjectInputStream o = new ObjectInputStream(f);
        return o.readObject();
    }

    private static Object readResource(String file) throws IOException, ClassNotFoundException {
        ObjectInputStream o = new ObjectInputStream(IOUtils.class.getResourceAsStream(file));
        return o.readObject();
    }
}
