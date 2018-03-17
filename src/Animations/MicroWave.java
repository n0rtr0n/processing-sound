package Animations;

import LightPanelSystem.LightPanelSystem;

public class MicroWave implements Animation {
    private LightPanelSystem applet;
    float theta = 0;
    float r = 30;

    public MicroWave(LightPanelSystem applet) {
        this.applet = applet;
    }

    public void play() {
//        applet.rotate(45);
//        applet.translate(0,-500);
        applet.noStroke();
        applet.fill(25,30);//background

        applet.rect(0,0,1500,1500);

        applet.noStroke();
        applet.fill(0,200,88);

        int k = 0;
        int l = 0;
//        for(int j = 0; j <= applet.height + 260; j += 37){
//            for(int i = 0; i <= applet.width + 260; i += 37){
        for(int j = 0; j <= applet.height + 260; j += 37){
            for(int i = 0; i <= applet.width + 260; i += 37){

                float x = r * applet.cos(theta + k);
                float y = r * applet.sin(theta + l);
                k += 0.5;
                l += 0.5;

                applet.noStroke();
                //color of dots
                applet.fill(0, applet.map(i,0,applet.height + 246,229,224), applet.map(i,10,applet.height+239,252,54));
                applet.ellipse(x+i,y+j,30,30);
            }
            k = 0;
            //i = 0;
            l = 0;
        }
        theta += 0.1;
    }

    public ColorMode getColorMode() {
        return ColorMode.RGB;
    }

    public void setup() {

    }

    public void prepare() {
        applet.background(255);
        applet.smooth();
        applet.frameRate(30);
        applet.strokeWeight(1);
    }

    public void cleanup() {

    }
}