import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class parser {

	class job {public int tab[][];}

 public static void main(String[] args) throws IOException {
 
	 Pattern pattern = Pattern.compile(".+");
	 //File file = new File("C:\\Users\\Vanessa Pinales\\Documents\\INSAToulouse\\8sem\\Meta-Heuristique\\edata\\car5.fjs");
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
                 //ArrayList<int[]> Operations = new ArrayList<int[]>();
		 //job tableau[] = new job[nbjobs];
		 
		 while (match.find()) {
			 //System.out.println("Job nÂ°" + (numtab+1) + " : " + match.group());
			 Pattern pattern21 = Pattern.compile("[0-9^.]+");
			 Matcher match21 = pattern21.matcher(match.group());
		 
			 match21.find();
			 nbop = Integer.parseInt(match21.group());
			 //System.out.println("Nombre d'operations : " + nbop);

			 int tab[][] = new int[nbop][nbmachines];
			 
			 for (int i=1 ; i<=nbop ; i++) {
				 //System.out.println("Operation n°" + i + " : ");
                                //int[] op= new int[2];
                                //op[0]=i; // id operation
                                //op[1] =numtab+1;//jobid
                                
				 match21.find();
				 machineop = Integer.parseInt(match21.group());
				 for (int j=0 ; j<machineop ; j++) {
					 
                                         
                                         match21.find();
					 aux = Integer.parseInt(match21.group());
					// System.out.println("M" + aux + " -> ");
                                         
					 match21.find();
					 aux2 = Integer.parseInt(match21.group());
					 tab[i-1][aux-1] = aux2;
					 //System.out.println(aux2);
				 }
                                //Operations.add(op);
			 }
                         
			 tableau.add(tab);
			 numtab++;
		 }
		 
		 /* Test fonction setList
		 ArrayList<Integer> uneliste = new ArrayList<Integer>();
		 uneliste = setList(tableau,1,5);
		 for (int a=0; a < uneliste.size() ; a++) {
			 System.out.print("!  " + uneliste.get(a) + "  ");
		 } */
		 
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
                 
		 System.out.println("Job nÂ°" + (i+1));
		 for (int j=0 ; j<maListe.get(i).length ; j++) {
			 System.out.print("Operation nÂ°" + j + " : ");
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
	
	 //liste passée en paramètre
	 ArrayList<int[][]> listeIni = new ArrayList<int[][]>();
	 
	 for(int i=0 ; i<maListe.size() ; i++){
		 int[][] nouveau = new int[nbop][nbmach];
		 for (int j=0 ; j<maListe.get(i).length ; j++) {
			 for (int k=0; k<maListe.get(i)[j].length ; k++) {
				 nouveau[j][k] = maListe.get(i)[j][k];
			 }
		 }
		 listeIni.add(nouveau);
	 }
	 
	 //ReprÃ©sentation/memorisation d'un solution
	 ArrayList<Integer> MA = new ArrayList<Integer>(); //Vecteur Machine Assignement
	 ArrayList<Integer> OS = new ArrayList<Integer>(); //Vecteur Operation Sequence
	
	 //pour tentative 2 :
	 int OSsize = nbjob * nbop;
	 int MAsize = (nbjob * nbop) + (nbjob - 1);
	 int MA2[] = new int[MAsize]; //Machine Assignement
	 int OS2[] = new int[OSsize]; // Operation Sequence
	 int PT[] = new int[OSsize]; // Process Time
	 
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
	 
         int[] tempsJob = new int[maListe.size()];
        int temps=0;
        int t1=0;
         
	 //job 1
	 int index = 0;
	 for(i=0 ; i<maListe.size() ; i++){
		 for (int j=0 ; j<maListe.get(i).length ; j++) {
			 for (int k=0; k<maListe.get(i)[j].length ; k++) {
				 if (maListe.get(i)[j][k] == 0) {
					 System.out.print(" ");
				 }
				 else {
                                     if(t1!=maListe.get(i)[j][k]){
                                        temps=temps+maListe.get(i)[j][k];
                                         t1=maListe.get(i)[j][k];
                                     }
					 v=Op.get(k)+1;
					 Op.set(k, v); 
					 if (M.get(k) == 0){
					 M.set(k, 1);
					 OS.add(i+1);
					 MA.add(k+1);
					//MA.set(index, element);
					
					 OS2[j] = i+1;
					 MA2[j] = k+1;
					 PT[j] = maListe.get(i)[j][k];
					 System.out.print("Machine nÂ°" + (k+1) + " fait l'operation nÂ° " + (j+1) + " du job " + (i+1) + "(" + maListe.get(i)[j][k] + ")\n");
					 }
				 }
			 }
		 }
                 tempsJob[i] = temps;
	 }
	 MA. add(0);
	 MA2[nbmach] = 0;
	 System.out.print("i" + Op.get(0)+ "\n");
	 
	 for (int x=0 ; x<nbjob ; x++) {
	        System.out.print("temps[Job n°"+(x+1)+"] = " + tempsJob[x]+ "\n");
	 }
	 
	 
	 int min = 100000;
	 int operation = 0;
	 int job = 0;
	 int val[][] = new int[nbop][nbmach];
	 //int arrete = 0;

     int OSposition = 0;
     int MAposition = 0;
	 
	 ArrayList<ArrayList <Integer>> aux = new ArrayList<ArrayList<Integer>>();
         ArrayList<ArrayList <Integer>> aux4 = new ArrayList<ArrayList<Integer>>();
	 int ind=0;
         int size=0;
         int Jmin;
	 for (int k=0; k<nbmach;k++) {
		 ArrayList<Integer> aux2 = new ArrayList<Integer>();
                 ArrayList<Integer> aux3 = new ArrayList<Integer>();
                 //System.out.println( "Op.get(k)-1=" + (Op.get(k)-1));
                 
		 for(int x=0; x < Op.get(k)-1; x++) {
			 min = 100000;
                         Jmin=10000000;
			 //arrete = 0;
			 for(i=1; i<maListe.size() ; i++) {
                             //cherhcer le job qui prends moins temps 
                             if(tempsJob[i]<Jmin){
                                 Jmin=i+1;
                             }
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
			 //aux2.add(operation);
                         aux3.add(operation);
			 OSposition = (job-1)*nbop + operation -1;
			 //System.out.print("os position : " + OSposition + "\n\n");
             OS2[OSposition] = job;
             
             PT[OSposition] = min;
             MAposition = OSposition + (job-1);
             //System.out.print("ma position : " + MAposition + "\n\n");
             MA2[MAposition] = k+1;
             
			 System.out.print("Machine nÂ°" + (k+1) + " fait l'operation nÂ°" + operation + " du job nÂ°" + job + "(" + min + ")\n");}
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
                     aux4.add(aux3);
                     for(int y=0;y<(ind-size);y++){
                         aux2.add(0);
                         aux3.add(0);
                     }
                 }else{
                 aux.add(aux2);
                 aux4.add(aux3);
                 }
		 index = 0;
	 }
         
         int ind1=0;
         int indline= aux.get(ind1).size();
         System.out.println("indline="+indline);
	 System.out.print("\n");
	 for (int b=0 ; b < aux.size() ; b++) {      
                for (int c=0 ; c < aux.get(b).size() ; c++) {
                 System.out.print(aux.get(b).get(c) + " ");
                }
                System.out.print("\n");  
         }
        System.out.print("\n");   System.out.print("\n"); 
         //print aux4 - #operation par machine
         for (int b=0 ; b < aux4.size() ; b++) {      
                for (int c=0 ; c < aux4.get(b).size() ; c++) {
                 System.out.print(aux4.get(b).get(c) + " ");
                }
                System.out.print("\n");  
         }
         
         System.out.print("\n");   System.out.print("\n");   System.out.print("\n");
         int u=0;
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
	 
	 System.out.print("\n\ntentative 2\n");
	 
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
	 
	 System.out.print("PT : \n");
	 System.out.print("(");
	 for (int a = 0 ; a < OSsize ; a++) {
		 System.out.print(PT[a] + ",");
	 }
	 System.out.print(") \n");
	 
	 //test OSmutation
	 int O[] = new int[OSsize];
	 O = OSmutation(OS2, OSsize);
	 
	 System.out.print("OSmutation : (");
	 for (int e = 0 ; e < OSsize ; e++) {
		 System.out.print(O[e] + ",");
	 }
	 System.out.print(") \n");
	 
	 //test MAmutation
	 int O2[] = new int [MAsize];
	 O2 = MAmutation(listeIni, MA2, nbmach);
	 
	 System.out.print("MAmutation : (");
	 for (int a = 0 ; a < MAsize ; a++) {
		 if(MA2[a]==0) {
			 System.out.print("|");
		 }else {
			 System.out.print("M" + O2[a] + ",");
		 }
	 }
	 System.out.print(") \n");
	 
	 /* test OScrossover
	 int p1[] = {1,3,1,2,2,3};
	 int p2[] = {3,2,1,2,3,1};
	 OScrossover(3, p1, p2); */
	 
	 /* test MAcrossover
	 int p1[] = {2,1,3,1,2,2};
	 int p2[] = {1,3,2,3,3,1};
	 MAcrossover(p1,p2); */
	 
 }
 
 
 private static int[] OSmutation (int[] P, int size) {
	 
	 int randomNum = ThreadLocalRandom.current().nextInt(0, size + 1);
	 int randomNum2;
	 do {
		 randomNum2 = ThreadLocalRandom.current().nextInt(0, size + 1);
	 } while (randomNum == randomNum2);
	 
	 int[] O = new int[size];
	 
	 for (int i = 0 ; i < size ; i++) {
		 if (i == randomNum) {
			 O[i] = P[randomNum2];
		 }
		 else if (i == randomNum2) {
			 O[i] = P[randomNum];
		 } 
		 else {
			 O[i] = P[i];
		 }
	 }
	 
	 return O;
	 
 }

 // liste des machines disponibles pour l'opération passée en paramètre
 private static ArrayList<Integer> setList (ArrayList<int[][]> maListe, int numjob, int numop) {
	 
	 ArrayList<Integer> ListeMach = new ArrayList<Integer>();
	 
	 for(int i=0 ; i<maListe.size() ; i++){
		 if (i == numjob-1) {
			 for (int j=0 ; j<maListe.get(i).length ; j++) {
				 if (j == numop-1) {
					 for (int k=0; k<maListe.get(i)[j].length ; k++) {
						 if (maListe.get(i)[j][k] != 0) {
							 ListeMach.add(k+1);
						 }
					 }
				 }
			 }
		 }
	 }
	 
	 return ListeMach;
 }
 
 
 
 private static int[] MAmutation (ArrayList<int[][]> maListe, int[] P, int nbmachines) {
	 
	 int r = P.length/2;
	 int position = 0;
	 int numjob = 0;
	 int numop = 0;
	 
	 int occ1 = 1;
	 int occ2 = 0;
	 
	 int[] O = new int[P.length]; //resultat
	 int[] aux = new int[P.length];
	 aux = P;
	 
	 int stop = 1; 
	 
	 do {
		 // pour trouver la position
		 for (int i=0; i<P.length ; i++) {
			 occ2++;
			 if (P[i] == 0) {
				 occ1++;
				 occ2 = 0;
			 }
			 if (i == position) {
				 numjob = occ1;
				 numop = occ2;
			 }
		 }
		 occ1 = 1;
		 occ2 = 0;
		 
		 // liste des machines possibles
		 ArrayList<Integer> uneliste = setList(maListe, numjob, numop);
		 
		 if (numop != 0) {
			 r--;
			 if (uneliste.size() > 1) {
				 //System.out.print("on peut switcher\n");
				 for (int i=0 ; i<aux.length ; i++) {
					 if (i == position) {
						 for (int l=0 ; l<uneliste.size() ; l++) {
							 if ((uneliste.get(l) != aux[i]) && (stop == 1)) {
								 //System.out.print("on switch\n" + uneliste.get(l) + aux[i]);
								 O[i] = uneliste.get(l);
								 stop = 0;
							 }
						 }
					 }
					 else {
						 O[i] = aux[i];
					 }
				 }
			 
			 }
			 else {
				 O = aux;
			 }
		 }
		 aux = O;
		 position += 2;
		 stop = 1;
	 } while (r>0);
	 
	 return O;
	 
 }
 
 private static void OScrossover(int nbjobs, int[] P1, int[] P2) {
	 
	 ArrayList<Integer> jobset1 = new ArrayList<Integer>();
	 ArrayList<Integer> jobset2 = new ArrayList<Integer>();
	 
	 int[] O1 = new int[P1.length];
	 int[] O2 = new int[P2.length];
	 
	 //step 1
	 
	 for (int n=0 ; n<nbjobs ; n++) {
		 if (n%2 == 1) {
			 jobset1.add(n+1);
		 }
		 else {
			 jobset2.add(n+1);
		 }
	 }
	 
	 System.out.print("jobset 1 : ");
	 for (int i=0; i<jobset1.size() ; i++) {
		 System.out.print(jobset1.get(i) + " ");
	 }
	 System.out.print("\njobset 2 : ");
	 for (int i=0; i<jobset2.size() ; i++) {
		 System.out.print(jobset2.get(i) + " ");
	 }
	 
	 //step 2
	 
	 for (int j=0 ; j<jobset1.size() ; j++) {
		 for (int i=0 ; i<P1.length ; i++) {
			 if (P1[i] == jobset1.get(j)) {
				 O1[i] = P1[i];
				 P1[i] = 0;
			 }
		 }
		 for (int i2=0 ; i2<P2.length; i2++) {
			 if (P2[i2] == jobset1.get(j)) {
				 O2[i2] = P2[i2];
				 P2[i2] = 0;
			 }
		 }
	 }
	 
	 //step 3
	 
	 for (int i=0 ; i<P1.length ; i++) {
		 for (int j=0 ; j<O2.length ; j++) {
			 if ((P1[i] != 0) && (O2[j] == 0)) {
				 O2[j] = P1[i];
				 P1[i] = 0;
			 }
		 }
	 }
	 
	 for (int i=0 ; i<P2.length ; i++) {
		 for (int j=0 ; j<O1.length ; j++) {
			 if ((P2[i] != 0) && (O1[j] == 0)) {
				 O1[j] = P2[i];
				 P2[i] = 0;
			 }
		 }
	 }	 
	 
	 System.out.print("P1 : ");
	 for (int i=0; i<P1.length ; i++) {
		 System.out.print(P1[i] + " ");
	 }
	 System.out.print("\nP2 : ");
	 for (int i=0; i<P2.length ; i++) {
		 System.out.print(P2[i] + " ");
	 }
	 System.out.print("\nO1 : ");
	 for (int i=0; i<O1.length ; i++) {
		 System.out.print(O1[i] + " ");
	 }
	 System.out.print("\nO2 : ");
	 for (int i=0; i<O2.length ; i++) {
		 System.out.print(O2[i] + " ");
	 }
	 
	 
 }
 
 private static void MAcrossover (int[] P1, int[] P2) {
	 
	 int randomNum = ThreadLocalRandom.current().nextInt(0, P1.length);
	 int randomNum2 = ThreadLocalRandom.current().nextInt(randomNum, P1.length + 1);
	 
	 int[] O1 = new int[P1.length];
	 int[] O2 = new int[P2.length];
	 
	 for (int i=0 ; i<randomNum ; i++) {
		 O1[i] = P1[i];
		 O2[i] = P2[i];
	 }
	 
	 for (int i=randomNum ; i<randomNum2 ; i++) {
		 O1[i] = P2[i];
		 O2[i] = P1[i];
	 }
	 
	 for (int i=randomNum2 ; i<P1.length ; i++) {
		 O1[i] = P1[i];
		 O2[i] = P2[i];
	 }
	 
	 System.out.print("crossover point 1 : " + randomNum + "\n");
	 System.out.print("crossover point 2 : " + randomNum2 + "\n");
	 
	 System.out.print("P1 : ");
	 for (int i=0; i<P1.length ; i++) {
		 System.out.print(P1[i] + " ");
	 }
	 System.out.print("\nP2 : ");
	 for (int i=0; i<P2.length ; i++) {
		 System.out.print(P2[i] + " ");
	 }
	 System.out.print("\nO1 : ");
	 for (int i=0; i<O1.length ; i++) {
		 System.out.print(O1[i] + " ");
	 }
	 System.out.print("\nO2 : ");
	 for (int i=0; i<O2.length ; i++) {
		 System.out.print(O2[i] + " ");
	 }
	 
	 
 }
 
 

 
}
