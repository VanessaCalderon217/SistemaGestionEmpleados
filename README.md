# SistemaGestionEmpleados
Sistema de gestión de empleados desarrollado en Java con programación orientada a objetos, Java Swing y MongoDB.

## Requisitos
- JDK 21
- NetBeans 22
- MongoDB Community Server
- mongosh
- MongoDB Database Tools (solo para generar o restaurar el respaldo)

## Instalacion
1. Tener MongoDB encendido en localhost:27017.
2. Crear la base de datos: doble clic en `crear_bd.bat`.
3. (Opcional) Cargar los datos de prueba: doble clic en `datos_prueba.bat`.
4. Abrir la carpeta `SistemaEmpleados` en NetBeans, hacer Clean and Build y ejecutar (Run).

Si no hay usuarios, el sistema pide registrar el primero. Si ya hay, muestra el login.

## Usuario de prueba
Se crea con `datos_prueba.bat`:
- Usuario: admin
- Contraseña: Admin2026!

En la base de datos solo se guarda el hash BCrypt de la contraseña.

## Base de datos
Nombre: `sistema_empleados`
- `usuarios`: usuario, nombre, passwordHash, activo
- `empleados`: codigo, nombres, apellidos, documento, cargo, tipo, fechaIngreso, activo y los datos de pago segun el tipo
- `calculos`: historial de remuneraciones por empleado y periodo

## Respaldo
Generar el respaldo (crea la carpeta `respaldo`):
```
respaldo.bat
```
Restaurar el respaldo:
```
mongorestore --drop --db sistema_empleados respaldo/sistema_empleados
```
