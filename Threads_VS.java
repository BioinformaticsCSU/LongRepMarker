//package Program;

import java.io.FileWriter;
import java.util.ArrayList;

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
	String insertionRecords="Insertion Records: \n\n FragmentID \t\t Variation Type \t\t Size \t\t ReferenceID \t\t Location on reference \n";
	String microInsertionRecords="Micro Insertion Records: \n\n FragmentID \t\t Variation Type \t\t Size \t\t ReferenceID \t\t Location on reference \n";
	String deletionRecords="Deletion Records: \n\n FragmentID \t\t Variation Type \t\t Size \t\t ReferenceID \t\t Location on reference \n";
	String microDeletionRecords="Micro Deletion Records: \n\n FragmentID \t\t Variation Type \t\t Size \t\t ReferenceID \t\t Location on reference \n";
	String inversionRecords="Inversion Records: \n\n FragmentID \t\t Variation Type \t\t Size \t\t ReferenceID \t\t Location on reference \n";
	String microInversionRecords="Micro Inversion Records: \n\n FragmentID \t\t Variation Type \t\t Size \t\t ReferenceID \t\t Location on reference \n";
	String translocationRecords="Translocation Records: \n\n FragmentID \t\t Variation Type \t\t Size \t\t ReferenceID \t\t Location on reference \n";
	String microTranslocationRecords="Micro Translocation Records: \n\n FragmentID \t\t Variation Type \t\t Size \t\t ReferenceID \t\t Location on reference \n";
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
			int LinesOfFragments=CommonClass.getFileLines(homePath+"/alignment/Repeats_"+index+".fa")/2;
			String Fragments_Array[]=new String[LinesOfFragments];
			CommonClass.FileToArray(homePath+"/alignment/Repeats_"+index+".fa", Fragments_Array, ">");
			ArrayList<String> FragmentID_Set = new ArrayList<String>();
			ArrayList<String> MultipleFragments_Set = new ArrayList<String>();
		    for(int p=0;p<realSize_SVsam;p++){
			     String [] Splitp = Save_SVSam_Array[p].split("\t|\\s+");
				 if(Splitp[1].equals("4")){
					 num_Insertions++;
					 String [] SplitScaffLine=Splitp[0].split("_");
					 insertionRecords+=" "+Splitp[0]+" \t\t Novo Insertion \t\t "+Fragments_Array[Integer.parseInt(SplitScaffLine[1])].length()+" \t\t Not Available \t\t Not Available "+"\n";
				 }
				 else
				 {
					 if(FragmentID_Set.size()==0){
						 FragmentID_Set.add(Splitp[0]);
						 MultipleFragments_Set.add(Save_SVSam_Array[p]);
					 }
					 else
					 {
						 if(FragmentID_Set.contains(Splitp[0])){
							 MultipleFragments_Set.add(Save_SVSam_Array[p]);
					     }
						 else
					     {
							 if(MultipleFragments_Set.size()>=1){
							     for(int g=0;g<MultipleFragments_Set.size();g++){
							    	String [] FragementLine=MultipleFragments_Set.get(g).split("\t|\\s+");
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
											if((CigarString.charAt(y)=='I')||(CigarString.charAt(y)=='D')||(CigarString.charAt(y)=='H'))
											{
												if(CigarString.charAt(y)=='I'){
													int VariSize=Integer.parseInt(SavePositionStr.substring(1,SavePositionStr.length()));
													if(VariSize>=50)
													{
														insertionRecords+=" "+FragmentsID+" \t\t Novo Insertion \t\t "+VariSize+" \t\t "+ReferenceID+" \t\t "+StartPosition+" \n";
														num_Insertions++;
													}
													else
													{
														microInsertionRecords+=" "+FragmentsID+" \t\t Micro Insertion \t\t "+VariSize+" \t\t "+ReferenceID+" \t\t "+StartPosition+" \n";
														num_MicroInsertions++;
													}
													SavePositionStr="$";
												}
												if(CigarString.charAt(y)=='D'){
													int VariSize=Integer.parseInt(SavePositionStr.substring(1,SavePositionStr.length()));
													if(VariSize>=50)
													{
													    deletionRecords+=" "+FragmentsID+" \t\t Novo Deletion \t\t "+VariSize+" \t\t "+ReferenceID+" \t\t "+StartPosition+" \n";
													    num_Deletions++;
													}
													else
													{
														microDeletionRecords+=" "+FragmentsID+" \t\t Micro Deletion \t\t "+VariSize+" \t\t "+ReferenceID+" \t\t "+StartPosition+" \n";
													    num_MicroDeletions++;
													}
													SavePositionStr="$";
												}
												if(CigarString.charAt(y)=='H'){
													int VariSize=Integer.parseInt(SavePositionStr.substring(1,SavePositionStr.length()));
													if(VariSize>=50)
													{
													    insertionRecords+=" "+FragmentsID+" \t\t Novo Insertion \t\t "+VariSize+" \t\t "+ReferenceID+" \t\t "+StartPosition+" \n";
													    num_Insertions++;
													}
													else
													{
														microInsertionRecords+=" "+FragmentsID+" \t\t Micro Insertion \t\t "+VariSize+" \t\t "+ReferenceID+" \t\t "+StartPosition+" \n";
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
							     }
							 }
							 FragmentID_Set.clear();
							 MultipleFragments_Set.clear();
							 FragmentID_Set.add(Splitp[0]);
						     MultipleFragments_Set.add(Save_SVSam_Array[p]);
					     }
					 }
				 }
			}
			FileWriter writer1= new FileWriter(homePath+"/alignment/Variations_"+index+".fa",true);
			writer1.write(insertionRecords+"\n----------------------------------------------------------\n"+
			              deletionRecords+"\n----------------------------------------------------------\n"+
						  //inversionRecords+"\n----------------------------------------------------------\n"+
						  //translocationRecords+"\n----------------------------------------------------------\n"+
					      microInsertionRecords+"\n----------------------------------------------------------\n"+
					      microDeletionRecords+"\n----------------------------------------------------------\n"+
					      //microInversionRecords+"\n----------------------------------------------------------\n"+microTranslocationRecords+
						  "\n\nNumber summary: \n"+
					      "\n\nNumber of Insertions:"+num_Insertions+"\n\nNumber of Deletions:"+num_Deletions+"\n\nNumber of Inversions:"+num_Inversions+"\n\nNumber of Translocations:"+num_Translocations+"\n\n"+
					      "Number of Micro Insertions:"+num_MicroInsertions+"\n\nNumber of Micro Deletions:"+num_MicroDeletions+"\n\nNumber of MicroInversions:"+num_MicroInversions+"\n\nNumber of Micro Translocations:"+num_MicroTranslocations+
						  "\n----------------------------------------------------------\n");
			writer1.close();
		}
	    catch(Exception e){
			System.out.println("Step10 Error:"+e.getMessage());
			e.printStackTrace();
	    }
    }
}