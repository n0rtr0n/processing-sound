package Animations;

import LightPanelSystem.LightPanelSystem;

public class SineWaving implements Animation {
    private LightPanelSystem applet;
    int h=0;

    public SineWaving(LightPanelSystem applet) {
        this.applet = applet;
    }

    public void play() {
        applet.background(255, 3, 95);
//        applet.background(0);
        float w = applet.width/270.0f;
//        applet.stroke(-1, 200);
        applet.stroke(0,200);
        applet.strokeWeight(12);
        for (int i = 0; i < 270; i += 8) {
            float l = 75 * applet.sin(applet.radians(i-h));
            float r =  4 * applet.sin(applet.radians(i+h));
            float p =  4 * applet.sin(applet.PI / 4 + applet.radians(i-h));
            applet.line(i * w, 0, i * w, applet.height/2 + l);
            applet.ellipse(i * w, applet.height/2+l, r, r);
            applet.line(i * w + 10, applet.height, i * w + 10, applet.height/2 - l);
            applet.ellipse(i * w + 10, applet.height/2 - l, p, p);
        }
        // never let h get too high
        if (h == 360) {
            h = 0;
        } else {
            h++;
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