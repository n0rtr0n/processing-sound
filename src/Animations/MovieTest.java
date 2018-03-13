package Animations;

import LightPanelSystem.LightPanelSystem;
import processing.video.Movie;

public class MovieTest implements Animation {

    Movie movie;
    LightPanelSystem applet;

    public MovieTest (LightPanelSystem applet)
    {
        this.applet = applet;

    }

    public void play()
    {
        if (movie.available()) {
            movie.read();
        }
        applet.image(movie, 0, (int) ((.5 * applet.height) - (movie.height / 2)));
    }

    public ColorMode getColorMode()
    {
        return ColorMode.RGB;
    }

    public void setup()
    {
        movie = new Movie(this.applet, "RoadTrip.mp4");
    }

    public void prepare()
    {
        movie.loop();
    }

    public void cleanup()
    {
        movie.stop();
    }
}
