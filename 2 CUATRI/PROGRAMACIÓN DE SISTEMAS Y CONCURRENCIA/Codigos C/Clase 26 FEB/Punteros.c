/*
 * Punteros.c
 *
 *  Created on: 26 feb. 2019
 *      Author: mmar
 */

#include <stdio.h>
#include <string.h>
#include <stdlib.h> //para la creación y destrucción de variables dinámicas

int decPunteros()
{
	char c, *cp;// carácter (1 byte), puntero a carácter (4 bytes, en una máquina de 32 bits)
	int i, *ip;// entero (4 bytes), puntero a entero (4 bytes)
	float f, *fp;// float (4 bytes), puntero a float (4 bytes)
	double d, *dp;// double (8 bytes), puntero a double (4 bytes)

	printf("c:%d \t cp:%d\n",&c,&cp);
	printf("i:%d \t ip:%d\n",&i,&ip);
	printf("f:%d \t fp:%d\n",&f,&fp);
	printf("d:%d \t dp:%d\n",&d,&dp);

	cp = &c;
	ip = &i;
	*ip = 42;

	printf("Nombre:%s \t Dirección:%d \t Valor:%d\n","cp",&cp,cp);
	printf("Nombre:%s \t Dirección:%d \t Valor:%d\n","ip",&ip,ip);
	printf("Nombre:%c \t Dirección:%d \t Valor:%d\n",'i',&i,i);

	return 0;
}

int punteros()
{

	char ca[3],*cp;
	ca[1] = 3;
	cp = &(ca[1]);
	*cp = 7;
	for (int i=0;i<3;i++)
	{
		printf("Nombre:ca[%d] \t Dirección:%u \t Valor:%d\n",i,&ca[i],ca[i]);
	}
	return 0;
}

int aritmeticaPunteros()
{
	char ca[3], *cp;
	int ia[3], *ip;
	cp = ca;
	ip = ia;
	printf("Nombre:%s\t Dirección:%u \t Valor:%d\n","ca",ca,ca[0]);
	printf("Nombre:%s \t Dirección:%u \t Valor:%d\n","ca[1]",&ca[1],ca[1]);
	printf("Nombre:%s \t Dirección:%u \t Valor:%d\n","ca[2]",&ca[2],ca[2]);
	printf("Nombre:%s\t Dirección:%u \t Valor:%d\n","ia",ia,ia[0]);
	printf("Nombre:%s \t Dirección:%u \t Valor:%d\n","ia[1]",&ia[1],ia[1]);
	printf("Nombre:%s \t Dirección:%u \t Valor:%d\n","ia[2]",&ia[2],ia[2]);
	printf("Nombre:%s \t Dirección:%u \t Valor:%d\n","cp",&cp,cp);
	printf("Nombre:%s \t Dirección:%u \t Valor:%d\n","ip",&ip,ip);
	*(cp + 2) = 8; //ca[2] = 8
	*(ia + 2) = 33; //ia[2] = 33
	printf("*******************************\n");
	printf("Nombre:%s\t Dirección:%u \t Valor:%d\n","ca",ca,ca[0]);
	printf("Nombre:%s \t Dirección:%u \t Valor:%d\n","ca[1]",&ca[1],ca[1]);
	printf("Nombre:%s \t Dirección:%u \t Valor:%d\n","ca[2]",&ca[2],ca[2]);
	printf("Nombre:%s\t Dirección:%u \t Valor:%d\n","ia",ia,ia[0]);
	printf("Nombre:%s \t Dirección:%u \t Valor:%d\n","ia[1]",&ia[1],ia[1]);
	printf("Nombre:%s \t Dirección:%u \t Valor:%d\n","ia[2]",&ia[2],ia[2]);


	for (int i = 0; i<3; i++)
	{
		*(cp + i) = 2*i;
		*(ip + i) = 3*i;
	}
	printf("*******************************\n");
	printf("Nombre:%s\t Dirección:%u \t Valor:%d\n","ca",ca,ca[0]);
	printf("Nombre:%s \t Dirección:%u \t Valor:%d\n","ca[1]",&ca[1],ca[1]);
	printf("Nombre:%s \t Dirección:%u \t Valor:%d\n","ca[2]",&ca[2],ca[2]);
	printf("Nombre:%s\t Dirección:%u \t Valor:%d\n","ia",ia,ia[0]);
	printf("Nombre:%s \t Dirección:%u \t Valor:%d\n","ia[1]",&ia[1],ia[1]);
	printf("Nombre:%s \t Dirección:%u \t Valor:%d\n","ia[2]",&ia[2],ia[2]);

	return 0;
}


