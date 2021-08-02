n1=0
n2=1
n3=0
i=2
count=1000

rep = 10//000000//00
x = 0
bench:
	while x<rep:
		while i<count:
			n3=n1+n2
			//log n3
			n1=n2 
			n2=n3
			i+=1
		end
		x+=1
	end
end

for 0..-6:
	log "yo"