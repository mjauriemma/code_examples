/* File:     min_bst.c
 * Purpose:  Create a binary search tree of ints and print it out
 *
 * Compile:  gcc -g -Wall -o min_bst min_bst.c
 * Run:      ./min_bst
 *
 * Input:    A sequence of ints followed by EOF (Ctrl-D under Linux).
 * Output:   The tree of ints
 *
 * Note:     The insert function has been fixed.
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

const int MAX_INDENT = 80;

struct tree_node_s {
   int key;
   struct tree_node_s* lc;
   struct tree_node_s* rc;
}; 

typedef struct tree_node_s tree_node_t;

tree_node_t* Insert(tree_node_t* curr_p, int val);
void Print_tree_wrapper(tree_node_t* root_p);
void Print_tree(tree_node_t* curr_p, char indent[]);

int main(void) {
   /* Empty tree has NULL root */
   tree_node_t* root_p = NULL;
   int val, rv;

   printf("Enter a sequence of ints, ctrl-D to quit\n");
   rv = scanf("%d", &val);

   while (rv != EOF) {
      root_p = Insert(root_p, val);
      rv = scanf("%d", &val);
   } /* while */

   printf("The tree is:\n");
   Print_tree_wrapper(root_p);
   return 0;
}  /* main */


/*---------------------------------------------------------------------
 * Function:  Insert
 * Purpose:   Insert a new node in the tree
 * In args:   curr_p:  pointer to the current node
 *            val:  the key to be inserted
 * Ret val:   The possibly updated current node
 */
tree_node_t* Insert(tree_node_t* curr_p, int val) {
   struct tree_node_s* temp_p = malloc(sizeof(struct tree_node_s));
   if (curr_p == NULL) {
      /* Insert new node */
	   temp_p->key = val;
	   return temp_p;
   } else if (val < curr_p->key) {
      /* Insert in left subtree */
      temp_p = Insert(curr_p->lc, val);
	  curr_p->lc = temp_p;
   } else if (val > curr_p->key) {
      /* Insert in right subtree */
      temp_p = Insert(curr_p->rc, val);
	  curr_p->rc = temp_p;
   } else {
      /* val == curr_p->key */
      printf("%d is already in the tree\n", val);
	  
   }
   return curr_p;

}  /* Insert */


/*---------------------------------------------------------------------
 * Function:  Print_tree_wrapper
 * Purpose:   Set up for call to Print_tree function by creating
 *              storage for a string that will be used to control
 *              indentation.
 * In arg:    root_p:  pointer to the root of the tree.
 */
void Print_tree_wrapper(tree_node_t* root_p) {
   char indent[MAX_INDENT];

   indent[0] = '\0';
   Print_tree(root_p, indent);
}  /* Print_tree_wrapper */


/*---------------------------------------------------------------------
 * Function:    Print_tree
 * Purpose:     Print the keys in the tree showing the structure of
 *                 the tree.  (Preorder traversal)
 * In args:     curr_p:  pointer to the current node
 * In/out arg:  indent:  array of char specifying the indentation
 *              for the node.
 *
 * Note:
 * Each new level of the tree is indented 3 spaces to the right
 *    on the screen
 */
void Print_tree(tree_node_t* curr_p, char indent[]) {

   if (curr_p != NULL) {
      printf("%s %d\n", indent, curr_p->key);
      strcat(indent, "   ");
      Print_tree(curr_p->lc, indent);
      Print_tree(curr_p->rc, indent); 
      indent[strlen(indent) - 3] = '\0';
   }
}  /* Print_tree */