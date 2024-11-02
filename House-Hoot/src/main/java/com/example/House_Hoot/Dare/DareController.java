package com.example.House_Hoot.Dare;

import com.example.House_Hoot.Common.MakerChecker.MakerRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController                                                     //Add REST Support
@CrossOrigin
@RequestMapping("/api/v1/dare")
public class DareController {
    private static final Logger logger = LoggerFactory.getLogger(Dare.class);
    @GetMapping("/view/{id}")
    public ResponseEntity<Optional<Dare>> getById(@PathVariable String id) throws Exception{
        logger.info("************* Inside Get Dare by Id Controller ****************");
        Optional<Dare> dare = Optional.of(new Dare());
        try {
            dare = Optional.ofNullable(new DareDataAccess().getById(id).orElseThrow(() -> new Exception("Dare not found with id " + id)));
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
    @PostMapping("/create/dare")
    public ResponseEntity<MakerRequest<Optional<Dare>>> createDareRequest(@RequestBody Dare request)throws Exception {
        logger.info("************* Inside Create Dare Controller ****************");

        ResponseEntity<MakerRequest<Optional<Dare>>> responseEntity;
        try {
            responseEntity = new DareBV().validateCreateRequest(request);
            if (responseEntity.hasBody()) throw new Exception("Already Present");
            responseEntity = new DareHelper().creteRequest(request);

        } catch (Exception e) {
            throw new Exception(e);
        }
        return ResponseEntity.ok(responseEntity.getBody());

    }

    @GetMapping("/approve/dare/{id}")
    public ResponseEntity<Optional<Dare>> approveDareRequest(@PathVariable String id)throws Exception {
        logger.info("************* Inside Approve Dare Controller ****************");

        ResponseEntity<Optional<Dare>> responseEntity;

        try {
            MakerRequest<Optional<Dare>> makerRequest = new DareBV().validateApproveRequest(id);

            responseEntity = new DareHelper().approveMakerRequest(makerRequest);

        } catch (Exception e) {
            throw new Exception(e);
        }
        return ResponseEntity.ok(responseEntity.getBody());

    }
}
