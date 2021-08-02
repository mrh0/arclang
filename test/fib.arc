n1=0
n2=1
n3=0
count=1000
i=2
rep=10000000
bench:
	for 0..rep:
		while i<count:
			n3=n1+n2
			n1=n2
			n2=n3
			i+=1
		end
	end
end