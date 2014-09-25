/* Author: Matthew Auriemma
*  File: bisect.c
*  Purpose: Calculate the exact soln for f(0) for a particular func f
*           f(x) = (x^2) - 2
*
*  Compile: gcc -g -Wall -o bisect bisect.c -lm
*
*  Run: ./bisect
*  
*  Input: a, b, tolerance, max_iters
*  Ouptut: Aprox. soln, num of iters, whether the iters converged
*          function value
*/


#include <stdio.h>
#include <math.h>

double bisect(double a, double b, int max_iters, int tolerance, int iters);
double f(double x);

int main(void){
	double a;  /*right boundry */
	double b;  /*left boundry */
	int iters;  /* number of times the func iterates */
	int max_iters; /* Max number of times func can run */
	double tolerance; /* Diff from exact solution allowed */
	double aprox_soln; /* Approximated calculated solution */
	
	/* User Input */

	printf("Enter boundry 'a':\n");
	scanf("%lf", &a);
	printf("Enter boundry 'b':\n");
	scanf("%lf", &b);
	printf("Enter the tolerance:\n");
	scanf("%lf", &tolerance);
	printf("Enter the maximum number of iterations:\n");
	scanf("%d", &max_iters);

	/* Tests of user input boundries */

	if (f(a)*f(b) > 0) {
		printf("Solution not contained between a and b!");
		return 0;
	} 

	/* Test if  boundries are the exact solution */

	else if (f(a) == 0) {
		printf("Ran 0 times and midpoint = %f was equal to a = %f\n", a, a);
		printf("Solution converged and the abs difference between computed and \n");
		printf("actual solution was 0");
		return 0;

	} else if (f(b) == 0) {
		printf("Ran 0 times and midpoint = %f was equal to b = %f", b, b);
		printf("Solution converged and the abs difference between computed and \n");
		printf("actual solution was 0");
		return 0;
	}

	/* Func Call of Bisect for calculation */

	iters = 0;
	aprox_soln = bisect(a, b, max_iters, tolerance, iters);

	/* Test how close to 0 the calc soln is */
	double func_val; 
	func_val = f(aprox_soln);
	
	/* Test if it converged */

	printf("Approx Solution is %lf, and f(m) = %f\n", aprox_soln, func_val);
	if(f(aprox_soln) == 0 || fabs(0-f(aprox_soln))<= tolerance){
		printf("Solution converged\n");
	}
	/* Calc abs dif */
	else{
		double abs_dif;
		abs_dif = fabs(0-f(aprox_soln));
		printf("The absolute difference of the computed solution from the exact\n");
		printf("solution was %f\n", abs_dif);
	}
	return 0;
		
}  /* main */


/* Func:  Bisect
* Purpose: Calculate the approx solution of x for f(x) = 0
* Input args: double a, double b, int max_iters, int tolerance, int iters
* Output args: m, the value of the approx soln
*/


double bisect(double a, double b, int max_iters, int tolerance, int iters){
	double m;

/* f(a)*f(b) < 0 */

	

/* fabs(b-a) computes the absolute value of b-a */

	while (iters < max_iters && fabs(b-a) > tolerance) {
		iters++;
		m = (a + b)/2;
/* Do we replace a or b? */
		if (f(m) == 0) {
/* This will exit the while loop */
			break; 
		} else if (f(m)*f(a) < 0) {
			b = m;
		} else /* f(m)*f(b) < 0 */ {
			a = m;
		}
	}

/* Print the iterations out of max iters */
	printf("Ran %d times out of maximum %d times\n", iters, max_iters);


	return m;
}

/* Function: f
* Purpose: Hardwired function f(x) = (x^2)-2
* Input args: double x
* Output args: solution of f(x) where x is the value of the input arg
*/

double f(double x){
	double soln;
	soln = (x*x) - 2;
	return soln;
}
