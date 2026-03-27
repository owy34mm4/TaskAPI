package com.taskapi.task.infraestructure.persistance.entity.tasks;

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
@Table(name = "usuarios_x_tarea")  
@Builder @Getter  
public class UsersXTaskTable {  
  
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;  
  
    // ← ID externo, módulo user  
    @Column(name = "usuario_id", nullable = false)  
    private Long usuarioId;  
  
    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "tarea_id")  
    private TaskTable tarea;  
}