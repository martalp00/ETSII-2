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

	@configuro los dos leds
        ldr     r0, =GPBASE
        mov     r1, #0b00001000000000000000000000000000
        str     r1, [r0, #GPFSEL0]

        mov     r1, #0b00000000000000000000000000000001
        str     r1, [r0, #GPFSEL1]
	
/* Programo contador C3 para futura interrupcion */
        ldr     r0, =STBASE
        ldr     r1, [r0, #STCLO]
        add     r1, #0x50000     @retardo de medio segundo
        str     r1, [r0, #STC3]

/* Habilito interrupciones, local y globalmente */
        ldr     r0, =INTBASE
        mov     r1, #0b1000
        str     r1, [r0, #INTENIRQ1]
        mov     r0, #0b01010011   @ Modo SVC, IRQ activo
        msr     cpsr_c, r0

/* Repetir para siempre */
bucle:  b       bucle

/* Rutina de tratamiento de interrupción */
irq_handler:
        push    {r0, r1, r2}          @ Salvo registros
	
	ldr r0, =GPBASE
	ldr r1, =onoff
	ldr r2, [r1]
	eors r2, #1
	str r2, [r1]
	
        mov r1, #0b00000000000000000000011000000000
	strne r1, [r0, #GPSET0]
	streq r1, [r0, #GPCLR0]
	
	ldr r0, =STBASE
	mov r1, #0b1000
	str r1, [r0, #STCS]
	
	ldr r1, [r0, #STCLO]
	add r1, #0x50000
	str r1, [r0, #STC3]

        pop     {r0, r1, r2}          @ Recupero registros
        subs    pc, lr, #4        @ Salgo de la RTI

onoff: .word 0
