/* File:    mpi_simpson.c
 *
 * Purpose: Implement parallel Simposon's rule allowing user input
 *          of data
 *
 * Input:   a, b, n
 * Output:  Estimate of the area between x = a, x = b, x-axis,
 *          and graph of f(x) using Simpson's rule and n 
 *          trapezoids.
 *
 * Compile: mpicc -g -Wall -o mpi_simpson mpi_simpson.c -lm
 * Run:     mpiexec -n <number of processes> ./mpi_simpson
 *
 * Algorithm:
 *    0.  Process 0 reads in a, b, and n, and distributes them
 *        among the processes.
 *    1.  Each process calculates "its" subinterval, i.e., its 
 *        local a and b.
 *    2.  Each process estimates the area of f(x)
 *        over its subinterval using Simpson's rule.
 *    3a. Each process != 0 sends its area to 0.
 *    3b. Process 0 sums the calculations received from
 *        the individual processes and prints the result.
 *
 * Note:  f(x) is hardwired.
 *
 * Runtimes: 1 Node:  3.86958122253418e-02 seconds
 *			 2 Nodes: 1.95682048797607e-02 seconds
 *			 4 Nodes: 9.52291488647461e-03 seconds
 */
#include <stdio.h>
#include <math.h>

/* We'll be using MPI routines, definitions, etc. */
#include <mpi.h>

void Get_data(int p, int my_rank, double* a_p, double* b_p, int* n_p);

double Simpson(double local_a, double local_b, int local_n,
           double h);    /* Calculate local area  */

double f(double x); /* function we're integrating */


int main(int argc, char** argv) {
    int         my_rank;   /* My process rank           */
    int         p;         /* The number of processes   */
    double      a;         /* Left endpoint             */
    double      b;         /* Right endpoint            */
    int         n;         /* Number of trapezoids      */
    double      h;         /* Trapezoid base length     */
    double      local_a;   /* Left endpoint my process  */
    double      local_b;   /* Right endpoint my process */
    int         local_n;   /* Number of trapezoids for  */
                           /* my calculation            */
    double      my_area;   /* Integral over my interval */
    double      total = 0;     /* Total area                */
    int         source;    /* Process sending area      */
    int         dest = 0;  /* All messages go to 0      */
    int         tag = 0;
	double		start, finish, elapsed;
    MPI_Status  status;

    /* Let the system do what it needs to start up MPI */
    MPI_Init(&argc, &argv);

    /* Get my process rank */
    MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);

    /* Find out how many processes are being used */
    MPI_Comm_size(MPI_COMM_WORLD, &p);

    Get_data(p, my_rank, &a, &b, &n);
	MPI_Barrier(MPI_COMM_WORLD);
	start = MPI_Wtime();

    h = (b-a)/n;    /* h is the same for all processes */
    local_n = n/p;  /* So is the number of trapezoids */

    /* Length of each process' interval of
     * integration = local_n*h.  So my interval
     * starts at: */
    local_a = a + my_rank*local_n*h;
    local_b = local_a + local_n*h;
    my_area = Simpson(local_a, local_b, local_n, h);

    /* Add up the areas calculated by each process */
    if (my_rank == 0) {
        total = my_area;
        for (source = 1; source < p; source++) {
            MPI_Recv(&my_area, 1, MPI_DOUBLE, source, tag,
                MPI_COMM_WORLD, &status);
            total = total + my_area;
        }
    } else {  
        MPI_Send(&my_area, 1, MPI_DOUBLE, dest,
            tag, MPI_COMM_WORLD);
    }
	
  
   finish = MPI_Wtime();

   elapsed = finish - start;
   printf("Elapsed time = %.14e seconds\n", elapsed);
   printf("Clock resolution = %.14e seconds\n", MPI_Wtick());


    /* Print the result */
    if (my_rank == 0) {
        printf("With n = %d trapezoids, our estimate\n",
            n);
        printf("of the area from %f to %f = %.15f\n",
            a, b, total);
    }

    /* Shut down MPI */
    MPI_Finalize();

    return 0;
} /*  main  */

/*------------------------------------------------------------------
 * Function:     Get_data
 * Purpose:      Read in the data on process 0 and send to other
 *               processes
 * Input args:   p, my_rank
 * Output args:  a_p, b_p, n_p
 */
void Get_data(int p, int my_rank, double* a_p, double* b_p, int* n_p) {
   int        q;
   MPI_Status status;

   if (my_rank == 0) {
      printf("Enter a, b, and n\n");
      scanf("%lf %lf %d", a_p, b_p, n_p);

      for (q = 1; q < p; q++) {
         MPI_Send(a_p, 1, MPI_DOUBLE, q, 0, MPI_COMM_WORLD);
         MPI_Send(b_p, 1, MPI_DOUBLE, q, 0, MPI_COMM_WORLD);
         MPI_Send(n_p, 1, MPI_INT, q, 0, MPI_COMM_WORLD);
      }
   } else {
      MPI_Recv(a_p, 1, MPI_DOUBLE, 0, 0, MPI_COMM_WORLD, &status);
      MPI_Recv(b_p, 1, MPI_DOUBLE, 0, 0, MPI_COMM_WORLD, &status);
      MPI_Recv(n_p, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
   }
}  /* Get_data */

/*------------------------------------------------------------------
 * Function:     Simpson
 * Purpose:      Estimate area using Simpson's rule
 * Input args:   a: left endpoint
 *               b: right endpoint
 *               n: number of subintervals
 *               h: stepsize = length of subintervals
 * Return val:   Simpson's rule estimate of area between x-axis,
 *               x = a, x = b, and graph of f(x)
 * Note:         Function assumes n is even.
 */
double Simpson(double  a, double  b, int n, double h) {
   double area, x, temp;
   int i;

   area = f(a) + f(b);

   temp = 0.0;
   for (i = 1; i <= n-1; i += 2) {
      x = a + i*h;
      temp += f(x);
   }
   area += 4*temp;
   
   temp = 0;
   for (i = 2; i <= n-2; i += 2) {
      x = a + i*h;
      temp += f(x);
   }
   area += 2*temp;

   area *= h;
   area /= 3;

   return area;
}  /* Simpson */



/*------------------------------------------------------------------
 * Function:    f
 * Purpose:     Compute value of function to be integrated
 * Input args:  x
 */
double f(double x) {
    double return_val;

    return_val = exp(sin(x));

    return return_val;
} /* f */
