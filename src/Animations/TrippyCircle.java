package Animations;

import LightPanelSystem.LightPanelSystem;

public class TrippyCircle implements Animation {

    private LightPanelSystem applet;
    int num = 25, frames = 90;
    float theta;

    public TrippyCircle(LightPanelSystem applet) {
        this.applet = applet;
    }

    public void play() {
        applet.background(20);
        applet.stroke(240);
        applet.noFill();
        for (int i=0; i < num; i++) {
            float sz = i*35;
            float sw = applet.map(applet.sin(theta + applet.TWO_PI / num * i), -1, 1, 1, 16);
            applet.strokeWeight(sw);
            applet.ellipse(applet.width / 2, applet.height / 2, sz, sz);
        }
        theta += applet.TWO_PI / frames;
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