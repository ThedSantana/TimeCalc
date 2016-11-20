package com.example.clindeqeuist.timecalculator.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EntryCollection
{

    private List<Entry> entries = new ArrayList<>();


    public List<Entry> getEntries()
    {
        return entries;
    }


    public void saveEntries(String filename, Context context)
    {
        try (FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE))
        {
            Gson gson = buildGson();
            String json = gson.toJson(entries);

            outputStream.write(json.getBytes("UTF-8"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public void loadEntries(String filename, Context context)
    {
        try (FileInputStream inputStream = context.openFileInput(filename))
        {
            String json = IOUtils.toString(inputStream, "UTF-8");

            Gson gson = buildGson();
            entries = gson.fromJson(json, new TypeToken<ArrayList<Entry>>(){}.getType());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    @NonNull
    private Gson buildGson()
    {
        return new GsonBuilder().create();
    }

}
