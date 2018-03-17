package Animations;

import LightPanelSystem.LightPanelSystem;
import processing.core.PVector;

public class SineDistance implements Animation {
    private LightPanelSystem applet;
    PVector[] points;
    int redColor;
    int blueColor;

    //TODO: This one doesn't render live very well, but might make a really cool pre-rendered animated loop
    public SineDistance(LightPanelSystem applet) {
        this.applet = applet;
    }

    public void play() {
        float f = (float) (applet.frameCount * 0.1);
        float d;
        int count = points.length;
        applet.loadPixels();
        for(int x=0; x < applet.width; x++) {
            for(int y=0; y < applet.height; y++) {
                d = 0;
                for(int i=0; i<count; i++) {
                    d += applet.sin((float) (applet.dist(x, y, points[i].x, points[i].y)*0.08));
                }
                d /= points.length;

                applet.pixels[ x + y * applet.width] = applet.floor(d * 15 + f) % 7 == 0 ? redColor : blueColor;
            }
        }
        applet.updatePixels();
        for(int i=0; i<points.length; i++) {
            points[i].set(
                applet.width/2 + applet.width/2 * applet.sin((float)(applet.frameCount * i * 0.0011)) * applet.sin((float)(applet.frameCount*i*0.0017)),
                applet.width/2 + applet.width/2 * applet.sin((float)(applet.frameCount*i*0.0013)) * applet.sin((float)(applet.frameCount*i*0.0019))
            );
        }
    }

    public ColorMode getColorMode() {
        return ColorMode.RGB;
    }

    public void setup() {
        points = new PVector[11];
        for(int i=0; i < points.length; i++) {
            points[i] = new PVector();
        }
        redColor = applet.color(237,24, 81);
        blueColor = applet.color(33, 7, 62);
    }

    public void prepare() {

    }

    public void cleanup() {

    }
}