package com.example.House_Hoot.truth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController                                                     //Add REST Support
@RequestMapping("/api/v1/truth")
public class TruthController {

    private static final Logger logger = LoggerFactory.getLogger(Truth.class);

    @GetMapping("/view/{id}")
    public ResponseEntity<Optional<Truth>> getById(@PathVariable String id) throws Exception{
        logger.info("************* Inside Get Truth by Id Controller ****************");
        Optional<Truth> truth = Optional.of(new Truth());
        try {
            truth = Optional.ofNullable(new TruthDataAccess().getById(id).orElseThrow(() -> new Exception("Truth not found with id " + id)));
        } catch (Exception e) {
            throw new Exception(e);
        }
        return ResponseEntity.ok(truth);
    }

    @GetMapping("/view/random")
    public ResponseEntity<Optional<Truth>> getAtRandom() throws Exception{
        logger.info("************* Inside Get Truth At Random Controller ****************");
        Optional<Truth> truth = Optional.of(new Truth());
        try {
            truth = Optional.ofNullable(new TruthDataAccess().getAtRandom().orElseThrow(() -> new Exception("Truth Table is Empty")));
        } catch (Exception e) {
            throw new Exception(e);
        }
        return ResponseEntity.ok(truth);
    }
}
