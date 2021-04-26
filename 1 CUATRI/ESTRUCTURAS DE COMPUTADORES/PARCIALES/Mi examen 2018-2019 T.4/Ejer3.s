        .include  "inter.inc"
.text
	@Agrego vector interrupcion
        ADDEXC  0x18, irq_handler

	@Inicializo la pila en modos FIQ, IRQ y SVC
	mov r0, #0b11010001
	msr cpsr_c, r0
	mov sp, #0x4000
	
        mov     r0, #0b11010010   @ Modo IRQ, FIQ&IRQ desact
        msr     cpsr_c, r0
        mov     sp, #0x8000
	
        mov     r0, #0b11010011   @ Modo SVC, FIQ&IRQ desact
        msr     cpsr_c, r0
        mov     sp, #0x8000000

	@configuro todos los leds
        ldr     r0, =GPBASE
        ldr   	r1, = 0b00001000000000000000000000000000
        str	r1, [r0, #GPFSEL0]  @ Configura GPIO 9, 3 y 4
        ldr   	r1, = 0b00000000001000000000000000001001
        str	r1, [r0, #GPFSEL1]  @ Configura GPIO 10, 11, 17
	ldr   	r1, = 0b00000000001000000000000001000000
        str	r1, [r0, #GPFSEL2]  @ Configura GPIO 22, 27
	
	@enciende todos los leds de inicio
        ldr   	 r1, = 0b00001000010000100000111000000000
        str     r1, [r0, #GPSET0]   @ Enciende GPIO 9, 10, 11, 17, 22, 27
	
	@Programo los pulsadores
	ldr	 r0,=GPBASE
	ldr	 r1,= 0b00000000000000000000000000001100
	str	 r1, [r0,#GPFEN0]

	@Habilito interrupciones, local y globalmente
	ldr	r0,=INTBASE
	ldr     r1, = 0b00000000000100000000000000000000
	str	r1, [r0,#INTENIRQ2]
	
	ldr r0, = STBASE
	ldr r1, [r0, # STCLO ]
	add r1, #2
	str r1, [r0, # STC1 ]
	str r1, [r0, # STC3 ]
	
        mov     r0, #0b01010011   @ Modo SVC, IRQ activo
        msr     cpsr_c, r0

	@Repetir para siempre
bucle:  b       bucle

	@Rutina de tratamiento de interrupciÃ³n
	
irq_handler:
        push    {r0, r1, r2}          @ Salvo registros
	
	@Compruebo si se ha pulsado el boton 1
	ldr	r0,=GPBASE
	ldr	r2, [r0,#GPEDS0]
	ands	r2,#0b00000000000000000000000000000100
	
	ldrne   r1, = 0b00001000010000100000111000000000
        streq      r1, [r0, #GPSET0]   @ Enciende GPIO 9, 10, 11, 17, 22, 27
	
	@Compruebo si se ha pulsado el boton 2
	ldr	r0,=GPBASE
	ldr	r2, [r0,#GPEDS0]
	ands	r2,#0b00000000000000000000000000001000
	
	@Si es así, apaga GPIO 9, 10, 11, 17, 22, 27
	ldreq   r1, = 0b00001000010000100000111000000000
        strne    r1, [r0, #GPCLR0]
	
	ldr r0, =STBASE
	mov r1, #0b1010
	str r1, [r0, #STCS]
	
	@compruebo si pulso cualquiera de los dos botones
	ldr	r0,=GPBASE
	ldr	r1, =0b00000000000000000000000000001100
	str r1, [r0,#GPFEN0]
	
        pop     {r0, r1, r2}          @ Recupero registros
        subs    pc, lr, #4        @ Salgo de la RTI

onoff: .word 0
bitson: .word 0