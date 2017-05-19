package ygh.study.sort;

import java.util.Scanner;

public class MergeSort {

	public static void main(String[] args) {
		int n;
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = sc.nextInt();
		}
		sc.close();
		mergeSort(a, n);
		printArray(a, n);
	}

	public static void printArray(int a[], int n) {
		for (int i = 0; i < n; i++) {
			if (i == n - 1) {
				System.out.print(a[i]);
			} else {
				System.out.print(a[i]+" ");
			}
		}
	}

	/**
	 * Use loop method to implement the merge sort
	 * 
	 * @param a
	 *            A integer array need to sort
	 * 
	 * @param n
	 *            The length of the array
	 */
	public static void mergeSort(int a[], int n) {
		int length;
		length = 1;
		int[] tmpA = new int[n];
		while (length < n) {
			mergePass(a, tmpA, length, n);
			length *= 2;
			mergePass(tmpA, a, length, n);
			length *= 2;
		}
	}

	/**
	 * merge ordered sub-sequence
	 * 
	 * @param a
	 *            original <code>int</code> array to store the elements
	 * @param tmpA
	 *            temporary <code>int</code> array to store the temporary
	 *            elements
	 * @param n
	 *            The length of the a
	 * @param the
	 *            ordered current sub-sequence length
	 */
	public static void mergePass(int[] a, int[] tmpA, int length, int n) {
		int i, j;
		/*
		 * The loop will stop when meet the last two ordered sub-sequence The
		 * rest may be two sub-sequence of one sub-sequence
		 */
		for (i = 0; i <= n - 2 * length; i += length * 2) {
			merge(a, tmpA, i, i + length, i + 2 * length - 1);
		}
		/*
		 * If the rest of is two sub-sequence
		 */
		if (i + length < n) {
			merge(a, tmpA, i, i + length, n - 1);
		} else {
			for (j = i; j < n; j++)
				tmpA[j] = a[j];
		}
	}

	/**
	 * Merge sub-sequence to original array.
	 * 
	 * @param a
	 *            original <code>elementType</code> array to store the elements
	 * @param tmpA
	 *            temporary <code>elementType</code> array to store the
	 *            temporary elements
	 * @param l
	 *            The start index of left sub-sequence
	 * @param r
	 *            The start index of left sub-sequence
	 * @param rightEnd
	 *            The end index of left sub-sequence
	 */
	public static void merge(int[] a, int[] tmpA, int l, int r, int rightEnd) {
		int tmp = l;
		int numCount = rightEnd - l + 1;
		int leftEnd = r - 1;
		while (l <= leftEnd && r <= rightEnd) {
			if (a[l] <= a[r]) {
				tmpA[tmp++] = a[l++];
			} else {
				tmpA[tmp++] = a[r++];
			}
		}

		while (l <= leftEnd) {
			tmpA[tmp++] = a[l++];
		}
		while (r <= rightEnd) {
			tmpA[tmp++] = a[r++];
		}

		for (int i = 0; i < numCount; i++, rightEnd--) {
			a[rightEnd] = tmpA[rightEnd];
		}
	}
}
