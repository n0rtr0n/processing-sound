package Animations;

import java.util.ArrayList;
import LightPanelSystem.LightPanelSystem;

public class WipeUp implements Animation{

    private boolean wipeUpInitialized = true;

    LightPanelSystem applet;

    int maxX, maxY;

    float x = 0.0f;
    float y = 0.0f;
    float speed = 15f;
    int msSinceLastWipe = 0;
    int currentColorIndex = 0;
    int nextColorIndex = 1;
    ColorWheel currentColor;
    ColorWheel nextColor;
    ArrayList<ColorWheel> colors;

    public WipeUp( LightPanelSystem applet)
    {
        this.applet = applet;
        colors = new ArrayList<ColorWheel>();
    }

    public void prepare()
    {
        x = 0.0f;
        y = 0.0f;
        currentColorIndex = 0;
        maxX = applet.width + applet.height;
        maxY = applet.width + applet.height;
        msSinceLastWipe = 0;
        colors.add((new ColorWheel()).setRed(255).setBlue(0).setGreen(0));
        colors.add((new ColorWheel()).setRed(0).setBlue(255).setGreen(0));
        colors.add((new ColorWheel()).setRed(0).setBlue(0).setGreen(255));
    }

    public void play()
    {
        currentColor = colors.get(currentColorIndex);
        nextColor = colors.get(nextColorIndex);

        applet.background(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue());
        applet.noStroke();
        applet.fill(255,0,0);


        applet.rect(0, y, applet.height + x, applet.height);
        if (x > applet.width + applet.height) {
            if ((currentColorIndex + 1) > colors.size()) {
                currentColorIndex = 0;
            }
            nextColorIndex = currentColorIndex + 1;
        }
        x += speed;


//        if (wipeUpInitialized) {
//            //move up shape
//            applet.rect(0, y, applet.height + x, applet.height);
//            if (x > applet.width + applet.height) {
//                wipeUpInitialized = false;
//            }
//            x += speed;
//        }
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

    public void initiateWipeUp()
    {
        wipeUpInitialized = true;
    }
}
