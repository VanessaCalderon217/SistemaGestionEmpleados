@echo off
echo Generando respaldo de la base de datos...
mongodump --db sistema_empleados --out "%~dp0respaldo"
echo Respaldo guardado en la carpeta respaldo
pause
