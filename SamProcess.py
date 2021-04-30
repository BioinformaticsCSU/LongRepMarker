import argparse
import os
import sys
from functools import cmp_to_key

import pysam
import re


def parse_sam(sam_path):
    mapping_count = {}
    sam_info = {}
    with open(sam_path, "r") as f_r:
        while True:
            line_sig = f_r.readline().replace('\n', '')
            if not line_sig:
                break
            elif line_sig.startswith("@"):
                continue
            else:
                parts = line_sig.split("\t")
                repeat_id = parts[0]
                sam_flag = parts[1]
                chrom = parts[2]
                mapping_start = parts[3]
                cigar = parts[5]
                info = (chrom, mapping_start, cigar)
                if sam_flag == '4':
                    # unmapping
                    count = 0
                else:
                    # mapping
                    count = 1
                    if not repeat_id in sam_info:
                        sam_info[repeat_id] = [info]
                    else:
                        sam_info[repeat_id].append(info)

                if repeat_id in mapping_count:
                    mapping_count[repeat_id] = mapping_count[repeat_id] + count
                else:
                    mapping_count[repeat_id] = count
    f_r.close()
    return mapping_count, sam_info


def get_N50_param(repeat_contigs, total_contig_len):
    N_len_map = {}
    len_fifty = total_contig_len * 0.5
    len_seventy_five = total_contig_len * 0.75
    len_ninety = total_contig_len * 0.9
    # since repeat_id is sorted by contig length, so we can go through map and get N50,N75,N90
    acc_len = 0
    N_50_found = False
    N_75_found = False
    N_90_found = False
    # sorted_repeat_contigs = [(k, repeat_contigs[k]) for k in sorted(repeat_contigs.keys(), key=cmp_to_key(lambda x, y: int(x.split('_')[1]) - int(y.split('_')[1])))]
    for item in repeat_contigs:
        repeat_id = item[0]
        contig = item[1]
        acc_len += len(contig)
        info = repeat_id + '-' + str(len(contig))
        if not N_50_found and acc_len >= len_fifty:
            N_len_map['N50'] = info
            N_50_found = True
        if not N_75_found and acc_len >= len_seventy_five:
            N_len_map['N75'] = info
            N_75_found = True
        if not N_90_found and acc_len >= len_ninety:
            N_len_map['N90'] = info
            N_90_found = True

        if N_50_found and N_75_found and N_90_found:
            break

    print(N_len_map)
    return N_len_map


def compute_identity(cigar, NM_tag, method):
    identity = 0
    n = int(NM_tag)
    if n == -1:
        return -1
    cigar = str(cigar)
    if method == "BLAST":
        l = 0
        it = re.finditer("(\d+)[MID]", cigar)
        for match in it:
            l += int(match.groups()[0])
        identity = float(l-n)/l
    elif method == "Gap-compressed":
        m = 0
        g = 0
        o = 0
        it = re.finditer("(\d+)M", cigar)
        for match in it:
            m += int(match.groups()[0])
        it = re.finditer("(\d+)[ID]", cigar)
        for match in it:
            g += int(match.groups()[0])
            o += 1
        identity = 1 - float(n-g+o) / (m+o)

    identity = format(identity, '.5f')
    return identity


def generate_map_info(sam_path, map_info_path, identity_mode):
    samfile = pysam.AlignmentFile(sam_path, "rb")

    # get mapping reference sequence
    unique_chrom = {}
    links = {}
    for read in samfile.fetch():
        if read.is_unmapped:
            continue
        qs = read.query_alignment_start
        qe = read.query_alignment_end

        rs = read.reference_start
        re = read.reference_end

        reference_name = read.reference_name
        query_name = read.query_name

        cigar = read.cigarstring
        NM_tag = 0
        try:
            NM_tag = read.get_tag('NM')
        except KeyError:
            NM_tag = -1

        identity = compute_identity(cigar, NM_tag, identity_mode)
        identity = float(identity) * 100
        link = (qs, qe, reference_name, rs, re, cigar, identity)

        if not links.__contains__(query_name):
            links[query_name] = [link]
        else:
            link_array = links[query_name]
            link_array.append(link)
            links[query_name] = link_array
        unique_chrom[reference_name] = 1
    samfile.close()

    # sort by query start
    sorted_query_name = sorted(links.keys(), key=cmp_to_key(lambda x, y: x < y))
    for query_name in sorted_query_name:
        link_array = links[query_name]
        link_array = sorted(link_array, key=lambda x: (x[0], x[1], x[3], x[4]))
        links[query_name] = link_array

    table_format = "{0:^20}\t{1:^20}\t{2:^20}\t{3:^20}\t{4:^20}\t{5:^20}\t{6:^60}\t{7:^20}\n"
    with open(map_info_path, "w") as f_save:
        f_save.write(table_format.format('[Repeat_id]', '[r(repeat)_start]', '[r_end]', '[chromosome]', '[R(Reference)_start]', '[R_end]', '[cigar]', '[identity(%)]'))
        for query_name in sorted_query_name:
            link_array = links[query_name]
            for link in link_array:
                f_save.write(table_format.format(str(query_name), str(link[0]), str(link[1]), str(link[2]), str(link[3]), str(link[4]), str(link[5]), str(link[6])))
    f_save.close()


if __name__ == '__main__':
    # 1.parse args
    parser = argparse.ArgumentParser(description='Generate map info file from sam')
    parser.add_argument('-s', metavar='sam file path',
                        help='input sam file path')
    parser.add_argument('-m', metavar='identity mode',
                        help='g for Gap-compressed; b for BLAST')
    parser.add_argument('-o', metavar='output dir',
                        help='input output dir')

    args = parser.parse_args()
    sam_path = args.s
    identity_mode = args.m
    output_dir = args.o
    map_info_path = output_dir + '/Annotation_report.info'

    identity_mode = 'BLAST' if identity_mode == 'b' else 'Gap-compressed'

    print("using %s for identity calculation" %identity_mode)

    generate_map_info(sam_path, map_info_path, identity_mode)