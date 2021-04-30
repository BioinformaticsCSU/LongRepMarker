Latest Version
==============
Please see the latest version of LongRepMarker on docker-hub : https://registry.hub.docker.com/repository/docker/liaoxy2docker/longrepmarker_docker


License
=======

Copyright (C) 2020 Xingyu Liao (liaoxingyu@csu.edu.cn/Xingyu_Liao@126.com)

Xingyu Liao  
Hunan Provincial Key Lab on Bioinformatics, School of Computer Science and Engineering  
Central South University  
ChangSha  
CHINA, 410083  


Installation and running of LongRepMarker 
==================================

### Dependencies

Ubuntu Xenial 16.04 (LTS)
docker [https://docs.docker.com/engine/install/ubuntu/]
 
### Install LongRepMarker
1)  Install docker  
*_sudo apt-get update_*  
*_sudo apt-get install docker-ce docker-ce-cli containerd.io_*  
2)  Pull image from socker-hub  
*_docker pull liaoxy2docker/longrepmarker_docker_*  
3)  Browse images  
*_docker images_*  
4)  Start a container in interactive mode based on the image longrepmarker_docker, and execute the /bin/bash command in the container  
*_docker run -it longrepmarker_docker /bin/bash_*  
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
	
	         -Xmx300G <This parameter is only used when processing the large datasets (For example: the genome size exceeds 5Gb)>
	         -r   <The reference file or the assemblies file (This parameter is only used in reference-assisted mode)>
		 -k   <The k-mer size used in detection(Default value: 49)>
		 -E   <This parameter controls whether the error correction is executed (Setting this parameter to 'yes' indicates that error correction will be executed)>
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
		
	    java -Xmx256G LongRepMarker -l [10X_linked_reads.fa] -k [49] -m [5000] -f [/home/reference.fa] -Q [yes/no] -M [yes/no] -v [yes/no] -t [8] -o [/home/finalResults/] [options] 
	    
(6) When dealing large datasets in de novo mode (Take de novo mode based on only NGS short reads as example)

	    java -Xmx300G LongRepMarker -q1 [ read1_left.fq ] -q2 [ read1_right.fq ] -q3 [ read2_left.fq ] -q4 [ read2_right.fq ] -k [49] -m [5000] -f [/home/reference.fa] -Q [yes/no] -M [yes/no] -v [yes/no] -t [8] -o [/home/finalResults/] [options]
		
### Output.
    
(1)The final detection results will be stored in the path specified by '-o'.
	
    If the value of '-o' is set to '/home/output', the final detection results will be stored in '/home/output'.
	Otherwise, the detection results will be stored in the default path (.../LongRepMarker-master/FinalRepeatLib/).


