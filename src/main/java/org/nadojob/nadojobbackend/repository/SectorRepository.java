package org.nadojob.nadojobbackend.repository;

import org.nadojob.nadojobbackend.entity.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SectorRepository extends JpaRepository<Sector, UUID> {

    List<Sector> findByNameIn(List<String> names);

    Boolean existsByName(String name);

}
