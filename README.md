# Security_Analysis

## Goals
- Compare predictions of the prices of publicly traded securities in the near future using a single-layer perceptron versus logistic regression. 
- Validate the application of machine learning in the financial discipline

## Methods
- Web Scraping: Java was used to scrape financial values of all ticker symbols currently being traded on the New York Stock Exchange (NYSE)
- Data Analysis:
  <ul>
    <li>The complete data contains 53 financial variables for 4828 securities. These variables were narrowed down to only significant predictors in the logistic regression model using R.</li>
    <li>The target variable’s value was determined by looking at each symbol’s price 7 days afterthe data was collected,  and comparing it to the original price;  price decreases correspondto a target of 0; price increases correspond to a target of 1</li>
    <li>The statistical procedure was implemented in R’s base package under the glm method with the binomial family parameter</li>
    <li>A single-layer perceptron was implemented in Python. The entire data set was split into three smaller sets using R:  a training set (70%), a validation set (20%), and a testing set (10%).</li>
    <li>The single-layer perceptron was trained onthe training set and then checked and improved on the validation set</li>
  </ul>