int division(int num,int den,int* coc, int* resto)
{
	if (den == 0) return -1;
	*coc = num/den;
	*resto = num%den;
	return 0;
}

int divideNumeros()
{
	int numerador = 88;
	int denominador = 25;
	int cociente;
	int resto;
	division(numerador,denominador,&cociente,&resto);
	printf("cociente: %d, resto: %d",cociente,resto);
	return 0;
}

int intercambia(int *x,int *y)
{
	int aux = *x;
	*x = *y;
	*y = aux;
	return 0;
}

int punterosArrays()
{
	double array[5];
	double *dptr;
	double valor;
	int i,pos;

	for (i = 0; i<5; i++)
	{
		array[i] = (double)i+10.0; //inicializamos el array
	}

	dptr = array; //dptr = &(array[0])

	while (1)
	{
		printf("Dirección(hex)\tDirección(base 10)\tValor\n");

		for (i = 0; i<5; i++)
		{
			printf("%p\t%u\t%lf\n",&(array[i]),&(array[i]),array[i]);
		}

		printf("Introduce posición valor (0 0 para terminar)");
		scanf("%d %lf",&pos,&valor);

		if (pos == 0 && valor == 0) break;

		if (pos<0 || pos>4)
		{
			printf("posición fuera del rango\n");
		}
		else
		{
			//tres formas de hacer lo mismo
			array[pos] = valor;
			*(dptr + pos) = valor;
			*(array + pos) = valor;
		}
	}
	return 0;
}

int arrayParametros1(char *str)
{
	for (int i=0; i<strlen(str); i++)
	{
		if (str[i]=='a') str[i] = 'A';
	}
	return 0;
}

int arrayParametros2(int *array, int lon)
{
	for (int i=0; i<lon; i++)
	{
		array[i] = i;
	}
	return 0;
}

int memoriaDinamica()
{
	double *a;
	a = (double *)malloc(40); //pido 40 bytes al SO
							 //, y hago el casting al tipo de la variable a
	// equivalente a malloc(5*sizeof(double))
	// un array de 5 doubles
	printf("a: %u MD: %u\n",&a, a);

	for (int i = 0; i<5; i++)
	{
		*(a+i)=25.0 + (double)i;
		//a[i] = 25.0 + (double)i; equivalente a la anterior
	}

	for (int i = 0; i<5; i++)
	{
		printf("a[%u] = %lf\n",i,*(a+i));
	}
	return 0;
}

int devolverMemoria()
{
	int *a = (int *)malloc(sizeof(int)*5);//pedimos 5 enteros al SO
	printf("Introduce 5 enteros");
	fflush (stdout);

	for (int i=0; i<5; i++)
	{
		scanf("%d",a+i);
	}
	printf("Array introducido\n");

	for (int i=0; i<5; i++)
	{
		printf("%u ",a[i]);
		//printf("%u ",*(a+i));
		//printf("%u ",a+i);
	}
	//a = (int *)malloc(sizeof(int)*8)
	// memory leak -- fuga de memoria
	free(a);
}

int main1()
{
	//decPunteros();
	//punteros();
	//aritmeticaPunteros();
	//divideNumeros();
	//***************
	//int x = 8, y = 25;
	//printf("x: %d, y: %d\n",x,y);
	//intercambia(&x,&y);
	//printf("x: %d, y: %d\n",x,y);
	//*****************
	//punterosArrays();
	//*********************
	//char a[5] = "Hola";
	//char *a = "Hola"; //esta inicialización no vale
	//arrayParametros1(a);
	//printf("%s",a);
	//*********************
	//int a[5];
	//arrayParametros2(a,5);
	//for (int i = 0; i<5; i++)
	//	printf("%u \n",a[i]);
	//*********************
	//memoriaDinamica();
	devolverMemoria();
	return 0;
}
