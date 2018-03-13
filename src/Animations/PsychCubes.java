package Animations;

import LightPanelSystem.LightPanelSystem;

public class PsychCubes implements Animation {
    private LightPanelSystem applet;
    int div=16;
    int t=0;
    float du, r;

    public PsychCubes(LightPanelSystem applet) {
        this.applet = applet;
    }

    public void play() {
        t++;
        applet.background(t%256, 255, 192+64 * applet.cos(t * applet.PI / 12));

        applet.translate(applet.width/2, applet.height/2, -du);

        applet.rotateZ(t*applet.PI/360);
        for (int k=0; k<14; k++) {
            applet.rotateZ(t * applet.PI / 1200);

            for (int i = 0; i < div; i++) {
                applet.rotateZ(2 * applet.PI / div);

                applet.pushMatrix();
                applet.translate(k * r, (float) (1.2 * r + k * k * r * 0.25), 0);

                //立方体
                applet.pushMatrix();
                applet.rotateY(k * 2 * applet.PI / div + t * applet.PI / 48);
                applet.fill(i * 255 / div, 216, 216, 128);
                applet.strokeWeight(du / 384);
                applet.stroke(255, 48);
                applet.box((float) (r * 1.56 + k * r / 2.6));
                applet.popMatrix();

                //直線
                applet.pushMatrix();
                applet.translate(0, 0, du / 4);
                applet.strokeWeight(du / 280);
                applet.stroke(255, 56);
                applet.line(0, 0, 0, -16 * du);
                applet.popMatrix();

                applet.popMatrix();
            }
        }
    }

    public ColorMode getColorMode() {
        return ColorMode.RGB;
    }

    public void setup() {
        float du = applet.max(applet.width, applet.height)/2;
        float r = du/16;
    }

    public void prepare() {

    }

    public void cleanup() {

    }
}