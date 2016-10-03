package com.qa.demo.utils.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.CharEncoding;
import org.testng.annotations.DataProvider;

import com.qa.demo.utils.reporting.Log;

import au.com.bytecode.opencsv.CSVReader;

/**
 * This Class provides the utilities for TESTNG to create data provider and other features
 * 
 * @author Deenesh
 */
public class TestNGUtilities {

    private TestNGUtilities() {
        // no-op
    }

    /**
     * @param m
     * @return
     * @throws TestNGUtilityException
     * @throws IOException
     */
    @DataProvider(name = "csvData")
    public static Object[][] getCSVData(Method m) throws IOException {
        String fileName = "./src/test/java/data" + System.getProperty("file.separator") + m.getName() + ".csv";
        return parseCsvToMap(fileName, 0, 0);
    }

    /**
     * @param m
     * @return
     * @throws IOException
     */
    @DataProvider(name = "csvAnnotatedData")
    public static Object[][] getCSVAnnotatedData(Method m) throws IOException {
        String fileName = "";
        int startRow = 0;
        int endRow = 0;
        Data dataAnnotation = m.getAnnotation(Data.class); // initialize data annotation

        if (dataAnnotation == null) {
            fileName = "data" + System.getProperty("file.separator") + m.getName() + ".csv";
        } else if (!dataAnnotation.fileName().isEmpty()) {
            fileName = dataAnnotation.fileName();
            startRow = dataAnnotation.startRow();
            endRow = dataAnnotation.endRow();
            if ((endRow < startRow && endRow != 0) || startRow < 0) {
                throw new TestNGUtilityException("Starting row cannot be less than 0 and not smaller than end row");
            }
        }
        Log.debug("Data driving file - [" + fileName + "], startRow - [" + startRow + "], enddRow - [" + endRow + "]");
        return parseCsvToMap(fileName, startRow, endRow);
    }

    /**
     * @param fileName
     * @param start
     * @param end
     * @param charset
     *            values UTF_8, UTF_16 etc
     * @return
     * @throws IOException
     */
    public static Object[][] parseCsvToMap(String fileName, int start, int end) throws IOException {
        File csvFile = new File(fileName);
        File currentDir = new File(".");

        if (!csvFile.exists()) {
            String errorMessage = "Input CSV File [" + fileName + "] not found in " + currentDir.getCanonicalPath()
                    + "\r\n";
            Log.error(errorMessage);
            throw new TestNGUtilityException(errorMessage);
        }

        if (end < start) {
            throw new TestNGUtilityException("Invalid start and end rows defined to read CSV file " + fileName);
        }

        try (CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(fileName), CharEncoding.UTF_8),
                ',', '"', '\\')) {
            return createObject(reader, fileName, start, end);
        } catch (Exception e) {
            String errorMessage = "Error reading CSV File: " + fileName;
            Log.error(errorMessage, e);
            throw new TestNGUtilityException(errorMessage, e);
        }
    }

    private static Object[][] createObject(CSVReader reader, String fileName, int start, int end) throws IOException {
        // load input file into hasMap
        String[] inputLine = null;
        boolean firstLine = true;
        int rowCount = 0;
        String[] columnNames = null;
        List<HashMap<String, String>> maps = new ArrayList<>();

        try {
            while ((inputLine = reader.readNext()) != null) {
                if (firstLine) {
                    columnNames = inputLine;
                    firstLine = false;
                } else {
                    if ((start == 0 || rowCount >= start) && (end == 0 || rowCount <= end)) {
                        HashMap<String, String> csvMap = new HashMap<>();
                        int counter = 0;
                        for (String column : columnNames) {
                            String value;
                            if (counter == inputLine.length) {
                                value = "";
                            } else {
                                value = inputLine[counter];
                            }
                            csvMap.put(column, value);
                            counter++;
                        }
                        maps.add(csvMap);
                    }
                }
                rowCount++;
            }
        } catch (Exception e) {
            throw new TestNGUtilityException("Unable to parse csv file: " + fileName, e);
        } finally {
            reader.close();
        }

        // Create Object type to return
        Object[][] returnArray = new Object[maps.size()][1];
        for (int i = 0; i < maps.size(); i++) {
            returnArray[i][0] = maps.get(i);
        }
        return returnArray;
    }

}
