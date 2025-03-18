import de.wps.ddd_schulung.wochenplan.model.Vorstellung;
import de.wps.ddd_schulung.wochenplan.model.VorstellungRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vorstellungen")
@CrossOrigin("*")
public class VorstellungController {
    private final VorstellungRepository vorstellungRepository;

    public VorstellungController(VorstellungRepository vorstellungRepository) {
        this.vorstellungRepository = vorstellungRepository;
    }

    @GetMapping
    public List<Vorstellung> getAllVorstellungen(){
        return vorstellungRepository.findAll();
    }

    @PostMapping
    public Vorstellung createVorstellung(@RequestBody Vorstellung vorstellung){
        return vorstellungRepository.save(vorstellung);
    }

}
