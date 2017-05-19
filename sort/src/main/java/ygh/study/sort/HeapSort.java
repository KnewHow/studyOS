package ygh.study.sort;

import java.util.Scanner;

public class HeapSort {

	public static void main(String[] args) {
		int n;
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = sc.nextInt();
		}
		sc.close();
		heapSort(a, n);
		printArray(a, n);
	}

	public static void printArray(int a[], int n) {
		for (int i = 0; i < n; i++) {
			if (i == n - 1) {
				System.out.print(a[i]);
			} else {
				System.out.print(a[i] + " ");
			}
		}
	}

	public static void heapSort(int[] a, int n) {
		int i, tmp;
		for (i = n / 2 - 1; i >= 0; i--) {
			percDowm(a, i, n);
		}
		for (i = n - 1; i > 0; i--) {
			tmp = a[i];
			a[i] = a[0];
			a[0] = tmp;
			percDowm(a, 0, i);
		}
	}

	public static void percDowm(int[] a, int p, int n) {
		int parent = 0, child = 0;
		int x = a[p];
		for (parent = p; (parent * 2 + 1) < n; parent = child) {
			child = parent * 2 + 1;
			if ((child != n - 1) && a[child] < a[child + 1]) {
				child++;
			}
			if (x >= a[child]) {
				break;
			} else {
				a[parent] = a[child];
			}
		}
		a[parent] = x;
	}
}
