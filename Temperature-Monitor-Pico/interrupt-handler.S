#include "hardware/regs/addressmap.h"
#include "hardware/regs/io_bank0.h"
#include "hardware/regs/timer.h"
#include "hardware/regs/m0plus.h"


.syntax unified                 @ Specify unified assembly syntax
.cpu    cortex-m0plus           @ Specify CPU type is Cortex M0+
.thumb                          @ Specify thumb assembly for RP2040
.global init                    @ Provide program starting address to the linker
.align 4                        @ Specify code alignment

.equ    DFLT_STATE_STRT, 1            @ Specify the value to start flashing
.equ    DFLT_STATE_STOP, 0            @ Specify the value to stop flashing
.equ    DFLT_ALARM_TIME, 1000000      @ Specify the default alarm timeout
.equ    ALRM_ISR_OFFSET, 0x40         @ ALARM0 is int #0 (vector table entry 16)
.equ    ADC_RSLT_OFFSET, 0x04         @ Offset for the result of the latest conversion


@ Entry point to the ASM portion of the program
init:
    BL      adc_init
    BL      install_alarm_isr
    LDR     R0, =DFLT_ALARM_TIME
    BL      alarm_set
main_asm:
    B       main_asm            @ Infinite loop


@ Subroutine used to initialize the ADC
adc_init:
    PUSH    {LR}
    MOVS    R0, #0xB
    LDR     R1, =(ADC_BASE)
    LDR     R2, [R1]
    ORRS    R2, R0
    LDR     R0, =#0x4000
    ORRS    R2, R0
    STR     R2, [R1]
    POP     {PC}
    

@ Subroutine used to intstall alarm interrupt
install_alarm_isr:
    LDR     R2, =(PPB_BASE + M0PLUS_VTOR_OFFSET)    
    LDR     R1, [R2]                                
    MOVS    R2, #ALRM_ISR_OFFSET                    
    ADDS    R2, R1
    LDR     R0, =alarm_isr_handler                  
    STR     R0, [R2]   

@ Subroutine used to enable the alarm interrupt    
alarm_isr_enable:
    MOVS    R0, #1
    LDR     R1, =(PPB_BASE + M0PLUS_NVIC_ICPR_OFFSET)
    STR     R0, [R1]
    LDR     R1, =(PPB_BASE + M0PLUS_NVIC_ISER_OFFSET)
    STR     R0, [R1]
    BX      LR

@ Subroutine used to schedule the alarm
alarm_set:
    MOVS    R3, #1
    LDR     R1, =(TIMER_BASE + TIMER_INTE_OFFSET)
    LDR     R2, [R1]
    ORRS    R2, R3
    STR     R2, [R1]
    LDR     R1, =(TIMER_BASE + TIMER_TIMELR_OFFSET)
    LDR     R2, [R1]
    ADD     R2, R0
    LDR     R1, =(TIMER_BASE + TIMER_ALARM0_OFFSET)
    STR     R2, [R1]
    BX      LR

@ Subroutine used to handle the alarm interrupts
@ TODO: make it work for temp sensor
.thumb_func
alarm_isr_handler:
    PUSH    {LR}
    LDR     R0, =(TIMER_BASE + TIMER_INTR_OFFSET)
    MOVS    R1, #1
    STR     R1, [R0]
    BL      read_data
    LDR     R6, =ltimer
    LDR     R0, [R6]
    BL      alarm_set
    POP     {PC}

@ Subroutine used to read raw data from the ADC
read_data:
    PUSH    {LR}
    LDR     R3, =(ADC_BASE + ADC_RSLT_OFFSET)
    LDR     R0, [R3]
    BL      printTemp
    POP     {PC}    

@ Set data alignment
.align 4

.data
ltimer: .word   DFLT_ALARM_TIME
