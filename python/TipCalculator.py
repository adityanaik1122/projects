def Calculator(bill, tip, split):
    spliting =( ((bill * (tip/100)) + bill) / split)
    return spliting
def main():
    print("Welcome to the tip calculator!")
    print("what was the total bill in £?")
    Bill = int(input())

    print("How much tip would you like to give? ")
    print("10%, 12% or 15%? ")
    Tip = int(input())

    print("How many people to split the bill? ")
    Split = int(input())   

    iRet = Calculator(Bill, Tip, Split) 

    print("Each person should pay : £",iRet)


if __name__ =="__main__":
    main()
