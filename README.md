Latest Version
==============
Please see the latest version of LongRepMarker:https://github.com/BioinformaticsCSU/LongRepMarker


License
=======

Copyright (C) 2019 Jianxin Wang(jxwang@mail.csu.edu.cn), Xingyu Liao(liaoxingyu@csu.edu.cn)

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 3
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, see <http://www.gnu.org/licenses/>.

Jianxin Wang(jxwang@mail.csu.edu.cn), Xingyu Liao(liaoxingyu@csu.edu.cn)
School of Computer Science and Engineering
Central South University
ChangSha
CHINA, 410083


Installation and operation of LongRepMarker 
==================================

### Dependencies

When running LongRepMarker from GitHub source the following tools are required:

* [jdk 1.8.0 or above]
* [GNU C++ 4.6.3 or above] 
* [perl 5.6.0 or above] 
* [python 2.7.14 or above]
 
### Install LongRepMarker

LongRepMarker is written in Java and therefore will require a machine with jdk pre-installed.

Create a main directory (eg:LongRepMarker-master). Copy all source code to this directory.

Run command line: javac LongRepMarker.java 

### Run LongRepMarker.

[Usage]:

java [options] LongRepMarker [main arguments]

[options]:

* -Xmx300G : This parameter is only used when working with large data sets.

[Main arguments]:
 
 * -K <int>: The k-mer size (37).
 * -m <int>: The minimum size of repeat (1000bp).
 * -q1 <string>: The file with left reads for paired-end library number 1.
 * -q2 <string>: The file with right reads for paired-end library number 1.
 * -q3 <string>: The file with left reads for paired-end library number 2.
 * -q4 <string>: The file with right reads for paired-end library number 2.
 * -q5 <string>: The file with left reads for paired-end library number 3.
 * -q6 <string>: The file with right reads for paired-end library number 3.
 * -t <int>: The number of threads (64).
 * -r <string>: The reference file.
 * -E <string>: Whether to perform data error correction (yes/no).
 * -o <string>: The path used to save the final repeat library file.
	
 If the system prompts "operation not permitted", we need to run the following command to modify the permissions of the LongRepMarker folder.
	
 chmod -R 777  LongRepMarker-master
 
### Output.
    
	The final repeat library file will be stored in the path specified by '-o' (For example: -o /home/.../finalRepeats/ , the final repeat library file will be stored in the following path).
    
    /home/.../finalRepeats/Final_RepeatLib.fa 
	
	If this parameter is not configured, the final repeat library file will be stored in the default directory(.../LongRepMarker-master/FinalRepeatLib/, the final repeat library file will be stored in the following path).
	
	.../LongRepMarker-master/FinalRepeatLib/Final_RepeatLib.fa