/*
 * Componentes.c
 *
 *  Created on: 21/4/2020
 *      Author: Álvaro
 */

#include "componentes.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>


/*
La funcion Lista_Crear crea una lista enlazada vacia
de nodos de tipo Componente.
*/
Lista Lista_Crear(){
	Lista lista = (Lista) malloc (sizeof(struct elemLista));
	lista = NULL;
	return lista;
}


/*
La rutina Adquirir_Componente se encarga de recibir los datos de un nuevo
componente (codigo y texto) que se le introducen por teclado y devolverlos
por los parametros pasados por referencia "codigo" y "texto".
*/
 void Adquirir_Componente(long *codigo,char *texto){
	 long cod;
	 printf("Introduzca codigo: \n");
	 scanf("%ld", &cod);

	 char tex[33];
	 printf("Introduce texto: \n");
	 scanf("%s",&tex);//POSIBLE ERROR

	 *codigo = cod;
	 strcpy (&(*texto), &(*tex));
 }


 /*
 La funcion Lista_Agregar toma como parametro un puntero a una lista,
 el cÃ³digo y el texto de un componente y  anyade un nodo al final de
 la lista con estos datos.
 */
 void Lista_Agregar(Lista *lista, long codigo, char* textoFabricante){
	 if (*lista == NULL){
		 Lista nuevoNodo = (Lista)malloc(sizeof(struct elemLista));
		 nuevoNodo->codigoComponente = codigo;
		 strcpy(nuevoNodo->textoFabricante, &(*textoFabricante));
		 nuevoNodo->sig = NULL;

		 *lista = nuevoNodo;
	 }else{
		 Lista ant = NULL;
		 Lista aux = *lista;
		 Lista nuevoNodo = (Lista)malloc(sizeof(struct elemLista));
		 nuevoNodo->codigoComponente = codigo;
		 strcpy(nuevoNodo->textoFabricante, &(*textoFabricante));
		 nuevoNodo->sig = NULL;
		 while(aux->sig!= NULL){//POSIBLE FALLO
			 ant = aux;
			 aux = aux->sig;
		 }
		 if(ant == NULL){
			 nuevoNodo->sig = aux;
			 *lista = nuevoNodo;
		 }else if(aux != NULL){
			 ant->sig = nuevoNodo;
			 nuevoNodo->sig = aux;
		 }else if(ant->sig == NULL){
			 ant->sig = nuevoNodo;
			 nuevoNodo->sig = NULL;
		 }
	 }
 }


 /*
 La funcion Lista_Imprimir se encarga de imprimir por pantalla la lista
 enlazada completa que se le pasa como parametro.
 */
 void Lista_Imprimir( Lista lista){
	 while (lista != NULL){
		 printf("[%ld,%s] -> ", lista->codigoComponente, lista->textoFabricante);
		 lista = lista->sig;
	 }
	 if(lista == NULL){
		 printf("NULL\n");
	 }
 }



 /*
 La rutina Lista_Vacia devuelve 1 si la lista que se le pasa
 como parametro esta vacia y 0 si no lo esta.
 */
 int Lista_Vacia(Lista lista){
	 int dev = 1;
	 if(lista != NULL){
		 dev = 0;
	 }
	 return dev;
 }

 /*Num_Elementos es una funcion a la que se le pasa un puntero a una lista
 y devuelve el numero de elementos de dicha lista.
 */
 int Num_Elementos(Lista  lista){
	 int cont = 0;
	 if(lista!=NULL){
		 while(lista != NULL){
			 cont++;
			 lista = lista->sig;
		 }
	 }
	 return cont;
 }

 /*
 Lista_Extraer toma como parametro un puntero a una Lista y elimina el
 Componente que se encuentra en su ultima posicion.
 */
 void Lista_Extraer(Lista *lista){
	 Lista aux;

	 if(*lista == NULL) {
		 printf("La lista esta vacia\n");
	 }else if((*lista)->sig == NULL){
		 aux = *lista;
		 free(aux);
		 *lista = NULL;
	 }else{
		 Lista ant = NULL;
		 Lista current=*lista;
		 while(current->sig != NULL){
			 ant = current;
			 current = current->sig;
		 }
		 if(current->sig ==NULL){
			 aux = current;
			 ant->sig = NULL;
			 free(aux);
		 }
	 }
 }

 void Lista_Vaciar(Lista *lista){
	 Lista aux;
	 Lista current = *lista;

	 while (current != NULL){
		 aux = current;
		 current = current->sig;
		 free(aux);
	 }
	 *lista = NULL;
 }




















