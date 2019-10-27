#Read in the data
data<-read.csv("Tester.csv", nrow=4820)

#permute the index using sample method
permutation <- sample(1:nrow(data), nrow(data), replace = FALSE)

#sets break points at 70th percentile and 90th percentile
breaks <- c(0.7*nrow(data), 0.9*nrow(data))

#separates data into three parts: 70%, 20%, and 10% of the data
first.sample <- 1:breaks[1]
second.sample <- (breaks[1]+1):breaks[2]
third.sample <- (breaks[2]+1):nrow(data)

one <- data[permutation[first.sample],]
two <- data[permutation[second.sample],]
three <- data[permutation[third.sample],]

##Export the output into csv file
write.csv(one, "Training.csv", row.names = FALSE)
write.csv(two, "Validation.csv", row.names = FALSE)
write.csv(three, "Testing.csv", row.names = FALSE)
