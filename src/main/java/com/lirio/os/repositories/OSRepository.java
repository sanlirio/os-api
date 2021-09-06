package com.lirio.os.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lirio.os.domain.OS;

@Repository
public interface OSRepository extends JpaRepository<OS, Long>{

}
