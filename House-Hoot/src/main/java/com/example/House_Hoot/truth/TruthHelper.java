package com.example.House_Hoot.truth;

import com.example.House_Hoot.Common.DbUtils.DbUtils;
import com.example.House_Hoot.Common.Enums.AllEnum;
import com.example.House_Hoot.Common.MakerChecker.MakerCheckerDataAccess;
import com.example.House_Hoot.Common.MakerChecker.MakerRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

public class TruthHelper {
    private final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(TruthHelper.class);
    public ResponseEntity<MakerRequest<Optional<Truth>>> creteRequest(Truth request)throws Exception{

        MakerRequest<Optional<Truth>> makerRequest = new MakerRequest<>();
        makerRequest.setId(UUID.randomUUID().toString());
        makerRequest.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        makerRequest.setRequest(request);
        makerRequest.setApprovalStatus(AllEnum.Status.PENDING.name());
        makerRequest.setModule(AllEnum.Module.Truth.name());
        makerRequest.setCreatedBy("SYSTEM");
        makerRequest.setTag(AllEnum.Tag.Truth.name());

        new MakerCheckerDataAccess().insertMaker(makerRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(makerRequest);
    }

    public ResponseEntity<Optional<Truth>> approveMakerRequest(MakerRequest makerRequest)throws Exception{

        Truth truth = mapper.readValue( (String)makerRequest.getRequest(), Truth.class);
        truth.setId(UUID.randomUUID().toString());
        truth.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        makerRequest.setApprovalStatus(AllEnum.Status.APPROVED.name());
        makerRequest.setModifiedOn(new Timestamp(System.currentTimeMillis()));
        makerRequest.setModifiedBy("SYSTEM");


        new MakerCheckerDataAccess().approveMaker(makerRequest, makerRequest.getId());
        new TruthDataAccess().insertNewTruth(truth);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Optional.ofNullable(truth));
    }
}
