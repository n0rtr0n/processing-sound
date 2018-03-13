package Animations;

import java.util.ArrayList;

public interface Animation {
    public void play();

    public ColorMode getColorMode();

    public void prepare();

    public void setup();

    public void cleanup();

    // TODO: register animation keys
    // TODO: display driver
}
