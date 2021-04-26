/*
 * 	Práctica 3 MARTA LÓPEZ PÉREZ
 * 	Descifrado usando acceso a memoria
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void decrypt_memoria( unsigned int *v, unsigned int *k)
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

int main1(int argc, char *argv[] )
{
	FILE *fent = fopen("crisantemoCifrado.cyf", "rb");
	FILE *fsal = fopen("crisantemo_memoria.jpg", "wb");
	if (fent == NULL || fsal == NULL)
	{
		perror("no se ha podido abrir el fichero");
	}
	else
	{
		unsigned int tam;
		unsigned int v[2];
		unsigned int k[4] = {128, 129, 130, 131};

		fread (&tam, sizeof(unsigned int),1,fent);

		unsigned int reserva = tam;

		if(tam%8 != 0)	//25 = 3*24 + 1
		{
			reserva += (8 - reserva%8); //25 + (8-1)=32
		}
		unsigned int *buffer = malloc(reserva);	//Reserva espacio en la memoria
		unsigned int *cbuffer = buffer;

		fread (buffer, 1, reserva, fent);

		while(tam>=8)
		{
			//fread (v, sizeof(unsigned int),2,fent);
			memcpy(v, cbuffer, 8);
			decrypt_memoria(v,k);
			fwrite (v, sizeof(unsigned int),2,fsal);
			cbuffer += 2;
			tam -= 8;
		}

		if(tam>0)
		{
			//fread (v, sizeof(unsigned int),2,fent);
			memcpy(v, cbuffer, 8);
			decrypt_memoria(v,k);
			fwrite (v, sizeof(char),tam,fsal);
			cbuffer += 2;
		}
		free(buffer);
		fclose(fent);
		fclose(fsal);
	}
	return 0;
}
