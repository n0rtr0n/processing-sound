package Animations;

import LightPanelSystem.LightPanelSystem;
import processing.core.PImage;

public class FireAnimation implements Animation {

    private PImage fireImage;

    private LightPanelSystem applet;

    public FireAnimation(LightPanelSystem applet)
    {
        fireImage = applet.loadImage("flames.jpeg");
        this.applet = applet;
    }


    public void setup(String image)
    {
        fireImage = applet.loadImage(image);
    }

    public void play()
    {

    }

    public void draw()
    {
        // Scale the image so that it matches the width of the window
        int imHeight = fireImage.height * applet.width / fireImage.width;

        // Scroll down slowly, and wrap around
        float speed = 0.05f;
        float y = (applet.millis() * -speed) % imHeight;

        // Use two copies of the image, so it seems to repeat infinitely
        applet.image(fireImage, 0, y, applet.width, imHeight);
        applet.image(fireImage, 0, y + imHeight, applet.width, imHeight);
    }

    public ColorMode getColorMode()
    {
        return ColorMode.RGB;
    }

    public void setup()
    {

    }

    public void cleanup()
    {

    }
}
