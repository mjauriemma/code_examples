
/* File:     bst.c
 * Purpose:  Create a binary search tree of ints and print it out
 *
 * Compile:  gcc -g -Wall -o bst bst.c
 * Run:      ./bst
 *
 * Input:    A sequence of ints followed by EOF (Ctrl-D under Linux).
 * Output:   The tree of ints
 *
 * Note:     The insert function has been fixed.
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

const int MAX_INDENT = 80;

struct tree_node_s {
   int key;
   struct tree_node_s* lc;
   struct tree_node_s* rc;
}; 

typedef struct tree_node_s tree_node_t;

struct tree_node_s* Insert(struct tree_node_s* curr_p, int val);
void Print_tree_wrapper(struct tree_node_s* root_p);
void Print_tree(struct tree_node_s* curr_p, char indent[]);
void Print_sort(struct tree_node_s* curr_p);
struct tree_node_s* Delete_wrapper(struct tree_node_s* root_p, int val);
struct tree_node_s* Delete(struct tree_node_s* root_p, struct tree_node_s* curr_p, int val);
bool Member(struct tree_node_s* curr_p, int val);
struct tree_node_s* Free_tree_wrapper(struct tree_node_s* root_p);
struct tree_node_s* Free_tree(struct tree_node_s* root_p, struct tree_node_s* curr_p);
char Get_command(void);
int  Get_value(void);

int main(void) {
  char command;
   int  value;
   struct tree_node_s* root_p = NULL;  
      /* start with empty tree */

   command = Get_command();
   while (command != 'q' && command != 'Q') {
      switch (command) {
         case 'i': 
         case 'I': 
            value = Get_value();
            root_p = Insert(root_p, value);
            break;
         case 's':
         case 'S':
            Print_sort(root_p);
            break;
	 	case 't':
	 	case 'T':
	   		Print_tree_wrapper(root_p);
	    	break;
         case 'm': 
         case 'M':
            value = Get_value();
            if (Member(root_p, value))
               printf("%d is in the tree\n", value);
            else
               printf("%d is not in the tree\n", value);
            break;
         case 'd':
         case 'D':
            value = Get_value();
            root_p = Delete_wrapper(root_p, value);
            break;
         case 'f':
         case 'F':
            root_p = Free_tree_wrapper(root_p);
            break;
         default:
            printf("There is no %c command\n", command);
            printf("Please try again\n");
      }
      command = Get_command();
   }

   root_p = Free_tree_wrapper(root_p);

   return 0;
}  /* main */



/*---------------------------------------------------------------------
 * Function:  Insert
 * Purpose:   Insert a new curr_p in the tree
 * In args:   curr_p:  pointer to the current curr_p
 *            val:  the key to be inserted
 * Ret val:   The possibly updated current curr_p
 */
struct tree_node_s* Insert(struct tree_node_s* curr_p, int val) {
   struct tree_node_s* temp_p = malloc(sizeof(struct tree_node_s));
   if (curr_p == NULL) {
      /* Insert new curr_p */
	   temp_p->key = val;
	   return temp_p;
   } else if (val < curr_p->key) {
      /* Insert in lc subtree */
      temp_p = Insert(curr_p->lc, val);
	  curr_p->lc = temp_p;
   } else if (val > curr_p->key) {
      /* Insert in rc subtree */
      temp_p = Insert(curr_p->rc, val);
	  curr_p->rc = temp_p;
   } else {
      /* val == curr_p->key */
      printf("%d is already in the tree\n", val);
	  
   }
   return curr_p;

}  /* Insert */


/*--------------------------------------------------------------------
 * Function: Delete_wrapper
 * Purpose:  Helper function for Delete
 * Input:    Pointer to root of tree, and value to be deleted
 * Output:   New root of the list with the value deleted
 */
struct tree_node_s* Delete_wrapper(struct tree_node_s* root_p, int val){
	struct tree_node_s* curr_p;
	curr_p = root_p;
	root_p = Delete(root_p, curr_p, val);
	return root_p;
}


/*--------------------------------------------------------------------
 * Function: Delete
 * Purpose:  Remove a node with the specified value from the tree
 * Input:    Root of list, pointer for a current node, value to be deleted
 * Output:   Updated root with value removed from tree
 */
struct tree_node_s* Delete(struct tree_node_s* root_p, struct tree_node_s* curr_p, int val){
	struct tree_node_s* Rc;
    struct tree_node_s* Rcp;
 
