package com.example.House_Hoot.truth;

import com.example.House_Hoot.Common.DbUtils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLSyntaxErrorException;
import java.util.Optional;

public class TruthDataAccess {
    private static final Logger logger = LoggerFactory.getLogger(TruthDataAccess.class);
    public Optional<Truth> getByTruthMessage(String truthMessage) throws Exception {
        logger.info("************* Inside Get Truth by Truth Message DataAccess****************");
        String query = "Select * From truth where truth = ?";
        return Optional.ofNullable(new DbUtils().returnedAsObject(query, Truth.class, truthMessage));
    }
    public Optional<Truth> getById(String id) throws Exception {
        logger.info("************* Inside Get Truth by Id DataAccess****************");
        String query = "Select * From truth where id = ?";
        return Optional.ofNullable(new DbUtils().returnedAsObject(query, Truth.class, id));
    }
    public Optional<Truth> getAtRandom() throws Exception {
        logger.info("************* Inside Get Truth At Random DataAccess****************");
        String query = "SELECT * FROM truth ORDER BY RAND() LIMIT 1";
        return Optional.ofNullable(new DbUtils().returnedAsObject(query, Truth.class));
    }
    public void insertNewTruth(Truth truth) throws Exception {
        logger.info("************* Inside Insert Truth DataAccess****************");

        new DbUtils().saveObject(truth,"truth");
    }
}
