@echo off
echo Creando base de datos sistema_empleados...
mongosh sistema_empleados --quiet --eval "db.createCollection('usuarios'); db.createCollection('empleados'); db.createCollection('calculos'); print('Base de datos creada')"
pause
