-- Insertar datos en la tabla ALUMNOS
INSERT INTO ALUMNOS (id, nombre, dni, email) VALUES (1, 'Juan Pérez', '12345678A', 'juan.perez@example.com');
INSERT INTO ALUMNOS (id, nombre, dni, email) VALUES (2, 'María García', '98765432B', 'maria.garcia@example.com');
INSERT INTO ALUMNOS (id, nombre, dni, email) VALUES (3, 'Carlos Ruiz', '56789012C', 'carlos.ruiz@example.com');
INSERT INTO ALUMNOS (id, nombre, dni, email) VALUES (4, 'Laura Martínez', '34567890D', 'laura.martinez@example.com');
INSERT INTO ALUMNOS (id, nombre, dni, email) VALUES (5, 'Pedro Rodríguez', '01234567E', 'pedro.rodriguez@example.com');
-- Insertar datos en la tabla ASIGNATURAS
INSERT INTO ASIGNATURAS (id, nombre, profesor) VALUES (1, 'Matemáticas', 'Profesor López');
INSERT INTO ASIGNATURAS (id, nombre, profesor) VALUES (2, 'Historia', 'Profesora González');
INSERT INTO ASIGNATURAS (id, nombre, profesor) VALUES (3, 'Ciencias Naturales', 'Profesora Sánchez');
INSERT INTO ASIGNATURAS (id, nombre, profesor) VALUES (4, 'Inglés', 'Profesor Martínez');
INSERT INTO ASIGNATURAS (id, nombre, profesor) VALUES (5, 'Programación', 'Profesor Rodríguez');
-- Insertar datos en la tabla TAREAS
INSERT INTO TAREAS (id, asignatura_id, descripcion, fecha_entrega) VALUES (1, 1, 'Resolver ecuaciones lineales', '2023-11-20');
INSERT INTO TAREAS (id, asignatura_id, descripcion, fecha_entrega) VALUES (3, 3, 'Realizar experimento de biología', '2023-11-25');
INSERT INTO TAREAS (id, asignatura_id, descripcion, fecha_entrega) VALUES (4, 4, 'Preparar presentación oral en inglés', '2023-11-30');
INSERT INTO TAREAS (id, asignatura_id, descripcion, fecha_entrega) VALUES (5, 5, 'Desarrollar programa en Python', '2023-12-05');
INSERT INTO TAREAS (id, asignatura_id, descripcion, fecha_entrega) VALUES (6, 1, 'Estudio de funciones cuadráticas', '2023-12-10');
INSERT INTO TAREAS (id, asignatura_id, descripcion, fecha_entrega) VALUES (7, 1, 'Resolución de problemas trigonométricos', '2023-12-15');
INSERT INTO TAREAS (id, asignatura_id, descripcion, fecha_entrega) VALUES (8, 3, 'Investigación sobre la célula', '2023-12-18');
INSERT INTO TAREAS (id, asignatura_id, descripcion, fecha_entrega) VALUES (9, 3, 'Análisis de ecosistemas locales', '2023-12-22');
INSERT INTO TAREAS (id, asignatura_id, descripcion, fecha_entrega) VALUES (10, 5, 'Desarrollo de aplicación web', '2023-12-28');
-- Insertar datos en la tabla EVALUACION
INSERT INTO EVALUACION (id, alumno_id, tarea_id, fecha_realizacion, puntuacion) VALUES (1, 1, 1, '2023-11-21', 9.3);
INSERT INTO EVALUACION (id, alumno_id, tarea_id, fecha_realizacion, puntuacion) VALUES (3, 3, 3, '2023-11-26', 5.7);
INSERT INTO EVALUACION (id, alumno_id, tarea_id, fecha_realizacion, puntuacion) VALUES (4, 4, 4, '2023-12-01', 8.3);
INSERT INTO EVALUACION (id, alumno_id, tarea_id, fecha_realizacion, puntuacion) VALUES (7, 2, 3, '2023-11-27', 9.5);
INSERT INTO EVALUACION (id, alumno_id, tarea_id, fecha_realizacion, puntuacion) VALUES (8, 3, 4, '2023-12-02', 4.7);
INSERT INTO EVALUACION (id, alumno_id, tarea_id, fecha_realizacion, puntuacion) VALUES (9, 4, 5, '2023-12-07', 3.3);
INSERT INTO EVALUACION (id, alumno_id, tarea_id, fecha_realizacion, puntuacion) VALUES (11, 1, 6, '2023-12-12', 1.6);
INSERT INTO EVALUACION (id, alumno_id, tarea_id, fecha_realizacion, puntuacion) VALUES (12, 2, 7, '2023-12-17', 8.6);
INSERT INTO EVALUACION (id, alumno_id, tarea_id, fecha_realizacion, puntuacion) VALUES (13, 3, 8, '2023-12-20', 4.9);
INSERT INTO EVALUACION (id, alumno_id, tarea_id, fecha_realizacion, puntuacion) VALUES (14, 4, 9, '2023-12-24', 6.8);
INSERT INTO EVALUACION (id, alumno_id, tarea_id, fecha_realizacion, puntuacion) VALUES (15, 4, 10, '2023-12-24', 2.8);
