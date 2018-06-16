package kr.ac.cnu.web.games.blackjack;

import kr.ac.cnu.web.exceptions.NotEnoughBalanceException;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by rokim on 2018. 5. 26..
 */
public class Player {
    @Getter
    private long balance;//잔고
    @Getter
    private long currentBet;//배팅한 금액
    @Getter
    private boolean isPlaying;
    @Getter
    private Hand hand;

    public Player(long seedMoney, Hand hand) {
        this.balance = seedMoney;
        this.hand = hand;

        isPlaying = false;
    }

    public void reset() {
        hand.reset();
        isPlaying = false;
    }

    public void placeBet(long bet) {
        if(balance < bet) {
        }
        balance -= bet;
        currentBet = bet;

        isPlaying = true;
    }

    public void deal() {//맨처음
        Card first = hand.drawCard();
        Card second = hand.drawCard();
        if(first.getRank() + second.getRank() ==21){ //blackjack이면 1.5배를 돌려받아야함
            balance  += getCurrentBet()*1.5;
            this.isPlaying=false;
        }
        if(first.getRank()+second.getRank() > 21){
//            currentBet=0;
            this.isPlaying=false;
        }

    }

    public void win() {//이김
        balance += currentBet * 2;
//        currentBet = 0;
    }

    public void tie() {//비김
        balance += currentBet;
//        currentBet = 0;
    }

    public void lost() {
//        currentBet = 0;
    }

    public Card hitCard() {
        return hand.drawCard();
    }

    public void stand() {

        this.isPlaying = false;
    }

}
