package com.example.burst;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

public class Documentation {

    //text
    public String date;
    private String location;
    private String time;
    private List<String> descriptions;
    private List<Integer> descriptionLevels;
    private List<String> photoPaths;
    private List<Integer> photoLevels;

    //Level 1 is general damage
    //Level 2 is pipe damage
    //Level 3 is Wall Damage
    //Level 4 is Floor Damage
    //Level 5 is Ceiling Damage
    //Level 6 is other structural
    //Level 7 is other items
    Documentation(String date, String time, String location)
    {
        descriptions = new ArrayList<String>();
        photoPaths = new ArrayList<String>();
        photoLevels = new ArrayList<Integer>();
        descriptionLevels = new ArrayList<Integer>();
        this.date = date;
        this.location = location;
        this.time = time;
    }

    public void addPhotoItem(String photoPath, int priority) {
        photoLevels.add(priority);
        photoPaths.add(photoPath);
    }
    public void addDescriptionItem(String description, int priority) {
        descriptions.add(description);
        photoLevels.add(priority);
    }
    //This method creates the document containing
    public Document buildDocument()
    {
        return null;
    }
}
