/*
 * Stack.c
 *
 *  Created on: 11 jun. 2019
 *      Author: galvez
 */
#include <stdio.h>
#include <stdlib.h>
#include "Stack.h"

// Creates an empty stack.
T_Stack create() {
	return NULL;
}

// Returns true if the stack is empty and false in other case.
int isEmpty(T_Stack q) {
	return q == NULL;
}

// Inserts a number into the stack.
void push(T_Stack * pq, int operand) {
	T_Stack ptr = *pq;
	T_Stack nuevo = (T_Stack)malloc(sizeof(T_Node));
	nuevo->number = operand;
	nuevo->next = NULL;
	if(isEmpty(*pq)){
		*pq = nuevo;
	}else{
		while(ptr->next != NULL){
			ptr = ptr->next;
		}
		ptr -> next = nuevo;
	}
}

// "Inserts" an operator into the stack and operates.
// Returns true if everything OK or false in other case.
int pushOperator(T_Stack * pq, char operator) {
	int i = 1;
	T_Stack ptr = *pq, ant = NULL;
	int x, y, res = -1;
	while(ptr->next->next != NULL){
		ant = ptr;
		ptr = ptr->next;
	}
	x = ptr->number;
	y = ptr->next->number;
	free(ptr->next);
	free(ptr);
	if(ant == NULL){
		*pq = NULL;
	}else{
		ant->next = NULL;
	}
	switch (operator){
		case '+':
			res = x + y;
			break;
		case '-':
			res = x - y;
			break;
		case '*':
			res = x * y;
			break;
		case '/':
			res = x / y;
			break;
		default:
			i = 0;
	}if(res == -1){
		i=0;
	}else{
		push(pq, res);
	}
	return i;
}

// Puts into data the number on top of the stack, and removes the top.
// Returns true if everything OK or false in other case.
int pop(T_Stack * pq, int * data) {
	int i = 1;
	T_Stack ptr = *pq, ant = NULL;
	while (ptr-> next != NULL){
		ant = ptr;
		ptr = ptr->next;
	}
	*data = ptr ->number;
	free(ptr);
	if(ant==NULL){
		*pq=NULL;
	}else{
		ant->next = NULL;
	}
	return i;
}

// Frees the memory of a stack and sets it to empty.
void destroy(T_Stack * pq) { // SE PODRIA HACER SIN EL PTR
	if(!isEmpty(*pq)){
			T_Stack ptr = *pq, aux = NULL;
				while(ptr->next != NULL){
					aux = ptr->next;
					free(ptr);
					ptr = aux;
				}
		}























}
