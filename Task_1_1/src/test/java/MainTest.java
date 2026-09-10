import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MainTest {

    @Test
    void testStandardArray() {
        int[] actual = {12, 11, 13, 5, 6, 7};
        int[] expected = {5, 6, 7, 11, 12, 13};

        Main.heapSort(actual);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testAlreadySortedArray() {
        int[] actual = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};

        Main.heapSort(actual);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testReversedArray() {
        int[] actual = {9, 7, 5, 3, 1};
        int[] expected = {1, 3, 5, 7, 9};

        Main.heapSort(actual);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testDuplicatesAndNegatives() {
        int[] actual = {-3, 10, -3, 0, 5, 0, -8};
        int[] expected = {-8, -3, -3, 0, 0, 5, 10};

        Main.heapSort(actual);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testEdgeCasesEmpty() {
        int[] empty = {};
        Main.heapSort(empty);
        assertArrayEquals(new int[]{}, empty);
    }

    @Test
    void testEdgeCasesSingle() {
        int[] single = {42};
        Main.heapSort(single);
        assertArrayEquals(new int[]{42}, single);
    }

    @Test
    void testEdgeCasesTwo() {
        int[] actual = {42, 12};
        int[] expected = {12, 42};
        Main.heapSort(actual);
        assertArrayEquals(expected, actual);
    }

}
