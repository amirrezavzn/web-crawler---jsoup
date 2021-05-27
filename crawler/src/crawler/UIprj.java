
package crawler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.DocFlavor.URL;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.fa.PersianAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.FSDirectory;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import org.apache.lucene.analysis.CharArraySet;
import static org.apache.lucene.analysis.standard.UAX29URLEmailTokenizer.URL;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;


/**
 *
 * @author Amirreza
 */

public class UIprj extends JFrame{
    
    private JTextField textfield3;
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    private JButton jButton5;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JEditorPane jTextArea1;
    private JTextField jTextField1;
    private JTextField jTextField2;
    
    private String st ;
    private Integer pagenum ;
    public int pagecount ;
    private  ArrayList<String> Output ;
    private  ArrayList<File> Output2 ;
    private static HashSet<String> links;
    
    public UIprj(){
      
        super("Lucene"); 
 
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JEditorPane();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        textfield3 = new JTextField(2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextField1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField1.setText("متن");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, javax.swing.UIManager.getDefaults().getColor("Nb.Desktop.background"), java.awt.Color.lightGray));

        jTextArea1.setEditable(false);
      //  jTextArea1.setContentType("text/html");
           
      /*  File f = new  File("a.txt");
        try {
            jTextArea1.setText("<html><a href="+f.toURI().toURL()+">amir reza</a> <br>");   //             boz
        } catch (MalformedURLException ex) {
            Logger.getLogger(UIprj.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        jTextArea1.addHyperlinkListener(new HyperlinkListener() {

            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
             //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if (e.getEventType()== HyperlinkEvent.EventType.ACTIVATED){
                    try { 
                        String s = e.getURL().getPath().substring(1) ;
                        ProcessBuilder pb = new ProcessBuilder("Notepad.exe",s);
                        pb.start();
                    } catch (IOException ex) {
                        Logger.getLogger(UIprj.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        jTextArea1.setVisible(true);
     //   jTextArea1.setBackground(Color.LIGHT_GRAY);                                       boz
     //   jTextArea1.setColumns(20); //20                                                                 boz
     //   jTextArea1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N                   boz
    //    jTextArea1.setRows(5);                                                                         boz
        jTextArea1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        jScrollPane1.setViewportView(jTextArea1);
        
        jButton1.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.shadow"));
        jButton1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rsa.co\\Documents\\NetBeansProjects\\crawler\\javad2.png")); // NOI18N
        jButton1.setText("جستجو");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        
        jButton2.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.shadow"));
        jButton2.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rsa.co\\Documents\\NetBeansProjects\\crawler\\right.png")); // NOI18N
        jButton2.setToolTipText("صفحه بعدی     next page");
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        
        jButton3.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.shadow"));
        jButton3.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rsa.co\\Documents\\NetBeansProjects\\crawler\\left.png")); // NOI18N
        jButton3.setToolTipText("صفحه قبلی        previous page");
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        
        jButton4.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        jButton4.setText("ایجاد فایل stopwords");
        jButton4.setIcon(new javax.swing.ImageIcon("babak.png"));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jButton4.setVisible(false);
        
        jButton5.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        jButton5.setText("ساخت ایندکس");
        jButton5.setIcon(new javax.swing.ImageIcon("halal.png"));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        
        jTextField2.setEditable(true);
        jTextField2.setBackground(Color.lightGray);
        jTextField2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.setText("pagenum from page count");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(62, 62, 62)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(81, 81, 81)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                      //  .addComponent(textfield3, javax.swing.GroupLayout.PREFERRED_SIZE,5, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(151, 151, 151)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               // .addComponent(textfield3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
       
    }
    
    
     private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {  //building stopwords file                                         
        // TODO add your handling code here:
        Set<String> commonTokens = new HashSet<>();
        LTools t = new LTools();
        String rootFilePath = "docs";
        File rootFile = new File(rootFilePath);
        ArrayList<File> arrDocs = new ArrayList<File>();
        arrDocs= t.findFiles(rootFile, arrDocs);  //creates array of documents
        if (arrDocs.size() > 1) 
        { // if we have more than one doc, then find stop words.
                 for (int i = 0; i < arrDocs.size(); i++) {
                 String fileText = t.readFile(arrDocs.get(i));
                 commonTokens = t.findStopWords( fileText, commonTokens);
                 }
        } 
     //#######################################################################
        ArrayList<String> st = new ArrayList<String>();  //array of stop words
        for(Object object : commonTokens)
            st.add((String) object );
     //########################################################################
        //creating a file of stop words :
       String path = "stop words.txt";		
		try{
			File file = new File(path); 
			if (!file.exists()) file.createNewFile();
			FileWriter fw = new FileWriter (file.getPath() , true);
			BufferedWriter  bw = new BufferedWriter (fw);
			for (int i=0 ; i <st.size() ; i++ ) {
				bw.write(st.get(i)+"\n"); //crate the file of stop words
				//bw.write(System.getProperty("line.separator"));
			}
			bw.close();
		}
		
		catch(Exception a) {
			System.out.println(a);
		}
                JOptionPane.showMessageDialog(null,"STOP WORDS FILE HAS BEEN CREATED!"," ACTION ",JOptionPane.INFORMATION_MESSAGE);
                System.out.println("STOP WORDS FILE HAS BEEN CREATED!");
    }                                        

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {  //indexing                                       
        // TODO add your handling code here:
        Crawler cr =  new Crawler();
        cr.getPageLinks("https://www.msrt.ir/fa/news");
        links = cr.getlinks();
        links.remove("https://www.msrt.ir/fa/news");
        System.out.println(links);
        System.out.println(links.size());
        cr.anqezi(links);
        FileCrawl fc = new FileCrawl();
        ArrayList<Document> arrdoc = fc.getarrdoc();
        System.out.println(arrdoc.size());
        // crating a directory of clean-docs : doc  defining tiltle & body for each one
        try{
                        Directory dir = FSDirectory.open(Paths.get("doc"));
			Analyzer analyzer = new PersianAnalyzer();  //Removes stop words from each file
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
			IndexWriter writer = new IndexWriter(dir, iwc);
                       
                        for (int i = 0; i < arrdoc.size(); i++) {
                                 writer.addDocument(arrdoc.get(i));
                         }
                         writer.close();
		}
		catch(IOException e ){
			System.out.print("error!");
		} 
        
                JOptionPane.showMessageDialog(null,"INDEXING FINISHED!"," ACTION ",JOptionPane.INFORMATION_MESSAGE);
                System.out.println("INDEXING FINISHED!");
    }                                        

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {  //showing the result                                        
        // TODO add your handling code here:
        String queryLine = st ;
        String indexPath = "doc";
		try {
			IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
			jTextArea1.setText("Number of all docs: " + String.valueOf(reader.numDocs())+"\n");
                       
			IndexSearcher searcher = new IndexSearcher(reader); 
			Analyzer analyzer = new PersianAnalyzer();
			QueryParser parser = new QueryParser("Body", analyzer);  //searching in the body of files
			//QueryParser parser = new QueryParser("Title", analyzer); //searching in the tiltle of files
			Query query = parser.parse(queryLine);
			TopDocs results = searcher.search(query, reader.numDocs()); //????^^^^^
			ScoreDoc[] hits = results.scoreDocs;   //computes Score of each Query-related file
			int numTotalHits = (int) results.totalHits;   //number of related files
                        
                        File f = new  File("a.txt");
			//jTextArea1.setText("<html><a href="+f.toURI().toURL()+">amirreza</a> <br>");   //             boz
                        System.out.println("Number of Hits: " + String.valueOf(numTotalHits) + "\n");
                        int x ;
                        if(numTotalHits % 10 ==0)
                        {  x = numTotalHits / 10;}
                        else
                        {  x = (numTotalHits / 10) + 1 ;}
                        pagecount = x ;
                        Output = new ArrayList<String>();
                        File file ;
                        String path ;
			StringBuilder sb = new StringBuilder(20000);
			int end = Math.min(numTotalHits, reader.numDocs()); 
			for (int i = 0; i < hits.length; i++) {
                                sb = sb.delete(0, sb.length());
				Document doc = searcher.doc(hits[i].doc);
				sb.append(doc.get("Title"));
                               /* path = doc.get("URL");
                                if(path == null) {
                                    File file2 = new File("a.txt");
                                    Output2.add(file2);
                                }
                                else{
                                file = new File(path);
                                System.out.println(path);
                                Output2.add(file); 
                                }*/
				sb.append("\t Score: ");
				sb.append(hits[i].score);
				sb.append("\n");
                                System.out.println(sb);
                                Output.add(sb.toString());
			}
                        pagenum = 1 ;
                        //adding 1st-10 result to textfield
                        StringBuilder sb2 = new  StringBuilder(2000);
                        int n = 10 ;
                        if(Output.size()<10)  n= Output.size();
                      //  sb2.append("<html>");
                        for(int j = 0 ; j <n ; j++)   
                        {  
                            sb2.append(Output.get(j));
                          //  sb2.append("<a href="+Output2.get(j).toURI().toURL()+">"+Output.get(j)+"</a> <br>");
                        }
                     //   sb2.append("</html>");
                        jTextArea1.setText(sb2.toString());
                        System.out.println(Output.get(0) + "\n" + Output.get(1));
                        String p = "page " + pagenum + " from " + pagecount ;
                        jTextField2.setText(p);
                    
		} catch (IOException e) {
			System.out.println(" caught a " + e.getClass()
					+ "\n with message: " + e.getMessage());
		} catch (org.apache.lucene.queryparser.classic.ParseException ex) {
			Logger.getLogger(UIprj.class.getName()).log(Level.SEVERE, null, ex);
		}catch(Exception e) {
            System.out.println("Null Pointer ");
        }
                
                
    }                                        

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) { //queryline                                           
        // TODO add your handling code here:
        st = evt.getActionCommand();
    }   
    
    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {  //jumping to a certain page 
        // TODO add your handling code here:
        st = evt.getActionCommand();
        int n = Integer.parseInt(st);
          if((n >= 1)&&(n <= pagecount))  {
           jTextArea1.setText(null);
           pagenum = n ;
           String p = "page " + pagenum + " from " + pagecount ;
           jTextField2.setText(p);
           int start = ((pagenum-1)*10)  ;
           int end ;
           if(pagenum == pagecount) {end = Output.size() ; }
           else {end = start +10 ;}
           //adding 10 result to rextfield
           StringBuilder sb = new StringBuilder();
        //   sb.append("<html>");
           for(int i = start ; i < end ; i++ ){
              /* try {
                   sb.append("<a href="+Output2.get(i).toURI().toURL()+">"+Output.get(i)+"</a> <br>");
               } catch (MalformedURLException ex) {
                   Logger.getLogger(UIprj.class.getName()).log(Level.SEVERE, null, ex);
               }*/
               sb.append(Output.get(i));
           }
          //     sb.append("</html>");
           jTextArea1.setText(sb.toString());
          }
          else{
              JOptionPane.showMessageDialog(null,"Page-Number must be in range of 1 to " + pagecount," ACTION ",JOptionPane.INFORMATION_MESSAGE);
          }
        
    } 

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {  //next page                                        
        // TODO add your handling code here:
      
       if(pagecount > pagenum)  {
           jTextArea1.setText(null);
           pagenum++;
           String p = "page " + pagenum + " from " + pagecount ;
           jTextField2.setText(p);
           int start = ((pagenum-1)*10)  ;
           int end ;
           if(pagenum == pagecount) {end = Output.size() ; }
           else {end = start +10 ;}
           StringBuilder sb = new StringBuilder();
           //  sb.append("<html>");
           for(int i = start ; i < end ; i++ ){
             /*try {
                   sb.append("<a href="+Output2.get(i).toURI().toURL()+">"+Output.get(i)+"</a> <br>");
               } catch (MalformedURLException ex) {
                   Logger.getLogger(UIprj.class.getName()).log(Level.SEVERE, null, ex);
               }*/
               sb.append(Output.get(i));
           }
            //   sb.append("</html>");
           jTextArea1.setText(sb.toString());
       }
    }                                        

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) { //previous page                                        
        // TODO add your handling code here:
        if(pagenum > 1)  {
           jTextArea1.setText(null);
           pagenum--;
           String p = "page " + pagenum + " from " + pagecount ;
           jTextField2.setText(p);
           int start = ((pagenum-1)*10)  ;
           int end ;
           if(pagenum == pagecount) {end = Output.size() ; }
           else {end = start +10 ;}
           StringBuilder sb = new StringBuilder();
           //    sb.append("<html>");
           for(int i = start ; i < end ; i++ ){
           /* try {
                   sb.append("<a href="+Output2.get(i).toURI().toURL()+">"+Output.get(i)+"</a> <br>");
               } catch (MalformedURLException ex) {
                   Logger.getLogger(UIprj.class.getName()).log(Level.SEVERE, null, ex);
               }*/
               sb.append(Output.get(i));
           }
           //    sb.append("</html>");
           jTextArea1.setText(sb.toString());
        }
    }

}
