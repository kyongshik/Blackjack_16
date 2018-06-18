package kr.ac.cnu.web.games.blackjack;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by rokim on 2018. 5. 26..
 */
public class GameRoom {
    @Getter
    private final String roomId;
    @Getter
    private final Dealer dealer;
    @Getter
    private final Map<String, Player> playerList;
    @Getter
    private final Deck deck;
    @Getter
    private boolean isFinished;
    private final Evaluator evaluator;

    public GameRoom(Deck deck) {
        this.roomId = UUID.randomUUID().toString();
        this.deck = deck;
        this.dealer = new Dealer(new Hand(deck));
        this.playerList = new HashMap<>();
        this.evaluator = new Evaluator(playerList, dealer);
        this.isFinished = true;
    }

    public void addPlayer(String playerName, long seedMoney) {
        Player player = new Player(seedMoney, new Hand(deck));

        playerList.put(playerName, player);
    }

    public void removePlayer(String playerName) {
        playerList.remove(playerName);
    }

    public void reset() {
        dealer.reset();
        playerList.forEach((s, player) -> player.reset());
    }

    public void bet(String name, long bet) {
        Player player = playerList.get(name);

        player.placeBet(bet);
    }

    public void deal() {
        System.out.println("deal_gameroom");
        this.isFinished = false;
        dealer.deal();
        playerList.forEach((s, player) -> player.deal());

    }

    public Card hit(String name) {
        System.out.println("hit_gameroom");
        Player player = playerList.get(name);
        Hand hand = player.getHand();
        Card c = player.hitCard();

        int sum = hand.getCardSum()+c.getRank();

        if(sum <=21){
            return c;
        }
        else{
            this.isFinished=true;
            return c;
        }

    }
    public void doubledown(String name){
        Player player = playerList.get(name);
        Hand hand = player.getHand();
        System.out.println("doubledown");
        Card c = player.hitCard();
        player.placeBet(player.getCurrentBet());
        player.hitCard();
        player.stand();


    }
    public void stand(String name) {
        Player player = playerList.get(name);
        player.stand();
    }

    public void playDealer() {
        dealer.play();
        evaluator.evaluate();
        this.isFinished = true;
    }

}
