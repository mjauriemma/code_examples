/* File:  
 *    pth_seq.c
 *
 * Purpose:
 *    Use condition wait to serialize threads.
 *
 * Input:
 *    none
 * Output:
 *    A message from each thread.  The messages are printed in thread rank
 *    order:  first thread 0's message, then thread 1's, etc.
 *
 * Compile: 
*     gcc -g -Wall -o pth_seq pth_seq.c -lpthread
 *
 * Usage:
 *    pth_seq <thread_count>
 *
 * Note:
 *    Added a counter in the Thread_work to update current_thread so 
 *    the program would no longer hang
 */

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>


int thread_count;
long current_thread = 0;
pthread_mutex_t sequence_mutex;
pthread_cond_t ok_to_proceed;

void Usage(char* prog_name);
void *Thread_work(void* rank);

/*--------------------------------------------------------------------*/
int main(int argc, char* argv[]) {
   long       thread;
   pthread_t* thread_handles; 

   if (argc != 2) Usage(argv[0]);
   thread_count = strtol(argv[1], NULL, 10);

   thread_handles = malloc (thread_count*sizeof(pthread_t));
   pthread_mutex_init(&sequence_mutex, NULL);
   pthread_cond_init(&ok_to_proceed, NULL);

   for (thread = 0; thread < thread_count; thread++)
      pthread_create(&thread_handles[thread], (pthread_attr_t*) NULL,
          Thread_work, (void*) thread);

   for (thread = 0; thread < thread_count; thread++) {
      pthread_join(thread_handles[thread], NULL);
   }

   pthread_mutex_destroy(&sequence_mutex);
   pthread_cond_destroy(&ok_to_proceed);
   free(thread_handles);
   return 0;
}  /* main */


/*--------------------------------------------------------------------
 * Function:    Usage
 * Purpose:     Print command line for function and terminate
 * In arg:      prog_name
 */
void Usage(char* prog_name) {

   fprintf(stderr, "usage: %s <number of threads>\n", prog_name);
   exit(0);
}  /* Usage */


/*-------------------------------------------------------------------
 * Function:    Thread_work
 * Purpose:     Serialize the threads:  first thread 0 prints the
 *              message, then thread 1, etc.
 * In arg:      rank
 * Global var:  thread_count, current_thread, sequence_mutex,
 *              ok_to_proceed
 * Return val:  Ignored
 */
void *Thread_work(void* rank) {
   long my_rank = (long) rank; 

   while(1) {
      pthread_mutex_lock(&sequence_mutex);
      if (current_thread == my_rank) {
         printf("Hello from thread %ld of %d\n", my_rank, thread_count);
	 fflush(stdout);
         pthread_cond_broadcast(&ok_to_proceed);
         pthread_mutex_unlock(&sequence_mutex);
         current_thread ++;
	 break;
      } else {
         // Wait unlocks mutex and puts thread to sleep.
         //    Put wait in while loop in case some other
         // event awakens thread.
         while (pthread_cond_wait(&ok_to_proceed,
                   &sequence_mutex) != 0);
         // Mutex is relocked at this point.
      }
      pthread_mutex_unlock(&sequence_mutex);
   }

   return NULL;
}  /* Thread_work */