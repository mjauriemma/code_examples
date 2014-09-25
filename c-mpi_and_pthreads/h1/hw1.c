/* File:    trap.c
 * Purpose: Calculate area using trapezoidal rule.
 *
 * Input:   a, b, n
 * Output:  estimate of area between x-axis, x = a, x = b, and graph of f(x)
 *          using n trapezoids.
 *
 * Compile: gcc -g -Wall -o trap trap.c
 * Run:     ./trap
 *
 * Note:    The function f(x) is hardwired.
 */

#include <stdio.h>
#include <math.h>

double Trap(double  a, double  b, int n, double h);
double f(double x);    /* Function we're integrating */
double Simpson(double  a, double  b, int n, double h);

int main(void) {
   double  area;       /* Store result in area       */
   double  sArea;
   double  a, b;       /* Left and right endpoints   */
   int     n;          /* Number of trapezoids       */
   double  h;          /* Trapezoid base width       */
   double  d;

   printf("Enter a, b, and n\n");
   scanf("%lf", &a);
   scanf("%lf", &b);
   scanf("%d", &n);
   
   h = (b-a)/n;
   area = Trap(a, b, n, h);
   sArea = Simpson(a, b, n, h);

   d = fabs(area-sArea);

   printf("With n = %d trapezoids, our estimate\n",n);
   printf("of the area using the Trapazoidal Rule from %f to %f = %.15f\n",
	a, b, area);
   printf("of the area using Simpson's Rule from %f to %f = %.15f\n",
	a, b, sArea);
   printf("\n The absoulte value of the difference of the areas is %.15f\n", 
	d);

   return 0;
}  /* main */

/*------------------------------------------------------------------
 * Function:     Trap
 * Purpose:      Estimate area using the trapezoidal rule
 * Input args:   a: left endpoint
 *               b: right endpoint
 *               n: number of trapezoids
 *               h: stepsize = length of base of trapezoids
 * Return val:   Trapezoidal rule estimate of area between x-axis,
 *               x = a, x = b, and graph of f(x)
 */
double Trap(double a, double b, int n, double h) {
    double area;   /* Store result in area  */
    double x;
    int i;

    area = (f(a) + f(b))/2.0;
    for (i = 1; i <= n-1; i++) {
        x = a + i*h;
        area = area + f(x);
    }
    area = area*h;

    return area;
} /*  Trap  */


/*------------------------------------------------------------------
 * Function:    f
 * Purpose:     Compute value of function to be integrated
 * Input args:  x
 */
double f(double x) {
   double return_val;

   return_val = x*x + 1;
   return return_val;
}  /* f */

/*-------------------------------------------------------------------
* Function: Simpson
* Purpose:  Estimate area using Simpson's rule
* Inputs:   a: Left Endpoint
*	    b: Right Endpoint
*	    n: Number of subintervals (Must be Even)
*	    h: Length of base of subintervals
* Returns:  Simpson's rule estimate of area
*/
double Simpson (double a, double b, int n, double h) {
    double sArea;    /*Store result in area  */
    double term;
    int i;
    sArea = (f(a) + f(b));
    for (i = 1; i <= n-1; i++) {
	term = a + i*h;
	if (i%2==0){
	   sArea = sArea + (4*f(term));
	}
	else {
	   sArea = sArea + (2*f(term));
	}
    }
    sArea = sArea*(h/3);
    return sArea;
}
