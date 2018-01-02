package fr.iutmindfuck.qcmiutlyon1.services;

import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileServices {

    private Context context;

    public FileServices(Context context) {
        this.context = context;
    }

    public void saveFile(String data, String savePath) {
        File file = new File(context.getFilesDir(), savePath);

        try
        {
            new FileWriter(file).write(data);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
