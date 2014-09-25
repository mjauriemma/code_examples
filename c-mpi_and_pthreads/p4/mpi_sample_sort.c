
/*******************************************************************
Author:   Matthew Auriemma
Program:  mpi_sample_sort
Function: Sorts a user inputted list of integers with sample sort
input:    size of array, size of sample, array of integers
Output:   Sorted array of integers
Compile:  mpicc -g -Wall -o mpi_sample_sort mpi_sample_sort.c
Run:      mpiexec -n <number of nodes> ./mpi_sample_sort.c
********************************************************************
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <mpi.h>

int Compare(const void* a_p, const void* b_p);
void Sort(int local_A[], int local_n, int my_rank, 
		  int p, MPI_Comm comm);
void Odd_even_iter(int local_A[], int temp_B[], int temp_C[],
				   int local_n, int phase, int even_partner, int odd_partner,
				   int my_rank, int p, MPI_Comm comm);
void Merge_split_low(int local_A[], int temp_B[], int temp_C[], 
					 int local_n);
void Merge_split_high(int local_A[], int temp_B[], int temp_C[], 
					  int local_n);
void Print_list(int global_lst[], int global_sz, int recvcounts[], int p);

int main (int argc, char *argv[]){
	/* Variable Declarations */

	int 	     p, my_rank;
	int 	     i, n, s, temp;
	int 	     *list, *local_list;
	int 	     splitter, *AllSplitter;
	int 	     *local_bucket;
	int        subscript;
	int          *big_bucket, *displs, *recvcounts;

	MPI_Comm comm;
	MPI_Status  status; 

	/**** Initialising ****/

	MPI_Init(&argc, &argv);
	comm = MPI_COMM_WORLD;
	MPI_Comm_size(comm, &p);
	MPI_Comm_rank(comm, &my_rank);

	AllSplitter = (int *) malloc (p * sizeof (int));
	big_bucket = (int *) malloc (n * sizeof (int));

	/**** Reading list ****/

	if (my_rank == 0){

		printf("Please Enter List size: \n");
		scanf("%d", &n);
		printf("Please Enter Sample Size: \n");
		scanf("%d", &s);
		list = malloc(sizeof(int) * n);
		printf("Please Enter List to be Sorted: \n");
		for (i = 0; i < n; i++){
			scanf("%d", &list[i]);
		}

	}

	/**** Sending Data ****/
	//MPI_Scatter (&glob_n, 1, MPI_INT, &n, 1, MPI_INT, 0, comm);
	//MPI_Scatter (&glob_s, 1, MPI_INT, &s, 1, MPI_INT, 0, comm);
	MPI_Bcast(&n, 1, MPI_INT, 0, comm);
	MPI_Bcast(&s, 1, MPI_INT, 0, comm);

	int local_n = n / p;

	local_list = malloc (local_n * sizeof (int));

	MPI_Scatter(list, local_n, MPI_INT, local_list, 
		local_n, MPI_INT, 0, comm);




	int local_s = s/p;

	srandom(my_rank + 1);
	int *local_sample = (int *) malloc (local_s * sizeof (int));
	for (i = 0; i < local_s; i++){
		subscript = random() % local_n;
		local_sample[i] = subscript;
	} 
	Sort(local_sample, local_s, my_rank, p, comm);

	MPI_Sendrecv(&local_sample[local_s-1], 1, MPI_INT, (my_rank+1) % p, 0,
		&temp, 1, MPI_INT, (my_rank+(p-1)) % p, 0, comm,
		&status);


	if (my_rank == 0){
		splitter = 0;
	}

	if (my_rank != 0){
		splitter = (temp + local_sample[0])/2;
	}

	AllSplitter = (int *) malloc (p * sizeof (int));

	MPI_Allgather(&splitter, 1, MPI_INT, AllSplitter, 1, MPI_INT, 
		comm);

	qsort(local_list, local_n, sizeof(int), Compare);

	int start = 0;
	int rank = 0;
	local_bucket = (int *) malloc (sizeof (int) * (local_n));
	int lbc = 0;
	int flag;

	while (rank < p){
		if(my_rank == rank){
			if(my_rank == p-1){
				while(start<local_n){
					if (local_n < lbc){
						local_bucket = realloc(local_bucket, sizeof(int) * lbc);
					}
					local_bucket[lbc] = local_list[start];
					start++;
					lbc++;
				}
			}
			else{
				while(local_list[start] < AllSplitter[rank+1] && start < local_n){
					if (local_n < lbc){
						local_bucket = realloc(local_bucket, sizeof(int) * lbc);
					}
					local_bucket[lbc] = local_list[start];
					start++;
					lbc++;
				}
			}
		}
		else{
			if (rank<p-1){
				while(local_list[start] < AllSplitter[rank+1] && start < local_n){
					MPI_Send(&local_list[start], 1, MPI_INT, rank, 0, comm);
					start++;
				}
			}
			else{
				while (local_list[start]>= AllSplitter[rank] && start < local_n){
					MPI_Send(&local_list[start], 1, MPI_INT, rank, 0, comm);
					start++;
				}
			}
		}
		rank++;
	}

	MPI_Barrier(comm);
	for (rank = 0; rank < p; rank++){
		MPI_Iprobe(rank, 0, comm, &flag, &status);
		while (flag == 1){
			if (local_n < lbc){
				local_bucket = realloc(local_bucket, sizeof(int) * lbc);
			}
			MPI_Recv(&local_bucket[lbc], 1, MPI_INT, rank, 0, comm, &status);
			lbc++;
			MPI_Iprobe(rank, 0, comm, &flag, &status);
		}
	}


	qsort(local_bucket, lbc, sizeof(int), Compare);



	recvcounts = malloc(sizeof(int)*p);
	displs = malloc(sizeof(int)*p);

	if (my_rank != 0){
		MPI_Send(&lbc, 1 , MPI_INT, 0, 0, comm);
	}
	else{

		recvcounts[0] = lbc;
		for (i=1; i<p; i++){
			MPI_Recv(&recvcounts[i], 1, MPI_INT, i, 0, comm, &status);

		}
		int k;
		k = 0;
		for (i=0; i<p; i++){
			if(i>0){
				k = k+recvcounts[i-1];
			}
			displs[i] = k;
		}

	}
	MPI_Gatherv(local_bucket, lbc, MPI_INT, big_bucket, recvcounts, 
		displs, MPI_INT, 0, comm);

	MPI_Barrier(comm);
	if (my_rank == 0){
		Print_list(big_bucket, n, recvcounts, p);
	}

	MPI_Finalize();
	return 0;
}

