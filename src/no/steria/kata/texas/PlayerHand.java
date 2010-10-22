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
        return Arrays.asList(5, 4, 3, 2, 14);
    }

    public List<Card> getStraightHand() {
        Map<Integer, List<Card>> cardsPerRank = getCardsPerRank();

        for (int rank=10; rank>=2; rank--) {
            List<Card> straight = getOneCardOfEachRank(cardsPerRank, Arrays.asList(rank+4, rank+3, rank+2, rank+1, rank));
            if (straight != null) return straight;
        }

        List<Card> straightWithAceLow = getOneCardOfEachRank(cardsPerRank, getAceLowStraight());
        if (straightWithAceLow != null) return straightWithAceLow;

        return null;
    }

    private List<Card> getOneCardOfEachRank(Map<Integer, List<Card>> cardsPerRank, List<Integer> ranks) {
        List<Card> hand = new ArrayList<Card>();
        for (Integer rank : ranks) {
            if (cardsPerRank.get(rank).size()!=1) return null;
            hand.add(cardsPerRank.get(rank).get(0));
        }
        return hand;
    }

    public List<Card> getFlushHand() {
        Map<String, List<Card>> cardsPerSuit = new HashMap<String, List<Card>>();
        String[] suits = new String[] { "clubs", "diamonds", "hearts", "spades" };
        for (String suit : suits) {
            cardsPerSuit.put(suit, new ArrayList<Card>());
        }
        for (Card card : cards) {
            cardsPerSuit.get(card.getSuit()).add(card);
        }

        for (String suit : suits) {
            if (cardsPerSuit.get(suit).size() >= 5) {
                Collections.sort(cardsPerSuit.get(suit));
                Collections.reverse(cardsPerSuit.get(suit));
                return cardsPerSuit.get(suit).subList(0, 5);
            }
        }
        return null;
    }

    public List<Card> getFullHouseHand() {
        Map<Integer, List<Card>> cardsPerRank = getCardsPerRank();
        List<Card> threeOfAKind = null;
        List<Card> pair = null;
        for (Integer rank : getRanks()) {
            if (cardsPerRank.get(rank).size() == 3 && threeOfAKind == null) {
                threeOfAKind = cardsPerRank.get(rank);
            } else if (cardsPerRank.get(rank).size() >= 2 && pair == null) {
                pair = cardsPerRank.get(rank).subList(0, 2);
            }
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

    public String getHandName() {
        if (getStraightFlushHand() != null) return "straight flush";
        if (getFourOfAKindHand() != null) return "four of a kind";
        if (getFullHouseHand() != null) return "full house";
        if (getFlushHand() != null) return "flush";
        if (getStraightHand() != null) return "straight";
        if (getThreeOfAKindHand() != null) return "three of a kind";
        if (getTwoPairHand() != null) return "two pairs";
        if (getPairHand() != null) return "pair";
        return "high card";
    }

}
