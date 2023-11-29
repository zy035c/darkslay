package com.woodburn.darkslay.creatures;

import javax.swing.plaf.basic.BasicSliderUI.ScrollListener;

import com.woodburn.darkslay.cards.Deck;
import com.woodburn.darkslay.scaling.StatusObject;


/******************************************************************************
 * @author Ziyi Chen
 *  parent class of all player classes
 *
 *
 ******************************************************************************/

 public abstract class AbstractPlayer extends AbstractCreature {

    /**
     * Classes, like character-type, of the player
     */
    public enum PlayerClass {
        Ironclad, Silent, Defect, Watcher, Dragonslayer, Seeker;
    }

    /**
     * Status object, status for scaling, new feature for future implementation
     */
    public StatusObject statusObj;

    /**
     * The current number of energy.
     */
    public int energy = 0;

    /**
     * The number of energy refilled at the start of each turn.
     */
    public int energyCap;

    /**
     * The max health of this player at the start of each game.
     */
    public int startingMaxHP; 

    /**
     * The deck that every draw accesses. 
     * At the start of each game, it is the masterDeck shuffled. 
     */
    public Deck drawPile;

    /**
     * Cards that are discarded.
     * They will be shuffled and put to draw pile
     * when draw pile asks for more cards in a draw.
     */
    public Deck discardPile;

    /**
     * Deck that holds all cards in hands.
     * These cards will be displayed as part of GUI. 
     */
    public Deck hand; 

    /**
     * Player-built deck, including all the cards that
     * join the game. 
     */
    public Deck masterDeck;

    /**
     * Deck that holds all exhausted cards.
     * Exhausted cards are used and not join the circulation
     * of drawing and discarding anymore. 
     */
    public Deck exhaustPile;

    /**
     * Cards that are reserved in hand until next turn.
     */
    public Deck limbo;

    /**
     * Max number of cards allowed in hand.
     */
    public int hand_max;

    /**
     * Number of draw at the start of turn.
     */
    public int drawNumber; 


    // RPG abilities
    int agile;
    int strength;
    int intelligence;

    public abstract ArrayList<String> getStartingDeck();
    // public abstract ArrayList<String> getStarterDeck();

    public AbstractPlayer(String name, int maxHP, int hand_max, int drawNumber) {
        this.name = name;
        this.isPlayer = true;
        this.maxHP = maxHP;
        this.startingMaxHP = maxHP;
        this.hand_max = hand_max;
        this.drawNumber = drawNumber;
        // this.gameHandSize = 10;

        drawPile = new Deck(Deck.DECK_TYPE.DRAW_PILE);
        discardPile = new Deck(Deck.DECK_TYPE.DISCARD_PILE);
        hand = new Deck(Deck.DECK_TYPE.HAND);
        masterDeck = new Deck(Deck.DECK_TYPE.MASTER_DECK);
        exhaustPile = new Deck(Deck.DECK_TYPE.EXHAUST_PILE);
        limbo = new Deck(Deck.DECK_TYPE.LIMBO);
    }

    protected void initializeClass() {
//        exhaustPile = new Deck(Deck.DECK_TYPE.EXHAUST_PILE);

    }

    /******************************************************************************
     *  below are methods for testing in demo.
     *
     ******************************************************************************/

    /*
     * this is an auto init of a deck
     * used in testing demo.
    **/
    public void initializeTestDeck() {
        // num = 13
        this.masterDeck.addToTop((AbstractCard)new Strike());
        this.masterDeck.addToTop((AbstractCard)new Strike());
        this.masterDeck.addToTop((AbstractCard)new Strike());
        this.masterDeck.addToTop((AbstractCard)new Strike());
        this.masterDeck.addToTop((AbstractCard)new Defend());

        this.masterDeck.addToTop((AbstractCard)new Defend());
        this.masterDeck.addToTop((AbstractCard)new Defend());
        this.masterDeck.addToTop((AbstractCard)new Defend());
        this.masterDeck.addToTop((AbstractCard)new Clash());
        this.masterDeck.addToTop((AbstractCard)new Clash());

        this.masterDeck.addToTop((AbstractCard)new Anger());
        this.masterDeck.addToTop((AbstractCard)new Anger());
    }

    public void initializeStarterDeck() {
        this.masterDeck.addToTop((AbstractCard)new Strike());
        this.masterDeck.addToTop((AbstractCard)new Strike());
        this.masterDeck.addToTop((AbstractCard)new Strike());
        this.masterDeck.addToTop((AbstractCard)new Strike());
        this.masterDeck.addToTop((AbstractCard)new Strike());
        this.masterDeck.addToTop((AbstractCard)new Defend());
        this.masterDeck.addToTop((AbstractCard)new Defend());
        this.masterDeck.addToTop((AbstractCard)new Defend());
        this.masterDeck.addToTop((AbstractCard)new Defend());
        this.masterDeck.addToTop((AbstractCard)new Defend());
    }

    /******************************************************************************
     * Below are methods about turns.
     ******************************************************************************/

    public int turn_num = 0; //

    public int drawNumberForTurn = -1;
    public int energyRechargeForTurn = -1;
    public void getDrawNumber() {
        // buff, ring...
        drawNumberForTurn = this.drawNumber; //
    }

    public void getEnergyRechargeNumber() {
        energyRechargeForTurn = this.energyCap; //
    }

    public void startTurn() {
        turn_num++;
        atTurnStart(); // activate buffs and rings
        getDrawNumber();
        getEnergyRechargeNumber();
        AbstractDungeon.actionManager.addToTop(new DrawCardAction(this, drawNumberForTurn, false));
        AbstractDungeon.actionManager.addToTop(new GainEnergyAction(energyRechargeForTurn)); // recharge energy
        this.loseBlock(); // reset block value
        AbstractDungeon.actionManager.emptyQueue();
    }


    public void endTurn() {
        atTurnEnd(); // activate buffs and rings
        AbstractDungeon.actionManager.addToTop(new DiscardAtEndOfTurnAction()); // discard all cards
        loseEnergyAtTurnEnd();
        AbstractDungeon.actionManager.emptyQueue();
    }

    public void loseEnergyAtTurnEnd() {
        this.energy = 0;
    }

    /******************************************************************************
     *  Methods that change status
     ******************************************************************************/
    // Gain e energy
    public void gainEnergy(int e) {
        this.energy += e;
        System.out.println(this.name+" gain "+e+" energy."
                +" Now "+this.energy+" energy.");
    }

    // Lose e energy
    public void loseEnergy(int e) {
        this.energy -= e;
        System.out.println(this.name+" lose "+e+" energy."
                +" Now "+this.energy+" energy.");
    }

    public void heal(int healAmount) {
    }

    //
    //
    //

    // Take damage. 
    @Override
    public int calculateDamageReceive(DamageInfo info) {
        int damageAmount = info.output;
        if (damageAmount < 0) {
            damageAmount = 0;
        }
        for (AbstractBuff buff: this.buffs) {
            if (buff.modifyDamageReceive) {
                damageAmount = buff.atDamageReceive(info, damageAmount);
            }
        }
        for (AbstractRing ring: this.rings) {
            if (ring.modifyDamageReceive) {
                damageAmount = ring.atDamageReceive(info, damageAmount);
            }
        }
        return damageAmount;
    }

    @Override
    public void damage(DamageInfo info) {
        boolean hadBlock = true;
        if (this.block == 0) {
            hadBlock = false;
        }
        int damageAmount = calculateDamageReceive(info); // for relics and buffs to take effect
        damageAmount = decrementBlock(info, damageAmount); // decrementBlock from parent class

        GameActionManager.damageReceivedThisTurn += damageAmount;
        GameActionManager.damageReceivedThisCombat += damageAmount;

        this.health -= damageAmount;
        System.out.println(this.name+" takes "+damageAmount+" damage."+
                " Now HP "+this.health);

//        if (damageAmount > 0 && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
//            updateCardsOnDamage();
//            this.damagedThisCombat++;
//        }

        if (this.health < 0) {
            this.health = 0;
        } else if (this.health < this.maxHP / 2.0F) {
            // effect
        }
        // AbstractGUI.updateHealthBar(this);

        if (this.health < 1) {
            this.isDead = true;
            // AbstractGUI.deathScreen();
        }
    };


    /******************************************************************************
     *  Below are methods related to card
     ******************************************************************************/

    /**
     * Draw #amount card from draw pile, put it into hand, and return the card.
     */
    public ArrayList<AbstractCard> drawAndPeek(int amount) {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        if (amount <= 0) {
            System.out.println("[PLAYER] DrawAndPeek: no card is drawn.");
            return cards;
        }
        
        // Limit the hand size. 
        if (this.hand.size() + amount > hand_max) {
            // messagebox
            return drawAndPeek(hand_max - amount);
        }

        // It's not possible to draw #amount carads. Draw less. 
        if (this.drawPile.size() + this.discardPile.size() < amount) {
            return drawAndPeek(this.drawPile.size() + this.discardPile.size());
        }
        // It's possible, but requires a shuffle to get discard pile's cards.  
        if (this.drawPile.size() < amount || this.drawPile.size() <= 0) {
            AbstractDungeon.actionManager.addToBottom(new ShuffleAction(this));
            AbstractDungeon.actionManager.executeAction();
        }
        //
        for (int i = 0; i < amount; i++) {
            // System.out.println("before drawPile.drawFromTop size "+drawPile.size());
            AbstractCard card = drawPile.drawFromTop();
            cards.add(card);
            System.out.println("[PLAYER] DrawAndPeek: " + this.name + " draws a " + card.NAME);
            hand.addToTop(card);
            this.onCardDrawOrDiscard(); // trigger once each card id drawn
            this.onRefreshHand();
        }
        // If there is no card in draw pile after the draw, do nothing. 

        if (cards.size() <= 0) {
            System.out.println("[PLAYER] DrawAndPeek: no card is drawn.");
        }
        return cards;
    }

    /**
     * A simplified draw method. Do not return drawn cards. 
     * @param amount
     */
    public void draw(int amount) {
        drawAndPeek(amount);
    }

    /**
     * Draw 1 card if void param.
     */
    public void draw() {
        draw(1);
    }
    // avoid outOfBoundException
    /**
     * Draw 1 card and peek if void param.
     * @return
     */
    public ArrayList<AbstractCard> drawAndPeek() {
        return drawAndPeek(1);
    }

    public void useCard(AbstractCard c, AbstractCreature target, int energyOnUse) {

        if (c.type == AbstractCard.CardType.ATTACK) {
            // useFastAttackAnimation();
        }

//        c.calculateCardDamage(this, target); // 

        // X-cost card: consume all energy
//        if (c.cost == -1 && EnergyPanel.totalCount < energyOnUse && !c.ignoreEnergyOnUse) {
//            c.energyOnUse = EnergyPanel.totalCount;
//        }

        this.loseEnergy(energyOnUse);

        if (c.cost == -1 && c.isInAutoplay) {
            c.freeToPlayOnce = true;
        }
        c.use(this, target);
        AbstractDungeon.actionManager.addToTop(new UseCardAction(c, target));

        this.hand.removeSpecificCard(c);
        this.onRefreshHand();

        // exhaust, go to draw pile...
        if (c.exhaust) {
            AbstractDungeon.actionManager.addToTop(new ExhaustAction(c));
        } else {
            AbstractGameAction act = new DiscardPlayAction(c);
            AbstractDungeon.actionManager.addToTop(act);
        }
        // this.onCardDrawOrDiscard();

        // this.onRefreshHand(); // not required anymore
        AbstractDungeon.actionManager.emptyQueue();
    }

    /**
     * Shuffled discard and put all to draw.
     */
    public void reshuffle() {
        discardPile.shuffle();
        while (discardPile.size() > 0) {
            drawPile.addToTop(discardPile.drawFromTop());
        }
        System.out.println("[PLAYER] Shuffled discard and put all to draw.");
    }

    /**
     * @param c Put the card on the top of discard pile.
     */
    public void moveToDiscardPile(AbstractCard c) {
        discardPile.addToTop(c);
        this.onCardDrawOrDiscard();
    }

    /**
     * @param c Remove the card from hand, then moveToDiscardPile().
     */
    public void discardFromHand(AbstractCard c) {
        this.hand.removeSpecificCard(c);
        this.onRefreshHand();
        moveToDiscardPile(c); // contains this.onCardDrawOrDiscard()
    }

    /**
     * @param c "Burn" the card. Join exhaust pile.
     */
    public void exhaustCard(AbstractCard c) {
        c.onExhaust();
        exhaustPile.addToTop(c);
    }

    /******************************************************************************
     *  Code related to rings and buffs
     ******************************************************************************/
    public ArrayList<AbstractRing> rings = new ArrayList<>();

    public void atPreBattle() {
        for (AbstractRing r: this.rings) {
            r.atPreBattle();
        }
        //
    }

    @Override
    public void atTurnEnd() {
        ArrayList<AbstractBuff> buffsToCheck = new ArrayList<>(this.buffs);
        for (AbstractBuff buff: buffsToCheck) {
            buff.atEndOfTurn();
        }
    }

    public void atTurnStart() {
        for (AbstractRing r: this.rings) {
            r.atTurnStart();
        }
        //
    }

    // buff相关的在上一层的abstract creature里

    public boolean hasRing(String ringName) {
        if (this.rings.isEmpty()) {
            return false;
        }
        return this.getRing(ringName) != null;
    }

    public void obtainRing(AbstractRing ring) {
        ring.onObtain();
        this.rings.add(ring);
    }

    public AbstractRing getRing(String ringName) {
        for (AbstractRing r: this.rings) {
            if (r.name.equals(ringName)) {
                return r;
            }
        }
        return null;
    }

    public void onRefreshHand() {
        for (AbstractRing r: this.rings) {
            r.onRefreshHand();
        }
        // buff
    }

    @Deprecated
    public void onCardDrawOrDiscard() {
        for (AbstractRing r: this.rings) {
            // r.onCardDraw();
            r.onDrawOrDiscard();
        }
        // buff
    }

    public void initializeTestRings() {}

}
