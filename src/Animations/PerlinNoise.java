package Animations;

import LightPanelSystem.LightPanelSystem;
import processing.core.PVector;
import java.util.ArrayList;

public class PerlinNoise implements Animation {

    LightPanelSystem applet;

    float radius, alpha;

    ArrayList<Particle> particles_a;
    ArrayList<Particle> particles_b;
    ArrayList<Particle> particles_c;
    int nums = 70;
    int noiseScale = 300;

    public PerlinNoise(LightPanelSystem applet)
    {
        this.applet = applet;
    }

    // TODO: this seems to be causing memory issues
    public void play()
    {
        applet.noStroke();
        applet.smooth();
//        applet.background(21, 8, 50);
        for(int i = 0; i < nums; i++){
            radius = applet.map(i,0,nums,1,2);
            alpha = 0; //applet.map(i,0,nums,0,250);

//            applet.fill(69,33,124,alpha);
            applet.fill(69,33,124);
            particles_a.get(i).move();
            particles_a.get(i).display(radius);
            particles_a.get(i).checkEdge();

//            applet.fill(7,153,242,alpha);
            applet.fill(7,153,242);
            particles_b.get(i).move();
            particles_b.get(i).display(radius);
            particles_b.get(i).checkEdge();

//            applet.fill(255,255,255,alpha);
            applet.fill(255,255,255);
            particles_c.get(i).move();
            particles_c.get(i).display(radius);
            particles_c.get(i).checkEdge();
        }
    }

    public ColorMode getColorMode()
    {
        return ColorMode.RGB;
    }

    public void setup()
    {
        // TODO: create more even dispersion
        // TODO: reset after a minute or so
        particles_a = new ArrayList<Particle>();
        particles_b = new ArrayList<Particle>();
        particles_c = new ArrayList<Particle>();
        applet.background(21, 8, 50);
        for(int i = 0; i < nums; i++){
            particles_a.add(new Particle((int) applet.random(0, applet.width), (int) applet.random(0,applet.height), applet));
            particles_b.add(new Particle((int) applet.random(0, applet.width), (int) applet.random(0,applet.height), applet));
            particles_c.add(new Particle((int) applet.random(0, applet.width), (int) applet.random(0,applet.height), applet));
        }
    }

    public void prepare()
    {
        applet.background(21, 8, 50);
    }

    public void cleanup()
    {
        // TODO: clear particles
    }

    private class Particle {
        PVector dir;
        PVector vel;
        PVector pos;
        int x, y;
        float speed = 0.4f;
        float angle;
        LightPanelSystem applet;

        public Particle(int x, int y, LightPanelSystem applet)
        {
            this.x = x;
            this.y = y;
            this.applet = applet;
            dir = new PVector();
            vel = new PVector();
            pos = new PVector();
        }

        public void move()
        {
            angle = applet.noise(this.pos.x/noiseScale, this.pos.y/noiseScale) * applet.TWO_PI * noiseScale;
            dir.x = applet.cos(angle);
            dir.y = applet.sin(angle);
            vel = this.dir.copy();
            vel.mult(this.speed);
            pos.add(this.vel);
        }

        public void display(float radius)
        {
            applet.ellipse(this.pos.x, this.pos.y, radius, radius);
        }

        public void checkEdge()
        {
            if(this.pos.x > applet.width || this.pos.x < 0 || this.pos.y > applet.height || this.pos.y < 0) {
                this.pos.x = applet.random(50, applet.width);
                this.pos.y = applet.random(50, applet.height);
            }
        }
    }
}
