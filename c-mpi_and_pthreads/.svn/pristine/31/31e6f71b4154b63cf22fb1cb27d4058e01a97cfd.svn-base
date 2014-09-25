/* 
*  Author:  Matthew Auriemma
*  File:    row_numbering.c
*  Purpose: Takes global or local row numbers of a matrix and converts them
*           to the local row number if they were global or vice versa.
*  Compile: gcc -g -Wall -o row_numbering row_numbering.c
*  Run:     ./row_numbering.c
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main (void) {
	
	int p, m, num, local_m, local_p;
	char s[1]; 

	printf ("Enter the number of processes: ");
	scanf("%d", &p);
	printf ("Enter the total number of rows: ");
	scanf("%d", &m);
	printf ("Enter the global or local row: ");
	scanf("%d", &num);
	printf ("Enter 'g' if row was global or 'l' if row was local: ");
	scanf("%s", &s[0]);
	local_m = m/p;
	if ( strcmp(s, "l") == 0){
		printf("Enter the process on where local row number was found: ");
		scanf("%d", &local_p);
		num = (local_p*local_m) + num;
		printf("The global row number is: %d\n", num);
	}
	else{
		int counter=local_m;
		local_p=0;
		while(num>=counter){
			counter+=local_m;
			local_p++;
		}
		local_m = local_m - (counter - num);
		printf("Row %d of the matrix is row %d on process %d.\n", 
			num, local_m, local_p);
	}

	return 0;

} /* main */