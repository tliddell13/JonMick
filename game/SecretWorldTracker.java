package game;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import org.jbox2d.common.Vec2;

public class SecretWorldTracker implements StepListener {
    //tracker for the secret world so coins can drop from the sky
    private final SecretWorld world;
    private boolean coinDrop = true;
    private final GameView view;
    public SecretWorldTracker(GameView view,SecretWorld world) {
        this.view = view;
        this.world = world;
    }

    @Override
    public void preStep(StepEvent stepEvent) {
        //ammo, health and score display
        view.setFireballCount(world.getFireballCount());
        view.setHealthCount(world.getJohn().getHealth());
        view.setScore(world.getJohn().getScore());
        view.repaint();
        if (coinDrop) {
            for (int x = 0; x < 25; x++) {
                int dropCoinX = (Math.random() <= 0.5) ? -30 : 30;
                Coin coin = new Coin(world);
                coin.setPosition(new Vec2(dropCoinX, 30));
                System.out.println("enter");
            }
            coinDrop = false;
        }
    }

    @Override
    public void postStep(StepEvent stepEvent) {

    }
}
