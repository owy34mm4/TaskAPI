package com.taskapi.task.infraestructure.persistance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity  
@Table(name = "tasks_status")  
@Builder @Getter  
public class TaskStatusTable {  
  
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;  
  
    @Column(nullable = false)  
    private String nombre;  
  
    // ← ID externo, módulo user  
    @Column(name = "creator_id")  
    private Long creatorId;  
}