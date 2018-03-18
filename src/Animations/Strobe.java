package Animations;

import LightPanelSystem.LightPanelSystem;

public class Strobe implements Animation {
    private LightPanelSystem applet;

    // this is how many flashes per second
    float speed = 30;
    int msLastSwitch = 0;
    int color = 0;

    public Strobe(LightPanelSystem applet) {
        this.applet = applet;
    }

    public void play() {
        if (applet.millis() > (msLastSwitch + (1000 / speed) )) {
            msLastSwitch = applet.millis();
            if (color == 0) {
                color = 255;
            } else {
                color = 0;
            }
        }
        applet.background(color);
    }

    public ColorMode getColorMode() {
        return ColorMode.RGB;
    }

    public void setup() {

    }

    public void prepare() {
        msLastSwitch = applet.millis();
    }

    public void cleanup() {

    }
}