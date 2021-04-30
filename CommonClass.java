//package Program;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CommonClass {
	public static int RSHash(String str) {
		int hash = 0;
		for (int i = 0; i < str.length(); i++) {
			hash = str.charAt(i) + (hash << 6) + (hash << 16) - hash;
		}
		return (hash & 0x7FFFFFFF);
	}
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
	public static double getFileLength(String ReadSetPath) throws IOException {
		double line = 0;
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
	public static int FileToArray3(String FileSetPath, String[] FileSetArray, String FilterChar) {
		int count = 0;
		String readtemp1 = "";
		String readtemp2 = "";
		try {
			String encoding = "utf-8";
			File file = new File(FileSetPath);
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				while ((readtemp1 = bufferedReader.readLine()) != null && (readtemp2 = bufferedReader.readLine()) != null) {
					String[] Splitp = readtemp1.split("_");
					FileSetArray[Integer.parseInt(Splitp[1])] = readtemp2;
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
	public static int FqToArray(String FileSetPath, String[] FileSetArray) {
		int count = 0;
		try {
			String encoding = "utf-8";
			File file = new File(FileSetPath);
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				while ((bufferedReader.readLine())!= null) {
					FileSetArray[count++]=bufferedReader.readLine();
					bufferedReader.readLine();
					bufferedReader.readLine();
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
						        || tmpFile.getName().startsWith("r_0")|| tmpFile.getName().startsWith("vol0")
						        || tmpFile.getName().endsWith(".mmi") || tmpFile.getName().endsWith(".records")
						        || tmpFile.getName().endsWith(".vcf")|| tmpFile.getName().endsWith(".paf")
						        || tmpFile.getName().startsWith("Reads")|| tmpFile.getName().endsWith(".fastq")
						        || tmpFile.getName().startsWith("read_")|| tmpFile.getName().endsWith(".fq")) {
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
	public static List<String> getAllFilePaths(File filePath,List<String> filePaths){
		File[] files = filePath.listFiles();
		if(files == null){
		     return filePaths;    
		}    
		for(File f:files){
		     if(f.isDirectory()){
		          filePaths.add(f.getPath());
		          getAllFilePaths(f,filePaths);
		     }
		     else
		     {
		          filePaths.add(f.getPath());
		     }    
		}
		return filePaths;
    }
	public static void deletePath(File file) {
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} 
			else if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0;i < files.length;i ++) {
					deletePath(files[i]);
				}
				file.delete();
			}
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
	public static void MergeFastaMultiLines2(String OringnalFile, String WritePath) throws IOException{
		int IndexNum = 0;
		String encoding = "utf-8";
		String readtemp = "";
		File file1 = new File(OringnalFile);
		File file2 = new File(WritePath);
		if(file2.exists()){
			file2.delete();
			file2.createNewFile();
		}
		else
		{
			file2.createNewFile();
		}
		if (file1.isFile() && file1.exists() && file2.isFile() && file2.exists()) {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file1), encoding);
			BufferedReader bufferedReader = new BufferedReader(read);
			OutputStreamWriter write = new  OutputStreamWriter(new FileOutputStream(file2),encoding);
			BufferedWriter bufferedWriter = new BufferedWriter(write) ;
			while ((readtemp = bufferedReader.readLine()) != null) {
				if (readtemp.charAt(0)=='>'&&IndexNum==0) {
					bufferedWriter.write(readtemp+"\n");
					IndexNum++;
				}
				else
				{
					if(readtemp.charAt(0)=='>'&&IndexNum!=0){
						bufferedWriter.write("\n"+readtemp+"\n");
					}
					else
					{
					    bufferedWriter.write(readtemp);
					}
				}
			}
			bufferedReader.close();
			bufferedWriter.close();
		}
	}
	public static void sv_identification(String homePath,String r, String output, int m) throws IOException, InterruptedException{
		Fa2fq(homePath+"/Results/RepeatLib.fa",homePath+"/Results/RepeatLib.fq", m);
		Runtime r_mapping = Runtime.getRuntime();
		Process pr_mapping=null;
		String[] cmd_mapping = { "sh", "-c", homePath+"/tools/ngmlr-master/bin/ngmlr-0.2.8/ngmlr -t 4 -r "+r+" -q "+homePath+"/Results/RepeatLib.fq -o "+homePath+"/Results/RepeatLib.sam"};
		pr_mapping=r_mapping.exec(cmd_mapping);
		pr_mapping.waitFor();
	    Runtime sam2bam  = Runtime.getRuntime();
		Process pk_sam2bam=null;
	    String[] cmd_sam2bam = { "sh", "-c", homePath+"/tools/samtools  view  -bS  "+homePath+"/Results/RepeatLib.sam > "+homePath+"/Results/RepeatLib.bam"};
	    pk_sam2bam=sam2bam.exec(cmd_sam2bam);
	    pk_sam2bam.waitFor();
		File DeletedKmerSamFile=new File(homePath+"/Results/RepeatLib.sam");
		if(DeletedKmerSamFile.exists()){
			 CommonClass.deleteFile(DeletedKmerSamFile);
		}
		Runtime bam2sort = Runtime.getRuntime();
		Process pk_bam2sort=null;
	    String[] cmd_bam2sort = { "sh", "-c", homePath+"/tools/samtools  sort  "+homePath+"/Results/RepeatLib.bam > "+homePath+"/Results/RepeatLib.sort.bam"};
	    pk_bam2sort=bam2sort.exec(cmd_bam2sort);
	    pk_bam2sort.waitFor();
		File DeletedKmerBamFile=new File(homePath+"/Results/RepeatLib.bam");
		if(DeletedKmerBamFile.exists()){
			 CommonClass.deleteFile(DeletedKmerBamFile);
		}
		Runtime sort2index = Runtime.getRuntime();
		Process pk_sort2index=null;
	    String[] cmd_sort2index = { "sh", "-c", homePath+"/tools/samtools  index  "+homePath+"/Results/RepeatLib.sort.bam"};
	    pk_sort2index=sort2index.exec(cmd_sort2index);
	    pk_sort2index.waitFor();
		Runtime sniffles = Runtime.getRuntime();
		Process pk_sniffles=null;
	    String[] cmd_sniffles = { "sh", "-c", homePath+"/tools/Sniffles-master/bin/sniffles-core-1.0.11/sniffles -m "+homePath+"/Results/RepeatLib.sort.bam -v "+homePath+"/Results/RepeatLib.vcf"};
	    pk_sniffles=sniffles.exec(cmd_sniffles);
	    pk_sniffles.waitFor();
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
		if (file1.isFile() && file1.exists()){
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
	public static void RewriteFile2(String OringnalFile, String WritePath, int m) throws IOException {
		int IndexNum = 0;
		String encoding = "utf-8";
		String readtemp = "";
		File file1 = new File(OringnalFile);
		if (file1.isFile() && file1.exists()){
			InputStreamReader read = new InputStreamReader(new FileInputStream(file1), encoding);
			BufferedReader bufferedReader = new BufferedReader(read);
			while ((readtemp = bufferedReader.readLine()) != null) {
				if (readtemp.charAt(0) != '>') {
					String[] Splitp = readtemp.split("N");
					for(int g=0;g<Splitp.length;g++){
						if(Splitp[g].length()>=m){
							FileWriter writer1 = new FileWriter(WritePath, true);
							writer1.write(">NODE_" + (IndexNum++) + "_Length_" + Splitp[g].length() + "\n" + Splitp[g] + "\n");
							writer1.close();
						}
					}
				}
			}
			bufferedReader.close();
		}
	}
    private static String createRepeatedStr(String seed,int n) {
        return String.join("", Collections.nCopies(n, seed));
    }
	public static void Fa2fq(String RepeatFile, String WritePath, int m) throws IOException {
		int IndexNum = 0;
		String encoding = "utf-8";
		String readtemp1 = "";
		String readtemp2 = "";
		OutputStreamWriter write = new  OutputStreamWriter(new FileOutputStream(WritePath),encoding);
		BufferedWriter bufferedWriter = new BufferedWriter(write) ;
		File file1 = new File(RepeatFile);
		if (file1.isFile() && file1.exists()){
			InputStreamReader read = new InputStreamReader(new FileInputStream(file1), encoding);
			BufferedReader bufferedReader = new BufferedReader(read);
			while ((readtemp1 = bufferedReader.readLine()) != null && (readtemp2 = bufferedReader.readLine()) != null) {
				if (readtemp1.charAt(0)=='>' && readtemp2.length()>=m) {
					bufferedWriter.write("@NODE_"+(IndexNum++)+"_Length_"+readtemp2.length()+"\n"+readtemp2+"\n"+"+"+"\n"+createRepeatedStr("?",readtemp2.length())+"\n");
				}
			}
			bufferedReader.close();
			bufferedWriter.close();
		}
	}
	public static void KmerFileToHash(String KmerFilePath, String[] KmerHashTable, int SizeOfHash) throws IOException {
		String encoding = "utf-8";
		try 
		{
			String readtemp = "";
			File file = new File(KmerFilePath);
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				while ((readtemp = bufferedReader.readLine()) != null) {
					if (readtemp.length()>=5) {
						int hashCode = RSHash(readtemp) % SizeOfHash;
						if (KmerHashTable[hashCode]==null) {
							KmerHashTable[hashCode]=readtemp;
						} 
						else 
						{
							int i = 1;
							while (KmerHashTable[(hashCode + i) % SizeOfHash]!= null){
								i++;
							}
							if (KmerHashTable[(hashCode + i) % SizeOfHash]== null){
								KmerHashTable[(hashCode + i) % SizeOfHash]=readtemp;
							}
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
	}
	public static int getHashUnit(String KmerStr, String HashTable[], int SizeOfDSK) {
		int i = 1;
		int hashcode = RSHash(KmerStr) % SizeOfDSK;
		if (HashTable[hashcode]!= null) {
			if (HashTable[hashcode].equals(KmerStr)) {
				return hashcode;
			} 
			else
			{
				while (HashTable[(hashcode + i) % SizeOfDSK]!= null){
					if (HashTable[(hashcode + i) % SizeOfDSK].equals(KmerStr)) {
						return (hashcode + i) % SizeOfDSK;
					} 
					else 
					{
						i++;
					}
				}
			}
		}
		return -1;
	}
	public static void DelePathFiles(String FilePath, String Name_Str) throws Exception {
		Process p_pre = null;
		Runtime r_pre = Runtime.getRuntime();
		String[] cmd_pre = { "sh", "-c", "rm -rf  " + FilePath + Name_Str };
		p_pre = r_pre.exec(cmd_pre);
		p_pre.waitFor();
	}
	public static void multipleAlignmentRatios(String samfile, String output) throws IOException {
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
			bufferedReader.close();
			totalFragments+=unmappedSegments;
            System.out.println("Total count:"+totalFragments+"\n"+"Align 0 times:"+ unmappedSegments+" (" + df.format(100*(double)unmappedSegments / (double)totalFragments)+"% )\n"+ "Align 1 times: " +singleAlignSegments+ " (" + df.format(100*(double)singleAlignSegments / (double)totalFragments)+"% )\n"+ "Align > 1 times: "+multiAlignSegments+" (" + df.format(100*(double)multiAlignSegments/ (double)totalFragments) + "% )");
            FileWriter writer= new FileWriter(output,true);
            writer.write("File name: "+samfile+"\n");
            writer.write("Total count: "+totalFragments+"\n");
            writer.write("Align 0 times: "+unmappedSegments+" (" + df.format(100*(double)unmappedSegments/(double)totalFragments)+"%"+ ")\n");
            writer.write("Align 1 times: " +singleAlignSegments+ " (" + df.format(100*(double)singleAlignSegments/(double)totalFragments)+"%"+")\n");
            writer.write("Align > 1 times: " +multiAlignSegments+" (" + df.format(100*(double)multiAlignSegments/(double)totalFragments)+"%"+")");
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
    		double fileLength=CommonClass.getFileLength(LinearFilePath);
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
 	 				            if(E.equals("yes")){
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
		int Segment_index=0;
		for(int t=0;t<FileArray.length;t++){
			FileWriter writer1 = new FileWriter(mainPath+ "/alignment/RepeatLib_final.fa", true);
			writer1.write(">Node_" + (Segment_index++) + "_" + FileArray[t].length()+ "\n" + FileArray[t]+"\n");
			writer1.close();
		}
		FileWriter writer= new FileWriter(mainPath+"/Results/Size_report.info",true);
		System.out.println("The Maximum segment length:"+FileArray[0].length());
		writer.write("The Maximum segment length:"+FileArray[0].length()+"\n");
		System.out.println("The Minimum segment length:"+FileArray[FileArray.length-1].length());
		writer.write("The Minimum segment length:"+FileArray[FileArray.length-1].length()+"\n");
		double FullLength=CommonClass.getFileLength(mainPath+"/alignment/RepeatLib.fa");
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
			CommonClass.multipleAlignmentRatios(mainPath+"/alignment/FinalAlignments.sam", mainPath+"/Results/Alignment_report.info");
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
			CommonClass.multipleAlignmentRatios(mainPath+"/alignment/FinalAlignments.sam", mainPath+"/Results/Alignment_report.info");
		}
    }
	public static void copy_Resultfiles(String mainPath, String o) throws IOException, InterruptedException{
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
	public static void generation_HighCovRegions(String OverlapFile,String DepthFile,String OutputPath, int m, int k) throws IOException, InterruptedException{	
		if(m<k){
			m=k;
		}
		int LinesOfScaffs=CommonClass.getFileLines(OverlapFile)/2;
		String SaveChangeLineScaffolds[]=new String[LinesOfScaffs];
		CommonClass.FileToArray3(OverlapFile, SaveChangeLineScaffolds, ">");
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
		//Add. Modify 2021.2.20
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
	public static void merging_relationships(String mainPath, String finalRepeatFile,String OutputPath, int k) throws IOException, InterruptedException{
		int LinesOfFragments=CommonClass.getFileLines(finalRepeatFile)/2;
		String SaveFragments[][]=new String[LinesOfFragments][2];
		for(int f=0;f<LinesOfFragments;f++){
			SaveFragments[f][0]="%";
			SaveFragments[f][1]="%";
		}
		ArrayList<Integer> reptitiveSeq = new ArrayList<Integer>();
		String seq_str1="";
		String seq_str2="";
		File SeqFilePath = new File(finalRepeatFile);
		if (SeqFilePath.isFile() && SeqFilePath.exists()) {
			 InputStreamReader SeqReader = new InputStreamReader(new FileInputStream(SeqFilePath), "utf-8"); 
			 BufferedReader bufferedReaderDepth = new BufferedReader(SeqReader);
			 while ((seq_str1=bufferedReaderDepth.readLine())!=null && (seq_str2=bufferedReaderDepth.readLine())!=null){
				  if(seq_str1.charAt(0)=='>'){
					  String [] SplitLine1=seq_str1.split("_");
					  int ArrayIndex=Integer.parseInt(SplitLine1[1]);
					  SaveFragments[ArrayIndex][0]=SplitLine1[1];
					  SaveFragments[ArrayIndex][1]=seq_str2;
				  }
			 }
			 bufferedReaderDepth.close();
		}		
		Runtime b_index2 = Runtime.getRuntime();
		Process br_index2=null;
	    Runtime hr_mapping2 = Runtime.getRuntime();
		Process phr_mapping2=null;
		String[] br_Index2 = { "sh", "-c", mainPath+"/tools/minimap2 -d "+mainPath+"/alignment/Seq_Merge.mmi "+finalRepeatFile};
		br_index2=b_index2.exec(br_Index2);
		br_index2.waitFor();
		String[] cmd_mapping2 = { "sh", "-c", mainPath+"/tools/minimap2 -a "+mainPath+"/alignment/Seq_Merge.mmi "+finalRepeatFile+" > "+mainPath+"/alignment/Seq_Merge.sam"};				      
		phr_mapping2=hr_mapping2.exec(cmd_mapping2);
		phr_mapping2.waitFor();
		String OverlapStr="";
		File OverlapFilePath = new File(mainPath+"/alignment/Seq_Merge.sam");
		if (OverlapFilePath.isFile() && OverlapFilePath.exists()) {
			 InputStreamReader DepthRead = new InputStreamReader(new FileInputStream(OverlapFilePath), "utf-8"); 
			 BufferedReader bufferedReaderDepth = new BufferedReader(DepthRead);
			 while ((OverlapStr=bufferedReaderDepth.readLine())!=null){
				  if(OverlapStr.charAt(0)!='@'){
	 				 String [] SplitLine1=OverlapStr.split("\t|\\s+");
	 				 if(!(SplitLine1[0].equals(SplitLine1[2])) && (!(SplitLine1[1].equals("4")))){
				          String seq_id=SplitLine1[0];
				          String ref_id=SplitLine1[2];
				          String cigar_seq=SplitLine1[5];
				          String seqlen="";
				          String reflen="";
				          String [] SplitLine2=seq_id.split("_");
				          if(seq_id.contains("Add")){
				        	  seqlen=SplitLine2[2];
				          }
				          else
				          {
				        	  seqlen=SplitLine2[3];
				          }
				          String [] SplitLine3=ref_id.split("_");
				          if(ref_id.contains("Add")){
				        	  reflen=SplitLine3[2];
				          }
				          else
				          {
				        	  reflen=SplitLine3[3];
				          }
				          String matchStr=seqlen+"M";
				          if((Integer.parseInt(reflen)>=Integer.parseInt(seqlen))&&(matchStr.equals(cigar_seq))){
				        	  if(!reptitiveSeq.contains(Integer.parseInt(SplitLine2[1]))){
						           reptitiveSeq.add(Integer.parseInt(SplitLine2[1]));
				    			   FileWriter writer1= new FileWriter(mainPath+"/alignment/Merging.records",true);
				    			   writer1.write(OverlapStr+"\n");
				    			   writer1.close();
				        	  }
				          }
	 				  }
				  }
			 }
			 bufferedReaderDepth.close();
		}
		for (int h=0;h<reptitiveSeq.size();h++){
            for(int j=0;j<LinesOfFragments;j++){
        		if((!SaveFragments[j][0].equals("%"))&&(reptitiveSeq.get(h).toString().equals(SaveFragments[j][0]))){
            		SaveFragments[j][0]="%";
            		SaveFragments[j][1]="%";
        		}
        	}
        }
		String exchanges="";
		for(int f=0;f<LinesOfFragments;f++){
			if(!SaveFragments[f].equals("%")){
				for(int h=f+1;h<LinesOfFragments;h++){
				   if(!SaveFragments[h].equals("%")){
					    if(SaveFragments[f][1].length()<SaveFragments[h][1].length()){
						   exchanges=SaveFragments[f][1];
						   SaveFragments[f][1]=SaveFragments[h][1];
						   SaveFragments[h][1]=exchanges;
					    }	
				    }
				}
			}
		}
		int finalFragments=0;
		for(int b=0;b<LinesOfFragments;b++){
			if(!SaveFragments[b][1].equals("%") && SaveFragments[b][1].length()>=k){
			    FileWriter writer1= new FileWriter(OutputPath,true);
				writer1.write(">NODE_"+(finalFragments++)+"_"+SaveFragments[b][1].length()+"\n"+SaveFragments[b][1]+"\n");
				writer1.close();
			}
		}
		reptitiveSeq=null;
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
				 //Add. Modify 2021.2.20
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
	public static void overlap_pretreatment(String orginal_overlap, String interim_overlap) throws IOException, InterruptedException{
		String OverlapStr="";
		File OverlapFilePath = new File(orginal_overlap);
		if (OverlapFilePath.isFile() && OverlapFilePath.exists()) {
			InputStreamReader OverlapRead = new InputStreamReader(new FileInputStream(OverlapFilePath), "utf-8"); 
			BufferedReader bufferedReaderDepth = new BufferedReader(OverlapRead);
			while ((OverlapStr=bufferedReaderDepth.readLine())!=null){
 				String [] SplitLine=OverlapStr.split("\t|\\s+");
 			    String overlap_Add=OverlapStr+"\n"+SplitLine[5]+"\t"+SplitLine[6]+"\t"+SplitLine[7]+"\t"+SplitLine[8]+"\t"+SplitLine[4]+"\t"+SplitLine[0]+"\t"+SplitLine[1]+"\t"+SplitLine[2]+"\t"+SplitLine[3]+"\t"+SplitLine[9]+"\t"+SplitLine[10]+"\t"+SplitLine[11];
			    FileWriter writer1 = new FileWriter(interim_overlap, true);
			    writer1.write(overlap_Add+"\n");
				writer1.close();
 			}
			bufferedReaderDepth.close();
		}
	}
	public static void File_HeadToArray(String FileSetPath, String[] FileSetArray, String SaveHeadFile[], String FilterChar) {
		int count_repeat=0;
		int count_head=0;
		String readtemp = "";
		try {
			String encoding = "utf-8";
			File file = new File(FileSetPath);
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				while ((readtemp = bufferedReader.readLine()) != null) {
					if (!readtemp.startsWith(FilterChar) && readtemp.length() > 5) {
						FileSetArray[count_repeat++] = readtemp;
					}
					else
					{
						SaveHeadFile[count_head++] = readtemp;
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
	public static int num_fragment(String ReadSetPath) throws IOException {
		int line = 0;
		String readtemp = "";
		String encoding = "utf-8";
		File file = new File(ReadSetPath);
		if (file.isFile() && file.exists()) {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
			BufferedReader bufferedReader = new BufferedReader(read);
			while ((readtemp = bufferedReader.readLine()) != null) {
				if (readtemp.charAt(0) == '>' || readtemp.charAt(0) == '@') {
					line++;
				}
			}
			bufferedReader.close();
		}
		return line;
	}
	public static void identification(String homePath, String r, String output, int m) throws IOException, InterruptedException{
		Fa2fq(output+"/RepeatLib.fa",output+"/RepeatLib.fq", m);
		Runtime r_mapping = Runtime.getRuntime();
		Process pr_mapping=null;
		String[] cmd_mapping = { "sh", "-c", homePath+"/tools/ngmlr-master/bin/ngmlr-0.2.8/ngmlr -t 4 -r "+r+" -q "+output+"/RepeatLib.fq -o "+output+"/RepeatLib.sam"};
		pr_mapping=r_mapping.exec(cmd_mapping);
		pr_mapping.waitFor();
	    Runtime sam2bam  = Runtime.getRuntime();
		Process pk_sam2bam=null;
	    String[] cmd_sam2bam = { "sh", "-c", homePath+"/tools/samtools  view  -bS  "+output+"/RepeatLib.sam > "+output+"/RepeatLib.bam"};
	    pk_sam2bam=sam2bam.exec(cmd_sam2bam);
	    pk_sam2bam.waitFor();
		File DeletedKmerSamFile=new File(output+"/RepeatLib.sam");
		if(DeletedKmerSamFile.exists()){
			 deleteFile(DeletedKmerSamFile);
		}
		Runtime bam2sort = Runtime.getRuntime();
		Process pk_bam2sort=null;
	    String[] cmd_bam2sort = { "sh", "-c", homePath+"/tools/samtools  sort  "+output+"/RepeatLib.bam > "+output+"/RepeatLib.sort.bam"};
	    pk_bam2sort=bam2sort.exec(cmd_bam2sort);
	    pk_bam2sort.waitFor();
		File DeletedKmerBamFile=new File(output+"/RepeatLib.bam");
		if(DeletedKmerBamFile.exists()){
			 deleteFile(DeletedKmerBamFile);
		}
		Runtime sort2index = Runtime.getRuntime();
		Process pk_sort2index=null;
	    String[] cmd_sort2index = { "sh", "-c", homePath+"/tools/samtools  index  "+output+"/RepeatLib.sort.bam"};
	    pk_sort2index=sort2index.exec(cmd_sort2index);
	    pk_sort2index.waitFor();
		Runtime sniffles = Runtime.getRuntime();
		Process pk_sniffles=null;
	    String[] cmd_sniffles = { "sh", "-c", homePath+"/tools/Sniffles-master/bin/sniffles-core-1.0.11/sniffles -m "+output+"/RepeatLib.sort.bam -v "+output+"/RepeatLib.vcf"};
	    pk_sniffles=sniffles.exec(cmd_sniffles);
	    pk_sniffles.waitFor();
	}
	public static void generation_reports(String mainPath, String r, String output) throws IOException, InterruptedException{
		CommonClass.copy_Resultfiles(mainPath+"/alignment",output);
		Runtime b_index2 = Runtime.getRuntime();
		Process br_index2=null;
		Runtime hr_mapping2 = Runtime.getRuntime();
		Process phr_mapping2=null;
		Runtime hr_report = Runtime.getRuntime();
		Process phr_report=null;
		Runtime hr_classifier= Runtime.getRuntime();
		Process phr_classifier=null;
		String[] br_Index2 = { "sh", "-c", mainPath+"/tools/minimap2 -d "+mainPath+"/Results/Ref.mmi "+r};
		br_index2=b_index2.exec(br_Index2);
		br_index2.waitFor();
		String[] cmd_mapping2 = { "sh", "-c", mainPath+"/tools/minimap2 -a "+mainPath+"/Results/Ref.mmi "+mainPath+"/Results/RepeatLib.fa > "+mainPath+"/Results/Results.sam"};				      
		phr_mapping2=hr_mapping2.exec(cmd_mapping2);
		phr_mapping2.waitFor();
		String[] cmd_report = { "sh", "-c", "python "+mainPath+"/src/SamProcess.py -m g -s "+mainPath+"/Results/Results.sam -o "+mainPath+"/Results"};				      
		phr_report=hr_report.exec(cmd_report);
		phr_report.waitFor();
		String[] cmd_classifier = { "sh", "-c", mainPath+"/tools/Classifier/RepeatClassifier -consensi  "+mainPath+"/Results/RepeatLib.fa"};				      
		phr_classifier=hr_classifier.exec(cmd_classifier);
		phr_classifier.waitFor();
	}
}
