//package Program;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

class Threads_Ref implements Runnable{
	int index=0;
	int thread=0;
	String alignTool="";
	String homePath="";
	String filePath="";
	String kmerFile="";
	public Threads_Ref(int Index, String HomePath, String FilePath, String KmerFile, int Threads)
	{
		index=Index;
		thread=Threads;
		filePath=FilePath;
		kmerFile=KmerFile;
		homePath=HomePath;
	}
	@Override
	public void run() {
		try
		{   
			File bowtiefile_Path = new File(homePath+"/alignment/");
	 	    CommonClass.delSpecialFile(bowtiefile_Path,"fre_"+index,".bt2");
	 	    CommonClass.delSpecialFile(bowtiefile_Path,"read_"+index,".fa");
	 	    CommonClass.delSpecialFile(bowtiefile_Path,"read_"+index,".sam");
	 	    CommonClass.delSpecialFile(bowtiefile_Path,"read_ms_"+index,".sam");
	 	    CommonClass.delSpecialFile(bowtiefile_Path,"read_ms_"+index,".bam");
			CommonClass.copyFile(homePath+"/readfile/read_"+index, homePath+"/alignment/read_"+index+".fa");
			Runtime r_index = Runtime.getRuntime();
			Process pr_index=null;
			String[] cmd_Index = { "sh", "-c", homePath+"/tools/bwa index -a bwtsw "+homePath+"/alignment/read_"+index+".fa"};
			pr_index=r_index.exec(cmd_Index);
			pr_index.waitFor();
		    Runtime r_mapping = Runtime.getRuntime();
		    Process pr_mapping=null;	    
		    String[] cmd_mapping = { "sh", "-c", homePath+"/tools/bwa mem -t 32 -x intractg "+homePath+"/alignment/read_"+index+".fa -a "+homePath+"/readfile/KmerFile.fa > "+homePath+"/alignment/read_"+index+".sam"};
		    pr_mapping=r_mapping.exec(cmd_mapping);
			pr_mapping.waitFor();
			String samStr="";
            ArrayList<String> KmerId = new ArrayList<String>();
            ArrayList<String> AlignStr = new ArrayList<String>();
			File SamFilePath=new File(homePath+"/alignment/read_"+index+".sam");
            RandomAccessFile aFile2 = new RandomAccessFile(homePath+"/alignment/read_ms_"+index+".sam","rw");
            FileChannel inChanne2 = aFile2.getChannel();
			if (SamFilePath.isFile() && SamFilePath.exists()) {
				 InputStreamReader AlignRead = new InputStreamReader(new FileInputStream(SamFilePath), "utf-8"); 
				 BufferedReader bufferedReaderDepth = new BufferedReader(AlignRead);
			     while ((samStr=bufferedReaderDepth.readLine())!=null){
		 			 if (samStr.charAt(0)=='@') {
		 				String WriteContents = samStr+"\n";
		 				ByteBuffer buf = ByteBuffer.allocate(50000);
		 			    buf.clear();
		 				buf.put(WriteContents.getBytes());
		 				buf.flip();
		 				while(buf.hasRemaining()){
		 					inChanne2.write(buf);
		 			    }
		 			}	
		 			else
		 			{
		 				String [] SamLine=samStr.split("\t|\\s+");
		 				if(!SamLine[1].equals("4")){
				 		    if(KmerId.size()==0){
				 		    	KmerId.add(SamLine[0]);
				 		    	AlignStr.add(samStr);
				 		    }
				 		    else
				 		    {
		 						if(KmerId.contains(SamLine[0])){
		 							AlignStr.add(samStr);
		 						}
		 						else
		 						{
		 						    if(AlignStr.size()>=2){
                                        for(int f=0;f<AlignStr.size();f++){
	   						 			   String WriteContents = AlignStr.get(f).toString()+"\n";
	   						 			   ByteBuffer buf = ByteBuffer.allocate(50000);
	   						 			   buf.clear();
	   						 			   buf.put(WriteContents.getBytes());
	   						 			   buf.flip();
	   						 			   while(buf.hasRemaining()){
	   						 				   inChanne2.write(buf);
	   						 			   }
                                        }
						  	 		}
		 						    KmerId.clear();
		 						    AlignStr.clear();
		 						    KmerId.add(SamLine[0]);
							        AlignStr.add(samStr);
				 		        }
			 			    }
		 				 }
		 			 }
			     }
			     bufferedReaderDepth.close();
			 }
			 KmerId=null;
			 AlignStr=null;
			 inChanne2.close();
			 aFile2.close();
			 File DeletedOriginalSamFile=new File(homePath+"/alignment/read_"+index+".sam");
			 if(DeletedOriginalSamFile.exists()){
				  CommonClass.deleteFile(DeletedOriginalSamFile);
			 }
		     Runtime k_sam2bam  = Runtime.getRuntime();
			 Process pk_sam2bam=null;
		     String[] cmd_sam2bam = { "sh", "-c", homePath+"/tools/samtools  view  -bS  "+homePath+"/alignment/read_ms_"+index+".sam > "+homePath+"/alignment/read_ms_"+index+".bam"};
		     pk_sam2bam=k_sam2bam.exec(cmd_sam2bam);
		     pk_sam2bam.waitFor();
			 Runtime k_bam2sort = Runtime.getRuntime();
			 Process pk_bam2sort=null;
		     String[] cmd_bam2sort = { "sh", "-c", homePath+"/tools/samtools  sort  "+homePath+"/alignment/read_ms_"+index+".bam > "+homePath+"/alignment/read_ms_"+index+".sort.bam"};
		     pk_bam2sort=k_bam2sort.exec(cmd_bam2sort);
		     pk_bam2sort.waitFor();
			 Runtime k_sort2index = Runtime.getRuntime();
			 Process pk_sort2index=null;
		     String[] cmd_sort2index = { "sh", "-c", homePath+"/tools/samtools  index  "+homePath+"/alignment/read_ms_"+index+".sort.bam"};
		     pk_sort2index=k_sort2index.exec(cmd_sort2index);
		     pk_sort2index.waitFor();
			 File DeletedKmerSamFile=new File(homePath+"/alignment/read_ms_"+index+".sam");
			 if(DeletedKmerSamFile.exists()){
				  CommonClass.deleteFile(DeletedKmerSamFile);
			 }
			 File DeletedKmerBamFile=new File(homePath+"/alignment/read_ms_"+index+".bam");
			 if(DeletedKmerBamFile.exists()){
				  CommonClass.deleteFile(DeletedKmerBamFile);
			 }
		}
	    catch(Exception e){
			System.out.println("Step10 Error:"+e.getMessage());
			e.printStackTrace();
	    }
    }
}