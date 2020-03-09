//package Program;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LongRepMarker {

	public static void main(String[] args) throws Exception {
		long startTime_main = System.currentTimeMillis();
		Runtime r_main = Runtime.getRuntime();
		long startMem_main = r_main.freeMemory();
		DecimalFormat df = new DecimalFormat("0.00");
		DecimalFormat df1 = new DecimalFormat("0.0000");
		/***************************************************
		 ***************************************************
		 ***************************************************
		 * 'k': The k-mer size.
		 * 'm': The minimum size of repeats. 
		 * 'l': The 10X linked read file. 
		 * 'q1': The left fastq file.
		 * 'q2': The right fastq file.
		 * 't': The number of threads.
		 * 'M': Evaluation the final repeat lib by multiple aligner(If the value is set to 'yes').
		 * 'Q': Evaluation the final repeat lib by quast(If the value is set to 'yes'). 
		 * 'r': The reference file. 
		 * 'E': Error correction. The value of E is set to 'yes', indicating that DLR performs error correction on long reads.  
		 * 'o': The path used to save the final RepeatLib.
		 * 'f': The reference file used for results evaluation.
		 * **************************************************
		 * **************************************************
		 * **************************************************
		 */
		int m = 5000;
		int t = 16;
		String l = "";
		String q1="";
		String q2="";
		String q3="";
		String q4="";
		String q5="";
		String q6="";
		String q7="";
		String q8="";
		String q9="";
		String q10="";
		int k = 49;
		String r = "";
		String a = "";
		String M = "";
		String Q = "";
		String E = "";
		String f = "";
		String v = "";
		String o = "";
		for (int i = 0; i < args.length; i += 2) {
			String headStr = args[i].substring(1, args[i].length());
			switch (headStr) {
			case "m": {
				m = Integer.parseInt(args[i + 1]);
			};
			    break;
			case "k": {
				k = Integer.parseInt(args[i + 1]);
			};
			    break;
			case "q1": {
				q1 = args[i + 1];
			};
			    break;  
			case "q2": {
				q2 = args[i + 1];
			};
			    break; 
			case "q3": {
				q3 = args[i + 1];
			};
			    break;  
			case "q4": {
				q4 = args[i + 1];
			};
			    break; 
			case "q5": {
				q5 = args[i + 1];
			};
			    break;  
			case "q6": {
				q6 = args[i + 1];
			};
			    break;   
			case "q7": {
				q7 = args[i + 1];
			};
			    break;  
			case "q8": {
				q8 = args[i + 1];
			};
			    break;  
			case "q9": {
				q9 = args[i + 1];
			};
			    break;  
			case "q10": {
				q10 = args[i + 1];
			};
			    break;      
			case "l": {
				l = args[i + 1];
			};
			    break;
			case "a": {
				a = args[i + 1];
			};
			    break;   
			case "M": {
				M = args[i + 1];
			};
			    break;
			case "Q": {
				Q = args[i + 1];
			};
			    break;
		    case "t": {
			    t = Integer.parseInt(args[i + 1]);
		    };
			    break;
			case "r": {
				r = args[i + 1];
			};
			    break;
			case "o": {
				o = args[i + 1];
			};
			    break;
			case "f": {
				f = args[i + 1];
			};
			    break;
			case "v": {
				f = args[i + 1];
			};
			    break;
			case "E": {
				E = args[i + 1];
			};
			    break;
			}
		}
		if (!r.equals("")||(!a.equals(""))||((!l.equals("")&&((!q1.equals("")&&!q2.equals(""))||(!q3.equals("")&&!q4.equals(""))||(!q5.equals("")&&!q6.equals(""))||(!q7.equals("")&&!q8.equals(""))||(!q9.equals("")&&!q10.equals("")))))) {
			System.out.println("-------------------------------------------------------------------------------------------");
			System.out.println("Copyright (C) 2019 Jianxin Wang(jxwang@mail.csu.edu.cn), Xingyu Liao(liaoxingyu@csu.edu.cn)" + "\n"+ "School of Computer Science and Engineering" + "\n" + "Central South University" + "\n"+ "ChangSha" + "CHINA, 410083" + "\n");
			File directory = new File(".");
			String ParentPath = directory.getCanonicalPath();
			//Delete.
			File readFileDirectoy = new File(ParentPath+"/readfile");
			if (readFileDirectoy.exists()) {
				CommonClass.delAllFile(readFileDirectoy);
			}
			File logFileDirectoy = new File(ParentPath+"/Log");
			if (logFileDirectoy.exists()) {
				CommonClass.delAllFile(logFileDirectoy);
			}
			File alignFileDirectoy = new File(ParentPath+"/alignment");
			if (alignFileDirectoy.exists()) {
				CommonClass.delAllFile(alignFileDirectoy);
			}
			File repeatFileDirectoy = new File(ParentPath+"/Repeats");
			if (repeatFileDirectoy.exists()) {
				CommonClass.delAllFile(repeatFileDirectoy);
			}
			File finalFileDirectoy = new File(ParentPath+"/FinalRepeatLib");
			if (finalFileDirectoy.exists()) {
				CommonClass.delAllFile(finalFileDirectoy);
			}
			//Make.
			File ReadFileDirectory = new File(ParentPath+"/readfile");
			if (!ReadFileDirectory.isDirectory()) {
				ReadFileDirectory.mkdir();
			}
			File LogFileDirectory = new File(ParentPath+"/Log");
			if (!LogFileDirectory.isDirectory()) {
				LogFileDirectory.mkdir();
			}
			File AlignFileDirectory = new File(ParentPath+"/alignment");
			if (!AlignFileDirectory.isDirectory()) {
				AlignFileDirectory.mkdir();
			}
			File RepeatFileDirectoy = new File(ParentPath+"/Repeats");
			if (!RepeatFileDirectoy.isDirectory()) {
				RepeatFileDirectoy.mkdir();
			}
			File FinalFileDirectory = new File(ParentPath+"/FinalRepeatLib");
			if (!FinalFileDirectory.isDirectory()) {
				FinalFileDirectory.mkdir();
			}
			//Start.
			System.out.println("Step1: Parameters configuration");
			String SchemaInfo = "de novo";
			if (!r.equals("")) {
				SchemaInfo = "reference";
			}
			System.out.println("===========================================================================================");
			System.out.println("  Operation Schema = [ " + SchemaInfo + " mode ]");
			System.out.println("  The Minimum Size of Repeat  = [ " + m + "bp ]");
			System.out.println("  The K-mer Size  = [ " + k + "bp ]");
			System.out.println("  Threads  = [ " + t + " ]");
			if (!r.equals("")||E.equals("") || E.equals("no")) {
				System.out.println("  Read error correction  = [  no ]");
			} else {
				System.out.println("  Read error correction  = [ yes ]");
			}
			if (v.equals("yes")) {
				System.out.println("  Variation identification = [ yes ]");
			}
			if (!a.equals("")||(!l.equals("")&&((!q1.equals("")&&!q2.equals(""))||(!q3.equals("")&&!q4.equals(""))||(!q5.equals("")&&!q6.equals(""))||(!q7.equals("")&&!q8.equals(""))||(!q9.equals("")&&!q10.equals(""))))){
				System.out.println("  Data Type  = [ NGS Short Reads + 10X Linked Reads]");
				if (!q1.equals("")) {
					System.out.println("  [Setting] 1-th fastq file Path = [ " + q1 + " ]");
				}
				if (!q2.equals("")) {
					System.out.println("  [Setting] 2-th fastq file Path = [ " + q2 + " ]");
				}
				if (!q3.equals("")) {
					System.out.println("  [Setting] 3-th fastq file Path = [ " + q3 + " ]");
				}
				if (!q4.equals("")) {
					System.out.println("  [Setting] 4-th fastq file Path = [ " + q4 + " ]");
				}
				if (!q5.equals("")) {
					System.out.println("  [Setting] 5-th fastq file Path = [ " + q5 + " ]");
				}
				if (!q6.equals("")) {
					System.out.println("  [Setting] 6-th fastq file Path = [ " + q6 + " ]");
				}
				if (!q7.equals("")) {
					System.out.println("  [Setting] 7-th fastq file Path = [ " + q7 + " ]");
				}
				if (!q8.equals("")) {
					System.out.println("  [Setting] 8-th fastq file Path = [ " + q8 + " ]");
				}
				if (!q9.equals("")) {
					System.out.println("  [Setting] 9-th fastq file Path = [ " + q9 + " ]");
				}
				if (!q10.equals("")) {
					System.out.println("  [Setting] 10-th fastq file Path = [ " + q10 + " ]");
				}
				if (!l.equals("")) {
					System.out.println("  [Setting] 10X Linked Reads Path = [ " + l + " ]");
				}
				if (!a.equals("")) {
					System.out.println("  [Setting] Assemblies Path = [ " + a + " ]");
				}
			}
			if (!(f.equals(""))) {
				System.out.println("  [Setting] The reference used for evalution  = [ "+f+" ]");
			}			
			if(M.equals("yes"))
			{
				System.out.println("  [Setting] Evalution the results by: [ Multiple-Sequence aligner (Minimap2) ]");
			}
			else
			{
				System.out.println("  [default] Evalution the results by: [ Not available ]");
			}
			if(Q.equals("yes"))
			{
				System.out.println("  [Setting] Evalution the results by: [ N50, N75, N90 ]");
			}
			else
			{
				System.out.println("  [default] Evalution the results by: [ Not available ]");
			}
			if (!r.equals("")) {
				System.out.println("  [Setting] Referece File Path = [ " + r + " ]");
			}
			if (!o.equals("")) {
				System.out.println("  [Setting] Output File Path = [ " + o + " ]");
			}
			if (o.equals("")) {
				o = ParentPath + "/FinalRepeatLib";
				System.out.println("  [default] Output File Path = [ " + o + " ]");
			}
			System.out.println("===========================================================================================");
			File ReadFiles = new File(ParentPath + "/readfile/read.fa");
			if (ReadFiles.exists()) {
				CommonClass.deleteFile(ReadFiles);
			}
			File ReadMergeFiles = new File(ParentPath + "/readfile/reads.fa");
			if (ReadMergeFiles.exists()) {
				CommonClass.deleteFile(ReadMergeFiles);
			}
			File ReadMergeQSFiles = new File(ParentPath + "/readfile/reads_qs.fa");
			if (ReadMergeQSFiles.exists()) {
				CommonClass.deleteFile(ReadMergeQSFiles);
			}
			File H5File = new File(ParentPath+"/OverlapRegions.h5");
			if (H5File.exists()) {
				CommonClass.deleteFile(H5File);
			}
			File dskFiles = new File(ParentPath+"/read.txt");
			if (dskFiles.exists()) {
				CommonClass.deleteFile(dskFiles);
			}
			File mmiFile1 = new File(ParentPath+"/alignment/ref_evalution.mmi");
			if (mmiFile1.exists()) {
				CommonClass.deleteFile(mmiFile1);
			}
			File mmiFile2 = new File(ParentPath+"/alignment/ref_Align.mmi");
			if (mmiFile2.exists()) {
				CommonClass.deleteFile(mmiFile2);
			}
			File mmiFile3 = new File(ParentPath+"/alignment/ref.mmi");
			if (mmiFile3.exists()) {
				CommonClass.deleteFile(mmiFile3);
			}
			File overlapFile = new File(ParentPath+"/alignment/ovlp.paf");
			if (overlapFile.exists()) {
				CommonClass.deleteFile(overlapFile);
			}
			if (!r.equals("")) {
				System.out.println("Step2: Loading reference genome");
				CommonClass.DelePathFiles(ParentPath+"/alignment/","LinearFile.fasta");
				CommonClass.MergeFastaMultiLines(ParentPath,r,ParentPath+"/alignment/Oringnal.fasta");
				CommonClass.RewriteFile(ParentPath+"/alignment/Oringnal.fasta",ParentPath+"/alignment/LinearFile.fasta",m);
				System.out.println("Step3: Finding overlap sequences between chromosomes");
				CommonClass.DelePathFiles(ParentPath+"/alignment/","ovlp.paf");
				CommonClass.DelePathFiles(ParentPath+"/alignment/","OverlapRegions.fa");
				Runtime r_overlap = Runtime.getRuntime();
				Process pr_overlap=null;
				String[] cmd_overlap = { "sh", "-c", ParentPath+"/tools/minimap2 -x ava-pb -g 3000 -w 10 -k 19 -m 100 -r 150 -t "+t+" "+ParentPath+"/alignment/LinearFile.fasta "+ParentPath+"/alignment/LinearFile.fasta > "+ParentPath+"/alignment/ovlp.paf"};
				pr_overlap=r_overlap.exec(cmd_overlap);
			    pr_overlap.waitFor();
				int LinesOfScaffs=CommonClass.getFileLines(ParentPath+"/alignment/LinearFile.fasta")/2;
				String SaveChangeLineScaffolds[]=new String[LinesOfScaffs];
				CommonClass.FileToArray2(ParentPath+"/alignment/LinearFile.fasta", SaveChangeLineScaffolds, ">");
				File OverlapFilePath = new File(ParentPath+"/alignment/ovlp.paf");
				ArrayList<String> readmark = new ArrayList<String>();
				ArrayList<String> overlaps = new ArrayList<String>();
				ArrayList<String> HighCoverageRegions = new ArrayList<String>();
				String OverlapStr="";
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
			 					   int readID=0;
			 				  	   String [] DepthLine=overlaps.get(0).split("\t|\\s+");
			 				  	   String [] ReadIDLine=DepthLine[0].split("_");
			 				  	   readID=Integer.parseInt(ReadIDLine[1]);
				 				   int CharArrayLength=SaveChangeLineScaffolds[readID].length();
				 				   char SavePositionArray1[]=new char[CharArrayLength];
				 				   for(int y=0;y<CharArrayLength;y++){
				 					   SavePositionArray1[y]='N';
				 				   }
				 	 			   for (int p=0;p<overlaps.size();p++) {
				 	 				   if(overlaps.get(p)!=null){
				 	 				  	   String [] PositionLine=overlaps.get(p).split("\t|\\s+");
				 	 				  	   int checkQuality=Integer.parseInt(PositionLine[11]);
				 	 				  	   if(checkQuality>=0){
					 	 				  	   int Start_Position=Integer.parseInt(PositionLine[2]);
					 	 				  	   int End_Position=Integer.parseInt(PositionLine[3]);
						 	 				   for(int s=Start_Position;s<End_Position;s++){
						 	 				  	  SavePositionArray1[s]=SaveChangeLineScaffolds[readID].charAt(s);
					 	 				  	   }
				 	 					   }
				 	 				   }
				 	 			   }
				 	 			   String ReplaceStr1=new String(SavePositionArray1);
				 	 			   String [] SplitScaffLine1=ReplaceStr1.split("N");   
				 	 			   for(int e=0;e<SplitScaffLine1.length;e++){
				 	 				   if(SplitScaffLine1[e].length()>=m){
				 	 					   HighCoverageRegions.add(SplitScaffLine1[e]);
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
			    int	overlap_Index=0;
				for (int p=0;p<HighCoverageRegions.size();p++) {
					  FileWriter writer1= new FileWriter(ParentPath+"/alignment/OverlapRegions.fa",true);
					  writer1.write(">Node_"+(overlap_Index++)+"_"+HighCoverageRegions.get(p).length()+"\n"+HighCoverageRegions.get(p)+"\n");
					  writer1.close();
			    }
				readmark=null;
				overlaps=null;
				SaveChangeLineScaffolds=null;
				HighCoverageRegions=null;			    
				System.out.println("Step4: Conversion of the chromosomes to unique k-mers");
				CommonClass.DelePathFiles(ParentPath,"OverlapRegions.h5");
				CommonClass.DelePathFiles(ParentPath+"/readfile/","KmerFile.fa");
				CommonClass.DelePathFiles(ParentPath,"read.txt");
				Process p_dsk1=null;
				Runtime r_dsk1=Runtime.getRuntime();
				try{
				      String cmd1=ParentPath+"/tools/dsk -file "+ParentPath+"/alignment/OverlapRegions.fa -kmer-size "+k+" -abundance-min 1";
				      p_dsk1=r_dsk1.exec(cmd1);
					  p_dsk1.waitFor();
				}
				catch(Exception e){
				      System.out.println("Step4 Error:"+e.getMessage());
				      e.printStackTrace();
				}
				Process p_dsk2=null;
				Runtime r_dsk2=Runtime.getRuntime();
				try{
					  String cmd2=ParentPath+"/tools/dsk2ascii -file OverlapRegions.h5 -out read.txt";
				      p_dsk2=r_dsk2.exec(cmd2);
					  p_dsk2.waitFor();
				}
				catch(Exception e){
				      System.out.println("Step4 Error:"+e.getMessage());
				      e.printStackTrace();
				}
				int KmerNum=0;
				String ReadTemp="";
				File Dskfile = new File(ParentPath+"/read.txt");
				RandomAccessFile aFile = new RandomAccessFile(ParentPath+"/readfile/KmerFile.fa","rw");
				FileChannel inChannel = aFile.getChannel();
				if (Dskfile.isFile() && Dskfile.exists()) {
					InputStreamReader read = new InputStreamReader(new FileInputStream(Dskfile), "utf-8");
				    BufferedReader bufferedReaderScaff = new BufferedReader(read);
				    while ((ReadTemp=bufferedReaderScaff.readLine())!= null) {
					    String [] SplitLine = ReadTemp.split("\t|\\s+");
					    String WriteContents = ">Node_"+(KmerNum++)+"\n"+SplitLine[0]+"\n";
						ByteBuffer buf = ByteBuffer.allocate(5000);
						buf.clear();
						buf.put(WriteContents.getBytes());
						buf.flip();
						while(buf.hasRemaining()){
							inChannel.write(buf);
						}
				    }
					bufferedReaderScaff.close();
					inChannel.close();
				}
				aFile.close();
				System.out.println("Step5: Mapping unique k-mers to reference genome");
				int SplitSize=0;
				int RealSize_Fasta=(CommonClass.getFileLines(ParentPath+"/alignment/OverlapRegions.fa"))/2;
                SplitSize=RealSize_Fasta/t;
				Process p_Split=null;
				Runtime r_Split=Runtime.getRuntime();
				String[] cmd_Split = { "sh", "-c", "split -l  "+2*SplitSize+" "+ParentPath+"/alignment/OverlapRegions.fa -d -a 4 "+ParentPath+"/readfile/read_"};
				p_Split=r_Split.exec(cmd_Split);
				p_Split.waitFor();
			    int SplitFileIndex=0;
			    File FilePath = new File(ParentPath+"/readfile/");
		      	if (!FilePath.exists()||!FilePath.isDirectory()) {
		      	    System.out.println("Path:"+ParentPath+"/readfile is empty");
		      	}
		      	else
		      	{
		      	    String[] tmpList=FilePath.list();
		      	    if (tmpList!= null) {
		      		   for (String aTempList : tmpList) {
		      			  File tmpFile = new File(FilePath, aTempList);
		      			  if (tmpFile.isFile() && tmpFile.getName().startsWith("read_")) {
		      				  String ID_change="read_"+(SplitFileIndex++);
		      				  File ReNameFile = new File(ParentPath+"/readfile/"+ID_change);
		      				  tmpFile.renameTo(ReNameFile);
		      				  tmpFile.delete();
		      			  }
		      		   }
		      	    }
		        }
		      	int Threads=SplitFileIndex;
		      	ExecutorService pool_1 = Executors.newFixedThreadPool(Threads);
		      	for(int s=0;s<SplitFileIndex;s++)
		      	{
		      		if(s!=Threads-1){
		      		    Threads_Ref mt = new Threads_Ref(s,ParentPath,ParentPath+"/readfile/read_"+s,ParentPath+"/readfile/KmerFile.fa",t);
		      			pool_1.execute(mt);
		      		}
		      		else
		      		{
		      			Threads_Ref mt = new Threads_Ref(s,ParentPath,ParentPath+"/readfile/read_"+s,ParentPath+"/readfile/KmerFile.fa",t);
		      			pool_1.execute(mt);
		      		}
		      	}
		      	pool_1.shutdown();
		      	pool_1.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		      	String MergeFileString=" ";
		      	File CoverageFilePath = new File(ParentPath+"/alignment/");
		      	String[] tmpList3=CoverageFilePath.list();
		      	if (tmpList3!= null) {
		      		for (String aTempList : tmpList3) {
		      			File tmpFile = new File(CoverageFilePath, aTempList);
		      			if (tmpFile.isFile() && (tmpFile.getName().startsWith("read_ms_")&&(tmpFile.getName().endsWith(".sort.bam")))) {
		      				MergeFileString+=" "+ParentPath+"/alignment/"+tmpFile.getName();
		      			} 		
		      		}
		      	}
			    Runtime b_bamMerge = Runtime.getRuntime();
			    Process br_bamMerge=null;
			    String[] cmd_bamMerge = { "sh", "-c", ParentPath+"/tools/samtools merge "+ParentPath+"/alignment/Merge.sort.bam "+MergeFileString};
			    br_bamMerge=b_bamMerge.exec(cmd_bamMerge);
			    br_bamMerge.waitFor();
				Runtime k_depth = Runtime.getRuntime();
				Process pk_depth=null;
		    	String[] cmd_depth = { "sh", "-c", ParentPath+"/tools/samtools depth  "+ParentPath+"/alignment/Merge.sort.bam > "+ParentPath+"/alignment/Merge.depth"};
		    	pk_depth=k_depth.exec(cmd_depth);
		    	pk_depth.waitFor();
		    	File bamfile_Path = new File(ParentPath+"/alignment/");
		    	CommonClass.delSpecialFile(bamfile_Path, "read_",".bam");
		    	CommonClass.delSpecialFile(bamfile_Path, "read_", ".sort.bam");
		        System.out.println("Step6: Generation of the high coverage regions");
				CommonClass.generation_HighCovRegions(ParentPath+"/alignment/OverlapRegions.fa",ParentPath+"/alignment/Merge.depth",ParentPath+"/alignment/HighCoverageRegions_"+m+".fa",m);
				CommonClass.DelePathFiles(ParentPath+"/alignment/","ref_Align.mmi");
				Runtime pR_index = Runtime.getRuntime();
				Process R_index=null;
				CommonClass.DelePathFiles(ParentPath+"/alignment/","ref_Align.mmi");
				String[] cmd_pr_Index = { "sh", "-c", ParentPath+"/tools/minimap2 -d "+ParentPath+"/alignment/ref_Align.mmi "+ParentPath+"/alignment/OverlapRegions.fa"};
				R_index=pR_index.exec(cmd_pr_Index);
				R_index.waitFor();
				System.out.print("Step7: Mapping high coverage regions to overlap sequences [");
				CommonClass.mapping_HighCovRegions2Ref(ParentPath, m, 2, r, t);
				System.out.println("Step7: Merging fragments that have duplicate or include relationships");
				CommonClass.merging_relationships(ParentPath+"/alignment/RepeatLib_orginal.fa",ParentPath+"/alignment/RepeatLib.fa");
				System.out.println("Step8: Generation of the final repeat library");
				CommonClass.copy_files(ParentPath+"/alignment",o);
		    }
		    else
		    {
				Process p_ErrorCorrection=null;
				Runtime r_ErrorCorrection=Runtime.getRuntime();
				Process p_assemblyRun=null;
				Runtime r_assemblyRun=Runtime.getRuntime();
			    try{
			    	  System.out.print("Step2: Error correction [ ");
			  		  String [] SplitLine1=q1.split("/|\n");
					  String full_name1=SplitLine1[SplitLine1.length-1];
					  String last_name1=full_name1.substring(0,full_name1.length());
			    	  String f1=ParentPath+"/readfile/karect_"+last_name1;
			  		  String [] SplitLine2=q2.split("/|\n");
					  String full_name2=SplitLine2[SplitLine2.length-1];
					  String last_name2=full_name2.substring(0,full_name2.length());	
			    	  String f2=ParentPath+"/readfile/karect_"+last_name2;
			  		  String [] SplitLine3=q3.split("/|\n");
					  String full_name3=SplitLine3[SplitLine3.length-1];
					  String last_name3=full_name3.substring(0,full_name3.length());	
			    	  String f3=ParentPath+"/readfile/karect_"+last_name3;
			  		  String [] SplitLine4=q4.split("/|\n");
					  String full_name4=SplitLine4[SplitLine4.length-1];
					  String last_name4=full_name4.substring(0,full_name4.length());	
			    	  String f4=ParentPath+"/readfile/karect_"+last_name4;
			  		  String [] SplitLine5=q5.split("/|\n");
					  String full_name5=SplitLine5[SplitLine5.length-1];
					  String last_name5=full_name5.substring(0,full_name5.length());	
			    	  String f5=ParentPath+"/readfile/karect_"+last_name5;
			  		  String [] SplitLine6=q6.split("/|\n");
					  String full_name6=SplitLine6[SplitLine6.length-1];
					  String last_name6=full_name6.substring(0,full_name6.length());	
			    	  String f6=ParentPath+"/readfile/karect_"+last_name6;
			  		  String [] SplitLine7=q7.split("/|\n");
					  String full_name7=SplitLine7[SplitLine7.length-1];
					  String last_name7=full_name7.substring(0,full_name7.length());	
			    	  String f7=ParentPath+"/readfile/karect_"+last_name7;
			  		  String [] SplitLine8=q8.split("/|\n");
					  String full_name8=SplitLine8[SplitLine8.length-1];
					  String last_name8=full_name8.substring(0,full_name8.length());	
			    	  String f8=ParentPath+"/readfile/karect_"+last_name8;
			  		  String [] SplitLine9=q9.split("/|\n");
					  String full_name9=SplitLine9[SplitLine9.length-1];
					  String last_name9=full_name9.substring(0,full_name9.length());	
			    	  String f9=ParentPath+"/readfile/karect_"+last_name9;
			  		  String [] SplitLine10=q10.split("/|\n");
					  String full_name10=SplitLine10[SplitLine10.length-1];
					  String last_name10=full_name10.substring(0,full_name10.length());	
			    	  String f10=ParentPath+"/readfile/karect_"+last_name10;
			    	  if(a.equals("")&&(!E.equals("") || E.equals("yes"))){
				    	  if((!q1.equals(""))&&(!q2.equals(""))){
							  System.out.print("lib:1-2 | ");
					    	  String[] cmd_errorCorrection = { "sh", "-c", ParentPath+"/tools/karect -correct -threads=128 -matchtype=hamming -celltype=haploid -tempdir="+ParentPath+"/readfile/ -resultdir="+ParentPath+"/readfile/ -inputfile="+q1+" -inputfile="+q2+" > "+ParentPath+"/readfile/read1.log"};
					    	  p_ErrorCorrection=r_ErrorCorrection.exec(cmd_errorCorrection);
					    	  p_ErrorCorrection.waitFor();
				    	  }
				    	  if((!q3.equals(""))&&(!q4.equals(""))){
							  System.out.print("lib:3-4 | ");
					    	  String[] cmd_errorCorrection = { "sh", "-c", ParentPath+"/tools/karect -correct -threads=128 -matchtype=hamming -celltype=haploid -tempdir="+ParentPath+"/readfile/ -resultdir="+ParentPath+"/readfile/ -inputfile="+q3+" -inputfile="+q4+" > "+ParentPath+"/readfile/read2.log"};
					    	  p_ErrorCorrection=r_ErrorCorrection.exec(cmd_errorCorrection);
					    	  p_ErrorCorrection.waitFor();
				    	  }
				    	  if((!q5.equals(""))&&(!q6.equals(""))){
							  System.out.print("lib:5-6 | ");
					    	  String[] cmd_errorCorrection = { "sh", "-c", ParentPath+"/tools/karect -correct -threads=128 -matchtype=hamming -celltype=haploid -tempdir="+ParentPath+"/readfile/ -resultdir="+ParentPath+"/readfile/ -inputfile="+q5+" -inputfile="+q6+" > "+ParentPath+"/readfile/read3.log"};
					    	  p_ErrorCorrection=r_ErrorCorrection.exec(cmd_errorCorrection);
					    	  p_ErrorCorrection.waitFor();
				    	  }
				    	  if((!q7.equals(""))&&(!q8.equals(""))){
							  System.out.print("lib:7-8 | ");
							  String[] cmd_errorCorrection = { "sh", "-c", ParentPath+"/tools/karect -correct -threads=128 -matchtype=hamming -celltype=haploid -tempdir="+ParentPath+"/readfile/ -resultdir="+ParentPath+"/readfile/ -inputfile="+q7+" -inputfile="+q8+" > "+ParentPath+"/readfile/read4.log"};
					    	  p_ErrorCorrection=r_ErrorCorrection.exec(cmd_errorCorrection);
					    	  p_ErrorCorrection.waitFor();
				    	  }
				    	  if((!q9.equals(""))&&(!q10.equals(""))){
							  System.out.print("lib:9-10 | ");
							  String[] cmd_errorCorrection = { "sh", "-c", ParentPath+"/tools/karect -correct -threads=128 -matchtype=hamming -celltype=haploid -tempdir="+ParentPath+"/readfile/ -resultdir="+ParentPath+"/readfile/ -inputfile="+q9+" -inputfile="+q10+" > "+ParentPath+"/readfile/read5.log"};
					    	  p_ErrorCorrection=r_ErrorCorrection.exec(cmd_errorCorrection);
					    	  p_ErrorCorrection.waitFor();
				    	  }
				    	  System.out.println("]");
						  CommonClass.DelePathFiles(ParentPath+"/readfile/","res_graph_b.txt");
						  CommonClass.DelePathFiles(ParentPath+"/readfile/","res_graph_a.txt");
						  CommonClass.DelePathFiles(ParentPath+"/readfile/","input_file.txt");
						  CommonClass.DelePathFiles(ParentPath+"/readfile/","temp_res_"+last_name1);
						  CommonClass.DelePathFiles(ParentPath+"/readfile/","temp_res_"+last_name2);
						  CommonClass.DelePathFiles(ParentPath+"/readfile/","temp_res_"+last_name3);
						  CommonClass.DelePathFiles(ParentPath+"/readfile/","temp_res_"+last_name4);
						  CommonClass.DelePathFiles(ParentPath+"/readfile/","temp_res_"+last_name5);
						  CommonClass.DelePathFiles(ParentPath+"/readfile/","temp_res_"+last_name6);
						  CommonClass.DelePathFiles(ParentPath+"/readfile/","temp_res_"+last_name7);
						  CommonClass.DelePathFiles(ParentPath+"/readfile/","temp_res_"+last_name8);
						  CommonClass.DelePathFiles(ParentPath+"/readfile/","temp_res_"+last_name9);
						  CommonClass.DelePathFiles(ParentPath+"/readfile/","temp_res_"+last_name10);
			    	  }
			    	  else
			    	  {
			    		  System.out.print("no | ");
				    	  if(!q1.equals("")&&!q2.equals(""))
				    	  {
				    		  CommonClass.copy_files(q1,f1);
				    		  CommonClass.copy_files(q2,f2);
				    	  }
				    	  if(!q3.equals("")&&!q4.equals(""))
				    	  {
				    		  CommonClass.copy_files(q3,f3);
				    		  CommonClass.copy_files(q4,f4);
				    	  }
				    	  if(!q5.equals("")&&!q6.equals(""))
				    	  {
				    		  CommonClass.copy_files(q5,f6);
				    		  CommonClass.copy_files(q6,f6);
				    	  }
				    	  if(!q7.equals("")&&!q8.equals(""))
				    	  {
				    		  CommonClass.copy_files(q7,f7);
				    		  CommonClass.copy_files(q8,f8);
				    	  }
				    	  if(!q9.equals("")&&!q10.equals(""))
				    	  {
				    		  CommonClass.copy_files(q9,f9);
				    		  CommonClass.copy_files(q10,f10);
				    	  }
				    	  if(!a.equals(""))
				    	  {
							  System.out.println("The error correction process has been skipped ]");
				    		  CommonClass.copyFile(a,ParentPath+"/readfile/scaffolds.fasta");
				    	  }
			    	  }
			    	  System.out.print("Step3: de novo assembly [ ");
			    	  File Q1 = new File(f1);
			    	  File Q2 = new File(f3);
			    	  File Q3 = new File(f5);
			    	  File Q4 = new File(f7);
			    	  File Q5 = new File(f9);
			    	  if(a.equals(""))
			    	  {
				    	  if(Q1.exists()&&!Q2.exists())
				    	  {
							  System.out.println("Assembly:1-2 ]");
					    	  String[] cmd_assemblyRun = { "sh", "-c", ParentPath+"/tools/SPAdes-3.10.1-Linux/bin/spades.py --only-assembler -t 64 --pe1-1 "+f1+" --pe1-2 "+f2+" -s "+l+"-o "+ParentPath+"/readfile/ > "+ParentPath+"/Log/SPAdeslog.out" };
							  p_assemblyRun=r_assemblyRun.exec(cmd_assemblyRun);
							  p_assemblyRun.waitFor();
				    	  }
				    	  if(Q1.exists()&&Q2.exists()&&!Q3.exists())
				    	  {
							  System.out.println("Assembly:1-4 ]");
					    	  String[] cmd_assemblyRun = { "sh", "-c", ParentPath+"/tools/SPAdes-3.10.1-Linux/bin/spades.py --only-assembler -t 64 --pe1-1 "+f1+" --pe1-2 "+f2+" --mp1-1 "+f3+" --mp1-2 "+f4+" -s "+l+" -o "+ParentPath+"/readfile/ > "+ParentPath+"/Log/SPAdeslog.out" };
					    	  p_assemblyRun=r_assemblyRun.exec(cmd_assemblyRun);
							  p_assemblyRun.waitFor();
				    	  }
				    	  if(Q1.exists()&&Q2.exists()&&Q3.exists()&&!Q4.exists())
				    	  {
							  System.out.println("Assembly:1-6 ]");
					    	  String[] cmd_assemblyRun = { "sh", "-c", ParentPath+"/tools/SPAdes-3.10.1-Linux/bin/spades.py --only-assembler -t 64 --pe1-1 "+f1+" --pe1-2 "+f2+" --mp1-1 "+f3+" --mp1-2 "+f4+" --mp2-1 "+f5+" --mp2-2 "+f6+" -s "+l+" -o "+ParentPath+"/readfile/ > "+ParentPath+"/Log/SPAdeslog.out" };
					    	  p_assemblyRun=r_assemblyRun.exec(cmd_assemblyRun);
							  p_assemblyRun.waitFor();
				    	  }
				    	  if(Q1.exists()&&Q2.exists()&&Q3.exists()&&Q4.exists()&&!Q5.exists())
				    	  {
							  System.out.println("Assembly:1-8 ]");
					    	  String[] cmd_assemblyRun = { "sh", "-c", ParentPath+"/tools/SPAdes-3.10.1-Linux/bin/spades.py --only-assembler -t 64 --pe1-1 "+f1+" --pe1-2 "+f2+" --mp1-1 "+f3+" --mp1-2 "+f4+" --mp2-1 "+f5+" --mp2-2 "+f6+" --mp3-1 "+f7+" --mp3-2 "+f8+" -s "+l+" -o "+ParentPath+"/readfile/ > "+ParentPath+"/Log/SPAdeslog.out" };
					    	  p_assemblyRun=r_assemblyRun.exec(cmd_assemblyRun);
							  p_assemblyRun.waitFor();
				    	  }
				    	  if(Q1.exists()&&Q2.exists()&&Q3.exists()&&Q4.exists()&&Q5.exists())
				    	  {
							  System.out.println("Assembly:1-10 ]");
					    	  String[] cmd_assemblyRun = { "sh", "-c", ParentPath+"/tools/SPAdes-3.10.1-Linux/bin/spades.py --only-assembler -t 64 --pe1-1 "+f1+" --pe1-2 "+f2+" --mp1-1 "+f3+" --mp1-2 "+f4+" --mp2-1 "+f5+" --mp2-2 "+f6+" --mp3-1 "+f7+" --mp3-2 "+f8+" --mp4-1 "+f9+" --mp4-2 "+f10+" -s "+l+" -o "+ParentPath+"/readfile/ > "+ParentPath+"/Log/SPAdeslog.out" };
					    	  p_assemblyRun=r_assemblyRun.exec(cmd_assemblyRun);
							  p_assemblyRun.waitFor();
				    	  }
			    	  }
			    	  else
			    	  {
			    		  System.out.println(" no | The assembly process has been skipped and the assemblies are now loaded ]");
			    	  }
			    }
			    catch(Exception e){
			    	  System.out.println("Step03 Error:"+e.getMessage());
			    	  e.printStackTrace();
			    }
				CommonClass.MergeFastaMultiLines(ParentPath,ParentPath+"/readfile/scaffolds.fasta",ParentPath+"/alignment/Oringnal.fasta");
				CommonClass.RewriteFile(ParentPath+"/alignment/Oringnal.fasta",ParentPath+"/alignment/LinearFile.fasta",m);
				System.out.println("Step4: Finding overlap sequences between chromosomes");
				CommonClass.DelePathFiles(ParentPath+"/alignment/","ovlp.paf");
				CommonClass.DelePathFiles(ParentPath+"/alignment/","OverlapRegions.fa");
				Runtime r_overlap = Runtime.getRuntime();
				Process pr_overlap=null;
				String[] cmd_overlap = { "sh", "-c", ParentPath+"/tools/minimap2 -x ava-pb -t "+t+" "+ParentPath+"/alignment/LinearFile.fasta "+ParentPath+"/alignment/LinearFile.fasta > "+ParentPath+"/alignment/ovlp.paf"};
				pr_overlap=r_overlap.exec(cmd_overlap);
			    pr_overlap.waitFor();
				int LinesOfScaffs=CommonClass.getFileLines(ParentPath+"/alignment/LinearFile.fasta")/2;
				String SaveChangeLineScaffolds[]=new String[LinesOfScaffs];
				CommonClass.FileToArray2(ParentPath+"/alignment/LinearFile.fasta", SaveChangeLineScaffolds, ">");
				File OverlapFilePath = new File(ParentPath+"/alignment/ovlp.paf");
				ArrayList<String> readmark = new ArrayList<String>();
				ArrayList<String> overlaps = new ArrayList<String>();
				ArrayList<String> HighCoverageRegions = new ArrayList<String>();
				String OverlapStr="";
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
			 					   int readID=0;
			 				  	   String [] DepthLine=overlaps.get(0).split("\t|\\s+");
			 				  	   String [] ReadIDLine=DepthLine[0].split("_");
			 				  	   readID=Integer.parseInt(ReadIDLine[1]);
				 				   int CharArrayLength=SaveChangeLineScaffolds[readID].length();
				 				   char SavePositionArray1[]=new char[CharArrayLength];
				 				   for(int y=0;y<CharArrayLength;y++){
				 					   SavePositionArray1[y]='N';
				 				   }
				 	 			   for (int p=0;p<overlaps.size();p++) {
				 	 				   if(overlaps.get(p)!=null){
				 	 				  	   String [] PositionLine=overlaps.get(p).split("\t|\\s+");
				 	 				  	   int checkQuality=Integer.parseInt(PositionLine[11]);
				 	 				  	   if(checkQuality>=0){
					 	 				  	   int Start_Position=Integer.parseInt(PositionLine[2]);
					 	 				  	   int End_Position=Integer.parseInt(PositionLine[3]);
						 	 				   for(int s=Start_Position;s<End_Position;s++){
						 	 				  	  SavePositionArray1[s]=SaveChangeLineScaffolds[readID].charAt(s);
					 	 				  	   }
				 	 					   }
				 	 				   }
				 	 			   }
				 	 			   String ReplaceStr1=new String(SavePositionArray1);
				 	 			   String [] SplitScaffLine1=ReplaceStr1.split("N");   
				 	 			   for(int e=0;e<SplitScaffLine1.length;e++){
				 	 				   if(SplitScaffLine1[e].length()>=m){
				 	 					   HighCoverageRegions.add(SplitScaffLine1[e]);
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
			    int	overlap_Index=0;
				for (int p=0;p<HighCoverageRegions.size();p++) {
					  FileWriter writer1= new FileWriter(ParentPath+"/alignment/OverlapRegions.fa",true);
					  writer1.write(">Node_"+(overlap_Index++)+"_"+HighCoverageRegions.get(p).length()+"\n"+HighCoverageRegions.get(p)+"\n");
					  writer1.close();
			    }
				readmark=null;
				overlaps=null;
				SaveChangeLineScaffolds=null;
				HighCoverageRegions=null;			    
				System.out.println("Step5: Conversion of the chromosomes to unique k-mers");
				CommonClass.DelePathFiles(ParentPath,"OverlapRegions.h5");
				CommonClass.DelePathFiles(ParentPath+"/readfile/","KmerFile.fa");
				CommonClass.DelePathFiles(ParentPath,"read.txt");
				Process p_dsk1=null;
				Runtime r_dsk1=Runtime.getRuntime();
				try{
				      String cmd1=ParentPath+"/tools/dsk -file "+ParentPath+"/alignment/OverlapRegions.fa -kmer-size "+k+" -abundance-min 1";
				      p_dsk1=r_dsk1.exec(cmd1);
					  p_dsk1.waitFor();
				}
				catch(Exception e){
				      System.out.println("Step4 Error:"+e.getMessage());
				      e.printStackTrace();
				}
				Process p_dsk2=null;
				Runtime r_dsk2=Runtime.getRuntime();
				try{
					  String cmd2=ParentPath+"/tools/dsk2ascii -file OverlapRegions.h5 -out read.txt";
				      p_dsk2=r_dsk2.exec(cmd2);
					  p_dsk2.waitFor();
				}
				catch(Exception e){
				      System.out.println("Step4 Error:"+e.getMessage());
				      e.printStackTrace();
				}
				int KmerNum=0;
				String ReadTemp="";
				File Dskfile = new File(ParentPath+"/read.txt");
				RandomAccessFile aFile = new RandomAccessFile(ParentPath+"/readfile/KmerFile.fa","rw");
				FileChannel inChannel = aFile.getChannel();
				if (Dskfile.isFile() && Dskfile.exists()) {
					InputStreamReader read = new InputStreamReader(new FileInputStream(Dskfile), "utf-8");
				    BufferedReader bufferedReaderScaff = new BufferedReader(read);
				    while ((ReadTemp=bufferedReaderScaff.readLine())!= null) {
					    String [] SplitLine = ReadTemp.split("\t|\\s+");
					    String WriteContents = ">Node_"+(KmerNum++)+"\n"+SplitLine[0]+"\n";
						ByteBuffer buf = ByteBuffer.allocate(5000);
						buf.clear();
						buf.put(WriteContents.getBytes());
						buf.flip();
						while(buf.hasRemaining()){
							inChannel.write(buf);
						}
				    }
					bufferedReaderScaff.close();
					inChannel.close();
				}
				aFile.close();
				System.out.println("Step6: Mapping unique k-mers to reference genome");
				int SplitSize=0;
				int RealSize_Fasta=(CommonClass.getFileLines(ParentPath+"/alignment/OverlapRegions.fa"))/2;
                SplitSize=RealSize_Fasta/t;
				Process p_Split=null;
				Runtime r_Split=Runtime.getRuntime();
				String[] cmd_Split = { "sh", "-c", "split -l  "+2*SplitSize+" "+ParentPath+"/alignment/OverlapRegions.fa -d -a 4 "+ParentPath+"/readfile/read_"};
				p_Split=r_Split.exec(cmd_Split);
				p_Split.waitFor();
			    int SplitFileIndex=0;
			    File FilePath = new File(ParentPath+"/readfile/");
		      	if (!FilePath.exists()||!FilePath.isDirectory()) {
		      	    System.out.println("Path:"+ParentPath+"/readfile is empty");
		      	}
		      	else
		      	{
		      	    String[] tmpList=FilePath.list();
		      	    if (tmpList!= null) {
		      		   for (String aTempList : tmpList) {
		      			  File tmpFile = new File(FilePath, aTempList);
		      			  if (tmpFile.isFile() && tmpFile.getName().startsWith("read_")) {
		      				  String ID_change="read_"+(SplitFileIndex++);
		      				  File ReNameFile = new File(ParentPath+"/readfile/"+ID_change);
		      				  tmpFile.renameTo(ReNameFile);
		      				  tmpFile.delete();
		      			  }
		      		   }
		      	    }
		        }
		      	int Threads=SplitFileIndex;
		      	ExecutorService pool_1 = Executors.newFixedThreadPool(Threads);
		      	for(int s=0;s<SplitFileIndex;s++)
		      	{
		      		if(s!=Threads-1){
		      		    Threads_Novo mt = new Threads_Novo(s,ParentPath,ParentPath+"/readfile/read_"+s,ParentPath+"/readfile/KmerFile.fa",t);
		      			pool_1.execute(mt);
		      		}
		      		else
		      		{
		      			Threads_Novo mt = new Threads_Novo(s,ParentPath,ParentPath+"/readfile/read_"+s,ParentPath+"/readfile/KmerFile.fa",t);
		      			pool_1.execute(mt);
		      		}
		      	}
		      	pool_1.shutdown();
		      	pool_1.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		      	String MergeFileString=" ";
		      	File CoverageFilePath = new File(ParentPath+"/alignment/");
		      	String[] tmpList3=CoverageFilePath.list();
		      	if (tmpList3!= null) {
		      		for (String aTempList : tmpList3) {
		      			File tmpFile = new File(CoverageFilePath, aTempList);
		      			if (tmpFile.isFile() && (tmpFile.getName().startsWith("read_ms_")&&(tmpFile.getName().endsWith(".sort.bam")))) {
		      				MergeFileString+=" "+ParentPath+"/alignment/"+tmpFile.getName();
		      			} 		
		      		}
		      	}
			    Runtime b_bamMerge = Runtime.getRuntime();
			    Process br_bamMerge=null;
			    String[] cmd_bamMerge = { "sh", "-c", ParentPath+"/tools/samtools merge "+ParentPath+"/alignment/Merge.sort.bam "+MergeFileString};
			    br_bamMerge=b_bamMerge.exec(cmd_bamMerge);
			    br_bamMerge.waitFor();
				Runtime k_depth = Runtime.getRuntime();
				Process pk_depth=null;
		    	String[] cmd_depth = { "sh", "-c", ParentPath+"/tools/samtools depth  "+ParentPath+"/alignment/Merge.sort.bam > "+ParentPath+"/alignment/Merge.depth"};
		    	pk_depth=k_depth.exec(cmd_depth);
		    	pk_depth.waitFor();
		    	File bamfile_Path = new File(ParentPath+"/alignment/");
		    	CommonClass.delSpecialFile(bamfile_Path, "read_",".bam");
		    	CommonClass.delSpecialFile(bamfile_Path, "read_", ".sort.bam");
		        System.out.println("Step7: Generation of the high coverage regions");
				CommonClass.generation_HighCovRegions(ParentPath+"/alignment/OverlapRegions.fa",ParentPath+"/alignment/Merge.depth",ParentPath+"/alignment/HighCoverageRegions_"+m+".fa",m);
				CommonClass.DelePathFiles(ParentPath+"/alignment/","ref_Align.mmi");
				Runtime pR_index = Runtime.getRuntime();
				Process R_index=null;
				CommonClass.DelePathFiles(ParentPath+"/alignment/","ref_Align.mmi");
				String[] cmd_pr_Index = { "sh", "-c", ParentPath+"/tools/minimap2 -d "+ParentPath+"/alignment/ref_Align.mmi "+ParentPath+"/alignment/LinearFile.fasta"};
				R_index=pR_index.exec(cmd_pr_Index);
				R_index.waitFor();
				System.out.print("Step8: Mapping the high coverage regions to assemblies [");
				CommonClass.mapping_HighCovRegions2Ref(ParentPath, m, 2, r, t);
				System.out.print("Step9: Identification of the genetic variations in repetitive regions [");
				if(v.equals("yes")&&(!f.equals(""))){
					int fileIndex=0;
					int LineNum=0;
					int Repeat_SplitSize=0;
					CommonClass.MergeFastaMultiLines(ParentPath, f, ParentPath+"/alignment/LineRef_Original.fasta");
					CommonClass.RewriteFile(ParentPath+"/alignment/LineRef_Original.fasta", ParentPath+"/alignment/LineRef.fasta", m);
					Runtime r_index = Runtime.getRuntime();
					Process pr_index=null;
					String[] cmd_Index = {"sh", "-c", ParentPath+"/tools/minimap2 -d "+ParentPath+"/alignment/fre_VS.mmi "+ParentPath+"/alignment/LineRef.fasta"};
					pr_index=r_index.exec(cmd_Index);
					pr_index.waitFor();
					int Real_RepeatSize=(CommonClass.getFileLines(ParentPath+"/alignment/RepeatLib_orginal.fa"))/2;
					int ClusterSize=8;
					if(Real_RepeatSize>128)
					{
						ClusterSize=128;
					}
					else if(Real_RepeatSize>=64 && Real_RepeatSize<128)
					{
						ClusterSize=64;
					}
					else if(Real_RepeatSize>=32 && Real_RepeatSize<64)
					{
						ClusterSize=32;
					}
					else if(Real_RepeatSize>=16 && Real_RepeatSize<32)
					{
						ClusterSize=16;
					}
					else if(Real_RepeatSize>=8 && Real_RepeatSize<16)
					{
						ClusterSize=8;
					}
					else if(Real_RepeatSize>=1 && Real_RepeatSize<8)
					{
						ClusterSize=Real_RepeatSize;
					}
					Repeat_SplitSize=Real_RepeatSize/ClusterSize;
					String SaveRepeats[]=new String[Real_RepeatSize];
					CommonClass.FileToArray(ParentPath+"/alignment/RepeatLib_orginal.fa", SaveRepeats, ">");
					for(int g=0;g<Real_RepeatSize;g++){
						if(g!=0 && ((double)g/Repeat_SplitSize!=0)){
							 FileWriter writer1= new FileWriter(ParentPath+"/alignment/Repeats_"+fileIndex+".fa",true);
							 writer1.write(">Repeat_"+fileIndex+"_"+(LineNum++)+"\n"+SaveRepeats[g]+"\n");
							 writer1.close();
						}
						else
						{
							 fileIndex++;
							 LineNum=0;
						}
					}
			      	int MapThreads=fileIndex;
			      	ExecutorService pool_2 = Executors.newFixedThreadPool(MapThreads);
			      	for(int s=0;s<fileIndex;s++)
			      	{
			      		if(s!=MapThreads-1){
			      			Threads_VS mt = new Threads_VS(s,ParentPath);
			      			pool_1.execute(mt);
			      		}
			      		else
			      		{
			      			Threads_VS mt = new Threads_VS(s,ParentPath);
			      			pool_1.execute(mt);
			      		}
			      	}
			      	pool_2.shutdown();
			      	pool_2.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			      	for(int x=0;x<fileIndex;x++){
			      		File VS_file = new File(ParentPath+"/alignment/Variations_"+x+".fa");
						String VS_Str="";
						if (VS_file.isFile() && VS_file.exists()) {
						   InputStreamReader VSRead = new InputStreamReader(new FileInputStream(VS_file), "utf-8"); 
						   BufferedReader bufferedReader_VS = new BufferedReader(VSRead);
						   while ((VS_Str=bufferedReader_VS.readLine())!=null){
								FileWriter writer1 = new FileWriter(o, true);
								writer1.write(VS_Str+"\n");
								writer1.close();
						   }
						   bufferedReader_VS.close();
						}
			      	}
				}
				else
				{
					System.out.println(" The VS identification is skiped ]");
				}
				System.out.println("Step9: Merging fragments with duplication or inclusion relationships");
				CommonClass.merging_relationships(ParentPath+"/alignment/RepeatLib_orginal.fa",ParentPath+"/alignment/RepeatLib.fa");
				System.out.println("Step11: Generation of the final repeat library");
				CommonClass.copy_files(ParentPath+"/alignment",o);
		    }
		    System.out.println("===========================================================================================");
			System.out.println("Evaluations:");
			File FinalRepeats = new File(o+"/RepeatLib.fa");
			if (FinalRepeats.exists()){
				if(M.equals("yes")||Q.equals("yes")){
					if(M.equals("yes")){
				       CommonClass.calculateMul_AlignRatio(ParentPath,f,t);
		            }
		            if(Q.equals("yes")){
				       CommonClass.calculateN50_N75_N90(ParentPath);
		            }
				}
                else
				{
					System.out.println("Evaluation is not enabled, please adjust -M and -Q these two parameters.");
				}
			}
			else
			{
			    System.out.println("There are no fragments longer than or equal to "+m+"bp in the test results. Please adjust the value of parameter m.");	
			}
		    System.out.println("===========================================================================================");
	    }
	    else
	    {
		    System.out.println("*****************************************************************************");
		    System.out.println("*****************************************************************************");
		    System.out.println("\nPlease check the configuration of parameters.\n");
		    System.out.println("[Usage]: java [options] LongRepMarker [main arguments]");
		    System.out.println("[options]:");
		    System.out.println(" * -Xmx300G : This parameter is only used when working with large data sets.");
		    System.out.println("[Main arguments]:");
		    System.out.println(" * -K <int>: The k-mer size (71).\n * -m <int>: The minimum size of repeat (5000bp).\n * -q1 <string>: The file with left reads for paired-end library number 1.\n * -q2 <string>: The file with right reads for paired-end library number 1.\n * -q3 <string>: The file with left reads for paired-end library number 2.\n * -q4 <string>: The file with right reads for paired-end library number 2.\n * -q5 <string>: The file with left reads for paired-end library number 3.\n * -q6 <string>: The file with right reads for paired-end library number 3.\n * -q7 <string>: The file with left reads for paired-end library number 4.\n * -q8 <string>: The file with right reads for paired-end library number 4.\n * -q9 <string>: The file with left reads for paired-end library number 5.\n * -q10 <string>: The file with right reads for paired-end library number 5.\n * -t <int>: The number of threads (64).\n * -r <string>: The reference file.\n * -E <string>: Whether to perform data error correction (yes/no). \n * -M <string>: Whether to perform Quast evluation (yes/no). \n * -o <string>: The path used to save the final RepeatLib.");
		    System.out.println("*****************************************************************************");
		    System.out.println("*****************************************************************************");
	    }
		long orzr_main = Math.abs(startMem_main - r_main.freeMemory());
		long endTime_main = System.currentTimeMillis();
		System.out.println("Thank you for using! [Total time consumption:"+df.format((endTime_main - startTime_main))+"ms. Total memory consumption:"+df1.format(orzr_main)+"B] ");
    }
}
