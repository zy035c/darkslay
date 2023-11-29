package actions;
import dungeons.AbstractDungeon;
import java.util.ArrayList;

/******************************************************************************
 * @author Ziyi Chen
 * @since Jun 1 2023
 * 
 * @doc
 * Maintains a queue, ArrayList<AbstractGameAction> actions. GameActionManager 
 * executes game actions in the queue. The queue is First-In-First-Out.
 * 
 * Also a state machine with the flag phase : Phase indicating if there are more
 * actions waiting the queue. When phase == EXECUTING_ACTIONS:
 * 1. Front-end is not interative.
 * 2. Write to the status of objects are prohibited. 
 * 
 * An action might trigger other actions. They are also Subjects that notify
 * Observers. Observers then add more actions into the queue. 
 ******************************************************************************/
public class GameActionManager {

    public boolean turnHasEnded;
    public static int damageReceivedThisTurn;
    public static int damageReceivedThisCombat;
    public ArrayList<AbstractGameAction> actions = new ArrayList<>();
    public AbstractGameAction currentAction = null;
    public Phase phase;

    public GameActionManager() {
        this.phase = Phase.WAITING_ON_USER;
    }

    /**
     * Bottom means oldest, least recent action. 
     * @param action
     */
    public void addToBottom(AbstractGameAction action) {
        actions.add(0, action);
    }

    /**
     * Most recent actions are on the top.
     * @param action
     */
    public void addToTop(AbstractGameAction action) {
        System.out.println("Adding action: "+action.getClass().getSimpleName());
        actions.add(action);
    }

    public void endTurn() {
        turnHasEnded = true;
        // actions.clear();
        // this.phase = Phase.WAITING_ON_USER;
    }

    public void startTurn() {
        turnHasEnded = false;
    }

    /**
     * Execute the least recent action in the queue. 
     */
    public void executeAction() {

        // if currentAction.isDone == true
        if (actions.size() <= 0) {
            System.out.println("[ACTION MANAGER] executeAction called but no action in queue.");
            this.phase = Phase.WAITING_ON_USER;
            return;
        }
        currentAction = actions.get(0);
        System.out.println("[ACTION MANAGER] executeAction: " + currentAction.getClass().getSimpleName());
        currentAction.update();
        actions.remove(0);

        if (actions.size() <= 0) {
            this.phase = Phase.WAITING_ON_USER;
        } else {
            this.phase = Phase.EXECUTING_ACTIONS;
        }
        System.out.println("[ACTION MANAGER] Previous Action done. " + this.phase);
    }

    /**
     * Execute actions until the queue is empty.
     */
    public void emptyQueue() {
        if (this.actions.isEmpty()) {
            this.phase = Phase.WAITING_ON_USER;
            return;
        }
        this.phase = Phase.WAITING_ON_USER;
        while (actions.size() > 0) {
            executeAction();
        }
    }

    /**
     * Jump the queue. Insert an action and execute immediately. 
     */
    public void queueJump(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
        AbstractDungeon.actionManager.executeAction();
    }

    public enum Phase {
        WAITING_ON_USER, EXECUTING_ACTIONS;
    }

}
