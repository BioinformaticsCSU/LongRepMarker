//package Program;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class CommonClass {
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
        long filesize=0;
    	if (file.exists() && file.isFile()){
    		filesize=file.length();
        }
        return filesize;
    }
	public static int getFileLength(String ReadSetPath) throws IOException {
		int line = 0;
		String readtemp="";
		String encoding = "utf-8";
		File file = new File(ReadSetPath);
		if (file.isFile() && file.exists()) {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
			BufferedReader bufferedReader = new BufferedReader(read);
			while ((readtemp=bufferedReader.readLine()) != null) {
				if(readtemp.charAt(0)!='>'){
				      line+=readtemp.length();
				}
			}
			bufferedReader.close();
		}
		return line;
	}
	public static void FileToArray(String FileSetPath, String[] FileSetArray, String FilterChar) {
		int count = 0;
		String readtemp="";
		try {
			String encoding = "utf-8";
			File file = new File(FileSetPath);
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				while ((readtemp=bufferedReader.readLine())!= null) {
					if(!readtemp.startsWith(FilterChar) && readtemp.length()>5){
						FileSetArray[count++]=readtemp;
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
		String readtemp="";
		try {
			String encoding = "utf-8";
			File file = new File(FileSetPath);
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				while ((readtemp=bufferedReader.readLine())!= null) {
					if(!readtemp.startsWith(FilterChar) && readtemp.length()>5){
						FileSetArray[count++]=readtemp;
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
            return ;
        }
        String[] tmpList = path.list();
        if (tmpList != null) {
            for (String aTempList : tmpList) {
                File tmpFile = new File(path, aTempList);
                if (tmpFile.isFile() && (tmpFile.getName().startsWith("read_")||tmpFile.getName().endsWith(".amb")||tmpFile.getName().endsWith(".ann")||tmpFile.getName().endsWith(".bwt")||tmpFile.getName().endsWith(".pac")||tmpFile.getName().endsWith(".sa")||tmpFile.getName().endsWith(".h5")||tmpFile.getName().endsWith(".SAMFile")||tmpFile.getName().endsWith(".gz")||tmpFile.getName().endsWith(".depth")||tmpFile.getName().endsWith(".fastg")||tmpFile.getName().endsWith(".yaml")||tmpFile.getName().endsWith(".info")||tmpFile.getName().endsWith(".gfa")||tmpFile.getName().endsWith(".paths")||tmpFile.getName().endsWith(".fasta")||tmpFile.getName().endsWith(".bt2")||tmpFile.getName().endsWith(".fa")||tmpFile.getName().endsWith(".sam")||tmpFile.getName().endsWith(".bam"))||tmpFile.getName().endsWith(".bai")) {
                    tmpFile.delete();
                } else if (tmpFile.isDirectory()) {
                    delAllFile(tmpFile);
                }
            }
        }
    }
    public static void deleteFile(File file){
        if (file.isFile()){
            file.delete();
        }else{
            String[] childFilePath = file.list();
            for (String path:childFilePath){
                File childFile= new File(file.getAbsoluteFile()+"/"+path);
                deleteFile(childFile);
            }
            System.out.println(file.getAbsoluteFile());
            file.delete();
        }
    }
    public static void GenerateFastaFromFastqFiles(String ParentPath,String Fq_left,String Fq_right,String DataName,int t) {
		try {
			  Process p_Merge=null;
			  Runtime r_Merge=Runtime.getRuntime();
			  String[] cmd_Merge = { "sh", "-c", ParentPath+"/tools/shuffleSequences_fastq.pl "+Fq_left+" "+Fq_right+" "+ParentPath+"/readfile/"+DataName+".fq"};
			  p_Merge=r_Merge.exec(cmd_Merge);
			  p_Merge.waitFor();
		 } catch (Exception e) {
			System.out.println("Error liaoxingyu");
			e.printStackTrace();
		 }
    }
	public static void MergeContigMultiLines(String homePath, String ContigPath, String WritePath) throws IOException {
		try 
		{
			Runtime r_change = Runtime.getRuntime();
			Process pr_change=null;
		    String[] cmd_change = { "sh", "-c", "python "+homePath+"/tools/fasta.py "+ContigPath+" "+WritePath};
		    pr_change=r_change.exec(cmd_change);
			pr_change.waitFor();
			
		} catch (Exception e) {
			System.out.println("Error liaoxingyu");
			e.printStackTrace();
		}
	}
	public static int SamFileToArray(String FileSetPath, String[] FileSetArray) {
		int count = 0;
		String readtemp="";
		try {
			String encoding = "utf-8";
			File file = new File(FileSetPath);
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				while ((readtemp=bufferedReader.readLine())!= null) {
					if(readtemp.charAt(0)!='@')
					{
				    	String [] Splitp = readtemp.split("\t|\\s+");
				    	if(!Splitp[1].equals("4"))
				    	{
						   FileSetArray[count++]=readtemp;
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
            return ;
        }
        String[] tmpList = path.list();
        if (tmpList != null) {
            for (String aTempList : tmpList) {
                File tmpFile = new File(path, aTempList);
                if (tmpFile.isFile() && (tmpFile.getName().endsWith(suffix)&&tmpFile.getName().startsWith(Prefix))) {
                    tmpFile.delete();
                }
            }
        }
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
            return ;
        }
        String[] tmpList = path.list();
        if (tmpList != null) {
            for (String aTempList : tmpList) {
                File tmpFile = new File(path, aTempList);
                if (tmpFile.isFile() && (tmpFile.getName().endsWith(suffix)||tmpFile.getName().startsWith(Prefix))) {
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
        byte[] buffer=new byte[2097152];
        int readByte = 0;
        while((readByte = in.read(buffer)) != -1){
            out.write(buffer, 0, readByte);
        }
        in.close();
        out.close();
    }
	public static double EstimatingKmerDepth(String gceFile1,String WritePath) throws IOException {
	    double PeakValue=0;
		String encoding = "utf-8";
		String readtemp="";
		File file1 = new File(gceFile1);
		if (file1.isFile() && file1.exists()) {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file1), encoding);
			BufferedReader bufferedReader = new BufferedReader(read);
			while ((readtemp=bufferedReader.readLine()) != null) {
				if(readtemp.equals("Final estimation table:")){
					bufferedReader.readLine();
					String GetString=bufferedReader.readLine();
					String [] SplitLine = GetString.split("\t|\\s+");
					PeakValue= Double.valueOf(SplitLine[4]);
					break;
				}
			}
			bufferedReader.close();
		}
		return PeakValue;
	}
	public static void DelePathFiles(String FilePath,String Name_Str) throws Exception {		
		Process p_pre=null;
		Runtime r_pre=Runtime.getRuntime();
		String[] cmd_pre = { "sh", "-c", "rm -rf  "+FilePath+Name_Str};
		p_pre=r_pre.exec(cmd_pre);
		p_pre.waitFor();
	}
}
public class LongRepMarker {
	public static void main(String[] args) throws IOException, Exception {
		long startTime_main = System.currentTimeMillis(); 
        Runtime r_main = Runtime.getRuntime();
        long startMem_main  = r_main.freeMemory();
		/***************************************************
		 ***************************************************
		 ***************************************************
		 * 'K': The k-mer size.
		 * 'm': The minimum size of repeat.
		 * 'q1': The left end of paired-end reads.
		 * 'q2': The right end of paired-end reads.
		 * 't' : The number of threads.
		 * 'r' : The reference file.
		 * 'E' : Error correction.
		 * 'o' : The path used to save the final RepeatLib.
		 */
		int    K = 37;
		int    m = 1000;
		int    t = 64;
		String q1 = "";
		String q2 = "";
		String q3 = "";
		String q4 = "";
		String q5 = "";
		String q6 = "";
		String r  = "";
		String E = "";
		String o  = "";
		for (int i = 0; i < args.length; i += 2) {
				String headStr = args[i].substring(1, args[i].length());
				switch (headStr) {
				case "K": {
					K = Integer.parseInt(args[i + 1]);
				};break;
				case "m": {
					m = Integer.parseInt(args[i + 1]);
				};break;
				case "q1": {
					q1 = args[i + 1];
				};break;
				case "q2": {
					q2 = args[i + 1];
				};break;
				case "q3": {
					q3 = args[i + 1];
				};break;
				case "q4": {
					q4 = args[i + 1];
				};break;
				case "q5": {
					q5 = args[i + 1];
				};break;
				case "q6": {
					q6 = args[i + 1];
				};break;
				case "t": {
					t = Integer.parseInt(args[i + 1]);
				};break;
				case "r": {
					r = args[i + 1];
				};break;
				case "o": {
					o = args[i + 1];
				};break;
				case "E": {
					E = args[i + 1];
				};break;
		   }
	  }
	  if(!o.equals("")&&(!r.equals("")||(!q1.equals(""))))
	  {
		        System.out.println("-------------------------------------------------------------------------------------------");
		        System.out.println("Copyright (C) 2019 Jianxin Wang(jxwang@mail.csu.edu.cn), Xingyu Liao(liaoxingyu@csu.edu.cn)"+"\n"+"School of Computer Science and Engineering"+"\n"+"Central South University"+"\n"+"ChangSha"+"CHINA, 410083"+"\n");		  
			    File directory = new File(".");
			    String ParentPath=directory.getCanonicalPath();
			    File CorrectedDirectoy = new File(ParentPath+"/assembly/corrected");
			    if(CorrectedDirectoy.exists())
			    {
				    CommonClass.delAllFile(CorrectedDirectoy);
			    }
			    File AlignDirectoy = new File(ParentPath+"/alignment");
			    if(AlignDirectoy.exists())
			    {
				    CommonClass.delAllFile(AlignDirectoy);
			    }
			    File AssemblyDirectoy = new File(ParentPath+"/assembly");
			    if(AssemblyDirectoy.exists())
			    {
				    CommonClass.delAllFile(AssemblyDirectoy);
			    }
			    File ReadFileDirectoy = new File(ParentPath+"/readfile");
			    if(ReadFileDirectoy.exists())
			    {
				    CommonClass.delAllFile(ReadFileDirectoy);
			    }
			    File KmerFileDirectoy = new File(ParentPath+"/kmerfile");
			    if(KmerFileDirectoy.exists())
			    {
				    CommonClass.delAllFile(KmerFileDirectoy);
			    }
		        String kmerpath = ParentPath +"/kmerfile";
		        File kmerFileDirectory =new File(kmerpath);  
		        if(!kmerFileDirectory .isDirectory()){ 
		    	    kmerFileDirectory.mkdir();
		        }
		        String Repeatpath = ParentPath +"/Repeats";
		        File RepeatFileDirectory =new File(Repeatpath);  
		        if(!RepeatFileDirectory.isDirectory()){ 
		    	   RepeatFileDirectory.mkdir();
		        }
		        String readpath = ParentPath +"/readfile";
		        File FileDirectory =new File(readpath);  
		        if(!FileDirectory.isDirectory()){ 
		    	   FileDirectory.mkdir();
		        }
		        String Logpath = ParentPath +"/Log";
		        File LogFileDirectory =new File(Logpath);  
		        if(!LogFileDirectory.isDirectory()){ 
		    	   LogFileDirectory.mkdir();
		        }
		        String FinalRepeatPath = ParentPath +"/FinalRepeatLib";
		        File FinalRepeatDirectory =new File(FinalRepeatPath);  
		        if(!FinalRepeatDirectory.isDirectory()){ 
		    	   FinalRepeatDirectory.mkdir();
		        }
		        String LeftFastapath = ParentPath +"/readfile/read_left.fa";
		        File LeftFastaFile =new File(LeftFastapath);  
		        if(!LeftFastaFile.isFile()){ 
		    	   LeftFastaFile.createNewFile();
		        }
		        String RightFastapath = ParentPath +"/readfile/read_right.fa";
		        File RightFastaFile =new File(RightFastapath);  
		        if(!RightFastaFile.isFile()){ 
		    	   RightFastaFile.createNewFile();
		        }
		        String Fastapath = ParentPath +"/readfile/read.fa";
		        File FastaFile =new File(Fastapath);  
		        if(!FastaFile.isFile()){ 
		    	   FastaFile.createNewFile();
		        }
		        String Assemblypath = ParentPath +"/assembly";
		        File AssemblyDirectory =new File(Assemblypath);  
		        if(!AssemblyDirectory.isDirectory()){ 
		    	   AssemblyDirectory.mkdir();
		        }
		        String AllAssemblypath = ParentPath +"/assembly/All";
		        File AllAssemblyDirectory =new File(AllAssemblypath);  
		        if(!AllAssemblyDirectory.isDirectory()){ 
		    	   AllAssemblyDirectory.mkdir();
		        }
		        String MultAlignReadsAssemblypath = ParentPath +"/assembly/MultAlignReads";
		        File MultAlignReadsAssemblyDirectory =new File(MultAlignReadsAssemblypath);  
		        if(!MultAlignReadsAssemblyDirectory.isDirectory()){ 
		    	   MultAlignReadsAssemblyDirectory.mkdir();
		        }
		        String KmerAlign2Assemblypath = ParentPath +"/alignment";
		        File KmerAlign2AssemblyDirectory =new File(KmerAlign2Assemblypath);  
		        if(!KmerAlign2AssemblyDirectory.isDirectory()){ 
		    	   KmerAlign2AssemblyDirectory.mkdir();
		        }
		        System.out.println("Step01: Parameters configuration");
		        String SchemaInfo="de novo";
		        if(!r.equals(""))
		        {
		    	   SchemaInfo="reference";
		        }
		        System.out.println("===========================================================================================");
		        System.out.println("  Operation Schema = [ "+SchemaInfo+" mode ]");
		        System.out.println("  K   = [ "+K+" ]");
		        System.out.println("  M   = [ "+m+" ]");
		        System.out.println("  t   = [ "+t+" ]");
		        if(E.equals("")||E.equals("no"))
		        {
		        	System.out.println("  Read error correction   = [  no ]");
		        }
		        else
		        {
		        	System.out.println("  Read error correction   = [ yes ]");
		        }
		        if((!q1.equals(""))||(!q2.equals("")))
		        {
		        	System.out.println("  Data Type  = [ Short reads ]");
			        if(!q1.equals(""))
			        { 
			           System.out.println("  q1  = [ "+q1+" ]");
			        }
			        if(!q2.equals(""))
			        {
			           System.out.println("  q2  = [ "+q2+" ]");
			        }
			        if(!q3.equals(""))
			        {
			           System.out.println("  q3  = [ "+q3+" ]");
			        }
			        if(!q4.equals(""))
			        {
			           System.out.println("  q4  = [ "+q4+" ]");
			        }
			        if(!q5.equals(""))
			        {
			           System.out.println("  q5  = [ "+q5+" ]");
			        }
			        if(!q6.equals(""))
			        {
			           System.out.println("  q6  = [ "+q6+" ]");
			        }
		        }
		        if(!r.equals(""))
		        {
		    	   System.out.println("  Ref = [ "+r+" ]");
		        }
		        if(!o.equals(""))
		        {
		    	   System.out.println("  [Setting] Output_path = [ "+o+" ]");
		        }
		        if(o.equals(""))
		        {
		    	   o=ParentPath+"/FinalRepeatLib";
		    	   System.out.println("  [default] Output_path = [ "+o+" ]");
		        }
		        System.out.println("===========================================================================================");
		        File ReadFiles=new File(ParentPath+"/readfile/read.fa");
		        if(ReadFiles.exists())
		        {
		    	   CommonClass.deleteFile(ReadFiles);
		        }
		        File ReadMergeFiles=new File(ParentPath+"/readfile/reads.fa");
		        if(ReadMergeFiles.exists())
		        {
		    	   CommonClass.deleteFile(ReadMergeFiles);
		        }
		        File ReadMergeQSFiles=new File(ParentPath+"/readfile/reads_qs.fa");
		        if(ReadMergeQSFiles.exists())
		        {
		    	   CommonClass.deleteFile(ReadMergeQSFiles);
		        }
		        File ReadMergeFQFiles=new File(ParentPath+"/readfile/read.fq");
		        if(ReadMergeFQFiles.exists())
		        {
		    	   CommonClass.deleteFile(ReadMergeFQFiles);
		        }
		        File ReadFiles_left=new File(ParentPath+"/readfile/read_left.fa");
		        if(ReadFiles_left.exists())
		        {
		    	   CommonClass.deleteFile(ReadFiles_left);
		        }
		        File ReadFiles_right=new File(ParentPath+"/readfile/read_right.fa");
		        if(ReadFiles_right.exists())
		        {
		           CommonClass.deleteFile(ReadFiles_right);
		        }
		        File KmerFiles=new File(ParentPath+"/read.txt");
		        if(KmerFiles.exists())
		        {
		    	   CommonClass.deleteFile(KmerFiles);
		        }
		        File Kmerh5Files=new File(ParentPath+"/read.h5");
		        if(Kmerh5Files.exists())
		        {
		    	   CommonClass.deleteFile(Kmerh5Files);
		        }
		        File KmerHisFiles=new File(ParentPath+"/kmer_histogram.txt");
		        if(KmerHisFiles.exists())
		        {
		    	   CommonClass.deleteFile(KmerHisFiles);
		        }
		        File HighFreKmerFiles=new File(ParentPath+"/kmerfile/HighFrequencyKmer.fa");
		        if(HighFreKmerFiles.exists())
		        {
		    	   CommonClass.deleteFile(HighFreKmerFiles);
		        }
		        File GCEFiles_Error=new File(ParentPath+"/gce.error");
		        if(GCEFiles_Error.exists())
		        {
		    	   CommonClass.deleteFile(GCEFiles_Error);
		        }
		        File GCEFiles_Table=new File(ParentPath+"/gce.table");
		        if(GCEFiles_Table.exists())
		        {
		    	   CommonClass.deleteFile(GCEFiles_Table);
		        }
			    File ESTFiles=new File(ParentPath+"/Estimated_kmerdepth.txt");
		        if(ESTFiles.exists())
		        {
		    	   CommonClass.deleteFile(ESTFiles);
		        }
			    File ScaffoldLinesFiles=new File(ParentPath+"/alignment/scaffolds.fasta");
		        if(ESTFiles.exists())
		        {
		    	   CommonClass.deleteFile(ScaffoldLinesFiles);
		        }
			    File MultipleAlignFiles=new File(ParentPath+"/alignment/MultipleAlignment.fa");
		        if(MultipleAlignFiles.exists())
		        {
		    	   CommonClass.deleteFile(MultipleAlignFiles);
		        }
			    File SAMFiles=new File(ParentPath+"/alignment/kmer2scaffolds.sam");
		        if(SAMFiles.exists())
		        {
		    	   CommonClass.deleteFile(SAMFiles);
		        }
			    File ScaffoldMarked=new File(ParentPath+"/alignment/ScaffoldMarked.sam");
		        if(ScaffoldMarked.exists())
		        {
		    	   CommonClass.deleteFile(ScaffoldMarked);
		        }
			    File SpadesScaffold=new File(ParentPath+"/assembly/All/scaffolds.fasta");
		        if(SpadesScaffold.exists())
		        {
		    	   CommonClass.deleteFile(SpadesScaffold);
		        }
			    File MultipleReadScaffold=new File(ParentPath+"/assembly/MultAlignReads/scaffolds.fasta");
		        if(MultipleReadScaffold.exists())
		        {
		    	   CommonClass.deleteFile(MultipleReadScaffold);
		        }
			    File ScaffoldChange=new File(ParentPath+"/assembly/All/ChangeLine_Scaffolds.fa");
		        if(ScaffoldChange.exists())
		        {
		    	   CommonClass.deleteFile(ScaffoldChange);
		        }
			    File MultipleHighFreKmer2Scaffolds=new File(ParentPath+"/alignment/MultAlignHighFreKmer2scaffolds.SAMFile");
		        if(MultipleHighFreKmer2Scaffolds.exists())
		        {
		    	   CommonClass.deleteFile(MultipleHighFreKmer2Scaffolds);
		        }
			    File MultipleHighFreKmer2RepeatRegions=new File(ParentPath+"/alignment/MultAlignHighFreKmer2RepeatRegions.SAMFile");
		        if(MultipleHighFreKmer2RepeatRegions.exists())
		        {
		    	   CommonClass.deleteFile(MultipleHighFreKmer2RepeatRegions);
		        }
		        if(r.equals(""))
		        {
		           System.out.print("Step02: De novo assembly");
			       long startTime_assembly = System.currentTimeMillis(); 
			       Runtime r_assembly = Runtime.getRuntime();
			       long startMem_assembly = r_assembly.freeMemory();
			       try
			       {
                        if((!q1.equals(""))||(!q3.equals(""))||(!q5.equals("")))
                        {
                        	System.out.print(" [SPAdes] ");
                			Process p_assemblyRun=null;
                			Runtime r_assemblyRun=Runtime.getRuntime();
                		    String OPT_str="";
                		    if(E.equals("")||E.equals("no")){
                				OPT_str="--only-assembler";
                		    }                			  
                			try
                		    {
                		    	if(!q1.equals("")&&q3.equals("")){
                		    		System.out.print(" [Lib=1] ");
                			    	String[] cmd_assemblyRun = { "sh", "-c", ParentPath+"/tools/spades.py "+OPT_str+" -t 64 --pe1-1 "+q1+" --pe1-2 "+q2+" -o "+ParentPath+"/assembly/All/ > "+ParentPath+"/Log/SPAdeslog.out" };
                			    	p_assemblyRun=r_assemblyRun.exec(cmd_assemblyRun);
                				    p_assemblyRun.waitFor();
                		    	}
                		    	else if(!q3.equals("")&&q5.equals("")){
                		    		System.out.print(" [Lib=2] ");
                			    	String[] cmd_assemblyRun = { "sh", "-c", ParentPath+"/tools/spades.py "+OPT_str+" -t 64 --pe1-1 "+q1+" --pe1-2 "+q2+" --mp1-1 "+q3+" --mp1-2 "+q4+" -o "+ParentPath+"/assembly/All/ > "+ParentPath+"/Log/SPAdeslog.out" };
                			    	p_assemblyRun=r_assemblyRun.exec(cmd_assemblyRun);
                					p_assemblyRun.waitFor();
                		        }
                		    	else if(!q5.equals("")){
                		    		System.out.print(" [Lib=3] ");
                			    	String[] cmd_assemblyRun = { "sh", "-c", ParentPath+"/tools/spades.py "+OPT_str+" -t 64 --pe1-1 "+q1+" --pe1-2 "+q2+" --mp1-1 "+q3+" --mp1-2 "+q4+" --mp2-1 "+q5+" --mp2-2 "+q6+" -o "+ParentPath+"/assembly/All/ > "+ParentPath+"/Log/SPAdeslog.out" };
                			    	p_assemblyRun=r_assemblyRun.exec(cmd_assemblyRun);
                				    p_assemblyRun.waitFor();
                		    	}
                			    CommonClass.MergeContigMultiLines(ParentPath,ParentPath+"/assembly/All/scaffolds.fasta",ParentPath+"/alignment/scaffolds.fasta");
                		    }
                		    catch(Exception e){
                		    	System.out.println("Step03 Error:"+e.getMessage());
                		    	e.printStackTrace();
                		    }
                       }
			       }
			       catch(Exception e){
			    	   System.out.println("Step03 Error:"+e.getMessage());
			    	   e.printStackTrace();
			       }
			       long orz_assembly = Math.abs(startMem_assembly - r_assembly.freeMemory());
			       long endTime_assembly = System.currentTimeMillis();
			       System.out.println(" Time consumption:"+(endTime_assembly-startTime_assembly)+"ms. Memory consumption:"+(double)orz_assembly/1000000000+"GB] ");
		       }
	           else
	           {
	        	   System.out.print("Step02: Loading reference sequence");
	        	   long startTime_scaff = System.currentTimeMillis(); 
	        	   Runtime r_scaff = Runtime.getRuntime();
	        	   long startMem_scaff = r_scaff.freeMemory();
	        	   CommonClass.MergeContigMultiLines(ParentPath,r,ParentPath+"/alignment/scaffolds.fasta");
	        	   long orz_scaff = Math.abs(startMem_scaff - r_scaff.freeMemory());
	        	   long endTime_scaff = System.currentTimeMillis();
	        	   System.out.println(" [ Time consumption:"+(endTime_scaff-startTime_scaff)+"ms. Memory consumption:"+(double)orz_scaff/1000000000+"GB] ");
	           }
	           int ScaffoldID=0;
	           String ScaffStr="";
	           String encoding = "utf-8";
	           File file = new File(ParentPath+"/alignment/scaffolds.fasta");
			   if (file.isFile() && file.exists()) {
				   InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); 
				   BufferedReader bufferedReaderScaff = new BufferedReader(read);
				   while ((ScaffStr=bufferedReaderScaff.readLine())!=null){
					   if(ScaffStr.charAt(0)!='>'&&ScaffStr.length()>=m){
						   FileWriter writer1= new FileWriter(ParentPath+"/alignment/Scaffolds.fasta",true);
						   writer1.write(">NODE_"+(ScaffoldID++)+"_Length_"+ScaffStr.length()+"\n"+ScaffStr+"\n");
						   writer1.close(); 
					   }
				   }
				   bufferedReaderScaff.close();
			   }
			   File OldScaffoldFiles=new File(ParentPath+"/alignment/scaffolds.fasta");
			   if(OldScaffoldFiles.exists())
			   {
			    	CommonClass.deleteFile(OldScaffoldFiles);
			   }
			   System.out.print("Step04: K-mer frequency statistics");
			   long startTime2 = System.currentTimeMillis(); 
			   Runtime r2 = Runtime.getRuntime();
			   long startMem2 = r2.freeMemory();
		       File DeleteH5File=new File(ParentPath+"/Scaffolds.h5");
		       if(DeleteH5File.exists())
		       {
		    	   CommonClass.DelePathFiles(ParentPath,"Scaffolds.h5");
		       }
		       File DeleteReadsFile=new File(ParentPath+"/readfile/");
		       if(DeleteReadsFile.exists())
		       {
		    	   CommonClass.DelePathFiles(ParentPath,"read_*");
		       } 
		       File DeleteTextFile=new File(ParentPath+"/read.txt");
		       if(DeleteTextFile.exists())
		       {
		    	   CommonClass.deleteFile(DeleteTextFile);
		       }
			   Process p_dsk1=null;
			   Runtime r_dsk1=Runtime.getRuntime();
			   try{
			    	 String cmd1=ParentPath+"/tools/dsk -file "+ParentPath+"/alignment/Scaffolds.fasta -kmer-size "+K+" -abundance-min 1";
			    	 p_dsk1=r_dsk1.exec(cmd1);
				     p_dsk1.waitFor();
			   }
			   catch(Exception e){
			    	 System.out.println("Step03 Error:"+e.getMessage());
			    	 e.printStackTrace();
			   }
			   Process p_dsk2=null;
			   Runtime r_dsk2=Runtime.getRuntime();
			   try{
				  	 String cmd2=ParentPath+"/tools/dsk2ascii -file Scaffolds.h5 -out read.txt";
			    	 p_dsk2=r_dsk2.exec(cmd2);
					 p_dsk2.waitFor();
			   }
			   catch(Exception e){
			    	 System.out.println("Step03 Error:"+e.getMessage());
			    	 e.printStackTrace();
			   }
			   int KmerNum=0;
			   String ReadTemp="";
			   File Dskfile = new File(ParentPath+"/read.txt");
			   RandomAccessFile aFile = new RandomAccessFile(ParentPath+"/kmerfile/KmerFile.fa","rw");
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
			   int SplitSize=0;
			   int RealSize_Fasta=(CommonClass.getFileLines(ParentPath+"/kmerfile/KmerFile.fa"))/2;
			   SplitSize=RealSize_Fasta/t;
			   if(SplitSize%2!=0){
				   SplitSize*=2;
			   }
			   Process p_Split=null;
			   Runtime r_Split=Runtime.getRuntime();
			   String[] cmd_Split = { "sh", "-c", "split -l  "+SplitSize+" "+ParentPath+"/kmerfile/KmerFile.fa -d -a 4 "+ParentPath+"/readfile/read_"};
			   p_Split=r_Split.exec(cmd_Split);
			   p_Split.waitFor();
			   long orz2 = Math.abs(startMem2 - r2.freeMemory());
			   long endTime2 = System.currentTimeMillis();
			   System.out.println(" [Time consumption:"+(endTime2-startTime2)+"ms. Memory consumption:"+(double)orz2/1000000000+"GB] ");
		       System.out.print("Step05: Mapping unique k-mers to the assemblies");  
		       long startTime_mr = System.currentTimeMillis(); 
		       Runtime r_mr = Runtime.getRuntime();
		       Runtime r_tgce = Runtime.getRuntime();
		       long startMem_mr = r_mr.freeMemory();
			   Runtime r_index = Runtime.getRuntime();
			   Process pr_index=null;
			   CommonClass.DelePathFiles(ParentPath+"/alignment/","Scaffolds.fasta.sa");
			   CommonClass.DelePathFiles(ParentPath+"/alignment/","Scaffolds.fasta.pac");
			   CommonClass.DelePathFiles(ParentPath+"/alignment/","Scaffolds.fasta.bwt");
			   CommonClass.DelePathFiles(ParentPath+"/alignment/","Scaffolds.fasta.ann");
			   CommonClass.DelePathFiles(ParentPath+"/alignment/","Scaffolds.fasta.amb");
			   String[] cmd_Index = { "sh", "-c", ParentPath+"/tools/bwa index -a bwtsw "+ParentPath+"/alignment/Scaffolds.fasta"};
			   pr_index=r_index.exec(cmd_Index);
			   pr_index.waitFor(); 
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
  	      			   MultipleAlignmentThreads mt = new MultipleAlignmentThreads(s,ParentPath,ParentPath+"/readfile/read_"+s,ParentPath+"/alignment/Scaffolds.fasta");
  	      			   pool_1.execute(mt);
  	      		   }
  	      		   else{
  	      			   MultipleAlignmentThreads mt = new MultipleAlignmentThreads(s,ParentPath,ParentPath+"/readfile/read_"+s,ParentPath+"/alignment/Scaffolds.fasta");
  	      			   pool_1.execute(mt);
  	      		   }
  	      	   }
  	      	   pool_1.shutdown();
  	      	   pool_1.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS); 
  	      	   File CoverageFilePath = new File(ParentPath+"/alignment/");
  	      	   String[] tmpList=CoverageFilePath.list();
  	      	   if (tmpList!= null) {
  	      		   for (String aTempList : tmpList) {
  	      			   String ScaffString="";
  	      			   File tmpFile = new File(CoverageFilePath, aTempList);
  	      			   if (tmpFile.isFile() && (tmpFile.getName().startsWith("read_")&&(tmpFile.getName().endsWith(".depth")))) {
  	      				  InputStreamReader read = new InputStreamReader(new FileInputStream(tmpFile), "utf-8"); 
	  	 				  BufferedReader bufferedReaderScaff = new BufferedReader(read);
	  	 				  while ((ScaffString=bufferedReaderScaff.readLine())!=null){
	  	 					  String [] SplitLine=ScaffString.split("\t|\\s+");
	  	 					  String [] SplitName=SplitLine[0].split("_");
	  	 					  int ScaffID=Integer.parseInt(SplitName[1]);
	  	 					  File depthfile = new File(ParentPath+"/alignment/Scaff_"+ScaffID+".depth");
	  	 					  if(!depthfile.exists())
	  	 					  {
	  	 						  depthfile.createNewFile();
	  	 					  }
	  	 		  			  RandomAccessFile cFile = new RandomAccessFile(ParentPath+"/alignment/Scaff_"+ScaffID+".depth","rw");
		 		  			  cFile.seek(cFile.length());
		 		  			  FileChannel inChanne_l = cFile.getChannel();
	 					      ByteBuffer buf = ByteBuffer.allocate(100);
	   					      buf.clear();
	   					      String writeString=ScaffString+"\n";
	   					      buf.put(writeString.getBytes());
	   					      buf.flip();
	   			              while(buf.hasRemaining()){
	   				             inChanne_l.write(buf);
	   			              }
	   			              inChanne_l.close();
	   			              cFile.close();
	  	 				  }
	  	 				  bufferedReaderScaff.close();
  	 			       } 
  	      		   }
  	      	   } 	      	   
		       long orz_tgce = Math.abs(startMem_mr - r_tgce.freeMemory());
	           long endTime_mr = System.currentTimeMillis();
	           System.out.println(" Time consumption:"+(endTime_mr-startTime_mr)+"ms. Memory consumption:"+(double)orz_tgce/1000000000+"GB] "); 
	           System.out.print("Step06: Generating high coverage regions [");
	 		   long startTime_km = System.currentTimeMillis(); 
			   Runtime r_km = Runtime.getRuntime();
			   long startMem_km = r_km.freeMemory();
		       File AllAlignmentFiles=new File(ParentPath+"/alignment/");
		       if (!AllAlignmentFiles.exists() || !AllAlignmentFiles.isDirectory()) {
		        	System.out.println("Path:"+ParentPath+"/Scafffile is empty");
		       }
		       else
		       {
			        String[] ScaffList=AllAlignmentFiles.list();
			        if (ScaffList != null) {
			            for (String aTempList : ScaffList) {
			                File tmpFile = new File(AllAlignmentFiles, aTempList);
			                if (tmpFile.isFile()&&(tmpFile.getName().startsWith("read_")&&tmpFile.getName().endsWith(".fa"))) {
			                    tmpFile.delete();
			                } 
			            }
			        }
		       }
	  	       int HighCoverNum=0;
			   int LinesOfScaffs=CommonClass.getFileLines(ParentPath+"/alignment/Scaffolds.fasta")/2;
			   String SaveChangeLineScaffolds[]=new String[LinesOfScaffs];
			   int CuntScaffs=CommonClass.FileToArray2(ParentPath+"/alignment/Scaffolds.fasta", SaveChangeLineScaffolds, ">");
			   for(int k=0;k<CuntScaffs;k++)
			   {
				   int CharArrayLength=SaveChangeLineScaffolds[k].length();
				   char SavePositionArray2[]=new char[CharArrayLength];
				   for(int y=0;y<CharArrayLength;y++)
				   {
					  SavePositionArray2[y]='N';
				   }
				   String DepthStr="";
				   File DepthFilePath = new File(ParentPath+"/alignment/Scaff_"+k+".depth");
				   if (DepthFilePath.isFile() && DepthFilePath.exists()) {
					   InputStreamReader DepthRead = new InputStreamReader(new FileInputStream(DepthFilePath), "utf-8"); 
					   BufferedReader bufferedReaderDepth = new BufferedReader(DepthRead);
					   while ((DepthStr=bufferedReaderDepth.readLine())!=null){
	  	 					  String [] SplitLine=DepthStr.split("\t|\\s+");
	  	 					  int Position=Integer.parseInt(SplitLine[1]);
	  	 				      SavePositionArray2[Position-1]=SaveChangeLineScaffolds[k].charAt(Position-1);
					   }
					   bufferedReaderDepth.close();
				   }
				   String ReplaceStr2=new String(SavePositionArray2);
				   String [] SplitScaffLine=ReplaceStr2.split("N");
				   for(int e=0;e<SplitScaffLine.length;e++){
					   if(SplitScaffLine[e].length()>=m){
							FileWriter writer1= new FileWriter(ParentPath +"/alignment/HighCoverageRegions_"+m+".fa",true);
							writer1.write(">NODE_"+(HighCoverNum++)+"_"+SplitScaffLine[e].length()+"\n"+SplitScaffLine[e]+"\n");
							writer1.close();
					   }
				   }
				   if(k%100==0)
				   {
					   System.out.print(" "+ (int)(100*((double)k/CuntScaffs))+"%");
				   }  
	  	       }
			   SaveChangeLineScaffolds=null;
			   long orz_km = Math.abs(startMem_km - r_km.freeMemory());
			   long endTime_km = System.currentTimeMillis();
			   System.out.println(" Time consumption:"+(endTime_km-startTime_km)+"ms. Memory consumption:"+orz_km/1000000000+"GB] ");
			   System.out.print("Step07: Mapping high coverage regions to assemblies");
			   long startTime_hm = System.currentTimeMillis(); 
			   Runtime r_hm = Runtime.getRuntime();
			   long startMem_hm = r_hm.freeMemory();
			   Runtime hr_mapping = Runtime.getRuntime();
			   Process phr_mapping=null;
			   int MultAlignCount=0;
			   int NumCountHCR=0;
			   int ReadFileLines=CommonClass.getFileLines(ParentPath +"/alignment/HighCoverageRegions_"+m+".fa")/2;
			   String SaveReadsArray[]=new String[ReadFileLines];
			   int SizeOfReadArray=CommonClass.FileToArray2(ParentPath +"/alignment/HighCoverageRegions_"+m+".fa",SaveReadsArray, ">");
			   System.out.print(" [ HighCoverageRegionSize:"+SizeOfReadArray);
			   try{
				      Runtime b_index = Runtime.getRuntime();
				      Process br_index=null;
				      String[] br_Index = { "sh", "-c", ParentPath+"/tools/bowtie2-bin/bowtie2-build "+ParentPath+"/alignment/Scaffolds.fasta "+ParentPath+"/alignment/fre"};
				      br_index=b_index.exec(br_Index);
				      br_index.waitFor();
					  String[] cmd_mapping = { "sh", "-c", ParentPath+"/tools/bowtie2-bin/bowtie2 -x "+ParentPath+"/alignment/fre -f -k 5 -p 128 -U "+ParentPath +"/alignment/HighCoverageRegions_"+m+".fa -S "+ParentPath+"/alignment/HighCoverageRegions.sam"};				      
				      phr_mapping=hr_mapping.exec(cmd_mapping);
					  phr_mapping.waitFor();
					  int linesOfSamFile=CommonClass.getFileLines(ParentPath+"/alignment/HighCoverageRegions.sam");
					  String SaveSam_Array[]=new String[linesOfSamFile];
					  int realSize_sam=CommonClass.SamFileToArray(ParentPath+"/alignment/HighCoverageRegions.sam",SaveSam_Array);
					  HashSet<Integer> MultipleAlignReadID_Set = new HashSet<Integer>();
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
						 if(entry.getValue()>1){
							String [] SplitReadID = (entry.getKey()).split("_");
						    MultipleAlignReadID_Set.add(Integer.parseInt(SplitReadID[1]));
							FileWriter writer1= new FileWriter(ParentPath +"/alignment/HighCoverageRegions.txt",true);
						    writer1.write(entry+"\n");
						    writer1.close();
						 }
						 else
						 {
							String [] SplitReadID = (entry.getKey()).split("_");
						    FileWriter writer1= new FileWriter(ParentPath +"/alignment/UniqueMapHighCoverageRegions.fa",true);
							writer1.write(">NODE_"+(NumCountHCR++)+"_"+SaveReadsArray[Integer.parseInt(SplitReadID[1])].length()+"\n"+SaveReadsArray[Integer.parseInt(SplitReadID[1])]+"\n");
							writer1.close();
						 }
					 }
					 SaveSam_Array=null;
					 map=null;
					 File MultAlignReadFiles=new File(ParentPath+"/alignment/");
					 CommonClass.delSpecialFile(MultAlignReadFiles,"RepeatLib",".fa");
					 Iterator<Integer> it2 = MultipleAlignReadID_Set.iterator();
					 while (it2.hasNext()) {
						 int Current_id=it2.next();
						 FileWriter writer1= new FileWriter(ParentPath +"/alignment/RepeatLib.fa",true);
						 writer1.write(">NODE_"+(MultAlignCount++)+"_"+Current_id+"_"+SaveReadsArray[Current_id].length()+"\n"+SaveReadsArray[Current_id]+"\n");
						 writer1.close();
					 }
					 MultipleAlignReadID_Set=null;
					 File DeletedMutiAlignSamFile=new File(ParentPath+"/alignment/HighCoverageRegions.sam");
					 if(DeletedMutiAlignSamFile.exists()){
					     CommonClass.deleteFile(DeletedMutiAlignSamFile);
					 }
			   }catch (Exception e) {
						System.out.println("Error liaoxingyu");
						e.printStackTrace();
			   }
			   SaveReadsArray=null;
			   long orz_hm = Math.abs(startMem_hm - r_hm.freeMemory());
			   long endTime_hm = System.currentTimeMillis();
			   System.out.println(" Time consumption:"+(endTime_hm-startTime_hm)+"ms. Memory consumption:"+orz_hm/1000000000+"GB] ");
			   System.out.print("Step08: Spliting single-alignment blocks");
			   long startTime_spliting = System.currentTimeMillis(); 
			   Runtime r_spliting = Runtime.getRuntime();
			   long startMem_spliting = r_spliting.freeMemory();
			   Process sb_index=null;
			   Process sb_mapping=null;
			   Process sb_sam2bam=null;
			   Runtime ksb_index = Runtime.getRuntime();
			   Runtime ksb_mapping = Runtime.getRuntime();
			   Runtime ksb_sam2bam  = Runtime.getRuntime();
			   CommonClass.DelePathFiles(ParentPath+"/alignment/","Data_nucmer.delta");
			   CommonClass.DelePathFiles(ParentPath+"/alignment/","Data_filter");
			   CommonClass.DelePathFiles(ParentPath+"/alignment/","Data_coords.txt");
			   CommonClass.DelePathFiles(ParentPath+"/alignment/","MUMmerGroupRecords.fa");
			   try
			   {
					String[] cmd_nucmer = { "sh", "-c", ParentPath+"/tools/nucmer -c 50 -p "+ParentPath+"/alignment/Data_nucmer "+ParentPath +"/alignment/UniqueMapHighCoverageRegions.fa"+" "+r};
					sb_index=ksb_index.exec(cmd_nucmer);
					sb_index.waitFor();
					String[] cmd_mapping = { "sh", "-c", ParentPath+"/tools/delta-filter -i 97 -q -r "+ParentPath+"/alignment/Data_nucmer.delta > "+ParentPath+"/alignment/Data_filter"};
					sb_mapping=ksb_mapping.exec(cmd_mapping);
					sb_mapping.waitFor();
					String[] cmd_sam2bam = { "sh", "-c", ParentPath+"/tools/show-coords -dTlro "+ParentPath+"/alignment/Data_filter > "+ParentPath+"/alignment/Data_coords.txt"};
					sb_sam2bam=ksb_sam2bam.exec(cmd_sam2bam);
					sb_sam2bam.waitFor();
			   }
			   catch(Exception e){
					System.out.println("Step08 Spliting Error:"+e.getMessage());
					e.printStackTrace();
			   }
			   int SizeOfMUMmerFile=CommonClass.getFileLines(ParentPath+"/alignment/Data_coords.txt");
			   String MUMerArray[]=new String[SizeOfMUMmerFile];
			   int RealSizeMUMmer=CommonClass.FileToArray2(ParentPath+"/alignment/Data_coords.txt",MUMerArray,">");
			   System.out.print(" [ MUMmer_Num:"+RealSizeMUMmer);
			   int SizeOfUniqueRegions=CommonClass.getFileLines(ParentPath+"/alignment/UniqueMapHighCoverageRegions.fa");
			   String UniqueRegionsArray[]=new String[SizeOfUniqueRegions];
			   int RealSizeUniqueRegions=CommonClass.FileToArray2(ParentPath+"/alignment/UniqueMapHighCoverageRegions.fa",UniqueRegionsArray,">");
			   System.out.print(" UniqueRegions_Num:"+RealSizeUniqueRegions);
			   String SaveTempArray[]=new String[RealSizeMUMmer];
			   for(int w=4;w<RealSizeMUMmer;w++){
				    if(MUMerArray[w].charAt(0)!='#'){
					    int CountSave=0;
					    String MUMmerGroupRecords="";
					    String [] SplitLine1 = MUMerArray[w].split("\t|\\s+");
					    SaveTempArray[CountSave++]=MUMerArray[w];
					    MUMmerGroupRecords=MUMerArray[w]+"\n";
					    MUMerArray[w]="#"+MUMerArray[w];
					    for(int e=w+1;e<RealSizeMUMmer;e++){
					       if(MUMerArray[e].charAt(0)!='#'){
						       String [] SplitLine2 = MUMerArray[e].split("\t|\\s+");
						       if(SplitLine1[11].equals(SplitLine2[11])){
						            SaveTempArray[CountSave++]=MUMerArray[e];
						            MUMmerGroupRecords+=MUMerArray[e]+"\n";
						            MUMerArray[e]="#"+MUMerArray[e];
						       }
					       }
					    }
					    FileWriter writer1= new FileWriter(ParentPath+"/alignment/MUMmerGroupRecords.fa",true);
				        writer1.write(MUMmerGroupRecords+"-------------------------------------------\n");
				        writer1.close();
				        if(CountSave>1)
				        {
						   String [] SplitLine3 = SaveTempArray[0].split("\t|\\s+");
						   String [] SplitLine4 = SplitLine3[10].split("_");
						   int UniqueRegion_id=Integer.parseInt(SplitLine4[1]);
						   int SaveCoords[]=new int[UniqueRegionsArray[UniqueRegion_id].length()];
						   char SaveContigString[]=new char[UniqueRegionsArray[UniqueRegion_id].length()];
						   for(int h=0;h<UniqueRegionsArray[UniqueRegion_id].length();h++)
						   {
						        SaveContigString[h]=UniqueRegionsArray[UniqueRegion_id].charAt(h);
						   }
					       for(int f=0;f<CountSave;f++){
								String [] SplitLine5 = SaveTempArray[f].split("\t|\\s+");
							    int EPGA_start=Integer.parseInt(SplitLine5[0]);
							    int EPGA_end=Integer.parseInt(SplitLine5[1]);
							    int Cut_Start=EPGA_start-1;
							    int Cut_End=EPGA_end-1;
							    for(int h=Cut_Start;h<Cut_End;h++){
							        SaveCoords[h]+=1;
							    }
					        }
					        for(int c=0;c<UniqueRegionsArray[UniqueRegion_id].length();c++){
					            if(SaveCoords[c]<2){
					                SaveContigString[c]='N';
					            }
					        }
						    String Str=new String(SaveContigString);
						    String [] SplitLine = Str.split("N");
						    for(int y=0;y<SplitLine.length;y++){
						        if(SplitLine[y].length()>=m){
									FileWriter writer2= new FileWriter(ParentPath +"/alignment/RepeatLib.fa",true);
									writer2.write(">NODE_"+(MultAlignCount++)+"_"+SplitLine[y].length()+"\n"+SplitLine[y]+"\n");
									writer2.close();
						        }
						    }
				        }
				    }
				}
			    SaveTempArray=null;
			    MUMerArray=null;
			    UniqueRegionsArray=null;
				long orz_spliting = Math.abs(startMem_spliting-r_spliting.freeMemory());
				long endTime_spliting = System.currentTimeMillis();
				System.out.println(" Time consumption:"+(endTime_spliting-startTime_spliting)+"ms. Memory consumption:"+(double)orz_spliting/1000000000+"GB] ");
				System.out.print("Step09: Sorting and filtering");
				int FinalRepeat=0;
				long startTime_filtering = System.currentTimeMillis(); 
				Runtime r_filtering = Runtime.getRuntime();
				long startMem_filtering = r_filtering.freeMemory();
				int LineOfInitialRepeats=CommonClass.getFileLines(ParentPath +"/alignment/RepeatLib.fa")/2;
				String InitialRepeatArray[]=new String[LineOfInitialRepeats];
				int RealSizeOfInitialRepeats=CommonClass.FileToArray2(ParentPath +"/alignment/RepeatLib.fa",InitialRepeatArray,">");
				String Exstring="";
				for(int b=0;b<RealSizeOfInitialRepeats-1;b++){
					for(int f=b+1;f<RealSizeOfInitialRepeats;f++){
						if(InitialRepeatArray[b].length()<InitialRepeatArray[f].length()){
							Exstring=InitialRepeatArray[b];
							InitialRepeatArray[b]=InitialRepeatArray[f];
							InitialRepeatArray[f]=Exstring;
						}
					}
				}
				for(int b=0;b<RealSizeOfInitialRepeats-1;b++){
					for(int f=b+1;f<RealSizeOfInitialRepeats;f++){
						if(InitialRepeatArray[b].contains(InitialRepeatArray[f])||InitialRepeatArray[b].contains(CommonClass.reverse(InitialRepeatArray[f]))){	
						    InitialRepeatArray[f]="#"+InitialRepeatArray[f];
						}
					}
				}				
			    for(int y=0;y<RealSizeOfInitialRepeats;y++){
			        if(InitialRepeatArray[y].charAt(0)!='#'){
						FileWriter writer2= new FileWriter(ParentPath +"/alignment/Final_RepeatLib.fa",true);
						writer2.write(">NODE_"+(FinalRepeat++)+"_"+InitialRepeatArray[y].length()+"\n"+InitialRepeatArray[y]+"\n");
						writer2.close();
			        }
			    }
				long orz_filtering = Math.abs(startMem_filtering-r_filtering.freeMemory());
				long endTime_filtering = System.currentTimeMillis();
				System.out.println(" Time consumption:"+(endTime_filtering-startTime_filtering)+"ms. Memory consumption:"+(double)orz_filtering/1000000000+"GB] ");
				File FinalRepeatFile=new File(ParentPath +"/alignment/RepeatLib.fa");
				if(FinalRepeatFile.exists()){
					File OutPutDirectory =new File(o); 
					if(!OutPutDirectory.isDirectory()){
					    OutPutDirectory.mkdir();
				        CommonClass.copyFile(ParentPath +"/alignment/Final_RepeatLib.fa", o+"/Final_RepeatLib.fa");
					}
					else
					{
					    OutPutDirectory.mkdir();
					}
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
		  System.out.println(" * -K <int>: The k-mer size (37).\n * -m <int>: The minimum size of repeat (1000bp).\n * -q1 <string>: The file with left reads for paired-end library number 1.\n * -q2 <string>: The file with right reads for paired-end library number 1.\n * -q3 <string>: The file with left reads for paired-end library number 2.\n * -q4 <string>: The file with right reads for paired-end library number 2.\n * -q5 <string>: The file with left reads for paired-end library number 3.\n * -q6 <string>: The file with right reads for paired-end library number 3.\n * -t <int>: The number of threads (64).\n * -r <string>: The reference file.\n * -E <string>: Whether to perform data error correction (yes/no). \n * -o <string>: The path used to save the final RepeatLib.");
	      System.out.println("*****************************************************************************");
		  System.out.println("*****************************************************************************");
	  } 
      long orzr_main = Math.abs(startMem_main-r_main.freeMemory());
      long endTime_main = System.currentTimeMillis();
      System.out.println("Thank you for using! [Total time consumption:"+(endTime_main-startTime_main)+"ms. Total memory consumption:"+orzr_main+"B] ");
   }
}
class MultipleAlignmentThreads implements Runnable{
	int index=0;
	String alignTool="";
	String homePath="";
	String filePath="";
	String indexPath="";
	public MultipleAlignmentThreads(int Index, String HomePath, String FilePath, String IndexPath)
	{
		index=Index;
		filePath=FilePath;
		indexPath=IndexPath;
		homePath=HomePath;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try
		{
			int FastaIndex=0;
			String encoding = "utf-8";
			File fastafile = new File(homePath+"/readfile/read_"+index);
			RandomAccessFile aFile = new RandomAccessFile(homePath+"/alignment/read_"+index+".fa","rw");
			FileChannel inChannel = aFile.getChannel();
			if (fastafile.isFile() && fastafile.exists()) {
			    InputStreamReader read = new InputStreamReader(new FileInputStream(fastafile), encoding);
			    BufferedReader bufferedReader = new BufferedReader(read);
			    while ((bufferedReader.readLine())!= null) {
				    String WriteString=bufferedReader.readLine();
				    String WriteContents = ">NODE_"+(FastaIndex++)+"_"+WriteString.length()+"\n"+WriteString+"\n";
					ByteBuffer buf = ByteBuffer.allocate(5000);
					buf.clear();
					buf.put(WriteContents.getBytes());
					buf.flip();
					while(buf.hasRemaining()){
						inChannel.write(buf);
					}
				}
				bufferedReader.close();
				inChannel.close();
		    }
			aFile.close();
		    Runtime r_mapping = Runtime.getRuntime();
		    Process pr_mapping=null;
			String[] cmd_mapping = { "sh", "-c", homePath+"/tools/bwa mem -t 32 -B 2 -A 3 -x intractg "+homePath+"/alignment/Scaffolds.fasta -a "+homePath+"/alignment/read_"+index+".fa > "+homePath+"/alignment/read_"+index+".sam"};
			pr_mapping=r_mapping.exec(cmd_mapping);
			pr_mapping.waitFor();
			String readtemp1="";
			int Count=0;
			int CheckCount=1;
			@SuppressWarnings("rawtypes")
			Set hashSet = new HashSet();
			String SaveRecords="";
			File file = new File(homePath+"/alignment/read_"+index+".sam");
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file),"UTF-8");
				BufferedReader bufferedReader = new BufferedReader(read);
				while ((readtemp1=bufferedReader.readLine())!=null && readtemp1.length()>10) {
					if(readtemp1.charAt(0)=='@'){
						FileWriter writer1= new FileWriter(homePath+"/alignment/read_"+index+"_filtering.sam",true);
						writer1.write(readtemp1+"\n");
						writer1.close();
					}
					else
					{
						String [] Split1 = readtemp1.split("\t|\\s+");
						if(Count==0){
							hashSet.add(Split1[0]);
							SaveRecords=readtemp1;
							Count++;
						}
						else
						{
							if(hashSet.contains(Split1[0])){
								SaveRecords+="\n"+readtemp1;
								CheckCount++;
							}
							else{
								hashSet.clear();
								if(CheckCount>1){
									FileWriter writer1= new FileWriter(homePath+"/alignment/read_"+index+"_filtering.sam",true);
									writer1.write(SaveRecords+"\n");
									writer1.close();
								}
								hashSet.add(Split1[0]);
								SaveRecords=readtemp1;
								CheckCount=1;
							}
						}
					}
				}
			    if(CheckCount>1){
					FileWriter writer1= new FileWriter(homePath+"/alignment/read_"+index+"_filtering.sam",true);
					writer1.write(SaveRecords+"\n");
					writer1.close();
				}
				bufferedReader.close();
			}
			int CountReal=0;
			String readfiltering="";
			File FilterFile = new File(homePath+"/alignment/read_"+index+"_filtering.sam");
			if (FilterFile.isFile() && FilterFile.exists()){
				InputStreamReader read = new InputStreamReader(new FileInputStream(FilterFile), "utf-8");
				BufferedReader bufferedReader = new BufferedReader(read);
				while ((readfiltering=bufferedReader.readLine())!= null) {
					if(readfiltering.charAt(0)!='@'){
						CountReal++;
					}
				}
				bufferedReader.close();
			} 
			else 
			{
				System.out.println("Sam file is not exist!");
			}
			if(CountReal==0){
				FilterFile.delete();
			}
			File AlignSamFile=new File(homePath+"/alignment/read_"+index+".sam");
	    	if(AlignSamFile.exists()){
	    		 AlignSamFile.delete();
	    	}
			if(FilterFile.exists()){
			    int linesOfSamFile=CommonClass.getFileLines(homePath+"/alignment/read_"+index+"_filtering.sam");
			    String SaveSam_Array[]=new String[linesOfSamFile];
			    int realSize_sam=CommonClass.SamFileToArray(homePath+"/alignment/read_"+index+"_filtering.sam",SaveSam_Array);
			    int MultAlignCount=0;
			    HashSet<Integer> MultipleAlignReadID_Set = new HashSet<Integer>();
			    Map<String, Integer> map = new HashMap<>();
			    for(int p=0;p<realSize_sam;p++){
			    	String [] Splitp = SaveSam_Array[p].split("\t|\\s+");
			    	if(!Splitp[1].equals("4")){
				        Integer num = map.get(Splitp[0]);  
				        map.put(Splitp[0], num == null ? 1 : num + 1);
			    	}
			    }
			    Iterator<java.util.Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
			    while (iterator.hasNext()) {
			         java.util.Map.Entry<String, Integer> entry = iterator.next();
			         if(entry.getValue()>1){
					    String [] SplitReadID = (entry.getKey()).split("_");
			            MultipleAlignReadID_Set.add(Integer.parseInt(SplitReadID[1]));
						FileWriter writer1= new FileWriter(homePath+"/alignment/read_"+index+"_MultAlignRecords.txt",true);
						writer1.write(entry+"\n");
						writer1.close();
			         }
			    }
		        SaveSam_Array=null;
		        map=null;
			    int ReadFileLines=CommonClass.getFileLines(homePath+"/alignment/read_"+index+".fa")/2;
			    String SaveReadsArray[]=new String[ReadFileLines];
			    CommonClass.FileToArray(homePath+"/alignment/read_"+index+".fa",SaveReadsArray, ">");
			    File MultAlignReadFiles=new File(homePath+"/alignment/");
			    CommonClass.delSpecialFile(MultAlignReadFiles,"read_"+index+"_MultAlignReads","fa");
		        Iterator<Integer> it2 = MultipleAlignReadID_Set.iterator();
				while (it2.hasNext()) {
					  int Current_id=it2.next();
					  FileWriter writer1= new FileWriter(homePath +"/alignment/read_"+index+"_MultAlignReads.fa",true);
					  writer1.write(">NODE_"+(MultAlignCount++)+"_"+Current_id+"_"+SaveReadsArray[Current_id].length()+"\n"+SaveReadsArray[Current_id]+"\n");
					  writer1.close();
				}
				SaveReadsArray=null;
				MultipleAlignReadID_Set=null;
		    	File DeletedMutiAlignSamFile=new File(homePath+"/alignment/read_"+index+"_filtering.sam");
		    	if(DeletedMutiAlignSamFile.exists()){
		    	     CommonClass.deleteFile(DeletedMutiAlignSamFile);
		    	}
				Runtime k_mapping = Runtime.getRuntime();
				Runtime k_sam2bam  = Runtime.getRuntime();
				Runtime k_bam2sort = Runtime.getRuntime();
				Runtime k_sort2index = Runtime.getRuntime();
				Runtime k_depth = Runtime.getRuntime();
				Process pk_mapping=null;
				Process pk_sam2bam=null;
				Process pk_bam2sort=null;
				Process pk_sort2index=null;
				Process pk_depth=null;
				try{
				        String[] cmd_map = { "sh", "-c", homePath+"/tools/bwa mem -t 32 -B 2 -A 3 -x intractg "+homePath+"/alignment/Scaffolds.fasta -a "+homePath+"/alignment/read_"+index+"_MultAlignReads.fa > "+homePath+"/alignment/read_"+index+"_MultAlignReads.sam"};
				        pk_mapping=k_mapping.exec(cmd_map);
					    pk_mapping.waitFor();
				    	String[] cmd_sam2bam = { "sh", "-c", homePath+"/tools/samtools  view  -bS  "+homePath+"/alignment/read_"+index+"_MultAlignReads.sam > "+homePath+"/alignment/read_"+index+"_MultAlignReads.bam"};
				    	pk_sam2bam=k_sam2bam.exec(cmd_sam2bam);
				    	pk_sam2bam.waitFor();
				    	String[] cmd_bam2sort = { "sh", "-c", homePath+"/tools/samtools  sort  "+homePath+"/alignment/read_"+index+"_MultAlignReads.bam > "+homePath+"/alignment/read_"+index+"_MultAlignReads.sort.bam"};
				    	pk_bam2sort=k_bam2sort.exec(cmd_bam2sort);
				    	pk_bam2sort.waitFor();
				    	String[] cmd_sort2index = { "sh", "-c", homePath+"/tools/samtools  index  "+homePath+"/alignment/read_"+index+"_MultAlignReads.sort.bam"};
				    	pk_sort2index=k_sort2index.exec(cmd_sort2index);
				    	pk_sort2index.waitFor();
				    	String[] cmd_depth = { "sh", "-c", homePath+"/tools/samtools depth  "+homePath+"/alignment/read_"+index+"_MultAlignReads.sort.bam > "+homePath+"/alignment/read_"+index+"_MultAlignReads.depth"};
				    	pk_depth=k_depth.exec(cmd_depth);
				    	pk_depth.waitFor();
				    	File DeletedKmerSamFile=new File(homePath+"/alignment/read_"+index+"_MultAlignReads.sam");
				    	if(DeletedKmerSamFile.exists())
				    	{
				    	   CommonClass.deleteFile(DeletedKmerSamFile);
				    	}
				    	File DeletedKmerBamFile=new File(homePath+"/alignment/read_"+index+"_MultAlignReads.bam");
				    	if(DeletedKmerBamFile.exists())
				    	{
				    	   CommonClass.deleteFile(DeletedKmerBamFile);
				    	}
				    	File DeletedKmerSortBamFile=new File(homePath+"/alignment/read_"+index+"_MultAlignReads.sort.bam");
				    	if(DeletedKmerSortBamFile.exists())
				    	{
				    	   CommonClass.deleteFile(DeletedKmerSortBamFile);
				    	}
				    	File DeletedKmerSortBaiFile=new File(homePath+"/alignment/read_"+index+"_MultAlignReads.sort.bam.bai");
				    	if(DeletedKmerSortBaiFile.exists())
				    	{
				    	   CommonClass.deleteFile(DeletedKmerSortBaiFile);
				    	}
				    	File DeleteSplitReadFile=new File(homePath+"/alignment/read_"+index+".fa");
				    	if(DeleteSplitReadFile.exists())
				    	{
				    	   CommonClass.deleteFile(DeleteSplitReadFile);
				    	}
				    	File DeleteMultiAlignReadFile=new File(homePath+"/alignment/read_"+index+"_MultAlignReads.fa");
				    	if(DeleteMultiAlignReadFile.exists())
				    	{
				    	   CommonClass.deleteFile(DeleteMultiAlignReadFile);
				    	}
				    	File DeleteMultiAlignRecordFile=new File(homePath+"/alignment/read_"+index+"_MultAlignRecords.txt");
				    	if(DeleteMultiAlignRecordFile.exists())
				    	{
				    	   CommonClass.deleteFile(DeleteMultiAlignRecordFile);
				    	}
				  }
				  catch(Exception e){
				    	System.out.println("Step10 Error:"+e.getMessage());
				    	e.printStackTrace();
				  }
			}
       }catch (Exception e){
	        System.out.println("Error liaoxingyu");
		    e.printStackTrace();
       }
   }
}