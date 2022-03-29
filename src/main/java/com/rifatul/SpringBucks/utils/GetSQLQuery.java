package com.rifatul.SpringBucks.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class GetSQLQuery {

    private static final String end = ".properties";
    private static final String files_directory = "queries/";
    private static Properties props;
    private static final String fileName = "queries";

    public static Properties getPropsFile() throws SQLException {

        if (props != null) {
            return props;
        }

        String propFileName = files_directory + fileName + end;

        InputStream is = GetErrorMessage.class.getResourceAsStream("/" + propFileName);

        if (is == null){
            throw new SQLException("Unable to load property file: " + propFileName);
        }

        props = new Properties();
        try {
            props.load(is);
        } catch (IOException e) {
            throw new SQLException("Unable to load property file: " + propFileName + "\n" + e.getMessage());
        }

        return props;
    }

    public static String getSQLQuery(String query) {
        String res = "";
        try {
            res = getPropsFile().getProperty(query);
        } catch (SQLException e) {

        }
        return res;
    }

}
