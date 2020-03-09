//package Program;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonClass {

	public static int getFileLines(String ReadSetPath) throws IOException {
		int line = 0;
		String encoding = "utf-8";
		File file = new File(ReadSetPath);
		if (file.isFile() && file.exists()) {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
			BufferedReader bufferedReader = new BufferedReader(read);
			while ((bufferedReader.readLine()) != null) {
				line++;
			}
			bufferedReader.close();
		}
		return line;
	}
	public static long getFileSize(File file) {
		long filesize = 0;
		if (file.exists() && file.isFile()) {
			filesize = file.length();
		}
		return filesize;
	}
	public static int getFileLength(String ReadSetPath) throws IOException {
		int line = 0;
		String readtemp = "";
		String encoding = "utf-8";
		File file = new File(ReadSetPath);
		if (file.isFile() && file.exists()) {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
			BufferedReader bufferedReader = new BufferedReader(read);
			while ((readtemp = bufferedReader.readLine()) != null) {
				if (readtemp.charAt(0) != '>') {
					line += readtemp.length();
				}
			}
			bufferedReader.close();
		}
		return line;
	}
	public static void FileToArray(String FileSetPath, String[] FileSetArray, String FilterChar) {
		int count = 0;
		String readtemp = "";
		try {
			String encoding = "utf-8";
			File file = new File(FileSetPath);
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				while ((readtemp = bufferedReader.readLine()) != null) {
					if (!readtemp.startsWith(FilterChar) && readtemp.length() > 5) {
						FileSetArray[count++] = readtemp;
					}
				}
				bufferedReader.close();
			} else {
				System.out.println("File is not exist!");
			}
		} catch (Exception e) {
			System.out.println("Error liaoxingyu");
			e.printStackTrace();
		}
	}
	public static int FileToArray2(String FileSetPath, String[] FileSetArray, String FilterChar) {
		int count = 0;
		String readtemp = "";
		try {
			String encoding = "utf-8";
			File file = new File(FileSetPath);
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				while ((readtemp = bufferedReader.readLine()) != null) {
					if (!readtemp.startsWith(FilterChar) && readtemp.length() > 5) {
						FileSetArray[count++] = readtemp;
					}
				}
				bufferedReader.close();
			} else {
				System.out.println("File is not exist!");
			}
		} catch (Exception e) {
			System.out.println("Error liaoxingyu");
			e.printStackTrace();
		}
		return count;
	}
	public static void delAllFile(File path) {
		if (!path.exists() || !path.isDirectory()) {
			return;
		}
		String[] tmpList = path.list();
		if (tmpList != null) {
			for (String aTempList : tmpList) {
				File tmpFile = new File(path, aTempList);
				if (tmpFile.isFile()
						&& (tmpFile.getName().startsWith("read_") || tmpFile.getName().endsWith(".amb")
								|| tmpFile.getName().endsWith(".ann") || tmpFile.getName().endsWith(".bwt")
								|| tmpFile.getName().endsWith(".pac") || tmpFile.getName().endsWith(".sa")
								|| tmpFile.getName().endsWith(".h5") || tmpFile.getName().endsWith(".SAMFile")
								|| tmpFile.getName().endsWith(".gz") || tmpFile.getName().endsWith(".depth")
								|| tmpFile.getName().endsWith(".fastg") || tmpFile.getName().endsWith(".yaml")
								|| tmpFile.getName().endsWith(".info") || tmpFile.getName().endsWith(".gfa")
								|| tmpFile.getName().endsWith(".paths") || tmpFile.getName().endsWith(".fasta")
								|| tmpFile.getName().endsWith(".bt2") || tmpFile.getName().endsWith(".fa")
								|| tmpFile.getName().endsWith(".sam") || tmpFile.getName().endsWith(".bam")
								|| tmpFile.getName().endsWith(".txt"))|| tmpFile.getName().endsWith(".bai")
						        || tmpFile.getName().endsWith(".part0")|| tmpFile.getName().endsWith(".part0")
						        || tmpFile.getName().endsWith("_files")|| tmpFile.getName().endsWith(".can")
						        || tmpFile.getName().startsWith("r_0")|| tmpFile.getName().startsWith("vol0")|| tmpFile.getName().startsWith(".mmi")) {
					tmpFile.delete();
				} else if (tmpFile.isDirectory()) {
					delAllFile(tmpFile);
				}
			}
		}
	}
	public static void deleteFile(File file) {
		if (file.isFile()) {
			file.delete();
		} else {
			String[] childFilePath = file.list();
			for (String path : childFilePath) {
				File childFile = new File(file.getAbsoluteFile() + "/" + path);
				deleteFile(childFile);
			}
			System.out.println(file.getAbsoluteFile());
			file.delete();
		}
	}
	public static void GenerateFastaFromFastqFiles(String toolsPath, String FastqFile, String FastaFile) {
		try {
			Runtime r_change = Runtime.getRuntime();
			Process pr_change = null;
			String[] cmd_change = { "sh","-c", toolsPath+"/fq2fa "+FastqFile+" "+FastaFile};			
			pr_change = r_change.exec(cmd_change);
			pr_change.waitFor();
		} catch (Exception e) {
			System.out.println("Error liaoxingyu");
			e.printStackTrace();
		}
	}
	public static void MergeFastaMultiLines(String homePath, String FilePath, String WritePath) throws IOException {
		try {
			Runtime r_change = Runtime.getRuntime();
			Process pr_change = null;
			String[] cmd_change = { "sh","-c","python "+homePath+"/tools/fasta.py "+FilePath+" "+WritePath};
			pr_change = r_change.exec(cmd_change);
			pr_change.waitFor();
		} catch (Exception e) {
			System.out.println("Error liaoxingyu");
			e.printStackTrace();
		}
	}
	public static int SamFileToArray(String FileSetPath, String[] FileSetArray) {
		int count = 0;
		String readtemp = "";
		try {
			String encoding = "utf-8";
			File file = new File(FileSetPath);
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				while ((readtemp = bufferedReader.readLine()) != null) {
					if (readtemp.charAt(0) != '@') {
						String[] Splitp = readtemp.split("\t|\\s+");
						if (!Splitp[1].equals("4")) {
							FileSetArray[count++] = readtemp;
						}
					}
				}
				bufferedReader.close();
			} else {
				System.out.println("File is not exist!");
			}
		} catch (Exception e) {
			System.out.println("Error liaoxingyu");
			e.printStackTrace();
		}
		return count;
	}
	public static void delSpecialFile(File path, String Prefix, String suffix) {
		if (!path.exists() || !path.isDirectory()) {
			return;
		}
		String[] tmpList = path.list();
		if (tmpList != null) {
			for (String aTempList : tmpList) {
				File tmpFile = new File(path, aTempList);
				if (tmpFile.isFile() && (tmpFile.getName().endsWith(suffix) && tmpFile.getName().startsWith(Prefix))) {
					tmpFile.delete();
				}
			}
		}
	}
    private static int compare(String str, String target) 
    {
        int d[][];
        int n = str.length();
        int m = target.length();
        int i;
        int j;
        char ch1;
        char ch2;
        int temp;
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n + 1][m + 1];
        for (i = 0; i <= n; i++) {
               d[i][0] = i;
        }
        for (j = 0; j <= m; j++) {
               d[0][j] = j;
        }
        for (i = 1; i <= n; i++) {
            ch1 = str.charAt(i - 1);
            for (j = 1; j <= m; j++) {
               ch2 = target.charAt(j - 1);
               if (ch1 == ch2) {
                   temp = 0;
               } else {
                   temp = 1;
               }
               d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
            }
        }
        return d[n][m];
    }
    private static int min(int one, int two, int three) {
        return (one = one < two ? one : two) < three ? one : three;
    }
    public static float getSimilarityRatio(String str, String target) {
        return 1 - (float) compare(str, target) / Math.max(str.length(), target.length());
    }
	public static String maxSubstring(String strOne, String strTwo){
		if(strOne==null || strTwo == null){
			return null;
		}
		if(strOne.equals("") || strTwo.equals("")){
			return null;
		}
		String max = "";
		String min = "";
		if(strOne.length() < strTwo.length()){
			max = strTwo;
			min = strOne;
		} else{
			max = strTwo;
			min = strOne;
		}
		String current = "";
		for(int i=0; i<min.length(); i++){
			for(int begin=0, end=min.length()-i; end<=min.length(); begin++, end++){
				current = min.substring(begin, end);
				if(max.contains(current)){
					return current;
				}
			}
		}
		return null;
	}
	public static String reverse(String s) {
		int length = s.length();
		String reverse = "";
		for (int i = 0; i < length; i++) {
			if (s.charAt(i) == 'A') {
				reverse = "T" + reverse;
			} else if (s.charAt(i) == 'T') {
				reverse = "A" + reverse;
			} else if (s.charAt(i) == 'G') {
				reverse = "C" + reverse;
			} else if (s.charAt(i) == 'C') {
				reverse = "G" + reverse;
			} else {
				reverse = "N" + reverse;
			}
		}
		return reverse;
	}
	public static void delCommonFile(File path, String Prefix, String suffix) {
		if (!path.exists() || !path.isDirectory()) {
			return;
		}
		String[] tmpList = path.list();
		if (tmpList != null) {
			for (String aTempList : tmpList) {
				File tmpFile = new File(path, aTempList);
				if (tmpFile.isFile() && (tmpFile.getName().endsWith(suffix) || tmpFile.getName().startsWith(Prefix))) {
					tmpFile.delete();
				}
			}
		}
	}
	public static void copyFile(String oldPath, String newPath) throws IOException {
		File oldFile = new File(oldPath);
		File file = new File(newPath);
		FileInputStream in = new FileInputStream(oldFile);
		FileOutputStream out = new FileOutputStream(file);
		byte[] buffer = new byte[2097152];
		int readByte = 0;
		while ((readByte = in.read(buffer)) != -1) {
			out.write(buffer, 0, readByte);
		}
		in.close();
		out.close();
	}
	public static double EstimatingKmerDepth(String gceFile1, String WritePath) throws IOException {
		double PeakValue = 0;
		String encoding = "utf-8";
		String readtemp = "";
		File file1 = new File(gceFile1);
		if (file1.isFile() && file1.exists()) {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file1), encoding);
			BufferedReader bufferedReader = new BufferedReader(read);
			while ((readtemp = bufferedReader.readLine()) != null) {
				if (readtemp.equals("Final estimation table:")) {
					bufferedReader.readLine();
					String GetString = bufferedReader.readLine();
					String[] SplitLine = GetString.split("\t|\\s+");
					PeakValue = Double.valueOf(SplitLine[4]);
					break;
				}
			}
			bufferedReader.close();
		}
		return PeakValue;
	}
	public static void RewriteFile(String OringnalFile, String WritePath, int m) throws IOException {
		int IndexNum = 0;
		String encoding = "utf-8";
		String readtemp = "";
		File file1 = new File(OringnalFile);
		if (file1.isFile() && file1.exists()) {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file1), encoding);
			BufferedReader bufferedReader = new BufferedReader(read);
			while ((readtemp = bufferedReader.readLine()) != null) {
				if (readtemp.charAt(0) != '>' && readtemp.length()>=m) {
					FileWriter writer1 = new FileWriter(WritePath, true);
					writer1.write(">NODE_" + (IndexNum++) + "_Length_" + readtemp.length() + "\n" + readtemp + "\n");
					writer1.close();
				}
			}
			bufferedReader.close();
		}
	}
	public static void DelePathFiles(String FilePath, String Name_Str) throws Exception {
		Process p_pre = null;
		Runtime r_pre = Runtime.getRuntime();
		String[] cmd_pre = { "sh", "-c", "rm -rf  " + FilePath + Name_Str };
		p_pre = r_pre.exec(cmd_pre);
		p_pre.waitFor();
	}
	public static void multipleAlignmentRatios(String samfile, String output) throws IOException
	{
		int totalFragments=0;
		int unmappedSegments=0;
		int multiAlignSegments=0;
		int singleAlignSegments=0;
		String readtemp = "";
		DecimalFormat df = new DecimalFormat("0.00");
		ArrayList<String> readmark = new ArrayList<String>();
		ArrayList<String> overlaps = new ArrayList<String>();
		File file1 = new File(samfile);
		if (file1.isFile() && file1.exists()){
			InputStreamReader read;
			read = new InputStreamReader(new FileInputStream(file1),"utf-8");
			BufferedReader bufferedReader = new BufferedReader(read);
			while ((readtemp = bufferedReader.readLine()) != null) {
			    if (readtemp.charAt(0)!='@') {
			    	 String [] SplitLine=readtemp.split("\t|\\s+");   
					 if(SplitLine[1].equals("4")){
						 unmappedSegments++;
					 }
					 else
					 {
				 		 if(readmark.size()==0){
				 			 readmark.add(SplitLine[0]);
				 			 overlaps.add(readtemp);
				 		 }
				 		 else
				 		 {
		 					 if(readmark.contains(SplitLine[0])){
		 						overlaps.add(readtemp);
		 					 }
		 					 else
		 					 {
		 						if(overlaps.size()>=2){
		 							multiAlignSegments++;
		 						}
		 						else
		 						{
		 							singleAlignSegments++;
		 						}
		 						totalFragments++;
		 						readmark.clear();
		 						overlaps.clear();
		 						readmark.add(SplitLine[0]);
		 						overlaps.add(readtemp);
		 					}
				 		 }
					 }
				}
			}
			bufferedReader.close();
            System.out.println("Total count:"+totalFragments+"\n"+"Align 0 times:"+unmappedSegments+" (" + df.format((double)unmappedSegments / (double)totalFragments* 100) + ")\n"+ "Align 1 times: " +singleAlignSegments+ " (" + df.format((double)singleAlignSegments / (double)totalFragments*100) + ")\n"+ "Align > 1 times: "+multiAlignSegments+" (" + df.format((double)multiAlignSegments/ (double)totalFragments* 100) + ")");
            FileWriter writer= new FileWriter(output,true);
            writer.write("File name: "+samfile+"\n");
            writer.write("Total count: "+totalFragments+"\n");
            writer.write("Align 0 times: "+unmappedSegments+" (" + df.format((double)unmappedSegments/(double)totalFragments* 100) + ")\n");
            writer.write("Align 1 times: " +singleAlignSegments+ " (" + df.format((double)singleAlignSegments/(double)totalFragments* 100) + ")\n");
            writer.write("Align > 1 times: " +multiAlignSegments+" (" + df.format((double)multiAlignSegments/(double)totalFragments* 100) + ")");
            writer.close();
		}
	}
	public static double aveCovEstimation(String mainPath, String LinearFilePath, String overlpFilePath, String C, String L, String E) throws IOException, Exception
	{
		double aveCovage=0;
    	if(!C.equals("")){
    		aveCovage=Double.parseDouble(C);
    	}
    	else if (!L.equals(""))
    	{
    		int fileLength=CommonClass.getFileLength(LinearFilePath);
    		aveCovage=(double)fileLength/Integer.parseInt(L);
    	}
    	else
    	{
    		String OverlapStr="";
    		int ReadLength=0;
    		int ReadCount=0;
    		double AverageCovOfRead=0;
    		File OverlapFilePath = new File(overlpFilePath);
    		ArrayList<String> readmark = new ArrayList<String>();
    		ArrayList<String> overlaps = new ArrayList<String>();
    		if (OverlapFilePath.isFile() && OverlapFilePath.exists()) {
    			   InputStreamReader DepthRead = new InputStreamReader(new FileInputStream(OverlapFilePath), "utf-8"); 
    			   BufferedReader bufferedReaderDepth = new BufferedReader(DepthRead);
    			   while ((OverlapStr=bufferedReaderDepth.readLine())!=null){
    				   if(OverlapStr.charAt(0)!='@'){
     					      String [] SplitLine=OverlapStr.split("\t|\\s+");
    	 				      if(readmark.size()==0){
    	 				    	  readmark.add(SplitLine[0]);
    	 				    	  overlaps.add(SplitLine[2]+"\t"+SplitLine[3]+"\t"+SplitLine[6]+"\t"+SplitLine[7]+"\t"+SplitLine[8]);
    	 				      }
    	 				      else
    	 				      {
    	 				    	  if(readmark.contains(SplitLine[0])){
    	 				    		 overlaps.add(SplitLine[2]+"\t"+SplitLine[3]+"\t"+SplitLine[6]+"\t"+SplitLine[7]+"\t"+SplitLine[8]);
    	 				    	  }
    	 				    	  else
    	 				          {
    	 				    		  if(overlaps.size()>=1){
        	 				    		  int overlapDepth=0;
            	 				    	  String [] ReadIdLine=readmark.get(0).split("_");
            	 				    	  ReadLength=Integer.parseInt(ReadIdLine[3]);
        	 	 					      int SavePositionArray[]=new int[ReadLength];
        	 	 					      for(int y=0;y<ReadLength;y++){
        	 	 						      SavePositionArray[y]=0;
        	 	 					      }
        	 	 					      for (int p=0;p<overlaps.size();p++){
        	 	 					    	  if(overlaps.get(p)!=null){
        		 	 					    	  String PosiString=overlaps.get(p).toString();
        		 	 					    	  String [] SplitUnit=PosiString.split("\t|\\s+");
        		 	 				              int StartPos1=Integer.parseInt(SplitUnit[0]);
        		 	 				              int EndPos1=Integer.parseInt(SplitUnit[1]);
        		 	 				              int LengthRead2=Integer.parseInt(SplitUnit[2]);
        		 	 				              int StartPos2=Integer.parseInt(SplitUnit[3]);
        		 	 				              int EndPos2=Integer.parseInt(SplitUnit[4]);
        		 	 				              if(E.equals("yes"))
        		 	 				              {
            		 	 				              if(((double)(Math.abs(StartPos2-EndPos2))/LengthRead2>=0.9)){
                		 	 				              for(int w=StartPos1;w<EndPos1;w++){
    	            		 	 				              SavePositionArray[w]+=1;
    	            		 	 				          }
            		 	 				              }
        		 	 				              }
        		 	 				              else
        		 	 				              {
            		 	 				              if(((double)(Math.abs(StartPos2-EndPos2))/LengthRead2>=0.2)){
                		 	 				              for(int w=StartPos1;w<EndPos1;w++){
    	            		 	 				              SavePositionArray[w]+=1;
    	            		 	 				          }
            		 	 				              }
        		 	 				              }
        	 	 					    	  }
        	 	 					      }
        	 	 						  for(int e=0;e<ReadLength;e++){
        	 	 							  if(SavePositionArray[e]>0){
        	 	 							      overlapDepth+=SavePositionArray[e];
        	 	 							  }
        	 	 						  }
        	 	 						  ReadCount++;
        	 	 						  AverageCovOfRead+=(double)overlapDepth/ReadLength;
    	 				    		  }
    	 	 						  readmark.clear();
    	 	 						  overlaps.clear();
    	 	 						  readmark.add(SplitLine[0]);
    								  overlaps.add(SplitLine[2]+"\t"+SplitLine[3]+"\t"+SplitLine[6]+"\t"+SplitLine[7]+"\t"+SplitLine[8]); 
    	 				    	  }
    	 				     }
    			       }
    		    }
    			bufferedReaderDepth.close();
    		}
    		aveCovage=AverageCovOfRead/ReadCount;
    	}
    	return aveCovage;
	}
	public static void calculateN50_N75_N90(String mainPath) throws IOException{
		String exchange="";
	    int ArraySize=CommonClass.getFileLines(mainPath +"/alignment/RepeatLib.fa")/2;
	    String FileArray[]=new String[ArraySize];
		CommonClass.FileToArray(mainPath+"/alignment/RepeatLib.fa",FileArray,">");
		for(int w=0;w<FileArray.length;w++){
			for(int g=w+1;g<FileArray.length;g++){
				if(FileArray[w].length()<FileArray[g].length()){
					exchange=FileArray[g];
					FileArray[g]=FileArray[w];
					FileArray[w]=exchange;
				}
			}
		}
		FileWriter writer= new FileWriter(mainPath+"/alignment/quast.txt",true);
		System.out.println("The Maximum segment length:"+FileArray[0].length());
		writer.write("The Maximum segment length:"+FileArray[FileArray.length-1].length()+"\n");
		System.out.println("The Minimum segment length:"+FileArray[FileArray.length-1].length());
		writer.write("The Minimum segment length:"+FileArray[FileArray.length-1].length()+"\n");
		int FullLength=CommonClass.getFileLength(mainPath+"/alignment/RepeatLib.fa");
		int TotalLength=0;
		for(int v=0;v<FileArray.length;v++){
			TotalLength+=FileArray[v].length();
			if((double)TotalLength/FullLength>=0.5){
				System.out.println("The value of N50:"+FileArray[v].length());
				writer.write("The value of N50:"+FileArray[v].length()+"\n");
				TotalLength=0;
				break;
			}
			
		}
		for(int v=0;v<FileArray.length;v++){
			TotalLength+=FileArray[v].length();
			if((double)TotalLength/FullLength>=0.75){
				System.out.println("The value of N75:"+FileArray[v].length());
				writer.write("The value of N75:"+FileArray[v].length()+"\n");
				TotalLength=0;
				break;
			}
			
		}
		for(int v=0;v<FileArray.length;v++){
			TotalLength+=FileArray[v].length();
			if((double)TotalLength/FullLength>=0.90){
				System.out.println("The value of N90:"+FileArray[v].length());
				writer.write("The value of N90:"+FileArray[v].length()+"\n");
				TotalLength=0;
				break;
			}
		}
	    writer.close();
    }
	public static void calculateMul_AlignRatio(String mainPath, String f, int t) throws IOException, InterruptedException{
		if(f.equals("")){
			Runtime hr_mapping2 = Runtime.getRuntime();
			Process phr_mapping2=null;
			String[] cmd_mapping2 = { "sh", "-c", mainPath+"/tools/minimap2 -a "+mainPath+"/alignment/ref_Align.mmi "+mainPath+"/alignment/RepeatLib.fa > "+mainPath+"/alignment/FinalAlignments.sam"};				      
			phr_mapping2=hr_mapping2.exec(cmd_mapping2);
			phr_mapping2.waitFor();
			CommonClass.multipleAlignmentRatios(mainPath+"/alignment/FinalAlignments.sam", mainPath+"/FinalRepeatLib/report1.txt");
		}
		else
		{
			Runtime b_index = Runtime.getRuntime();
			Process br_index=null;
			Runtime hr_mapping2 = Runtime.getRuntime();
			Process phr_mapping2=null;
			String[] br_Index = { "sh", "-c", mainPath+"/tools/minimap2 -d "+mainPath+"/alignment/ref_evalution.mmi "+f};
			br_index=b_index.exec(br_Index);
			br_index.waitFor();	
			String[] cmd_mapping2 = { "sh", "-c", mainPath+"/tools/minimap2 -a -N5000 -g50000 -w10 -k13 -m10 -r5000 -t"+t+" "+mainPath+"/alignment/ref_evalution.mmi "+mainPath+"/alignment/RepeatLib.fa > "+mainPath+"/alignment/FinalAlignments.sam"};				      
			phr_mapping2=hr_mapping2.exec(cmd_mapping2);
			phr_mapping2.waitFor();
			CommonClass.multipleAlignmentRatios(mainPath+"/alignment/FinalAlignments.sam", mainPath+"/FinalRepeatLib/report1.txt");
		}
    }
	public static void copy_files(String mainPath, String o) throws IOException, InterruptedException{
		File FinalRepeatFile=new File(mainPath+"/RepeatLib.fa");
		if(FinalRepeatFile.exists()){
			File OutPutDirectory =new File(o); 
			if(!OutPutDirectory.isDirectory()){
			    OutPutDirectory.mkdir();
		        CommonClass.copyFile(mainPath+"/RepeatLib.fa", o+"/RepeatLib.fa");
			}
			else
			{
				CommonClass.copyFile(mainPath+"/RepeatLib.fa", o+"/RepeatLib.fa");
			}
		}
	}
	public static void generation_HighCovRegions(String OverlapFile,String DepthFile,String OutputPath, int m) throws IOException, InterruptedException{	
		int LinesOfScaffs=CommonClass.getFileLines(OverlapFile)/2;
		String SaveChangeLineScaffolds[]=new String[LinesOfScaffs];
		CommonClass.FileToArray2(OverlapFile, SaveChangeLineScaffolds, ">");
		ArrayList<String> readmark = new ArrayList<String>();
		ArrayList<String> overlaps = new ArrayList<String>();
		ArrayList<String> HighCoverageRegions = new ArrayList<String>();
		int depth_Index=0;
		String OverlapStr="";
		File OverlapFilePath = new File(DepthFile);
		if (OverlapFilePath.isFile() && OverlapFilePath.exists()) {
			   InputStreamReader DepthRead = new InputStreamReader(new FileInputStream(OverlapFilePath), "utf-8"); 
			   BufferedReader bufferedReaderDepth = new BufferedReader(DepthRead);
			   while ((OverlapStr=bufferedReaderDepth.readLine())!=null){
 					  String [] SplitLine=OverlapStr.split("\t|\\s+");
 					  if(readmark.size()==0){
 						  readmark.add(SplitLine[0]);
 						  overlaps.add(OverlapStr);
 					  }
 					  else
 					  {
 						  if(readmark.contains(SplitLine[0])){
 							  overlaps.add(OverlapStr);
 						  }
 						  else
 						  {
 							  if(overlaps.size()>=m){
 								  int readID=0;
 				  	 			  String [] DepthLine=overlaps.get(0).split("\t|\\s+");
 				  	 			  String [] ReadIDLine=DepthLine[0].split("_");
 				  	 			  readID=Integer.parseInt(ReadIDLine[1]);
	 							  int CharArrayLength=SaveChangeLineScaffolds[readID].length();
	 							  char SavePositionArray2[]=new char[CharArrayLength];
	 							  for(int y=0;y<CharArrayLength;y++){
	 								  SavePositionArray2[y]='N';
	 					    	  }
	 	 					      for (int p=0;p<overlaps.size();p++) {
	 	 					    	  if(overlaps.get(p)!=null){
	 	 				  	 			   String [] PositionLine=overlaps.get(p).split("\t|\\s+");
	 	 				  	 			   int Position=Integer.parseInt(PositionLine[1]);
	 	 				  	 			   SavePositionArray2[Position-1]=SaveChangeLineScaffolds[readID].charAt(Position-1);
	 	 					    	  }
	 	 					      }
	 	 						  String ReplaceStr2=new String(SavePositionArray2);
	 	 						  String [] SplitScaffLine=ReplaceStr2.split("N");
	 	 						  for(int e=0;e<SplitScaffLine.length;e++){
	 	 						      if(SplitScaffLine[e].length()>=m){
	 	 							       HighCoverageRegions.add(SplitScaffLine[e]);
	 	 						      }
	 	 						  }
 						      }
	 						  readmark.clear();
	 						  overlaps.clear();
 						      readmark.add(SplitLine[0]);
 						      overlaps.add(OverlapStr);
 					      }
			          }
			   }
			   bufferedReaderDepth.close();
		}
		for (int p=0;p<HighCoverageRegions.size();p++) {
		    FileWriter writer1= new FileWriter(OutputPath,true);
		    writer1.write(">Node_"+(depth_Index++)+"_"+HighCoverageRegions.get(p).length()+"\n"+HighCoverageRegions.get(p)+"\n");
		    writer1.close();
		}
		readmark=null;
		overlaps=null;
		HighCoverageRegions=null;
		SaveChangeLineScaffolds=null;
	}
	public static void merging_relationships(String finalRepeatFile,String OutputPath) throws IOException, InterruptedException{
		String exchanges="";
		int LinesOfFragments=CommonClass.getFileLines(finalRepeatFile)/2;
		String SaveFragments[]=new String[LinesOfFragments];
		int num_fragments=CommonClass.FileToArray2(finalRepeatFile, SaveFragments, ">");
		for(int f=0;f<num_fragments;f++){
			for(int h=f+1;h<num_fragments;h++){
			   if(SaveFragments[f].length()<SaveFragments[h].length()){
				   exchanges=SaveFragments[f];
				   SaveFragments[f]=SaveFragments[h];
				   SaveFragments[h]=exchanges;
			   }	
			}
		}
		for(int j=0;j<num_fragments;j++){
			for(int g=j+1;g<num_fragments;g++){
				if(SaveFragments[j].contains(SaveFragments[g])||SaveFragments[j].equals(SaveFragments[g])){
					SaveFragments[g]="#"+SaveFragments[g];
				}
			}
		}
		int finalFragments=0;
		for(int k=0;k<num_fragments;k++){
			if(SaveFragments[k].charAt(0)!='#'){
		        FileWriter writer1= new FileWriter(OutputPath,true);
			    writer1.write(">NODE_"+(finalFragments++)+"_"+SaveFragments[k].length()+"\n"+SaveFragments[k]+"\n");
			    writer1.close();
			}
		}
		SaveFragments=null;
	}
	public static void mapping_HighCovRegions2Ref(String mainPath, int m, double R, String r, int t) throws IOException, InterruptedException{
		Runtime hr_mapping = Runtime.getRuntime();
		Process phr_mapping=null;
		int MultAlignCount=0;
		int SingleAlignCount=0;
		int NumCountHCR=0;
		int ReadFileLines=CommonClass.getFileLines(mainPath+"/alignment/HighCoverageRegions_"+m+".fa")/2;
		String SaveReadsArray[]=new String[ReadFileLines];
		int SizeOfReadArray=CommonClass.FileToArray2(mainPath+"/alignment/HighCoverageRegions_"+m+".fa",SaveReadsArray, ">");
		System.out.println(" HighCoverageRegionSize:"+SizeOfReadArray+" ]");
		try{		      
				String[] cmd_mapping = { "sh", "-c", mainPath+"/tools/minimap2 -a -t "+t+" "+mainPath+"/alignment/ref_Align.mmi "+mainPath+"/alignment/HighCoverageRegions_"+m+".fa > "+mainPath+"/alignment/HighCoverageRegions.sam"};
				phr_mapping=hr_mapping.exec(cmd_mapping);
			    phr_mapping.waitFor();
				int linesOfSamFile=CommonClass.getFileLines(mainPath+"/alignment/HighCoverageRegions.sam");
				String SaveSam_Array[]=new String[linesOfSamFile];
				int realSize_sam=CommonClass.SamFileToArray(mainPath+"/alignment/HighCoverageRegions.sam",SaveSam_Array);
				HashSet<Integer> MultipleAlignReadID_Set = new HashSet<Integer>();
				HashSet<Integer> SingleAlignReadID_Set = new HashSet<Integer>();
				Map<String, Integer> map = new HashMap<>();
			    for(int p=0;p<realSize_sam;p++){
				     String [] Splitp = SaveSam_Array[p].split("\t|\\s+");
					 if(!Splitp[1].equals("4")){
					     Integer num = map.get(Splitp[0]);  
						 map.put(Splitp[0], num == null ? 1 : num + 1);
					 }
				}
				Iterator<Entry<String, Integer>> iterator = map.entrySet().iterator();
				while (iterator.hasNext()) {
					 Entry<String, Integer> entry = iterator.next();
					 if(!entry.getValue().equals(null)){
						 if(r.equals("")){
							 if(entry.getValue()>=R){
									String [] SplitReadID = (entry.getKey()).split("_");
								    MultipleAlignReadID_Set.add(Integer.parseInt(SplitReadID[1]));
									FileWriter writer1= new FileWriter(mainPath+"/alignment/HighCoverageRegions.txt",true);
								    writer1.write(entry+"\n");
								    writer1.close();
						      }
							  else
						      {
									String [] SplitReadID = (entry.getKey()).split("_");
									SingleAlignReadID_Set.add(Integer.parseInt(SplitReadID[1]));
								    FileWriter writer1= new FileWriter(mainPath+"/alignment/UniqueMapHighCoverageRegions.fa",true);
									writer1.write(">NODE_"+(NumCountHCR++)+"_"+SaveReadsArray[Integer.parseInt(SplitReadID[1])].length()+"\n"+SaveReadsArray[Integer.parseInt(SplitReadID[1])]+"\n");
									writer1.close();
							  }
						 }
						 else
						 {
							 if(entry.getValue()>=2){
									String [] SplitReadID = (entry.getKey()).split("_");
								    MultipleAlignReadID_Set.add(Integer.parseInt(SplitReadID[1]));
									FileWriter writer1= new FileWriter(mainPath+"/alignment/HighCoverageRegions.txt",true);
								    writer1.write(entry+"\n");
								    writer1.close();
						      }
							  else
						      {
									String [] SplitReadID = (entry.getKey()).split("_");
									SingleAlignReadID_Set.add(Integer.parseInt(SplitReadID[1]));
								    FileWriter writer1= new FileWriter(mainPath+"/alignment/UniqueMapHighCoverageRegions.fa",true);
									writer1.write(">NODE_"+(NumCountHCR++)+"_"+SaveReadsArray[Integer.parseInt(SplitReadID[1])].length()+"\n"+SaveReadsArray[Integer.parseInt(SplitReadID[1])]+"\n");
									writer1.close();
							  }
						 }
					 }
				 }
				 SaveSam_Array=null;
				 map=null;
				 File MultAlignReadFiles=new File(mainPath+"/alignment/");
				 CommonClass.delSpecialFile(MultAlignReadFiles,"RepeatLib_orginal.fa",".fa");
				 Iterator<Integer> it2 = MultipleAlignReadID_Set.iterator();
				 while (it2.hasNext()) {
					 int Current_id=it2.next();
					 FileWriter writer1= new FileWriter(mainPath+"/alignment/RepeatLib_orginal.fa",true);
					 writer1.write(">NODE_"+(MultAlignCount++)+"_"+Current_id+"_"+SaveReadsArray[Current_id].length()+"\n"+SaveReadsArray[Current_id]+"\n");
					 writer1.close();
				 }
				 MultipleAlignReadID_Set=null;
				 File DeletedMutiAlignSamFile=new File(mainPath+"/alignment/HighCoverageRegions.sam");
				 if(DeletedMutiAlignSamFile.exists()){
				     CommonClass.deleteFile(DeletedMutiAlignSamFile);
				 }
				 Iterator<Integer> it3 = SingleAlignReadID_Set.iterator();
				 while (it3.hasNext()) {
					 int Current_id=it3.next();
					 FileWriter writer1= new FileWriter(mainPath+"/alignment/SingleAlignments.fa",true);
					 writer1.write(">NODE_"+(SingleAlignCount++)+"_"+Current_id+"_"+SaveReadsArray[Current_id].length()+"\n"+SaveReadsArray[Current_id]+"\n");
					 writer1.close();
				 }
				 Runtime b_index2 = Runtime.getRuntime();
				 Process br_index2=null;
				 Runtime hr_mapping2 = Runtime.getRuntime();
				 Process phr_mapping2=null;
				 String[] br_Index2 = { "sh", "-c", mainPath+"/tools/minimap2 -d "+mainPath+"/alignment/ref2.mmi "+mainPath+"/alignment/SingleAlignments.fa"};
				 br_index2=b_index2.exec(br_Index2);
				 br_index2.waitFor();
				 String[] cmd_mapping2 = { "sh", "-c", mainPath+"/tools/minimap2 -a "+mainPath+"/alignment/ref2.mmi "+mainPath+"/alignment/OverlapRegions.fa > "+mainPath+"/alignment/SingleAlignments.sam"};				      
				 phr_mapping2=hr_mapping2.exec(cmd_mapping2);
				 phr_mapping2.waitFor();
				 Runtime k_sam2bam  = Runtime.getRuntime();
				 Process pk_sam2bam=null;
			     String[] cmd_sam2bam = { "sh", "-c", mainPath+"/tools/samtools  view  -bS  "+mainPath+"/alignment/SingleAlignments.sam > "+mainPath+"/alignment/SingleAlignments.bam"};
			     pk_sam2bam=k_sam2bam.exec(cmd_sam2bam);
			     pk_sam2bam.waitFor();
				 Runtime k_bam2sort = Runtime.getRuntime();
				 Process pk_bam2sort=null;
			     String[] cmd_bam2sort = { "sh", "-c", mainPath+"/tools/samtools  sort  "+mainPath+"/alignment/SingleAlignments.bam > "+mainPath+"/alignment/SingleAlignments.sort.bam"};
			     pk_bam2sort=k_bam2sort.exec(cmd_bam2sort);
			     pk_bam2sort.waitFor();
				 Runtime k_sort2index = Runtime.getRuntime();
				 Process pk_sort2index=null;
			     String[] cmd_sort2index = { "sh", "-c", mainPath+"/tools/samtools  index  "+mainPath+"/alignment/SingleAlignments.sort.bam"};
			     pk_sort2index=k_sort2index.exec(cmd_sort2index);
			     pk_sort2index.waitFor();
				 Runtime k_depth2 = Runtime.getRuntime();
				 Process pk_depth2=null;
			     String[] cmd_depth2 = { "sh", "-c", mainPath+"/tools/samtools depth  "+mainPath+"/alignment/SingleAlignments.sort.bam > "+mainPath+"/alignment/SingleAlignments.depth"};
			     pk_depth2=k_depth2.exec(cmd_depth2);
			     pk_depth2.waitFor();
			     File bamfile_Path=new File(mainPath+"/alignment/");
			     CommonClass.delCommonFile(bamfile_Path, " ",".sam");
			     CommonClass.delCommonFile(bamfile_Path, " ",".bam");
			     CommonClass.delCommonFile(bamfile_Path, " ",".sort.bam");
				 int LinesOfSingleAligns=CommonClass.getFileLines(mainPath+"/alignment/SingleAlignments.fa")/2;
			     String SingleAlignmentArray[]=new String[LinesOfSingleAligns];
			     CommonClass.FileToArray2(mainPath+"/alignment/SingleAlignments.fa",SingleAlignmentArray, ">");
                 ArrayList<String> SingleId = new ArrayList<String>();
                 ArrayList<String> SinglePosition = new ArrayList<String>();
				 ArrayList<String> SingleAligns = new ArrayList<String>();
				 String SingleAlignStr="";
				 File SingleAlignFilePath = new File(mainPath+"/alignment/SingleAlignments.depth");
				 if (SingleAlignFilePath.isFile() && SingleAlignFilePath.exists()) {
					 InputStreamReader AlignRead = new InputStreamReader(new FileInputStream(SingleAlignFilePath), "utf-8"); 
					 BufferedReader bufferedReaderDepth = new BufferedReader(AlignRead);
				     while ((SingleAlignStr=bufferedReaderDepth.readLine())!=null){
			 			String [] SplitSingleAlignLine=SingleAlignStr.split("\t|\\s+");
			 		    if(SingleId.size()==0){
			 		    	SingleId.add(SplitSingleAlignLine[0]);
			 		    	SinglePosition.add(SingleAlignStr);
			 		    }
			 		    else
			 		    {
	 						if(SingleId.contains(SplitSingleAlignLine[0])){
	 							SinglePosition.add(SingleAlignStr);
	 						}
	 						else
	 						{
	 							if(SinglePosition.size()>=m){
	 								  int readID=0;
	 				  	 			  String [] DepthLine=SinglePosition.get(0).split("\t|\\s+");
	 				  	 			  String [] ReadIDLine=DepthLine[0].split("_");
	 				  	 			  readID=Integer.parseInt(ReadIDLine[1]);
 	 								  int CharArrayLength=SingleAlignmentArray[readID].length();
 	 								  char SavePositionArray2[]=new char[CharArrayLength];
 	 								  for(int y=0;y<CharArrayLength;y++){
 	 								      SavePositionArray2[y]='N';
 	 					    		  }
		 	 					      for (int p=0;p<SinglePosition.size();p++) {
		 	 					    	  if(SinglePosition.get(p)!=null){
		 	 				  	 			   String [] PositionLine=SinglePosition.get(p).split("\t|\\s+");
		 	 				  	 			   int Position=Integer.parseInt(PositionLine[1]);
		 	 				  	 			   int Coverage=Integer.parseInt(PositionLine[2]);
		 	 				  	 			   if(Coverage>=5){
		 	 				  	 			       SavePositionArray2[Position-1]=SingleAlignmentArray[readID].charAt(Position-1);
		 	 				  	 			   }
		 	 					    	  }
		 	 					      }
		 	 						  String ReplaceStr2=new String(SavePositionArray2);
		 	 						  String [] SplitScaffLine=ReplaceStr2.split("N");
		 	 						  for(int e=0;e<SplitScaffLine.length;e++){
		 	 						      if(SplitScaffLine[e].length()>=m){
		 	 						    	   SingleAligns.add(SplitScaffLine[e]);
		 	 						      }
		 	 						  }
		 	 						  SavePositionArray2=null;
	 						      }
	 							  SingleId.clear();
	 							  SinglePosition.clear();
 	 						      SingleId.add(SplitSingleAlignLine[0]);
 	 						      SinglePosition.add(SingleAlignStr);
	 						}
			 		     }
				     }  
				     bufferedReaderDepth.close();
				 }
				 for (int p=0;p<SingleAligns.size();p++) {
					 if(SingleAligns.get(p).length()>=m){
						 FileWriter writer1= new FileWriter(mainPath+"/alignment/RepeatLib_orginal.fa",true);
						 writer1.write(">Node_"+(MultAlignCount++)+"_"+SingleAligns.get(p).length()+"_Add"+"\n"+SingleAligns.get(p)+"\n");
						 writer1.close();
					 }
				 }
				 SingleId=null;
				 SinglePosition=null;
				 SingleAligns=null;
				 SingleAlignmentArray=null;    
		}
		catch (Exception e) {
			System.out.println("Error liaoxingyu");
			e.printStackTrace();
		}
		SaveReadsArray=null;
	}
    public static boolean isDigit2(String strNum) {  
        Pattern pattern = Pattern.compile("[0-9]{1,}");  
        Matcher matcher = pattern.matcher((CharSequence) strNum);  
        return matcher.matches();  
    }
}
