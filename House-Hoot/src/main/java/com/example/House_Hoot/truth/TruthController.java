package com.example.House_Hoot.truth;

import com.example.House_Hoot.Common.MakerChecker.MakerRequest;
import com.example.House_Hoot.Dare.Dare;
import com.example.House_Hoot.Dare.DareBV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController                                                     //Add REST Support
@CrossOrigin
@RequestMapping("/api/v1/truth")
public class TruthController {

    private static final Logger logger = LoggerFactory.getLogger(Truth.class);

    @PostMapping("/create/truth")
    public ResponseEntity<MakerRequest<Optional<Truth>>> createTruthRequest(@RequestBody Truth request)throws Exception {
        logger.info("************* Inside Create Truth Controller ****************");

        ResponseEntity<MakerRequest<Optional<Truth>>> responseEntity;
        try {
            responseEntity = new TruthBV().validateCreateRequest(request);
            if (responseEntity.hasBody()) throw new Exception("Already Present");
            responseEntity = new TruthHelper().creteRequest(request);

        } catch (Exception e) {
            throw new Exception(e);
        }
        return ResponseEntity.ok(responseEntity.getBody());

    }

    @GetMapping("/approve/truth/{id}")
    public ResponseEntity<Optional<Truth>> approveTruthRequest(@PathVariable String id)throws Exception {
        logger.info("************* Inside Approve Truth Controller ****************");

        ResponseEntity<Optional<Truth>> responseEntity;

        try {
            MakerRequest<Optional<Truth>> makerRequest = new TruthBV().validateApproveRequest(id);

            responseEntity = new TruthHelper().approveMakerRequest(makerRequest);

        } catch (Exception e) {
            throw new Exception(e);
        }
        return ResponseEntity.ok(responseEntity.getBody());

    }

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
