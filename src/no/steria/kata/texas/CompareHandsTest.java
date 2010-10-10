package no.steria.kata.texas;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class CompareHandsTest {

    @Test(expected=IllegalArgumentException.class)
    public void wontAllowCreationOfHandWithWrongNumberOfCards() throws Exception {
        new PlayerHand(Card.spades(2));
    }

    @Test
    public void shouldNotFindAnythingElseInHighCard() throws Exception {
        PlayerHand highCardHand = new PlayerHand(Card.spades(2), Card.hearts(8), Card.spades(10), Card.diamonds(4), Card.clubs(6));

        assertThat(highCardHand.getPairHand()).isNull();
        assertThat(highCardHand.getFlushHand()).isNull();
        assertThat(highCardHand.getStraightFlushHand()).isNull();
    }

    @Test
    public void shouldCompareHighCards() throws Exception {
        PlayerHand playerHandWithNoFaceCards = new PlayerHand(Card.spades(2), Card.hearts(8), Card.spades(10), Card.diamonds(4), Card.clubs(6));
        PlayerHand playerHandWithKing = new PlayerHand(Card.spades(2), Card.hearts(5), Card.spades(3), Card.diamonds(13), Card.clubs(4));

        assertThat(playerHandWithKing.getHighCardHand())
            .containsExactly(Card.diamonds(13), Card.hearts(5), Card.clubs(4), Card.spades(3), Card.spades(2));
        assertBetter(playerHandWithKing, playerHandWithNoFaceCards);
    }

    @Test
    public void shouldCompareAllHighCards() throws Exception {
        PlayerHand playerHand1 = new PlayerHand(Card.spades(12), Card.hearts(11), Card.spades(9), Card.diamonds(7), Card.clubs(6));
        PlayerHand playerHand2 = new PlayerHand(Card.spades(12), Card.hearts(11), Card.spades(9), Card.diamonds(7), Card.clubs(5));

        assertBetter(playerHand1, playerHand2);
    }

    @Test
    public void pairBeatsAllHighCards() throws Exception {
        PlayerHand goodHighCards = new PlayerHand(Card.spades(14), Card.hearts(13), Card.spades(12), Card.diamonds(11), Card.clubs(9));
        PlayerHand badPair = new PlayerHand(Card.clubs(3), Card.clubs(2), Card.diamonds(3), Card.diamonds(4), Card.hearts(5));

        assertThat(badPair.getPairHand()).startsWith(Card.clubs(3), Card.diamonds(3));

        assertBetter(badPair, goodHighCards);
    }

    @Test
    public void bestPairWins() throws Exception {
        PlayerHand goodPair = new PlayerHand(Card.clubs(3), Card.clubs(13), Card.diamonds(13), Card.diamonds(4), Card.hearts(5));
        PlayerHand badPair = new PlayerHand(Card.clubs(3), Card.clubs(2), Card.diamonds(3), Card.diamonds(4), Card.hearts(5));
        assertThat(goodPair.getPairHand()).startsWith(Card.clubs(13), Card.diamonds(13));

        assertBetter(goodPair, badPair);
    }

    @Test
    public void highCardBreaksTieWithPair() throws Exception {
        PlayerHand pairWithBestHighCard = new PlayerHand(Card.clubs(3), Card.clubs(13), Card.diamonds(13), Card.diamonds(4), Card.hearts(10));
        PlayerHand pairWithWorseHighCard = new PlayerHand(Card.clubs(3), Card.clubs(13), Card.diamonds(13), Card.diamonds(4), Card.hearts(5));

        assertBetter(pairWithBestHighCard, pairWithWorseHighCard);
    }

    @Test
    public void twoPairsBeatOnePair() throws Exception {
        PlayerHand goodPair = new PlayerHand(Card.clubs(3), Card.clubs(13), Card.diamonds(13), Card.diamonds(4), Card.hearts(5));
        PlayerHand badTwoPairs = new PlayerHand(Card.clubs(3), Card.clubs(2), Card.diamonds(3), Card.diamonds(2), Card.hearts(5));
        assertThat(badTwoPairs.getTwoPairHand()).startsWith(Card.clubs(3), Card.diamonds(3), Card.clubs(2), Card.diamonds(2));
        assertBetter(badTwoPairs, goodPair);
    }

    @Test
    public void threeOfAKindBeatsTwoPairs() throws Exception {
        PlayerHand goodTwoPairs = new PlayerHand(Card.clubs(13), Card.clubs(12), Card.diamonds(13), Card.diamonds(12), Card.hearts(5));
        PlayerHand badThreeOfAKind = new PlayerHand(Card.clubs(3), Card.hearts(3), Card.diamonds(2), Card.diamonds(3), Card.hearts(5));

        assertThat(badThreeOfAKind.getThreeOfAKindHand()).startsWith(Card.clubs(3), Card.hearts(3), Card.diamonds(3));
        assertBetter(badThreeOfAKind, goodTwoPairs);
    }

    @Test
    public void straightBeatsThreeOfAKind() throws Exception {
        PlayerHand goodThreeOfAKind = new PlayerHand(Card.clubs(13), Card.hearts(13), Card.diamonds(2), Card.diamonds(13), Card.hearts(5));
        PlayerHand badStraight = new PlayerHand(Card.clubs(2), Card.hearts(3), Card.diamonds(5), Card.diamonds(4), Card.hearts(6));

        assertThat(badStraight.getStraightHand()).containsExactly(Card.clubs(2), Card.hearts(3), Card.diamonds(4), Card.diamonds(5), Card.hearts(6));
        assertBetter(badStraight, goodThreeOfAKind);
    }

    @Test
    public void aceCanBeUsedAtBothEndsOfStraight() throws Exception {
        PlayerHand straightWithAceHigh = new PlayerHand(Card.clubs(1), Card.hearts(13), Card.diamonds(11), Card.spades(12), Card.spades(10));
        assertThat(straightWithAceHigh.getStraightHand()).contains(Card.spades(10), Card.diamonds(11), Card.spades(12), Card.hearts(13), Card.clubs(1));

        PlayerHand straightWithAceLow = new PlayerHand(Card.clubs(1), Card.hearts(3), Card.diamonds(4), Card.spades(2), Card.spades(5));
        assertThat(straightWithAceLow.getStraightHand()).contains(Card.clubs(1), Card.spades(2), Card.hearts(3), Card.diamonds(4), Card.spades(5));
    }

    @Test
    public void flushBeatsStaight() throws Exception {
        PlayerHand goodStraight = new PlayerHand(Card.clubs(13), Card.hearts(11), Card.diamonds(12), Card.diamonds(1), Card.hearts(10));
        PlayerHand badFlush = new PlayerHand(Card.clubs(2), Card.clubs(3), Card.clubs(5), Card.clubs(8), Card.clubs(6));

        assertThat(badFlush.getFlushHand()).containsExactly(Card.clubs(8), Card.clubs(6), Card.clubs(5), Card.clubs(3), Card.clubs(2));
        assertBetter(badFlush, goodStraight);
    }

    @Test
    public void fullHouseBeatsFlush() throws Exception {
        PlayerHand goodFlush = new PlayerHand(Card.clubs(1), Card.hearts(3), Card.diamonds(4), Card.spades(2), Card.spades(5));
        PlayerHand badFullHouse = new PlayerHand(Card.clubs(2), Card.clubs(3), Card.spades(3), Card.hearts(2), Card.spades(2));

        assertThat(badFullHouse.getFullHouseHand()).containsExactly(Card.clubs(2), Card.hearts(2), Card.spades(2), Card.clubs(3), Card.spades(3));
        assertBetter(badFullHouse, goodFlush);
    }

    @Test
    public void fourOfAKindBeatsHouse() throws Exception {
        PlayerHand goodFullHouse = new PlayerHand(Card.clubs(12), Card.clubs(13), Card.spades(13), Card.hearts(12), Card.spades(12));
        PlayerHand badFourOfAKind = new PlayerHand(Card.clubs(3), Card.hearts(3), Card.diamonds(4), Card.spades(3), Card.diamonds(3));

        assertThat(badFourOfAKind.getFourOfAKindHand()).containsExactly(Card.clubs(3), Card.hearts(3), Card.spades(3), Card.diamonds(3));
        assertBetter(badFourOfAKind, goodFullHouse);
    }

    @Test
    public void straightFlushBeatsFourOfAKind() throws Exception {
        PlayerHand goodFourOfAKind = new PlayerHand(Card.clubs(13), Card.hearts(13), Card.diamonds(4), Card.spades(13), Card.diamonds(13));
        PlayerHand badStraightFlush = new PlayerHand(Card.clubs(2), Card.clubs(3), Card.clubs(5), Card.clubs(6), Card.clubs(4));

        assertThat(badStraightFlush.getStraightFlushHand()).containsExactly(Card.clubs(6), Card.clubs(5), Card.clubs(4), Card.clubs(3), Card.clubs(2));
        assertBetter(badStraightFlush, goodFourOfAKind);
    }


    private void assertBetter(PlayerHand betterHand, PlayerHand worseHand) {
        assertThat(betterHand.compareTo(worseHand))
            .as(betterHand + " should beat " + worseHand)
            .isGreaterThan(0);
        assertThat(worseHand.compareTo(betterHand))
            .as(betterHand + " should beat " + worseHand)
            .isLessThan(0);
    }

}
