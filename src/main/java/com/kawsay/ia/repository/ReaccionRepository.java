package com.kawsay.ia.repository;
import com.kawsay.ia.entity.Reaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ReaccionRepository extends JpaRepository<Reaccion, Integer> {

    List<Reaccion> findByPublicacionId(Integer idPublicacion);


    @Query("""
    SELECT r.tipo, COUNT(r)
    FROM Reaccion r
    WHERE r.comentario.id = :comentarioId
    GROUP BY r.tipo
""")
    List<Object[]> contarReaccionesPorTipoComentario(@Param("comentarioId") Integer comentarioId);

}
