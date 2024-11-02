package com.example.House_Hoot.Dare;

import com.example.House_Hoot.Common.Enums.AllEnum;
import com.example.House_Hoot.Common.MakerChecker.MakerCheckerDataAccess;
import com.example.House_Hoot.Common.MakerChecker.MakerRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

public class DareHelper {
    private final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(DareHelper.class);
    public ResponseEntity<MakerRequest<Optional<Dare>>> creteRequest(Dare request)throws Exception{

        MakerRequest<Optional<Dare>> makerRequest = new MakerRequest<>();
        makerRequest.setId(UUID.randomUUID().toString());
        makerRequest.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        makerRequest.setRequest(request);
        makerRequest.setApprovalStatus(AllEnum.Status.PENDING.name());
        makerRequest.setModule(AllEnum.Module.Dare.name());
        makerRequest.setCreatedBy("SYSTEM");
        makerRequest.setTag(AllEnum.Tag.Dare.name());

        new MakerCheckerDataAccess().insertMaker(makerRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(makerRequest);
    }

    public ResponseEntity<Optional<Dare>> approveMakerRequest(MakerRequest makerRequest)throws Exception{

        Dare dare = mapper.readValue( (String)makerRequest.getRequest(), Dare.class);
        dare.setId(UUID.randomUUID().toString());
        dare.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        makerRequest.setApprovalStatus(AllEnum.Status.APPROVED.name());
        makerRequest.setModifiedOn(new Timestamp(System.currentTimeMillis()));
        makerRequest.setModifiedBy("SYSTEM");


        new MakerCheckerDataAccess().approveMaker(makerRequest, makerRequest.getId());
        new DareDataAccess().insertNewDare(dare);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Optional.ofNullable(dare));
    }
}
