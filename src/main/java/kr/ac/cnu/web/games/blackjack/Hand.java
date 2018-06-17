package kr.ac.cnu.web.games.blackjack;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rokim on 2018. 5. 26..
 */
public class Hand {
    private Deck deck;
        @Getter
        private List<Card> cardList = new ArrayList<>();

    public Hand(Deck deck) {
            this.deck = deck;
        }

        public Card drawCard() {
            Card card = deck.drawCard();

        cardList.add(card);
        return card;
    }

    private int prev_sum=0;
    private int chk = 0;

    public int getCardSum() {

        prev_sum= cardList.stream().mapToInt(card -> {
            int sum;
            if(card.getRank()==1){
                chk++;
            }
            if(card.getRank()>10) {
                System.out.println(card.getRank());
                sum = 10;

            }else {
                System.out.println(card.getRank());
                sum = card.getRank();
            }
            return sum;
        }).sum();
        if(chk!=0 && prev_sum<=11){
            prev_sum +=10;
            chk=0;
        }
        return prev_sum;
    }

    public void reset() {
        cardList.clear();
    }
}
