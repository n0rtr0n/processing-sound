package Animations;

import LightPanelSystem.LightPanelSystem;
import processing.core.PImage;

public class Dots implements Animation {
    PImage dot;
    private LightPanelSystem applet;

    public Dots(LightPanelSystem applet) {
        this.applet = applet;
    }

    public void play() {
        applet.background(0);

        // Draw the image, centered at the mouse location
        double dotSize = applet.width * 0.2;
        applet.image(dot, (float) (applet.mouseX - dotSize / 2) , (float) (applet.mouseY - dotSize / 2), (float) dotSize, (float) dotSize);
    }

    public ColorMode getColorMode() {
        return ColorMode.RGB;
    }

    public void setup() {
        dot = applet.loadImage("color-dot.png");
    }

    public void prepare()
    {

    }

    public void cleanup() {

    }
}