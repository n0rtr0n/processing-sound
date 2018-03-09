package Animations;

import LightPanelSystem.LightPanelSystem;

public class WipeUp implements Animation{

    private boolean wipeUpInitialized = true;


    LightPanelSystem applet;

    public WipeUp( LightPanelSystem applet)
    {
        this.applet = applet;
    }

    public void play()
    {
        if (wipeUpInitialized) {
            //move up shape
        }
    }

    public void initiateWipeUp()
    {
        wipeUpInitialized = true;
    }
}
