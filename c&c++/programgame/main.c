#include <stdio.h>
#define len 10
void quick(int* arr,int left,int right);
void print(int* arr);
void swap(int* arr, int i, int j);
int partition(int* arr, int left, int right);
int hoarePartition(int* arr, int left, int right);
int midPartition(int* arr, int left, int right);
int medianOfThree(int* arr, int left, int right);
int* dutch(int* arr, int left, int right);
int main() {
	int arr[] = {5,1,4,3,2,10,8,7,9,6};
	quick(arr, 0, len - 1);
	printf("\n");
	print(arr);
	return 0;
}


/*
¿ìÅÅ
*/
//quick - start
void quick(int* arr, int left, int right) {
	if (left >= right) return;
	int* pi = dutch(arr, left, right);
	quick(arr, left, pi[0]);
	quick(arr, pi[1], right);
}

int* dutch(int* arr, int left, int right) {
	int pivot = medianOfThree(arr, left, right);
	int pi = arr[pivot];
	int positions[2] = { left - 1,right + 1 };
	for (int i = left; i < positions[1]; i++) {
		if (arr[i] < pi) {
			positions[0]++;
			swap(arr, i, positions[0]);
		}
		else if (arr[i] > pi) {
			positions[1]--;
			swap(arr, i, positions[1]);
			i--;
		}
	}
	printf("dutch");
	print(arr);
	return positions;
}

int midPartition(int* arr, int left, int right) {
	int pivot = medianOfThree(arr, left, right);
	swap(arr, pivot, right);
	return hoarePartition(arr, left, right);
}


int medianOfThree(int* arr, int left, int right) {
	int mid = left + (right - left) / 2;
	if (arr[left] > arr[mid]) swap(arr, left, mid);
	if (arr[left] > arr[right]) swap(arr, left, right);
	if (arr[mid] > arr[right]) swap(arr, mid, right);
	printf("medianOfThree");
	print(arr);
	return mid;
}
int hoarePartition(int* arr, int left, int right) {
	int pivot = arr[left];
	int i = left - 1;
	int j = right + 1;
	while (1)
	{
		do {
			i++;
		} while (arr[i] < pivot);
		do {
			j--;
		} while (arr[j] > pivot);
		if (i < j) {
			swap(arr, i, j);
		}
		else {
			return j;
		}
	}
}
int partition(int* arr, int left, int right) {
	int pivot = arr[right];
	int i = left - 1;
	for (int j = left; j < right; j++) {
		if (arr[j] < pivot) {
			i++;
			swap(arr, i, j);
		}
	}
	swap(arr, i + 1, right);
	return i + 1;
}
void swap(int* arr, int i, int j) {
	int t = arr[i];
	arr[i] = arr[j];
	arr[j] = t;
}
void print(int* arr) {
	for (int i = 0; i < len; i++) {
		printf("%d ", arr[i]);
	}
	printf("\n");
}
// quick - end