package Animations;

import LightPanelSystem.LightPanelSystem;

public class FastRainbowFade implements Animation {

    private ColorWheel colorWheel;
    private LightPanelSystem applet;

    public FastRainbowFade(LightPanelSystem applet, ColorWheel colorWheel)
    {
        this.applet = applet;
        this.colorWheel = colorWheel;
    }

    public void play()
    {
        if (colorWheel.getRed() >= 255)  {
            colorWheel.setRed(0);
        }  else  {
            colorWheel.setRed(colorWheel.getRed() + 1);
        }
        applet.background(colorWheel.getRed(), 255, 255);
    }
}
