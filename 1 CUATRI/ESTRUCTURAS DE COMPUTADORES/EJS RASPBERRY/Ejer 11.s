.include "inter.inc"
	.text
		mov 	r0, #0
		ADDEXC 0x18, irq_handler
/*Inicializacion modo IRQ*/
		mov 	r0, #0b11010010
		msr 	cpsr_c, r0
		mov 	sp, #0x8000
/*Inicializacion modo SVC*/
		mov 	r0, #0b11010011
		msr 	cpsr_c, r0
		mov 	sp, #0x800000
		ldr 	r0, =GPBASE
				// xx999888777666555444333222111000
		ldr 	r1, =0b00001000000000000001000000000000
		str 	r1, [r0, #GPFSEL0]
				// xx999888777666555444333222111000
		ldr 	r1, =0b00000000001000000000000000001001
		str 	r1, [r0, #GPFSEL1]
				// xx999888777666555444333222111000
		ldr 	r1, =0b00000000001000000000000001000000
		str 	r1, [r0, #GPFSEL2]
		ldr 	r0, =STBASE
		ldr 	r1, [r0, #STCLO]
		ldr 	r2, =200000
		add 	r1, r2
		str 	r1, [r0, #STC1]
		str 	r1, [r0, #STC3]
		ldr 	r0, =INTBASE
		mov 	r1, #0b1010
		str 	r1, [r0, #INTENIRQ1]
		mov 	r0, #0b01010011
		msr 	cpsr_c, r0
bucle: 
		b 		bucle

irq_handler:
		push 	{r0, r1, r2, r3, r4, r5, r6, r7}
		ldr 	r1, =GPBASE
		ldr 	r0, =STBASE
		ldr 	r7, [r0, #STCS]
		cmp 	r7, #0b0010
		bne 	sonido
		ldr r2, =cuenta
//	guia bits 		  10987654321098765432109876543210
		ldr 	r6, =0b0001000010000100000111000000000
		
/*Apago todos los leds*/
		str 	r6,[r1,#GPCLR0]
		
/*Cargo en r3 el contenido de la variable cuenta*/
		ldr 	r3, [r2]
		ldr 	r4, =secuencia
		
/*Cargo en r3 el contenido de la posición de secuencia determinado por la variable cuenta*/
		ldr 	r3, [r4, r3, LSL #2]
		str 	r3, [r1, #GPSET0]
		ldr 	r3, [r2]
		add 	r3, #1
		cmp 	r3, #6
		moveq 	r3, #0
		str 	r3, [r2]
		
/*Reseteo el estado de la interrupción C1*/
		mov 	r3, #0b0010
		str 	r3, [r0, #STCS]
		
/*Programo la siguiente interrupción en 200ms*/
		ldr 	r3, [r0, #STCLO]
		ldr 	r2, =200000
		add 	r3, r2
		str 	r3, [r0, #STC1]
		ldr 	r7, [r0, #STCS]
		cmp 	r7, #0b01000
		bne 	fin
sonido:
		ldr 	r2, =onoff
		ldr 	r3, [r2]
		eors	r3, #1
		str 	r3, [r2]
		mov 	r3, #0b10000
		streq 	r3, [r1, #GPSET0]
		strne 	r3, [r1, #GPCLR0]
		mov 	r3, #0b1000
		str 	r3, [r0, #STCS]
		ldr 	r3, [r0, #STCLO]
		ldr 	r2, =1136
		add 	r3, r2
		str 	r3, [r0, #STC3]

fin: 
		pop 	{r0, r1, r2, r3, r4, r5, r6, r7}
		subs 	pc, lr, #4
onoff: 
		.word 	0
cuenta: 
		.word 	0
secuencia: 
		.word 	0b00000000000000000000001000000000
		.word 	0b00000000000000000000010000000000
		.word 	0b00000000000000000000100000000000
		.word 	0b00000000000000100000000000000000
		.word 	0b00000000010000000000000000000000
		.word 	0b00001000000000000000000000000000
		
		
		
		
		
		
		
		
		
		
		
@EJERCICIO 11 OTRO MODO
.include  "inter.inc"
.text
/* Agrego vector interrupcion */
        ADDEXC  0x18, irq_handler


/* Inicializo la pila en modos IRQ y SVC */
        mov     r0, #0b11010010   @ Modo IRQ, FIQ&IRQ desact
        msr     cpsr_c, r0
        mov     sp, #0x8000
        mov     r0, #0b11010011   @ Modo SVC, FIQ&IRQ desact
        msr     cpsr_c, r0
        mov     sp, #0x8000000

/* Configuro GPIO 9 y 4  como salida */
        ldr     r0, =GPBASE
/* guia bits              xx999888777666555444333222111000*/
        ldr     r1, =0b00001000000000000001000000000000
        str     r1, [r0, #GPFSEL0]
	
/* Configuro GPIO 10, 11 y 17 como salida */
        ldr     r0, =GPBASE
/* guia bits           xx999888777666555444333222111000*/
        ldr     r1, =0b00000000001000000000000000001001
        str     r1, [r0, #GPFSEL1]
	
/* Configuro GPIO 22 y 27 como salida */
        ldr     r0, =GPBASE
/* guia bits           xx999888777666555444333222111000*/
        ldr     r1, =0b00000000001000000000000001000000
        str     r1, [r0, #GPFSEL2]
	
/* guia bits          10987654321098765432109876543210*/
        ldr	r0, =STBASE	@ r0 es un parametro de sonido (dir base ST)
	ldr	r2, =1136	@ r1 es un parametro de sonido (periodo/2)

/* Programo contador C1 para futura interrupcion */
        ldr     r0, =STBASE
        ldr     r1, [r0, #STCLO]
        add     r1, r2      
        str     r1, [r0, #STC1]
	
/* Programo contador C3 para futura interrupcion */
        ldr     r0, =STBASE
        ldr     r1, [r0, #STCLO]
        add     r1, #0x20000     @0.2 segundos
        str     r1, [r0, #STC3]

/* Habilito interrupciones, local y globalmente */
        ldr     r0, =INTBASE
        mov     r1, #0b1010
        str     r1, [r0, #INTENIRQ1]
        mov     r0, #0b01010011   @ Modo SVC, IRQ activo
        msr     cpsr_c, r0
	


/* Repetir para siempre */
bucle:  b       bucle

/* Rutina de tratamiento de interrupción */
irq_handler:
	push {r0, r1, r2, r4}	@Salvo registros
	ldr r0, =STBASE
	ldr r2, [r0, #STCS]
	ands r2, #0b1000 @C3?
	bne C3
	ldr r2, [r0, #STCS]
	ands r2, #0b0010 @C1?
	bne C1
	
C3:
	ldr r0, =GPBASE
	ldr r1, =onoff
	ldr r2, [r1]
	mov r3, #6
	
modulo:
	sub r4, r2, r3
	cmp r4, #0
	blt final
	mov r2, r4
	b modulo
	
final:
	add r4, r4, r3
	cmp r4, #0
/* guia bits          10987654321098765432109876543210*/
	mov r1, #0b00000000000000000000001000000000
	streq r1, [r0, #GPSET0]	@Enciendo LED 9
	mov r1, #0b00001000000000000000000000000000
	streq r1, [r0, #GPCLR0]	@Apago LED 27
	
	cmp r4, #1
/* guia bits          10987654321098765432109876543210*/
	mov r1, #0b00000000000000000000010000000000
	streq r1, [r0, #GPSET0]	@Enciendo LED 10
	mov r1, #0b00000000000000000000001000000000
	streq r1, [r0, #GPCLR0]	@Apago LED 9
	
	cmp r4, #2
/* guia bits          10987654321098765432109876543210*/
	mov r1, #0b00000000000000000000100000000000
	streq r1, [r0, #GPSET0]	@Enciendo LED 11
	mov r1, #0b00000000000000000000010000000000
	streq r1, [r0, #GPCLR0]	@Apago LED 10
	
	cmp r4, #3
/* guia bits          10987654321098765432109876543210*/
	mov r1, #0b00000000000000100000000000000000
	streq r1, [r0, #GPSET0]	@Enciendo LED 17
	mov r1, #0b00000000000000000000100000000000
	streq r1, [r0, #GPCLR0]	@Apago LED 11
	
	cmp r4, #4
/* guia bits          10987654321098765432109876543210*/
	mov r1, #0b00000000010000000000000000000000
	streq r1, [r0, #GPSET0]	@Enciendo LED 22
	mov r1, #0b00000000000000100000000000000000
	streq r1, [r0, #GPCLR0]	@Apago LED 17
	
	cmp r4, #5
/* guia bits          10987654321098765432109876543210*/
	mov r1, #0b00001000000000000000000000000000
	streq r1, [r0, #GPSET0]	@Enciendo LED 27
	mov r1, #0b00000000010000000000000000000000
	streq r1, [r0, #GPCLR0]	@Apago LED 22

	add r4, r4, #1
	ldr r1, =onoff
	str r4, [r1]
	ldr r0, =STBASE
	mov r1, #0b1000
	str r1, [r0, #STCS]
	ldr r1, [r0, #STCLO]
	add r1, #0x20000
	str r1, [r0, #STC3]
	b salida

C1:
	ldr r1, =onofff
	ldr r2, [r1]
	eors r2, #1
	str r2, [r1]
	mov	r1, #0b00000000000000000000000000010000
	ldr r0, =GPBASE
	strne r1, [r0, #GPSET0]
	streq r1, [r0, #GPCLR0]
	ldr r0, =STBASE
	mov r1, #0b0010
	str r1, [r0, #STCS]
	ldr r1, [r0, #STCLO]
	ldr	r2, =1136	@ r1 es un parametro de sonido (periodo/2)
	add r1, r2
	str r1, [r0, #STC1]
	b salida

salida:	
	pop {r0, r1, r2, r4}	        @Recupero registros
	subs pc, lr, #4		@Salgo de la RTI

onoff: .word 0
onofff: .word 0
	