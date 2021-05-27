
package crawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
/**
 *
 * @author Rsa.co
 */
public class FileCrawl {
     public String title;
    public String body;
    public String URL ;
    public static int fileNum ;
    public static ArrayList<Document> arrdoc = new ArrayList<Document>();
            
    public void FileCrawl1(String t , String b ,String u){
        title = t ;
        body = b ;
        URL= u ;
        fileNum++;

        try{
                        Document doc = new Document();
			doc.add(new TextField("Title", title , Field.Store.YES));
			doc.add(new TextField("Body", body, Field.Store.YES));
                        doc.add(new TextField("URL", URL , Field.Store.YES));
                        
                        File f = new File("documents\\file"+fileNum +".txt");
                        if (!f.exists()) f.createNewFile();
                        FileWriter fw = new FileWriter(f.getPath(),true);
                        BufferedWriter  bw = new BufferedWriter (fw);
			bw.write(doc.get("Title").toString());
                        bw.write(System.getProperty("line.separator"));
                        bw.write(doc.get("Body").toString());
			bw.close();
                        
                        arrdoc.add(doc);
                      /*  Directory dir = FSDirectory.open(Paths.get("doc2"));
			Analyzer analyzer = new PersianAnalyzer();
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
			IndexWriter iwriter = new IndexWriter(dir, iwc);
                        
                        iwriter.addDocument(doc);*/                   
            }
            catch (IOException e) {
                System.out.println(" caught a " + e.getClass()+ "\n with message: " + e.getMessage());
            }
    }
    
    
    public ArrayList<Document> getarrdoc(){
        System.out.println("arrdoc dakheli"+arrdoc.size());
    return arrdoc ;
    }
    
    
}
