import sys
reload(sys)
fr=open(sys.argv[1],'r')
fw=open(sys.argv[2],'w')
cnt=0
for line in fr:
        if line.startswith('>'):
                if(cnt==0):
                        fw.write(line)
                else:
                        fw.write('\n')
                        fw.write(line)
        else:
                fw.write(line.replace('\n',''))
        cnt=cnt+1
fw.write('\n')
fr.close()
fw.close()
