package com.example.House_Hoot.Common.MakerChecker;

import com.example.House_Hoot.Common.DbUtils.DbUtils;
import com.example.House_Hoot.truth.Truth;
import com.example.House_Hoot.truth.TruthDataAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MakerCheckerDataAccess {
    private static final Logger logger = LoggerFactory.getLogger(MakerCheckerDataAccess.class);
    public void insertMaker(MakerRequest maker)throws Exception{
        logger.info("************* Inside Insert MakerRequest ****************");
        new DbUtils().saveObject(maker,"makerrequest");
    }
    public void approveMaker(MakerRequest maker,String id)throws Exception{
        logger.info("************* Inside Approve MakerRequest by Id DataAccess****************");
        new DbUtils().updateObject(maker,"makerrequest",id);
    }
    public MakerRequest getById(String id) throws Exception {
        logger.info("************* Inside Get MakerRequest by Id DataAccess****************");
        String query = "Select * From makerrequest where id = ?";
        return new DbUtils().returnedAsObject(query, MakerRequest.class, id);
    }
    public List<MakerRequest> getByTag(String tag)throws Exception{
        String query = "Select * from makerrequest where tag = ?";
        return new DbUtils().returnedAsList(query, MakerRequest.class, tag);
    }
}
