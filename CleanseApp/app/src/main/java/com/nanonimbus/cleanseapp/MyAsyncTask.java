package com.nanonimbus.cleanseapp;

import android.content.Context;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

/**
 * Created by mattcorrente on 9/10/16.
 */



 class MyAsyncTask extends AsyncTask<MyTaskParams, Void, Void> {



    @Override
    protected Void doInBackground(MyTaskParams... params) {


        Set<String> mySet =  params[0].set;
        Context context =  params[0].context;


        URL url;
        InputStream is;
        File file;
        File parent;

        for(String theURL : mySet) {
            System.out.println("images to save " + theURL);
        }

        for(String theURL : mySet) {



            try {

                url = new URL(theURL);
                is = url.openStream();

                file = new File(context.getFilesDir(), theURL);
                parent = file.getParentFile();

                if(!parent.exists() && !parent.mkdirs()) {
                    file.mkdirs();
                    System.out.println("file did not exist");
                }


                OutputStream os = new FileOutputStream(file);

                //byte[] b = new byte[2048];
                byte[] b = new byte[10000000];

                int length;

                while ((length = is.read(b)) != -1) {
                    os.write(b, 0, length);
                }

                is.close();
                os.close();

                System.out.println("Successfully saved image " + theURL);


            }catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        try {
            if(params[0].progRef != null && params[0].progRef.isShowing()){
                params[0].progRef.dismiss();
                params[0].progRef = null;
            }
        } catch (final IllegalArgumentException e) {
            // Handle or log or ignore
        } catch (final Exception e) {
            // Handle or log or ignore
        } finally {
            params[0].progRef = null;
        }





        return null;
    }





    // doInBackground() et al.
}
