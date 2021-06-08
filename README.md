Description
==============

Numerous studies have shown that repetitive regions in genomes play indispensable roles in the evolution, inheritance and variation of living organisms. However, most existing methods cannot achieve satisfactory performance on identifying repeats in terms of both accuracy and size, since NGS reads are too short to identify long repeats whereas SMS (Single Molecule Sequencing) long reads are with high error rates.
In this study, we present a novel identification framework, LongRepMarker, based on the global de novo assembly and k-mer based multiple sequence alignment for precisely marking long repeats in genomes. The major characteristics of LongRepMarker are as follows:
(1) by introducing barcode linked reads and SMS long reads to assist the assembly of all short paired-end reads, it can identify the repeats to a greater extent;
(2) by finding the overlap sequences between assemblies or chomosomes, it locates the repeats faster and more accurately;
(3) by using the multi-alignment unique k-mers rather than the high frequency k-mers to identify repeats in overlap sequences, it can obtain the repeats more comprehensively and stably;
(4) by applying the parallel alignment model based on the multi-alignment unique k-mers, the efficiency of data processing can be greatly optimized; and
(5) by taking the corresponding identification strategies, structural variations that occur between repeats can be identified.
Comprehensive experimental results show that LongRepMarker can achieve more satisfactory results than the existing de novo detection methods 

Main improvements of LongRepMarker
==============

Compared with the existing de novo detection methods, the major improvements of LongRepMarker are as follows:

(i) The repeats obtained by LongRepMarker are more comprehensive and accurate.

a) By assembling all paired-end reads and barcode linked reads or SMS long reads instead of assembling the high frequency k-mers, the algorithm can identify the repeats in the genomes to a greater extent.

b) The repetitive sequences are a special kind of overlap sequences, and the overlap sequences occupy only a small partion of the overall sequences. By finding the overlap sequences between assemblies or chomosomes, the algorithm locates the repetitive regions faster and more accurately.

c) Due to the sequencing bias, the high frequency threshold is often difficult to obtain accurately, which has a great impact on the range of the high frequency k-mers. By using the multi-alignment unique k-mers to identify repeats in overlap sequences, the algorithm can obtain the repeats in the genomes more comprehensively and stably.


(ii) The parallel alignment model based on the multi-alignment unique k-mers can greatly optimize the efficiency of data processing in LongRepMarker. LongRepMarker has superior computing efficiency when dealing with large genomes such as human and mouse. For example, it takes only 264.05 minutes to obtain the whole repeat library of the human genome (hg38) in the reference-assisted mode and 2.86 hours to obtain the whole repeat library of the mouse genome in the de novo mode.

(iii) The structural variations that occur between repetitive regions can be identified by LongRepMarker. 
The study and analysis of genomic structural variations that occur within the repetitive regions can provide a new perspective for understanding life processes and analyzing life mechanisms. In order to identify structural variations in the repetitive regions, the proposed algorithm also designs corresponding identification strategies.

(iv) The new detection mode based on only SMS long reads has been integrated into LongRepMarker.
As the development of the third generation sequencing, the SMS long reads have been widely applied in various fields of bioinformatics. A new detection mode based on only SMS long reads has been developed in the LongRepMarker framework. Compared with the existing detection methods based on SMS long reads, this mode has the advantages of low memory consumption, high speed and high detection accuracy.


Latest Version
==============
Please see the latest version of LongRepMarker on docker-hub : https://registry.hub.docker.com/repository/docker/liaoxy2docker/longrepmarker_docker


Author
=======

Copyright (C) 2021 Xingyu Liao (liaoxingyu@csu.edu.cn / Xingyu_Liao@126.com)

Xingyu Liao  
Hunan Provincial Key Lab on Bioinformatics, School of Computer Science and Engineering  
Central South University  
ChangSha  
CHINA, 410083  


Installation and running of LongRepMarker 
==================================

### Dependencies
Linux x86 64bit  
Ubuntu Xenial 16.04 (LTS)
docker [https://docs.docker.com/engine/install/ubuntu/]
 
### Install LongRepMarker
1)  Install docker  
*_sudo apt-get update_*  
*_sudo apt-get install docker-ce docker-ce-cli containerd.io_*  
2)  Pull image from docker-hub  
*_sudo docker pull liaoxy2docker/longrepmarker_docker:latest_*  
3)  Browse images  
*_sudo docker images_*  
4)  Start a container in interactive mode based on the image longrepmarker_docker, and execute the /bin/bash command in the container  
*_sudo docker run -it image_id /bin/bash_*  
5)  Installation location of LongRepMarker in the image  
*/home/LongRepMarker*  
6)  Enter the working directory of LongRepMarker  
*cd /home/LongRepMarker*
7)  Compile the source code  
./Makefile  

