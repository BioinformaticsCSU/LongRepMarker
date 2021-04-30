//package Program;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
		 * -Xmx256G <This parameter is only used when processing large datasets
		 * (For example: the genome size exceeds 5Gb)> -r <The reference file or
		 * the assemblies file (This parameter is only used in
		 * reference-assisted mode)> -X <The file of 10X linked reads> -l <The
		 * file of SMS long reads> -k <The k-mer size used during the
		 * detection(Default value: 49)> -E <This parameter controls whether the
		 * SMS long read error correction is executed(Setting this parameter to
		 * 'yes' indicates that long read error correction will be executed)> -e
		 * <This parameter controls whether the NGS short read error correction
		 * is executed(Setting this parameter to 'yes' indicates that short read
		 * error correction will be executed)> -t <The number of threads(Default
		 * value: 8)> -m <The minimum size of repeats(Default value: 5kb)> -c
		 * <The coverage threshold which is used to filter the low coverage
		 * overlaps(Default value:1.5)> -q1 <The file with left reads for the
		 * 1-th paired-end reads> -q2 <The file with right reads for the 1-th
		 * paired-end reads> -q3 <The file with left reads for the 2-th
		 * paired-end reads> -q4 <The file with right reads for the 2-th
		 * paired-end reads> -q5 <The file with left reads for the 3-th
		 * paired-end reads> -q6 <The file with right reads for the 3-th
		 * paired-end reads> -q7 <The file with left reads for the 4-th
		 * paired-end reads> -q8 <The file with right reads for the 4-th
		 * paired-end reads> -q9 <The file with left reads for the 5-th
		 * paired-end reads> -q10 <The file with right reads for the 5-th
		 * paired-end reads> -M <This parameter controls whether the alignment
		 * rate of detection results is counted(Setting this parameter to 'yes'
		 * indicates statistics)> -Q <This parameter controls whether the
		 * effective size of detection results is counted(Setting this parameter
		 * to 'yes' indicates statistics)> -f <The reference file used for
		 * results evaluation> -v <This parameter controls whether the
		 * structural variation detection is executed(Setting this parameter to
		 * 'yes' indicates that variation detection will be executed, this
		 * parameter is only used in de novo mode)> -o <The path used to save
		 * the final detection results>
		 * **************************************************
		 * **************************************************
		 * **************************************************
		 */
		int m = 5000;
		int t = 16;
		String r = "";
		String X1 = "";
		String X2 = "";
		String X3 = "";
		String X4 = "";
		String l = "";
		String q1 = "";
		String q2 = "";
		String q3 = "";
		String q4 = "";
		String q5 = "";
		String q6 = "";
		String q7 = "";
		String q8 = "";
		String q9 = "";
		String q10 = "";
		int k = 49;
		int W = 0;
		//If the file size is larger than S-GB, the parallelism of LongRepMarker needs 
		//to be controlled to reduce the use of disk space.
		double S = 1.0; 
		//When the file size is larger than S-GB, the number of thread need to be 
		//adjusted from t to T.
		int T = 8; 
		String A = "1";
		String g = "yes";
		String M = "";
		String Q = "";
		String e = "";
		String E = "";
		String c = "1.5";
		String f = "";
		String v = "yes";
		String o = "";
		for (int i = 0; i < args.length; i += 2) {
			String headStr = args[i].substring(1, args[i].length());
			switch (headStr) {
			case "m": {
				m = Integer.parseInt(args[i + 1]);
			}
				;
				break;
			case "r": {
				r = args[i + 1];
			}
				;
				break;
			case "X1": {
				X1 = args[i + 1];
			}
				;
				break;
			case "X2": {
				X2 = args[i + 1];
			}
				;
				break;
			case "X3": {
				X3 = args[i + 1];
			}
				;
				break;
			case "X4": {
				X4 = args[i + 1];
			}
				;
				break;
			case "l": {
				l = args[i + 1];
			}
				;
				break;
			case "k": {
				k = Integer.parseInt(args[i + 1]);
			}
				;
				break;
			case "q1": {
				q1 = args[i + 1];
			}
				;
				break;
			case "q2": {
				q2 = args[i + 1];
			}
				;
				break;
			case "q3": {
				q3 = args[i + 1];
			}
				;
				break;
			case "q4": {
				q4 = args[i + 1];
			}
				;
				break;
			case "q5": {
				q5 = args[i + 1];
			}
				;
				break;
			case "q6": {
				q6 = args[i + 1];
			}
				;
				break;
			case "q7": {
				q7 = args[i + 1];
			}
				;
				break;
			case "q8": {
				q8 = args[i + 1];
			}
				;
				break;
			case "q9": {
				q9 = args[i + 1];
			}
				;
				break;
			case "q10": {
				q10 = args[i + 1];
			}
				;
				break;
			case "M": {
				M = args[i + 1];
			}
				;
				break;
			case "Q": {
				Q = args[i + 1];
			}
				;
				break;
			case "t": {
				t = Integer.parseInt(args[i + 1]);
			}
				;
				break;
			case "T": {
				T = Integer.parseInt(args[i + 1]);
			}
				;
				break;
			case "W": {
				W = Integer.parseInt(args[i + 1]);
			}
				;
				break;
			case "S": {
				S = Double.parseDouble(args[i + 1]);
			}
				;
				break;
			case "A": {
				A = args[i + 1];
			}
				;
				break;
			case "o": {
				o = args[i + 1];
			}
				;
				break;
			case "g": {
				g = args[i + 1];
			}
				;
				break;
			case "f": {
				f = args[i + 1];
			}
				;
				break;
			case "v": {
				v = args[i + 1];
			}
				;
				break;
			case "E": {
				E = args[i + 1];
			}
				;
				break;
			case "e": {
				e = args[i + 1];
			}
				;
				break;
			case "c": {
				c = args[i + 1];
			}
				;
				break;
			}
		}
		System.out.println("-------------------------------------------------------------------------------------------");
		System.out.println("Copyright (C) 2019 Jianxin Wang(jxwang@mail.csu.edu.cn), Xingyu Liao(liaoxingyu@csu.edu.cn)"
				+ "\n" + "School of Computer Science and Engineering" + "\n" + "Central South University" + "\n"
				+ "ChangSha" + "CHINA, 410083" + "\n");
		File directory = new File(".");
		String ParentPath = directory.getCanonicalPath();
		// Delete folders.
		File AssPath = new File(ParentPath + "/readfile/Denovo");
		if (AssPath.exists()) {
			CommonClass.deletePath(AssPath);
		}
		File readFileDirectoy = new File(ParentPath + "/readfile");
		if (readFileDirectoy.exists()) {
			CommonClass.delAllFile(readFileDirectoy);
		}
		File logFileDirectoy = new File(ParentPath + "/Log");
		if (logFileDirectoy.exists()) {
			CommonClass.delAllFile(logFileDirectoy);
		}
		File alignFileDirectoy = new File(ParentPath + "/alignment");
		if (alignFileDirectoy.exists()) {
			CommonClass.delAllFile(alignFileDirectoy);
		}
		File finalFileDirectoy = new File(ParentPath + "/Results");
		if (finalFileDirectoy.exists()) {
			CommonClass.delAllFile(finalFileDirectoy);
		}
		// System Start.
		System.out.println("Step01: Parameters configuration");
		String SchemaInfo = "de novo";
		if (!r.equals("")) {
			SchemaInfo = "reference";
		}
		System.out.println("====================================System settings========================================");
		System.out.println("  Operation Schema = [ " + SchemaInfo + " mode ]");
		System.out.println("  The Minimum Size of Repeat  = [ " + m + "bp ]");
		System.out.println("  The K-mer Size  = [ " + k + "bp ]");
		System.out.println("  Threads  = [ " + t + " ]");
		System.out.println("  Storage control = [ S=" + S + ", T=" + T + " ] ");
		if (A.equals("1")) {
			System.out.println("  Local Alignment Mode  = [ Fast ]");
		}
		if (A.equals("2")) {
			System.out.println("  Local Alignment Mode  = [ Sensitive ]");
		}
		if (A.equals("3")) {
			System.out.println("  Local Alignment Mode  = [ Very Sensitive ]");
		}
		if (!(f.equals(""))) {
			System.out.println("  [Setting] The reference sequence used for evalution  = [ " + f + " ]");
		} else {
			System.out.println("  [default] The reference sequence used for evalution  = [ Not available ]");
		}
		if (M.equals("yes")) {
			System.out.println(
					"  [Setting] Accuracy evalution of the detection results: [ Multiple-Sequence aligner (Minimap2) ]");
		} else {
			System.out.println("  [default] Accuracy evalution of the detection results: [ Not available ]");
		}
		if (Q.equals("yes")) {
			System.out.println("  [Setting] Size evalution of the detection results: [ N50, N75, N90 ]");
		} else {
			System.out.println("  [default] Size evalution of the detection results: [ Not available ]");
		}
		if (!o.equals("")) {
			System.out.println("  [Setting] Output path = [ " + o + " ]");
		} else {
			o = ParentPath + "/Results";
			System.out.println("  [default] Output path = [ " + o + " ]");
		}
		// Clear files.
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
		File H5File = new File(ParentPath + "/OverlapRegions.h5");
		if (H5File.exists()) {
			CommonClass.deleteFile(H5File);
		}
		File dskFiles = new File(ParentPath + "/read.txt");
		if (dskFiles.exists()) {
			CommonClass.deleteFile(dskFiles);
		}
		File H5File2 = new File(ParentPath + "/Reads.h5");
		if (H5File2.exists()) {
			CommonClass.deleteFile(H5File2);
		}
		File dskFiles2 = new File(ParentPath + "/Reads.txt");
		if (dskFiles2.exists()) {
			CommonClass.deleteFile(dskFiles2);
		}
		File mmiFile1 = new File(ParentPath + "/alignment/ref_evalution.mmi");
		if (mmiFile1.exists()) {
			CommonClass.deleteFile(mmiFile1);
		}
		File mmiFile2 = new File(ParentPath + "/alignment/ref_Align.mmi");
		if (mmiFile2.exists()) {
			CommonClass.deleteFile(mmiFile2);
		}
		File mmiFile3 = new File(ParentPath + "/alignment/ref.mmi");
		if (mmiFile3.exists()) {
			CommonClass.deleteFile(mmiFile3);
		}
		File overlapFile = new File(ParentPath + "/alignment/ovlp.paf");
		if (overlapFile.exists()) {
			CommonClass.deleteFile(overlapFile);
		}
		File VsFile = new File(ParentPath + "/alignment/Variations.Info");
		if (VsFile.exists()) {
			CommonClass.deleteFile(VsFile);
		}
		// Reference mode.
		if (!r.equals("")) {
			System.out.println("  Data Type  = [ Reference genome]");
			System.out.println("  [Setting] Referece path = [ " + r + " ]");
			System.out.println("  [default] Reference length  = [ " + CommonClass.getFileLength(r) + "bp ]");
			System.out
					.println("  [default] Number of fragments in Reference  = [ " + CommonClass.num_fragment(r) + " ]");
			System.out.println(
					"===========================================================================================");
			System.out.println("Step02: Loading reference genome");
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "LinearFile.fasta");
			CommonClass.MergeFastaMultiLines2(r, ParentPath + "/alignment/Oringnal.fasta");
			CommonClass.RewriteFile(ParentPath + "/alignment/Oringnal.fasta",
					ParentPath + "/alignment/LinearFile.fasta", m);
			System.out.println("Step03: Finding overlap sequences between chromosomes");
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "ovlp.paf");
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "ovlp_orginal.paf");
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "ovlp_interim.paf");
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "OverlapRegions.fa");
			Runtime r_overlap = Runtime.getRuntime();
			Process pr_overlap = null;
			String[] cmd_overlap = { "sh", "-c", ParentPath + "/tools/minimap2 -x ava-pb -g 3000 -w 10 -k 19 -m 100 -r 150 -t " + t + " "
							+ ParentPath + "/alignment/LinearFile.fasta " + ParentPath
							+ "/alignment/LinearFile.fasta > " + ParentPath + "/alignment/ovlp.paf" };
			pr_overlap = r_overlap.exec(cmd_overlap);
			pr_overlap.waitFor();
			int LinesOfScaffs = CommonClass.getFileLines(ParentPath + "/alignment/LinearFile.fasta") / 2;
			String SaveChangeLineScaffolds[] = new String[LinesOfScaffs];
			CommonClass.FileToArray2(ParentPath + "/alignment/LinearFile.fasta", SaveChangeLineScaffolds, ">");
			File OverlapFilePath = new File(ParentPath + "/alignment/ovlp.paf");
			ArrayList<String> readmark = new ArrayList<String>();
			ArrayList<String> overlaps = new ArrayList<String>();
			ArrayList<String> HighCoverageRegions = new ArrayList<String>();
			String OverlapStr = "";
			if (OverlapFilePath.isFile() && OverlapFilePath.exists()) {
				InputStreamReader DepthRead = new InputStreamReader(new FileInputStream(OverlapFilePath), "utf-8");
				BufferedReader bufferedReaderDepth = new BufferedReader(DepthRead);
				while ((OverlapStr = bufferedReaderDepth.readLine()) != null) {
					String[] SplitLine = OverlapStr.split("\t|\\s+");
					if (readmark.size() == 0) {
						readmark.add(SplitLine[0]);
						overlaps.add(OverlapStr);
					} else {
						if (readmark.contains(SplitLine[0])) {
							overlaps.add(OverlapStr);
						} else {
							int readID = 0;
							String[] DepthLine = overlaps.get(0).split("\t|\\s+");
							String[] ReadIDLine = DepthLine[0].split("_");
							readID = Integer.parseInt(ReadIDLine[1]);
							int CharArrayLength = SaveChangeLineScaffolds[readID].length();
							char SavePositionArray1[] = new char[CharArrayLength];
							for (int y = 0; y < CharArrayLength; y++) {
								SavePositionArray1[y] = 'N';
							}
							for (int p = 0; p < overlaps.size(); p++) {
								if (overlaps.get(p) != null) {
									String[] PositionLine = overlaps.get(p).split("\t|\\s+");
									int checkQuality = Integer.parseInt(PositionLine[11]);
									if (checkQuality >= 0) {
										int Start_Position = Integer.parseInt(PositionLine[2]);
										int End_Position = Integer.parseInt(PositionLine[3]);
										for (int s = Start_Position; s < End_Position; s++) {
											SavePositionArray1[s] = SaveChangeLineScaffolds[readID].charAt(s);
										}
									}
								}
							}
							String ReplaceStr1 = new String(SavePositionArray1);
							String[] SplitScaffLine1 = ReplaceStr1.split("N");
							for (int e1 = 0; e1 < SplitScaffLine1.length; e1++) {
								if (SplitScaffLine1[e1].length() >= m) {
									HighCoverageRegions.add(SplitScaffLine1[e1]);
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
			int readID = 0;
			String[] DepthLine = overlaps.get(0).split("\t|\\s+");
			String[] ReadIDLine = DepthLine[0].split("_");
			readID = Integer.parseInt(ReadIDLine[1]);
			int CharArrayLength = SaveChangeLineScaffolds[readID].length();
			char SavePositionArray1[] = new char[CharArrayLength];
			for (int y = 0; y < CharArrayLength; y++) {
				SavePositionArray1[y] = 'N';
			}
			for (int p = 0; p < overlaps.size(); p++) {
				if (overlaps.get(p) != null) {
					String[] PositionLine = overlaps.get(p).split("\t|\\s+");
					int checkQuality = Integer.parseInt(PositionLine[11]);
					if (checkQuality >= 0) {
						int Start_Position = Integer.parseInt(PositionLine[2]);
						int End_Position = Integer.parseInt(PositionLine[3]);
						for (int s = Start_Position; s < End_Position; s++) {
							SavePositionArray1[s] = SaveChangeLineScaffolds[readID].charAt(s);
						}
					}
				}
			}
			String ReplaceStr1 = new String(SavePositionArray1);
			String[] SplitScaffLine1 = ReplaceStr1.split("N");
			for (int e1 = 0; e1 < SplitScaffLine1.length; e1++) {
				if (SplitScaffLine1[e1].length() >= m) {
					HighCoverageRegions.add(SplitScaffLine1[e1]);
				}
			}
			readmark.clear();
			overlaps.clear();
			int overlap_Index = 0;
			for (int p = 0; p < HighCoverageRegions.size(); p++) {
				FileWriter writer1 = new FileWriter(ParentPath + "/alignment/OverlapRegions.fa", true);
				writer1.write(">Node_" + (overlap_Index++) + "_" + HighCoverageRegions.get(p).length() + "\n"
						+ HighCoverageRegions.get(p) + "\n");
				writer1.close();
			}
			readmark = null;
			overlaps = null;
			SaveChangeLineScaffolds = null;
			HighCoverageRegions = null;
			System.out.println("Step04: Converting chromosomes to the unique k-mers");
			CommonClass.DelePathFiles(ParentPath, "/OverlapRegions.h5");
			CommonClass.DelePathFiles(ParentPath, "/readfile/KmerFile.fa");
			CommonClass.DelePathFiles(ParentPath, "/read.txt");
			Process p_dsk1 = null;
			Runtime r_dsk1 = Runtime.getRuntime();
			try {
				String cmd1 = ParentPath + "/tools/dsk -file " + ParentPath + "/alignment/OverlapRegions.fa -kmer-size "
						+ k + " -abundance-min 1";
				p_dsk1 = r_dsk1.exec(cmd1);
				p_dsk1.waitFor();
			} catch (Exception e1) {
				System.out.println("Step4 Error:" + e1.getMessage());
				e1.printStackTrace();
			}
			Process p_dsk2 = null;
			Runtime r_dsk2 = Runtime.getRuntime();
			try {
				String cmd2 = ParentPath + "/tools/dsk2ascii -file OverlapRegions.h5 -out read.txt";
				p_dsk2 = r_dsk2.exec(cmd2);
				p_dsk2.waitFor();
			} catch (Exception e1) {
				System.out.println("Step4 Error:" + e1.getMessage());
				e1.printStackTrace();
			}
			int KmerNum = 0;
			String ReadTemp = "";
			File Dskfile = new File(ParentPath + "/read.txt");
			RandomAccessFile aFile = new RandomAccessFile(ParentPath + "/readfile/KmerFile.fa", "rw");
			FileChannel inChannel = aFile.getChannel();
			if (Dskfile.isFile() && Dskfile.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(Dskfile), "utf-8");
				BufferedReader bufferedReaderScaff = new BufferedReader(read);
				while ((ReadTemp = bufferedReaderScaff.readLine()) != null) {
					String[] SplitLine = ReadTemp.split("\t|\\s+");
					String WriteContents = ">Node_" + (KmerNum++) + "\n" + SplitLine[0] + "\n";
					ByteBuffer buf = ByteBuffer.allocate(5000);
					buf.clear();
					buf.put(WriteContents.getBytes());
					buf.flip();
					while (buf.hasRemaining()) {
						inChannel.write(buf);
					}
				}
				bufferedReaderScaff.close();
				inChannel.close();
			}
			aFile.close();
			File dskFile = new File(ParentPath + "/read.txt");
			if (dskFile.exists()) {
				CommonClass.deleteFile(dskFile);
			}
			File h5File = new File(ParentPath + "/OverlapRegions.h5");
			if (h5File.exists()) {
				CommonClass.deleteFile(h5File);
			}
			System.out.print("Step05: Mapping the unique k-mers to reference genome [");
			int SplitSize = 0;
			int RealSize_Fasta = (CommonClass.getFileLines(ParentPath + "/alignment/OverlapRegions.fa")) / 2;
			SplitSize = RealSize_Fasta / t;
			Process p_Split = null;
			Runtime r_Split = Runtime.getRuntime();
			String[] cmd_Split = { "sh", "-c", "split -l  " + 2 * SplitSize + " " + ParentPath
					+ "/alignment/OverlapRegions.fa -d -a 4 " + ParentPath + "/readfile/read_" };
			p_Split = r_Split.exec(cmd_Split);
			p_Split.waitFor();
			int SplitFileIndex = 0;
			File FilePath = new File(ParentPath + "/readfile/");
			if (!FilePath.exists() || !FilePath.isDirectory()) {
				System.out.println("Path:" + ParentPath + "/readfile is empty");
			} else {
				String[] tmpList = FilePath.list();
				if (tmpList != null) {
					for (String aTempList : tmpList) {
						File tmpFile = new File(FilePath, aTempList);
						if (tmpFile.isFile() && tmpFile.getName().startsWith("read_")) {
							String ID_change = "read_" + (SplitFileIndex++);
							File ReNameFile = new File(ParentPath + "/readfile/" + ID_change);
							tmpFile.renameTo(ReNameFile);
							tmpFile.delete();
						}
					}
				}
			}
			File refSeq = new File(r);
			if (CommonClass.getFileSize(refSeq) / (1024 * 1024 * 1024) > S) {
				System.out.println(" S=" + S + ", T=" + T + " ]");
				int Threads = T;
				int flagArray[][] = new int[SplitFileIndex][2];
				for (int h = 0; h < SplitFileIndex; h++) {
					flagArray[h][0] = 0;
					flagArray[h][1] = h;
				}
				int block_size = (int) Math.ceil((double) SplitFileIndex / Threads);
				for (int j = 0; j < block_size; j++) {
					ExecutorService pool_1 = Executors.newFixedThreadPool(Threads + 1);
					int u = 0;
					for (int s = 0; s < SplitFileIndex; s++) {
						if (flagArray[s][0] != 1) {
							if (u++ < Threads) {
								Threads_Ref mt = new Threads_Ref(flagArray[s][1], ParentPath,
										ParentPath + "/readfile/read_" + flagArray[s][1],
										ParentPath + "/readfile/KmerFile.fa", t);
								pool_1.execute(mt);
								flagArray[s][0] = 1;
							} else {
								Threads_Ref mt = new Threads_Ref(flagArray[s][1], ParentPath,
										ParentPath + "/readfile/read_" + flagArray[s][1],
										ParentPath + "/readfile/KmerFile.fa", t);
								pool_1.execute(mt);
								flagArray[s][0] = 1;
								break;
							}
						}
					}
					pool_1.shutdown();
					pool_1.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
				}
				flagArray = null;
			} else {
				System.out.println(" S=" + S + ", T=" + T + " ]");
				int Threads = SplitFileIndex;
				ExecutorService pool_1 = Executors.newFixedThreadPool(Threads);
				for (int s = 0; s < SplitFileIndex; s++) {
					Threads_Ref mt = new Threads_Ref(s, ParentPath, ParentPath + "/readfile/read_" + s,
							ParentPath + "/readfile/KmerFile.fa", t);
					pool_1.execute(mt);
				}
				pool_1.shutdown();
				pool_1.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			}
			String MergeFileString = " ";
			File CoverageFilePath = new File(ParentPath + "/alignment/");
			String[] tmpList3 = CoverageFilePath.list();
			if (tmpList3 != null) {
				for (String aTempList : tmpList3) {
					File tmpFile = new File(CoverageFilePath, aTempList);
					if (tmpFile.isFile() && (tmpFile.getName().startsWith("read_ms_")
							&& (tmpFile.getName().endsWith(".sort.bam")))) {
						MergeFileString += " " + ParentPath + "/alignment/" + tmpFile.getName();
					}
				}
			}
			Runtime b_bamMerge = Runtime.getRuntime();
			Process br_bamMerge = null;
			String[] cmd_bamMerge = { "sh", "-c", ParentPath + "/tools/samtools merge " + ParentPath
					+ "/alignment/Merge.sort.bam " + MergeFileString };
			br_bamMerge = b_bamMerge.exec(cmd_bamMerge);
			br_bamMerge.waitFor();
			Runtime k_depth = Runtime.getRuntime();
			Process pk_depth = null;
			String[] cmd_depth = { "sh", "-c", ParentPath + "/tools/samtools depth  " + ParentPath
					+ "/alignment/Merge.sort.bam > " + ParentPath + "/alignment/Merge.depth" };
			pk_depth = k_depth.exec(cmd_depth);
			pk_depth.waitFor();
			File bamfile_Path = new File(ParentPath + "/alignment/");
			CommonClass.delSpecialFile(bamfile_Path, "read_", ".bam");
			CommonClass.delSpecialFile(bamfile_Path, "read_", ".sort.bam");
			System.out.println("Step06: Generating the high coverage regions");
			CommonClass.generation_HighCovRegions(ParentPath + "/alignment/OverlapRegions.fa",
					ParentPath + "/alignment/Merge.depth", ParentPath + "/alignment/HighCoverageRegions_" + m + ".fa",
					m, k);
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "ref_Align.mmi");
			Runtime pR_index = Runtime.getRuntime();
			Process R_index = null;
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "ref_Align.mmi");
			String[] cmd_pr_Index = { "sh", "-c", ParentPath + "/tools/minimap2 -d " + ParentPath
					+ "/alignment/ref_Align.mmi " + ParentPath + "/alignment/OverlapRegions.fa" };
			R_index = pR_index.exec(cmd_pr_Index);
			R_index.waitFor();
			System.out.print("Step07: Mapping high coverage regions to overlap sequences [");
			CommonClass.mapping_HighCovRegions2Ref(ParentPath, m, 2, r, t);
			System.out.println("Step08: Merging fragments that have duplicate or include relationships");
			if (g.equals("") || g.equals("no")) {
				System.out.println("Mergeing process is skiped.");
			} else {
				CommonClass.merging_relationships(ParentPath, ParentPath + "/alignment/RepeatLib_orginal.fa",
						ParentPath + "/alignment/RepeatLib.fa", k);
			}
			System.out.println("Step09: Generating the final repetitive sequence library and dectection reports");
			CommonClass.generation_reports(ParentPath, r, o);
			System.out.println("===========================================================================================");
			System.out.println("Evaluations:");
			File FinalRepeats = new File(ParentPath + "/alignment/RepeatLib.fa");
			if (FinalRepeats.exists()) {
				if (M.equals("yes") || Q.equals("yes")) {
					if (M.equals("yes")) {
						CommonClass.calculateMul_AlignRatio(ParentPath, f, t);
					}
					if (Q.equals("yes")) {
						CommonClass.calculateN50_N75_N90(ParentPath);
					}
				} else {
					System.out.println("Evaluation is not enabled, please adjust -M and -Q these two parameters.");
				}
			} else {
				System.out.println("There are no fragments longer than or equal to " + m
						+ "bp in the test results. Please adjust the value of parameter m.");
			}
		}
		// NGS+10X.
		else if ((r.equals("")) && ((!X1.equals("") && (!X2.equals("")) && (!q1.equals("") && !q2.equals(""))) || ((X1.equals("") && X2.equals("")) && (l.equals("")) && (!q1.equals("") && !q2.equals(""))))) {
			if((X1.equals("") && X2.equals("")) && (l.equals("")) && (!q1.equals("") && !q2.equals(""))){
				System.out.println("  [Setting] Data Type  = [ NGS Short Reads Only]");
			}
			else
			{
				System.out.println("  [Setting] Data Type  = [ NGS Short Reads + 10X Linked Reads]");
			}
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
			if (!X1.equals("")) {
				System.out.println("  [Setting] 10X Linked Reads (PE1-left) = [ " + X1 + " ]");
			}
			if (!X2.equals("")) {
				System.out.println("  [Setting] 10X Linked Reads (PE1-right) = [ " + X2 + " ]");
			}
			if (!X3.equals("")) {
				System.out.println("  [Setting] 10X Linked Reads (PE2-left) = [ " + X3 + " ]");
			}
			if (!X4.equals("")) {
				System.out.println("  [Setting] 10X Linked Reads (PE2-right) = [ " + X4 + " ]");
			}
			if (!c.equals("")) {
				System.out.println("  [Setting] High coverage threshold for overlaps filtering = [ " + c + " ]");
			}
			if (e.equals("") || e.equals("no")) {
				System.out.println("  [Setting] Short reads error correction  = [  no ]");
			} else {
				System.out.println("  [Setting] Short reads error correction  = [ yes ]");
			}
			if (v.equals("yes")) {
				System.out.println("  [Setting] Variation identification = [ yes ]");
			} else {
				System.out.println("  [Setting] Variation identification = [ no ]");
			}
			System.out.println("===========================================================================================");
			Process p_ErrorCorrection = null;
			Runtime r_ErrorCorrection = Runtime.getRuntime();
			Process p_assemblyRun = null;
			Runtime r_assemblyRun = Runtime.getRuntime();
			try {
		
				System.out.print("Step02: Error correction [");
				String[] SplitLine1 = q1.split("/|\n");
				String full_name1 = SplitLine1[SplitLine1.length - 1];
				String last_name1 = full_name1.substring(0, full_name1.length());
				String f1 = ParentPath + "/readfile/karect_" + last_name1;
				String[] SplitLine2 = q2.split("/|\n");
				String full_name2 = SplitLine2[SplitLine2.length - 1];
				String last_name2 = full_name2.substring(0, full_name2.length());
				String f2 = ParentPath + "/readfile/karect_" + last_name2;
				String[] SplitLine3 = q3.split("/|\n");
				String full_name3 = SplitLine3[SplitLine3.length - 1];
				String last_name3 = full_name3.substring(0, full_name3.length());
				String f3 = ParentPath + "/readfile/karect_" + last_name3;
				String[] SplitLine4 = q4.split("/|\n");
				String full_name4 = SplitLine4[SplitLine4.length - 1];
				String last_name4 = full_name4.substring(0, full_name4.length());
				String f4 = ParentPath + "/readfile/karect_" + last_name4;
				String[] SplitLine5 = q5.split("/|\n");
				String full_name5 = SplitLine5[SplitLine5.length - 1];
				String last_name5 = full_name5.substring(0, full_name5.length());
				String f5 = ParentPath + "/readfile/karect_" + last_name5;
				String[] SplitLine6 = q6.split("/|\n");
				String full_name6 = SplitLine6[SplitLine6.length - 1];
				String last_name6 = full_name6.substring(0, full_name6.length());
				String f6 = ParentPath + "/readfile/karect_" + last_name6;
				String[] SplitLine7 = q7.split("/|\n");
				String full_name7 = SplitLine7[SplitLine7.length - 1];
				String last_name7 = full_name7.substring(0, full_name7.length());
				String f7 = ParentPath + "/readfile/karect_" + last_name7;
				String[] SplitLine8 = q8.split("/|\n");
				String full_name8 = SplitLine8[SplitLine8.length - 1];
				String last_name8 = full_name8.substring(0, full_name8.length());
				String f8 = ParentPath + "/readfile/karect_" + last_name8;
				String[] SplitLine9 = q9.split("/|\n");
				String full_name9 = SplitLine9[SplitLine9.length - 1];
				String last_name9 = full_name9.substring(0, full_name9.length());
				String f9 = ParentPath + "/readfile/karect_" + last_name9;
				String[] SplitLine10 = q10.split("/|\n");
				String full_name10 = SplitLine10[SplitLine10.length - 1];
				String last_name10 = full_name10.substring(0, full_name10.length());
				String f10 = ParentPath + "/readfile/karect_" + last_name10;
				File F1 = new File(f1);
				if (F1.exists())
					CommonClass.deleteFile(F1);
				File F2 = new File(f2);
				if (F2.exists())
					CommonClass.deleteFile(F2);
				File F3 = new File(f3);
				if (F3.exists())
					CommonClass.deleteFile(F3);
				File F4 = new File(f4);
				if (F4.exists())
					CommonClass.deleteFile(F4);
				File F5 = new File(f5);
				if (F5.exists())
					CommonClass.deleteFile(F5);
				File F6 = new File(f6);
				if (F6.exists())
					CommonClass.deleteFile(F6);
				File F7 = new File(f7);
				if (F7.exists())
					CommonClass.deleteFile(F7);
				File F8 = new File(f8);
				if (F8.exists())
					CommonClass.deleteFile(F8);
				File F9 = new File(f9);
				if (F9.exists())
					CommonClass.deleteFile(F9);
				File F10 = new File(f10);
				if (F10.exists())
					CommonClass.deleteFile(F10);
				if (e.equals("yes")) {
					if ((!q1.equals("")) && (!q2.equals(""))) {
						System.out.print(" lib:(pe1-2->");
						String[] cmd_errorCorrection = { "sh", "-c", ParentPath + "/tools/karect -correct -threads=128 -matchtype=hamming -celltype=haploid -tempdir=" + ParentPath + "/readfile/ -resultdir=" + ParentPath + "/readfile/ -inputfile=" + q1 + " -inputfile=" + q2 + " > " + ParentPath + "/readfile/read1.log" };
						p_ErrorCorrection = r_ErrorCorrection.exec(cmd_errorCorrection);
						p_ErrorCorrection.waitFor();
						System.out.print("Corrected)");
					}
					if ((!q3.equals("")) && (!q4.equals(""))) {
						System.out.print(" | lib:(pe:3-4->");
						String[] cmd_errorCorrection = { "sh", "-c",
								ParentPath
										+ "/tools/karect -correct -threads=128 -matchtype=hamming -celltype=haploid -tempdir="
										+ ParentPath + "/readfile/ -resultdir=" + ParentPath + "/readfile/ -inputfile="
										+ q3 + " -inputfile=" + q4 + " > " + ParentPath + "/readfile/read2.log" };
						p_ErrorCorrection = r_ErrorCorrection.exec(cmd_errorCorrection);
						p_ErrorCorrection.waitFor();
						System.out.print("Corrected)");
					}
					if ((!q5.equals("")) && (!q6.equals(""))) {
						System.out.print(" | lib:(pe:5-6->");
						String[] cmd_errorCorrection = { "sh", "-c",
								ParentPath
										+ "/tools/karect -correct -threads=128 -matchtype=hamming -celltype=haploid -tempdir="
										+ ParentPath + "/readfile/ -resultdir=" + ParentPath + "/readfile/ -inputfile="
										+ q5 + " -inputfile=" + q6 + " > " + ParentPath + "/readfile/read3.log" };
						p_ErrorCorrection = r_ErrorCorrection.exec(cmd_errorCorrection);
						p_ErrorCorrection.waitFor();
						System.out.print("Corrected)");
					}
					if ((!q7.equals("")) && (!q8.equals(""))) {
						System.out.print(" | lib:(pe:7-8->");
						String[] cmd_errorCorrection = { "sh", "-c",
								ParentPath
										+ "/tools/karect -correct -threads=128 -matchtype=hamming -celltype=haploid -tempdir="
										+ ParentPath + "/readfile/ -resultdir=" + ParentPath + "/readfile/ -inputfile="
										+ q7 + " -inputfile=" + q8 + " > " + ParentPath + "/readfile/read4.log" };
						p_ErrorCorrection = r_ErrorCorrection.exec(cmd_errorCorrection);
						p_ErrorCorrection.waitFor();
						System.out.print("Corrected)");
					}
					if ((!q9.equals("")) && (!q10.equals(""))) {
						System.out.print(" | lib:(pe:9-10->");
						String[] cmd_errorCorrection = { "sh", "-c",
								ParentPath
										+ "/tools/karect -correct -threads=128 -matchtype=hamming -celltype=haploid -tempdir="
										+ ParentPath + "/readfile/ -resultdir=" + ParentPath + "/readfile/ -inputfile="
										+ q9 + " -inputfile=" + q10 + " > " + ParentPath + "/readfile/read5.log" };
						p_ErrorCorrection = r_ErrorCorrection.exec(cmd_errorCorrection);
						p_ErrorCorrection.waitFor();
						System.out.print("Corrected)");
					}
					System.out.println(" ]");
					CommonClass.DelePathFiles(ParentPath + "/readfile/", "res_graph_b.txt");
					CommonClass.DelePathFiles(ParentPath + "/readfile/", "res_graph_a.txt");
					CommonClass.DelePathFiles(ParentPath + "/readfile/", "input_file.txt");
					CommonClass.DelePathFiles(ParentPath + "/readfile/", "temp_res_" + last_name1);
					CommonClass.DelePathFiles(ParentPath + "/readfile/", "temp_res_" + last_name2);
					CommonClass.DelePathFiles(ParentPath + "/readfile/", "temp_res_" + last_name3);
					CommonClass.DelePathFiles(ParentPath + "/readfile/", "temp_res_" + last_name4);
					CommonClass.DelePathFiles(ParentPath + "/readfile/", "temp_res_" + last_name5);
					CommonClass.DelePathFiles(ParentPath + "/readfile/", "temp_res_" + last_name6);
					CommonClass.DelePathFiles(ParentPath + "/readfile/", "temp_res_" + last_name7);
					CommonClass.DelePathFiles(ParentPath + "/readfile/", "temp_res_" + last_name8);
					CommonClass.DelePathFiles(ParentPath + "/readfile/", "temp_res_" + last_name9);
					CommonClass.DelePathFiles(ParentPath + "/readfile/", "temp_res_" + last_name10);
				} 
				else 
				{
					System.out.println("no | Short reads error correction is skiped ]");
					if (!q1.equals("") && !q2.equals("")) {
						CommonClass.copyFile(q1, f1);
						CommonClass.copyFile(q2, f2);
					}
					if (!q3.equals("") && !q4.equals("")) {
						CommonClass.copyFile(q3, f3);
						CommonClass.copyFile(q4, f4);
					}
					if (!q5.equals("") && !q6.equals("")) {
						CommonClass.copyFile(q5, f5);
						CommonClass.copyFile(q6, f6);
					}
					if (!q7.equals("") && !q8.equals("")) {
						CommonClass.copyFile(q7, f7);
						CommonClass.copyFile(q8, f8);
					}
					if (!q9.equals("") && !q10.equals("")) {
						CommonClass.copyFile(q9, f9);
						CommonClass.copyFile(q10, f10);
					}
				}
				System.out.print("Step03: de novo assembly [ ");
				int max_mem = 100;
				if (W == 0) {
					double totalMem = Runtime.getRuntime().maxMemory();
					int Current_max_mem = (int) (totalMem / (1024 * 1024 * 1024));
					if (Current_max_mem >= max_mem) {
						max_mem = Current_max_mem;
					}
					System.out.print("Max_mem=" + max_mem + "GB |");
				} else {
					max_mem = W;
				}
				File Q1 = new File(f1);
				File Q2 = new File(f3);
				File Q3 = new File(f5);
				File Q4 = new File(f7);
				File Q5 = new File(f9);
				if (Q1.exists() && !Q2.exists()) {
					if (!X1.equals("") || !X3.equals("")) {
						if (!X1.equals("") && X3.equals("")) {
							System.out.print(" Assembly:(pe:1-2)(10x_pe:1-2)");
							String[] cmd_assemblyRun = { "sh", "-c",
									ParentPath + "/tools/SPAdes-3.15.2/bin/spades.py --only-assembler -t 64 -m "
											+ max_mem + " --pe1-1 " + f1 + " --pe1-2 " + f2 + " --pe2-1 " + X1
											+ " --pe2-2 " + X2 + " -o " + ParentPath + "/readfile/ > " + ParentPath
											+ "/log/SPAdeslog.out" };
							p_assemblyRun = r_assemblyRun.exec(cmd_assemblyRun);
							p_assemblyRun.waitFor();
							File scaffFile_before = new File(ParentPath + "/readfile/scaffolds.fasta");
							if (!scaffFile_before.exists()) {
								String Assfile1 = "";
								List<String> paths = new ArrayList<String>();
								paths = CommonClass.getAllFilePaths(new File(ParentPath + "/readfile/"), paths);
								for (String path : paths) {
									if (path.contains("before_rr.fasta"))
										Assfile1 = path;
								}
								if(!Assfile1.equals("")){
									CommonClass.copyFile(Assfile1, ParentPath + "/readfile/scaffolds.fasta");
								}
								else
								{
									System.out.println("\nThe assembly process ended abnormally!"
											+ "\nPlease check the heap memory configuration of LongRepMarker!"
											+ "\nUsers can use the -Xmx parameter to reconfigure the heap memory space of LongRepMarker!"
											+ "\nPlease refer to " + ParentPath + "/log/SPAdeslog.out"
											+ " for detailed error information.\n\n");
								}
							}
						}
						if (!X1.equals("") && !X3.equals("")) {
							System.out.print(" Assembly:(pe:1-2)(10x_pe:1-2)(10x_pe:3-4)");
							String[] cmd_assemblyRun = { "sh", "-c",
									ParentPath + "/tools/SPAdes-3.15.2/bin/spades.py --only-assembler -t 64 -m "
											+ max_mem + " --pe1-1 " + f1 + " --pe1-2 " + f2 + " --pe2-1 " + X1
											+ " --pe2-2 " + X2 + " --pe3-1 " + X3 + " --pe3-2 " + X4 + " -o "
											+ ParentPath + "/readfile/ > " + ParentPath + "/log/SPAdeslog.out" };
							p_assemblyRun = r_assemblyRun.exec(cmd_assemblyRun);
							p_assemblyRun.waitFor();
							File scaffFile_before = new File(ParentPath + "/readfile/scaffolds.fasta");
							if (!scaffFile_before.exists()) {
								String Assfile1 = "";
								List<String> paths = new ArrayList<String>();
								paths = CommonClass.getAllFilePaths(new File(ParentPath + "/readfile/"), paths);
								for (String path : paths) {
									if (path.contains("before_rr.fasta")){
										Assfile1 = path;
									}
								}
								if(!Assfile1.equals("")){
									CommonClass.copyFile(Assfile1, ParentPath + "/readfile/scaffolds.fasta");
								}
								else
								{
									System.out.println("\nThe assembly process ended abnormally!"
											+ "\nPlease check the heap memory configuration of LongRepMarker!"
											+ "\nUsers can use the -Xmx parameter to reconfigure the heap memory space of LongRepMarker!"
											+ "\nPlease refer to " + ParentPath + "/log/SPAdeslog.out"
											+ " for detailed error information.\n\n");
								}	
							} 
						}
					} 
					else 
					{
						System.out.print(" Assembly:(pe:1-2)(10x_pe:None)");
						String[] cmd_assemblyRun = { "sh", "-c",
								ParentPath + "/tools/SPAdes-3.15.2/bin/spades.py --only-assembler -t 64 -m " + max_mem
										+ " --pe1-1 " + f1 + " --pe1-2 " + f2 + " -o " + ParentPath + "/readfile/ > "
										+ ParentPath + "/log/SPAdeslog.out" };
						p_assemblyRun = r_assemblyRun.exec(cmd_assemblyRun);
						p_assemblyRun.waitFor();
						File scaffFile_before = new File(ParentPath + "/readfile/scaffolds.fasta");
						if (!scaffFile_before.exists()) {
							String Assfile1 = "";
							List<String> paths = new ArrayList<String>();
							paths = CommonClass.getAllFilePaths(new File(ParentPath + "/readfile/"), paths);
							for (String path : paths) {
								if (path.contains("before_rr.fasta")){
									Assfile1 = path;
								}
							}
							if(!Assfile1.equals("")){
								CommonClass.copyFile(Assfile1, ParentPath + "/readfile/scaffolds.fasta");
							}
							else
							{
								System.out.println("\nThe assembly process ended abnormally!"
										+ "\nPlease check the heap memory configuration of LongRepMarker!"
										+ "\nUsers can use the -Xmx parameter to reconfigure the heap memory space of LongRepMarker!"
										+ "\nPlease refer to " + ParentPath + "/log/SPAdeslog.out"
										+ " for detailed error information.\n\n");
							}	
						} 
					}
					int file_index = 0;
					String readtemp1 = "";
					String readtemp2 = "";
					try {
						String encoding = "utf-8";
						File file1 = new File(f1);
						File file2 = new File(f2);
						if (file1.exists() && file2.exists()) {
							InputStreamReader read1 = new InputStreamReader(new FileInputStream(file1), encoding);
							InputStreamReader read2 = new InputStreamReader(new FileInputStream(file2), encoding);
							BufferedReader bufferedReader1 = new BufferedReader(read1);
							BufferedReader bufferedReader2 = new BufferedReader(read2);
							OutputStreamWriter write = new OutputStreamWriter(
									new FileOutputStream(ParentPath + "/readfile/Reads.fa"), encoding);
							BufferedWriter bufferedWriter = new BufferedWriter(write);
							while ((bufferedReader1.readLine()) != null && (bufferedReader2.readLine()) != null) {
								readtemp1 = bufferedReader1.readLine();
								readtemp2 = bufferedReader2.readLine();
								bufferedReader1.readLine();
								bufferedReader2.readLine();
								bufferedReader1.readLine();
								bufferedReader2.readLine();
								bufferedWriter.write(">" + (file_index++) + "\n" + readtemp1 + "\n>" + (file_index++)
										+ "\n" + readtemp2 + "\n");
							}
							bufferedReader1.close();
							bufferedReader2.close();
							bufferedWriter.close();
						} else {
							System.out.println("File is not exist!");
						}
					} catch (Exception e1) {
						System.out.println("Error liaoxingyu");
						e1.printStackTrace();
					}
				}
				if (Q1.exists() && Q2.exists() && !Q3.exists()) {
					if (!X1.equals("") || !X3.equals("")) {
						if (!X1.equals("") && X3.equals("")) {
							System.out.print(" Assembly:(pe:1-2)(pe:3-4)(10x_pe:1-2)");
							String[] cmd_assemblyRun = { "sh", "-c",
									ParentPath + "/tools/SPAdes-3.15.2/bin/spades.py --only-assembler -t 64 -m "
											+ max_mem + " --pe1-1 " + f1 + " --pe1-2 " + f2 + " --mp1-1 " + f3
											+ " --mp1-2 " + f4 + " --pe2-1 " + X1 + " --pe2-2 " + X2 + " -o "
											+ ParentPath + "/readfile/ > " + ParentPath + "/log/SPAdeslog.out" };
							p_assemblyRun = r_assemblyRun.exec(cmd_assemblyRun);
							p_assemblyRun.waitFor();
							File scaffFile_before = new File(ParentPath + "/readfile/scaffolds.fasta");
							if (!scaffFile_before.exists()) {
								String Assfile1 = "";
								List<String> paths = new ArrayList<String>();
								paths = CommonClass.getAllFilePaths(new File(ParentPath + "/readfile/"), paths);
								for (String path : paths) {
									if (path.contains("before_rr.fasta")){
										Assfile1 = path;
									}
								}
								if(!Assfile1.equals("")){
									CommonClass.copyFile(Assfile1, ParentPath + "/readfile/scaffolds.fasta");
								}
								else
								{
									System.out.println("\nThe assembly process ended abnormally!"
											+ "\nPlease check the heap memory configuration of LongRepMarker!"
											+ "\nUsers can use the -Xmx parameter to reconfigure the heap memory space of LongRepMarker!"
											+ "\nPlease refer to " + ParentPath + "/log/SPAdeslog.out"
											+ " for detailed error information.\n\n");
								}	
							} 
						}
						if (!X1.equals("") && !X3.equals("")) {
							System.out.print(" Assembly:(pe:1-2)(pe:3-4)(10x_pe:1-2)(10x_pe:3-4)");
							String[] cmd_assemblyRun = { "sh", "-c",
									ParentPath + "/tools/SPAdes-3.15.2/bin/spades.py --only-assembler -t 64 -m "
											+ max_mem + " --pe1-1 " + f1 + " --pe1-2 " + f2 + " --mp1-1 " + f3
											+ " --mp1-2 " + f4 + " --pe2-1 " + X1 + " --pe2-2 " + X2 + " --pe3-1 " + X3
											+ " --pe3-2 " + X4 + " -o " + ParentPath + "/readfile/ > " + ParentPath
											+ "/log/SPAdeslog.out" };
							p_assemblyRun = r_assemblyRun.exec(cmd_assemblyRun);
							p_assemblyRun.waitFor();
							File scaffFile_before = new File(ParentPath + "/readfile/scaffolds.fasta");
							if (!scaffFile_before.exists()) {
								String Assfile1 = "";
								List<String> paths = new ArrayList<String>();
								paths = CommonClass.getAllFilePaths(new File(ParentPath + "/readfile/"), paths);
								for (String path : paths) {
									if (path.contains("before_rr.fasta")){
										Assfile1 = path;
									}
								}
								if(!Assfile1.equals("")){
									CommonClass.copyFile(Assfile1, ParentPath + "/readfile/scaffolds.fasta");
								}
								else
								{
									System.out.println("\nThe assembly process ended abnormally!"
											+ "\nPlease check the heap memory configuration of LongRepMarker!"
											+ "\nUsers can use the -Xmx parameter to reconfigure the heap memory space of LongRepMarker!"
											+ "\nPlease refer to " + ParentPath + "/log/SPAdeslog.out"
											+ " for detailed error information.\n\n");
								}	
							} 
						}
					} 
					else 
					{
						System.out.print(" Assembly:(pe:1-2, pe:3-4)(10x_pe:None)");
						String[] cmd_assemblyRun = { "sh", "-c",
								ParentPath + "/tools/SPAdes-3.15.2/bin/spades.py --only-assembler -t 64 -m " + max_mem
										+ " --pe1-1 " + f1 + " --pe1-2 " + f2 + " --mp1-1 " + f3 + " --mp1-2 " + f4
										+ " -o " + ParentPath + "/readfile/ > " + ParentPath + "/log/SPAdeslog.out" };
						p_assemblyRun = r_assemblyRun.exec(cmd_assemblyRun);
						p_assemblyRun.waitFor();
						File scaffFile_before = new File(ParentPath + "/readfile/scaffolds.fasta");
						if (!scaffFile_before.exists()) {
							String Assfile1 = "";
							List<String> paths = new ArrayList<String>();
							paths = CommonClass.getAllFilePaths(new File(ParentPath + "/readfile/"), paths);
							for (String path : paths) {
								if (path.contains("before_rr.fasta")){
									Assfile1 = path;
								}
							}
							if(!Assfile1.equals("")){
								CommonClass.copyFile(Assfile1, ParentPath + "/readfile/scaffolds.fasta");
							}
							else
							{
								System.out.println("\nThe assembly process ended abnormally!"
										+ "\nPlease check the heap memory configuration of LongRepMarker!"
										+ "\nUsers can use the -Xmx parameter to reconfigure the heap memory space of LongRepMarker!"
										+ "\nPlease refer to " + ParentPath + "/log/SPAdeslog.out"
										+ " for detailed error information.\n\n");
							}	
						} 
					}
					int file_index = 0;
					String readtemp1 = "";
					String readtemp2 = "";
					try {
						String encoding = "utf-8";
						File file1 = new File(f1);
						File file2 = new File(f2);
						if (file1.exists() && file2.exists()) {
							InputStreamReader read1 = new InputStreamReader(new FileInputStream(file1), encoding);
							InputStreamReader read2 = new InputStreamReader(new FileInputStream(file2), encoding);
							BufferedReader bufferedReader1 = new BufferedReader(read1);
							BufferedReader bufferedReader2 = new BufferedReader(read2);
							OutputStreamWriter write = new OutputStreamWriter(
									new FileOutputStream(ParentPath + "/readfile/Reads.fa"), encoding);
							BufferedWriter bufferedWriter = new BufferedWriter(write);
							while ((bufferedReader1.readLine()) != null && (bufferedReader2.readLine()) != null) {
								readtemp1 = bufferedReader1.readLine();
								readtemp2 = bufferedReader2.readLine();
								bufferedReader1.readLine();
								bufferedReader2.readLine();
								bufferedReader1.readLine();
								bufferedReader2.readLine();
								bufferedWriter.write(">" + (file_index++) + "\n" + readtemp1 + "\n>" + (file_index++)
										+ "\n" + readtemp2 + "\n");
							}
							bufferedReader1.close();
							bufferedReader2.close();
							bufferedWriter.close();
						} else {
							System.out.println("File is not exist!");
						}
					} catch (Exception e1) {
						System.out.println("Error liaoxingyu");
						e1.printStackTrace();
					}
				}
				if (Q1.exists() && Q2.exists() && Q3.exists() && !Q4.exists()) {
					if (!X1.equals("") || !X3.equals("")) {
						if (!X1.equals("") && X3.equals("")) {
							System.out.print(" Assembly:(pe:1-2)(pe:3-4)(pe:5-6)(10x_pe:1-2)");
							String[] cmd_assemblyRun = { "sh", "-c",
									ParentPath + "/tools/SPAdes-3.15.2/bin/spades.py --only-assembler -t 64 -m "
											+ max_mem + " --pe1-1 " + f1 + " --pe1-2 " + f2 + " --mp1-1 " + f3
											+ " --mp1-2 " + f4 + " --mp2-1 " + f5 + " --mp2-2 " + f6 + " --pe2-1 " + X1
											+ " --pe2-2 " + X2 + " -o " + ParentPath + "/readfile/ > " + ParentPath
											+ "/log/SPAdeslog.out" };
							p_assemblyRun = r_assemblyRun.exec(cmd_assemblyRun);
							p_assemblyRun.waitFor();
							File Scaffolds_file = new File(ParentPath + "/readfile/scaffolds.fasta");
							File scaffFile_before = new File(ParentPath + "/readfile/scaffolds.fasta");
							if (!scaffFile_before.exists()) {
								String Assfile1 = "";
								List<String> paths = new ArrayList<String>();
								paths = CommonClass.getAllFilePaths(new File(ParentPath + "/readfile/"), paths);
								for (String path : paths) {
									if (path.contains("before_rr.fasta")){
										Assfile1 = path;
									}
								}
								if(!Assfile1.equals("")){
									CommonClass.copyFile(Assfile1, ParentPath + "/readfile/scaffolds.fasta");
								}
								else
								{
									System.out.println("\nThe assembly process ended abnormally!"
											+ "\nPlease check the heap memory configuration of LongRepMarker!"
											+ "\nUsers can use the -Xmx parameter to reconfigure the heap memory space of LongRepMarker!"
											+ "\nPlease refer to " + ParentPath + "/log/SPAdeslog.out"
											+ " for detailed error information.\n\n");
								}	
							} 
						}
						if (!X1.equals("") && !X3.equals("")) {
							System.out.print(" Assembly:(pe:1-2)(pe:3-4)(pe:5-6)(10x_pe:1-2)(10x_pe:3-4)");
							String[] cmd_assemblyRun = { "sh", "-c",
									ParentPath + "/tools/SPAdes-3.15.2/bin/spades.py --only-assembler -t 64 -m "
											+ max_mem + " --pe1-1 " + f1 + " --pe1-2 " + f2 + " --mp1-1 " + f3
											+ " --mp1-2 " + f4 + " --mp2-1 " + f5 + " --mp2-2 " + f6 + " --pe2-1 " + X1
											+ " --pe2-2 " + X2 + " --pe3-1 " + X3 + " --pe3-2 " + X4 + " -o "
											+ ParentPath + "/readfile/ > " + ParentPath + "/log/SPAdeslog.out" };
							p_assemblyRun = r_assemblyRun.exec(cmd_assemblyRun);
							p_assemblyRun.waitFor();
							File scaffFile_before = new File(ParentPath + "/readfile/scaffolds.fasta");
							if (!scaffFile_before.exists()) {
								String Assfile1 = "";
								List<String> paths = new ArrayList<String>();
								paths = CommonClass.getAllFilePaths(new File(ParentPath + "/readfile/"), paths);
								for (String path : paths) {
									if (path.contains("before_rr.fasta")){
										Assfile1 = path;
									}
								}
								if(!Assfile1.equals("")){
									CommonClass.copyFile(Assfile1, ParentPath + "/readfile/scaffolds.fasta");
								}
								else
								{
									System.out.println("\nThe assembly process ended abnormally!"
											+ "\nPlease check the heap memory configuration of LongRepMarker!"
											+ "\nUsers can use the -Xmx parameter to reconfigure the heap memory space of LongRepMarker!"
											+ "\nPlease refer to " + ParentPath + "/log/SPAdeslog.out"
											+ " for detailed error information.\n\n");
								}	
							} 
						}
					} 
					else 
					{
						System.out.print(" Assembly:(pe:1-2, pe:3-4, pe:5-6)(10x_pe:None)");
						String[] cmd_assemblyRun = { "sh", "-c",
								ParentPath + "/tools/SPAdes-3.15.2/bin/spades.py --only-assembler -t 64 -m " + max_mem
										+ " --pe1-1 " + f1 + " --pe1-2 " + f2 + " --mp1-1 " + f3 + " --mp1-2 " + f4
										+ " --mp2-1 " + f5 + " --mp2-2 " + f6 + " -o " + ParentPath + "/readfile/ > "
										+ ParentPath + "/log/SPAdeslog.out" };
						p_assemblyRun = r_assemblyRun.exec(cmd_assemblyRun);
						p_assemblyRun.waitFor();
						File scaffFile_before = new File(ParentPath + "/readfile/scaffolds.fasta");
						if (!scaffFile_before.exists()) {
							String Assfile1 = "";
							List<String> paths = new ArrayList<String>();
							paths = CommonClass.getAllFilePaths(new File(ParentPath + "/readfile/"), paths);
							for (String path : paths) {
								if (path.contains("before_rr.fasta")){
									Assfile1 = path;
								}
							}
							if(!Assfile1.equals("")){
								CommonClass.copyFile(Assfile1, ParentPath + "/readfile/scaffolds.fasta");
							}
							else
							{
								System.out.println("\nThe assembly process ended abnormally!"
										+ "\nPlease check the heap memory configuration of LongRepMarker!"
										+ "\nUsers can use the -Xmx parameter to reconfigure the heap memory space of LongRepMarker!"
										+ "\nPlease refer to " + ParentPath + "/log/SPAdeslog.out"
										+ " for detailed error information.\n\n");
							}	
						}  
					}
					int file_index = 0;
					String readtemp1 = "";
					String readtemp2 = "";
					try {
						String encoding = "utf-8";
						File file1 = new File(f1);
						File file2 = new File(f2);
						if (file1.exists() && file2.exists()) {
							InputStreamReader read1 = new InputStreamReader(new FileInputStream(file1), encoding);
							InputStreamReader read2 = new InputStreamReader(new FileInputStream(file2), encoding);
							BufferedReader bufferedReader1 = new BufferedReader(read1);
							BufferedReader bufferedReader2 = new BufferedReader(read2);
							OutputStreamWriter write = new OutputStreamWriter(
									new FileOutputStream(ParentPath + "/readfile/Reads.fa"), encoding);
							BufferedWriter bufferedWriter = new BufferedWriter(write);
							while ((bufferedReader1.readLine()) != null && (bufferedReader2.readLine()) != null) {
								readtemp1 = bufferedReader1.readLine();
								readtemp2 = bufferedReader2.readLine();
								bufferedReader1.readLine();
								bufferedReader2.readLine();
								bufferedReader1.readLine();
								bufferedReader2.readLine();
								bufferedWriter.write(">" + (file_index++) + "\n" + readtemp1 + "\n>" + (file_index++)
										+ "\n" + readtemp2 + "\n");
							}
							bufferedReader1.close();
							bufferedReader2.close();
							bufferedWriter.close();
						} else {
							System.out.println("File is not exist!");
						}
					} catch (Exception e1) {
						System.out.println("Error liaoxingyu");
						e1.printStackTrace();
					}
				}
				if (Q1.exists() && Q2.exists() && Q3.exists() && Q4.exists() && !Q5.exists()) {
					if (!X1.equals("") || !X3.equals("")) {
						if (!X1.equals("") && X3.equals("")) {
							System.out.print(" Assembly:(pe:1-2)(pe:3-4)(pe:5-6)(pe:7-8)(10x_pe:1-2)");
							String[] cmd_assemblyRun = { "sh", "-c",
									ParentPath + "/tools/SPAdes-3.15.2/bin/spades.py --only-assembler -t 64 -m "
											+ max_mem + " --pe1-1 " + f1 + " --pe1-2 " + f2 + " --mp1-1 " + f3
											+ " --mp1-2 " + f4 + " --mp2-1 " + f5 + " --mp2-2 " + f6 + " --mp3-1 " + f7
											+ " --mp3-2 " + f8 + " --pe2-1 " + X1 + " --pe2-2 " + X2 + " -o "
											+ ParentPath + "/readfile/ > " + ParentPath + "/log/SPAdeslog.out" };
							p_assemblyRun = r_assemblyRun.exec(cmd_assemblyRun);
							p_assemblyRun.waitFor();
							File scaffFile_before = new File(ParentPath + "/readfile/scaffolds.fasta");
							if (!scaffFile_before.exists()) {
								String Assfile1 = "";
								List<String> paths = new ArrayList<String>();
								paths = CommonClass.getAllFilePaths(new File(ParentPath + "/readfile/"), paths);
								for (String path : paths) {
									if (path.contains("before_rr.fasta")){
										Assfile1 = path;
									}
								}
								if(!Assfile1.equals("")){
									CommonClass.copyFile(Assfile1, ParentPath + "/readfile/scaffolds.fasta");
								}
								else
								{
									System.out.println("\nThe assembly process ended abnormally!"
											+ "\nPlease check the heap memory configuration of LongRepMarker!"
											+ "\nUsers can use the -Xmx parameter to reconfigure the heap memory space of LongRepMarker!"
											+ "\nPlease refer to " + ParentPath + "/log/SPAdeslog.out"
											+ " for detailed error information.\n\n");
								}	
							} 
						}
						if (!X1.equals("") && !X3.equals("")) {
							System.out.print(" Assembly:(pe:1-2)(pe:3-4)(pe:5-6)(pe:7-8)(10x_pe:1-2)(10x_pe:3-4)");
							String[] cmd_assemblyRun = { "sh", "-c",
									ParentPath + "/tools/SPAdes-3.15.2/bin/spades.py --only-assembler -t 64 -m "
											+ max_mem + " --pe1-1 " + f1 + " --pe1-2 " + f2 + " --mp1-1 " + f3
											+ " --mp1-2 " + f4 + " --mp2-1 " + f5 + " --mp2-2 " + f6 + " --mp3-1 " + f7
											+ " --mp3-2 " + f8 + " --pe2-1 " + X1 + " --pe2-2 " + X2 + " --pe3-1 " + X3
											+ " --pe3-2 " + X4 + " -o " + ParentPath + "/readfile/ > " + ParentPath
											+ "/log/SPAdeslog.out" };
							p_assemblyRun = r_assemblyRun.exec(cmd_assemblyRun);
							p_assemblyRun.waitFor();
							File scaffFile_before = new File(ParentPath + "/readfile/scaffolds.fasta");
							if (!scaffFile_before.exists()) {
								String Assfile1 = "";
								List<String> paths = new ArrayList<String>();
								paths = CommonClass.getAllFilePaths(new File(ParentPath + "/readfile/"), paths);
								for (String path : paths) {
									if (path.contains("before_rr.fasta")){
										Assfile1 = path;
									}
								}
								if(!Assfile1.equals("")){
									CommonClass.copyFile(Assfile1, ParentPath + "/readfile/scaffolds.fasta");
								}
								else
								{
									System.out.println("\nThe assembly process ended abnormally!"
											+ "\nPlease check the heap memory configuration of LongRepMarker!"
											+ "\nUsers can use the -Xmx parameter to reconfigure the heap memory space of LongRepMarker!"
											+ "\nPlease refer to " + ParentPath + "/log/SPAdeslog.out"
											+ " for detailed error information.\n\n");
								}	
							} 
						}
					} else {
						System.out.print(" Assembly:(pe:1-2, pe:3-4, pe:5-6, pe:7-8)(10x_pe:None)");
						String[] cmd_assemblyRun = { "sh", "-c",
								ParentPath + "/tools/SPAdes-3.15.2/bin/spades.py --only-assembler -t 64 -m " + max_mem
										+ " --pe1-1 " + f1 + " --pe1-2 " + f2 + " --mp1-1 " + f3 + " --mp1-2 " + f4
										+ " --mp2-1 " + f5 + " --mp2-2 " + f6 + " -o " + ParentPath + "/readfile/ > "
										+ ParentPath + "/log/SPAdeslog.out" };
						p_assemblyRun = r_assemblyRun.exec(cmd_assemblyRun);
						p_assemblyRun.waitFor();
						File scaffFile_before = new File(ParentPath + "/readfile/scaffolds.fasta");
						if (!scaffFile_before.exists()) {
							String Assfile1 = "";
							List<String> paths = new ArrayList<String>();
							paths = CommonClass.getAllFilePaths(new File(ParentPath + "/readfile/"), paths);
							for (String path : paths) {
								if (path.contains("before_rr.fasta")){
									Assfile1 = path;
								}
							}
							if(!Assfile1.equals("")){
								CommonClass.copyFile(Assfile1, ParentPath + "/readfile/scaffolds.fasta");
							}
							else
							{
								System.out.println("\nThe assembly process ended abnormally!"
										+ "\nPlease check the heap memory configuration of LongRepMarker!"
										+ "\nUsers can use the -Xmx parameter to reconfigure the heap memory space of LongRepMarker!"
										+ "\nPlease refer to " + ParentPath + "/log/SPAdeslog.out"
										+ " for detailed error information.\n\n");
							}	
						} 
					}
					int file_index = 0;
					String readtemp1 = "";
					String readtemp2 = "";
					try {
						String encoding = "utf-8";
						File file1 = new File(f1);
						File file2 = new File(f2);
						if (file1.exists() && file2.exists()) {
							InputStreamReader read1 = new InputStreamReader(new FileInputStream(file1), encoding);
							InputStreamReader read2 = new InputStreamReader(new FileInputStream(file2), encoding);
							BufferedReader bufferedReader1 = new BufferedReader(read1);
							BufferedReader bufferedReader2 = new BufferedReader(read2);
							OutputStreamWriter write = new OutputStreamWriter(
									new FileOutputStream(ParentPath + "/readfile/Reads.fa"), encoding);
							BufferedWriter bufferedWriter = new BufferedWriter(write);
							while ((bufferedReader1.readLine()) != null && (bufferedReader2.readLine()) != null) {
								readtemp1 = bufferedReader1.readLine();
								readtemp2 = bufferedReader2.readLine();
								bufferedReader1.readLine();
								bufferedReader2.readLine();
								bufferedReader1.readLine();
								bufferedReader2.readLine();
								bufferedWriter.write(">" + (file_index++) + "\n" + readtemp1 + "\n>" + (file_index++)
										+ "\n" + readtemp2 + "\n");
							}
							bufferedReader1.close();
							bufferedReader2.close();
							bufferedWriter.close();
						} else {
							System.out.println("File is not exist!");
						}
					} catch (Exception e1) {
						System.out.println("Error liaoxingyu");
						e1.printStackTrace();
					}
				}
				if (Q1.exists() && Q2.exists() && Q3.exists() && Q4.exists() && Q5.exists()) {
					if (!X1.equals("") || !X3.equals("")) {
						if (!X1.equals("") && X3.equals("")) {
							System.out.print(" Assembly:(pe:1-2)(pe:3-4)(pe:5-6)(pe:7-8)(pe:9-10)(10x_pe:1-2)");
							String[] cmd_assemblyRun = { "sh", "-c",
									ParentPath + "/tools/SPAdes-3.15.2/bin/spades.py --only-assembler -t 64 -m "
											+ max_mem + " --pe1-1 " + f1 + " --pe1-2 " + f2 + " --mp1-1 " + f3
											+ " --mp1-2 " + f4 + " --mp2-1 " + f5 + " --mp2-2 " + f6 + " --mp3-1 " + f7
											+ " --mp3-2 " + f8 + " --mp4-1 " + f9 + " --mp4-2 " + f10 + " --pe2-1 " + X1
											+ " --pe2-2 " + X2 + " -o " + ParentPath + "/readfile/ > " + ParentPath
											+ "/log/SPAdeslog.out" };
							p_assemblyRun = r_assemblyRun.exec(cmd_assemblyRun);
							p_assemblyRun.waitFor();
							File scaffFile_before = new File(ParentPath + "/readfile/scaffolds.fasta");
							if (!scaffFile_before.exists()) {
								String Assfile1 = "";
								List<String> paths = new ArrayList<String>();
								paths = CommonClass.getAllFilePaths(new File(ParentPath + "/readfile/"), paths);
								for (String path : paths) {
									if (path.contains("before_rr.fasta")){
										Assfile1 = path;
									}
								}
								if(!Assfile1.equals("")){
									CommonClass.copyFile(Assfile1, ParentPath + "/readfile/scaffolds.fasta");
								}
								else
								{
									System.out.println("\nThe assembly process ended abnormally!"
											+ "\nPlease check the heap memory configuration of LongRepMarker!"
											+ "\nUsers can use the -Xmx parameter to reconfigure the heap memory space of LongRepMarker!"
											+ "\nPlease refer to " + ParentPath + "/log/SPAdeslog.out"
											+ " for detailed error information.\n\n");
								}	
							}  
						}
						if (!X1.equals("") && !X3.equals("")) {
							System.out.print(
									" Assembly:(pe:1-2)(pe:3-4)(pe:5-6)(pe:7-8)(pe:9-10)(10x_pe:1-2)(10x_pe:3-4)");
							String[] cmd_assemblyRun = { "sh", "-c",
									ParentPath + "/tools/SPAdes-3.15.2/bin/spades.py --only-assembler -t 64 -m "
											+ max_mem + " --pe1-1 " + f1 + " --pe1-2 " + f2 + " --mp1-1 " + f3
											+ " --mp1-2 " + f4 + " --mp2-1 " + f5 + " --mp2-2 " + f6 + " --mp3-1 " + f7
											+ " --mp3-2 " + f8 + " --mp4-1 " + f9 + " --mp4-2 " + f10 + " --pe2-1 " + X1
											+ " --pe2-2 " + X2 + " --pe3-1 " + X3 + " --pe3-2 " + X4 + " -o "
											+ ParentPath + "/readfile/ > " + ParentPath + "/log/SPAdeslog.out" };
							p_assemblyRun = r_assemblyRun.exec(cmd_assemblyRun);
							p_assemblyRun.waitFor();
							File scaffFile_before = new File(ParentPath + "/readfile/scaffolds.fasta");
							if (!scaffFile_before.exists()) {
								String Assfile1 = "";
								List<String> paths = new ArrayList<String>();
								paths = CommonClass.getAllFilePaths(new File(ParentPath + "/readfile/"), paths);
								for (String path : paths) {
									if (path.contains("before_rr.fasta")){
										Assfile1 = path;
									}
								}
								if(!Assfile1.equals("")){
									CommonClass.copyFile(Assfile1, ParentPath + "/readfile/scaffolds.fasta");
								}
								else
								{
									System.out.println("\nThe assembly process ended abnormally!"
											+ "\nPlease check the heap memory configuration of LongRepMarker!"
											+ "\nUsers can use the -Xmx parameter to reconfigure the heap memory space of LongRepMarker!"
											+ "\nPlease refer to " + ParentPath + "/log/SPAdeslog.out"
											+ " for detailed error information.\n\n");
								}	
							} 
						}
					} 
					else 
					{
						System.out.print(" Assembly:(pe:1-2, pe:3-4, pe:5-6, pe:7-8, pe:9-10)(10x_pe:None)");
						String[] cmd_assemblyRun = { "sh", "-c",
								ParentPath + "/tools/SPAdes-3.15.2/bin/spades.py --only-assembler -t 64 -m " + max_mem
										+ " --pe1-1 " + f1 + " --pe1-2 " + f2 + " --mp1-1 " + f3 + " --mp1-2 " + f4
										+ " --mp2-1 " + f5 + " --mp2-2 " + f6 + " -o " + ParentPath + "/readfile/ > "
										+ ParentPath + "/log/SPAdeslog.out" };
						p_assemblyRun = r_assemblyRun.exec(cmd_assemblyRun);
						p_assemblyRun.waitFor();
						File scaffFile_before = new File(ParentPath + "/readfile/scaffolds.fasta");
						if (!scaffFile_before.exists()) {
							String Assfile1 = "";
							List<String> paths = new ArrayList<String>();
							paths = CommonClass.getAllFilePaths(new File(ParentPath + "/readfile/"), paths);
							for (String path : paths) {
								if (path.contains("before_rr.fasta")){
									Assfile1 = path;
								}
							}
							if(!Assfile1.equals("")){
								CommonClass.copyFile(Assfile1, ParentPath + "/readfile/scaffolds.fasta");
							}
							else
							{
								System.out.println("\nThe assembly process ended abnormally!"
										+ "\nPlease check the heap memory configuration of LongRepMarker!"
										+ "\nUsers can use the -Xmx parameter to reconfigure the heap memory space of LongRepMarker!"
										+ "\nPlease refer to " + ParentPath + "/log/SPAdeslog.out"
										+ " for detailed error information.\n\n");
							}	
						} 
					}
					int file_index = 0;
					String readtemp1 = "";
					String readtemp2 = "";
					try {
						String encoding = "utf-8";
						File file1 = new File(f1);
						File file2 = new File(f2);
						if (file1.exists() && file2.exists()) {
							InputStreamReader read1 = new InputStreamReader(new FileInputStream(file1), encoding);
							InputStreamReader read2 = new InputStreamReader(new FileInputStream(file2), encoding);
							BufferedReader bufferedReader1 = new BufferedReader(read1);
							BufferedReader bufferedReader2 = new BufferedReader(read2);
							OutputStreamWriter write = new OutputStreamWriter(
									new FileOutputStream(ParentPath + "/readfile/Reads.fa"), encoding);
							BufferedWriter bufferedWriter = new BufferedWriter(write);
							while ((bufferedReader1.readLine()) != null && (bufferedReader2.readLine()) != null) {
								readtemp1 = bufferedReader1.readLine();
								readtemp2 = bufferedReader2.readLine();
								bufferedReader1.readLine();
								bufferedReader2.readLine();
								bufferedReader1.readLine();
								bufferedReader2.readLine();
								bufferedWriter.write(">" + (file_index++) + "\n" + readtemp1 + "\n>" + (file_index++)
										+ "\n" + readtemp2 + "\n");
							}
							bufferedReader1.close();
							bufferedReader2.close();
							bufferedWriter.close();
						} else {
							System.out.println("File is not exist!");
						}
					} catch (Exception e1) {
						System.out.println("Error liaoxingyu");
						e1.printStackTrace();
					}
				}
				if (!X1.equals("")) {
					System.out.println(" + 10X linked reads ]");
				} else {
					System.out.println(" : only based on the short reads ]");
				}
			} catch (Exception z) {
				System.out.println("Step03 Error:" + z.getMessage());
				z.printStackTrace();
			}
			File scaffFile1 = new File(ParentPath + "/readfile/scaffolds.fasta");
			if (scaffFile1.exists()) {
				File LinearFile = new File(ParentPath + "/alignment/LinearFile.fasta");
				File OringnalFile = new File(ParentPath + "/alignment/Oringnal.fasta");
				if (LinearFile.exists())
					CommonClass.deleteFile(LinearFile);
				if (OringnalFile.exists())
					CommonClass.deleteFile(OringnalFile);
				CommonClass.MergeFastaMultiLines(ParentPath, ParentPath + "/readfile/scaffolds.fasta",
						ParentPath + "/alignment/Oringnal.fasta");
				CommonClass.RewriteFile(ParentPath + "/alignment/Oringnal.fasta",
						ParentPath + "/alignment/LinearFile.fasta", m);
			} else {
				String Assfile1 = "";
				List<String> paths = new ArrayList<String>();
				paths = CommonClass.getAllFilePaths(new File(ParentPath + "/readfile/"), paths);
				for (String path : paths) {
					if (path.contains("before_rr.fasta"))
						Assfile1 = path;
				}
				File scaff2Reps1 = new File(Assfile1);
				if (scaff2Reps1.exists()) {
					File LinearFile = new File(ParentPath + "/alignment/LinearFile.fasta");
					File OringnalFile = new File(ParentPath + "/alignment/Oringnal.fasta");
					if (LinearFile.exists())
						CommonClass.deleteFile(LinearFile);
					if (OringnalFile.exists())
						CommonClass.deleteFile(OringnalFile);
					CommonClass.copyFile(Assfile1, ParentPath + "/readfile/scaffolds.fasta");
					CommonClass.MergeFastaMultiLines(ParentPath, Assfile1, ParentPath + "/alignment/Oringnal.fasta");
					CommonClass.RewriteFile(ParentPath + "/alignment/Oringnal.fasta",
							ParentPath + "/alignment/LinearFile.fasta", m);
				}
			}
			System.out.println("Step04: Finding overlap sequences between contigs");
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "ovlp.paf");
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "ovlp_interim.paf");
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "ovlp_orginal.paf");
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "OverlapRegions.fa");
			Runtime r_overlap = Runtime.getRuntime();
			Process pr_overlap = null;
			String[] cmd_overlap = { "sh", "-c",
					ParentPath + "/tools/minimap2 -x ava-pb -g 3000 -w 10 -k 27 -m 100 -r 150 -t " + t + " "
							+ ParentPath + "/alignment/LinearFile.fasta " + ParentPath
							+ "/alignment/LinearFile.fasta > " + ParentPath + "/alignment/ovlp_orginal.paf" };
			pr_overlap = r_overlap.exec(cmd_overlap);
			pr_overlap.waitFor();
			CommonClass.overlap_pretreatment(ParentPath + "/alignment/ovlp_orginal.paf",
					ParentPath + "/alignment/ovlp_interim.paf");
			Runtime r_sort = Runtime.getRuntime();
			Process pr_sort = null;
			String[] cmd_sort = { "sh", "-c", "sort -t '\t' -k 1 -n " + ParentPath + "/alignment/ovlp_interim.paf -o "
					+ ParentPath + "/alignment/ovlp.paf" };
			pr_sort = r_sort.exec(cmd_sort);
			pr_sort.waitFor();
			int LinesOfScaffs = CommonClass.getFileLines(ParentPath + "/alignment/LinearFile.fasta") / 2;
			String SaveChangeLineScaffolds[] = new String[LinesOfScaffs];
			CommonClass.FileToArray3(ParentPath + "/alignment/LinearFile.fasta", SaveChangeLineScaffolds, ">");
			File OverlapFilePath = new File(ParentPath + "/alignment/ovlp.paf");
			ArrayList<String> readmark = new ArrayList<String>();
			ArrayList<String> overlaps = new ArrayList<String>();
			ArrayList<String> HighCoverageRegions = new ArrayList<String>();
			String OverlapStr = "";
			if (OverlapFilePath.isFile() && OverlapFilePath.exists()) {
				InputStreamReader DepthRead = new InputStreamReader(new FileInputStream(OverlapFilePath), "utf-8");
				BufferedReader bufferedReaderDepth = new BufferedReader(DepthRead);
				while ((OverlapStr = bufferedReaderDepth.readLine()) != null) {
					String[] SplitLine = OverlapStr.split("\t|\\s+");
					if (readmark.size() == 0) {
						readmark.add(SplitLine[0]);
						overlaps.add(OverlapStr);
					} else {
						if (readmark.contains(SplitLine[0])) {
							overlaps.add(OverlapStr);
						} else {
							int readID = 0;
							String[] DepthLine = overlaps.get(0).split("\t|\\s+");
							String[] ReadIDLine = DepthLine[0].split("_");
							readID = Integer.parseInt(ReadIDLine[1]);
							int CharArrayLength = SaveChangeLineScaffolds[readID].length();
							char SavePositionArray1[] = new char[CharArrayLength];
							for (int y = 0; y < CharArrayLength; y++) {
								SavePositionArray1[y] = 'N';
							}
							for (int p = 0; p < overlaps.size(); p++) {
								if (overlaps.get(p) != null) {
									String[] PositionLine = overlaps.get(p).split("\t|\\s+");
									int checkQuality = Integer.parseInt(PositionLine[11]);
									if (checkQuality >= 0) {
										int Start_Position = Integer.parseInt(PositionLine[2]);
										int End_Position = Integer.parseInt(PositionLine[3]);
										for (int s = Start_Position; s < End_Position; s++) {
											SavePositionArray1[s] = SaveChangeLineScaffolds[readID].charAt(s);
										}
									}
								}
							}
							String ReplaceStr1 = new String(SavePositionArray1);
							String[] SplitScaffLine1 = ReplaceStr1.split("N");
							for (int u = 0; u < SplitScaffLine1.length; u++) {
								if (SplitScaffLine1[u].length() >= m) {
									HighCoverageRegions.add(SplitScaffLine1[u]);
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
			int readID = 0;
			String[] DepthLine = overlaps.get(0).split("\t|\\s+");
			String[] ReadIDLine = DepthLine[0].split("_");
			readID = Integer.parseInt(ReadIDLine[1]);
			int CharArrayLength = SaveChangeLineScaffolds[readID].length();
			char SavePositionArray1[] = new char[CharArrayLength];
			for (int y = 0; y < CharArrayLength; y++) {
				SavePositionArray1[y] = 'N';
			}
			for (int p = 0; p < overlaps.size(); p++) {
				if (overlaps.get(p) != null) {
					String[] PositionLine = overlaps.get(p).split("\t|\\s+");
					int checkQuality = Integer.parseInt(PositionLine[11]);
					if (checkQuality >= 0) {
						int Start_Position = Integer.parseInt(PositionLine[2]);
						int End_Position = Integer.parseInt(PositionLine[3]);
						for (int s = Start_Position; s < End_Position; s++) {
							SavePositionArray1[s] = SaveChangeLineScaffolds[readID].charAt(s);
						}
					}
				}
			}
			String ReplaceStr1 = new String(SavePositionArray1);
			String[] SplitScaffLine1 = ReplaceStr1.split("N");
			for (int u = 0; u < SplitScaffLine1.length; u++) {
				if (SplitScaffLine1[u].length() >= m) {
					HighCoverageRegions.add(SplitScaffLine1[u]);
				}
			}
			readmark.clear();
			overlaps.clear();
			int overlap_Index = 0;
			for (int p = 0; p < HighCoverageRegions.size(); p++) {
				FileWriter writer1 = new FileWriter(ParentPath + "/alignment/OverlapRegions_orginal.fa", true);
				writer1.write(">Node_" + (overlap_Index++) + "_" + HighCoverageRegions.get(p).length() + "\n"
						+ HighCoverageRegions.get(p) + "\n");
				writer1.close();
			}
			readmark = null;
			overlaps = null;
			SaveChangeLineScaffolds = null;
			HighCoverageRegions = null;
			System.out.print("Step05: Estimation of the average coverage of overlap sequences [ ");
			Runtime c_index = Runtime.getRuntime();
			Process cr_index = null;
			Runtime c_mapping = Runtime.getRuntime();
			Process cr_mapping = null;
			Runtime c_sam2bam = Runtime.getRuntime();
			Process cr_sam2bam = null;
			Runtime c_bam2sort = Runtime.getRuntime();
			Process cr_bam2sort = null;
			Runtime c_sort2index = Runtime.getRuntime();
			Process cr_sort2index = null;
			Runtime c_depth = Runtime.getRuntime();
			Process cr_depth = null;
			String[] cmd_index = { "sh", "-c", ParentPath + "/tools/minimap2 -d " + ParentPath
					+ "/alignment/overlap_cov.mmi " + ParentPath + "/alignment/OverlapRegions_orginal.fa" };
			cr_index = c_index.exec(cmd_index);
			cr_index.waitFor();
			String[] cmd_mapping = { "sh", "-c",
					ParentPath + "/tools/minimap2 -a -t" + t + " " + ParentPath + "/alignment/overlap_cov.mmi "
							+ ParentPath + "/alignment/OverlapRegions_orginal.fa > " + ParentPath
							+ "/alignment/OverlapRegions_cov.sam" };
			cr_mapping = c_mapping.exec(cmd_mapping);
			cr_mapping.waitFor();
			String[] cmd_sam2bam = { "sh", "-c", ParentPath + "/tools/samtools  view  -bS  " + ParentPath
					+ "/alignment/OverlapRegions_cov.sam > " + ParentPath + "/alignment/OverlapRegions_cov.bam" };
			cr_sam2bam = c_sam2bam.exec(cmd_sam2bam);
			cr_sam2bam.waitFor();
			String[] cmd_bam2sort = { "sh", "-c", ParentPath + "/tools/samtools  sort  " + ParentPath
					+ "/alignment/OverlapRegions_cov.bam > " + ParentPath + "/alignment/OverlapRegions_cov.sort.bam" };
			cr_bam2sort = c_bam2sort.exec(cmd_bam2sort);
			cr_bam2sort.waitFor();
			String[] cmd_sort2index = { "sh", "-c",
					ParentPath + "/tools/samtools  index  " + ParentPath + "/alignment/OverlapRegions_cov.sort.bam" };
			cr_sort2index = c_sort2index.exec(cmd_sort2index);
			cr_sort2index.waitFor();
			String[] cmd_depth2 = { "sh", "-c",
					ParentPath + "/tools/samtools depth  " + ParentPath + "/alignment/OverlapRegions_cov.sort.bam > "
							+ ParentPath + "/alignment/OverlapRegions_cov.depth" };
			cr_depth = c_depth.exec(cmd_depth2);
			cr_depth.waitFor();
			File bamfile_Path = new File(ParentPath + "/alignment/");
			CommonClass.delCommonFile(bamfile_Path, " ", ".sam");
			CommonClass.delCommonFile(bamfile_Path, " ", ".bam");
			CommonClass.delCommonFile(bamfile_Path, " ", ".sort.bam");
			CommonClass.delCommonFile(bamfile_Path, " ", ".sort.bam.bai");
			String readDepth = "";
			double iterm_count = 0;
			double depthSum = 0;
			double averageDepth = 0;
			File readDepthFile = new File(ParentPath + "/alignment/OverlapRegions_cov.depth");
			if (readDepthFile.isFile() && readDepthFile.exists()) {
				InputStreamReader AlignRead = new InputStreamReader(new FileInputStream(readDepthFile), "utf-8");
				BufferedReader bufferedReaderDepth = new BufferedReader(AlignRead);
				while ((readDepth = bufferedReaderDepth.readLine()) != null) {
					String[] SplitDepthFile = readDepth.split("\t|\\s+");
					depthSum += Double.parseDouble(SplitDepthFile[2]);
					iterm_count++;
				}
				bufferedReaderDepth.close();
			}
			averageDepth = depthSum / iterm_count;
			System.out.println("Coverage:" + df.format(averageDepth) + ". Threshold:"
					+ df.format(averageDepth * Double.parseDouble(c)) + "]");
			System.out.println("Step06: Overlap sequences filtering");
			int overlaps_size = CommonClass.getFileLines(ParentPath + "/alignment/OverlapRegions_orginal.fa") / 2;
			String overlaps_Array[] = new String[overlaps_size];
			CommonClass.FileToArray2(ParentPath + "/alignment/OverlapRegions_orginal.fa", overlaps_Array, ">");
			ArrayList<String> Overlap_Id = new ArrayList<String>();
			ArrayList<String> Overlap_Position = new ArrayList<String>();
			ArrayList<String> Overlap_Coverage = new ArrayList<String>();
			String ReadOverlap_Str = "";
			int Overlap_highcov_Num = 0;
			int Overlap_lowcov_Num = 0;
			File OverlapDepth_FilePath = new File(ParentPath + "/alignment/OverlapRegions_cov.depth");
			if (OverlapDepth_FilePath.isFile() && OverlapDepth_FilePath.exists()) {
				InputStreamReader ReadDepth = new InputStreamReader(new FileInputStream(OverlapDepth_FilePath),
						"utf-8");
				BufferedReader bufferedReaderDepth = new BufferedReader(ReadDepth);
				while ((ReadOverlap_Str = bufferedReaderDepth.readLine()) != null) {
					String[] SplitDepthLine = ReadOverlap_Str.split("\t|\\s+");
					if (Overlap_Id.size() == 0) {
						Overlap_Id.add(SplitDepthLine[0]);
						Overlap_Position.add(SplitDepthLine[1]);
						Overlap_Coverage.add(SplitDepthLine[2]);
					} else {
						if (Overlap_Id.contains(SplitDepthLine[0])) {
							Overlap_Position.add(SplitDepthLine[1]);
							Overlap_Coverage.add(SplitDepthLine[2]);
						} else {
							if (Overlap_Position.size() >= m) {
								int overlap_ID = 0;
								String[] DepthLine1 = Overlap_Id.get(0).split("\t|\\s+");
								String[] ReadIDLine1 = DepthLine1[0].split("_");
								overlap_ID = Integer.parseInt(ReadIDLine1[1]);
								double Cov_Sum = 0;
								double Ave_cov = 0;
								for (int p = 0; p < Overlap_Position.size(); p++) {
									if (Overlap_Coverage.get(p) != null) {
										Cov_Sum += Integer.parseInt(Overlap_Coverage.get(p));
									}
								}
								Ave_cov = Cov_Sum / Overlap_Position.size();
								if (Ave_cov >= averageDepth * Double.parseDouble(c)) {
									FileWriter writer1 = new FileWriter(
											ParentPath + "/alignment/OverlapRegions_high.fa", true);
									writer1.write(">Node_" + (Overlap_highcov_Num++) + "_"
											+ overlaps_Array[overlap_ID].length() + "\n" + overlaps_Array[overlap_ID]
											+ "\n");
									writer1.close();
								} else {
									FileWriter writer1 = new FileWriter(ParentPath + "/alignment/OverlapRegions_low.fa",
											true);
									writer1.write(">Node_" + (Overlap_lowcov_Num++) + "_"
											+ overlaps_Array[overlap_ID].length() + "\n" + overlaps_Array[overlap_ID]
											+ "\n");
									writer1.close();
								}
							}
							Overlap_Id.clear();
							Overlap_Position.clear();
							Overlap_Coverage.clear();
							Overlap_Id.add(SplitDepthLine[0]);
							Overlap_Position.add(SplitDepthLine[1]);
							Overlap_Coverage.add(SplitDepthLine[2]);
						}
					}
				}
				bufferedReaderDepth.close();
			}
			if (Overlap_Position.size() >= m) {
				int overlap_ID = 0;
				String[] DepthLine2 = Overlap_Id.get(0).split("\t|\\s+");
				String[] ReadIDLine2 = DepthLine2[0].split("_");
				overlap_ID = Integer.parseInt(ReadIDLine2[1]);
				double Cov_Sum = 0;
				double Ave_cov = 0;
				for (int p = 0; p < Overlap_Position.size(); p++) {
					if (Overlap_Coverage.get(p) != null) {
						Cov_Sum += Integer.parseInt(Overlap_Coverage.get(p));
					}
				}
				Ave_cov = Cov_Sum / Overlap_Position.size();
				if (Ave_cov >= averageDepth * Double.parseDouble(c)) {
					FileWriter writer1 = new FileWriter(ParentPath + "/alignment/OverlapRegions_high.fa", true);
					writer1.write(">Node_" + (Overlap_highcov_Num++) + "_" + overlaps_Array[overlap_ID].length() + "\n"
							+ overlaps_Array[overlap_ID] + "\n");
					writer1.close();
				} else {
					FileWriter writer1 = new FileWriter(ParentPath + "/alignment/OverlapRegions_low.fa", true);
					writer1.write(">Node_" + (Overlap_lowcov_Num++) + "_" + overlaps_Array[overlap_ID].length() + "\n"
							+ overlaps_Array[overlap_ID] + "\n");
					writer1.close();
				}
			}
			overlaps_Array = null;
			Overlap_Id = null;
			Overlap_Position = null;
			Overlap_Coverage = null;
			Runtime b_index = Runtime.getRuntime();
			Process br_index = null;
			Runtime hr_mapping2 = Runtime.getRuntime();
			Process phr_mapping2 = null;
			String[] br_Index = { "sh", "-c", ParentPath + "/tools/minimap2 -d " + ParentPath
					+ "/alignment/scaffolds.mmi " + ParentPath + "/readfile/scaffolds.fasta" };
			br_index = b_index.exec(br_Index);
			br_index.waitFor();
			String[] cmd_mapping2 = { "sh", "-c",
					ParentPath + "/tools/minimap2 -a -t " + t + " " + ParentPath + "/alignment/scaffolds.mmi "
							+ ParentPath + "/alignment/OverlapRegions_high.fa > " + ParentPath
							+ "/alignment/OverlapRegions_high.sam" };
			phr_mapping2 = hr_mapping2.exec(cmd_mapping2);
			phr_mapping2.waitFor();
			int multiAlignment_index = 0;
			int singleAlignment_index = 0;
			String samStr = "";
			int overlaps_high_size = CommonClass.getFileLines(ParentPath + "/alignment/OverlapRegions_high.fa") / 2;
			String overlaps_high_Array[] = new String[overlaps_high_size];
			CommonClass.FileToArray2(ParentPath + "/alignment/OverlapRegions_high.fa", overlaps_high_Array, ">");
			ArrayList<String> Fragment_Id = new ArrayList<String>();
			ArrayList<String> Alignments_Str = new ArrayList<String>();
			File SamFilePath = new File(ParentPath + "/alignment/OverlapRegions_high.sam");
			if (SamFilePath.isFile() && SamFilePath.exists()) {
				InputStreamReader SamRead = new InputStreamReader(new FileInputStream(SamFilePath), "utf-8");
				BufferedReader bufferedSamReader = new BufferedReader(SamRead);
				while ((samStr = bufferedSamReader.readLine()) != null) {
					if (samStr.charAt(0) != '@') {
						String[] SamLine = samStr.split("\t|\\s+");
						if (!SamLine[1].equals("4")) {
							if (Fragment_Id.size() == 0) {
								Fragment_Id.add(SamLine[0]);
								Alignments_Str.add(samStr);
							} else {
								if (Fragment_Id.contains(SamLine[0])) {
									Alignments_Str.add(samStr);
								} else {
									if (Alignments_Str.size() >= 2) {
										String[] AlignLine = Fragment_Id.get(0).split("_");
										int overlap_ID = Integer.parseInt(AlignLine[1]);
										FileWriter writer1 = new FileWriter(
												ParentPath + "/alignment/RepeatLib_orginal.fa", true);
										writer1.write(">Node_" + (multiAlignment_index++) + "_"
												+ overlaps_high_Array[overlap_ID].length() + "\n"
												+ overlaps_high_Array[overlap_ID] + "\n");
										writer1.close();
										for (int j = 0; j < Alignments_Str.size(); j++) {
											FileWriter writers = new FileWriter(
													ParentPath + "/alignment/multiple_alignment.fa", true);
											writers.write(Alignments_Str.get(j) + "\n");
											writers.close();
										}

									} else {
										String[] AlignLine = Fragment_Id.get(0).split("_");
										int overlap_ID = Integer.parseInt(AlignLine[1]);
										FileWriter writer1 = new FileWriter(
												ParentPath + "/alignment/OverlapRegions_low.fa", true);
										writer1.write(">Node_" + (singleAlignment_index++) + "_"
												+ overlaps_high_Array[overlap_ID].length() + "\n"
												+ overlaps_high_Array[overlap_ID] + "\n");
										writer1.close();
									}
									Fragment_Id.clear();
									Alignments_Str.clear();
									Fragment_Id.add(SamLine[0]);
									Alignments_Str.add(samStr);
								}
							}
						}
					}
				}
				bufferedSamReader.close();
			}
			if (Alignments_Str.size() >= 2 && Fragment_Id.size() >= 1) {
				String[] AlignLine = Fragment_Id.get(0).split("_");
				int overlap_ID = Integer.parseInt(AlignLine[1]);
				FileWriter writer1 = new FileWriter(ParentPath + "/alignment/RepeatLib_orginal.fa", true);
				writer1.write(">Node_" + (multiAlignment_index++) + "_" + overlaps_high_Array[overlap_ID].length()
						+ "\n" + overlaps_high_Array[overlap_ID] + "\n");
				writer1.close();
				for (int j = 0; j < Alignments_Str.size(); j++) {
					FileWriter writers = new FileWriter(ParentPath + "/alignment/multiple_alignment.fa", true);
					writers.write(Alignments_Str.get(j) + "\n");
					writers.close();
				}
			} else {
				if (Fragment_Id.size() >= 1) {
					String[] AlignLine = Fragment_Id.get(0).split("_");
					int overlap_ID = Integer.parseInt(AlignLine[1]);
					FileWriter writer1 = new FileWriter(ParentPath + "/alignment/OverlapRegions_low.fa", true);
					writer1.write(">Node_" + (singleAlignment_index++) + "_" + overlaps_high_Array[overlap_ID].length()
							+ "\n" + overlaps_high_Array[overlap_ID] + "\n");
					writer1.close();
				}
			}
			overlaps_high_Array = null;
			Fragment_Id = null;
			Alignments_Str = null;
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "OverlapRegions_high.sam");
			CommonClass.RewriteFile(ParentPath + "/alignment/OverlapRegions_low.fa",
					ParentPath + "/alignment/OverlapRegions.fa", m);
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "OverlapRegions_low.fa");
			System.out.print("Step07: Conversion of the overlap sequences to unique k-mers [ ");
			CommonClass.DelePathFiles(ParentPath, "OverlapRegions.h5");
			CommonClass.DelePathFiles(ParentPath + "/readfile/", "KmerFile.fa");
			CommonClass.DelePathFiles(ParentPath, "read.txt");
			Process p_dsk1 = null;
			Runtime r_dsk1 = Runtime.getRuntime();
			try {
				File ScaffLength = new File(ParentPath + "/readfile/scaffolds.fasta");
				File ReadLength = new File(ParentPath + "/readfile/Reads.fa");
				double scaffLen = ScaffLength.length();
				double readLen = ReadLength.length();
				int high_fre = (int) (Math.ceil(1.5 * readLen / scaffLen));
				System.out.println("high_fre:" + high_fre+" ]");
				String cmd1_1 = ParentPath + "/tools/dsk -file " + ParentPath
						+ "/alignment/OverlapRegions.fa -kmer-size " + k + " -abundance-min 1";
				String cmd1_2 = ParentPath + "/tools/dsk -file " + ParentPath
						+ "/readfile/Reads.fa -kmer-size 15 -abundance-min " + high_fre;
				p_dsk1 = r_dsk1.exec(cmd1_1);
				p_dsk1 = r_dsk1.exec(cmd1_2);
				p_dsk1.waitFor();
			} catch (Exception z) {
				System.out.println("Step4 Error:" + z.getMessage());
				z.printStackTrace();
			}
			Process p_dsk2 = null;
			Runtime r_dsk2 = Runtime.getRuntime();
			try {
				String cmd2_1 = ParentPath + "/tools/dsk2ascii -file OverlapRegions.h5 -out read.txt";
				String cmd2_2 = ParentPath + "/tools/dsk2ascii -file Reads.h5 -out Reads.txt";
				p_dsk2 = r_dsk2.exec(cmd2_1);
				p_dsk2 = r_dsk2.exec(cmd2_2);
				p_dsk2.waitFor();
			} catch (Exception z) {
				System.out.println("Step4 Error:" + z.getMessage());
				z.printStackTrace();
			}
			int KmerNum = 0;
			String ReadTemp = "";
			File Dskfile = new File(ParentPath + "/read.txt");
			RandomAccessFile aFile = new RandomAccessFile(ParentPath + "/readfile/KmerFile.fa", "rw");
			FileChannel inChannel = aFile.getChannel();
			if (Dskfile.isFile() && Dskfile.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(Dskfile), "utf-8");
				BufferedReader bufferedReaderScaff = new BufferedReader(read);
				while ((ReadTemp = bufferedReaderScaff.readLine()) != null) {
					String[] SplitLine = ReadTemp.split("\t|\\s+");
					String WriteContents = ">Node_" + (KmerNum++) + "\n" + SplitLine[0] + "\n";
					ByteBuffer buf = ByteBuffer.allocate(5000);
					buf.clear();
					buf.put(WriteContents.getBytes());
					buf.flip();
					while (buf.hasRemaining()) {
						inChannel.write(buf);
					}
				}
				bufferedReaderScaff.close();
				inChannel.close();
			}
			aFile.close();
			System.out.println("Step08: Mapping unique k-mers to the overlap sequences");
			int SplitSize = 0;
			int RealSize_Fasta = (CommonClass.getFileLines(ParentPath + "/alignment/OverlapRegions.fa")) / 2;
			if (RealSize_Fasta > t) {
				SplitSize = RealSize_Fasta / t;
				Process p_Split = null;
				Runtime r_Split = Runtime.getRuntime();
				String[] cmd_Split = { "sh", "-c", "split -l  " + 2 * SplitSize + " " + ParentPath
						+ "/alignment/OverlapRegions.fa -d -a 4 " + ParentPath + "/readfile/read_" };
				p_Split = r_Split.exec(cmd_Split);
				p_Split.waitFor();
			} else {
				Process p_Split = null;
				Runtime r_Split = Runtime.getRuntime();
				String[] cmd_Split = { "sh", "-c", "split -l  " + RealSize_Fasta + " " + ParentPath
						+ "/alignment/OverlapRegions.fa -d -a 4 " + ParentPath + "/readfile/read_" };
				p_Split = r_Split.exec(cmd_Split);
				p_Split.waitFor();
			}
			int SplitFileIndex = 0;
			File FilePath = new File(ParentPath + "/readfile/");
			if (!FilePath.exists() || !FilePath.isDirectory()) {
				System.out.println("Path:" + ParentPath + "/readfile is empty");
			} else {
				String[] tmpList = FilePath.list();
				if (tmpList != null) {
					for (String aTempList : tmpList) {
						File tmpFile = new File(FilePath, aTempList);
						if (tmpFile.isFile() && tmpFile.getName().startsWith("read_")) {
							String ID_change = "read_" + (SplitFileIndex++);
							File ReNameFile = new File(ParentPath + "/readfile/" + ID_change);
							tmpFile.renameTo(ReNameFile);
							tmpFile.delete();
						}
					}
				}
			}
			File refSeq = new File(ParentPath + "/alignment/OverlapRegions.fa");
			if (CommonClass.getFileSize(refSeq) / (1024 * 1024 * 1024) > 1) {
				int Threads = 4;
				int flagArray[][] = new int[SplitFileIndex][2];
				for (int h = 0; h < SplitFileIndex; h++) {
					flagArray[h][0] = 0;
					flagArray[h][1] = h;
				}
				int block_size = (int) Math.ceil((double) SplitFileIndex / Threads);
				for (int j = 0; j < block_size; j++) {
					ExecutorService pool_1 = Executors.newFixedThreadPool(Threads + 1);
					int u = 0;
					for (int s = 0; s < SplitFileIndex; s++) {
						if (flagArray[s][0] != 1) {
							if (u++ < Threads) {
								Threads_Ref mt = new Threads_Ref(flagArray[s][1], ParentPath,
										ParentPath + "/readfile/read_" + flagArray[s][1],
										ParentPath + "/readfile/KmerFile.fa", t);
								pool_1.execute(mt);
								flagArray[s][0] = 1;
							} else {
								Threads_Ref mt = new Threads_Ref(flagArray[s][1], ParentPath,
										ParentPath + "/readfile/read_" + flagArray[s][1],
										ParentPath + "/readfile/KmerFile.fa", t);
								pool_1.execute(mt);
								flagArray[s][0] = 1;
								break;
							}
						}
					}
					pool_1.shutdown();
					pool_1.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
				}
				flagArray = null;
			} else {
				int Threads = SplitFileIndex;
				ExecutorService pool_1 = Executors.newFixedThreadPool(Threads);
				for (int s = 0; s < SplitFileIndex; s++) {
					Threads_Ref mt = new Threads_Ref(s, ParentPath, ParentPath + "/readfile/read_" + s,
							ParentPath + "/readfile/KmerFile.fa", t);
					pool_1.execute(mt);
				}
				pool_1.shutdown();
				pool_1.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			}
			String MergeFileString = " ";
			int sortbam_count = 0;
			File CoverageFilePath = new File(ParentPath + "/alignment/");
			String[] tmpList3 = CoverageFilePath.list();
			if (tmpList3 != null) {
				for (String aTempList : tmpList3) {
					File tmpFile = new File(CoverageFilePath, aTempList);
					if (tmpFile.isFile() && (tmpFile.getName().startsWith("read_ms_")
							&& (tmpFile.getName().endsWith(".sort.bam")))) {
						MergeFileString += " " + ParentPath + "/alignment/" + tmpFile.getName();
						sortbam_count++;
					}
				}
			}
			if (sortbam_count < 1) {
				System.out.println("\n\n Multiple sequence alignment ended abnormally!"
						+ "\nPlease check the available disk space!\n\n");
			}
			Runtime b_bamMerge = Runtime.getRuntime();
			Process br_bamMerge = null;
			String[] cmd_bamMerge = { "sh", "-c", ParentPath + "/tools/samtools merge " + ParentPath
					+ "/alignment/Merge.sort.bam " + MergeFileString };
			br_bamMerge = b_bamMerge.exec(cmd_bamMerge);
			br_bamMerge.waitFor();
			Runtime k_depth = Runtime.getRuntime();
			Process pk_depth = null;
			String[] cmd_depth = { "sh", "-c", ParentPath + "/tools/samtools depth  " + ParentPath
					+ "/alignment/Merge.sort.bam > " + ParentPath + "/alignment/Merge.depth" };
			pk_depth = k_depth.exec(cmd_depth);
			pk_depth.waitFor();
			File Alignfile_Path = new File(ParentPath + "/alignment/");
			CommonClass.delSpecialFile(Alignfile_Path, "fre_", ".bt2");
			CommonClass.delSpecialFile(Alignfile_Path, "read_", ".fa");
			CommonClass.delSpecialFile(Alignfile_Path, "read_", ".bam");
			CommonClass.delSpecialFile(Alignfile_Path, "read_", ".sort.bam");
			CommonClass.delSpecialFile(Alignfile_Path, "read_", ".sort.bam.bai");
			System.out.println("Step09: Generation of the high coverage regions");
			CommonClass.generation_HighCovRegions(ParentPath + "/alignment/OverlapRegions.fa",
					ParentPath + "/alignment/Merge.depth", ParentPath + "/alignment/HighCoverageRegions_" + m + ".fa",
					m, k);
			Runtime pR_index = Runtime.getRuntime();
			Process R_index = null;
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "ref_Align.mmi");
			String[] cmd_pr_Index = { "sh", "-c", ParentPath + "/tools/minimap2 -d " + ParentPath
					+ "/alignment/ref_Align.mmi " + ParentPath + "/alignment/LinearFile.fasta" };
			R_index = pR_index.exec(cmd_pr_Index);
			R_index.waitFor();
			System.out.print("Step10: Mapping the high coverage regions to assemblies [");
			CommonClass.mapping_HighCovRegions2Ref(ParentPath, m, 2, r, t);
			CommonClass.RewriteFile(ParentPath + "/alignment/RepeatLib_orginal.fa",
					ParentPath + "/alignment/RepeatLib_interim.fa", m);
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "RepeatLib_orginal.fa");
			System.out.println("Step11: Merging fragments that have duplicate or include relationships");
			if (g.equals("") || g.equals("no")) {
				System.out.println("Mergeing process is skiped.");
			} else {
				CommonClass.merging_relationships(ParentPath, ParentPath + "/alignment/RepeatLib_interim.fa",
						ParentPath + "/alignment/RepeatLib.fa", k);
			}
			System.out.print("Step12: Fragment extension [ ");
			String readtemp = "";
			String encoding = "utf-8";
			HashMap<String, Integer> map_A = new HashMap<String, Integer>();
			HashMap<String, Integer> map_T = new HashMap<String, Integer>();
			HashMap<String, Integer> map_G = new HashMap<String, Integer>();
			HashMap<String, Integer> map_C = new HashMap<String, Integer>();
			File file1 = new File(ParentPath + "/Reads.txt");
			if (file1.exists()) {
				InputStreamReader read1 = new InputStreamReader(new FileInputStream(file1), encoding);
				BufferedReader bufferedReader1 = new BufferedReader(read1);
				while ((readtemp = bufferedReader1.readLine()) != null) {
					if (readtemp.charAt(0) == 'A') {
						String[] Splitp = readtemp.split("\t|\\s+");
						map_A.put(Splitp[0], Integer.parseInt(Splitp[1]));
					}
					if (readtemp.charAt(0) == 'T') {
						String[] Splitp = readtemp.split("\t|\\s+");
						map_T.put(Splitp[0], Integer.parseInt(Splitp[1]));
					}
					if (readtemp.charAt(0) == 'G') {
						String[] Splitp = readtemp.split("\t|\\s+");
						map_G.put(Splitp[0], Integer.parseInt(Splitp[1]));
					}
					if (readtemp.charAt(0) == 'C') {
						String[] Splitp = readtemp.split("\t|\\s+");
						map_C.put(Splitp[0], Integer.parseInt(Splitp[1]));
					}
				}
				bufferedReader1.close();
			}
			int sizefq1_1 = CommonClass.getFileLines(q1) / 4;
			int sizefq1_2 = CommonClass.getFileLines(q2) / 4;
			String Array_fq1[] = new String[sizefq1_1];
			String Array_fq2[] = new String[sizefq1_2];
			int realSizefq1 = CommonClass.FqToArray(q1, Array_fq1);
			int realSizefq2 = CommonClass.FqToArray(q2, Array_fq2);
			int common_size = 0;
			if (realSizefq1 > realSizefq2) {
				common_size = realSizefq2;
			} else {
				common_size = realSizefq1;
			}
			ExecutorService pool_1 = Executors.newFixedThreadPool(t);
			int NumSplits = (int) (Math.ceil((double) common_size / t));
			for (int w = 0; w < t; w++) {
				if (w != t - 1) {
					MarkingReadThreads mt_l = new MarkingReadThreads(0.8, 15, map_A, map_T, map_G, map_C, Array_fq1,
							NumSplits, w * NumSplits, (w + 1) * NumSplits - 1);
					pool_1.execute(mt_l);
				} else {
					MarkingReadThreads mt_l = new MarkingReadThreads(0.8, 15, map_A, map_T, map_G, map_C, Array_fq1,
							NumSplits, w * NumSplits, common_size - 1);
					pool_1.execute(mt_l);
				}
			}
			pool_1.shutdown();
			pool_1.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			ExecutorService pool_2 = Executors.newFixedThreadPool(t);
			System.out.print(" Lib1-Over, ");
			for (int w = 0; w < t; w++) {
				if (w != t - 1) {
					MarkingReadThreads mt_l = new MarkingReadThreads(0.8, 15, map_A, map_T, map_G, map_C, Array_fq2,
							NumSplits, w * NumSplits, (w + 1) * NumSplits - 1);
					pool_2.execute(mt_l);
				} else {
					MarkingReadThreads mt_l = new MarkingReadThreads(0.8, 15, map_A, map_T, map_G, map_C, Array_fq2,
							NumSplits, w * NumSplits, common_size - 1);
					pool_2.execute(mt_l);
				}
			}
			pool_2.shutdown();
			pool_2.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			System.out.print(" Lib2-Over, ");
			int l_q1 = 0;
			int r_q1 = 0;
			int single_index = 0;
			OutputStreamWriter write_q1 = new OutputStreamWriter(
					new FileOutputStream(ParentPath + "/readfile/q1_highfre.fa"), encoding);
			BufferedWriter bufferedWriter_q1 = new BufferedWriter(write_q1);
			OutputStreamWriter write_q2 = new OutputStreamWriter(
					new FileOutputStream(ParentPath + "/readfile/q2_highfre.fa"), encoding);
			BufferedWriter bufferedWriter_q2 = new BufferedWriter(write_q2);
			OutputStreamWriter write_single = new OutputStreamWriter(
					new FileOutputStream(ParentPath + "/readfile/single_highfre.fa"), encoding);
			BufferedWriter bufferedWriter_single = new BufferedWriter(write_single);
			for (int z = 0; z < common_size; z++) {
				if (Array_fq1[z].charAt(0) == '#' && Array_fq2[z].charAt(0) == '#') {
					bufferedWriter_q1
							.write(">" + (l_q1++) + "\n" + Array_fq1[z].substring(1, Array_fq1[z].length()) + "\n");
					bufferedWriter_q2
							.write(">" + (r_q1++) + "\n" + Array_fq2[z].substring(1, Array_fq2[z].length()) + "\n");
				}
				if (Array_fq1[z].charAt(0) == '#' && Array_fq2[z].charAt(0) != '#') {
					bufferedWriter_single.write(
							">" + (single_index++) + "\n" + Array_fq1[z].substring(1, Array_fq1[z].length()) + "\n");
				}
				if (Array_fq1[z].charAt(0) != '#' && Array_fq2[z].charAt(0) == '#') {
					bufferedWriter_single.write(
							">" + (single_index++) + "\n" + Array_fq2[z].substring(1, Array_fq2[z].length()) + "\n");
				}
			}
			bufferedWriter_q1.close();
			bufferedWriter_q2.close();
			bufferedWriter_single.close();
			Process p_assemblyRun2 = null;
			Runtime r_assemblyRun2 = Runtime.getRuntime();
			String[] cmd_assemblyRun = { "sh", "-c",
					"python " + ParentPath + "/tools/SPAdes-3.15.2/bin/spades.py --only-assembler -t 64 --pe1-1 "
							+ ParentPath + "/readfile/q1_highfre.fa" + " --pe1-2 " + ParentPath
							+ "/readfile/q2_highfre.fa" + " -s " + ParentPath + "/readfile/single_highfre.fa "
							+ " --trusted-contigs " + ParentPath + "/alignment/RepeatLib.fa " + " -o " + ParentPath
							+ "/readfile/Denovo/ > " + ParentPath + "/log/SPAdeslog2.out" };
			p_assemblyRun2 = r_assemblyRun2.exec(cmd_assemblyRun);
			p_assemblyRun2.waitFor();
			File scaffFile = new File(ParentPath + "/readfile/Denovo/scaffolds.fasta");
			if (scaffFile.exists()) {
				File replibFile = new File(ParentPath + "/alignment/RepeatLib.fa");
				CommonClass.deleteFile(replibFile);
				CommonClass.MergeFastaMultiLines2(ParentPath + "/readfile/Denovo/scaffolds.fasta",
						ParentPath + "/readfile/Denovo/scaffolds_line.fasta");
				CommonClass.RewriteFile2(ParentPath + "/readfile/Denovo/scaffolds_line.fasta",
						ParentPath + "/alignment/RepeatLib.fa", m);
			} else {
				String Assfile = "";
				List<String> paths = new ArrayList<String>();
				paths = CommonClass.getAllFilePaths(new File(ParentPath + "/readfile/Denovo/"), paths);
				for (String path : paths) {
					if (path.contains("before_rr.fasta"))
						Assfile = path;
				}
				File scaff2Reps = new File(Assfile);
				if (scaff2Reps.exists()) {
					File replibFile = new File(ParentPath + "/alignment/RepeatLib.fa");
					CommonClass.deleteFile(replibFile);
					CommonClass.MergeFastaMultiLines2(Assfile, ParentPath + "/readfile/Denovo/before_line.fasta");
					CommonClass.RewriteFile2(ParentPath + "/readfile/Denovo/before_line.fasta",
							ParentPath + "/alignment/RepeatLib.fa", m);
				}
			}
			CommonClass.copyFile(ParentPath + "/alignment/RepeatLib.fa", ParentPath + "/Results/RepeatLib.fa");
			System.out.println(", The final repeatLib is generated. ]");
			System.out.println("Step13: Identification of the genetic variations in repetitive regions");
			if (v.equals("yes") && (!f.equals(""))) {
				CommonClass.identification(ParentPath, f, ParentPath + "/Results", m);
			} else {
				System.out.println(" Missing the parameters v and f. VS identification is skiped ]");
			}
			System.out.println("Step14: Generating the final repetitive sequence library and dectection reports");
			if (!f.equals("")) {
				CommonClass.generation_reports(ParentPath, f, o);
			} else {
				System.out.println("Evaluation is not enabled, please adjust the parameter -f.");
			}
			System.out.println("===========================================================================================");
			System.out.println("Evaluations:");
			File FinalRepeats = new File(ParentPath + "/alignment/RepeatLib.fa");
			if (FinalRepeats.exists() && !f.equals("")) {
				if (M.equals("yes") || Q.equals("yes")) {
					if (M.equals("yes")) {
						CommonClass.calculateMul_AlignRatio(ParentPath, f, t);
					}
					if (Q.equals("yes")) {
						CommonClass.calculateN50_N75_N90(ParentPath);
					}
				} else {
					System.out
							.println("Evaluation is not enabled, please adjust -f, -M and -Q these three parameters.");
				}
			} else {
				System.out.println("There are no fragments longer than or equal to " + m
						+ "bp in the test results. Please adjust the value of parameter m.");
			}
		}
		// NGS+SMS long reads.
		else if ((r.equals("")) && (X1.equals("") && X2.equals("") && X3.equals("") && X4.equals("")) && (!l.equals(""))
				&& (!q1.equals("") && !q2.equals(""))) {
			System.out.println("  Data Type  = [ NGS Short Reads + SMS Long Reads]");
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
				System.out.println("  [Setting] SMS Long Reads File Path = [ " + l + " ]");
			}
			if (e.equals("") || e.equals("no")) {
				System.out.println("  Short reads error correction  = [  no ]");
			} else {
				System.out.println("  Short reads error correction  = [ yes ]");
			}
			if (E.equals("") || E.equals("no")) {
				System.out.println("  SMS long reads error correction  = [  no ]");
			} else {
				System.out.println("  SMS long reads error correction  = [ yes ]");
			}
			if (v.equals("yes")) {
				System.out.println("  Variation identification = [ yes ]");
			} else {
				System.out.println("  Variation identification = [ no ]");
			}
			System.out.println("===========================================================================================");
			Process p_ErrorCorrection = null;
			Runtime r_ErrorCorrection = Runtime.getRuntime();
			Process p_assemblyRun = null;
			Runtime r_assemblyRun = Runtime.getRuntime();
			try {
				System.out.print("Step2: Error correction [");
				String[] SplitLine1 = q1.split("/|\n");
				String full_name1 = SplitLine1[SplitLine1.length - 1];
				String last_name1 = full_name1.substring(0, full_name1.length());
				String f1 = ParentPath + "/readfile/karect_" + last_name1;
				String[] SplitLine2 = q2.split("/|\n");
				String full_name2 = SplitLine2[SplitLine2.length - 1];
				String last_name2 = full_name2.substring(0, full_name2.length());
				String f2 = ParentPath + "/readfile/karect_" + last_name2;
				String[] SplitLine3 = q3.split("/|\n");
				String full_name3 = SplitLine3[SplitLine3.length - 1];
				String last_name3 = full_name3.substring(0, full_name3.length());
				String f3 = ParentPath + "/readfile/karect_" + last_name3;
				String[] SplitLine4 = q4.split("/|\n");
				String full_name4 = SplitLine4[SplitLine4.length - 1];
				String last_name4 = full_name4.substring(0, full_name4.length());
				String f4 = ParentPath + "/readfile/karect_" + last_name4;
				String[] SplitLine5 = q5.split("/|\n");
				String full_name5 = SplitLine5[SplitLine5.length - 1];
				String last_name5 = full_name5.substring(0, full_name5.length());
				String f5 = ParentPath + "/readfile/karect_" + last_name5;
				String[] SplitLine6 = q6.split("/|\n");
				String full_name6 = SplitLine6[SplitLine6.length - 1];
				String last_name6 = full_name6.substring(0, full_name6.length());
				String f6 = ParentPath + "/readfile/karect_" + last_name6;
				String[] SplitLine7 = q7.split("/|\n");
				String full_name7 = SplitLine7[SplitLine7.length - 1];
				String last_name7 = full_name7.substring(0, full_name7.length());
				String f7 = ParentPath + "/readfile/karect_" + last_name7;
				String[] SplitLine8 = q8.split("/|\n");
				String full_name8 = SplitLine8[SplitLine8.length - 1];
				String last_name8 = full_name8.substring(0, full_name8.length());
				String f8 = ParentPath + "/readfile/karect_" + last_name8;
				String[] SplitLine9 = q9.split("/|\n");
				String full_name9 = SplitLine9[SplitLine9.length - 1];
				String last_name9 = full_name9.substring(0, full_name9.length());
				String f9 = ParentPath + "/readfile/karect_" + last_name9;
				String[] SplitLine10 = q10.split("/|\n");
				String full_name10 = SplitLine10[SplitLine10.length - 1];
				String last_name10 = full_name10.substring(0, full_name10.length());
				String f10 = ParentPath + "/readfile/karect_" + last_name10;
				File F1 = new File(f1);
				if (F1.exists())
					CommonClass.deleteFile(F1);
				File F2 = new File(f2);
				if (F2.exists())
					CommonClass.deleteFile(F2);
				File F3 = new File(f3);
				if (F3.exists())
					CommonClass.deleteFile(F3);
				File F4 = new File(f4);
				if (F4.exists())
					CommonClass.deleteFile(F4);
				File F5 = new File(f5);
				if (F5.exists())
					CommonClass.deleteFile(F5);
				File F6 = new File(f6);
				if (F6.exists())
					CommonClass.deleteFile(F6);
				File F7 = new File(f7);
				if (F7.exists())
					CommonClass.deleteFile(F7);
				File F8 = new File(f8);
				if (F8.exists())
					CommonClass.deleteFile(F8);
				File F9 = new File(f9);
				if (F9.exists())
					CommonClass.deleteFile(F9);
				File F10 = new File(f10);
				if (F10.exists())
					CommonClass.deleteFile(F10);
				if (e.equals("yes")) {
					if ((!q1.equals("")) && (!q2.equals(""))) {
						System.out.print(" lib:(pe:1-2->");
						String[] cmd_errorCorrection = { "sh", "-c",
								ParentPath
										+ "/tools/karect -correct -threads=128 -matchtype=hamming -celltype=haploid -tempdir="
										+ ParentPath + "/readfile/ -resultdir=" + ParentPath + "/readfile/ -inputfile="
										+ q1 + " -inputfile=" + q2 + " > " + ParentPath + "/readfile/read1.log" };
						p_ErrorCorrection = r_ErrorCorrection.exec(cmd_errorCorrection);
						p_ErrorCorrection.waitFor();
						System.out.print("Corrected)");
					}
					if ((!q3.equals("")) && (!q4.equals(""))) {
						System.out.print(" | lib:(pe:3-4->");
						String[] cmd_errorCorrection = { "sh", "-c",
								ParentPath
										+ "/tools/karect -correct -threads=128 -matchtype=hamming -celltype=haploid -tempdir="
										+ ParentPath + "/readfile/ -resultdir=" + ParentPath + "/readfile/ -inputfile="
										+ q3 + " -inputfile=" + q4 + " > " + ParentPath + "/readfile/read2.log" };
						p_ErrorCorrection = r_ErrorCorrection.exec(cmd_errorCorrection);
						p_ErrorCorrection.waitFor();
						System.out.print("Corrected)");
					}
					if ((!q5.equals("")) && (!q6.equals(""))) {
						System.out.print(" | lib:(pe:5-6->");
						String[] cmd_errorCorrection = { "sh", "-c",
								ParentPath
										+ "/tools/karect -correct -threads=128 -matchtype=hamming -celltype=haploid -tempdir="
										+ ParentPath + "/readfile/ -resultdir=" + ParentPath + "/readfile/ -inputfile="
										+ q5 + " -inputfile=" + q6 + " > " + ParentPath + "/readfile/read3.log" };
						p_ErrorCorrection = r_ErrorCorrection.exec(cmd_errorCorrection);
						p_ErrorCorrection.waitFor();
						System.out.print("Corrected)");
					}
					if ((!q7.equals("")) && (!q8.equals(""))) {
						System.out.print(" | lib:(pe:7-8->");
						String[] cmd_errorCorrection = { "sh", "-c",
								ParentPath
										+ "/tools/karect -correct -threads=128 -matchtype=hamming -celltype=haploid -tempdir="
										+ ParentPath + "/readfile/ -resultdir=" + ParentPath + "/readfile/ -inputfile="
										+ q7 + " -inputfile=" + q8 + " > " + ParentPath + "/readfile/read4.log" };
						p_ErrorCorrection = r_ErrorCorrection.exec(cmd_errorCorrection);
						p_ErrorCorrection.waitFor();
						System.out.print("Corrected)");
					}
					if ((!q9.equals("")) && (!q10.equals(""))) {
						System.out.print(" | lib:(pe:9-10->");
						String[] cmd_errorCorrection = { "sh", "-c",
								ParentPath
										+ "/tools/karect -correct -threads=128 -matchtype=hamming -celltype=haploid -tempdir="
										+ ParentPath + "/readfile/ -resultdir=" + ParentPath + "/readfile/ -inputfile="
										+ q9 + " -inputfile=" + q10 + " > " + ParentPath + "/readfile/read5.log" };
						p_ErrorCorrection = r_ErrorCorrection.exec(cmd_errorCorrection);
						p_ErrorCorrection.waitFor();
						System.out.print("Corrected)");
					}
					CommonClass.DelePathFiles(ParentPath + "/readfile/", "res_graph_b.txt");
					CommonClass.DelePathFiles(ParentPath + "/readfile/", "res_graph_a.txt");
					CommonClass.DelePathFiles(ParentPath + "/readfile/", "input_file.txt");
					CommonClass.DelePathFiles(ParentPath + "/readfile/", "temp_res_" + last_name1);
					CommonClass.DelePathFiles(ParentPath + "/readfile/", "temp_res_" + last_name2);
					CommonClass.DelePathFiles(ParentPath + "/readfile/", "temp_res_" + last_name3);
					CommonClass.DelePathFiles(ParentPath + "/readfile/", "temp_res_" + last_name4);
					CommonClass.DelePathFiles(ParentPath + "/readfile/", "temp_res_" + last_name5);
					CommonClass.DelePathFiles(ParentPath + "/readfile/", "temp_res_" + last_name6);
					CommonClass.DelePathFiles(ParentPath + "/readfile/", "temp_res_" + last_name7);
					CommonClass.DelePathFiles(ParentPath + "/readfile/", "temp_res_" + last_name8);
					CommonClass.DelePathFiles(ParentPath + "/readfile/", "temp_res_" + last_name9);
					CommonClass.DelePathFiles(ParentPath + "/readfile/", "temp_res_" + last_name10);
				} else {
					System.out.print(" Short reads error correction is skiped ");
					if (!q1.equals("") && !q2.equals("")) {
						CommonClass.copyFile(q1, f1);
						CommonClass.copyFile(q2, f2);
					}
					if (!q3.equals("") && !q4.equals("")) {
						CommonClass.copyFile(q3, f3);
						CommonClass.copyFile(q4, f4);
					}
					if (!q5.equals("") && !q6.equals("")) {
						CommonClass.copyFile(q5, f5);
						CommonClass.copyFile(q6, f6);
					}
					if (!q7.equals("") && !q8.equals("")) {
						CommonClass.copyFile(q7, f7);
						CommonClass.copyFile(q8, f8);
					}
					if (!q9.equals("") && !q10.equals("")) {
						CommonClass.copyFile(q9, f9);
						CommonClass.copyFile(q10, f10);
					}
				}
				System.out.print(" | ");
				if (E.equals("yes")) {
					File PM_File = new File(ParentPath + "/readfile/LongReads.pm.can");
					File Corrected_File = new File(ParentPath + "/readfile/LongReads.corrected.fastq");
					if (PM_File.exists()) {
						CommonClass.deleteFile(PM_File);
					}
					if (Corrected_File.exists()) {
						CommonClass.deleteFile(Corrected_File);
					}
					Process p_longReadRun1 = null;
					Process p_longReadRun2 = null;
					Runtime r_longReadRun1 = Runtime.getRuntime();
					Runtime r_longReadRun2 = Runtime.getRuntime();
					String[] cmd_longReadRun1 = { "sh", "-c", ParentPath + "/tools/mecat2pw -j 0 -d " + l + " -o "
							+ ParentPath + "/readfile/LongReads.pm.can -w " + ParentPath + "/readfile/ -t 64" };
					p_longReadRun1 = r_longReadRun1.exec(cmd_longReadRun1);
					p_longReadRun1.waitFor();
					String[] cmd_longReadRun2 = { "sh", "-c",
							ParentPath + "/tools/mecat2cns -i 0 -t 40 " + ParentPath + "/readfile/LongReads.pm.can " + l
									+ " " + ParentPath + "/readfile/LongReads.corrected.fasta" };
					p_longReadRun2 = r_longReadRun2.exec(cmd_longReadRun2);
					p_longReadRun2.waitFor();
					if (PM_File.exists() && Corrected_File.exists()) {
						System.out.print(" lib:long-reads->Corrected ");
					} else {
						System.out.print(
								" Long reads error correction failed. Please check the format of input file (error correction is skiped) ");
						CommonClass.copyFile(l, ParentPath + "/readfile/LongReads.corrected.fastq");
					}
				} else {
					System.out.print(" Long reads error correction is skiped ");
					CommonClass.copyFile(l, ParentPath + "/readfile/LongReads.corrected.fastq");
				}
				System.out.println("]");
				System.out.print("Step3: de novo assembly [ S+L ");
				int max_mem = 100;
				if (W == 0) {
					double totalMem = Runtime.getRuntime().maxMemory();
					int Current_max_mem = (int) (totalMem / (1024 * 1024 * 1024));
					if (Current_max_mem >= max_mem) {
						max_mem = Current_max_mem;
					}
					System.out.print("Max_mem=" + max_mem + "GB |");
				} else {
					max_mem = W;
				}
				File Q1 = new File(f1);
				File Q2 = new File(f3);
				File Q3 = new File(f5);
				File Q4 = new File(f7);
				File Q5 = new File(f9);
				String LongReadFile = "";
				File longReadFQ = new File(ParentPath + "/readfile/LongReads.corrected.fastq");
				File longReadFA = new File(ParentPath + "/readfile/LongReads.corrected.fasta");
				if (longReadFQ.exists()) {
					LongReadFile = ParentPath + "/readfile/LongReads.corrected.fastq";
				}
				if (longReadFA.exists()) {
					LongReadFile = ParentPath + "/readfile/LongReads.corrected.fasta";
				}
				if (Q1.exists() && !Q2.exists()) {
					System.out.print(" Assembly:(pe:1-2)");
					String[] cmd_assemblyRun = { "sh", "-c",
							ParentPath + "/tools/SPAdes-3.15.2/bin/spades.py --only-assembler -t 64 -m " + max_mem
									+ " --pe1-1 " + f1 + " --pe1-2 " + f2 + " --pacbio " + LongReadFile + " -o " + ParentPath
									+ "/readfile/ > " + ParentPath + "/log/SPAdeslog.out" };
					p_assemblyRun = r_assemblyRun.exec(cmd_assemblyRun);
					p_assemblyRun.waitFor();					
					File scaffFile_before = new File(ParentPath + "/readfile/scaffolds.fasta");
					if (!scaffFile_before.exists()) {
						String Assfile1 = "";
						List<String> paths = new ArrayList<String>();
						paths = CommonClass.getAllFilePaths(new File(ParentPath + "/readfile/"), paths);
						for (String path : paths) {
							if (path.contains("before_rr.fasta")){
								Assfile1 = path;
							}
						}
						if(!Assfile1.equals("")){
							CommonClass.copyFile(Assfile1, ParentPath + "/readfile/scaffolds.fasta");
						}
						else
						{
							System.out.println("\nThe assembly process ended abnormally!"
									+ "\nPlease check the heap memory configuration of LongRepMarker!"
									+ "\nUsers can use the -Xmx parameter to reconfigure the heap memory space of LongRepMarker!"
									+ "\nPlease refer to " + ParentPath + "/log/SPAdeslog.out"
									+ " for detailed error information.\n\n");
						}	
					} 
					int file_index = 0;
					String readtemp1 = "";
					String readtemp2 = "";
					try {
						String encoding = "utf-8";
						File file1 = new File(f1);
						File file2 = new File(f2);
						if (file1.exists() && file2.exists()) {
							InputStreamReader read1 = new InputStreamReader(new FileInputStream(file1), encoding);
							InputStreamReader read2 = new InputStreamReader(new FileInputStream(file2), encoding);
							BufferedReader bufferedReader1 = new BufferedReader(read1);
							BufferedReader bufferedReader2 = new BufferedReader(read2);
							OutputStreamWriter write = new OutputStreamWriter(
									new FileOutputStream(ParentPath + "/readfile/Reads.fa"), encoding);
							BufferedWriter bufferedWriter = new BufferedWriter(write);
							while ((bufferedReader1.readLine()) != null && (bufferedReader2.readLine()) != null) {
								readtemp1 = bufferedReader1.readLine();
								readtemp2 = bufferedReader2.readLine();
								bufferedReader1.readLine();
								bufferedReader2.readLine();
								bufferedReader1.readLine();
								bufferedReader2.readLine();
								bufferedWriter.write(">" + (file_index++) + "\n" + readtemp1 + "\n>" + (file_index++)
										+ "\n" + readtemp2 + "\n");
							}
							bufferedReader1.close();
							bufferedReader2.close();
							bufferedWriter.close();
						} else {
							System.out.println("File is not exist!");
						}
					} catch (Exception e1) {
						System.out.println("Error liaoxingyu");
						e1.printStackTrace();
					}
				}
				if (Q1.exists() && Q2.exists() && !Q3.exists()) {
					System.out.print(" Assembly:(pe:1-2, pe:3-4)");
					String[] cmd_assemblyRun = { "sh", "-c",
							ParentPath + "/tools/SPAdes-3.15.2/bin/spades.py --only-assembler -t 64 -m " + max_mem
									+ " --pe1-1 " + f1 + " --pe1-2 " + f2 + " --mp1-1 " + f3 + " --mp1-2 " + f4 + " --pacbio "
									+ LongReadFile + " -o " + ParentPath + "/readfile/ > " + ParentPath
									+ "/log/SPAdeslog.out" };
					p_assemblyRun = r_assemblyRun.exec(cmd_assemblyRun);
					p_assemblyRun.waitFor();
					File scaffFile_before = new File(ParentPath + "/readfile/scaffolds.fasta");
					if (!scaffFile_before.exists()) {
						String Assfile1 = "";
						List<String> paths = new ArrayList<String>();
						paths = CommonClass.getAllFilePaths(new File(ParentPath + "/readfile/"), paths);
						for (String path : paths) {
							if (path.contains("before_rr.fasta")){
								Assfile1 = path;
							}
						}
						if(!Assfile1.equals("")){
							CommonClass.copyFile(Assfile1, ParentPath + "/readfile/scaffolds.fasta");
						}
						else
						{
							System.out.println("\nThe assembly process ended abnormally!"
									+ "\nPlease check the heap memory configuration of LongRepMarker!"
									+ "\nUsers can use the -Xmx parameter to reconfigure the heap memory space of LongRepMarker!"
									+ "\nPlease refer to " + ParentPath + "/log/SPAdeslog.out"
									+ " for detailed error information.\n\n");
						}	
					} 
					int file_index = 0;
					String readtemp1 = "";
					String readtemp2 = "";
					try {
						String encoding = "utf-8";
						File file1 = new File(f1);
						File file2 = new File(f2);
						if (file1.exists() && file2.exists()) {
							InputStreamReader read1 = new InputStreamReader(new FileInputStream(file1), encoding);
							InputStreamReader read2 = new InputStreamReader(new FileInputStream(file2), encoding);
							BufferedReader bufferedReader1 = new BufferedReader(read1);
							BufferedReader bufferedReader2 = new BufferedReader(read2);
							OutputStreamWriter write = new OutputStreamWriter(
									new FileOutputStream(ParentPath + "/readfile/Reads.fa"), encoding);
							BufferedWriter bufferedWriter = new BufferedWriter(write);
							while ((bufferedReader1.readLine()) != null && (bufferedReader2.readLine()) != null) {
								readtemp1 = bufferedReader1.readLine();
								readtemp2 = bufferedReader2.readLine();
								bufferedReader1.readLine();
								bufferedReader2.readLine();
								bufferedReader1.readLine();
								bufferedReader2.readLine();
								bufferedWriter.write(">" + (file_index++) + "\n" + readtemp1 + "\n>" + (file_index++)
										+ "\n" + readtemp2 + "\n");
							}
							bufferedReader1.close();
							bufferedReader2.close();
							bufferedWriter.close();
						} else {
							System.out.println("File is not exist!");
						}
					} catch (Exception e1) {
						System.out.println("Error liaoxingyu");
						e1.printStackTrace();
					}
				}
				if (Q1.exists() && Q2.exists() && Q3.exists() && !Q4.exists()) {
					System.out.print(" Assembly:(pe:1-2, pe:3-4, pe:5-6)");
					String[] cmd_assemblyRun = { "sh", "-c",
							ParentPath + "/tools/SPAdes-3.15.2/bin/spades.py --only-assembler -t 64 -m " + max_mem
									+ " --pe1-1 " + f1 + " --pe1-2 " + f2 + " --mp1-1 " + f3 + " --mp1-2 " + f4
									+ " --mp2-1 " + f5 + " --mp2-2 " + f6 + " --pacbio " + LongReadFile + " -o " + ParentPath
									+ "/readfile/ > " + ParentPath + "/log/SPAdeslog.out" };
					p_assemblyRun = r_assemblyRun.exec(cmd_assemblyRun);
					p_assemblyRun.waitFor();
					File scaffFile_before = new File(ParentPath + "/readfile/scaffolds.fasta");
					if (!scaffFile_before.exists()) {
						String Assfile1 = "";
						List<String> paths = new ArrayList<String>();
						paths = CommonClass.getAllFilePaths(new File(ParentPath + "/readfile/"), paths);
						for (String path : paths) {
							if (path.contains("before_rr.fasta")){
								Assfile1 = path;
							}
						}
						if(!Assfile1.equals("")){
							CommonClass.copyFile(Assfile1, ParentPath + "/readfile/scaffolds.fasta");
						}
						else
						{
							System.out.println("\nThe assembly process ended abnormally!"
									+ "\nPlease check the heap memory configuration of LongRepMarker!"
									+ "\nUsers can use the -Xmx parameter to reconfigure the heap memory space of LongRepMarker!"
									+ "\nPlease refer to " + ParentPath + "/log/SPAdeslog.out"
									+ " for detailed error information.\n\n");
						}	
					} 
					int file_index = 0;
					String readtemp1 = "";
					String readtemp2 = "";
					try {
						String encoding = "utf-8";
						File file1 = new File(f1);
						File file2 = new File(f2);
						if (file1.exists() && file2.exists()) {
							InputStreamReader read1 = new InputStreamReader(new FileInputStream(file1), encoding);
							InputStreamReader read2 = new InputStreamReader(new FileInputStream(file2), encoding);
							BufferedReader bufferedReader1 = new BufferedReader(read1);
							BufferedReader bufferedReader2 = new BufferedReader(read2);
							OutputStreamWriter write = new OutputStreamWriter(
									new FileOutputStream(ParentPath + "/readfile/Reads.fa"), encoding);
							BufferedWriter bufferedWriter = new BufferedWriter(write);
							while ((bufferedReader1.readLine()) != null && (bufferedReader2.readLine()) != null) {
								readtemp1 = bufferedReader1.readLine();
								readtemp2 = bufferedReader2.readLine();
								bufferedReader1.readLine();
								bufferedReader2.readLine();
								bufferedReader1.readLine();
								bufferedReader2.readLine();
								bufferedWriter.write(">" + (file_index++) + "\n" + readtemp1 + "\n>" + (file_index++)
										+ "\n" + readtemp2 + "\n");
							}
							bufferedReader1.close();
							bufferedReader2.close();
							bufferedWriter.close();
						} else {
							System.out.println("File is not exist!");
						}
					} catch (Exception e1) {
						System.out.println("Error liaoxingyu");
						e1.printStackTrace();
					}
				}
				if (Q1.exists() && Q2.exists() && Q3.exists() && Q4.exists() && !Q5.exists()) {
					System.out.print(" Assembly:(pe:1-2, pe:3-4, pe:5-6, pe:7-8)");
					String[] cmd_assemblyRun = { "sh", "-c",
							ParentPath + "/tools/SPAdes-3.15.2/bin/spades.py --only-assembler -t 64 -m " + max_mem
									+ " --pe1-1 " + f1 + " --pe1-2 " + f2 + " --mp1-1 " + f3 + " --mp1-2 " + f4
									+ " --mp2-1 " + f5 + " --mp2-2 " + f6 + " --mp3-1 " + f7 + " --mp3-2 " + f8 + " --pacbio "
									+ LongReadFile + " -o " + ParentPath + "/readfile/ > " + ParentPath
									+ "/log/SPAdeslog.out" };
					p_assemblyRun = r_assemblyRun.exec(cmd_assemblyRun);
					p_assemblyRun.waitFor();
					File scaffFile_before = new File(ParentPath + "/readfile/scaffolds.fasta");
					if (!scaffFile_before.exists()) {
						String Assfile1 = "";
						List<String> paths = new ArrayList<String>();
						paths = CommonClass.getAllFilePaths(new File(ParentPath + "/readfile/"), paths);
						for (String path : paths) {
							if (path.contains("before_rr.fasta")){
								Assfile1 = path;
							}
						}
						if(!Assfile1.equals("")){
							CommonClass.copyFile(Assfile1, ParentPath + "/readfile/scaffolds.fasta");
						}
						else
						{
							System.out.println("\nThe assembly process ended abnormally!"
									+ "\nPlease check the heap memory configuration of LongRepMarker!"
									+ "\nUsers can use the -Xmx parameter to reconfigure the heap memory space of LongRepMarker!"
									+ "\nPlease refer to " + ParentPath + "/log/SPAdeslog.out"
									+ " for detailed error information.\n\n");
						}	
					}  
					int file_index = 0;
					String readtemp1 = "";
					String readtemp2 = "";
					try {
						String encoding = "utf-8";
						File file1 = new File(f1);
						File file2 = new File(f2);
						if (file1.exists() && file2.exists()) {
							InputStreamReader read1 = new InputStreamReader(new FileInputStream(file1), encoding);
							InputStreamReader read2 = new InputStreamReader(new FileInputStream(file2), encoding);
							BufferedReader bufferedReader1 = new BufferedReader(read1);
							BufferedReader bufferedReader2 = new BufferedReader(read2);
							OutputStreamWriter write = new OutputStreamWriter(
									new FileOutputStream(ParentPath + "/readfile/Reads.fa"), encoding);
							BufferedWriter bufferedWriter = new BufferedWriter(write);
							while ((bufferedReader1.readLine()) != null && (bufferedReader2.readLine()) != null) {
								readtemp1 = bufferedReader1.readLine();
								readtemp2 = bufferedReader2.readLine();
								bufferedReader1.readLine();
								bufferedReader2.readLine();
								bufferedReader1.readLine();
								bufferedReader2.readLine();
								bufferedWriter.write(">" + (file_index++) + "\n" + readtemp1 + "\n>" + (file_index++)
										+ "\n" + readtemp2 + "\n");
							}
							bufferedReader1.close();
							bufferedReader2.close();
							bufferedWriter.close();
						} else {
							System.out.println("File is not exist!");
						}
					} catch (Exception e1) {
						System.out.println("Error liaoxingyu");
						e1.printStackTrace();
					}
				}
				if (Q1.exists() && Q2.exists() && Q3.exists() && Q4.exists() && Q5.exists()) {
					System.out.print(" Assembly:(pe:1-2, pe:3-4, pe:5-6, pe:7-8, pe:9-10)");
					String[] cmd_assemblyRun = { "sh", "-c",
							ParentPath + "/tools/SPAdes-3.15.2/bin/spades.py --only-assembler -t 64 -m " + max_mem
									+ " --pe1-1 " + f1 + " --pe1-2 " + f2 + " --mp1-1 " + f3 + " --mp1-2 " + f4
									+ " --mp2-1 " + f5 + " --mp2-2 " + f6 + " --mp3-1 " + f7 + " --mp3-2 " + f8
									+ " --mp4-1 " + f9 + " --mp4-2 " + f10 + " --pacbio " + LongReadFile + " -o " + ParentPath
									+ "/readfile/ > " + ParentPath + "/log/SPAdeslog.out" };
					p_assemblyRun = r_assemblyRun.exec(cmd_assemblyRun);
					p_assemblyRun.waitFor();
					File scaffFile_before = new File(ParentPath + "/readfile/scaffolds.fasta");
					if (!scaffFile_before.exists()) {
						String Assfile1 = "";
						List<String> paths = new ArrayList<String>();
						paths = CommonClass.getAllFilePaths(new File(ParentPath + "/readfile/"), paths);
						for (String path : paths) {
							if (path.contains("before_rr.fasta")){
								Assfile1 = path;
							}
						}
						if(!Assfile1.equals("")){
							CommonClass.copyFile(Assfile1, ParentPath + "/readfile/scaffolds.fasta");
						}
						else
						{
							System.out.println("\nThe assembly process ended abnormally!"
									+ "\nPlease check the heap memory configuration of LongRepMarker!"
									+ "\nUsers can use the -Xmx parameter to reconfigure the heap memory space of LongRepMarker!"
									+ "\nPlease refer to " + ParentPath + "/log/SPAdeslog.out"
									+ " for detailed error information.\n\n");
						}	
					}  
					int file_index = 0;
					String readtemp1 = "";
					String readtemp2 = "";
					try {
						String encoding = "utf-8";
						File file1 = new File(f1);
						File file2 = new File(f2);
						if (file1.exists() && file2.exists()) {
							InputStreamReader read1 = new InputStreamReader(new FileInputStream(file1), encoding);
							InputStreamReader read2 = new InputStreamReader(new FileInputStream(file2), encoding);
							BufferedReader bufferedReader1 = new BufferedReader(read1);
							BufferedReader bufferedReader2 = new BufferedReader(read2);
							OutputStreamWriter write = new OutputStreamWriter(
									new FileOutputStream(ParentPath + "/readfile/Reads.fa"), encoding);
							BufferedWriter bufferedWriter = new BufferedWriter(write);
							while ((bufferedReader1.readLine()) != null && (bufferedReader2.readLine()) != null) {
								readtemp1 = bufferedReader1.readLine();
								readtemp2 = bufferedReader2.readLine();
								bufferedReader1.readLine();
								bufferedReader2.readLine();
								bufferedReader1.readLine();
								bufferedReader2.readLine();
								bufferedWriter.write(">" + (file_index++) + "\n" + readtemp1 + "\n>" + (file_index++)
										+ "\n" + readtemp2 + "\n");
							}
							bufferedReader1.close();
							bufferedReader2.close();
							bufferedWriter.close();
						} else {
							System.out.println("File is not exist!");
						}
					} catch (Exception e1) {
						System.out.println("Error liaoxingyu");
						e1.printStackTrace();
					}
				}
				System.out.println(" + SMS long reads ]");
			} catch (Exception z) {
				System.out.println("Step03 Error:" + z.getMessage());
				z.printStackTrace();
			}
			File scaffFile1 = new File(ParentPath + "/readfile/scaffolds.fasta");
			if (scaffFile1.exists()) {
				File LinearFile = new File(ParentPath + "/alignment/LinearFile.fasta");
				File OringnalFile = new File(ParentPath + "/alignment/Oringnal.fasta");
				if (LinearFile.exists())
					CommonClass.deleteFile(LinearFile);
				if (OringnalFile.exists())
					CommonClass.deleteFile(OringnalFile);
				CommonClass.MergeFastaMultiLines(ParentPath, ParentPath + "/readfile/scaffolds.fasta",
						ParentPath + "/alignment/Oringnal.fasta");
				CommonClass.RewriteFile(ParentPath + "/alignment/Oringnal.fasta",
						ParentPath + "/alignment/LinearFile.fasta", m);
			} else {
				String Assfile1 = "";
				List<String> paths = new ArrayList<String>();
				paths = CommonClass.getAllFilePaths(new File(ParentPath + "/readfile/"), paths);
				for (String path : paths) {
					if (path.contains("before_rr.fasta"))
						Assfile1 = path;
				}
				File scaff2Reps1 = new File(Assfile1);
				if (scaff2Reps1.exists()) {
					File LinearFile = new File(ParentPath + "/alignment/LinearFile.fasta");
					File OringnalFile = new File(ParentPath + "/alignment/Oringnal.fasta");
					if (LinearFile.exists())
						CommonClass.deleteFile(LinearFile);
					if (OringnalFile.exists())
						CommonClass.deleteFile(OringnalFile);
					CommonClass.copyFile(Assfile1, ParentPath + "/readfile/scaffolds.fasta");
					CommonClass.MergeFastaMultiLines(ParentPath, Assfile1, ParentPath + "/alignment/Oringnal.fasta");
					CommonClass.RewriteFile(ParentPath + "/alignment/Oringnal.fasta",
							ParentPath + "/alignment/LinearFile.fasta", m);
				}
			}
			System.out.println("Step04: Finding overlap sequences between contigs");
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "ovlp.paf");
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "ovlp_interim.paf");
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "ovlp_orginal.paf");
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "OverlapRegions.fa");
			Runtime r_overlap = Runtime.getRuntime();
			Process pr_overlap = null;
			String[] cmd_overlap = { "sh", "-c",
					ParentPath + "/tools/minimap2 -x ava-pb -g 3000 -w 10 -k 27 -m 100 -r 150 -t " + t + " "
							+ ParentPath + "/alignment/LinearFile.fasta " + ParentPath
							+ "/alignment/LinearFile.fasta > " + ParentPath + "/alignment/ovlp_orginal.paf" };
			pr_overlap = r_overlap.exec(cmd_overlap);
			pr_overlap.waitFor();
			CommonClass.overlap_pretreatment(ParentPath + "/alignment/ovlp_orginal.paf",
					ParentPath + "/alignment/ovlp_interim.paf");
			Runtime r_sort = Runtime.getRuntime();
			Process pr_sort = null;
			String[] cmd_sort = { "sh", "-c", "sort -t '\t' -k 1 -n " + ParentPath + "/alignment/ovlp_interim.paf -o "
					+ ParentPath + "/alignment/ovlp.paf" };
			pr_sort = r_sort.exec(cmd_sort);
			pr_sort.waitFor();
			int LinesOfScaffs = CommonClass.getFileLines(ParentPath + "/alignment/LinearFile.fasta") / 2;
			String SaveChangeLineScaffolds[] = new String[LinesOfScaffs];
			CommonClass.FileToArray3(ParentPath + "/alignment/LinearFile.fasta", SaveChangeLineScaffolds, ">");
			File OverlapFilePath = new File(ParentPath + "/alignment/ovlp.paf");
			ArrayList<String> readmark = new ArrayList<String>();
			ArrayList<String> overlaps = new ArrayList<String>();
			ArrayList<String> HighCoverageRegions = new ArrayList<String>();
			String OverlapStr = "";
			if (OverlapFilePath.isFile() && OverlapFilePath.exists()) {
				InputStreamReader DepthRead = new InputStreamReader(new FileInputStream(OverlapFilePath), "utf-8");
				BufferedReader bufferedReaderDepth = new BufferedReader(DepthRead);
				while ((OverlapStr = bufferedReaderDepth.readLine()) != null) {
					String[] SplitLine = OverlapStr.split("\t|\\s+");
					if (readmark.size() == 0) {
						readmark.add(SplitLine[0]);
						overlaps.add(OverlapStr);
					} else {
						if (readmark.contains(SplitLine[0])) {
							overlaps.add(OverlapStr);
						} else {
							int readID = 0;
							String[] DepthLine = overlaps.get(0).split("\t|\\s+");
							String[] ReadIDLine = DepthLine[0].split("_");
							readID = Integer.parseInt(ReadIDLine[1]);
							int CharArrayLength = SaveChangeLineScaffolds[readID].length();
							char SavePositionArray1[] = new char[CharArrayLength];
							for (int y = 0; y < CharArrayLength; y++) {
								SavePositionArray1[y] = 'N';
							}
							for (int p = 0; p < overlaps.size(); p++) {
								if (overlaps.get(p) != null) {
									String[] PositionLine = overlaps.get(p).split("\t|\\s+");
									int checkQuality = Integer.parseInt(PositionLine[11]);
									if (checkQuality >= 0) {
										int Start_Position = Integer.parseInt(PositionLine[2]);
										int End_Position = Integer.parseInt(PositionLine[3]);
										for (int s = Start_Position; s < End_Position; s++) {
											SavePositionArray1[s] = SaveChangeLineScaffolds[readID].charAt(s);
										}
									}
								}
							}
							String ReplaceStr1 = new String(SavePositionArray1);
							String[] SplitScaffLine1 = ReplaceStr1.split("N");
							for (int u = 0; u < SplitScaffLine1.length; u++) {
								if (SplitScaffLine1[u].length() >= m) {
									HighCoverageRegions.add(SplitScaffLine1[u]);
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
			int readID = 0;
			String[] DepthLine = overlaps.get(0).split("\t|\\s+");
			String[] ReadIDLine = DepthLine[0].split("_");
			readID = Integer.parseInt(ReadIDLine[1]);
			int CharArrayLength = SaveChangeLineScaffolds[readID].length();
			char SavePositionArray1[] = new char[CharArrayLength];
			for (int y = 0; y < CharArrayLength; y++) {
				SavePositionArray1[y] = 'N';
			}
			for (int p = 0; p < overlaps.size(); p++) {
				if (overlaps.get(p) != null) {
					String[] PositionLine = overlaps.get(p).split("\t|\\s+");
					int checkQuality = Integer.parseInt(PositionLine[11]);
					if (checkQuality >= 0) {
						int Start_Position = Integer.parseInt(PositionLine[2]);
						int End_Position = Integer.parseInt(PositionLine[3]);
						for (int s = Start_Position; s < End_Position; s++) {
							SavePositionArray1[s] = SaveChangeLineScaffolds[readID].charAt(s);
						}
					}
				}
			}
			String ReplaceStr1 = new String(SavePositionArray1);
			String[] SplitScaffLine1 = ReplaceStr1.split("N");
			for (int u = 0; u < SplitScaffLine1.length; u++) {
				if (SplitScaffLine1[u].length() >= m) {
					HighCoverageRegions.add(SplitScaffLine1[u]);
				}
			}
			readmark.clear();
			overlaps.clear();
			int overlap_Index = 0;
			for (int p = 0; p < HighCoverageRegions.size(); p++) {
				FileWriter writer1 = new FileWriter(ParentPath + "/alignment/OverlapRegions_orginal.fa", true);
				writer1.write(">Node_" + (overlap_Index++) + "_" + HighCoverageRegions.get(p).length() + "\n"
						+ HighCoverageRegions.get(p) + "\n");
				writer1.close();
			}
			readmark = null;
			overlaps = null;
			SaveChangeLineScaffolds = null;
			HighCoverageRegions = null;
			System.out.print("Step05: Estimation of the average coverage of overlap sequences [ ");
			Runtime c_index = Runtime.getRuntime();
			Process cr_index = null;
			Runtime c_mapping = Runtime.getRuntime();
			Process cr_mapping = null;
			Runtime c_sam2bam = Runtime.getRuntime();
			Process cr_sam2bam = null;
			Runtime c_bam2sort = Runtime.getRuntime();
			Process cr_bam2sort = null;
			Runtime c_sort2index = Runtime.getRuntime();
			Process cr_sort2index = null;
			Runtime c_depth = Runtime.getRuntime();
			Process cr_depth = null;
			String[] cmd_index = { "sh", "-c", ParentPath + "/tools/minimap2 -d " + ParentPath
					+ "/alignment/overlap_cov.mmi " + ParentPath + "/alignment/OverlapRegions_orginal.fa" };
			cr_index = c_index.exec(cmd_index);
			cr_index.waitFor();
			String[] cmd_mapping = { "sh", "-c",
					ParentPath + "/tools/minimap2 -a -t" + t + " " + ParentPath + "/alignment/overlap_cov.mmi "
							+ ParentPath + "/alignment/OverlapRegions_orginal.fa > " + ParentPath
							+ "/alignment/OverlapRegions_cov.sam" };
			cr_mapping = c_mapping.exec(cmd_mapping);
			cr_mapping.waitFor();
			String[] cmd_sam2bam = { "sh", "-c", ParentPath + "/tools/samtools  view  -bS  " + ParentPath
					+ "/alignment/OverlapRegions_cov.sam > " + ParentPath + "/alignment/OverlapRegions_cov.bam" };
			cr_sam2bam = c_sam2bam.exec(cmd_sam2bam);
			cr_sam2bam.waitFor();
			String[] cmd_bam2sort = { "sh", "-c", ParentPath + "/tools/samtools  sort  " + ParentPath
					+ "/alignment/OverlapRegions_cov.bam > " + ParentPath + "/alignment/OverlapRegions_cov.sort.bam" };
			cr_bam2sort = c_bam2sort.exec(cmd_bam2sort);
			cr_bam2sort.waitFor();
			String[] cmd_sort2index = { "sh", "-c",
					ParentPath + "/tools/samtools  index  " + ParentPath + "/alignment/OverlapRegions_cov.sort.bam" };
			cr_sort2index = c_sort2index.exec(cmd_sort2index);
			cr_sort2index.waitFor();
			String[] cmd_depth2 = { "sh", "-c",
					ParentPath + "/tools/samtools depth  " + ParentPath + "/alignment/OverlapRegions_cov.sort.bam > "
							+ ParentPath + "/alignment/OverlapRegions_cov.depth" };
			cr_depth = c_depth.exec(cmd_depth2);
			cr_depth.waitFor();
			File bamfile_Path = new File(ParentPath + "/alignment/");
			CommonClass.delCommonFile(bamfile_Path, " ", ".sam");
			CommonClass.delCommonFile(bamfile_Path, " ", ".bam");
			CommonClass.delCommonFile(bamfile_Path, " ", ".sort.bam");
			CommonClass.delCommonFile(bamfile_Path, " ", ".sort.bam.bai");
			String readDepth = "";
			double iterm_count = 0;
			double depthSum = 0;
			double averageDepth = 0;
			File readDepthFile = new File(ParentPath + "/alignment/OverlapRegions_cov.depth");
			if (readDepthFile.isFile() && readDepthFile.exists()) {
				InputStreamReader AlignRead = new InputStreamReader(new FileInputStream(readDepthFile), "utf-8");
				BufferedReader bufferedReaderDepth = new BufferedReader(AlignRead);
				while ((readDepth = bufferedReaderDepth.readLine()) != null) {
					String[] SplitDepthFile = readDepth.split("\t|\\s+");
					depthSum += Double.parseDouble(SplitDepthFile[2]);
					iterm_count++;
				}
				bufferedReaderDepth.close();
			}
			averageDepth = depthSum / iterm_count;
			System.out.println("Coverage:" + df.format(averageDepth) + ". Threshold:"
					+ df.format(averageDepth * Double.parseDouble(c)) + "]");
			System.out.println("Step06: Overlap sequences filtering");
			int overlaps_size = CommonClass.getFileLines(ParentPath + "/alignment/OverlapRegions_orginal.fa") / 2;
			String overlaps_Array[] = new String[overlaps_size];
			CommonClass.FileToArray2(ParentPath + "/alignment/OverlapRegions_orginal.fa", overlaps_Array, ">");
			ArrayList<String> Overlap_Id = new ArrayList<String>();
			ArrayList<String> Overlap_Position = new ArrayList<String>();
			ArrayList<String> Overlap_Coverage = new ArrayList<String>();
			String ReadOverlap_Str = "";
			int Overlap_highcov_Num = 0;
			int Overlap_lowcov_Num = 0;
			File OverlapDepth_FilePath = new File(ParentPath + "/alignment/OverlapRegions_cov.depth");
			if (OverlapDepth_FilePath.isFile() && OverlapDepth_FilePath.exists()) {
				InputStreamReader ReadDepth = new InputStreamReader(new FileInputStream(OverlapDepth_FilePath),
						"utf-8");
				BufferedReader bufferedReaderDepth = new BufferedReader(ReadDepth);
				while ((ReadOverlap_Str = bufferedReaderDepth.readLine()) != null) {
					String[] SplitDepthLine = ReadOverlap_Str.split("\t|\\s+");
					if (Overlap_Id.size() == 0) {
						Overlap_Id.add(SplitDepthLine[0]);
						Overlap_Position.add(SplitDepthLine[1]);
						Overlap_Coverage.add(SplitDepthLine[2]);
					} else {
						if (Overlap_Id.contains(SplitDepthLine[0])) {
							Overlap_Position.add(SplitDepthLine[1]);
							Overlap_Coverage.add(SplitDepthLine[2]);
						} else {
							if (Overlap_Position.size() >= m) {
								int overlap_ID = 0;
								String[] DepthLine1 = Overlap_Id.get(0).split("\t|\\s+");
								String[] ReadIDLine1 = DepthLine1[0].split("_");
								overlap_ID = Integer.parseInt(ReadIDLine1[1]);
								double Cov_Sum = 0;
								double Ave_cov = 0;
								for (int p = 0; p < Overlap_Position.size(); p++) {
									if (Overlap_Coverage.get(p) != null) {
										Cov_Sum += Integer.parseInt(Overlap_Coverage.get(p));
									}
								}
								Ave_cov = Cov_Sum / Overlap_Position.size();
								if (Ave_cov >= averageDepth * Double.parseDouble(c)) {
									FileWriter writer1 = new FileWriter(
											ParentPath + "/alignment/OverlapRegions_high.fa", true);
									writer1.write(">Node_" + (Overlap_highcov_Num++) + "_"
											+ overlaps_Array[overlap_ID].length() + "\n" + overlaps_Array[overlap_ID]
											+ "\n");
									writer1.close();
								} else {
									FileWriter writer1 = new FileWriter(ParentPath + "/alignment/OverlapRegions_low.fa",
											true);
									writer1.write(">Node_" + (Overlap_lowcov_Num++) + "_"
											+ overlaps_Array[overlap_ID].length() + "\n" + overlaps_Array[overlap_ID]
											+ "\n");
									writer1.close();
								}
							}
							Overlap_Id.clear();
							Overlap_Position.clear();
							Overlap_Coverage.clear();
							Overlap_Id.add(SplitDepthLine[0]);
							Overlap_Position.add(SplitDepthLine[1]);
							Overlap_Coverage.add(SplitDepthLine[2]);
						}
					}
				}
				bufferedReaderDepth.close();
			}
			if (Overlap_Position.size() >= m) {
				int overlap_ID = 0;
				String[] DepthLine2 = Overlap_Id.get(0).split("\t|\\s+");
				String[] ReadIDLine2 = DepthLine2[0].split("_");
				overlap_ID = Integer.parseInt(ReadIDLine2[1]);
				double Cov_Sum = 0;
				double Ave_cov = 0;
				for (int p = 0; p < Overlap_Position.size(); p++) {
					if (Overlap_Coverage.get(p) != null) {
						Cov_Sum += Integer.parseInt(Overlap_Coverage.get(p));
					}
				}
				Ave_cov = Cov_Sum / Overlap_Position.size();
				if (Ave_cov >= averageDepth * Double.parseDouble(c)) {
					FileWriter writer1 = new FileWriter(ParentPath + "/alignment/OverlapRegions_high.fa", true);
					writer1.write(">Node_" + (Overlap_highcov_Num++) + "_" + overlaps_Array[overlap_ID].length() + "\n"
							+ overlaps_Array[overlap_ID] + "\n");
					writer1.close();
				} else {
					FileWriter writer1 = new FileWriter(ParentPath + "/alignment/OverlapRegions_low.fa", true);
					writer1.write(">Node_" + (Overlap_lowcov_Num++) + "_" + overlaps_Array[overlap_ID].length() + "\n"
							+ overlaps_Array[overlap_ID] + "\n");
					writer1.close();
				}
			}
			overlaps_Array = null;
			Overlap_Id = null;
			Overlap_Position = null;
			Overlap_Coverage = null;
			Runtime b_index = Runtime.getRuntime();
			Process br_index = null;
			Runtime hr_mapping2 = Runtime.getRuntime();
			Process phr_mapping2 = null;
			String[] br_Index = { "sh", "-c", ParentPath + "/tools/minimap2 -d " + ParentPath
					+ "/alignment/scaffolds.mmi " + ParentPath + "/readfile/scaffolds.fasta" };
			br_index = b_index.exec(br_Index);
			br_index.waitFor();
			String[] cmd_mapping2 = { "sh", "-c",
					ParentPath + "/tools/minimap2 -a -t " + t + " " + ParentPath + "/alignment/scaffolds.mmi "
							+ ParentPath + "/alignment/OverlapRegions_high.fa > " + ParentPath
							+ "/alignment/OverlapRegions_high.sam" };
			phr_mapping2 = hr_mapping2.exec(cmd_mapping2);
			phr_mapping2.waitFor();
			int multiAlignment_index = 0;
			int singleAlignment_index = 0;
			String samStr = "";
			int overlaps_high_size = CommonClass.getFileLines(ParentPath + "/alignment/OverlapRegions_high.fa") / 2;
			String overlaps_high_Array[] = new String[overlaps_high_size];
			CommonClass.FileToArray2(ParentPath + "/alignment/OverlapRegions_high.fa", overlaps_high_Array, ">");
			ArrayList<String> Fragment_Id = new ArrayList<String>();
			ArrayList<String> Alignments_Str = new ArrayList<String>();
			File SamFilePath = new File(ParentPath + "/alignment/OverlapRegions_high.sam");
			if (SamFilePath.isFile() && SamFilePath.exists()) {
				InputStreamReader SamRead = new InputStreamReader(new FileInputStream(SamFilePath), "utf-8");
				BufferedReader bufferedSamReader = new BufferedReader(SamRead);
				while ((samStr = bufferedSamReader.readLine()) != null) {
					if (samStr.charAt(0) != '@') {
						String[] SamLine = samStr.split("\t|\\s+");
						if (!SamLine[1].equals("4")) {
							if (Fragment_Id.size() == 0) {
								Fragment_Id.add(SamLine[0]);
								Alignments_Str.add(samStr);
							} else {
								if (Fragment_Id.contains(SamLine[0])) {
									Alignments_Str.add(samStr);
								} else {
									if (Alignments_Str.size() >= 2) {
										String[] AlignLine = Fragment_Id.get(0).split("_");
										int overlap_ID = Integer.parseInt(AlignLine[1]);
										FileWriter writer1 = new FileWriter(
												ParentPath + "/alignment/RepeatLib_orginal.fa", true);
										writer1.write(">Node_" + (multiAlignment_index++) + "_"
												+ overlaps_high_Array[overlap_ID].length() + "\n"
												+ overlaps_high_Array[overlap_ID] + "\n");
										writer1.close();
										for (int j = 0; j < Alignments_Str.size(); j++) {
											FileWriter writers = new FileWriter(
													ParentPath + "/alignment/multiple_alignment.fa", true);
											writers.write(Alignments_Str.get(j) + "\n");
											writers.close();
										}

									} else {
										String[] AlignLine = Fragment_Id.get(0).split("_");
										int overlap_ID = Integer.parseInt(AlignLine[1]);
										FileWriter writer1 = new FileWriter(
												ParentPath + "/alignment/OverlapRegions_low.fa", true);
										writer1.write(">Node_" + (singleAlignment_index++) + "_"
												+ overlaps_high_Array[overlap_ID].length() + "\n"
												+ overlaps_high_Array[overlap_ID] + "\n");
										writer1.close();
									}
									Fragment_Id.clear();
									Alignments_Str.clear();
									Fragment_Id.add(SamLine[0]);
									Alignments_Str.add(samStr);
								}
							}
						}
					}
				}
				bufferedSamReader.close();
			}
			if (Alignments_Str.size() >= 2 && Fragment_Id.size() >= 1) {
				String[] AlignLine = Fragment_Id.get(0).split("_");
				int overlap_ID = Integer.parseInt(AlignLine[1]);
				FileWriter writer1 = new FileWriter(ParentPath + "/alignment/RepeatLib_orginal.fa", true);
				writer1.write(">Node_" + (multiAlignment_index++) + "_" + overlaps_high_Array[overlap_ID].length()
						+ "\n" + overlaps_high_Array[overlap_ID] + "\n");
				writer1.close();
				for (int j = 0; j < Alignments_Str.size(); j++) {
					FileWriter writers = new FileWriter(ParentPath + "/alignment/multiple_alignment.fa", true);
					writers.write(Alignments_Str.get(j) + "\n");
					writers.close();
				}
			} else {
				if (Fragment_Id.size() >= 1) {
					String[] AlignLine = Fragment_Id.get(0).split("_");
					int overlap_ID = Integer.parseInt(AlignLine[1]);
					FileWriter writer1 = new FileWriter(ParentPath + "/alignment/OverlapRegions_low.fa", true);
					writer1.write(">Node_" + (singleAlignment_index++) + "_" + overlaps_high_Array[overlap_ID].length()
							+ "\n" + overlaps_high_Array[overlap_ID] + "\n");
					writer1.close();
				}
			}
			overlaps_high_Array = null;
			Fragment_Id = null;
			Alignments_Str = null;
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "OverlapRegions_high.sam");
			CommonClass.RewriteFile(ParentPath + "/alignment/OverlapRegions_low.fa",
					ParentPath + "/alignment/OverlapRegions.fa", m);
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "OverlapRegions_low.fa");
			System.out.println("Step07: Conversion of the overlap sequences to unique k-mers");
			CommonClass.DelePathFiles(ParentPath, "OverlapRegions.h5");
			CommonClass.DelePathFiles(ParentPath + "/readfile/", "KmerFile.fa");
			CommonClass.DelePathFiles(ParentPath, "read.txt");
			Process p_dsk1 = null;
			Runtime r_dsk1 = Runtime.getRuntime();
			try {
				File ScaffLength = new File(ParentPath + "/readfile/scaffolds.fasta");
				File ReadLength = new File(ParentPath + "/readfile/Reads.fa");
				double scaffLen = ScaffLength.length();
				double readLen = ReadLength.length();
				int high_fre = (int) (Math.ceil(1.5 * readLen / scaffLen));
				System.out.println("high_fre:" + high_fre);
				String cmd1_1 = ParentPath + "/tools/dsk -file " + ParentPath
						+ "/alignment/OverlapRegions.fa -kmer-size " + k + " -abundance-min 1";
				String cmd1_2 = ParentPath + "/tools/dsk -file " + ParentPath
						+ "/readfile/Reads.fa -kmer-size 15 -abundance-min " + high_fre;
				p_dsk1 = r_dsk1.exec(cmd1_1);
				p_dsk1 = r_dsk1.exec(cmd1_2);
				p_dsk1.waitFor();
			} catch (Exception z) {
				System.out.println("Step4 Error:" + z.getMessage());
				z.printStackTrace();
			}
			Process p_dsk2 = null;
			Runtime r_dsk2 = Runtime.getRuntime();
			try {
				String cmd2_1 = ParentPath + "/tools/dsk2ascii -file OverlapRegions.h5 -out read.txt";
				String cmd2_2 = ParentPath + "/tools/dsk2ascii -file Reads.h5 -out Reads.txt";
				p_dsk2 = r_dsk2.exec(cmd2_1);
				p_dsk2 = r_dsk2.exec(cmd2_2);
				p_dsk2.waitFor();
			} catch (Exception z) {
				System.out.println("Step4 Error:" + z.getMessage());
				z.printStackTrace();
			}
			int KmerNum = 0;
			String ReadTemp = "";
			File Dskfile = new File(ParentPath + "/read.txt");
			RandomAccessFile aFile = new RandomAccessFile(ParentPath + "/readfile/KmerFile.fa", "rw");
			FileChannel inChannel = aFile.getChannel();
			if (Dskfile.isFile() && Dskfile.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(Dskfile), "utf-8");
				BufferedReader bufferedReaderScaff = new BufferedReader(read);
				while ((ReadTemp = bufferedReaderScaff.readLine()) != null) {
					String[] SplitLine = ReadTemp.split("\t|\\s+");
					String WriteContents = ">Node_" + (KmerNum++) + "\n" + SplitLine[0] + "\n";
					ByteBuffer buf = ByteBuffer.allocate(5000);
					buf.clear();
					buf.put(WriteContents.getBytes());
					buf.flip();
					while (buf.hasRemaining()) {
						inChannel.write(buf);
					}
				}
				bufferedReaderScaff.close();
				inChannel.close();
			}
			aFile.close();
			System.out.println("Step08: Mapping unique k-mers to the overlap sequences");
			int SplitSize = 0;
			int RealSize_Fasta = (CommonClass.getFileLines(ParentPath + "/alignment/OverlapRegions.fa")) / 2;
			if (RealSize_Fasta > t) {
				SplitSize = RealSize_Fasta / t;
				Process p_Split = null;
				Runtime r_Split = Runtime.getRuntime();
				String[] cmd_Split = { "sh", "-c", "split -l  " + 2 * SplitSize + " " + ParentPath
						+ "/alignment/OverlapRegions.fa -d -a 4 " + ParentPath + "/readfile/read_" };
				p_Split = r_Split.exec(cmd_Split);
				p_Split.waitFor();
			} else {
				Process p_Split = null;
				Runtime r_Split = Runtime.getRuntime();
				String[] cmd_Split = { "sh", "-c", "split -l  " + RealSize_Fasta + " " + ParentPath
						+ "/alignment/OverlapRegions.fa -d -a 4 " + ParentPath + "/readfile/read_" };
				p_Split = r_Split.exec(cmd_Split);
				p_Split.waitFor();
			}
			int SplitFileIndex = 0;
			File FilePath = new File(ParentPath + "/readfile/");
			if (!FilePath.exists() || !FilePath.isDirectory()) {
				System.out.println("Path:" + ParentPath + "/readfile is empty");
			} else {
				String[] tmpList = FilePath.list();
				if (tmpList != null) {
					for (String aTempList : tmpList) {
						File tmpFile = new File(FilePath, aTempList);
						if (tmpFile.isFile() && tmpFile.getName().startsWith("read_")) {
							String ID_change = "read_" + (SplitFileIndex++);
							File ReNameFile = new File(ParentPath + "/readfile/" + ID_change);
							tmpFile.renameTo(ReNameFile);
							tmpFile.delete();
						}
					}
				}
			}
			File refSeq = new File(ParentPath + "/alignment/OverlapRegions.fa");
			if (CommonClass.getFileSize(refSeq) / (1024 * 1024 * 1024) > 1) {
				int Threads = 4;
				int flagArray[][] = new int[SplitFileIndex][2];
				for (int h = 0; h < SplitFileIndex; h++) {
					flagArray[h][0] = 0;
					flagArray[h][1] = h;
				}
				int block_size = (int) Math.ceil((double) SplitFileIndex / Threads);
				for (int j = 0; j < block_size; j++) {
					ExecutorService pool_1 = Executors.newFixedThreadPool(Threads + 1);
					int u = 0;
					for (int s = 0; s < SplitFileIndex; s++) {
						if (flagArray[s][0] != 1) {
							if (u++ < Threads) {
								Threads_Ref mt = new Threads_Ref(flagArray[s][1], ParentPath,
										ParentPath + "/readfile/read_" + flagArray[s][1],
										ParentPath + "/readfile/KmerFile.fa", t);
								pool_1.execute(mt);
								flagArray[s][0] = 1;
							} else {
								Threads_Ref mt = new Threads_Ref(flagArray[s][1], ParentPath,
										ParentPath + "/readfile/read_" + flagArray[s][1],
										ParentPath + "/readfile/KmerFile.fa", t);
								pool_1.execute(mt);
								flagArray[s][0] = 1;
								break;
							}
						}
					}
					pool_1.shutdown();
					pool_1.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
				}
				flagArray = null;
			} else {
				int Threads = SplitFileIndex;
				ExecutorService pool_1 = Executors.newFixedThreadPool(Threads);
				for (int s = 0; s < SplitFileIndex; s++) {
					Threads_Ref mt = new Threads_Ref(s, ParentPath, ParentPath + "/readfile/read_" + s,
							ParentPath + "/readfile/KmerFile.fa", t);
					pool_1.execute(mt);
				}
				pool_1.shutdown();
				pool_1.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			}
			String MergeFileString = " ";
			int sortbam_count = 0;
			File CoverageFilePath = new File(ParentPath + "/alignment/");
			String[] tmpList3 = CoverageFilePath.list();
			if (tmpList3 != null) {
				for (String aTempList : tmpList3) {
					File tmpFile = new File(CoverageFilePath, aTempList);
					if (tmpFile.isFile() && (tmpFile.getName().startsWith("read_ms_")
							&& (tmpFile.getName().endsWith(".sort.bam")))) {
						MergeFileString += " " + ParentPath + "/alignment/" + tmpFile.getName();
						sortbam_count++;
					}
				}
			}
			if (sortbam_count < 1) {
				System.out.println("\n\n Multiple sequence alignment ended abnormally!"
						+ "\nPlease check the available disk space!\n\n");
			}
			Runtime b_bamMerge = Runtime.getRuntime();
			Process br_bamMerge = null;
			String[] cmd_bamMerge = { "sh", "-c", ParentPath + "/tools/samtools merge " + ParentPath
					+ "/alignment/Merge.sort.bam " + MergeFileString };
			br_bamMerge = b_bamMerge.exec(cmd_bamMerge);
			br_bamMerge.waitFor();
			Runtime k_depth = Runtime.getRuntime();
			Process pk_depth = null;
			String[] cmd_depth = { "sh", "-c", ParentPath + "/tools/samtools depth  " + ParentPath
					+ "/alignment/Merge.sort.bam > " + ParentPath + "/alignment/Merge.depth" };
			pk_depth = k_depth.exec(cmd_depth);
			pk_depth.waitFor();
			File Alignfile_Path = new File(ParentPath + "/alignment/");
			CommonClass.delSpecialFile(Alignfile_Path, "fre_", ".bt2");
			CommonClass.delSpecialFile(Alignfile_Path, "read_", ".fa");
			CommonClass.delSpecialFile(Alignfile_Path, "read_", ".bam");
			CommonClass.delSpecialFile(Alignfile_Path, "read_", ".sort.bam");
			CommonClass.delSpecialFile(Alignfile_Path, "read_", ".sort.bam.bai");
			System.out.println("Step09: Generation of the high coverage regions");
			CommonClass.generation_HighCovRegions(ParentPath + "/alignment/OverlapRegions.fa",
					ParentPath + "/alignment/Merge.depth", ParentPath + "/alignment/HighCoverageRegions_" + m + ".fa",
					m, k);
			Runtime pR_index = Runtime.getRuntime();
			Process R_index = null;
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "ref_Align.mmi");
			String[] cmd_pr_Index = { "sh", "-c", ParentPath + "/tools/minimap2 -d " + ParentPath
					+ "/alignment/ref_Align.mmi " + ParentPath + "/alignment/LinearFile.fasta" };
			R_index = pR_index.exec(cmd_pr_Index);
			R_index.waitFor();
			System.out.print("Step10: Mapping the high coverage regions to assemblies [");
			CommonClass.mapping_HighCovRegions2Ref(ParentPath, m, 2, r, t);
			CommonClass.RewriteFile(ParentPath + "/alignment/RepeatLib_orginal.fa",
					ParentPath + "/alignment/RepeatLib_interim.fa", m);
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "RepeatLib_orginal.fa");
			System.out.println("Step11: Merging fragments that have duplicate or include relationships");
			if (g.equals("") || g.equals("no")) {
				System.out.println("Mergeing process is skiped.");
			} else {
				CommonClass.merging_relationships(ParentPath, ParentPath + "/alignment/RepeatLib_interim.fa",
						ParentPath + "/alignment/RepeatLib.fa", k);
			}
			System.out.print("Step12: Fragment extension [ ");
			String readtemp = "";
			String encoding = "utf-8";
			HashMap<String, Integer> map_A = new HashMap<String, Integer>();
			HashMap<String, Integer> map_T = new HashMap<String, Integer>();
			HashMap<String, Integer> map_G = new HashMap<String, Integer>();
			HashMap<String, Integer> map_C = new HashMap<String, Integer>();
			File file1 = new File(ParentPath + "/Reads.txt");
			if (file1.exists()) {
				InputStreamReader read1 = new InputStreamReader(new FileInputStream(file1), encoding);
				BufferedReader bufferedReader1 = new BufferedReader(read1);
				while ((readtemp = bufferedReader1.readLine()) != null) {
					if (readtemp.charAt(0) == 'A') {
						String[] Splitp = readtemp.split("\t|\\s+");
						map_A.put(Splitp[0], Integer.parseInt(Splitp[1]));
					}
					if (readtemp.charAt(0) == 'T') {
						String[] Splitp = readtemp.split("\t|\\s+");
						map_T.put(Splitp[0], Integer.parseInt(Splitp[1]));
					}
					if (readtemp.charAt(0) == 'G') {
						String[] Splitp = readtemp.split("\t|\\s+");
						map_G.put(Splitp[0], Integer.parseInt(Splitp[1]));
					}
					if (readtemp.charAt(0) == 'C') {
						String[] Splitp = readtemp.split("\t|\\s+");
						map_C.put(Splitp[0], Integer.parseInt(Splitp[1]));
					}
				}
				bufferedReader1.close();
			}
			int sizefq1_1 = CommonClass.getFileLines(q1) / 4;
			int sizefq1_2 = CommonClass.getFileLines(q2) / 4;
			String Array_fq1[] = new String[sizefq1_1];
			String Array_fq2[] = new String[sizefq1_2];
			int realSizefq1 = CommonClass.FqToArray(q1, Array_fq1);
			int realSizefq2 = CommonClass.FqToArray(q2, Array_fq2);
			int common_size = 0;
			if (realSizefq1 > realSizefq2) {
				common_size = realSizefq2;
			} else {
				common_size = realSizefq1;
			}
			ExecutorService pool_1 = Executors.newFixedThreadPool(t);
			int NumSplits = (int) (Math.ceil((double) common_size / t));
			for (int w = 0; w < t; w++) {
				if (w != t - 1) {
					MarkingReadThreads mt_l = new MarkingReadThreads(0.8, 15, map_A, map_T, map_G, map_C, Array_fq1,
							NumSplits, w * NumSplits, (w + 1) * NumSplits - 1);
					pool_1.execute(mt_l);
				} else {
					MarkingReadThreads mt_l = new MarkingReadThreads(0.8, 15, map_A, map_T, map_G, map_C, Array_fq1,
							NumSplits, w * NumSplits, common_size - 1);
					pool_1.execute(mt_l);
				}
			}
			pool_1.shutdown();
			pool_1.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			ExecutorService pool_2 = Executors.newFixedThreadPool(t);
			System.out.print(" Lib1-Over, ");
			for (int w = 0; w < t; w++) {
				if (w != t - 1) {
					MarkingReadThreads mt_l = new MarkingReadThreads(0.8, 15, map_A, map_T, map_G, map_C, Array_fq2,
							NumSplits, w * NumSplits, (w + 1) * NumSplits - 1);
					pool_2.execute(mt_l);
				} else {
					MarkingReadThreads mt_l = new MarkingReadThreads(0.8, 15, map_A, map_T, map_G, map_C, Array_fq2,
							NumSplits, w * NumSplits, common_size - 1);
					pool_2.execute(mt_l);
				}
			}
			pool_2.shutdown();
			pool_2.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			System.out.print(" Lib2-Over, ");
			int l_q1 = 0;
			int r_q1 = 0;
			int single_index = 0;
			OutputStreamWriter write_q1 = new OutputStreamWriter(
					new FileOutputStream(ParentPath + "/readfile/q1_highfre.fa"), encoding);
			BufferedWriter bufferedWriter_q1 = new BufferedWriter(write_q1);
			OutputStreamWriter write_q2 = new OutputStreamWriter(
					new FileOutputStream(ParentPath + "/readfile/q2_highfre.fa"), encoding);
			BufferedWriter bufferedWriter_q2 = new BufferedWriter(write_q2);
			OutputStreamWriter write_single = new OutputStreamWriter(
					new FileOutputStream(ParentPath + "/readfile/single_highfre.fa"), encoding);
			BufferedWriter bufferedWriter_single = new BufferedWriter(write_single);
			for (int z = 0; z < common_size; z++) {
				if (Array_fq1[z].charAt(0) == '#' && Array_fq2[z].charAt(0) == '#') {
					bufferedWriter_q1
							.write(">" + (l_q1++) + "\n" + Array_fq1[z].substring(1, Array_fq1[z].length()) + "\n");
					bufferedWriter_q2
							.write(">" + (r_q1++) + "\n" + Array_fq2[z].substring(1, Array_fq2[z].length()) + "\n");
				}
				if (Array_fq1[z].charAt(0) == '#' && Array_fq2[z].charAt(0) != '#') {
					bufferedWriter_single.write(
							">" + (single_index++) + "\n" + Array_fq1[z].substring(1, Array_fq1[z].length()) + "\n");
				}
				if (Array_fq1[z].charAt(0) != '#' && Array_fq2[z].charAt(0) == '#') {
					bufferedWriter_single.write(
							">" + (single_index++) + "\n" + Array_fq2[z].substring(1, Array_fq2[z].length()) + "\n");
				}
			}
			bufferedWriter_q1.close();
			bufferedWriter_q2.close();
			bufferedWriter_single.close();
			Process p_assemblyRun2 = null;
			Runtime r_assemblyRun2 = Runtime.getRuntime();
			String[] cmd_assemblyRun = { "sh", "-c",
					"python " + ParentPath + "/tools/SPAdes-3.15.2/bin/spades.py --only-assembler -t 64 --pe1-1 "
							+ ParentPath + "/readfile/q1_highfre.fa" + " --pe1-2 " + ParentPath
							+ "/readfile/q2_highfre.fa" + " -s " + ParentPath + "/readfile/single_highfre.fa "
							+ " --trusted-contigs " + ParentPath + "/alignment/RepeatLib.fa " + " -o " + ParentPath
							+ "/readfile/Denovo/ > " + ParentPath + "/log/SPAdeslog2.out" };
			p_assemblyRun2 = r_assemblyRun2.exec(cmd_assemblyRun);
			p_assemblyRun2.waitFor();
			File scaffFile = new File(ParentPath + "/readfile/Denovo/scaffolds.fasta");
			if (scaffFile.exists()) {
				File replibFile = new File(ParentPath + "/alignment/RepeatLib.fa");
				CommonClass.deleteFile(replibFile);
				CommonClass.MergeFastaMultiLines2(ParentPath + "/readfile/Denovo/scaffolds.fasta",
						ParentPath + "/readfile/Denovo/scaffolds_line.fasta");
				CommonClass.RewriteFile2(ParentPath + "/readfile/Denovo/scaffolds_line.fasta",
						ParentPath + "/alignment/RepeatLib.fa", m);
			} 
			else 
			{
				String Assfile = "";
				List<String> paths = new ArrayList<String>();
				paths = CommonClass.getAllFilePaths(new File(ParentPath + "/readfile/Denovo/"), paths);
				for (String path : paths) {
					if (path.contains("before_rr.fasta"))
						Assfile = path;
				}
				File scaff2Reps = new File(Assfile);
				if (scaff2Reps.exists()) {
					File replibFile = new File(ParentPath + "/alignment/RepeatLib.fa");
					CommonClass.deleteFile(replibFile);
					CommonClass.MergeFastaMultiLines2(Assfile, ParentPath + "/readfile/Denovo/before_line.fasta");
					CommonClass.RewriteFile2(ParentPath + "/readfile/Denovo/before_line.fasta",
							ParentPath + "/alignment/RepeatLib.fa", m);
				}
			}
			CommonClass.copyFile(ParentPath + "/alignment/RepeatLib.fa", ParentPath + "/Results/RepeatLib.fa");
			System.out.println(", The final repeatLib is generated. ]");
			System.out.println("Step13: Identification of the genetic variations in repetitive regions");
			if (v.equals("yes") && (!f.equals(""))) {
				CommonClass.identification(ParentPath, f, ParentPath + "/Results", m);
			} else {
				System.out.println(" Missing the parameters v and f. VS identification is skiped ]");
			}
			System.out.println("Step14: Generating the final repetitive sequence library and dectection reports");
			if (!f.equals("")) {
				CommonClass.generation_reports(ParentPath, f, o);
			} else {
				System.out.println("Evaluation is not enabled, please adjust the parameter -f.");
			}
			System.out.println("===========================================================================================");
			System.out.println("Evaluations:");
			File FinalRepeats = new File(ParentPath + "/alignment/RepeatLib.fa");
			if (FinalRepeats.exists() && !f.equals("")) {
				if (M.equals("yes") || Q.equals("yes")) {
					if (M.equals("yes")) {
						CommonClass.calculateMul_AlignRatio(ParentPath, f, t);
					}
					if (Q.equals("yes")) {
						CommonClass.calculateN50_N75_N90(ParentPath);
					}
				} else {
					System.out
							.println("Evaluation is not enabled, please adjust -f, -M and -Q these three parameters.");
				}
			} else {
				System.out.println("There are no fragments longer than or equal to " + m
						+ "bp in the test results. Please adjust the value of parameter m.");
			}
		}
		// SMS
		else if ((r.equals("")) && (X1.equals("") && X2.equals("") && X3.equals("") && X4.equals("")) && (!l.equals(""))
				&& (q1.equals("") && q2.equals(""))) {
			System.out.println("  [Data Type]  = [ SMS Long Reads]");
			System.out.println("  [Setting] SMS long reads path = [ " + l + " ]");
			System.out.println("  [Setting] High coverage threshold for overlaps filtering = [ " + c + " ]");
			if (v.equals("yes")) {
				System.out.println("  [Setting] Variation identification = [ yes ]");
			} else {
				System.out.println("  [Setting] Variation identification = [ no ]");
			}
			System.out.println("  Read Number  = [ " + CommonClass.num_fragment(l) + " ]");
			System.out.println("  Total length of long reads  = [ " + CommonClass.getFileLength(l) + "bp ]");
			System.out.println("  The average length of long reads  = [ "
					+ ((double) CommonClass.getFileLength(l) / CommonClass.num_fragment(l)) + "bp ]");
			if (E.equals("yes")) {
				System.out.println("  Long reads error correction  = [ " + E + " ]");
			} else {
				System.out.println("  Long reads error correction  = [ no ]");
			}
			System.out.println("===========================================================================================");
			System.out.print("Step2: Loading long reads [");
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "LinearFile.fasta");
			if (E.equals("yes") && (l.endsWith(".fq")||l.endsWith(".fastq"))) {
				File PM_File = new File(ParentPath + "/readfile/LongReads.pm.can");
				File Corrected_File = new File(ParentPath + "/readfile/LongReads.corrected.fasta");
				if (PM_File.exists()) {
					CommonClass.deleteFile(PM_File);
				}
				if (Corrected_File.exists()) {
					CommonClass.deleteFile(Corrected_File);
				}
				Process p_longReadRun1 = null;
				Process p_longReadRun2 = null;
				Runtime r_longReadRun1 = Runtime.getRuntime();
				Runtime r_longReadRun2 = Runtime.getRuntime();
				String[] cmd_longReadRun1 = { "sh", "-c", ParentPath + "/tools/mecat2pw -j 0 -d " + l + " -o " + ParentPath + "/readfile/LongReads.pm.can -w " + ParentPath + "/readfile/ -t 64" };
				p_longReadRun1 = r_longReadRun1.exec(cmd_longReadRun1);
				p_longReadRun1.waitFor();
				String[] cmd_longReadRun2 = { "sh", "-c", ParentPath + "/tools/mecat2cns -i 0 -t 40 " + ParentPath + "/readfile/LongReads.pm.can " + l + " " + ParentPath + "/readfile/LongReads.corrected.fasta" };
				p_longReadRun2 = r_longReadRun2.exec(cmd_longReadRun2);
				p_longReadRun2.waitFor();
				if (PM_File.exists() && Corrected_File.exists()) {
					System.out.print(" lib:long-reads->Corrected ");
				} else {
					System.out.print(
							" Long reads error correction failed. Please check the format of input file (error correction is skiped) ");
					CommonClass.copyFile(l, ParentPath + "/readfile/LongReads.corrected.fasta");
				}
			} else {
				System.out.print(" Long reads error correction is skiped ");
				CommonClass.copyFile(l, ParentPath + "/readfile/LongReads.corrected.fasta");
			}
			System.out.println("]");
			CommonClass.MergeFastaMultiLines(ParentPath, ParentPath + "/readfile/LongReads.corrected.fasta",
					ParentPath + "/alignment/Oringnal.fasta");
			CommonClass.RewriteFile(ParentPath + "/alignment/Oringnal.fasta",
					ParentPath + "/alignment/LinearFile.fasta", m);
			System.out.print("Step3: Estimation of the average depth of sequencing [");
			Runtime b_index = Runtime.getRuntime();
			Process br_index = null;
			Runtime hr_mapping2 = Runtime.getRuntime();
			Process phr_mapping2 = null;
			String[] br_Index = { "sh", "-c", ParentPath + "/tools/minimap2 -d " + ParentPath
					+ "/alignment/longread_line.mmi " + ParentPath + "/alignment/LinearFile.fasta" };
			br_index = b_index.exec(br_Index);
			br_index.waitFor();
			//String[] cmd_mapping2 = { "sh", "-c", ParentPath + "/tools/minimap2 -a -N5000 -g50000 -w10 -k13 -m10 -r5000 -t" + t + " " + ParentPath+ "/alignment/longread_line.mmi " + ParentPath + "/alignment/LinearFile.fasta > "+ ParentPath + "/alignment/longread.sam" };
			String[] cmd_mapping2 = { "sh", "-c", ParentPath + "/tools/minimap2 -a -t " + t + " " + ParentPath+ "/alignment/longread_line.mmi " + ParentPath + "/alignment/LinearFile.fasta > "+ ParentPath + "/alignment/longread.sam" };
			phr_mapping2 = hr_mapping2.exec(cmd_mapping2);
			phr_mapping2.waitFor();
			Runtime k_sam2bam = Runtime.getRuntime();
			Process pk_sam2bam = null;
			String[] cmd_sam2bam = { "sh", "-c", ParentPath + "/tools/samtools  view  -bS  -T " +ParentPath + "/alignment/LinearFile.fasta " + ParentPath
					+ "/alignment/longread.sam > " + ParentPath + "/alignment/longread.bam" };
			pk_sam2bam = k_sam2bam.exec(cmd_sam2bam);
			pk_sam2bam.waitFor();
			Runtime k_bam2sort = Runtime.getRuntime();
			Process pk_bam2sort = null;
			String[] cmd_bam2sort = { "sh", "-c", ParentPath + "/tools/samtools  sort  " + ParentPath
					+ "/alignment/longread.bam > " + ParentPath + "/alignment/longread.sort.bam" };
			pk_bam2sort = k_bam2sort.exec(cmd_bam2sort);
			pk_bam2sort.waitFor();
			Runtime k_sort2index = Runtime.getRuntime();
			Process pk_sort2index = null;
			String[] cmd_sort2index = { "sh", "-c",
					ParentPath + "/tools/samtools  index  " + ParentPath + "/alignment/longread.sort.bam" };
			pk_sort2index = k_sort2index.exec(cmd_sort2index);
			pk_sort2index.waitFor();
			Runtime k_depth2 = Runtime.getRuntime();
			Process pk_depth2 = null;
			String[] cmd_depth2 = { "sh", "-c", ParentPath + "/tools/samtools depth  " + ParentPath
					+ "/alignment/longread.sort.bam > " + ParentPath + "/alignment/longread.depth" };
			pk_depth2 = k_depth2.exec(cmd_depth2);
			pk_depth2.waitFor();
			File bamfile_Path = new File(ParentPath + "/alignment/");
			CommonClass.delCommonFile(bamfile_Path, " ", ".sam");
			CommonClass.delCommonFile(bamfile_Path, " ", ".bam");
			CommonClass.delCommonFile(bamfile_Path, " ", ".sort.bam");
			String readDepth = "";
			double iterm_count = 0;
			double depthSum = 0;
			double averageDepth = 0;
			File readDepthFile = new File(ParentPath + "/alignment/longread.depth");
			if (readDepthFile.isFile() && readDepthFile.exists()) {
				InputStreamReader AlignRead = new InputStreamReader(new FileInputStream(readDepthFile), "utf-8");
				BufferedReader bufferedReaderDepth = new BufferedReader(AlignRead);
				while ((readDepth = bufferedReaderDepth.readLine()) != null) {
					String[] SplitDepthFile = readDepth.split("\t|\\s+");
					depthSum += Double.parseDouble(SplitDepthFile[2]);
					iterm_count++;
				}
				bufferedReaderDepth.close();
			}
			averageDepth = depthSum / iterm_count;
			System.out.println(" The average depth of sequencing is:" + averageDepth + ", High Coverage Threshold:"+ averageDepth * Double.parseDouble(c) + "]");
			System.out.println("Step4: Finding overlap sequences between long reads");
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "ovlp.paf");
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "ovlp_interim.paf");
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "ovlp_orginal.paf");
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "OverlapRegions.fa");
			Runtime r_overlap = Runtime.getRuntime();
			Process pr_overlap = null;
			String[] cmd_overlap = { "sh", "-c", ParentPath + "/tools/minimap2 -x ava-pb -g 3000 -w 10 -k 19 -m 100 -r 150 -t " + t + " "+ ParentPath + "/alignment/LinearFile.fasta " + ParentPath + "/alignment/LinearFile.fasta > " + ParentPath + "/alignment/ovlp_orginal.paf" };
			pr_overlap = r_overlap.exec(cmd_overlap);
			pr_overlap.waitFor();
			CommonClass.overlap_pretreatment(ParentPath + "/alignment/ovlp_orginal.paf", ParentPath + "/alignment/ovlp_interim.paf");
			Runtime r_sort = Runtime.getRuntime();
			Process pr_sort = null;
			String[] cmd_sort = { "sh", "-c", "sort -t '\t' -k 1 -n " + ParentPath + "/alignment/ovlp_interim.paf -o " + ParentPath + "/alignment/ovlp.paf" };
			pr_sort = r_sort.exec(cmd_sort);
			pr_sort.waitFor();
			int LinesOfScaffs = CommonClass.getFileLines(ParentPath + "/alignment/LinearFile.fasta") / 2;
			String SaveChangeLineScaffolds[] = new String[LinesOfScaffs];
			CommonClass.FileToArray2(ParentPath + "/alignment/LinearFile.fasta", SaveChangeLineScaffolds, ">");
			File OverlapFilePath = new File(ParentPath + "/alignment/ovlp.paf");
			ArrayList<String> readmark = new ArrayList<String>();
			ArrayList<String> overlaps = new ArrayList<String>();
			ArrayList<String> HighCoverageRegions = new ArrayList<String>();
			String OverlapStr = "";
			if (OverlapFilePath.isFile() && OverlapFilePath.exists()) {
				InputStreamReader DepthRead = new InputStreamReader(new FileInputStream(OverlapFilePath), "utf-8");
				BufferedReader bufferedReaderDepth = new BufferedReader(DepthRead);
				while ((OverlapStr = bufferedReaderDepth.readLine()) != null) {
					String[] SplitLine = OverlapStr.split("\t|\\s+");
					if (readmark.size() == 0) {
						readmark.add(SplitLine[0]);
						overlaps.add(OverlapStr);
					} 
					else 
					{
						if (readmark.contains(SplitLine[0])) {
							overlaps.add(OverlapStr);
						} 
						else 
						{
							if (overlaps.size() >= 1){
								int readID = 0;
								String[] DepthLine = overlaps.get(0).split("\t|\\s+");
								String[] ReadIDLine = DepthLine[0].split("_");
								readID = Integer.parseInt(ReadIDLine[1]);
								int CharArrayLength = SaveChangeLineScaffolds[readID].length();
								char SavePositionArray1[] = new char[CharArrayLength];
								for (int y = 0; y < CharArrayLength; y++) {
									SavePositionArray1[y] = 'N';
								}
								for (int p = 0; p < overlaps.size(); p++) {
									if (overlaps.get(p) != null) {
										String[] PositionLine = overlaps.get(p).split("\t|\\s+");
										int checkQuality = Integer.parseInt(PositionLine[11]);
										if (checkQuality >= 0) {
											int Start_Position = Integer.parseInt(PositionLine[2]);
											int End_Position = Integer.parseInt(PositionLine[3]);
											for (int s = Start_Position; s < End_Position; s++) {
												SavePositionArray1[s] = SaveChangeLineScaffolds[readID].charAt(s);
											}
										}
									}
								}
								String ReplaceStr1 = new String(SavePositionArray1);
								String[] SplitScaffLine1 = ReplaceStr1.split("N");
								for (int u = 0; u < SplitScaffLine1.length; u++) {
									if (SplitScaffLine1[u].length() >= m) {
										HighCoverageRegions.add(SplitScaffLine1[u]);
									}
								}
								readmark.clear();
								overlaps.clear();
								readmark.add(SplitLine[0]);
								overlaps.add(OverlapStr);
							}
						}
					}
				}
				bufferedReaderDepth.close();
			}
			if (overlaps.size() >= 1){
				int readID = 0;
				String[] DepthLine2 = overlaps.get(0).split("\t|\\s+");
				String[] ReadIDLine2 = DepthLine2[0].split("_");
				readID = Integer.parseInt(ReadIDLine2[1]);
				int CharArrayLength = SaveChangeLineScaffolds[readID].length();
				char SavePositionArray1[] = new char[CharArrayLength];
				for (int y = 0; y < CharArrayLength; y++) {
					SavePositionArray1[y] = 'N';
				}
				for (int p = 0; p < overlaps.size(); p++) {
					if (overlaps.get(p) != null) {
						String[] PositionLine = overlaps.get(p).split("\t|\\s+");
						int checkQuality = Integer.parseInt(PositionLine[11]);
						if (checkQuality >= 0) {
							int Start_Position = Integer.parseInt(PositionLine[2]);
							int End_Position = Integer.parseInt(PositionLine[3]);
							for (int s = Start_Position; s < End_Position; s++) {
								SavePositionArray1[s] = SaveChangeLineScaffolds[readID].charAt(s);
							}
						}
					}
				}
				String ReplaceStr1 = new String(SavePositionArray1);
				String[] SplitScaffLine1 = ReplaceStr1.split("N");
				for (int u = 0; u < SplitScaffLine1.length; u++) {
					if (SplitScaffLine1[u].length() >= m) {
						HighCoverageRegions.add(SplitScaffLine1[u]);
					}
				}
			}
			int overlap_Index = 0;
			for (int p = 0; p < HighCoverageRegions.size(); p++) {
				FileWriter writer1 = new FileWriter(ParentPath + "/alignment/OverlapRegions.fa", true);
				writer1.write(">Node_" + (overlap_Index++) + "_" + HighCoverageRegions.get(p).length() + "\n"+ HighCoverageRegions.get(p) + "\n");
				writer1.close();
			}
			readmark.clear();
			overlaps.clear();
			readmark = null;
			overlaps = null;
			SaveChangeLineScaffolds = null;
			HighCoverageRegions = null;
			System.out.println("Step5: Overlap sequences filtering");
			Runtime o_index = Runtime.getRuntime();
			Process or_index = null;
			Runtime ho_mapping2 = Runtime.getRuntime();
			Process pho_mapping2 = null;
			String[] or_Index = { "sh", "-c", ParentPath + "/tools/minimap2 -d " + ParentPath
					+ "/alignment/overlap_regions.mmi " + ParentPath + "/alignment/OverlapRegions.fa" };
			or_index = o_index.exec(or_Index);
			or_index.waitFor();
			String[] overlap_mapping2 = { "sh", "-c", ParentPath + "/tools/minimap2 -a -t" + t + " " + ParentPath + "/alignment/overlap_regions.mmi " + ParentPath + "/alignment/LinearFile.fasta > "+ ParentPath + "/alignment/longreads2Overlaps.sam" };
			pho_mapping2 = ho_mapping2.exec(overlap_mapping2);
			pho_mapping2.waitFor();
			Runtime o_sam2bam = Runtime.getRuntime();
			Process po_sam2bam = null;
			String[] overlap_sam2bam = { "sh", "-c", ParentPath + "/tools/samtools  view  -bS  -T " +ParentPath + "/alignment/OverlapRegions.fa " + ParentPath + "/alignment/longreads2Overlaps.sam > " + ParentPath + "/alignment/longreads2Overlaps.bam" };
			po_sam2bam = o_sam2bam.exec(overlap_sam2bam);
			po_sam2bam.waitFor();
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "longreads2Overlaps.sam");
			Runtime o_bam2sort = Runtime.getRuntime();
			Process po_bam2sort = null;
			String[] overlap_bam2sort = { "sh", "-c", ParentPath + "/tools/samtools  sort  " + ParentPath + "/alignment/longreads2Overlaps.bam > " + ParentPath + "/alignment/longreads2Overlaps.sort.bam" };
			po_bam2sort = o_bam2sort.exec(overlap_bam2sort);
			po_bam2sort.waitFor();
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "longreads2Overlaps.bam");
			Runtime o_sort2index = Runtime.getRuntime();
			Process po_sort2index = null;
			String[] overlap_sort2index = { "sh", "-c",
					ParentPath + "/tools/samtools  index  " + ParentPath + "/alignment/longreads2Overlaps.sort.bam" };
			po_sort2index = o_sort2index.exec(overlap_sort2index);
			po_sort2index.waitFor();
			Runtime o_depth2 = Runtime.getRuntime();
			Process po_depth2 = null;
			String[] overlap_depth2 = { "sh", "-c", ParentPath + "/tools/samtools depth  " + ParentPath + "/alignment/longreads2Overlaps.sort.bam > " + ParentPath + "/alignment/OverlapRegions.depth" };
			po_depth2 = o_depth2.exec(overlap_depth2);
			po_depth2.waitFor();
			CommonClass.DelePathFiles(ParentPath + "/alignment/", "longreads2Overlaps.sort.bam");
			int overlaps_size = CommonClass.getFileLines(ParentPath + "/alignment/OverlapRegions.fa") / 2;
			String overlaps_Array[] = new String[overlaps_size];
			CommonClass.FileToArray2(ParentPath + "/alignment/OverlapRegions.fa", overlaps_Array, ">");
			ArrayList<String> Overlap_Id = new ArrayList<String>();
			ArrayList<String> Overlap_Position = new ArrayList<String>();
			ArrayList<String> Overlap_Coverage = new ArrayList<String>();
			String ReadOverlap_Str = "";
			int Overlap_Num = 0;
			File OverlapDepth_FilePath = new File(ParentPath + "/alignment/OverlapRegions.depth");
			if (OverlapDepth_FilePath.isFile() && OverlapDepth_FilePath.exists()) {
				InputStreamReader ReadDepth = new InputStreamReader(new FileInputStream(OverlapDepth_FilePath),
						"utf-8");
				BufferedReader bufferedReaderDepth = new BufferedReader(ReadDepth);
				while ((ReadOverlap_Str = bufferedReaderDepth.readLine()) != null) {
					String[] SplitDepthLine = ReadOverlap_Str.split("\t|\\s+");
					if (Overlap_Id.size() == 0) {
						Overlap_Id.add(SplitDepthLine[0]);
						Overlap_Position.add(SplitDepthLine[1]);
						Overlap_Coverage.add(SplitDepthLine[2]);
					} 
					else 
					{
						if (Overlap_Id.contains(SplitDepthLine[0])) {
							Overlap_Position.add(SplitDepthLine[1]);
							Overlap_Coverage.add(SplitDepthLine[2]);
						} 
						else 
						{
							if (Overlap_Position.size() >= m) {
								int overlap_ID = 0;
								String[] DepthLine = Overlap_Id.get(0).split("\t|\\s+");
								String[] ReadIDLine = DepthLine[0].split("_");
								overlap_ID = Integer.parseInt(ReadIDLine[1]);
								double Cov_Sum = 0;
								double Ave_cov = 0;
								for (int p = 0; p < Overlap_Position.size(); p++) {
									if (Overlap_Coverage.get(p) != null) {
										Cov_Sum += Integer.parseInt(Overlap_Coverage.get(p));
									}
								}
								Ave_cov = Cov_Sum / Overlap_Position.size();
								if (Ave_cov >= averageDepth * Double.parseDouble(c)) {	
								FileWriter writer1 = new FileWriter(ParentPath + "/alignment/RepeatLib_orginal.fa",
											true);
									writer1.write(">Node_" + (Overlap_Num++) + "_" + overlaps_Array[overlap_ID].length()
											+ "\n" + overlaps_Array[overlap_ID] + "\n");
									writer1.close();
								}
							}
							Overlap_Id.clear();
							Overlap_Position.clear();
							Overlap_Coverage.clear();
							Overlap_Id.add(SplitDepthLine[0]);
							Overlap_Position.add(SplitDepthLine[1]);
							Overlap_Coverage.add(SplitDepthLine[2]);
						}
					}
				}
				bufferedReaderDepth.close();
			}
			if (Overlap_Position.size() >= m) {
				int overlap_ID = 0;
				String[] DepthLine = Overlap_Id.get(0).split("\t|\\s+");
				String[] ReadIDLine = DepthLine[0].split("_");
				overlap_ID = Integer.parseInt(ReadIDLine[1]);
				double Cov_Sum = 0;
				double Ave_cov = 0;
				for (int p = 0; p < Overlap_Position.size(); p++) {
					if (Overlap_Coverage.get(p) != null) {
						Cov_Sum += Integer.parseInt(Overlap_Coverage.get(p));
					}
				}
				Ave_cov = Cov_Sum / Overlap_Position.size();
				if (Ave_cov >= Math.ceil(averageDepth * Double.parseDouble(c))) {
					FileWriter writer1 = new FileWriter(ParentPath + "/alignment/RepeatLib_orginal.fa",true);
					writer1.write(">Node_" + (Overlap_Num++) + "_" + overlaps_Array[overlap_ID].length()+ "\n" + overlaps_Array[overlap_ID] + "\n");
					writer1.close();
				}
			}
			Overlap_Id.clear();
			Overlap_Position.clear();
			Overlap_Coverage.clear();
			overlaps_Array = null;
			Overlap_Id = null;
			Overlap_Position = null;
			Overlap_Coverage = null;
			System.out.print("[ The average coverage of overlap sequences is :"+averageDepth);
			if (v.equals("yes") && (!f.equals(""))) {
				int fileIndex = 0;
				int Repeat_SplitSize = 0;
				CommonClass.MergeFastaMultiLines(ParentPath, ParentPath + "/alignment/RepeatLib_orginal.fa", ParentPath + "/alignment/RepeatLib_line.fa");
				CommonClass.RewriteFile(ParentPath + "/alignment/RepeatLib_line.fa", ParentPath + "/alignment/RepeatLib.fa", m);
				CommonClass.copyFile(ParentPath + "/alignment/RepeatLib.fa", ParentPath + "/Results/RepeatLib.fa");
				System.out.println(", The final repeatLib is generated. ]");
				System.out.println("Step6: Identification of the genetic variations in repetitive regions");
				if (v.equals("yes") && (!f.equals(""))) {
					CommonClass.identification(ParentPath, f, ParentPath + "/Results", m);
				} else {
					System.out.println(" Missing the parameters v and f. VS identification is skiped ]");
				}
				System.out.println("Step7: Generating the final repetitive sequence library and dectection reports");
				if (!f.equals("")) {
					CommonClass.generation_reports(ParentPath, f, o);
				} else {
					System.out.println("Evaluation is not enabled, please adjust the parameter -f.");
				}
				System.out.println(
						"===========================================================================================");
				System.out.println("Evaluations:");
				File FinalRepeats = new File(ParentPath + "/alignment/RepeatLib.fa");
				if (FinalRepeats.exists() && !f.equals("")) {
					if (M.equals("yes") || Q.equals("yes")) {
						if (M.equals("yes")) {
							CommonClass.calculateMul_AlignRatio(ParentPath, f, t);
						}
						if (Q.equals("yes")) {
							CommonClass.calculateN50_N75_N90(ParentPath);
						}
					} 
					else 
					{
						System.out.println("Evaluation is not enabled, please adjust -f, -M and -Q these three parameters.");
					}
				} 
				else 
				{
					System.out.println("There are no fragments longer than or equal to " + m + "bp in the test results. Please adjust the value of parameter m.");
				}
			}
		} 
		else 
		{
			System.out.println("*****************************************************************************");
			System.out.println("*****************************************************************************");
			System.out.println("\nPlease check the configuration of parameters.\n");
			System.out.println("[Usage]: java [options] LongRepMarker [main arguments]");
			System.out.println("[options]:");
			System.out.println(" * -Xmx250G : This parameter is only used when working with large data sets.");
			System.out.println("[Main arguments]:");
			System.out.println(
					"-r   <The reference file or the assemblies file (This parameter is only used in reference-assisted mode)>\n"
							+ "-k [int]  <The k-mer size used during the detection(Default value: 49)>\n"
							+ "-e [yes|no]  <This parameter controls whether the short reads error correction is executed(Setting this parameter to 'yes' indicates that error correction will be executed)>\n"
							+ "-E [yes|no] <This parameter controls whether the long reads error correction is executed(Setting this parameter to 'yes' indicates that error correction will be executed)>\n"
							+ "-t [int] <The number of threads(Default value: 8)>\n"
							+ "-m [int] <The minimum size of repeats(Default value: 5kb)>\n"
							+ "-q1  [filepath] <The file with left reads for the 1-th paired-end reads>\n"
							+ "-q2  [filepath] <The file with right reads for the 1-th paired-end reads>\n"
							+ "-q3  [filepath] <The file with left reads for the 2-th paired-end reads>\n"
							+ "-q4  [filepath] <The file with right reads for the 2-th paired-end reads>\n"
							+ "-q5  [filepath] <The file with left reads for the 3-th paired-end reads>\n"
							+ "-q6  [filepath] <The file with right reads for the 3-th paired-end reads>\n"
							+ "-q7  [filepath] <The file with left reads for the 4-th paired-end reads>\n"
							+ "-q8  [filepath] <The file with right reads for the 4-th paired-end reads>\n"
							+ "-q9  [filepath] <The file with left reads for the 5-th paired-end reads>\n"
							+ "-q10 [filepath] <The file with right reads for the 5-th paired-end reads>\n"
							+ "-X   [filepath] <The file of 10X linked reads>\n"
							+ "-l   [filepath] <The file of the SMS long reads>\n"
							+ "-c   [double] <The coverage threshold which is used to filter the low coverage overlaps(Default value:1.5)>"
							+ "-A   [int] <The local alignment mode [1:fast, 2:sensitive, 3:very sensitive]>\n"
							+ "-M   [yes|no] <This parameter controls whether the alignment rate of detection results is counted(Setting this parameter to 'yes' indicates statistics)>\n"
							+ "-Q   [yes|no] <This parameter controls whether the effective size of detection results is counted(Setting this parameter to 'yes' indicates statistics)>\n"
							+ "-f   [filepath] <The reference file used for results evaluation>\n"
							+ "-v   [yes|no] <This parameter controls whether the structural variation detection is executed(Setting this parameter to 'yes' indicates that variation detection will be executed, this parameter is only used in de novo mode)>\n"
							+ "-o   [filepath] <The path used to save the final detection results>\n");
			System.out.println("*****************************************************************************");
			System.out.println("*****************************************************************************");
		}
		System.out
				.println("===========================================================================================");
		long orzr_main = Math.abs(startMem_main - r_main.freeMemory());
		long endTime_main = System.currentTimeMillis();
		System.out.println("Thank you for using LongRepMarker! [Total time consumption:"
				+ df.format((endTime_main - startTime_main)) + "ms. Total memory consumption:" + df1.format(orzr_main)
				+ "B] ");
	}
}
