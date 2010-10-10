package no.steria.kata.texas;

import static no.steria.kata.texas.CompareUtils.compareLists;
import static no.steria.kata.texas.CompareUtils.compareMany;
import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;


public class CompareUtilsTest {
    @Test
    public void checkCompareLists() throws Exception {
        assertThat(compareLists((List<Integer>)null, null)).isZero();
        assertThat(compareLists(null, new ArrayList<Integer>())).isNegative();
        assertThat(compareLists(new ArrayList<Integer>(), null)).isPositive();
        assertThat(compareLists(new ArrayList<Integer>(), new ArrayList<Integer>())).isZero();

        assertThat(compareLists(Arrays.asList(2, 3), Arrays.asList(2, 4)))
            .isNegative();
        assertThat(compareLists(Arrays.asList(2, 4), Arrays.asList(2, 3)))
            .isPositive();
        assertThat(compareLists(Arrays.asList(2, 4), Arrays.asList(2, 4)))
            .isZero();

        assertThat(compareLists(Arrays.asList(10), Arrays.asList(9, 4)))
            .isPositive();
        assertThat(compareLists(Arrays.asList(9, 4), Arrays.asList(10)))
            .isNegative();
        assertThat(compareLists(Arrays.asList(9, 4), Arrays.asList(9)))
            .isPositive();
        assertThat(compareLists(Arrays.asList(9), Arrays.asList(9, 4)))
            .isNegative();
    }

    @Test
    public void checkCompareMany() throws Exception {
        assertThat(compareMany(0, 0, 0)).isEqualTo(0);
        assertThat(compareMany(1, 0, 10)).isEqualTo(1);
        assertThat(compareMany(0, 0, 10)).isEqualTo(10);
    }


}
