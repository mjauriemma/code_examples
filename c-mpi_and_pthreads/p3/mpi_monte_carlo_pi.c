/* File:    mpi_monte_carlo_pi.c
*  Purpose: Compute the value of pi by estimation
*  Compile: mpicc -g -Wall -o mpi_monte_carlo_pi mpi_monte_carlo_pi.c -lm 
*  Note:    Can choose to define a type of communication or defaults to 
*			Allreduce
*  Run:     mpiexec -n <#ofprocesses> ./mpi_monte_carlo_pi <value of n>
*  
*  Input:   Command line arg for value of n
*  Output:  Darts in circle, n, estimated value and actual value of pi, runtime
*/

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <mpi.h>
#include "timer.h"

float global_calc (long long int n, long long int in_circle, 
				   int my_rank, MPI_Comm comm, int p, float ans);
long long int monte_carlo_pi (long long int n, int my_rank, 
							  long long int in_circle);
double pi_val(void);

int main (int argc, char *argv[]){
	MPI_Comm comm;
	int p, my_rank;
	long long int n;
	long long int local_n;
	float ans=0;
	long long int in_circle=0;
	double start, finish, elapsed;
	float pi,err;



	MPI_Init(&argc, &argv);
	comm = MPI_COMM_WORLD;
	MPI_Comm_size(comm, &p);
	MPI_Comm_rank(comm, &my_rank);

	if (my_rank == 0){
		if (argc>1) {
			n = strtol(argv[1], NULL, 10);
		}
		else {
			n = 0;
		}
	}
	MPI_Bcast(&n, 1, MPI_LONG_LONG, 0, comm);
	if (n <= 0){
		if (my_rank == 0){
			printf("Enter the value for n: mpiexec -n <number of nodes> "); 
			printf("./mpi_monte_carlo_pi <value for n>. \nProgram will Terminate\n");
		}
		MPI_Finalize();
	}

	GET_TIME(start);
	local_n = n/p;
	in_circle = monte_carlo_pi(local_n, my_rank, in_circle);
	ans = global_calc(n, in_circle, my_rank, comm, p, ans);
	pi = pi_val();
	err = pi - ans;
	GET_TIME(finish);
	elapsed = finish-start;

	if (my_rank ==0){
		printf ("Estimated Value of Pi for %lld 'darts': %f\n",n,ans);
		printf ("Actual Value of Pi: %f\n", pi);
		printf ("Difference between Estimated and Actual: %f\n",err);
		printf ("Time: %f\n", elapsed);
	}

	MPI_Finalize();
	return 0;
}
 /* main */

/******************************************************************************
*Function: monte_carlo_pi
*Purpose:  generate random numbers on a cartesian plane and calculate how many
*		   are contained in the unit circle
*in-args:  number of darts to throw, rank of process, local number of darts in 
*          the unit circle (starts at 0)
*out-args: number of darts thrown that land in the unit circle
*/
long long int monte_carlo_pi (long long int n, int my_rank, 
							  long long int in_circle) {
								  float x, y;
								  srandom(my_rank+1);
								  int i;
								  for (i = 0; i < n; i++) {
									  //x = ((float)rand()/
									  //(float)RAND_MAX -1);
									  //y = ((float)rand()/
									  //(float)RAND_MAX - 1);
									  x = random()/((double) RAND_MAX);
									  x = 2.0*x - 1.0;
									  y = random()/((double) RAND_MAX);
									  y = 2.0*x - 1.0;
									  if ((x*x) + (y*y)<= 1 ){
										  in_circle ++;
									  }
								  }
								  return in_circle;}		/* monte_carlo_pi *//*******************************************************************************Function: global_calc*Purpose:  Compute the estimated value of pi by combining multiple processes's *          values of darts 'in circle'*in-args:  number of darts, local number of darts in the circle, rank of the*		   process, mpi_comm, number of processes, and a float for the answer*out-args: Estimate of pi*Note:*		   User can specify type of MPI_COMM used, or defaults to Allreduce*Note:*		   BFLY does not function*/float global_calc (long long int n, 				   long long int in_circle, 				   int my_rank, 				   MPI_Comm comm, int p,				   float ans){					   long long int glob_in_circ = 0; 					   #ifdef LOOP
					   long long int temp;
					   if (my_rank == 0) {
						   glob_in_circ = in_circle;
						   int q;
						   for (q = 1; q < p; q++) {
							   MPI_Recv(&temp, 1, MPI_LONG_LONG, q, 0, 
								   comm, MPI_STATUS_IGNORE);
							   glob_in_circ += in_circle;
						   }
					   }else{
						   MPI_Send(&in_circle, 1, MPI_LONG_LONG, 0, 0, comm);
					   }



#elif defined TREE
					   long long int temp;
					   int partner;
					   int done = 0;
					   unsigned bitmask = (unsigned) 1;


					   int my_pass = -1;
					   partner = -1;

					   while (!done && bitmask < p) {
						   partner = my_rank ^ bitmask;

						   my_pass++;

						   if (my_rank < partner) {
							   if (partner < p) {
								   MPI_Recv(&temp, 1, MPI_LONG_LONG, partner, 0, comm, 
									   MPI_STATUS_IGNORE);
								   glob_in_circ += temp;
							   }
							   bitmask <<= 1;
						   } else {
							   MPI_Send(&in_circle, 1, MPI_LONG_LONG, partner, 0, comm); 
							   done = 1;
						   }
					   }



#elif defined BFLY

# else
					   MPI_Allreduce(&in_circle, &glob_in_circ, 1, 
						   MPI_LONG_LONG, MPI_SUM, comm);

#endif					   if (my_rank == 0){						   printf ("'Darts' on the Dartboard: %lld\n", 							   glob_in_circ);					   }					   ans = 4.0*glob_in_circ/n;					   return ans;} /* global_calc *//**************************************************************************** Function: pi_val* Purpose:  Calculate actual Value for pi* Output:   Actual value for pi*/double pi_val(void){	double temp;	temp = 4.0*atan(1.0);	return temp;}/* pi_val */