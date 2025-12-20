# Test CPython-style indentation handling

# Test 1: Basic indentation
def func1():
    x = 1
    y = 2

# Test 2: Nested indentation
class MyClass:
    def method1(self):
        if True:
            x = 1

# Test 3: Multiline list (implicit continuation)
my_list = [
    1,
    2,
    3
]

# Test 4: Multiline dict (implicit continuation)
my_dict = {
    'a': 1,
    'b': 2,
    'c': 3
}

# Test 5: Multiline function call (implicit continuation)
result = some_function(
    arg1,
    arg2,
    arg3
)

# Test 6: Decorator with multiline
@decorator1
@decorator2
def decorated():
    pass

# Test 7: if/elif/else with proper DEDENT
if True:
    x = 1
elif False:
    y = 2
else:
    z = 3

# Test 8: for/else
for i in [1, 2, 3]:
    print(i)
else:
    print("done")

# Test 9: try/except/finally
try:
    x = 1
except:
    y = 2
finally:
    z = 3

print("All tests complete")
