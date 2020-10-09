package com.softwarefoundation.fooddeliverycontroleacesso.repository;

import com.softwarefoundation.fooddeliverycontroleacesso.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Login,Long> {
}
