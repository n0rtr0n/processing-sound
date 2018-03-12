package Animations;

import LightPanelSystem.LightPanelSystem;

public class LongRainbowFade implements Animation{

    private ColorWheel colorWheel;
    private LightPanelSystem applet;

    public LongRainbowFade(LightPanelSystem applet, ColorWheel colorWheel)
    {
        this.applet = applet;
        this.colorWheel = colorWheel;
    }

    public void play()
    {
        if (applet.msSince(colorWheel.getLastColorChangeAt()) > 150) {
            if (colorWheel.getRed() >= 255)  {
                colorWheel.setRed(0);
            }  else  {
                colorWheel.setRed(colorWheel.getRed() + 1);
            }
            colorWheel.setLastColorChangeAt(applet.millis());
        }
        applet.background(colorWheel.getRed(), 255, 255);
    }

    public ColorMode getColorMode()
    {
        return ColorMode.HSV;
    }

    public void setup()
    {

    }

    public void cleanup()
    {

    }
}
