package com.example.House_Hoot.Dare;

import com.example.House_Hoot.Common.DbUtils.DbUtils;
import com.example.House_Hoot.Dare.Dare;
import com.example.House_Hoot.Dare.DareDataAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class DareDataAccess {
    private static final Logger logger = LoggerFactory.getLogger(DareDataAccess.class);
    public Optional<Dare> getById(String id) throws Exception {
        logger.info("************* Inside Get Dare by Id DataAccess****************");
        String query = "Select * From dare where id = ?";
        return Optional.ofNullable(new DbUtils().returnedAsObject(query, Dare.class, id));
    }
    public Optional<Dare> getAtRandom() throws Exception {
        logger.info("************* Inside Get Dare At Random DataAccess****************");
        String query = "SELECT * FROM dare ORDER BY RAND() LIMIT 1";
        return Optional.ofNullable(new DbUtils().returnedAsObject(query, Dare.class));
    }
    public Optional<Dare> getByDareMessage(String DareMessage) throws Exception {
        logger.info("************* Inside Get Dare by Dare Message DataAccess****************");
        String query = "Select * From dare where dare = ?";
        return Optional.ofNullable(new DbUtils().returnedAsObject(query, Dare.class, DareMessage));
    }
    public void insertNewDare(Dare Dare) throws Exception {
        logger.info("************* Inside Insert Dare DataAccess****************");

        new DbUtils().saveObject(Dare,"dare");
    }
}
