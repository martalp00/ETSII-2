/*
 * EstructurasControl.c
 *
 *  Created on: 19 feb. 2019
 *      Author: mmar
 */

#include <stdio.h>
int esPrimo (int n) {
	if (n<=0) return -1;//no hay excepciones
	if (n==1 || n==2) return 1;
	else {
		int primo = 1;
		int i = 2;
		while (primo && i*i<=n){
			primo = n % i != 0;
			i++;
		}
		return primo;
	}
}


int main3(int argc,char ** args){
	int nPrimos;
	printf("Introduce el número de primos que quieres: ");
	scanf("%d",&nPrimos);
	printf("\n");
	int pPrimo = 1;
	while (nPrimos>0){
		if (esPrimo(pPrimo)) {
			printf("%d ",pPrimo);
			nPrimos--;
		}
		pPrimo++;
	}

	return 0;
}
