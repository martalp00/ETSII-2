/*
 * 	Práctica 3 MARTA LÓPEZ PÉREZ
 * 	Descifrado sin usar acceso a memoria
 */

#include <stdio.h>
#include <stdlib.h>


void decrypt( unsigned int *v, unsigned int *k)
{
	unsigned int delta = 0x9e3779b9;
	unsigned int suma = 0xC6EF3720;

	for(int i = 0; i<32; i++)
	{
		v[1] -= ((v[0]<<4) + k[2]) ^ (v[0]+suma) ^ ((v[0]>>5) + k[3]);
		v[0] -= ((v[1]<<4) + k[0]) ^ (v[1]+suma) ^ ((v[1]>>5) + k[1]);
		suma -= delta;
	}
}

int main2(int argc, char *argv[] )
{
	FILE *fent = fopen("crisantemoCifrado.cyf", "rb");
	FILE *fsal = fopen("crisantemo_memoria.jpg", "wb");
	if (fent == NULL || fsal == NULL)
	{
		perror("No se ha podido abrir el fichero");
	}
	else
	{
		unsigned int tam;
		unsigned int v[2];
		unsigned int k[4] = {128, 129, 130, 131};

		fread (&tam, sizeof(unsigned int),1,fent);
		
		while(tam>=8)
		{
			fread (v, sizeof(unsigned int),2,fent);
			decrypt(v,k);
			fwrite (v, sizeof(unsigned int),2,fsal);
			tam -= 8;
		}

		if(tam>0)
		{
			fread (v, sizeof(unsigned int),2,fent);
			decrypt(v,k);
			fwrite (v, sizeof(char),tam,fsal);
		}
		fclose(fent);
		fclose(fsal);
	}
	return 0;
}
