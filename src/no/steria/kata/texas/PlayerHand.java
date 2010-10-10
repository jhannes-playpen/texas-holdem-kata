package no.steria.kata.texas;

import static no.steria.kata.texas.CompareUtils.compareLists;
import static no.steria.kata.texas.CompareUtils.compareMany;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerHand implements Comparable<PlayerHand> {

    private final Card[] cards;

    public PlayerHand(Card... cards) {
        if (cards.length != 5) throw new IllegalArgumentException(Arrays.asList(cards) + " wrong length " + cards.length);
        this.cards = cards;
    }

    @Override
    public int compareTo(PlayerHand o) {
        return compareMany(
            compareLists(getStraightFlushHand(), o.getStraightFlushHand()),
            compareLists(getFourOfAKindHand(), o.getFourOfAKindHand()),
            compareLists(getFullHouseHand(), o.getFullHouseHand()),
            compareLists(getFlushHand(), o.getFlushHand()),
            compareLists(getStraightHand(), o.getStraightHand()),
            compareLists(getThreeOfAKindHand(), o.getThreeOfAKindHand()),
            compareLists(getTwoPairHand(), o.getTwoPairHand()),
            compareLists(getPairHand(), o.getPairHand()),
            compareLists(getHighCardHand(), o.getHighCardHand()));
    }

    @Override
    public String toString() {
        return getHighCardHand().toString();
    }

    public List<Card> getHighCardHand() {
        List<Card> result = Arrays.asList(cards);
        Collections.sort(result);
        Collections.reverse(result);
        return result;
    }

    public List<Card> getPairHand() {
        Map<Integer, List<Card>> cardsPerRank = getCardsPerRank();
        for (List<Card> rankCards : cardsPerRank.values()) {
            if (rankCards.size() == 2) return rankCards;
        }
        return null;
    }

    public List<Card> getTwoPairHand() {
        Map<Integer, List<Card>> cardsPerRank = getCardsPerRank();
        List<Card> result = new ArrayList<Card>();
        for (int rank : getRanks()) {
            if (cardsPerRank.get(rank).size() == 2) result.addAll(cardsPerRank.get(rank));
        }
        if (result.size() == 4) return result;
        return null;
    }

    public List<Card> getThreeOfAKindHand() {
        Map<Integer, List<Card>> cardsPerRank = getCardsPerRank();
        for (List<Card> rankCards : cardsPerRank.values()) {
            if (rankCards.size() == 3) return rankCards;
        }
        return null;
    }

    private Map<Integer, List<Card>> getCardsPerRank() {
        Map<Integer, List<Card>> cardsPerRank = new HashMap<Integer, List<Card>>();
        for (int rank : getRanks()) cardsPerRank.put(rank, new ArrayList<Card>());
        for (Card card : cards) {
            cardsPerRank.get(card.getRank()).add(card);
        }
        return cardsPerRank;
    }

    private List<Integer> getRanks() {
        return Arrays.asList(14,13,12,11,10,9,8,7,6,5,4,3,2);
    }

    private List<Integer> getAceLowStraight() {
        return Arrays.asList(14, 2, 3, 4, 5);
    }

    public List<Card> getStraightHand() {
        List<Card> straightWithAceLow = getStraightWithAceLow();
        if (straightWithAceLow != null) return straightWithAceLow;

        List<Card> hand = Arrays.asList(cards);
        Collections.sort(hand);
        int previousRank = hand.get(0).getRank();
        for (int cardIndex = 1; cardIndex < hand.size(); cardIndex++) {
            if (previousRank+1 != hand.get(cardIndex).getRank()) return null;
            previousRank++;
        }
        return hand;
    }

    private List<Card> getStraightWithAceLow() {
        Map<Integer, List<Card>> cardsPerRank = getCardsPerRank();
        List<Integer> aceLowStraight = getAceLowStraight();
        List<Card> hand = new ArrayList<Card>();

        for (Integer rank : aceLowStraight) {
            if (cardsPerRank.get(rank).size()!=1) return null;
            hand.addAll(cardsPerRank.get(rank));
        }
        return hand;
    }

    public List<Card> getFlushHand() {
        String suit = cards[0].getSuit();
        for (Card card : cards) {
            if (!card.getSuit().equals(suit)) return null;
        }
        return getHighCardHand();
    }

    public List<Card> getFullHouseHand() {
        Map<Integer, List<Card>> cardsPerRank = getCardsPerRank();
        List<Card> threeOfAKind = null;
        List<Card> pair = null;
        for (Integer rank : getRanks()) {
            if (cardsPerRank.get(rank).size() == 3) threeOfAKind = cardsPerRank.get(rank);
            if (cardsPerRank.get(rank).size() == 2) pair = cardsPerRank.get(rank);
        }
        if (threeOfAKind == null || pair == null) return null;
        List<Card> result = new ArrayList<Card>(threeOfAKind);
        result.addAll(pair);
        return result;
    }

    public List<Card> getFourOfAKindHand() {
        Map<Integer, List<Card>> cardsPerRank = getCardsPerRank();
        for (Integer rank : getRanks()) {
            if (cardsPerRank.get(rank).size() == 4) return cardsPerRank.get(rank);
        }
        return null;
    }

    public List<Card> getStraightFlushHand() {
        if (getFlushHand() == null || getStraightHand() == null) return null;
        return getHighCardHand();
    }

}
