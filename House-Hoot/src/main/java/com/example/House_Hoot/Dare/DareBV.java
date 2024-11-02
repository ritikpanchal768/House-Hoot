package com.example.House_Hoot.Dare;

import com.example.House_Hoot.Common.Enums.AllEnum;
import com.example.House_Hoot.Common.MakerChecker.MakerCheckerDataAccess;
import com.example.House_Hoot.Common.MakerChecker.MakerRequest;
import com.example.House_Hoot.Dare.Dare;
import com.example.House_Hoot.Dare.DareDataAccess;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class DareBV {
    public ResponseEntity<MakerRequest<Optional<Dare>>> validateCreateRequest(Dare request)throws Exception{
        MakerRequest<Optional<Dare>> makerRequest = new MakerRequest<>();
        Optional<Dare> Dare = new DareDataAccess().getByDareMessage(request.getDare());
        if(!ObjectUtils.isEmpty(Dare)){
            makerRequest.setRequest(Dare);
            return ResponseEntity.badRequest()
                    .body(makerRequest);
        }


        List<MakerRequest> makerRequestList = new MakerCheckerDataAccess().getByTag("Dare");
        if (!ObjectUtils.isEmpty(makerRequestList)) {
            for (MakerRequest maker : makerRequestList) {
                Dare DareRequest = getDareRequest(maker);

                if (DareRequest.getDare().equals(request.getDare())
                        && maker.getTag().equals(AllEnum.Tag.Dare.name())
                        && maker.getApprovalStatus().equals(AllEnum.Status.PENDING.name())) {
                    throw new Exception("Request already present with this Dare. Please wait until acceptance or rejection of the request.");
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public MakerRequest<Optional<Dare>> validateApproveRequest(String id)throws Exception{
        MakerRequest<Optional<Dare>> makerRequest = new MakerCheckerDataAccess().getById(id);
        if(ObjectUtils.isEmpty(makerRequest)){
            throw new Exception("Not Found with id: " + id);
        }
        if(!makerRequest.getTag().equals(AllEnum.Tag.Dare.name())){
            throw new Exception("Request Tag in INVALID :" + makerRequest.getTag());
        }
        if(!makerRequest.getApprovalStatus().equals(AllEnum.Status.PENDING.name())){
            throw new Exception("Request is already " + makerRequest.getTag());
        }
        Dare DareRequest = getDareRequest(makerRequest);
        Optional<Dare> Dare = new DareDataAccess().getByDareMessage(DareRequest.getDare());
        if(!ObjectUtils.isEmpty(Dare)){
            throw new Exception("Already Present: " + id);
        }
        return makerRequest;
    }

    private static final ObjectMapper mapper = new ObjectMapper();

    public Dare getDareRequest(MakerRequest makerRequest) throws Exception {
        Object request = makerRequest.getRequest();

        // Debugging: Log the type of request to understand its structure
        System.out.println("Request type: " + request.getClass().getName());

        // Check if request is already a Dare object
        if (request instanceof Dare) {
            return (Dare) request;
        }

        // If request is a JSON string, deserialize it
        if (request instanceof String) {
            try {
                return mapper.readValue((String) request, Dare.class);
            } catch (Exception e) {
                throw new Exception("Error deserializing request to Dare", e);
            }
        }
        // If request is a HashMap, convert to JSON and then to Dare
        if (request instanceof HashMap) {
            String json = mapper.writeValueAsString(request); // Convert HashMap to JSON
            return mapper.readValue(json, Dare.class);       // Deserialize JSON to Dare
        }

        // If neither Dare nor String, throw exception with details
        throw new ClassCastException("Cannot cast request to Dare. Actual type: " + request.getClass().getName());
    }
}
