/************************
 * File:	pth_lcs.c
 *
 * Purpose:	Calculate the longest common subsequence 
 *			of 2 sequences
 *
 * Compile: gcc -g -Wall -o pth_lcs pth_lcs.c -lpthread
 *
 * Run:		./pth_lcs <number of threads>
 *
 * Input:	none
 *
 * Output:	LCS of 2 user given sequences
 *
 * Usage:	./pth_lcs <number of threads>
 *
 * Note: 	This program is potentially only partially
 *			functional
 */


#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

/* Global Variables */
int *A;
int *B;
int *L;
int m, n;
int thread_count;
pthread_barrier_t barrier;
int count;

/* Func Decs */
int Compute_L(int row, int col);
void *Thread_work(void* rank);
void Read();
void Usage(char* prog_name);
int Compute_col(int diag, int diag_entry);
int Compute_row(int diag, int diag_entry);
int Get_diag_len(int diag);
void Print();

/*
* Main
*/
int main(int argc, char* argv[]) {
	long  thread;
	pthread_t* thread_handles; 
	int i, smaller;
	count = 0;
	/* Checks if thread num was input else exits */
   	if (argc != 2) Usage(argv[0]);
   	Read();
   	/* Instantiate Thread Count */
   	thread_count = strtol(argv[1], NULL, 10);

	/* Inits Barrier */
	pthread_barrier_init(&barrier, NULL, thread_count);

	/* Create threads, call threadwork, and join threads */
	thread_handles = malloc (thread_count*sizeof(pthread_t));
	for (thread = 0; thread < thread_count; thread++)
		pthread_create(&thread_handles[thread], NULL, 
				Thread_work, (void*) thread);
	for (thread = 0; thread < thread_count; thread++)
		pthread_join(thread_handles[thread], NULL);
	
	/* Print out L */
	for (i = 0; i < smaller; i++) {
		printf("%d, ", L[i]);
	}
	printf("\n");
	
	if (count == m){
		int i;
		for (i = 0; i < count; i++) {
			printf("%d, ", A[i]);
		}
	}elseif (count == n){
		int i;
		for (i = 0; i < count; i++) {
			printf("%d, ", B[i]);
		}
	}else{
		int* temp;
		temp = malloc(sizeof(int)*count);
		while (count > 0){
			
		}
	}
	printf("\n");

	/*Destroy and free global variables */
	pthread_barrier_destroy(&barrier);
	free(thread_handles);
	free(A);
	free(B);
	free(L);
	return 0;
}
/*--------------------------------------------------------------------
 * Function:    Usage
 * Purpose:     Print command line for function and terminate
 * In arg:      prog_name
 */
void Usage(char* prog_name) {
   fprintf(stderr, "usage: %s <number of threads>\n", prog_name);
   exit(0);
}  /* Usage */

/*--------------------------------------------------------------------
 * Function:	Read
 * Purpose:     Reads user input from scanf and 
 *				mallocs L
 */
 void Read(){
 	/* Gather user input for sequences, m, and n */
	printf("Input the length of the first sequence\n");
	scanf("%d", &m);
	A = malloc(m * sizeof(int));
	printf("Input the values of the first sequence\n");
	for (i = 0; i < m; i++) {
		scanf("%d", &A[i]);
	}
	printf("Input the length of the second sequence\n");
	scanf("%d", &n);
	B = malloc(n * sizeof(int));
	printf("Input the values of the second sequence\n");
	for (i = 0; i < n; i++) {
		scanf("%d", &B[i]);
	}
	/* Decides whether to use m or n for sizeof L */
	if (m > n) {
		smaller = n;
	} else {
		smaller = m;
	}

	/* Malloc L and to either m or n */
	L = malloc(smaller * sizeof(int));
 }

/*--------------------------------------------------------------------
 * Function:	Thread_work
 * Purpose:     Iterates through diagonals and computes calls
 *				calls compute for row, column, and L to populate
 *				the LCS.
 * In arg:		rank
 */
void *Thread_work(void* rank) {
	long my_rank = (long) rank;	
	int diag_count = m + n;
	int diag_entry;
	int diag;
	int diag_len, row, col;
	for (diag = 1; diag < diag_count; diag++) {
		diag_len = Get_diag_len(diag);
			for (diag_entry = my_rank; diag_entry < diag_len ; 
					diag_entry = diag_entry + thread_count) {
				row = Compute_row(diag, diag_entry);
				col = Compute_col(diag, diag_entry);
				printf("rank = %d, row = %d, col = %d, diag = %d, diag_entry = %d, diag_len = %d\n", 
					my_rank, row, col, diag, diag_entry, diag_len);
				L[row, col] = Compute_L(row, col);
			}
		pthread_barrier_wait(&barrier);
	}
}
/*-------------------------------------------------------------------
 * Function:	Get_diag_len
 * Purpose:	Return the length of the current diagonal
 * In arg:	diag
 * Returns:	the length of diag
 */
int Get_diag_len(int diag) {
	if (diag <= n) {
		return diag;
	} else {
		return n + m - diag;
	}
}
/*-------------------------------------------------------------------
 * Function:	Compute_row
 * Purpose:	Return the row number
 * In arg:	diag, diag_entry
 * Returns:	row number
 */
int Compute_row(int diag, int diag_entry) {
	if (diag <= m) {
		return diag - (diag_entry + 1);
	} else {
		return m - 1 - diag_entry;
	}
}
/*-------------------------------------------------------------------
 * Function:	Compute_col
 * Purpse:	Return the column number
 * In arg:	diag, diag_entry
 * Returns:	column number
 */
int Compute_col(int diag, int diag_entry) {
	if (diag <= n) {
		return diag - (diag_entry+1);
	} else {
		return diag - m + 1 + diag_entry;
	}
}
/*-------------------------------------------------------------------
 * Function:	Compute_L
 * Purpose:	compute the value of that L
 * In arg:	row, col
 */
int Compute_L(int i, int j) {
	int len1 = 0;
	int len2 = 0;
	printf("row = %d, col = %d\n", i,j);
	if (A[i] == B[j]) {
		return A[i];//L[i - 1, j -1] + A[i];
		count++;
	}
	if (i == 0 || j == 0) return 0; 
	else {
		len1 = L[i, j-1];
		len2 = L[i-1, j];
		if (len1 > len2) {
			return L[i, j-1];
		} else {
			return L[i-1, j];
		}
	}
}

/*-------------------------------------------------------------------
 * Function: Print
 * Purpose:	 Print out L
 * In arg:	 row, col
 */
 void Print(){
    i = m;
    int j = n;
    while (i >= 0) {
        while (j >= 0) {
            if (L[i, j-1] != L[i, j]) {
                printf("%d\n", L[i, j]);
                i--;
                j--;
            } else {
                j--;
            }
        }
	    i--;
    }
	printf("\n");
 }