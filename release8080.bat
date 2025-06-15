@echo off
echo Buscando procesos que usan el puerto 8080...

rem Obtiene la lista de conexiones en el puerto 8080 y extrae los PID
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8080') do (
    echo Terminando proceso con PID %%a
    taskkill /F /PID %%a >nul 2>&1
)

echo Todos los procesos en el puerto 8080 han sido terminados.
pause
