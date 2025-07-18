package org.nadojob.nadojobbackend.service;

import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.entity.Sector;
import org.nadojob.nadojobbackend.exception.SectorNameAlreadyExistsException;
import org.nadojob.nadojobbackend.exception.SectorNotFoundException;
import org.nadojob.nadojobbackend.repository.SectorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SectorService {

    private final SectorRepository sectorRepository;

    public void create(String sectorName) {
        if (sectorRepository.existsByName(sectorName)) {
            throw new SectorNameAlreadyExistsException("Сектор с таким назвнием уже существует");
        }
        sectorRepository.save(createNew(sectorName));
    }

    public void createAll(List<String> sectorNames) {
        List<Sector> sectors = sectorNames.stream()
                .filter(name -> !sectorRepository.existsByName(name))
                .map(this::createNew)
                .toList();

        if (!sectors.isEmpty()) {
            sectorRepository.saveAll(sectors);
        }
    }

    public void deleteById(UUID id) {
        if (!sectorRepository.existsById(id)) {
            throw new SectorNotFoundException("Сектор не найден");
        }
    }

    private Sector createNew(String name) {
        Sector sector = new Sector();
        sector.setName(name);
        return sector;
    }

}
