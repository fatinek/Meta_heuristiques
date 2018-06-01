import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class parser {
 
 /**
 * @param args
 * @throws IOException
 */

	class job {public int tab[][];}

 public static void main(String[] args) throws IOException {
 
	 Pattern pattern = Pattern.compile(".+");
	 //File file = new File("C:\\Users\\Vanessa Pinales\\Documents\\INSAToulouse\\8sem\\Meta-Heuristique\\edata\\car1.fjs");
	 File file = new File("./car1.fjs");
	 //File file = new File("/home/pinalesc/Documents/Meta-heuristiques/edata/mt06.fjs");
	 String fileContent = readFileAsString(file.getAbsolutePath());
 
	 Matcher match = pattern.matcher(fileContent);
 
	 int numnombre = 1;
	 int nbjobs = 0;
	 int nbmachines = 0;
	 int nbop = 0;
	 int machineop = 0;
	 int numtab = 0;
	 int aux = 0;
	 int aux2 = 0;
 
	 	 match.find();
		 //System.out.println(match.group());
		 Pattern pattern2 = Pattern.compile("[0-9^.]+");
		 Matcher match2 = pattern2.matcher(match.group());
		 
		 while (match2.find() && numnombre <= 3){
			 switch(numnombre){
			 	case 1 : 	System.out.println("Nombre de jobs : " + match2.group());
			 				nbjobs = Integer.parseInt(match2.group());
			 				break;
			 	case 2 : 	System.out.println("Nombre de machines : " + match2.group());
			 				nbmachines = Integer.parseInt(match2.group());
			 				break;
			 	case 3 :	System.out.println("Nombre moyen de machines par operation : " + match2.group());
			 				break;
			 }
			 numnombre++;
		 }
		 
		 ArrayList<int[][]> tableau = new ArrayList<int[][]>();
		 //job tableau[] = new job[nbjobs];
		 
		 while (match.find()) {
			 //System.out.println("Job n°" + (numtab+1) + " : " + match.group());
			 Pattern pattern21 = Pattern.compile("[0-9^.]+");
			 Matcher match21 = pattern21.matcher(match.group());
		 
			 match21.find();
			 nbop = Integer.parseInt(match21.group());
			 //System.out.println("Nombre d'operations : " + nbop);

			 int tab[][] = new int[nbop][nbmachines];
			 
			 for (int i=1 ; i<=nbop ; i++) {
				 //System.out.println("Operation n°" + i + " : ");
				 match21.find();
				 machineop = Integer.parseInt(match21.group());
				 for (int j=0 ; j<machineop ; j++) {
					 match21.find();
					 aux = Integer.parseInt(match21.group());
					 //System.out.println("M" + aux + " -> ");
					 match21.find();
					 aux2 = Integer.parseInt(match21.group());
					 tab[i-1][aux-1] = aux2;
					 //System.out.println(aux2);
				 }
			 }
			 tableau.add(tab);
			 numtab++;
		 }
		 
		 Afficher(tableau);
		 Ordonner(tableau, nbmachines, nbjobs, nbop);
		 Afficher(tableau);
	 }

 
 private static String readFileAsString(String filePath) throws java.io.IOException{
	 byte[] buffer = new byte[(int) new File(filePath).length()];
	 BufferedInputStream f = null;
	 try {
		 f = new BufferedInputStream(new FileInputStream(filePath));
		 f.read(buffer);
	 } finally {
		 if (f != null) try { f.close(); } catch (IOException ignored) { }
	 }
	 return new String(buffer);
 }

 private static void Afficher(ArrayList<int[][]> maListe) {
	 for(int i=0; i<maListe.size() ; i++) {
		 System.out.println("Job n°" + (i+1));
		 for (int j=0 ; j<maListe.get(i).length ; j++) {
			 System.out.print("Operation n°" + j + " : ");
			 for (int k=0; k<maListe.get(i)[j].length ; k++) {
				 if (maListe.get(i)[j][k] == 0) {
					 System.out.print(" -- ");
				 }
				 else {
					 System.out.print(" " + maListe.get(i)[j][k] + " ");
				 }
			 }
			 System.out.print("\n");
		 }
	 }
	 
	  
 }
 
 private static void Ordonner(ArrayList<int[][]> maListe, int nbmach, int nbjob, int nbop) {
	 
	//Représentation/memorisation d'un solution
	 ArrayList<Integer> MA = new ArrayList<Integer>(); //Vecteur Machine Assignement
	 ArrayList<Integer> OS = new ArrayList<Integer>(); //Vecteur Operation Sequence
	
	 //pour tentative 2 :
	 int OSsize = nbjob * nbop;
	 int MAsize = (nbjob * nbop) + (nbjob - 1);
	 int MA2[] = new int[MAsize];
	 int OS2[] = new int[OSsize]; 
	 
	 int i = 0;
	 int v =0;
	 ArrayList<Integer> Op = new ArrayList<Integer>(); //Nb Operations
	 ArrayList<Integer> M = new ArrayList<Integer>(); //Liste Machine[job]
	 ArrayList<int []> Job = new ArrayList<int []>(); //
	 int J[] = new int[2];
	 
	 
	 for (int x=0 ; x<nbmach ; x++) {
	        M.add(x);
	        M.set(x, 0);
	        Op.add(x);
	        Op.set(x, 0);
	 }
	 
	 //job 1
	 int index = 0;
	 for(i=0 ; i<maListe.size() ; i++){
		 for (int j=0 ; j<maListe.get(i).length ; j++) {
			 for (int k=0; k<maListe.get(i)[j].length ; k++) {
				 if (maListe.get(i)[j][k] == 0) {
					 System.out.print(" ");
				 }
				 else {
					 v=Op.get(k)+1;
					 Op.set(k, v); 
					 if (M.get(k) == 0){
					 M.set(k, 1);
					 OS.add(i+1);
					 MA.add(k+1);
					//MA.set(index, element);
					
					 OS2[j] = i+1;
					 MA2[j] = k+1;
					 
					 System.out.print("Machine n°" + (k+1) + " fait l'operation n° " + (j+1) + " du job " + (i+1) + "\n");
					 }
				 }
			 }
		 }
	 }
	 MA. add(0);
	 MA2[5] = 0;
	 System.out.print("i" + Op.get(0)+ "\n");
	 
	 for (int x=0 ; x<nbmach ; x++) {
	        System.out.print("Machine[job] = " + M.get(x)+ "\n");
	 }
	 
	 //Garder min machine 1
	 int min = 100000;
	 int operation = 0;
	 int job = 0;
	 int val[][] = new int[nbop][nbmach];
	 //int arrete = 0;

     int OSposition = 0;
     int MAposition = 0;
	 
	 ArrayList<ArrayList <Integer>> aux = new ArrayList<ArrayList<Integer>>();
	 ArrayList<int[]> aux3 = new ArrayList<int[]>();
         int ind=0;
         int nOp=0;
         int size=0;
	 for (int k=0; k<nbmach;k++) {
		 ArrayList<Integer> aux2 = new ArrayList<Integer>();
                 System.out.println( "Op.get(k)-1=" + (Op.get(k)-1));
                 nOp=Op.get(k)-1;
		 for(int x=0; x < Op.get(k)-1; x++) {
			 min = 100000;
			 //arrete = 0;
			 for(i=1; i<maListe.size() ; i++) {
				 for (int j=0 ; j<maListe.get(i).length ; j++) {
				 	/*for(int t=0; t < Job.size(); t++) {
				 		if ((Job.get(t)[0] == i) && ((Job.get(t)[1] == j))) {
				 			arrete = 1;
				 		}
				 	}*/
					 //chercher temps min
				 	if ( (maListe.get(i)[j][k] != 0) && (maListe.get(i)[j][k] < min) /*&& (arrete == 0)*/) {
						 min = maListe.get(i)[j][k];
						 operation = j+1;
						 job = i+1;
					}	
				 }
				 	
			 }
		//actualiser valeurs 	 
		 for (int ligne = 0; ligne < nbmach; ligne++) {
			 if (maListe.get(job-1)[operation-1][ligne] != 0) {
				 val = maListe.get(job-1);
				 val[operation-1][ligne] = 0;
				 maListe.set(job-1,val);
			 }
		 }
		 val = maListe.get(job-1);
		 val[operation-1][k] = 0;
		 maListe.set(job-1,val);
		 if (min != 100000) {
			
			 aux2.add(job);
			 
			 OSposition = (job-1)*nbop + operation -1;
			 //System.out.print("os position : " + OSposition + "\n\n");
             OS2[OSposition] = job;
             
             MAposition = OSposition + (job-1);
             //System.out.print("ma position : " + MAposition + "\n\n");
             MA2[MAposition] = k+1;
             
			 System.out.print("Machine n°" + (k+1) + " fait l'operation n°" + operation + " du job n°" + job + "(" + min + ")\n");}
		 J[0] = job;
		 J[1] = operation;
		 Job.add(J);
		 }
                 
                 System.out.println("aux2.size()="+ aux2.size());
                 size=aux2.size();
                 if(aux2.size()>ind){
                 ind=aux2.size();
                 }
                 System.out.println("ind="+ ind);
                 if(size<ind){
                     aux.add(aux2);
                     for(int y=0;y<(ind-size);y++){
                         aux2.add(0);
                     }
                 }else{
                 aux.add(aux2);
                 }
		 index = 0;
	 }
         
         int ind1=0;
         
         int indline= aux.get(ind1).size();
	 System.out.print("\n");
	 for (int b=0 ; b < aux.size() ; b++) {      
                for (int c=0 ; c < aux.get(b).size() ; c++) {
                 System.out.print(aux.get(b).get(c) + " ");
                }
                System.out.print("\n");  
         }
         
         System.out.print("\n");   System.out.print("\n");   System.out.print("\n");    
         //add JM in MA
         
         System.out.print("OS[]=");
             for(int d=0;d<indline;d++){
            	 
            	 int[] JM = new int[nbmach];
            	 for (int b=0 ; b < aux.size() ; b++) { 
            	 
                for (int c=0 ; c < aux.get(b).size() ; c++) {
                    if(ind1<JM.length){
                    	//if(aux.get(c).get(d)!=0) {
                        JM[ind1] = aux.get(c).get(d);
                         ind1++;
                    	//}
                    }                    
                 }
                 
                
         }
            	 //System.out.print("JM[]=");
            		 for(int g=0; g <JM.length ; g++) {
	                	 if (JM[g] != 0) {
	                		 OS.add(JM[g]);
	                		MA.add(g+1); 
	                	 }
                     //System.out.print(JM[g] + " ");
            		 }
                  MA.add(0);
                  ind1=0;
                 //System.out.print("\n");     
         }        
         
	 System.out.print("\n");
	 
	 System.out.print("MA : \n");
	 System.out.print("(");
	 for (int a = 0 ; a < MA.size() ; a++) {
		 if(MA.get(a)==0) {
			 System.out.print("|");
		 }else {
			 System.out.print(MA.get(a) + ",");
		 }
	 }
	 System.out.print(") ");
	 System.out.print("\nOS : \n");
	 System.out.print("(");
	 for (int a = 0 ; a < OS.size() ; a++) {
		 System.out.print(OS.get(a) + ",");
	 }
	 System.out.print(") \n");
	 
	 System.out.print("tentative 2\n");
	 
	 System.out.print("MA2 : \n");
	 System.out.print("(");
	 for (int a = 0 ; a < MAsize ; a++) {
		 if(MA2[a]==0) {
			 System.out.print("|");
		 }else {
			 System.out.print("M" + MA2[a] + ",");
		 }
	 }
	 System.out.print(") ");
	 System.out.print("\nOS2 : \n");
	 System.out.print("(");
	 for (int a = 0 ; a < OSsize ; a++) {
		 System.out.print(OS2[a] + ",");
	 }
	 System.out.print(") \n");
	 
	 
	 
 }

 
}
