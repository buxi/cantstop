package de.vt.cantstop.utils;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatDtdDataSet;

public class DatabaseExportSample
{
    public static void main(String[] args) throws Exception
    {
        // database connection
        @SuppressWarnings({ "rawtypes", "unused" })
		Class driverClass = Class.forName("org.hsqldb.jdbcDriver");
        Connection jdbcConnection = DriverManager.getConnection("jdbc:hsqldb:http://localhost:9080/CantStop/hsqldb;create=true", "sa", "");
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

        // write DTD file
        FlatDtdDataSet.write(connection.createDataSet(), new FileOutputStream("test.dtd"));
        
    }
}