package ro.mpp.concertpro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.mpp.concertpro.model.dto.AddSpectacleDto;
import ro.mpp.concertpro.model.dto.SpectacleDto;
import ro.mpp.concertpro.model.mapper.SpectacleMapper;
import ro.mpp.concertpro.repository.SpectacleRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpectacleService {

    private final SpectacleRepository spectacleRepository;

    public List<SpectacleDto> getAllSpectacles() {
        return this.spectacleRepository.findAll().stream()
            .map(SpectacleMapper::mapToDto)
            .toList();
    }

    public SpectacleDto addSpectacle(AddSpectacleDto spectacleDto) {
        return Optional.of(spectacleDto)
            .map(SpectacleMapper::mapFromAddDtoToModel)
            .map(this.spectacleRepository::save)
            .map(SpectacleMapper::mapToDto)
            .orElseThrow();
    }

    public SpectacleDto updateSpectacle(SpectacleDto spectacleDto) {
        return this.spectacleRepository.findById(spectacleDto.spectacleId())
            .map(spectacle -> this.spectacleRepository.save(SpectacleMapper.mapToModel(spectacleDto)))
            .map(SpectacleMapper::mapToDto)
            .orElseThrow();
    }

    public SpectacleDto getSpectacleById(Long spectacleId) {
        return this.spectacleRepository.findById(spectacleId)
            .map(SpectacleMapper::mapToDto)
            .orElseThrow();
    }

    public void deleteSpectacle(Long spectacleId) {
        this.spectacleRepository.findById(spectacleId)
            .ifPresent(this.spectacleRepository::delete);
    }

}
