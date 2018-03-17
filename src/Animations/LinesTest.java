package Animations;

import LightPanelSystem.LightPanelSystem;

public class LinesTest implements Animation {
    private LightPanelSystem applet;
    float i = 0f;
    float speed = 5f;

    public LinesTest(LightPanelSystem applet) {
        this.applet = applet;
    }

    public void play() {
        applet.background(0);
        i = (i % 360) + speed ;
        applet.stroke(255);
        applet.line(applet.width / 2, applet.height / 2, (applet.width / 2) + (applet.sin(applet.radians(i)) * 100), (applet.height / 2) - (applet.cos(applet.radians(i)) * 100) );
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