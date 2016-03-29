package kz.test.spravochnik.helper;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import kz.test.spravochnik.SpravochnikApp;
import timber.log.Timber;

public class SerializerHelper {

    public static <T> T readObject(Class<T> cls, String fileName) throws IOException {
        T result = null;
        try {
            result = (T) new ObjectInputStream(SpravochnikApp.get().openFileInput(fileName)).readObject();
        } catch (Exception e) {
            Timber.e(e.toString());
        }
        return result;
    }

    public static void storeObject(Serializable object, String fileName) {
        try {
            new ObjectOutputStream(SpravochnikApp.get().openFileOutput(fileName, 0)).writeObject(object);
        } catch (IOException e) {
            Timber.d(Log.getStackTraceString(e));
        }
    }

    public static <T> T readObject(String fileName) {
        T result = null;
        try {
            result = (T) new ObjectInputStream(SpravochnikApp.get().openFileInput(fileName)).readObject();
        } catch (Exception e) {
            storeObject(null, fileName);
            Timber.e(e.toString());
        }
        return result;
    }
}