/*---------------------------------------------------------------------
* Function:  Print_list
* Purpose:   Print a list of ints
* In args:   all
*/
void Print_list(int global_lst[], int n, int recvcounts[], int p) {
	int i, j,k, start;
	k = recvcounts[0];
	start = 0;
	for (j = 0; j < p; j++){
		printf("Proc %d > ", j);
		if(j>0){
			start = start + recvcounts[j-1];
			k = k+recvcounts[j];
		}
		for (i = start; i < k; i++){
			printf("%d ", global_lst[i]);
		}
		printf("\n");
	}
}  /* Print_list */


/*-------------------------------------------------------------------
* Function:    Compare
* Purpose:     Compare 2 ints, return -1, 0, or 1, respectively, when
*              the first int is less than, equal, or greater than
*              the second.  Used by qsort.
*/
int Compare(const void* a_p, const void* b_p) {
	int a = *((int*)a_p);
	int b = *((int*)b_p);

	if (a < b)
		return -1;
	else if (a == b)
		return 0;
	else /* a > b */
		return 1;
}  /* Compare */


/*-------------------------------------------------------------------
* Function:    Sort
* Purpose:     Use odd-even sort to sort global list.
* Input args:  local_n, my_rank, p, comm
* In/out args: local_A 
*/
void Sort(int local_A[], int local_n, int my_rank, 
		  int p, MPI_Comm comm) {
			  int phase;
			  int *temp_B, *temp_C;
			  int even_partner;  /* phase is even or left-looking */
			  int odd_partner;   /* phase is odd or right-looking */

			  /* Temporary storage used in merge-split */
			  temp_B = (int*) malloc(local_n*sizeof(int));
			  temp_C = (int*) malloc(local_n*sizeof(int));

			  /* Find partners:  negative rank => do nothing during phase */
			  if (my_rank % 2 != 0) {
				  even_partner = my_rank - 1;
				  odd_partner = my_rank + 1;
				  if (odd_partner == p) odd_partner = -1;  // Idle during odd phase
			  } else {
				  even_partner = my_rank + 1;
				  if (even_partner == p) even_partner = -1;  // Idle during even phase
				  odd_partner = my_rank-1;  
			  }

			  /* Sort local list using built-in quick sort */
			  qsort(local_A, local_n, sizeof(int), Compare);

			  for (phase = 0; phase < p; phase++)
				  Odd_even_iter(local_A, temp_B, temp_C, local_n, phase, 
				  even_partner, odd_partner, my_rank, p, comm);

			  free(temp_B);
			  free(temp_C);
}  /* Sort */


