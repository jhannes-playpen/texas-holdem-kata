package no.steria.kata.texas;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class CardTest {

    @Test
    public void toStringShouldNameCard() throws Exception {
        assertThat(Card.clubs(2).toString()).isEqualTo("2 \u2663");
        assertThat(Card.diamonds(11).toString()).isEqualTo("J \u2666");
        assertThat(Card.hearts(12).toString()).isEqualTo("Q \u2665");
        assertThat(Card.spades(13).toString()).isEqualTo("K \u2660");
        assertThat(Card.spades(1).toString()).isEqualTo("A \u2660");
    }

    @Test
    public void shouldCompare() throws Exception {
        assertIsBetter(Card.clubs(4), Card.clubs(3));
        assertIsBetter(Card.clubs(4), Card.diamonds(3));

        assertThat(Card.clubs(4).compareTo(Card.diamonds(4))).isZero();
    }

    @Test
    public void shouldRankAceHighest() throws Exception {
        assertIsBetter(Card.clubs(1), Card.clubs(13));
    }

    @Test
    public void shouldBeEqualWhenRankAndSuitEqual() throws Exception {
        assertThat(Card.clubs(2))
            .isEqualTo(Card.clubs(2))
            .isNotEqualTo(Card.diamonds(2))
            .isNotEqualTo(Card.clubs(3))
            .isNotEqualTo(new Object());

        assertThat(Card.clubs(2).hashCode()).as("hashCode")
            .isEqualTo(Card.clubs(2).hashCode())
            .isNotEqualTo(Card.diamonds(2).hashCode())
            .isNotEqualTo(Card.clubs(3).hashCode());
    }

    private void assertIsBetter(Card betterCard, Card worseCard) {
        assertThat(betterCard.compareTo(worseCard)).isGreaterThan(0)
            .as(betterCard + " should beat " + worseCard);
        assertThat(worseCard.compareTo(betterCard)).isLessThan(0)
            .as(betterCard + " should beat " + worseCard);
    }

}
