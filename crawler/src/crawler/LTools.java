/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.fa.PersianAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;
import static sun.rmi.transport.TransportConstants.Version;

/**
 *
 * @author Amirreza
 */
public class LTools {
    
     public static ArrayList<File> findFiles(File rootFile, ArrayList<File> files) {  //creates arry of all docs
        File[] listFiles = rootFile.listFiles(); // return childrens of root
        for (File file : listFiles) {
            if (file.isFile() && file.getName().endsWith(".txt") && !file.getName().startsWith("._")) { //hidden and invalid files start with ._
                files.add(file);
            } else if (file.isDirectory()) { // check if it's a folder
                findFiles(file, files);
            }
        }
        return files;
    }

    public static String readFile(File file) {  //returns the body of each file
        try {
            BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedreader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append('\n');
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String[] readFileForIndex(File file) {  //gets one file and returns 2 string : 1st: title & 2nd : body of file
        String title = "";
        String body = "";
        boolean flag = true;  //for reading first line : title
        try {
            BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedreader.readLine()) != null) {
                if (flag) {
                    title = line;
                    flag = false;
                } else {
                    stringBuilder.append(line);
                    stringBuilder.append('\n');
                }
            }
            body = stringBuilder.toString();
            return new String[]{title, body};
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{"", ""};  //2string in form of string array (size = 2) 
        }
    }

    public static Set<String> findStopWords( String fileText, Set<String> commonTokens) { // returns a hash set of stop words
        Analyzer analyzer = new PersianAnalyzer();
        Set<String> fileTextTokens = new HashSet<>();
        try {
            TokenStream tokenStream = analyzer.tokenStream(null, new StringReader(fileText));
            tokenStream.reset(); // read tokens from first
            while (tokenStream.incrementToken()) {
                fileTextTokens.add(tokenStream.getAttribute(CharTermAttribute.class).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (commonTokens.size() == 0) {
            return fileTextTokens;
        } else {
            commonTokens.retainAll(fileTextTokens);// find common tokens
            return commonTokens;
        }
    }

    
}
