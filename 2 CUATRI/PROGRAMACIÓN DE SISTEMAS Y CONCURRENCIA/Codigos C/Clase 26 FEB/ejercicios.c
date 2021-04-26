/*
 * ejercicios.c
 *
 *  Created on: 26 feb. 2019
 *      Author: mmar
 */
#include <stdlib.h>
#include <stdio.h>

void func(int *p2)
{
	p2 = (int *)malloc(sizeof(int));
	*p2 = 14;
	printf("%u\n",*p2);
}

void ejercicio2()
{
	int *p=NULL;
	func(p);
	printf("%u\n",*p); //Por qué es incorrecta esta llamada?

}

int * creaArray(int tam)
{
   int a[tam];
   //int *a = (int *)malloc(tam*sizeof(int));
   for (int i = 0; i<3;i++)
   {
	   a[i] = 0;
   }
   return a;
}

void ejercicio1()
{
	int *array;
	array = creaArray(3); //por qué no funciona bien la inicialización?
	for (int i=0; i<3; i++)
	{
		printf("%u ",array[i]);
	}
}

void intercambiar(char * a, char *b)
{
	char aux;
	aux = *a;
	*a = *b;
	*b = aux;
}

void permutar(char *str, int inic, int fin)
{
	if (inic == fin) printf("%s\n",str);
	else
	{
		for (int i = inic; i<=fin; i++)
		{
			intercambiar(str+inic,str+i);
			permutar(str,inic+1,fin);
			intercambiar(str+inic,str+i);
		}
	}
}

int main2()
{
	char str[10]="abc";
	permutar(str,0,2);
	return 0;
}
