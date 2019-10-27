import numpy as np

##reads the data given as a parameter line by line
##stores the input value of each categorical variables into a vector
def readin(data):
	with open(data, "r") as fo:
		dataset = []
		linenum = 0
		#for each line in the input(csv file), we spilt the line by comma delimeter
		for line in fo:
			if linenum==0:
				linenum += 1
				continue
			newdata=line.split(",")
			colnum = 0
			tempdata = []
			#for each numeric value in each line, we store it into a vector
			for s in newdata:
				if colnum==0: 
					colnum += 1
					continue
				s=float(s)
				colnum += 1
				tempdata.append(s)
			dataset.append(tempdata)
			linenum += 1
			
	return dataset

##predicts the target value using the equation of dot product of input vector and weight vector plus bias 	
def predict(data,weights,bias):
	input=np.array(data[:-1])
	weight=np.array(weights)
	if input.dot(weight)+bias>0:
		return 1
	else:
		return 0

##updates the bias and the weights using the error and the learning rate, which is achieved by actual target minus the predicted target
def update(learningrate, data, weights, bias):
	error=data[-1]-predict(data,weights,bias)
	newbias=bias+learningrate*error
	newWeight=np.array(weights)+learningrate*error*np.array(data[:-1])
	return (newbias,newWeight)

##goes through N iterations, passed by a parameter, and updates bias and weights every iteration 
def perceptron(learningrate, dataset, weights, bias, N):
	for i in range(N):	
		for data in dataset:
			(bias,weights)=update(learningrate,data,weights,bias)
			#print(bias,weights)
	return (bias,weights)


	
##Read in training dataset
data="Training.csv"
dataset=readin(data)

##Set initial conditions: learning rate, weights, bias, and number of iterations 
lrate = 0.1
weig = [0.3] * (len(dataset[0])-1)
bias = 0.5
N = 150

output = open("ResultTester2.csv","w")
output.write("Number of iterations,accuracy rate(testing),accuracy rate(training)\n")


while N >= 0:
	print(N)
	if N < 0: continue
	##implements perceptron algorithm to acquire final bias and weights for N iterations
	a = perceptron(lrate,dataset,weig,bias,N)
	data2=readin("Testing.csv")
	data3=readin("Training.csv")
	
	# get accuracy on testing data set
	count = 0
	correct = 0
	for line in data2:
		actual = line[-1]
		pred = predict(line, a[1], a[0])
		count += 1
		if actual == pred:
			correct += 1
	output.write(str(N)+","+str(correct/count))	
			
	# get accuracy on training data set
	count = 0
	correct = 0
	for line in data3:
		actual = line[-1]
		pred = predict(line, a[1], a[0])
		count += 1
		if actual == pred:
			correct += 1
	output.write(","+str(correct/count)+"\n")
	N-=1