public class Main {
    private static void swap(int[] array, int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    private static boolean compare(int[] array, int index1, int index2) {
        return array[index1] < array[index2];
    }

    private void shiftDown(int[] array, int index, int len) {
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
            }
            else {
                break;
            }
        }
    }

    public static void heapSort(int[] array) {
        int len = array.length;
        for (int i = len / 2 - 1; i >= 0; i--) {
            shiftDown(array, i, n);
        }
        for (int i = n - 1; i > 0; i--) {
            swap(array, 0, i);
            shiftDown(array, 0, i);
        }
    }

    public static void main(String )
}
