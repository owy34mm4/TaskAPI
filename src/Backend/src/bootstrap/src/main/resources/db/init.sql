-- Roles del sistema (fijos)
INSERT INTO system_roles (code, nombre, descripcion, scope)
SELECT 'OWNER', 'Owner', 'Propietario del equipo', 'SYSTEM'
WHERE NOT EXISTS (SELECT 1 FROM system_roles WHERE code = 'OWNER');

INSERT INTO system_roles (code, nombre, descripcion, scope)
SELECT 'OBSERVADOR', 'Observador', 'Solo lectura', 'SYSTEM'
WHERE NOT EXISTS (SELECT 1 FROM system_roles WHERE code = 'OBSERVADOR');

-- Permisos del sistema (fijos)
INSERT INTO permissions (code, nombre) VALUES
('TASK_CREATE',  'Crear tarea'),
('TASK_DELETE',  'Eliminar tarea'),
('TASK_ASSIGN',  'Asignar tarea'),
('TEAM_MANAGE',  'Gestionar equipo'),
('TEAM_VIEW',    'Ver equipo'),
('TASK_VIEW',   'Ver tareas');

-- Permisos del OWNER (todos)
INSERT INTO permissions_x_role (permiso_id, rol_id)
SELECT p.id, r.id FROM permissions p, system_roles r
WHERE r.code = 'OWNER';

-- Permisos del OBSERVADOR (solo lectura)
INSERT INTO permissions_x_role (permiso_id, rol_id)
SELECT p.id, r.id FROM permissions p, system_roles r
WHERE r.code = 'OBSERVADOR' AND p.code IN ('TASK_VIEW', 'TEAM_VIEW');