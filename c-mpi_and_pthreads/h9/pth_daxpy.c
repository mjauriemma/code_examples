/* File:     
 *    pth_daxpy.c 
 *
 * Purpose:  
 *     Computes the daxpy.  Vectors are distributed by blocks.
 *
 * Input:
 *     n: dimension of vector
 *     x, y: the vectors to be multiplied
 *     alpha: Double being multiplied into x
 *
 * Output:
 *     y: the product vector
 *
 * Compile:  gcc -g -Wall -o pth_daxpy pth_daxpy.c -lpthread
 * Usage:
 *     pth_daxpy <thread_count>
 *
 */

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

/* Global variables */
int     thread_count;
int     n;
double* alpha;
double* x;
double* y;

/* Serial functions */
void Usage(char* prog_name);
void Read_vector(char* prompt, double x[], int n);
void Print_vector(char* title, double y[], int n);

/* Parallel function */
void *Pth_daxpy(void* rank);

/*------------------------------------------------------------------*/
int main(int argc, char* argv[]) {
   long       thread;
   pthread_t* thread_handles;

   if (argc != 2) Usage(argv[0]);
   thread_count = strtol(argv[1], NULL, 10);
   thread_handles = malloc(thread_count*sizeof(pthread_t));

   printf("Enter n\n");
   scanf("%d", &n);

   printf("Enter alpha\n");
   scanf("%d", &alpha);
   
   x = malloc(n*sizeof(double));
   y = malloc(n*sizeof(double));
   
   Read_vector("Enter x", x, n);
   Print_vector("We read", x, n);

   Read_vector("Enter y", y, n);
   Print_vector("We read", y, n);

   for (thread = 0; thread < thread_count; thread++)
      pthread_create(&thread_handles[thread], NULL,
         Pth_daxpy, (void*) thread);

   for (thread = 0; thread < thread_count; thread++)
      pthread_join(thread_handles[thread], NULL);

   Print_vector("The DAXPY is", y, n);

   free(alpha);
   free(x);
   free(y);
   free(thread_handles);

   return 0;
}  /* main */


/*------------------------------------------------------------------
 * Function:  Usage
 * Purpose:   print a message showing what the command line should
 *            be, and terminate
 * In arg :   prog_name
 */
void Usage (char* prog_name) {
   fprintf(stderr, "usage: %s <thread_count>\n", prog_name);
   exit(0);
}  /* Usage */


/*------------------------------------------------------------------
 * Function:        Read_vector
 * Purpose:         Read in the vector x
 * In arg:          prompt, n
 * Out arg:         x
 */
void Read_vector(char* prompt, double x[], int n) {
   int   i;

   printf("%s\n", prompt);
   for (i = 0; i < n; i++) 
      scanf("%lf", &x[i]);
}  /* Read_vector */


/*------------------------------------------------------------------
 * Function:       Pth_daxpy
 * Purpose:        Multiply an n dimensional vector by alpha and add
				   it to another vector;
 * In arg:         rank
 * Global in vars: x, y, alpha, n, thread_count
 * Global out var: y
 */
void *Pth_daxpy(void* rank) {
   long my_rank = (long) rank;
   int i;
   int local_n = n/thread_count; 
   int my_first_row = my_rank*local_n;
   int my_last_row = my_first_row + local_n - 1;

    for (i = my_first_row; i < my_last_row; i++){
         y[i] += alpha*(x[i]);
	}
   return NULL;
}  /* Pth_mat_vect */


/*------------------------------------------------------------------
 * Function:    Print_vector
 * Purpose:     Print a vector
 * In args:     title, y, n
 */
void Print_vector(char* title, double y[], int n) {
   int   i;

   printf("%s\n", title);
   for (i = 0; i < n; i++)
      printf("%4.1f ", y[i]);
   printf("\n");
}  /* Print_vector */