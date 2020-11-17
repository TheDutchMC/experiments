; x86-64 Assembly
; Compile: nasm -f elf64 -o helloworld.o helloworld.asm
; Link: ld -o helloworld helloworld.o
; Run: ./helloworld

section .text
    global _start     ;must be declared for linker (ld)

_start:
    mov edx,len     ;message length
    mov rsi,msg     ;message to write
    mov edi,1       ;file descriptor (stdout)
    mov eax,edi     ;system call number (sys_write)
    syscall         ;call kernel

    ;Exit the program gracefully
    xor edi, edi
    mov eax,60
    syscall

section .data
    msg db 'Hello world!', 0xa	;The String we want to print. 0xa is a new line
    len equ $ - msg     	;length of the string
