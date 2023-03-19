package bullscows;

public class BullsCows {
    public static void main(String[] args) {
        BullsCowsController controller = new BullsCowsController();
        GameView gameview = new GameView(controller);
        gameview.runGame();
    }
}
