package Animations;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;

public class FireAnimation extends PApplet implements Animation {

    private ArrayList<Drawable> drawables;

    public ArrayList<Drawable> getDrawableObjects()
    {
        return drawables;
    }

    public void play()
    {

    }

//    public void draw()
//    {
//        // Scale the image so that it matches the width of the window
//        int imHeight = fireImage.height * width / fireImage.width;
//
//        // Scroll down slowly, and wrap around
//        float speed = 0.05f;
//        float y = (millis() * -speed) % imHeight;
//
//        // Use two copies of the image, so it seems to repeat infinitely
//        image(fireImage, 0, y, width, imHeight);
//        image(fireImage, 0, y + imHeight, width, imHeight);
//    }

}