    if(curr_p==NULL){    
		printf("%d is not in list.\n",val);    
		return(root_p);

	}else{
        if(val == curr_p->key){
            if(curr_p->lc == NULL && curr_p->rc == NULL){ //leaf curr_p
                if(root_p == NULL){ //special case deleting the root curr_p
                    free(curr_p);
                    return(NULL);
                }
                if(root_p->lc == curr_p){
                    root_p->lc = NULL;
                }else{
                    root_p->rc = NULL;
                }
                free(curr_p);
                return(root_p);
            }
            if(curr_p->lc ==NULL ){ //one child
                if(root_p == NULL){ //deleting root having no lc child
                    struct tree_node_s* temp = curr_p;
                    curr_p = curr_p->rc;
                    free(temp);
                    return(curr_p);
                }
                if(root_p->lc == curr_p){
                    root_p->lc = curr_p->rc;
                }else{
                    root_p->rc = curr_p->rc;
                }   
                free(curr_p);
                return(root_p);
            }
            if(curr_p->rc ==NULL ){ //one child
                if(root_p == NULL){ //deleting root having no rc child
                    struct tree_node_s* temp = curr_p;
                    curr_p = curr_p->lc;
                    free(temp);
                    return(curr_p);
                }
                if(root_p->lc == curr_p){
                    root_p->lc = curr_p->lc;
                }else{
                    root_p->rc = curr_p->lc;
                }   
                free(curr_p);
                return(root_p);
            }

            //two children case
            Rc = curr_p->rc;
            Rcp=curr_p;
            while(Rc->lc != NULL)
            {
                Rcp=Rc;
                Rc = Rc->lc;
            }
            curr_p->key=Rc->key;
            if(Rcp == curr_p)
            {
                //Rcp->rc=Rc->rc;
                curr_p->rc=Rc->rc;
            }else{
                //Rcp->lc=NULL;
                Rcp->lc=Rc->rc;
            }
            free(Rc);
            if(root_p ==NULL) //root curr_p
            {
                return(curr_p);
            }           
            return(root_p);
        }else{
            if(val < curr_p->key){
                Delete(root_p,curr_p->lc,val);
                return(curr_p);
            }else{
                Delete(root_p,curr_p->rc,val);
                return(curr_p);
            }
		
        }

    }
	
	
}/*Delete*/


/*--------------------------------------------------------------------
 * Function: Free_tree_wrapper
 * Purpose:  Helper function for Free_tree
 * Input:    Root of tree to be deleted
 * Output:   Null root of an empty tree
 */
struct tree_node_s* Free_tree_wrapper(struct tree_node_s* root_p){
	struct tree_node_s* curr_p;
	curr_p = root_p;
	Free_tree(curr_p);
	root_p = NULL;
	return root_p;
}

/*--------------------------------------------------------------------
 * Function: Free_tree
 * Purpose:  Delete and free all nodes in a tree
 * Input:    Root of tree to be deleted, current node
 * Output:   Root of empty tree
 */
struct tree_node_s* Free_tree(struct tree_node_s* root_p, struct tree_node_s* curr_p){

	if (curr_p !=NULL){
		if (curr_p->lc == NULL){
			if (curr_p->rc == NULL){
				free(curr_p);
			}else{
				Free_tree(root_p, curr_p->rc);
			}
		}else{
			Free_tree(root_p, curr_p->lc);
		}		
	}
	return root_p;
} /*Free_tree */


/*--------------------------------------------------------------------
 * Function: Member
 * Purpose:  Test to see if value is in the tree
 * Input:    Root of tree
 * Output:   0 for false, 1 for true
 */
bool Member(struct tree_node_s* curr_p, int val){
	if (curr_p != NULL){
		if (curr_p->key<val){
			return Member(curr_p->rc, val);

		}
		if (curr_p->key>val){
			return Member(curr_p->lc, val);
			
		}
		if (curr_p->key == val){
			return 1;
		}
	}
	return 0;

}/*Member*/



/*---------------------------------------------------------------------
 * Function:  Print_tree_wrapper
 * Purpose:   Set up for call to Print_tree function by creating
 *              storage for a string that will be used to control
 *              indentation.
 * In arg:    root_p:  pointer to the root of the tree.
 */
void Print_tree_wrapper(struct tree_node_s* root_p) {
   char indent[MAX_INDENT];

   indent[0] = '\0';
   Print_tree(root_p, indent);
}  /* Print_tree_wrapper */



/*---------------------------------------------------------------------
 * Function: Print_sort
 * Purpose:  Recursively print the tree sorted in order

 * Input:    curr_p which initially holds the value of root_p
 */

void Print_sort(struct tree_node_s* curr_p) {
	if (curr_p != NULL) {
		Print_sort(curr_p->lc);
		printf("%d ", curr_p->key);
		Print_sort(curr_p->rc);
	}
} /* Print_sort */


/*---------------------------------------------------------------------
 * Function:    Print_tree
 * Purpose:     Print the keys in the tree showing the structure of
 *                 the tree.  (Preorder traversal)
 * In args:     curr_p:  pointer to the current curr_p
 * In/out arg:  indent:  array of char specifying the indentation
 *              for the curr_p.
 *
 * Note:
 * Each new level of the tree is indented 3 spaces to the rc
 *    on the screen
 */
void Print_tree(struct tree_node_s* curr_p, char indent[]) {

   if (curr_p != NULL) {
      printf("%s %d\n", indent, curr_p->key);
      strcat(indent, "   ");
      Print_tree(curr_p->lc, indent);
      Print_tree(curr_p->rc, indent); 
      indent[strlen(indent) - 3] = '\0';
   }
}  /* Print_tree */

/*-----------------------------------------------------------------
 * Function:      Get_command
 * Purpose:       Get a single character command from stdin
 * Return value:  the first non-whitespace character from stdin
 */
char Get_command(void) {
   char c;

   printf("Please enter a command (i, s, t, m, d, f, q):  ");
   /* Put the space before the %c so scanf will skip white space */
   scanf(" %c", &c);
   return c;
}  /* Get_command */

/*-----------------------------------------------------------------
 * Function:   Get_value
 * Purpose:    Get an int from stdin
 * Return value:  the next int in stdin
 * Note:       Behavior unpredictable if an int isn't entered
 */
int  Get_value(void) {
   int val;

   printf("Please enter a value:  ");
   scanf("%d", &val);
   return val;
}  /* Get_value */
