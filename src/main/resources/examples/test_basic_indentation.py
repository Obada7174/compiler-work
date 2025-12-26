# Test basic CPython indentation

# Test 1: Function with indentation
def func1():
    x = 1
    y = 2

# Test 2: Class with method
class MyClass:
    def method1(self):
        x = 1

# Test 3: Multiline list
my_list = [
    1,
    2,
    3
]

# Test 4: Multiline dict
my_dict = {
    'a': 1,
    'b': 2
}

# Test 5: Multiline function call
result = some_function(
    arg1,
    arg2
)

# Test 6: if/elif/else
if True:
    x = 1
elif False:
    y = 2
else:
    z = 3

# Test 7: Nested if
if True:
    if False:
        x = 1
    else:
        y = 2

# Test 8: try/except/finally
try:
    x = 1
except:
    y = 2
finally:
    z = 3

print("Done")
