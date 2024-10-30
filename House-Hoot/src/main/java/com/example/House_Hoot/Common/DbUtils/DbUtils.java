package com.example.House_Hoot.Common.DbUtils;



import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.*;


public class DbUtils {

    public <T> T returnedAsObject(String query, Class<T> clazz, Object... parameters) throws Exception {
        T result = null;
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/househood", "root", "ritik768")) {

            // Use PreparedStatement to prevent SQL injection
            PreparedStatement preparedStatement = con.prepareStatement(query);

            // Set parameters (if any) to the PreparedStatement
            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    Object object = parameters[i];
                    if(object.getClass().getSimpleName().equals("String")){
                        preparedStatement.setString(i + 1, (String) parameters[i]);
                    }
                    else if(object.getClass().getSimpleName().equals("Integer")){
                        preparedStatement.setInt(i + 1, (Integer) parameters[i]);
                    }
                    else if(object.getClass().getSimpleName().equals("BigDecimal")){
                        preparedStatement.setBigDecimal(i + 1, (BigDecimal) parameters[i]);
                    }
                    else{
                        preparedStatement.setObject(i + 1, parameters[i]);
                    }

                }
            }

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();


            // Assuming we are retrieving only one object from the query
            if (resultSet.next()) {
                result = clazz.getDeclaredConstructor().newInstance(); // Create an instance of T

                // Iterate over the fields of class T and populate them with result set data
                for (Field field : clazz.getDeclaredFields()) {
                    field.setAccessible(true); // Allow access to private fields

                    Object value = resultSet.getObject(field.getName()); // Get the column value using field name
                    field.set(result, value); // Set the value to the corresponding field
                }
            }

            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return result;
    }
}
