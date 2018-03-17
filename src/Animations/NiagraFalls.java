package Animations;

import LightPanelSystem.LightPanelSystem;
import processing.core.PVector;

import java.util.ArrayList;

public class NiagraFalls implements Animation {
    private LightPanelSystem applet;
    ArrayList<Particle> allParticles;
    int globalHue = 0;
    int spawnPerFrame = 3;
    int mouseSize = 100;
    float d;
    PVector vec;

    public NiagraFalls(LightPanelSystem applet) {
        this.applet = applet;
    }

    public void play() {
//        applet.background(0, 0, 0);
        //applet.rect(0, 0, applet.width, applet.height);

        for (int i = 0; i < spawnPerFrame; i++) {
            allParticles.add(new Particle((int) applet.random(applet.width), 0, globalHue, applet));
        }

        for (int i = allParticles.size() - 1; i > -1; i--) {
            allParticles.get(i).acc.add(new PVector(0, (float)(allParticles.get(i).size * 0.01)));

            // Quick check to avoid calculating distance if possible.
            if (applet.abs(allParticles.get(i).pos.x - applet.mouseX) < mouseSize) {
                d = applet.dist(applet.mouseX, applet.mouseY, allParticles.get(i).pos.x, allParticles.get(i).pos.y);
                if (d < mouseSize) {
                    vec = new PVector(applet.mouseX, applet.mouseY - mouseSize);
                    vec.sub(new PVector(allParticles.get(i).pos.x, allParticles.get(i).pos.y));
                    vec.normalize();
                    allParticles.get(i).vel.add(vec);

                    allParticles.get(i).hue += 1.5;
                    if (allParticles.get(i).hue > 360) {
                        allParticles.get(i).hue = 0;
                    }
                }
            }

            allParticles.get(i).vel.add(allParticles.get(i).acc);
            allParticles.get(i).pos.add(allParticles.get(i).vel);
            allParticles.get(i).acc.mult(0);

            applet.stroke(allParticles.get(i).hue, 360, 360);
            applet.strokeWeight(allParticles.get(i).size);
            applet.line(allParticles.get(i).lastPos.x, allParticles.get(i).lastPos.y, allParticles.get(i).pos.x, allParticles.get(i).pos.y);

            allParticles.get(i).lastPos.set(allParticles.get(i).pos.x, allParticles.get(i).pos.y);

            if (allParticles.get(i).pos.y > applet.height || allParticles.get(i).pos.x < 0 || allParticles.get(i).pos.x > applet.width) {
                allParticles.remove(i);
            }
        }

        globalHue += 0.15;
        if (globalHue > 360) {
            globalHue = 0;
        }
    }

    public ColorMode getColorMode() {
        return ColorMode.HSB;
    }

    public void setup() {
        allParticles = new ArrayList<Particle>();
    }

    public void prepare() {
        applet.background(0, 0, 0);
    }

    public void cleanup() {

    }

    private class Particle {
        public PVector lastPos;
        public PVector vel;
        public PVector pos;
        public PVector acc;
        public float size;
        public int x, y, hue;
        LightPanelSystem applet;

        public Particle(int x, int y, int hue, LightPanelSystem applet)
        {
            this.x = x;
            this.y = y;
            this.applet = applet;
            this.lastPos = new PVector(this.x, this.y);
            this.pos = new PVector(x, y);
            this.vel = new PVector(0, 0);
            this.acc = new PVector(0, 0);
            this.size = applet.random(2, 20);
            this.hue = hue;
        }
    }
}

/*
var allParticles = [];
var globalHue = 0;
var spawnPerFrame = 3;
var mouseSize = 100;


function Particle(x, y) {
  this.lastPos = new p5.Vector(x, y);
  this.pos = new p5.Vector(x, y);
  this.vel = new p5.Vector(0, 0);
  this.acc = new p5.Vector(0, 0);
  this.size = random(2, 20);
  this.h = globalHue;
}


function setup() {
  createCanvas(windowWidth, windowHeight);
  colorMode(HSB, 360);

  mouseX = width/2;
  mouseY = height/2;
}


function draw() {
  noStroke();
  fill(0, 5);
  rect(0, 0, width, height);

  for (var i = 0; i < spawnPerFrame; i++) {
  	allParticles.push(new Particle(random(width), 0));
  }

  for (var i = allParticles.length-1; i > -1; i--) {
    allParticles[i].acc.add(new p5.Vector(0, allParticles[i].size*0.01));

    // Quick check to avoid calculating distance if possible.
    if (abs(allParticles[i].pos.x-mouseX) < mouseSize) {
      d = dist(mouseX, mouseY, allParticles[i].pos.x, allParticles[i].pos.y);
      if (d < mouseSize) {
        var vec = new p5.Vector(mouseX, mouseY-mouseSize);
        vec.sub(new p5.Vector(allParticles[i].pos.x, allParticles[i].pos.y));
        vec.normalize();
        allParticles[i].vel.add(vec);

        allParticles[i].h += 1.5;
        if (allParticles[i].h > 360) {
          allParticles[i].h = 0;
        }
      }
    }

    allParticles[i].vel.add(allParticles[i].acc);
    allParticles[i].pos.add(allParticles[i].vel);
    allParticles[i].acc.mult(0);

    stroke(allParticles[i].h, 360, 360);
    strokeWeight(allParticles[i].size);
    line(allParticles[i].lastPos.x, allParticles[i].lastPos.y,
         allParticles[i].pos.x, allParticles[i].pos.y);

    allParticles[i].lastPos.set(allParticles[i].pos.x, allParticles[i].pos.y);

    if (allParticles[i].pos.y > height || allParticles[i].pos.x < 0 || allParticles[i].pos.x > width) {
      allParticles.splice(i, 1);
    }
  }

  globalHue += 0.15;
  if (globalHue > 360) {
    globalHue = 0;
  }
}
 */