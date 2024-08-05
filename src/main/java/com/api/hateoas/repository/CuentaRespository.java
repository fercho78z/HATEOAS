package com.api.hateoas.repository;

//import org.apache.ibatis.annotations.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.api.hateoas.model.Cuentas;
import java.util.List;


@Repository
public interface CuentaRespository extends JpaRepository<Cuentas, Integer>{

	// @Update("UPDATE departamento SET nombre= #{nombre}, ubicacion= #{ubicacion} WHERE id=#{id}")
	// @Query("UPDATE Cuentas c SET c.balance=c.balance + ?1 WHERE c.id=?2")

	@Query("UPDATE Cuentas SET balance= balance+?1 WHERE id=?2")
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	void actualizarBalance(float balance,Integer id);
	
	
}
