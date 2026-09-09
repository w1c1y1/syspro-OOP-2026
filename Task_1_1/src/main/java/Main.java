public class Main {
    /**
     * Method for swapping elements of an array by their index.
     *
     * @param array array of ints
     * @param index1 index of first elem to swap
     * @param index2 index of second elem to swap
     */
    private static void swap(int[] array, int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    /**
     * Function to compare two elements.
     *
     * @param array array of ints
     * @param index1 first index
     * @param index2 second index
     * @return boolean result
     */
    private static boolean compare(int[] array, int index1, int index2) {
        return array[index1] > array[index2];
    }

    /**
     * Method for shift down elements of an array with binary heap invariant.
     *
     * @param array array that we sort
     * @param index index of elem to shift down
     * @param len len of an array
     */
    private static void shiftDown(int[] array, int index, int len) {
        while (true) {
            int smallest = index;
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            if (left < len && compare(array, left, smallest)) {
                smallest = left;
            }
            if (right < len && compare(array, right, smallest)) {
                smallest = right;
            }
            if (smallest != index) {
                swap(array, smallest, index);
                index = smallest;
            } else {
                break;
            }
        }
    }

    /**
     * heapSort implementation.
     *
     * @param array array that we sorting
     */
    public static void heapSort(int[] array) {
        int len = array.length;
        for (int i = len / 2 - 1; i >= 0; i--) {
            shiftDown(array, i, len);
        }
        for (int i = len - 1; i > 0; i--) {
            swap(array, 0, i);
            shiftDown(array, 0, i);
        }
    }

}