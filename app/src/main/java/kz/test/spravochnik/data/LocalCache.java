package kz.test.spravochnik.data;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import kz.test.spravochnik.SpravochnikApp;
import kz.test.spravochnik.data.model.cinema.Cinema;
import kz.test.spravochnik.data.model.hospital.Hospital;
import kz.test.spravochnik.helper.SerializerHelper;
import timber.log.Timber;

public class LocalCache {

    private static LocalCache INSTANCE = null;
    private static final String HOSPITAL_FILE = "hospitals";
    private static final String CINEMA_FILE = "cinemas";

    static {
        INSTANCE = new LocalCache();
    }

    private LocalCache() {
    }

    public static LocalCache getInstance() {
        return INSTANCE;
    }

    private void saveToFile(String fileName, String text) {
        try {
            new PrintWriter(fileName).println(text);
        } catch (FileNotFoundException e) {
            Timber.e(Log.getStackTraceString(e));
        }
    }

    private String readFromFile(String fileName) {
        IOException ioException;
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            try {
                for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                    sb.append(line);
                    sb.append('\n');
                }
                bufferedReader.close();
            } catch (IOException iOException) {
                ioException = iOException;
                Timber.e(Log.getStackTraceString(ioException));
                return sb.toString();
            } catch (Throwable th) {
                bufferedReader.close();
            }
        } catch (IOException iOException) {
            ioException = iOException;
            Timber.e(Log.getStackTraceString(ioException));
            return sb.toString();
        }
        return sb.toString();
    }

    public void clear() {
        List<String> fileList = new ArrayList();
        fileList.add(HOSPITAL_FILE);
        fileList.add(CINEMA_FILE);
        for (String item : fileList) {
            Timber.d("Deleting file %s, result: %b", item, SpravochnikApp.get().deleteFile(item));
        }
    }

    public void storeHospitals(List<Hospital> hospitalsWrapper) {
        SerializerHelper.storeObject((Serializable) hospitalsWrapper, HOSPITAL_FILE);
    }

    public List<Hospital>  readHospitals() {
        return SerializerHelper.readObject(HOSPITAL_FILE);
    }

    public void storeCinemas(List<Cinema> cinemasWrapper) {
        SerializerHelper.storeObject((Serializable) cinemasWrapper, CINEMA_FILE);
    }

    public List<Cinema> readCinemas() {
        return SerializerHelper.readObject(CINEMA_FILE);
    }

}
