package com.api.hateoas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.api.hateoas.model.Cuentas;

@Repository
public interface CuentaRespository extends JpaRepository<Cuentas, Integer>{
/*
	@Query("UPDATE Cuenta c SET c.balance=c.balance+?1 WHERE c.id=?2")
	@Modifying
	void actualizarBalance(float balance,Integer id);
	*/
}
