package game;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import org.jbox2d.common.Vec2;

public class YouWonTracker implements StepListener {
    private final YouWon win;
    private boolean move = true;
    public YouWonTracker(YouWon win) {
        this.win = win;
    }
    @Override
    public void preStep(StepEvent stepEvent) {

    }

    @Override
    public void postStep(StepEvent stepEvent) {
        if (move) {
            win.MoveYouWon();
            if (win.getYouWon().getPosition().y < -20) {
                new CreateHighScoresFile(win.getGame().getView().getScore());
                win.getYouWon().setPosition(new Vec2(0, 0));
                move = false;
            }
        }

    }
}