### Run LongRepMarker.

    Running command (/home/LongRepMarker):  
    	 java -Xmx300G LongRepMarker [options] 
	
	[options]
	
	         -Xmx300G <This parameter is only used when processing the large datasets (For example: the genome size exceeds 5Gb, and the size of sequencing data is larger than 15GB)>
	         -r   <The reference file or the assemblies file (This parameter is only used in reference-assisted mode)>
		 -k   <The k-mer size used in detection (Default value: 49)>
		 -e   <This parameter controls whether the short reads error correction is executed (Setting this parameter to 'yes' indicates that error correction will be executed)>
		 -E   <This parameter controls whether the long reads error correction is executed (Setting this parameter to 'yes' indicates that error correction will be executed)>
		 -c   <The coverage threshold which is used to filter the low coverage overlaps (Default value:1.5)>
		 -t   <The number of threads (Default value: 8)>
             -m   <The minmum length of detected fragment (Default value: 5kb)>
		 -q1  <The file with left reads for the 1-th paired-end reads>
		 -q2  <The file with right reads for the 1-th paired-end reads>
		 -q3  <The file with left reads for the 2-th paired-end reads>
		 -q4  <The file with right reads for the 2-th paired-end reads>
		 -q5  <The file with left reads for the 3-th paired-end reads>
		 -q6  <The file with right reads for the 3-th paired-end reads>
		 -q7  <The file with left reads for the 4-th paired-end reads>
		 -q8  <The file with right reads for the 4-th paired-end reads>
		 -q9  <The file with left reads for the 5-th paired-end reads>
		 -q10 <The file with right reads for the 5-th paired-end reads>
		 -X1  <The file with left reads for the 1-th 10X linked reads>
		 -X2  <The file with right reads for the 1-th 10X linked reads>
		 -X3  <The file with left reads for the 2-th 10X linked reads>
		 -X4  <The file with right reads for the 2-th 10X linked reads>
		 -l   <The file of SMS long reads>  
		 -M   <This parameter controls whether to evaluate the alignment between detected fragments and the reference genome. (Setting this parameter to 'yes' indicates the evluation will be executed and a report will be generated in the final results), this parameter needs to be used in conjunction with parameter -f >  
		 -Q   <This parameter controls whether to evalute the effective size of detected fragments. (Setting this parameter to 'yes' indicates the evluation will be executed and a report will be generated in the final results)>
		 -f   <The reference genome used for detection results evaluation>
		 -v   <This parameter controls whether to detect the structural variations that appear in the detected repetitive fragments. (Setting this parameter to 'yes' indicates the detection will be executed, this parameter is only used in de novo mode)>
             -o   <The path used to save the final detection results>
	
### For example.
   
(1) Reference-assisted mode
	
	    java LongRepMarker -r [ /home/reference.fa | /home/assemblies.fa ] -k [49] -m [5000] -f [/home/reference.fa] -Q [yes/no] -M [yes/no] -t [8] -o [/home/finalResults/] [options]  
	    
(2) de novo mode based on only NGS short reads  

	    java LongRepMarker -q1 [ read1_left.fq ] -q2 [ read1_right.fq ] -q3 [ read2_left.fq ] -q4 [ read2_right.fq ] -k [49] -m [5000] -f [/home/reference.fa] -Q [yes/no] -M [yes/no] -v [yes/no] -t [8] -o [/home/finalResults/] [options]
	
(3) de novo mode based on NGS short reads + 10X linked reads
	
	    java LongRepMarker -q1 [ read1_left.fq ] -q2 [ read1_right.fq ] -q3 [ read2_left.fq ] -q4 [ read2_right.fq ] -X1 [10X_read1_left.fq] -X2 [10X_read1_right.fq] -X3 [10X_read2_left.fq] -X4 [10X_read2_right.fq]-k [49] -m [5000] -f [/home/reference.fa] -Q [yes/no] -M [yes/no] -v [yes/no] -t [8] -o [/home/finalResults/] [options]  
	    
(4) de novo mode based on NGS short reads + SMS long reads

	    java LongRepMarker -q1 [ read1_left.fq ] -q2 [ read1_right.fq ] -q3 [ read2_left.fq ] -q4 [ read2_right.fq ] -l [long_reads.fq] -k [49] -m [5000] -f [/home/reference.fa] -Q [yes/no] -M [yes/no] -v [yes/no] -t [8] -o [/home/finalResults/] [options] 

(5) de novo mode based on only SMS long reads
		
	    java LongRepMarker -l [1ong_reads.fa] -k [49] -m [5000] -f [/home/reference.fa] -Q [yes/no] -M [yes/no] -v [yes/no] -t [8] -o [/home/finalResults/] [options] 
	    
(6) When dealing large datasets in the de novo mode (Take de novo mode based on only NGS short reads as example)

	    java -Xmx300G LongRepMarker -q1 [ read1_left.fq ] -q2 [ read1_right.fq ] -q3 [ read2_left.fq ] -q4 [ read2_right.fq ] -k [49] -m [5000] -f [/home/reference.fa] -Q [yes/no] -M [yes/no] -v [yes/no] -t [8] -o [/home/finalResults/] [options]
		
### Output.
    
(1) The final detection results will be stored in the path specified by '-o'. If the parameter '-o' is not configured, the final detection results will be stored in the path of '/home/LongRepMarker/Results'.

(2) The following detailed reports will be generated in the final detection results.

	   /home/LongRepMarker/Results/Alignment_report.info     // The report of alignment between detected fragments and the reference genome.
	   /home/LongRepMarker/Results/Annotation_report.info    // The report of distribution of detected fragments in the genome.
	   /home/LongRepMarker/Results/Size_report.info          // The report of distribution of length of the detected fragments.
	   /home/LongRepMarker/Results/RepeatLib.vcf             // The report of the structural variations that appear in the detected repetitive fragments.
	   /home/LongRepMarker/Results/RepeatLib.fa              // The final repeat library.
	   /home/LongRepMarker/Results/RepeatLib.fa.classified   // The final repeat library with annotation.
