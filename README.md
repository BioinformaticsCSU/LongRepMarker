Latest Version
==============
Please see the latest version of LongRepMarker on docker-hub : https://registry.hub.docker.com/repository/docker/liaoxy2docker/longrepmarker_docker


License
=======

Copyright (C) 2020 Xingyu Liao(liaoxingyu@csu.edu.cn / Xingyu_Liao@126.com)

Xingyu Liao(liaoxingyu@csu.edu.cn / Xingyu_Liao@126.com)
Hunan Provincial Key Lab on Bioinformatics, School of Computer Science and Engineering
Central South University
ChangSha
CHINA, 410083


Installation and operation of LongRepMarker 
==================================

### Dependencies

Ubuntu Xenial 16.04 (LTS)
docker [https://docs.docker.com/engine/install/ubuntu/]
 
### Install LongRepMarker

1) Install docker
   sudo apt-get update
   sudo apt-get install docker-ce docker-ce-cli containerd.io

2) Pull image from socker-hub
   docker pull liaoxy2docker/longrepmarker_docker
   
3）Browse images
   docker images
  
4）Start a container in interactive mode based on the image longrepmarker_docker, and execute the /bin/bash command in the container.
  docker run -it longrepmarker_docker /bin/bash

### Run LongRepMarker.
	
    Running command:  
	java -Xmx256G LongRepMarker [options] 
	
	[options]
	
	         -Xmx256G <This parameter is only used when processing large datasets (For example: the genome size exceeds 5Gb)>
	         -r   <The reference file or the assemblies file (This parameter is only used in reference-assisted mode)>
		 -k   <The k-mer size used during the detection(Default value: 49)>
		 -E   <This parameter controls whether the error correction is executed(Setting this parameter to 'yes' indicates that error correction will be executed)>
		 -t   <The number of threads(Default value: 8)>
             -m   <The minmum size of repeats(Default value: 5kb)>
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
		 -l   <The file of 10X linked reads>
		 -M   <This parameter controls whether the alignment rate of detection results is counted(Setting this parameter to 'yes' indicates statistics)>
             -Q   <This parameter controls whether the effective size of detection results is counted(Setting this parameter to 'yes' indicates statistics)>
		 -f   <The reference file used for results evaluation>
		 -v   <This parameter controls whether the structural variation detection is executed(Setting this parameter to 'yes' indicates that variation detection will be executed, this parameter is only used in de novo mode)>
             -o   <The path used to save the final detection results>
		 
	[extremely]
	
	If the system prompts "operation not permitted" ,we need to run the following commands to modify the permissions of LongRepMarker-master folder at this time.
    
	cd ..
	chmod -R 777 LongRepMarker-master
	cd LongRepMarker-master
	java LongRepMarker
	
### For example.
   
    (1) Reference-assisted mode
	
	    java LongRepMarker -r [ /home/reference.fa | /home/assemblies.fa ] -k [49] -m [5000] -f [/home/reference.fa] -Q [yes/no] -M [yes/no] -t [8] -o [/home/finalResults/] [options] 
	
	(2) de novo mode

	    java LongRepMarker -q1 [ read1_left.fa ] -q2 [ read1_right.fa ] -q3 [ read2_left.fa ] -q4 [ read2_right.fa ] -l [10X_linked_reads.fa] -k [49] -m [5000] -f [/home/reference.fa] -Q [yes/no] -M [yes/no] -v [yes/no] -t [8] -o [/home/finalResults/] [options] 
		
	(3) When dealing large datasets in de novo mode
		
	    java -Xmx256G LongRepMarker -q1 [ read1_left.fa ] -q2 [ read1_right.fa ] -q3 [ read2_left.fa ] -q4 [ read2_right.fa ] -l [10X_linked_reads.fa] -k [49] -m [5000] -f [/home/reference.fa] -Q [yes/no] -M [yes/no] -v [yes/no] -t [8] -o [/home/finalResults/] [options] 
		
### Output.
    
	(1)The final detection results will be stored in the path specified by '-o'.
	
    If the value of '-o' is set to '/home/output', the final detection results will be stored in '/home/output'.
	Otherwise, the detection results will be stored in the default path (.../LongRepMarker-master/FinalRepeatLib/).


