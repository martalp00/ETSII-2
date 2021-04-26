/*
 * Lista.h
 *
 *  Created on: 28/1/2020
 *      Author: usuario
 */

#ifndef LISTA_H_
#define LISTA_H_

typedef struct T_Nodo* T_Lista;
struct T_Nodo {
	unsigned num;
	T_Lista sig;
};

void Crear(T_Lista* lista); //struct T_Nodo**
void Destruir(T_Lista* lista);
void Rellenar (T_Lista *lista);
void Mostrar (T_Lista lista);
void EscribirF(char *nombre,T_Lista lista);
void LeerDeFichero(char*nombre,T_Lista *l);

#endif /* LISTA_H_ */
