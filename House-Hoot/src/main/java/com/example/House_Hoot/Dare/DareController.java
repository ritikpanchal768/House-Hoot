package com.example.House_Hoot.Dare;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController                                                     //Add REST Support
@RequestMapping("/api/v1/dare")
public class DareController {
    private static final Logger logger = LoggerFactory.getLogger(Dare.class);
    @GetMapping("/view/{id}")
    public ResponseEntity<Optional<Dare>> getById(@PathVariable String id) throws Exception{
        logger.info("************* Inside Get Dare by Id Controller ****************");
        Optional<Dare> dare = Optional.of(new Dare());
        try {
            dare = Optional.ofNullable(new DareDataAccess().getById(id).orElseThrow(() -> new Exception("Truth not found with id " + id)));
        } catch (Exception e) {
            throw new Exception(e);
        }
        return ResponseEntity.ok(dare);
    }

    @GetMapping("/view/random")
    public ResponseEntity<Optional<Dare>> getAtRandom() throws Exception{
        logger.info("************* Inside Get Dare At Random Controller ****************");
        Optional<Dare> dare = Optional.of(new Dare());
        try {
            dare = Optional.ofNullable(new DareDataAccess().getAtRandom().orElseThrow(() -> new Exception("Dare Table is Empty")));
        } catch (Exception e) {
            throw new Exception(e);
        }
        return ResponseEntity.ok(dare);
    }
}
