import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

public class Sorting {

  /**
   * Implement merge sort.
   *
   * It should be:
   * out-of-place
   * stable
   * not adaptive
   *
   * Have a worst case running time of: O(n log n)
   * And a best case running time of: O(n log n)
   *
   * You can create more arrays to run merge sort, but at the end, everything
   * should be merged back into the original T[] which was passed in.
   *
   * When splitting the array, if there is an odd number of elements, put the
   * extra data on the right side.
   *
   * Hint: You may need to create a helper method that merges two arrays
   * back into the original T[] array. If two data are equal when merging,
   * think about which subarray you should pull from first.
   *
   * You may assume that the passed in array and comparator are both valid
   * and will not be null.
   *
   * @param <T>        Data type to sort.
   * @param arr        The array to be sorted.
   * @param comparator The Comparator used to compare the data in arr.
   */
  public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
    int length = arr.length;

    if (length < 2) {
      return;
    }

    T[] leftArr = createSubarray(arr, 0, length / 2);
    T[] rightArr = createSubarray(arr, length / 2, length);

    mergeSort(leftArr, comparator);
    mergeSort(rightArr, comparator);

    int leftIndx = 0;
    int rightIndx = 0;
    int currIndx = 0;
    while (leftIndx < leftArr.length && rightIndx < rightArr.length) {
      if (comparator.compare(leftArr[leftIndx], rightArr[rightIndx]) <= 0) {
        arr[currIndx] = leftArr[leftIndx];
        leftIndx++;
      } else {
        arr[currIndx] = rightArr[rightIndx];
        rightIndx++;
      }
      currIndx++;
    }

    while (leftIndx < leftArr.length) {
      arr[currIndx] = leftArr[leftIndx];
      leftIndx++;
      currIndx++;
    }

    while (rightIndx < rightArr.length) {
      arr[currIndx] = rightArr[rightIndx];
      currIndx++;
      rightIndx++;
    }
  }

  private static <T> T[] createSubarray(T[] arr, int start, int end) {
    T[] subArr = (T[]) new Comparable[end - start];
    int n = 0;
    for (int i = start; i < end; i++) {
      subArr[n] = arr[i];
      n++;
    }
    return subArr;
  }

  /**
   * Implement LSD (least significant digit) radix sort.
   *
   * It should be:
   * out-of-place
   * stable
   * not adaptive
   *
   * Have a worst case running time of: O(kn)
   * And a best case running time of: O(kn)
   *
   * Feel free to make an initial O(n) passthrough of the array to
   * determine k, the number of iterations you need.
   *
   * At no point should you find yourself needing a way to exponentiate a
   * number; any such method would be non-O(1). Think about how how you can
   * get each power of BASE naturally and efficiently as the algorithm
   * progresses through each digit.
   *
   * You may use an ArrayList or LinkedList if you wish, but it should only
   * be used inside radix sort and any radix sort helpers. Do NOT use these
   * classes with merge sort. However, be sure the List implementation you
   * choose allows for stability while being as efficient as possible.
   *
   * Do NOT use anything from the Math class except Math.abs().
   *
   * You may assume that the passed in array is valid and will not be null.
   *
   * @param arr The array to be sorted.
   */
  public static void lsdRadixSort(int[] arr) {
    if (arr.length < 2) {
      return;
    }

    // create array of linkedlists
    LinkedList<Integer>[] buckets = new LinkedList[19];
    for (int i = 0; i < buckets.length; i++) {
      buckets[i] = new LinkedList<>();
    }

    // find number with max number of digits
    int largestAbs = Math.abs(arr[0]);
    for (int i = 1; i < arr.length; i++) {
      if (Math.abs(arr[i]) > largestAbs) {
        largestAbs = Math.abs(arr[i]);
      }
    }

    // calculate max number of digits
    int maxDigits = Integer.toString(largestAbs).length();

    int k = 0;
    while (k < maxDigits) {
      // partition numbers into buckets
      for (int i = 0; i < arr.length; i++) {
        int digit = (int) (arr[i] / Math.pow(10, k)) % 10;
        buckets[digit + 9].addFirst(arr[i]);
      }

      // empty buckets back into array
      int j = 0;
      for (int i = 0; i < buckets.length; i++) {
        while (buckets[i].size() > 0) {
          arr[j] = buckets[i].removeLast();
          j++;
        }
      }

      k++;
    }
  }
}