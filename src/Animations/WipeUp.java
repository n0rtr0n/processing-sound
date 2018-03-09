package Animations;

import LightPanelSystem.LightPanelSystem;

public class WipeUp implements Animation{

    private boolean wipeUpInitialized = true;

    LightPanelSystem applet;

    int maxX, maxY;

    float x = 0.0f;
    float y = 0.0f;
    float speed = 10.4f;


    public WipeUp( LightPanelSystem applet)
    {
        this.applet = applet;
    }

    public void prepare()
    {
        x = 0.0f;
        y = 0.0f;
        maxX = applet.width + applet.height;
        maxY = applet.width + applet.height;
    }

    public void play()
    {
        applet.background(0,0,0);
        applet.noStroke();
        applet.fill(255,0,0);
        if (wipeUpInitialized) {
            //move up shape
            applet.rect(x, y, applet.height, applet.height);
            if (x > applet.width + applet.height) {
                wipeUpInitialized = false;
            }
            x += speed;
        }
    }

    public void initiateWipeUp()
    {
        wipeUpInitialized = true;
    }
}
