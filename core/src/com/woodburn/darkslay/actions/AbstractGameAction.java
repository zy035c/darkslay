package actions;
import cards.DamageInfo;
import core.AbstractPlayer;
import dungeons.AbstractDungeon;
import core.AbstractCreature;

/******************************************************************************
 * @author Ziyi Chen
 * @since Jun 1 2023
 * @doc
 * 
 * GameAction is an abstraction of a single or a series of operations in the 
 * game. 
 * 
 * These operations update the state (attributes) of an entity (an 
 * AbstractCreature in most cases) and probably can be mapped to certain 
 * animations. I will use “state update” to refer to the update of state, for 
 * instance, takeDamage() of a Player. 
 * 
 * These operations themselves are state machines because they require a certain 
 * amount of time to complete. Sometimes, even after the state update is 
 * complete, the thread that handles this job should still be blocked, waiting 
 * for the thread of animations to finish.
 * 
 * More than that, a player plays a card. As a result, Its use() function adds 
 * some GameActions to the queue of GameActionManager. The queue then starts to 
 * process these actions. It takes out the first action and calls its update().
 *  
 * The update() method of the GameAction calls some state update methods. These 
 * methods scatter in game entity classes.  
 * 
 * In the Observer Pattern, Subjects notify Observers. notify() are usually 
 * written in the form of triggerOnWhatever(). An example is 
 * triggerOnEndOfPlayerTurn() in class AbstractCard above.
 *  
 * Observers classes usually are game entities such as Rings, Buffs and Cards. 
 * They are instances of classes that inherit from the Observer abstract class, 
 * and the implementation of notify() is different. (a) If there is no 
 * implementation in this child class, go to the same-name method in abstract 
 * parent class. (b) This game object has its own implementation to respond to 
 * this state update. 
 *
 * Finally, they may add new GameActions to the GameActionManager queue. Both
 * GameActions and GameActionManager keep records of their state as a state 
 * machine. They ensure that GameActions are executed one after the other and 
 * that game data is updated step by step. Otherwise, concurrent multiple writes
 * would lead to a race condition.
 * 
 *******************************************************************************/
public abstract class AbstractGameAction {

    public AbstractCreature target;
    public int amount;
    public ActionType actionType;
    public AbstractCreature source;
    public AbstractPlayer p;
    public boolean isDone;
    public AttackEffect attackEffect;

    public DamageInfo.DamageType damageType;

    protected void setValues(AbstractCreature target, AbstractCreature source, int amount) {
        this.target = target;
        this.source = source;
        this.amount = amount;
    }

    protected void setValues(AbstractCreature target, DamageInfo info) {
        this.source = info.owner;
        this.target = target;
        this.amount = 0;
    }

    /**
     * enum class for various types of visual effect 
     */
    public enum AttackEffect
    {
        BLUNT_LIGHT,
        BLUNT_HEAVY,
        SLASH_DIAGONAL, SMASH,
        SLASH_HEAVY, SLASH_HORIZONTAL,
        SLASH_VERTICAL,
        NONE, FIRE, POISON,
        SHIELD, LIGHTNING;
    }

    // 
    public enum ActionType
    {
        BLOCK,
        POWER,
        CARD_MANIPULATION,
        DAMAGE, DEBUFF,
        DISCARD, DRAW, EXHAUST,
        HEAL, ENERGY, TEXT, USE,
        CLEAR_CARD_QUEUE, DIALOG,
        SPECIAL, WAIT, SHUFFLE,
        REDUCE_POWER;
    }

//    protected boolean isDeadOrEscaped(core.AbstractCreature target) {
//        if (target.isDying || target.halfDead) {
//            return true;
//        }
//        if (!target.isPlayer) {
//            AbstractMonster m = (AbstractMonster) target;
//            if (m.isEscaping) {
//                return true;
//            }
//        }
//        return false;
//    }

    public abstract void update();

    protected void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }
    protected void addToTop(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }
}
