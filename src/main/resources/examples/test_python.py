# Enhanced Python Test Code - All features supported by grammar
def hello(name):
    print(f"Hello, {name}!")
    return name

def calculate(x, y, z):
    result = x + y
    return result

class Person:
    def __init__(self, name, age):
        self.name = name
        self.age = age

    def greet(self):
        return f"Hi, I'm {self.name}"

    def get_info(self):
        info = self.name
        return info

class Student(Person):
    def __init__(self, name, age, grade):
        self.grade = grade

    def study(self):
        print("Studying...")
        return True

# Main code
if __name__ == "__main__":
    person = Person("Alice", 25)
    hello(person.name)

    # Create a list the traditional way
    numbers = [0, 2, 4, 6, 8]

    # For loop
    for num in numbers:
        print(num)

    # While loop
    counter = 0
    while counter < 5:
        print(counter)
        counter = counter + 1

    # Nested if statements
    age = person.age
    if age > 18:
        print("Adult")
        if age > 65:
            print("Senior")
        else:
            print("Regular adult")
    else:
        print("Minor")

    # Try-except block
    try:
        result = calculate(10, 20, 30)
        print(result)
    except:
        print("Error occurred")

    # Multiple function calls
    student = Student("Bob", 20, "A")
    student.greet()
    student.study()

    # Accessing attributes
    name = student.name
    grade = student.grade

    # Boolean values
    is_active = True
    is_disabled = False
    nothing = None
