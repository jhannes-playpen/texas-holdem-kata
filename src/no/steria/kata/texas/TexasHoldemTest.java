package no.steria.kata.texas;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class TexasHoldemTest {

    @Test
    public void shouldPickBestFullHouse() throws Exception {
        PlayerHand hand = new PlayerHand(Card.clubs(13), Card.spades(9), Card.spades(13), Card.diamonds(13), Card.diamonds(9), Card.clubs(3), Card.diamonds(3));
        assertThat(hand.getFullHouseHand()).containsExactly(Card.clubs(13), Card.spades(13), Card.diamonds(13), Card.spades(9), Card.diamonds(9));
    }

    @Test
    public void shouldPickFullHouseFromTwoTripplets() throws Exception {
        PlayerHand hand = new PlayerHand(Card.clubs(13), Card.spades(9), Card.spades(13), Card.diamonds(13), Card.diamonds(9), Card.clubs(9), Card.diamonds(6));
        assertThat(hand.getFullHouseHand()).containsExactly(Card.clubs(13), Card.spades(13), Card.diamonds(13), Card.spades(9), Card.diamonds(9));
    }

    @Test
    public void shouldPickTwoPairs() throws Exception {
        PlayerHand hand = new PlayerHand(Card.clubs(9), Card.hearts(1), Card.spades(13), Card.diamonds(13), Card.diamonds(9), Card.clubs(3), Card.diamonds(6));
        assertThat(hand.getTwoPairHand()).containsExactly(Card.spades(13), Card.diamonds(13), Card.clubs(9), Card.diamonds(9));
    }

    @Test
    public void shouldPickFlush() throws Exception {
        PlayerHand hand = new PlayerHand(Card.diamonds(4), Card.diamonds(2), Card.spades(13), Card.diamonds(13), Card.diamonds(9), Card.clubs(3), Card.diamonds(6));
        assertThat(hand.getFlushHand()).containsExactly(Card.diamonds(13), Card.diamonds(9), Card.diamonds(6), Card.diamonds(4), Card.diamonds(2));
    }

    @Test
    public void shouldPickBestFlush() throws Exception {
        PlayerHand hand = new PlayerHand(Card.diamonds(4), Card.diamonds(2), Card.diamonds(5), Card.diamonds(13), Card.diamonds(9), Card.diamonds(3), Card.diamonds(6));
        assertThat(hand.getFlushHand()).containsExactly(Card.diamonds(13), Card.diamonds(9), Card.diamonds(6), Card.diamonds(5), Card.diamonds(4));
    }

    @Test
    public void shouldPickBestStraight() throws Exception {
        PlayerHand hand = new PlayerHand(Card.clubs(5), Card.hearts(2), Card.spades(4), Card.diamonds(7), Card.diamonds(9), Card.clubs(3), Card.diamonds(6));
        assertThat(hand.getStraightHand()).containsExactly(Card.diamonds(7), Card.diamonds(6), Card.clubs(5), Card.spades(4), Card.clubs(3));
    }

}
