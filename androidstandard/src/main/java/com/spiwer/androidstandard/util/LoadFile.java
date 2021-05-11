package com.spiwer.androidstandard.util;


import com.spiwer.androidstandard.exception.AppException;
import com.spiwer.androidstandard.lasting.EMessageStandard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author spiwer.com - Herman Leonardo Rey Baquero - leoreyb@gmail.com
 */
public class LoadFile {
    private final Map<String, String> map = new HashMap<String, String>();
    private StringBuilder lines = new StringBuilder();
    private String queryName = null;

    private static final LoadFile INSTANCE = new LoadFile();
    private final static Logger LOG = Logger.getLogger(LoadFile.class.getName());

    private LoadFile() {
    }

    public static LoadFile getInstance() {
        return INSTANCE;
    }

    public Map<String, String> load(InputStream input)
            throws AppException {
        InputStreamReader streamReader = new InputStreamReader(input);
        BufferedReader buffered = null;
        try {
            buffered = new BufferedReader(streamReader);
            String line = buffered.readLine();
            while (line != null) {
                line = line.trim();
                if (!line.startsWith("#") && !line.isEmpty()) {
                    processLine(line);
                }
                line = buffered.readLine();
            }
            processStament();
            return map;
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new AppException(EMessageStandard.ERROR_READ_FILE);
        } finally {
            ResourceUtil.close(buffered);
        }
    }

    public Map<String, String> load(String fileName)
            throws AppException {
        InputStream input = LoadFile.class.getResourceAsStream(fileName + ".god");
        return load(input);
    }

    private void processLine(String line) {
        int index = line.indexOf("=>");
        int indexSquareKeys = line.indexOf("[", index);
        if (index != -1 && indexSquareKeys != -1) {
            line = processWildcard(line, indexSquareKeys, index);
        }
        this.lines.append(line).append("\n");
    }

    private String processWildcard(String line, int indexSquareKeys, int index) {
        String wildcard = line.substring(index, ++indexSquareKeys);
        wildcard = wildcard.replaceAll(" ", "");
        if (wildcard.equals("=>[")) {
            processStament();
            queryName = line.substring(0, index).trim();
            line = line.substring(indexSquareKeys, line.length());
        }
        return line;
    }

    private void processStament() {
        String sqlWitoutSpace = lines.toString().trim();
        if (lines.length() == 0 || sqlWitoutSpace.isEmpty()) {
            return;
        }
        sqlWitoutSpace = sqlWitoutSpace.substring(0, sqlWitoutSpace.length() - 1);
        map.put(queryName, sqlWitoutSpace);
        lines = new StringBuilder();
    }

}
