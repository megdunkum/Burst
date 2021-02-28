package com.example.burst;

import android.Manifest;
import android.app.DirectAction;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import androidx.lifecycle.ViewModelProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private static final int PERMISSION_REQUEST_CODE = 200;
    static final int CAMERA_REQUEST = 1;
    private int priority = 1;
    Documentation current;
    private static final String IMAGE_DIRECTORY_NAME = "BURST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            // Create file
            FileOutputStream fos = openFileOutput("burst-report.txt", MODE_PRIVATE);

            // Write to file
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write("Hello world!");
            writer.close();

            // Read from file
            BufferedReader input = new BufferedReader(new InputStreamReader(openFileInput("burst-report.txt")));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(line + "\n");
            }
            String text = buffer.toString();

            Log.v("NOTICE", "Opened, wrote, and read!");
            Log.v("FILE CONTENTS", text);
        } catch (IOException e) {
            Log.v("ERROR", "could not open and write to file");
            e.printStackTrace();
        }

//TO_DO CHANGE NAME TO MATCH NEW UI
        /*
        FloatingActionButton camera = findViewById(R.id.fab);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    captureImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        */
//TO_DO CHANGE BUTTON TO MATCH UI
/*
        mDescriptionEnter = (Button)findViewById(R.id.button1);

        mButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
                saveDescription();
                //TO_DO Update view to next page
        	}
        });

 */
    }

    public void makeDocument(String date, String loc, String time)
    {
        current = new Documentation(date, time, loc);
    }

    public void addDescriptionItem(String description, int priority)
    {
        current.addDescriptionItem(description, priority);
    }

    public void addPhotoItem(String description, int priority)
    {
        current.addPhotoItem(description, priority);
    }

    public void createPDF() {
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(612, 7920, 1).create();
        PdfDocument.Page intro = document.startPage(pageInfo);

        Canvas canvas = intro.getCanvas();
        Paint paint = new Paint();
        canvas.drawPaint(paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(16);
        canvas.drawText("Hello world!", 100, 100, paint);
        canvas.drawText("Date of Incident: " + current.date, 100, 200, paint);
        canvas.drawText("Time of Incident: " + current.time, 100, 300, paint);
        canvas.drawText("Location of Incident: " + current.location, 100, 400, paint);
        canvas.drawText("Description:\n" + current.descriptions.get(0), 100, 500, paint);

        for (int i = 0; i < current.photoPaths.size(); i++) {
            try {
                File image = new File(current.photoPaths.get(i));
                ImageDecoder.Source source = ImageDecoder.createSource(image);
                Drawable drawable = ImageDecoder.decodeDrawable(source);
                drawable.draw(canvas);
            } catch (IOException e) {
                Log.v("ERROR", "error displaying images");
                e.printStackTrace();
            }

            canvas.drawText("\n" + current.descriptions.get(i+1), 100, 1000 + i*100, paint);
        }

        document.finishPage(intro);

        //PdfRenderer renderer = new PdfRenderer(document);
    }

    /*
     * The following function are used to capture and save images
     * void captureImage() -opens the camera and saves location of the photo
     * File getPictureFile() -creates a file instance for camera to save to
     * boolean checkCameraPermission() - returns true if camera permission is granted, false if not
     * void requestCameraPermission() - asks user for camera permissions
     *  boolean checkReadPermission() - returns true if read permission is granted, false if not
     * void requestReadPermission() - asks user for read permissions
     * boolean checkWritePermission() - returns true if write permission is granted, false if not
     * void requestWritePermission() - asks user for write permissions
     */

    //this function opens the camera to take a photo and saves the resulting photo to the
    //current documentation photoList at the current priority level
    private void captureImage() throws IOException {

        if (!checkCameraPermission()) {
            requestCameraPermission();
        }
        else {
            Uri relativePath = Uri.fromFile(getPictureFile());
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_FINISH_ON_COMPLETION, relativePath);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
            //TO_DO    current.addPhotoItem(relativePath.toString(),priority);
        }
    }
    //this function creates a file to store a camera image
    private File getPictureFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String pictureFile = "BURST_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(pictureFile,  ".jpg", storageDir);
        return image;
    }
    //this function checks the camera permissions
    private boolean checkCameraPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)
            return false;
        return true;
    }
    //this function requests the camera permissions
    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
    }
    //this function checks the read permissions
    private boolean checkReadPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            return false;
        return true;
    }
    //this function requests the read permissions
    private void requestReadPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);
    }
    //this function checks the write permissions
    private boolean checkWritePermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            return false;
        return true;
    }
    //this function requests the write permissions
    private void requestWritePermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}