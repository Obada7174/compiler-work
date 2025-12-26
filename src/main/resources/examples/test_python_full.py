#!/usr/bin/env python3
"""
Comprehensive Python test file
Tests multiple Python language features
"""

import os
import sys
from typing import List, Dict, Optional

# Global variables
CONSTANT = 42
config = {"debug": True, "version": "1.0"}

# Function definitions
def simple_function():
    """A simple function"""
    pass

def function_with_args(a, b, c=10, *args, **kwargs):
    """Function with various parameter types"""
    result = a + b + c
    return result

async def async_function(data: List[str]) -> Dict:
    """Async function with type hints"""
    await some_coroutine()
    return {"status": "done"}

# Lambda functions
double = lambda x: x * 2
add = lambda x, y: x + y

# Class definitions
class BaseClass:
    """Base class example"""
    class_var = "shared"

    def __init__(self, name):
        self.name = name

    def method(self):
        return f"Hello from {self.name}"

class DerivedClass(BaseClass):
    """Derived class example"""

    def __init__(self, name, value):
        super().__init__(name)
        self.value = value

    @property
    def display(self):
        return f"{self.name}: {self.value}"

    @staticmethod
    def static_method():
        return "Static"

    @classmethod
    def class_method(cls):
        return cls.class_var

# Control flow
def test_control_flow():
    # If-elif-else
    x = 10
    if x > 20:
        print("Greater than 20")
    elif x > 5:
        print("Greater than 5")
    else:
        print("Less than or equal to 5")

    # While loop
    count = 0
    while count < 5:
        count += 1

    # For loop
    for i in range(10):
        if i == 3:
            continue
        if i == 7:
            break
        print(i)

    # For with else
    for item in [1, 2, 3]:
        print(item)
    else:
        print("Loop completed")

# Exception handling
def test_exceptions():
    try:
        risky_operation()
    except ValueError as e:
        print(f"ValueError: {e}")
    except (TypeError, KeyError):
        print("Type or Key error")
    except Exception:
        print("Generic exception")
    else:
        print("No exceptions")
    finally:
        print("Cleanup")

# Comprehensions
def test_comprehensions():
    # List comprehension
    squares = [x**2 for x in range(10)]

    # List comprehension with condition
    evens = [x for x in range(20) if x % 2 == 0]

    # Nested comprehension
    matrix = [[i*j for j in range(3)] for i in range(3)]

    # Dict comprehension
    word_lengths = {word: len(word) for word in ["hello", "world"]}

    # Set comprehension
    unique_squares = {x**2 for x in [1, 2, 2, 3, 3, 3]}

    # Generator expression
    gen = (x for x in range(100) if x % 3 == 0)

# Pattern matching (Python 3.10+)
def test_match(value):
    match value:
        case 0:
            return "zero"
        case 1 | 2 | 3:
            return "small"
        case int(x) if x > 100:
            return "large"
        case [first, *rest]:
            return f"List starting with {first}"
        case {"name": name, "age": age}:
            return f"{name} is {age}"
        case _:
            return "unknown"

# Context managers
def test_context_managers():
    with open("file.txt", "r") as f:
        data = f.read()

    with open("file1.txt") as f1, open("file2.txt") as f2:
        data = f1.read() + f2.read()

# Decorators
@simple_decorator
def decorated_function():
    pass

@decorator_with_args(param=True)
@another_decorator
def multi_decorated():
    pass

# Advanced features
def test_advanced():
    # Unpacking
    a, b, *rest = [1, 2, 3, 4, 5]
    first, *middle, last = range(10)

    # Dictionary unpacking
    merged = {**dict1, **dict2}

    # Function call unpacking
    args = [1, 2, 3]
    kwargs = {"a": 1, "b": 2}
    function(*args, **kwargs)

    # Walrus operator
    if (n := len(data)) > 10:
        print(f"Large dataset: {n}")

    # Type hints
    def typed_function(x: int, y: str) -> List[str]:
        return [y] * x

# Async/await
async def test_async():
    result = await async_operation()

    async with async_context():
        await process()

    async for item in async_iterator():
        await handle(item)

# Assertions and global/nonlocal
def test_scopes():
    global CONSTANT
    CONSTANT = 100

    def inner():
        nonlocal x
        x = 20

    assert x == 20, "Assertion failed"

# Main block
if __name__ == "__main__":
    print("Running Python test file")
    test_control_flow()
    test_exceptions()
    test_comprehensions()

    # Test match
    result = test_match([1, 2, 3])
    print(result)
