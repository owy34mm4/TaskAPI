# TaskAPI
---

## ¿ Como iniciar el proyecto ?

    El proyecto está orquestado con docker-compose, lo cual facilita su reproducibilidad

#### Ejecutar este comando en la terminal ( A la altura del proyecto )

###### Desarrollo 
    #Bash
    docker compose up -f docker-compose.dev.yml 

###### Produccion
    ....


##### Servicios y sus Puertos Default ( Asignados en el proceso de Desarrollo )
- Database -> localhost:227
- PHPMyAdmin -> localhost:749
- Backend -> 0.0.0.0:900