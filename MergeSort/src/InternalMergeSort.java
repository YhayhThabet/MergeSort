/*
@author Yhayh Thabet
@assignment project 2
@semester Fall 2019
@class COSC 311
@professor Dr. Haynes
@description Comparing speed of external merge sort and internal merge sort
 */

import java.io.IOException;
import java.util.Random;

public class InternalMergeSort {

	public static void merge_sort(int[] arr, int p, int r) {

		// if index is right
		if (p < r) {
			int mid = (p + r) / 2;

			// Using recursive calls to decide where to start
			merge_sort(arr, p, mid);
			merge_sort(arr, mid + 1, r);
			merge(arr, p, mid, r);
		}
	}

	// merge the two arrays
	public static void merge(int[] arr, int p, int q, int r) {

		int n1 = q - p + 1; // length of left
		int n2 = r - q; // length of right
		int i, j, k;

		int[] left = new int[n1 + 1]; // create two arrays left and right
		int[] right = new int[n2 + 1];

		for (i = 0; i < n1; i++) // fill left with the contents of left subarray of array
			left[i] = arr[p + i];

		for (i = 0; i < n2; i++) // fill right with the contents of right subarray of array
			right[i] = arr[q + i + 1];

		// set the last element to infinity
		left[n1] = Integer.MAX_VALUE;
		right[n2] = Integer.MAX_VALUE;

		i = 0;
		j = 0;

		for (k = p; k <= r; k++) {

			// if left is smaller than right
			if (left[i] <= right[j]) {
				arr[k] = left[i];
				i++;
			}

			// if left is larger than right
			else {
				arr[k] = right[j];
				j++;
			}
		}
	}

	// fills the array
	public static void fill(int[] arr) {
		Random randGen = new Random(97);

		for (int i = 0; i < arr.length; i++) {
			arr[i] = randGen.nextInt(10000);
		}
	}

	// display runtime and dataset size
	public static void display(long time, int size) {

		System.out.println("Runtime is " + time + " milliseconds.");
		System.out.println("Dataset Size: " + size);
	}

	public static void main(String[] args) throws IOException {
		int[] arr = new int[1000000]; // setting arr size

		fill(arr);

		long start = 0, end = 0; // Get time

		start = System.nanoTime();
		merge_sort(arr, 0, arr.length - 1);
		end = System.nanoTime();

		long time = (end - start) / 1000000; // Convert to milliseconds

		display(time, arr.length);
	}
}