package com.hazmelaucb.ms_chat.dao;

import java.util.List;


import com.hazmelaucb.ms_chat.entidy.Conversacion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface ConversacionRepository extends JpaRepository<Conversacion, Long> {

    //  Buscar conversación en ambas direcciones (user1Id <-> user2Id)
    @Query("SELECT c FROM Conversacion c WHERE (c.user1Id = :user1 AND c.user2Id = :user2) OR (c.user1Id = :user2 AND c.user2Id = :user1)")
    List<Conversacion> findByUsers(@Param("user1") Long user1Id, @Param("user2") Long user2Id);

    //  Buscar todas las conversaciones de un usuario (sin errores de nombres de parámetros)
    List<Conversacion> findByUser1IdOrUser2Id(Long user1Id, Long user2Id);

    List<Conversacion> findByUser1IdAndUser2Id(Long user1Id, Long user2Id);
}
