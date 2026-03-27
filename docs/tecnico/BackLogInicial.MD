# TaskAPI 

{

    Famosa Aplicación de Tasks -> 20k ejemplos WEB

    - Requerimientos Mínimos ->

      - Arquitectura -> ???

      - Lenguajes -> ???

      - FrameWorks -> ???

	### BACKLOG

 - Casos de uso ->

	
- Backend ->
  
  - Usuario
	  - CreateUser -> Crear Usuario || status = ?
	  - GetUserByEmailAndPassword -> Obtener Usuario Por Email y Contraseña || status = ?
	  - GetAllUsers -> Traer Todos los usuarios || status = ?
	  - ChangePassword -> Cambiar Contraseña || status = ?
	  - DeleteUser -> Eliminar Usuario || status = ?
	    
- Equipo
	- CreateTeam -> Crear Equipo || status = Funcionando[Falta test y Swagger]: {Crea Equipo y relaciona [dueñoEquipo, Rol owner('systemRole')], requiere JWT}

	- GetAllTeamsByUserId -> Trear todos los equipos Por Usuario ID || status = ?

	-  JoinUserToATeam -> Ingresar usuario a un Equipo || status = ?

	- RemoveUserFromATeam -> Eliminar Usuario de un Equipo || status = ?

	- ChangeTeamName -> Cambiar Nombre del Equipo || status = ?
	
	- DeleteTeam -> Eliminar Equipo || status = ?
	  
		- Rol
			- CreateRole -> Crear Rol || status = ?
			- GetAllRoles -> Traer Todos Los Roles || status = ?
			- AssingRolesToAUser -> Asignar Roles a un usuario || status = ?
			  
		- Tarea
			-  CreateTask -> Crear Tarea || status = ?
			-  AssingUsersToATask-> Asignar Usuarios a una Tarea || status = ?
			- GetAllTasks -> Traer Todas las tasks || status = ?
			- GetAllHistoryTaskByTeamId -> Obtener todos los historicos de Tarea por EquipoId || status = ?
			- DeleteTaskById -> Eliminar Tarea Por Id || status = ?
------





- Frontend-> 
	- Vista Inicio Sesion
		 - Botón Olvidó Contraseña || status =?
		- Boton Login || status =?
		- Contraseña Oculta ( Boton ojito ) || status =?
	- Vista DashBoard Seleccion Equipos del usuario ( HomePage)
		- Modal Y Boton para unirse a un equipo  || status =?
		-   Botón Crear Equipo  || status =?
		- Listado con todos los equipos a los que pertenece el usuario || status =?
	- Vista DashBoard Equipo Seleccionado
		  -  Si es admin, permite acciones de edicion || status =?
		  - Con permisos suficientes, permite crear tareas || status =?
	- Vista DashBoad Tareas en el Equipo
		- Asignar Tareas a Usuarios || status =?
		- Editar Tarea si el privilegio lo permite || status = ? 
	- Seccion Configuraciones de la Cuenta
		 - Boton y Modal Eliminar Cuenta  || status =?
		- Cambiar informacion de Cuenta   || status =?
	  
-


  }
