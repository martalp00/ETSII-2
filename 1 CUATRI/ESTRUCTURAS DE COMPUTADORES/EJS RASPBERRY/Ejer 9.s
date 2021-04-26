/*Se hacen parpeadear los 6 leds uno detras de otro en secuencia manteniendo cada uno encendido 400 msg*/
	.include "inter.inc "
.text

/* Agrego vector interrupción */
	ADDEXC 0x18, irq_handler
	
/* Inicializo la pila en modos IRQ y SVC */
		mov 	r0, # 0b11010010 @ Modo IRQ, FIQ & IRQ desact
		msr 	cpsr_c, r0
		mov 	sp, # 0x8000
		mov 	r0, # 0b11010011 @ Modo SVC, FIQ & IRQ desact
		msr 	cpsr_c, r0
		mov 	sp, # 0x8000000
	
		ldr 	r0, = 
		//xxxx999888777666555444333222111000
		ldr 	r1, =0b00001000000000000000000000000000
		str 	r1, [r0,#GPFSEL0]
		ldr 	r1, =0b00000000001000000000000000001001
		str 	r1, [r0,#GPFSEL1]
		ldr 	r1, =0b00000000001000000000000001000000
		str 	r1, [r0,#GPFSEL2]
	
		ldr 	r0, = INTBASE
		mov 	r1, # 0b0010
		str 	r1, [ r0, # INTENIRQ1 ]
		mov 	r0, # 0b01010011 			@ Modo SVC, IRQ activo
		msr 	cpsr_c, r0
	
		ldr 	r0, =STBASE
		
		ldr 	r1, [ r0, # STCLO ]
		ldr 	r2, =400000
		add 	r1, r2
		str 	r1, [ r0, # STC1 ]
	
bucle: 
	
		b 		bucle

irq_handler :

		push 	{r0, r1, r2, r3 }
		ldr 	r0, =GPBASE
		ldr 	r1, =0b00001000010000100000111000000000
		str 	r1, [r0, #GPCLR0]
		ldr 	r1, =cuenta
		ldr 	r2, [r1]
		cmp 	r2,#6 						@Actualiza los flags
		add 		r2, #1
		moveq 	r2, #1 @Si es igual lo mueve
		str 	r2, [r1] 					@Guardo r2 en la direccion .de. la variable
		ldr 	r3, [ r1, + r2, LSL #2 ]	@Da la posicion del array que este en r2
		str 	r3, [r0, #GPSET0]
			
		ldr 	r0, =STBASE
		mov 	r3, #0b0010 				@Resetear la interrupcion
		str 	r3, [r0, #STCS]
		
		ldr 	r1, [ r0, # STCLO ]
		ldr 	r2, =400000
		add 	r1, r2
		str 	r1, [ r0, # STC1 ]
		pop 	{ r0, r1, r2, r3}
		subs	pc, lr, #4
		
cuenta: 
		.word 6
	
		//xx10987654321098765432109876543210
	
secuencia: 
		.word 	0b00000000000000000000001000000000
		.word 	0b00000000000000000000010000000000
		.word 	0b00000000000000000000100000000000
		.word 	0b00000000000000100000000000000000
		.word 	0b00000000010000000000000000000000
		.word 	0b00001000000000000000000000000000	
		
		
		
		
		
		
		
		
		
		
		
@EJERCICIO 9 OTRO MODO

onoff: .word 0

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

/* Configuro GPIO 9 como salida */
        ldr     r0, =GPBASE
/* guia bits              xx999888777666555444333222111000*/
        mov     r1, #0b00001000000000000000000000000000
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

/* Programo contador C1 para futura interrupcion */
        ldr     r0, =STBASE
        ldr     r1, [r0, #STCLO]
        add     r1, #0x40000     @0.4 segundos
        str     r1, [r0, #STC1]

/* Habilito interrupciones, local y globalmente */
        ldr     r0, =INTBASE
        mov     r1, #0b0010
        str     r1, [r0, #INTENIRQ1]
        mov     r0, #0b01010011   @ Modo SVC, IRQ activo
        msr     cpsr_c, r0

/* Repetir para siempre */
bucle:  b       bucle

/* Rutina de tratamiento de interrupción */
irq_handler:
	push {r0, r1, r2}	@Salvo registros
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
	mov r1, #0b0010
	str r1, [r0, #STCS]
	ldr r1, [r0, #STCLO]
	add r1, #0x40000
	str r1, [r0, #STC1]
	pop {r0, r1}	        @Recupero registros
	subs pc, lr, #4		@Salgo de la RTI
	