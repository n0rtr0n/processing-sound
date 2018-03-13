package Animations;

import LightPanelSystem.LightPanelSystem;
import processing.video.Capture;

public class CameraTest implements Animation {
    private LightPanelSystem applet;

    private Capture cam;

    public CameraTest(LightPanelSystem applet) {
        this.applet = applet;
    }

    public void play() {
        if(cam.available()) {
            cam.read();
            //cam.loadPixels();
         }
         applet.image(cam, 0, 0);
    }

    public ColorMode getColorMode() {
        return ColorMode.RGB;
    }

    public void setup() {
        //TODO: make this work if a webcam is not plugged in
        cam = new Capture(applet, Capture.list()[1]);
    }

    public void prepare()
    {
        cam.start();
    }

    public void cleanup() {
        cam.stop();
    }
}