package Animations;

import LightPanelSystem.LightPanelSystem;

public class RainbowCircles implements Animation {
    private LightPanelSystem applet;

    float baseHue = 0f;
    float hue = 0f;
    float hueWidth = 2f;
    float speed = 12f;

    float xOrigin, yOrigin;
    public RainbowCircles(LightPanelSystem applet) {
        this.applet = applet;
    }

    public void play() {
        applet.loadPixels();
        for (int x = 0; x < applet.width; x++) {
            // Loop through every pixel row
            for (int y = 0; y < applet.height; y++) {
                // Use the formula to find the 1D location
                int loc = x + y * applet.width;
                hue = (float) (((Math.sqrt( Math.pow(xOrigin - x, 2) + Math.pow(yOrigin - y, 2)) * 4f) - baseHue) % 360);
                applet.pixels[loc] = applet.color(hue, 255, 255);
            }
        }
        baseHue += speed;

        if (baseHue >= 360) {
            baseHue = 0;
        }
        applet.updatePixels();
    }

    public ColorMode getColorMode() {
        return ColorMode.HSB;
    }

    public void setup() {
        xOrigin = applet.width / 2;
        yOrigin = applet.height / 2;
    }

    public void prepare() {
        applet.background(0);
        applet.noStroke();
        applet.noFill();
    }

    public void cleanup() {

    }
}