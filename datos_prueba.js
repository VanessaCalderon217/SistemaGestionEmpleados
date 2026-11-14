// Datos de prueba para el sistema de empleados
db = db.getSiblingDB("sistema_empleados");

// Usuario de prueba: admin / Admin2026! (se guarda solo el hash)
db.usuarios.updateOne(
    { usuario: "admin" },
    { $set: { usuario: "admin", nombre: "Usuario de Prueba", activo: true,
              passwordHash: "$2a$10$0zNoZws026G/U.13K5jLUuRpaw7pkG2/z.puGDUWC1ljdntORUO8u" } },
    { upsert: true }
);

// Borrar empleados y calculos de prueba anteriores
var codigos = ["EMP002", "EMP003", "EMP004", "EMP005", "EMP006", "EMP007", "EMP008"];
db.empleados.deleteMany({ codigo: { $in: codigos } });
db.calculos.deleteMany({ codigoEmpleado: { $in: codigos } });

// Empleados de prueba
db.empleados.insertMany([
    { codigo: "EMP002", nombres: "Carlos", apellidos: "Martinez", documento: "02345678-9", cargo: "Asistente", tipo: "Administrativo", fechaIngreso: "2022-07-01", activo: true, salarioMensual: Double(650) },
    { codigo: "EMP003", nombres: "Luis", apellidos: "Perez", documento: "03456789-0", cargo: "Tecnico", tipo: "Por Hora", fechaIngreso: "2023-01-10", activo: true, precioPorHora: Double(5.5), horasTrabajadas: 160 },
    { codigo: "EMP004", nombres: "Sofia", apellidos: "Ramirez", documento: "04567890-1", cargo: "Disenadora", tipo: "Por Hora", fechaIngreso: "2023-09-05", activo: true, precioPorHora: Double(7.25), horasTrabajadas: 120 },
    { codigo: "EMP005", nombres: "Rosa", apellidos: "Diaz", documento: "05678901-2", cargo: "Vendedora", tipo: "Por Comision", fechaIngreso: "2024-02-20", activo: true, porcentajeComision: Double(10), ventasTotales: Double(6500) },
    { codigo: "EMP006", nombres: "Jorge", apellidos: "Flores", documento: "06789012-3", cargo: "Vendedor", tipo: "Por Comision", fechaIngreso: "2022-11-12", activo: true, porcentajeComision: Double(12.5), ventasTotales: Double(8000) },
    { codigo: "EMP007", nombres: "Marta", apellidos: "Gonzalez", documento: "07890123-4", cargo: "Recepcionista", tipo: "Administrativo", fechaIngreso: "2020-05-18", activo: false, salarioMensual: Double(500) },
    { codigo: "EMP008", nombres: "Daniel", apellidos: "Mejia", documento: "08901234-5", cargo: "Auxiliar de bodega", tipo: "Por Hora", fechaIngreso: "2024-06-03", activo: true, precioPorHora: Double(4), horasTrabajadas: 176 }
]);

// Calculos de prueba del mes de octubre
db.calculos.insertMany([
    { codigoEmpleado: "EMP002", nombreEmpleado: "Carlos Martinez", tipoEmpleado: "Administrativo", periodo: "Octubre 2026", fechaCalculo: "2026-10-31",
      salarioMensual: Double(650), precioPorHora: Double(0), horasTrabajadas: 0, porcentajeComision: Double(0), ventasTotales: Double(0), remuneracion: Double(650) },
    { codigoEmpleado: "EMP003", nombreEmpleado: "Luis Perez", tipoEmpleado: "Por Hora", periodo: "Octubre 2026", fechaCalculo: "2026-10-31",
      salarioMensual: Double(0), precioPorHora: Double(5.5), horasTrabajadas: 160, porcentajeComision: Double(0), ventasTotales: Double(0), remuneracion: Double(880) },
    { codigoEmpleado: "EMP005", nombreEmpleado: "Rosa Diaz", tipoEmpleado: "Por Comision", periodo: "Octubre 2026", fechaCalculo: "2026-10-31",
      salarioMensual: Double(0), precioPorHora: Double(0), horasTrabajadas: 0, porcentajeComision: Double(10), ventasTotales: Double(6500), remuneracion: Double(650) }
]);

print("Datos de prueba cargados");
print("Empleados: " + db.empleados.countDocuments());
print("Calculos: " + db.calculos.countDocuments());
