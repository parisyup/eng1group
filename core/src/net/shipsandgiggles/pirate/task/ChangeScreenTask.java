package net.shipsandgiggles.pirate.task;

import com.badlogic.gdx.utils.Timer;
import net.shipsandgiggles.pirate.PirateGame;
import net.shipsandgiggles.pirate.screen.ScreenType;

public class ChangeScreenTask extends Timer.Task {

    /** changing the screen*/

    private final ScreenType screenType;

    public ChangeScreenTask(ScreenType screenType) {
        this.screenType = screenType;
    }

    @Override
    public void run() {
        PirateGame.get().changeScreen(this.screenType);
    }
}