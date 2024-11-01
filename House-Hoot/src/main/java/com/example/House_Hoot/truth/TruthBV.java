package com.example.House_Hoot.truth;

import com.example.House_Hoot.Common.Enums.AllEnum;
import com.example.House_Hoot.Common.MakerChecker.MakerCheckerDataAccess;
import com.example.House_Hoot.Common.MakerChecker.MakerRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class TruthBV {
    public ResponseEntity<MakerRequest<Optional<Truth>>> validateCreateRequest(Truth request)throws Exception{
        MakerRequest<Optional<Truth>> makerRequest = new MakerRequest<>();
        Optional<Truth> truth = new TruthDataAccess().getByTruthMessage(request.getTruth());
        if(!ObjectUtils.isEmpty(truth)){
            makerRequest.setRequest(truth);
            return ResponseEntity.badRequest()
                    .body(makerRequest);
        }


        List<MakerRequest> makerRequestList = new MakerCheckerDataAccess().getByTag("truth");
        if (!ObjectUtils.isEmpty(makerRequestList)) {
            for (MakerRequest maker : makerRequestList) {
                Truth truthRequest = getTruthRequest(maker);

                if (truthRequest.getTruth().equals(request.getTruth())
                        && maker.getTag().equals(AllEnum.Tag.Truth.name())
                        && maker.getApprovalStatus().equals(AllEnum.Status.PENDING.name())) {
                    throw new Exception("Request already present with this truth. Please wait until acceptance or rejection of the request.");
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public MakerRequest<Optional<Truth>> validateApproveRequest(String id)throws Exception{
        MakerRequest<Optional<Truth>> makerRequest = new MakerCheckerDataAccess().getById(id);
        if(ObjectUtils.isEmpty(makerRequest)){
            throw new Exception("Not Found with id: " + id);
        }
        if(!makerRequest.getTag().equals(AllEnum.Tag.Truth.name())){
            throw new Exception("Request Tag in INVALID :" + makerRequest.getTag());
        }
        if(!makerRequest.getApprovalStatus().equals(AllEnum.Status.PENDING.name())){
            throw new Exception("Request is already " + makerRequest.getTag());
        }
        Truth truthRequest = getTruthRequest(makerRequest);
        Optional<Truth> truth = new TruthDataAccess().getByTruthMessage(truthRequest.getTruth());
        if(!ObjectUtils.isEmpty(truth)){
            throw new Exception("Already Present: " + id);
        }
        return makerRequest;
    }

    private static final ObjectMapper mapper = new ObjectMapper();

    public Truth getTruthRequest(MakerRequest makerRequest) throws Exception {
        Object request = makerRequest.getRequest();

        // Debugging: Log the type of request to understand its structure
        System.out.println("Request type: " + request.getClass().getName());

        // Check if request is already a Truth object
        if (request instanceof Truth) {
            return (Truth) request;
        }

        // If request is a JSON string, deserialize it
        if (request instanceof String) {
            try {
                return mapper.readValue((String) request, Truth.class);
            } catch (Exception e) {
                throw new Exception("Error deserializing request to Truth", e);
            }
        }
        // If request is a HashMap, convert to JSON and then to Truth
        if (request instanceof HashMap) {
            String json = mapper.writeValueAsString(request); // Convert HashMap to JSON
            return mapper.readValue(json, Truth.class);       // Deserialize JSON to Truth
        }

        // If neither Truth nor String, throw exception with details
        throw new ClassCastException("Cannot cast request to Truth. Actual type: " + request.getClass().getName());
    }

}
