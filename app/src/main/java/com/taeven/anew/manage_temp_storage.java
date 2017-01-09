package com.taeven.anew;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.Normalizer2;
import android.util.Log;

import org.apache.commons.codec.binary.Base64;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by vaibhav on 6/1/17.
 */
public class manage_temp_storage {
    private mentor[] person;
    private Context context;
    private String file_name;

    public manage_temp_storage(Context context)
    {
        person = new mentor[50];
        file_name = "temp_storage.data";
        this.context = context;

    }
    public void save_data(testing toSave)
    {
        try {

            ObjectOutputStream objectOutputStream = after_login.outputStream;
            objectOutputStream.writeObject(toSave);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public mentor[] get_data()
    {
        try {
            Log.d("here","again");


            FileInputStream fileInputStream=context.openFileInput(file_name);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Log.d("here","again2");
            int i=0;
            while(true)
            {
                Log.d("here","");
                testing tmp;
                tmp=(testing)objectInputStream.readObject();

                byte[] b= Base64.decodeBase64(tmp.image.getBytes());
                Bitmap decodeByteArray = BitmapFactory.decodeByteArray(b,0,b.length);
                person[i]= new mentor(tmp.name,decodeByteArray);
                if (person[i]==null)
                    break;
                i++;

//                objectInputStream.close();

            }

            objectInputStream.close();
            fileInputStream.close();
        }
        catch (EOFException e)
        {
            return person;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return person;

    }
}
