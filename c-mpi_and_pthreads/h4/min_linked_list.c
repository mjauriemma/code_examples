 
/* File:     min_linked_list.c
 * Purpose:  Try to create and print a linked list of ints.
 *
 * Compile:  gcc -g -Wall -o min_linked_list min_linked_list.c
 * Run:      ./min_linked_list
 *
 * Input:    List of positive ints terminated with an int <= 0
 * Output:   The positive ints in the list
 *
 * Notes:     
 * 1. The Create_node function has a serious bug.  The rest of
 *    the program is OK.
 *    *The bug has been fixed.
 * 2. The program is supposed to add new nodes to the head of
 *    the list.  For example, if the user enters 1 2 3 0,
 *    the list *should* be
 *
 *       head_p -> 3 -> 2 -> 1  
 */
#include <stdio.h>
#include <stdlib.h>

struct list_node_s {
   int data;
   struct list_node_s* next_p;
};

struct list_node_s* Create_node(int val, struct list_node_s* node_p);
void Print(struct list_node_s* head_p);

int main(void) {
   struct list_node_s* head_p = NULL;
   int val;

   /* Add new nodes to the head of the list */
   printf("Enter an int, <= 0 to stop\n");
   scanf("%d", &val);
   while (val > 0) {
      head_p = Create_node(val, head_p);
      scanf("%d", &val);
   }    

   Print(head_p);

   return 0;
}  /* main */


/*-----------------------------------------------------------------
 * Function:    Create_node
 * Purpose:     Return a pointer to a new struct list_node_s
 * Input args:  val:  value to put in data field
 *              node_p:  address to put in next_p field
 * Note:        This function has a serious bug
 * Note:        The bug has been fixed. temp_p was a local variable that was   	*		getting tossed to the heap at the return, but was fixed by 
 *		allocating memory for the member temp_p.
 */
struct list_node_s* Create_node(int val, struct list_node_s* node_p) {
   struct list_node_s* temp_p;

   temp_p = malloc(sizeof(struct list_node_s));
   temp_p->data = val;
   temp_p->next_p = node_p;
   node_p = temp_p;

   return node_p;
}  /* Create_node */

/*-----------------------------------------------------------------
 * Function:   Print
 * Purpose:    Print list on a single line of stdout
 * Input arg:  head_p
 */
void Print(struct list_node_s* head_p) {
   struct list_node_s* curr_p = head_p;

   printf("list = ");
   while (curr_p != NULL) {
      printf("%d ", curr_p->data);
      curr_p = curr_p->next_p;
   }  
   printf("\n");
}  /* Print */

