#import the randint function from module random
from random import randint
import os
import json

import firebase_admin
from firebase_admin import credentials
from firebase_admin import auth
from firebase_admin import db

cred = credentials.Certificate("./eeapp-d4a14-firebase-adminsdk-z9an8-a11d23246c.json")
firebase_admin.initialize_app(cred, {'databaseURL': 'https://eeapp-d4a14-default-rtdb.europe-west1.firebasedatabase.app/'})

uid = 'some-uid'

custom_token = auth.create_custom_token(uid)

ref = db.reference('statistik')

x = ref.get()
print(x["falsch"])

exit()

#create empty array(list) to store all number inputs
numbers = []

#count gusses
cnt_guess = 0

#read start number from user
print("Please enter start number: ", end='')
start = int(input())

print("Please enter end number: ", end='') #end surpresses the newline character
end = int(input())

if start >= end:
    print("Start number lower or equal than end number")
    os._exit(1) #exit the program <- here! - but I don't know if there is a nicer way
    #(sys.exit - unfortunately this did not work, although I imported "sys")

random_number = randint(start, end)

#create a function to print all numbers of array
def print_numbers(nums):
    print("Your entered numbers: ", end='')
    for n in nums:
        print(n, end=', ')
    print("")
    #print("Your guess count: ", end='')
    #print(len(nums))

#check if a given number (gu) alread exists (in nums)
def check_number_exists(nums, gu):
    for n in nums:
        if n == gu:
            return True
    return False

# ------------------------------------------
# exit = 0
# while exit = 0:
while True :
    print("Please enter your guessed number or 0 (zero) to exit: ", end='')
    guess = int(input())

    #raise guess count
    cnt_guess += 1

    #the sequence of the checks is important
    #first check if the number is already in the array
    #and then...
    if check_number_exists(numbers, guess):
        print("Number allready entered!")
        continue

    #... if not, add it
    numbers.append(guess)

    if guess == 0:
        print("Exiting..., programm stopped, have a nice day. :)")
        # exit = 1
        break #os._exit(2) # exit can be used instead of break to notify which exit code we have

    if guess == random_number:
        print("Congratulation! You guessed the number! :)")
        print_numbers(numbers)
        print("Your guess count: ", end='')
        print(cnt_guess)
        break
