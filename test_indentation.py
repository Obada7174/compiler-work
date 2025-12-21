# Simple Python test for indentation
def greet(name):
    print("Hello")
    print(name)

class Person:
    def __init__(self, name):
        self.name = name

    def say_hello(self):
        print("Hi")

# If-elif-else
if True:
    x = 10
    if x > 5:
        print("Big")
    else:
        print("Small")
elif False:
    print("Never")
else:
    print("Always")

# For loop
for i in range(5):
    print(i)

# While loop
count = 0
while count < 3:
    count = count + 1
    print(count)

# Try-except
try:
    x = 10
except:
    print("Error")
finally:
    print("Done")

print("Test complete")
