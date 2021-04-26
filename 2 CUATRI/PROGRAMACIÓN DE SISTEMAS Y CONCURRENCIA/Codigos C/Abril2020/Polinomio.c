/*
 * Polinomio.c
 *
 *  Created on: 29 abr. 2020
 *      Author: marta
 */

#include "Polinomio.h"
#include <stdlib.h>
#include <stdio.h>


//Parte 1. PARA APROBAR

/*Crea el polinomio 0 (es decir, un polinomio vacío).*/
void polinomioCero(TPolinomio *p)
{
	*p = NULL;
}


/*Devuelve el grado del polinomio, es decir, el mayor exponente de los
monomios que no son nulos. En el ejemplo, el grado es 7.
El grado del polinomio cero es 0.*/
unsigned int grado(TPolinomio p)
{
	int maximo_grado = 0;

	while (p!=NULL)
	{
		if (p->exp > maximo_grado)
		{
			maximo_grado = p->exp;
		}
		p = p->sig;
	}
	return maximo_grado;
}


/* Devuelve el coeficiente del exponente exp del polinomio p.*/
unsigned int coeficiente(TPolinomio p, unsigned int exp)
{
	int coef = 0;

	while (p!=NULL)
	{
		if (p->exp == exp)
		{
			coef = p->coef;
		}
		p = p->sig;
	}
	return coef;
}


/* Insertar el monomio con coeficiente coef, y exponente exp en el polinomio,
 * de manera que el polinomio quede ordenado. Asegurarse que no se insertan
 * monomios cuyo coeficiente sea 0 y tampoco dos monomios con el mismo exponente.
 * Si al insertar un monomio ya hay otro con el mismo exponente los coeficientes
 * se sumarán. Se puede asumir que el valor del coeficiente siempre será un numero
 * natural (entero no negativo).*/
TPolinomio crearNodoPolinomio(unsigned int coef, unsigned int exp)
{
	TPolinomio aux = malloc(sizeof(struct TMonomio));
	aux->coef = coef;
	aux->exp = exp;
	aux->sig = NULL;
	return aux;
}

void insertar(TPolinomio *p, unsigned int coef, unsigned int exp)
{
    TPolinomio ant = NULL;
    TPolinomio act = *p;
    TPolinomio aux = NULL;
    while (act!=NULL && act->exp > exp)
    {
        ant = act;
        act = act->sig;
    }

    if(coef != 0)
    {
        if(ant==NULL)
        {
            if(act==NULL)	//Polinomio vacio
            {
                *p = crearNodoPolinomio(coef, exp);
            }
            else
            {
                if(act->exp==exp)
                {
                	act->coef = act->coef + coef;
                }
                else	//lo inserto como primer nodo
                {
                    aux = crearNodoPolinomio(coef,exp);
                    aux->sig = *p;
                    *p = aux;
                }
            }
        }
        else if (act==NULL)		//el nuevo nodo va al final
        {
            ant->sig =crearNodoPolinomio(coef,exp);

        }
        else
        {
            if(act->exp == exp)
           	{
            	act->coef = act->coef + coef;
           	}
            else
            {
            	aux = crearNodoPolinomio(coef,exp);
            	aux->sig = act;
            	ant->sig = aux;
            }
        }
    }
}


/*Escribe por la pantalla el polinomio con un formato similar al siguiente:
 * [(3,7)(0,6)(2,5)(0,4)(3,3)(0,2)(5,1)(9,0)] para el polinomio ejemplo.
 * Ten en cuenta que los monomios de exponente menor al grado del polinomio
 * con coeficiente 0 también aparecen en la salida, aunque no estén almacenados
 * en el polinomio. */
void imprimir(TPolinomio p)
{
	printf("[");
	while(p!=NULL)
	{
		printf("(%d,%d)", p->coef,p->exp);
	    p=p->sig;

		/*
		 * Aquí habría que incluir un if que ponga como restriccion
		 * que se incluyan los monomios con coeficiente cero aunq no
		 * hayan sido insertados. Un if que reste los exponentes y en caso de que se salte alguno
		 * incluya por defecto el del coeficiente 0,
		 * acaba de resolverme la duda y hay que entregar, no me da tiempo a implementarlo
		 * pero se haría asi, un saludo.
		 */
	}

	printf("] \n");
}



/* Elimina todos los monomios del polinomio haciendo que el polinomio resultante
 * sea el polinomio 0.*/
void destruir(TPolinomio *p)
{
	TPolinomio aux ;

	while(*p!=NULL)
	{
		aux = *p;
	    *p = aux->sig;
	    free(aux);
	}

	*p=0;
}

//Parte 2. Notable
/* Lee los datos de un polinomio de un fichero de texto, y
 * crea la lista de monomios p. El formato del polinomio en el fichero contiene
 * una secuencia de pares de dígitos correspondientes al coeficiente y exponente
 * de cada monomio del polinomio, incluyendo los que tienen coeficiente nulo.
 * En ambos casos, suponemos que los coeficientes y exponentes son dígitos del 0 al 9
 * (no hay números superiores).
 * Por ejemplo, para el polinomio de ejemplo el fichero de texto estaría compuesto
 * por la secuencia de caracteres “0690332551370402”.
 * Observa que los monomios pueden venir desordenados en el fichero de entrada.
 *
 * La conversión de un valor de tipo ‘char’ que contenga
 * un valor númerico (ej. char c = ‘2’)
 * a su correspondiente valor entero (int valor),
 * se puede hacer de la siguiente forma: valor = c – ‘0’
*/
//void crearDeFichero(TPolinomio *p, char *nombre);

//Parte 3. Sobresaliente
/* Evalúa el polinomio para el valor x y devuelve el resultado.
 * Para la evaluación del polinomio debes utilizar el método de Horner,
 * de manera que ax^4 + bx^3+ cx^2+dx+e puede evaluarse
 * en un valor cualquiera x teniendo en cuenta que es equivalente
 * a: (((ax+b)x+c)x+d)x+e.
*/
//int evaluar(TPolinomio p,int x);

