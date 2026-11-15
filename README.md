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

## Arquitectura y Paquetes del Proyecto
El sistema aplica separación de responsabilidades bajo el paradigma de Programación Orientada a Objetos:
- `com.sistemaempleados.modelo`: Implementa herencia y polimorfismo mediante la superclase abstracta `Empleado` y sus subclases (`EmpleadoAdministrativo`, `EmpleadoPorHora`, `EmpleadoPorComision`), además de `RegistroCalculo` y `Usuario`.
- `com.sistemaempleados`: Contiene la lógica de negocio, validaciones y controladores (`GestorEmpleados`, `GestorCalculos`, `GestorReportes`, `GestorUsuarios`, `ValidadorEmpleados`, `OrdenadorEmpleados`, `Sesion` y `SistemaEmpleados`).
- `UsuarioDAO` y `com.sistemaempleados.conexionba`: Capa de persistencia y conexión local con MongoDB (`ConexionMongo`, `EmpleadoDAO`, `CalculoDAO`, `UsuarioDAO`).
- `vista`: Interfaces gráficas desarrolladas en Java Swing (`LoginFrame`, `PrimerUsuarioFrame`, `MenuPrincipal`, `PanelEmpleados`, `PanelCalculos`, `PanelReportes`, `PanelUsuarios`).

## DIAGRAMAS
Los archivos editables originales (`.drawio`) se encuentran disponibles en este repositorio junto con sus exportaciones visuales:

### 1. Diagrama Jerárquico
![Diagrama Jerárquico](DIAGRAMAS/Diagrama%20Jerarquico.drawio.png)

### 2. Flujograma del Sistema
![Flujograma](DIAGRAMAS/Flujograma%20Sistema%20Empleados.png)

### 3. Diagrama de Clases UML
![Diagrama de Clases UML](DIAGRAMAS/Diagrana%20de%20Clases%20Sistema%20de%20Administracion%20de%20Empleados%202.drawio.png)

## Funcionalidades y Reglas de Negocio
- **Control de Acceso (Login):** Validación de credenciales mediante hash seguro (**BCrypt**) antes de habilitar los módulos del sistema.
- **Gestión Integral de Empleados:** Registro, consulta, búsqueda por criterio, modificación e inactivación lógica (sin pérdida de historial) para modalidades *Administrativo*, *Por Hora* y *Por Comisión*.
- **Cálculo Polimórfico de Remuneraciones:** Cálculo automático según el tipo de contratación (`calcularSalario()`), bloqueando cálculos a empleados inactivos y evitando duplicados por período.
- **Historial y Reportes Consolidados:** Almacenamiento de cada cálculo realizado y generación de reportes generales, por modalidad y resúmenes totales de empleados activos.
- **Gestión de Usuarios:** Administración de cuentas de acceso al sistema con activación e inactivación controlada.

## Estructura del Repositorio
- `SistemaEmpleados/`: Código fuente del proyecto en Java (JDK 21), formularios Swing y configuración Maven (`pom.xml`).
- `DIAGRAMAS/`: Modelos de arquitectura y diseño en formato editable (`.drawio`) e imágenes (`.png`).
- `crear_bd.bat` / `datos_prueba.bat` / `respaldo.bat`: Scripts automatizados para inicializar la base de datos `sistema_empleados` en MongoDB, cargar los registros de prueba y generar copias de respaldo.

## Integrantes del Equipo (GT 08 — GP 06)

| Nombre Completo | Carné |
| :--- | :--- |
| Alfaro Delgado, Bryan Francisco | AD25004 |
| Calderón Orellana, Vanessa Alejandra | CO26003 |
| Sarceño Velásquez, Kevin Alexander | SV19021 |
| Tobar Juárez, Elizandro de Jesús | TJ26001 |