/*-------------------------------------------------------------------
* Function:    Odd_even_iter
* Purpose:     One iteration of Odd-even transposition sort
* In args:     local_n, phase, my_rank, p, comm
* In/out args: local_A
* Scratch:     temp_B, temp_C
*/
void Odd_even_iter(int local_A[], int temp_B[], int temp_C[],
				   int local_n, int phase, int even_partner, int odd_partner,
				   int my_rank, int p, MPI_Comm comm) {
					   MPI_Status status;

					   if (phase % 2 == 0) {  /* Even phase, odd process <-> rank-1 */
						   if (even_partner >= 0) {
							   MPI_Sendrecv(local_A, local_n, MPI_INT, even_partner, 0, 
								   temp_B, local_n, MPI_INT, even_partner, 0, comm,
								   &status);
							   if (my_rank % 2 != 0)
								   Merge_split_high(local_A, temp_B, temp_C, local_n);
							   else
								   Merge_split_low(local_A, temp_B, temp_C, local_n);
						   }
					   } else { /* Odd phase, odd process <-> rank+1 */
						   if (odd_partner >= 0) {
							   MPI_Sendrecv(local_A, local_n, MPI_INT, odd_partner, 0, 
								   temp_B, local_n, MPI_INT, odd_partner, 0, comm,
								   &status);
							   if (my_rank % 2 != 0)
								   Merge_split_low(local_A, temp_B, temp_C, local_n);
							   else
								   Merge_split_high(local_A, temp_B, temp_C, local_n);
						   }
					   }
}  /* Odd_even_iter */


/*-------------------------------------------------------------------
* Function:    Merge_split_low
* Purpose:     Merge the smallest local_n elements in local_A 
*              and temp_B into temp_C.  Then copy temp_C
*              back into local_A.
* In args:     local_n, temp_B
* In/out args: local_A
* Scratch:     temp_C
*/
void Merge_split_low(int local_A[], int temp_B[], int temp_C[], 
					 int local_n) {
						 int ai, bi, ci;

						 ai = 0;
						 bi = 0;
						 ci = 0;
						 while (ci < local_n) {
							 if (local_A[ai] <= temp_B[bi]) {
								 temp_C[ci] = local_A[ai];
								 ci++; ai++;
							 } else {
								 temp_C[ci] = temp_B[bi];
								 ci++; bi++;
							 }
						 }

						 memcpy(local_A, temp_C, local_n*sizeof(int));
}  /* Merge_split_low */

/*-------------------------------------------------------------------
* Function:    Merge_split_high
* Purpose:     Merge the largest local_n elements in local_A 
*              and temp_B into temp_C.  Then copy temp_C
*              back into local_A.
* In args:     local_n, temp_B
* In/out args: local_A
* Scratch:     temp_C
*/
void Merge_split_high(int local_A[], int temp_B[], int temp_C[], 
					  int local_n) {
						  int ai, bi, ci;

						  ai = local_n-1;
						  bi = local_n-1;
						  ci = local_n-1;
						  while (ci >= 0) {
							  if (local_A[ai] >= temp_B[bi]) {
								  temp_C[ci] = local_A[ai];
								  ci--; ai--;
							  } else {
								  temp_C[ci] = temp_B[bi];
								  ci--; bi--;
							  }
						  }

						  memcpy(local_A, temp_C, local_n*sizeof(int));
}  /* Merge_split_low */

