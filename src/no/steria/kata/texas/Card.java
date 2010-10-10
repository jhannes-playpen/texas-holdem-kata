package no.steria.kata.texas;

public class Card implements Comparable<Card> {

    private final int rank;
    private final char suiteSymbol;
    private String suit;

    public Card(int rank, String suit, char suiteSymbol) {
        this.rank = rank != 1 ? rank : 14;
        this.suit = suit;
        this.suiteSymbol = suiteSymbol;
    }

    public static Card spades(int rank) {
        return new Card(rank, "spades", '\u2660');
    }

    public static Card hearts(int rank) {
        return new Card(rank, "hearts", '\u2665');
    }

    public static Card diamonds(int rank) {
        return new Card(rank, "rank", '\u2666');
    }

    public static Card clubs(int rank) {
        return new Card(rank, "clubs", '\u2663');
    }

    @Override
    public String toString() {
        return getRankName() + " " + suiteSymbol;
    }

    @Override
    public int compareTo(Card o) {
        return rank - o.rank;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Card)) return false;
        Card o = (Card) obj;
        return rank == o.rank && suiteSymbol == o.suiteSymbol;
    }

    @Override
    public int hashCode() {
        return (rank & 0xff) << 8 | (short)suiteSymbol;
    }

    public int getRank() {
        return rank;
    }

    private String getRankName() {
        if (rank == 11) return "J";
        if (rank == 12) return "Q";
        if (rank == 13) return "K";
        if (rank == 14) return "A";
        return String.valueOf(rank);
    }

    public String getSuit() {
        return suit;
    }
}
