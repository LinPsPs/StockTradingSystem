# CSE 305 Final Project
## General Description
We use an online server as our database, you can access our datebase with IP: 107.155.113.86:3306, Database name: STOCKSYSTEM, user name: cse305, password: CSE305XYZ
## Test case 1: Sign up
You should firstly sign up at least 3 accounts in our online stock trading system - customer, customer representive, manager.

It's worth noting that the customer accounts are created by the customer representive.

We have already created 3 test accounts, they are

1. *Alice: Manager*  Email: alice.manager@test.com Password: test
2. *John: Customer Rresentative*    Email: john.cr@test.com Password: test
3. *Howard: Customer* Email: howard.customer@test.com Password: test

## Test case 2: Place order
We load 6 stocks for test. All stocks have an initial stock price history with change date 2019-05-01 00:00:00


In trailing stop case, the number in the textfield means 100% minus that number. For example, if you put 5 in that textfield, it means sell this stock when the price become equal or lower 95% of or original price.

#some information

1. Our databese is hosted at 107.155.113.86:3306

2. The login information is avaliable in dao classes, but user and password still provided in the upper.

3. The user interface has been modified.

4. For invalid inputs, we provided input inspection to prevent invalid inputs, please make sure you fallow the format we provided.

