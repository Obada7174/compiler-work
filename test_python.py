# Test Python code
def hello(name):
    print(f"Hello, {name}!")

class Person:
    def __init__(self, name, age):
        self.name = name
        self.age = age

    def greet(self):
        return f"Hi, I'm {self.name}"

# Main code
if __name__ == "__main__":
    person = Person("Alice", 25)
    hello(person.name)

    # List comprehension
    numbers = [x * 2 for x in range(10) if x % 2 == 0]

    # For loop
    for num in numbers:
        print(num)

    # Match statement (Python 3.10+)
    match person.age:
        case 25:
            print("Quarter century!")
        case _:
            print("Some other age")
