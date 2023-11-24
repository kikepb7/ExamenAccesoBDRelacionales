CREATE TABLE ALUMNOS (
    id INT PRIMARY KEY,
    nombre VARCHAR(255),
    dni VARCHAR(20),
    email VARCHAR(255)
);

CREATE TABLE ASIGNATURAS (
    id INT PRIMARY KEY,
    nombre VARCHAR(255),
    profesor VARCHAR(255)
);

CREATE TABLE TAREAS (
    id INT PRIMARY KEY,
    asignatura_id INT,
    descripcion VARCHAR(256),
    fecha_entrega DATE
);

CREATE TABLE EVALUACION (
    id INT PRIMARY KEY,
    alumno_id INT,
    tarea_id INT,
    fecha_realizacion DATE,
    puntuacion DOUBLE
);