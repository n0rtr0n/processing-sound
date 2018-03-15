package Animations;

import LightPanelSystem.LightPanelSystem;

public class Blackout implements Animation {
    private LightPanelSystem applet;

    public Blackout(LightPanelSystem applet) {
        this.applet = applet;
    }

    public void play() {
        if (applet.whiteLatched == true) {
            applet.background(255, 255, 255);
        } else if (applet.redLatched == true) {
            applet.background(255,0,0);
        } else {
            applet.background(0,0,0);
        }
    }

    public ColorMode getColorMode() {
        return ColorMode.RGB;
    }

    public void setup() {

    }

    public void prepare() {

    }

    public void cleanup() {

    }
}