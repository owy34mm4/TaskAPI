package com.taskapi.task.infraestructure.persistance.entity.logRecords;

import java.time.LocalDateTime;

import com.taskapi.task.infraestructure.persistance.entity.TeamTable;
import com.taskapi.task.infraestructure.persistance.entity.tasks.TaskStatusTable;
import com.taskapi.task.infraestructure.persistance.entity.tasks.TaskTable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity  
@Table(name = "historico_tarea")  
@Builder @Getter  
public class TaskRecordTable {  
  
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;  
  
    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "tarea_id")  
    private TaskTable tarea;  
  
    private String nombre;  
  
    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "equipo_id")  
    private TeamTable equipo;  
  
    private LocalDateTime creacion;  
  
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_id")
    private TaskStatusTable estadoId;  
  
    private String descripcion;  
    
    // ← IDs externos, módulo user  
    @Column(name = "solicitante_id")  
    private Long solicitanteId;  
}
