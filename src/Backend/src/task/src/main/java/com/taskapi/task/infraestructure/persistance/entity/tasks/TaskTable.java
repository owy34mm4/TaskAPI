package com.taskapi.task.infraestructure.persistance.entity.tasks;

import java.util.List;

import com.taskapi.task.infraestructure.persistance.entity.TeamTable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity  
@Table(name = "tasks")  
@Builder @Getter  
public class TaskTable {  
  
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;  
  
    @Column(nullable = false)  
    private String nombre;  
  
    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "estado_id")  
    private TaskStatusTable estado;  
  
    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "equipo_id")  
    private TeamTable equipo;  
  
    private String descripcion;  
  
    // ← ID externo, módulo user  
    @Column(name = "creator_id", nullable = false)  
    private Long creatorId;  
  
    // @Enumerated(EnumType.STRING)  
    // private Prioridad prioridad;  
  
    @OneToMany(mappedBy = "tarea", cascade = CascadeType.ALL)  
    private List<UsersXTaskTable> asignados;  
}