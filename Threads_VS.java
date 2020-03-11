//package Program;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

class Threads_VS implements Runnable{
	int index=0;
	String homePath="";
	int num_Insertions=0;
	int num_Deletions=0;
	int num_Inversions=0;
	int num_Translocations=0;
	int num_MicroInsertions=0;
	int num_MicroDeletions=0;
	int num_MicroInversions=0;
	int num_MicroTranslocations=0;
	String insertionRecords="";
	String microInsertionRecords="";
	String deletionRecords="";
	String microDeletionRecords="";
	String inversionRecords="";
	String microInversionRecords="";
	String translocationRecords="";
	String microTranslocationRecords="";
	public Threads_VS(int Index, String HomePath)
	{
		index=Index;
		homePath=HomePath;
	}
	@Override
	public void run() {
		try
		{   
		    Runtime r_mapping = Runtime.getRuntime();
		    Process pr_mapping=null;	    
		    String[] cmd_mapping = {"sh", "-c", homePath+"/tools/minimap2 -a "+homePath+"/alignment/fre_VS.mmi "+homePath+"/alignment/Repeats_"+index+".fa > "+homePath+"/alignment/VS_"+index+".sam"};
		    pr_mapping=r_mapping.exec(cmd_mapping);
			pr_mapping.waitFor();
			int linesOfSV_SamFile=CommonClass.getFileLines(homePath+"/alignment/VS_"+index+".sam");
			String Save_SVSam_Array[]=new String[linesOfSV_SamFile];
			int realSize_SVsam=CommonClass.SamFileToArray(homePath+"/alignment/VS_"+index+".sam",Save_SVSam_Array);
			for(int j=0;j<realSize_SVsam;j++){
				String [] FragementLine=Save_SVSam_Array[j].split("\t|\\s+");
				String FragmentsID=FragementLine[0];
				String ReferenceID=FragementLine[2];
				String StartPosition=FragementLine[3];
				String CigarString=FragementLine[5];
				String SavePositionStr="$";
				for(int y=0;y<CigarString.length();y++){
					if(Character.isDigit(CigarString.charAt(y))){
						SavePositionStr+=CigarString.charAt(y);
					}
					else
					{
						if((CigarString.charAt(y)=='I')||(CigarString.charAt(y)=='D')||(CigarString.charAt(y)=='H')){
							if(CigarString.charAt(y)=='I'){
							    int VariSize=Integer.parseInt(SavePositionStr.substring(1,SavePositionStr.length()));
								if(VariSize>=50){
									insertionRecords+=" "+FragmentsID+" \t\t Novo Insertion \t\t "+VariSize+" \t\t "+CigarString+" \t\t "+ReferenceID+" \t\t "+StartPosition+" \n";
									num_Insertions++;
								}
								else
								{
									microInsertionRecords+=" "+FragmentsID+" \t\t Micro Insertion \t\t "+VariSize+" \t\t "+CigarString+" \t\t "+ReferenceID+" \t\t "+StartPosition+" \n";
									num_MicroInsertions++;
								}
								SavePositionStr="$";
							}
						    if(CigarString.charAt(y)=='D'){
								int VariSize=Integer.parseInt(SavePositionStr.substring(1,SavePositionStr.length()));
								if(VariSize>=50){
									deletionRecords+=" "+FragmentsID+" \t\t Novo Deletion \t\t "+VariSize+" \t\t "+CigarString+" \t\t "+ReferenceID+" \t\t "+StartPosition+" \n";
									num_Deletions++;
								}
								else
								{
									microDeletionRecords+=" "+FragmentsID+" \t\t Micro Deletion \t\t "+VariSize+" \t\t "+CigarString+" \t\t "+ReferenceID+" \t\t "+StartPosition+" \n";
									num_MicroDeletions++;
								}
								SavePositionStr="$";
						    }
						    if(CigarString.charAt(y)=='H'){
								int VariSize=Integer.parseInt(SavePositionStr.substring(1,SavePositionStr.length()));
								if(VariSize>=50){
									insertionRecords+=" "+FragmentsID+" \t\t Novo Insertion \t\t "+VariSize+" \t\t "+CigarString+" \t\t "+ReferenceID+" \t\t "+StartPosition+" \n";
									num_Insertions++;
								}
								else
								{
									microInsertionRecords+=" "+FragmentsID+" \t\t Micro Insertion \t\t "+VariSize+" \t\t "+CigarString+" \t\t "+ReferenceID+" \t\t "+StartPosition+" \n";
									num_MicroInsertions++;
								}
								SavePositionStr="$";
						    }
					    }
						else
						{
							SavePositionStr="$";
						}
				    }
			    }
				for(int t=j+1;t<realSize_SVsam;t++){
	    	    	String [] SplitFragement=Save_SVSam_Array[t].split("\t|\\s+");
					String ReferenceID1=SplitFragement[2];
					String StartPosition1=SplitFragement[3];
                   	if((ReferenceID.equals(ReferenceID1))&&(Integer.parseInt(StartPosition)>Integer.parseInt(StartPosition1))){
                		if(SplitFragement[9].length()>=50){
                       		inversionRecords+=" "+FragmentsID+" \t\t Novo Inversion \t\t "+SplitFragement[9].length()+" \t\t "+ReferenceID1+" \t\t "+StartPosition1+" \n";
                       		num_Inversions++;
                		}
                		else if(SplitFragement[9].length()>=10 && SplitFragement[9].length()<50)
                		{
                			microInversionRecords+=" "+FragmentsID+" \t\t Micro Inversion \t\t "+SplitFragement[9].length()+" \t\t "+ReferenceID1+" \t\t "+StartPosition1+" \n";
                			num_MicroInversions++;
                		}
                	}
				}
				for(int t=j+1;t<realSize_SVsam;t++){
	    	    	String [] SplitFragement=Save_SVSam_Array[t].split("\t|\\s+");
					String ReferenceID1=SplitFragement[2];
					String StartPosition1=SplitFragement[3];
                   	if(!ReferenceID.equals(ReferenceID1)){
                		if(SplitFragement[9].length()>=50){
                			translocationRecords+=" "+FragmentsID+" \t\t Novo Translocation \t\t "+SplitFragement[9].length()+" \t\t "+ReferenceID1+" \t\t "+StartPosition1+" \n";
                			num_Translocations++;
                		}
                		else if(SplitFragement[9].length()>=10 && SplitFragement[9].length()<50)
                		{
                			microTranslocationRecords+=" "+FragmentsID+" \t\t Micro Translocation \t\t "+SplitFragement[9].length()+" \t\t "+ReferenceID1+" \t\t "+StartPosition1+" \n";
                			num_MicroTranslocations++;
                		}
                	}
				}
			}
			FileWriter writer1= new FileWriter(homePath+"/alignment/Variations_"+index+".fa",true);
			String Write_String="";
			if(!insertionRecords.equals("")){
				Write_String+=insertionRecords+"\n-------------------------------------------------------------------------------------------------------------------------------------------------\n";
			}
			if(!deletionRecords.equals("")){
				Write_String+=deletionRecords+"\n-------------------------------------------------------------------------------------------------------------------------------------------------\n";
			}
			if(!inversionRecords.equals("")){
				Write_String+=inversionRecords+"\n-------------------------------------------------------------------------------------------------------------------------------------------------\n";
			}
			if(!translocationRecords.equals("")){
				Write_String+=translocationRecords+"\n-------------------------------------------------------------------------------------------------------------------------------------------------\n";
			}
			if(!microInsertionRecords.equals("")){
				Write_String+=microInsertionRecords+"\n-------------------------------------------------------------------------------------------------------------------------------------------------\n";
			}
			if(!microDeletionRecords.equals("")){
				Write_String+=microDeletionRecords+"\n-------------------------------------------------------------------------------------------------------------------------------------------------\n";
			}
			if(!microInversionRecords.equals("")){
				Write_String+=microInversionRecords+"\n-------------------------------------------------------------------------------------------------------------------------------------------------\n";
			}
			if(!microTranslocationRecords.equals("")){
				Write_String+=microTranslocationRecords+"\n-------------------------------------------------------------------------------------------------------------------------------------------------\n";
			}
			if((!insertionRecords.equals(""))||(!deletionRecords.equals(""))||(!inversionRecords.equals(""))||(!translocationRecords.equals(""))||(!microInsertionRecords.equals(""))||(!microDeletionRecords.equals(""))||(!microInversionRecords.equals(""))||(!microTranslocationRecords.equals("")))
			{
				writer1.write("\n\n--------------------------------------------------------------------------------------------------Cluster "+index+" Start---------------------------------------------------------------------------------------------------\n"+     
			            "FragmentID \t\t Variation Type \t\t Size \t\t  Cigar  \t\t ReferenceID \t\t Location on reference \n"+Write_String+"\n\n"
						+"-------------------------------------------------------------------------------------------------------------------------------------------------\n"+
					    "\n\nNumber summary: \n"+"\n\nNumber of Insertions:"+num_Insertions+"\n\nNumber of Deletions:"+num_Deletions+"\n\nNumber of Inversions:"+num_Inversions+"\n\nNumber of Translocations:"+num_Translocations+"\n\n"+
					    "Number of Micro Insertions:"+num_MicroInsertions+"\n\nNumber of Micro Deletions:"+num_MicroDeletions+"\n\nNumber of MicroInversions:"+num_MicroInversions+"\n\nNumber of Micro Translocations:"+num_MicroTranslocations+"\n\n");
						writer1.close();
			}
		}
	    catch(Exception e){
			System.out.println("Step10 Error:"+e.getMessage());
			e.printStackTrace();
	    }
    }
}