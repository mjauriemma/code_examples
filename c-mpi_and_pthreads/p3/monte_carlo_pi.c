/* File:    monte_carlo_pi.c
*  Purpose: Compute the value of pi by estimation
*  Compile: gcc -g -Wall -o monte_carlo_pi monte_carlo_pi.c -lm 
			Allreduce
*  Run:     ./monte_carlo_pi <value of n>
*  
*  Input:   Command line arg for value of n
*  Output:  Darts in circle, n, estimated value and actual value of pi, runtime
*/

#include <stdio.h>
#include <stdlib.h>
#include "timer.h"


float monte_carlo_pi_serial (long long int n);
double pi_val(void);

int main (int argc, char *argv[]) {
	long long int n;
	float ans;
	double start, finish, elapsed;
	double pi, err;

	if (argc<2){
		printf("No value provided for n.\nProgram will terminate\n");
		return 0;
	}else{
		if (argv[1] <= 0){
			printf("Invalid value for n. n must be > 0\n");
			return 0;
		}else{

			GET_TIME(start); 
			n = strtol(argv[1], NULL, 10);
			ans = monte_carlo_pi_serial(n);
			pi = pi_val();
			err = pi - ans;
			GET_TIME(finish);
			elapsed = finish-start;

			printf ("Estimated Value of Pi for %lld 'darts': %f\n",n,ans);
			printf ("Actual Value of Pi: %f\n", pi);
			printf ("Difference between Estimated and Actual: %f\n",err);
			printf ("Time: %f\n", elapsed);

		}
		return 0;	
	}
} /* main */

/******************************************************************************
*Function: monte_carlo_pi_serial
*Purpose:  generate random numbers on a cartesian plane and calculate how many
*		   are contained in the unit circle
*in-args:  number of darts to throw, 
*out-args: estimated value of pi
*/
float monte_carlo_pi_serial (long long int n){
	float x, y;
	float pi_estimate=0;
	int in_circle_count = 0;
	srandom(n);
	int i;
	for (i = 0; i < n; i++) {
		x = ((float)rand()/(float)RAND_MAX -1);
		y = ((float)rand()/(float)RAND_MAX - 1);
		if ((x*x) + (y*y)<= 1 ){
			in_circle_count ++;
		}
	}
	printf ("In Circle: %d\n",in_circle_count);
	pi_estimate = 4.0*in_circle_count/n;	return pi_estimate;
} /* monte_carlo_pi_serial */

/**************************************************************************** Function: pi_val* Purpose:  Calculate actual Value for pi* Output:   Actual value for pi*/double pi_val(void){	double temp;	temp = 4.0*atan(1.0);	return temp;}/* pi_val */