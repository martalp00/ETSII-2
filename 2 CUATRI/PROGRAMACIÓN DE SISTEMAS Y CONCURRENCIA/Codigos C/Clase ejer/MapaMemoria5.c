/*
 * MapaMemoria.c
 *
 *  Created on: 20 feb. 2019
 *      Author: mmar
 */


#include <stdio.h>

int main5(int argc,char ** args){
	char a,b,c;
	a = 7;
	b = -13;
	c = 0;
	printf("nombre: %c, \t direccion:%p, \tvalor:%d\n",'a',&a,a);
	printf("nombre: %c, \t direccion:%p, \tvalor:%d\n",'b',&b,b);
	printf("nombre: %c, \t direccion:%p, \tvalor:%d\n",'c',&c,c);
	return 0;
}
int main6(int argc,char ** args){
	char a;
	int b;
	float f;
	double d;
	a = 7;
	b = -13;
	f = 0.1;
	d = 42.5;
	printf("nombre: %c, \t direccion:%d, \tvalor:%d\n",'a',&a,a);
	printf("nombre: %c, \t direccion:%d, \tvalor:%d\n",'b',&b,b);
	printf("nombre: %c, \t direccion:%d, \tvalor:%f\n",'c',&f,f);
	printf("nombre: %c, \t direccion:%d, \tvalor:%lf\n",'d',&d,d);
	return 0;
}

int main7(int argc,char ** args){
	char a;
	short int b;
	char c;
	a = 6;
	b = 13;
	c = '6';
	printf("nombre: %c, \t direccion:%d, \tvalor:%d, octal:%x\n",'a',&a,a,a);
	//0000 0110
	printf("nombre: %c, \t direccion:%d, \tvalor:%d, octal:%x\n",'b',&b,b,b);
	//0000 0000 0000 1101
	printf("nombre: %c, \t direccion:%d, \tvalor:%c, octal:%x\n",'c',&c,c,c);
	//0011 0110
    return 0;

}
