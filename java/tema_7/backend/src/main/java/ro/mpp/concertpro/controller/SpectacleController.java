package ro.mpp.concertpro.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.mpp.concertpro.model.dto.AddSpectacleDto;
import ro.mpp.concertpro.model.dto.SpectacleDto;
import ro.mpp.concertpro.service.SpectacleService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin(origins ="*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/spectacles")
@RequiredArgsConstructor
public class SpectacleController {

    private final SpectacleService spectacleService;

    @GetMapping
    public ResponseEntity<List<SpectacleDto>> getAllSpectacles() {
        return ResponseEntity.ok(this.spectacleService.getAllSpectacles());
    }

    @GetMapping("/{spectacleId}")
    public ResponseEntity<SpectacleDto> getSpectacleById(@PathVariable Long spectacleId) {
        return ResponseEntity.ok(this.spectacleService.getSpectacleById(spectacleId));
    }

    @PostMapping
    public ResponseEntity<SpectacleDto> addSpectacle(@Valid @RequestBody AddSpectacleDto spectacleDto) throws URISyntaxException {
        SpectacleDto spectacle = this.spectacleService.addSpectacle(spectacleDto);
        return ResponseEntity.created(new URI("/api/spectacles" + spectacle.spectacleId())).body(spectacle);
    }

    @PutMapping
    public ResponseEntity<SpectacleDto> updateSpectacle(@Valid @RequestBody SpectacleDto spectacleDto) {
        return ResponseEntity.ok(this.spectacleService.updateSpectacle(spectacleDto));
    }

    @DeleteMapping("/{spectacleId}")
    public ResponseEntity<Void> deleteSpectacle(@PathVariable Long spectacleId) {
        this.spectacleService.deleteSpectacle(spectacleId);
        return ResponseEntity.noContent().build();
    }

}
