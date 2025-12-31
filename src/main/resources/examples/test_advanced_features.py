# Test file for advanced Python features
# Tests: docstrings, comprehensions, conditional expressions, try/except

# =====================================================
# TEST 1: Function Docstrings
# =====================================================

def function_with_docstring():
    """
    This is a multi-line docstring.
    It should be recognized as the first statement.
    """
    x = 1
    return x

def another_function():
    """Single line docstring."""
    pass

class MyClass:
    """Class docstring."""

    def method(self):
        """Method docstring."""
        return True

# =====================================================
# TEST 2: Generator Expressions
# =====================================================

def test_generators():
    # Simple generator
    gen1 = (x for x in range(10))

    # Generator with condition
    gen2 = (x for x in range(100) if x % 2 == 0)

    # Generator in function call
    total = sum(x * 2 for x in range(10))

    # Generator with set()
    categories = set(p['category'] for p in products)

    # next() with generator and default
    item = next((p for p in products if p['id'] == 5), None)

    return gen1

# =====================================================
# TEST 3: List Comprehensions
# =====================================================

def test_list_comprehensions():
    # Simple comprehension
    squares = [x**2 for x in range(10)]

    # Comprehension with if
    evens = [x for x in range(20) if x % 2 == 0]

    # Nested comprehension
    matrix = [[i*j for j in range(3)] for i in range(3)]

    # Comprehension with complex expression
    names = [p['name'] for p in users if p.get('active', False)]

    return squares

# =====================================================
# TEST 4: Dict Comprehensions
# =====================================================

def test_dict_comprehensions():
    # Simple dict comprehension
    word_lengths = {word: len(word) for word in words}

    # Dict comprehension with filter
    filtered = {k: v for k, v in data.items() if v > 0}

    # Dict comprehension from list
    mapping = {p['id']: p['name'] for p in products}

    return word_lengths

# =====================================================
# TEST 5: Set Comprehensions
# =====================================================

def test_set_comprehensions():
    # Simple set comprehension
    unique_lengths = {len(word) for word in words}

    # Set comprehension with filter
    positive = {x for x in numbers if x > 0}

    # Set from nested data
    categories = {p['category'] for p in products}

    return unique_lengths

# =====================================================
# TEST 6: Conditional Expressions (Ternary)
# =====================================================

def test_conditional_expressions():
    # Simple ternary
    x = 10 if condition else 20

    # Nested ternary
    y = a if a > b else b if b > c else c

    # In function call
    max_value = max(items) if items else 0

    # With generator
    next_id = max(p['id'] for p in products) + 1 if products else 1

    # In return
    return value if value is not None else default

# =====================================================
# TEST 7: try/except/else/finally
# =====================================================

def test_try_except():
    # Simple try/except
    try:
        risky_operation()
    except ValueError:
        print("ValueError occurred")

    # try/except with alias
    try:
        dangerous_call()
    except ValueError as e:
        print(f"Error: {e}")

    # Multiple except clauses
    try:
        operation()
    except ValueError as e:
        handle_value_error(e)
    except TypeError as e:
        handle_type_error(e)
    except Exception as e:
        handle_generic_error(e)

    # try/except/else
    try:
        result = compute()
    except Exception as e:
        result = None
    else:
        print("Success!")

    # try/except/finally
    try:
        resource = open_resource()
    except IOError as e:
        print(f"Failed: {e}")
    finally:
        cleanup()

    # try/except/else/finally
    try:
        data = fetch_data()
    except ConnectionError as e:
        data = None
    else:
        process(data)
    finally:
        close_connection()

# =====================================================
# TEST 8: Boolean Operators
# =====================================================

def test_boolean_operators():
    # and/or/not
    result = flag1 and flag2 or flag3
    valid = name or email or phone
    check = not is_empty and is_valid

    # in/is operators
    if x in collection:
        pass
    if obj is None:
        pass
    if x is not None:
        pass

    return result

# =====================================================
# TEST 9: Combined Features
# =====================================================

def product_list():
    """
    Display all products with filtering and sorting.
    """
    try:
        # Get categories using set comprehension
        categories = {p['category'] for p in products}

        # Filter using list comprehension
        active = [p for p in products if p.get('active', True)]

        # Get next ID using conditional expression with generator
        next_id = max(p['id'] for p in products) + 1 if products else 1

        # Find specific item
        item = next((p for p in products if p['id'] == 5), None)

        return {
            'categories': categories,
            'active': active,
            'next_id': next_id,
            'item': item
        }
    except Exception as e:
        print(f"Error: {e}")
        return None

# =====================================================
# TEST 10: Complex Nested Structures
# =====================================================

def complex_function():
    """
    Complex function testing multiple features.
    """
    try:
        # Nested comprehensions with conditionals
        result = [
            {
                'id': p['id'],
                'name': p['name'],
                'tags': [t for t in p.get('tags', []) if t.startswith('prod')]
            }
            for p in products
            if p.get('active', True) and p.get('category') in allowed_categories
        ]

        # Generator with multiple conditions
        filtered = (
            item for item in items
            if item['status'] == 'active'
            if item['price'] > 0
        )

        # Conditional with boolean operators
        status = 'available' if (in_stock and not reserved) or special_order else 'unavailable'

        return result if result else []

    except ValueError as e:
        return []
    except Exception as e:
        return []
    finally:
        cleanup_resources()

# Global level comprehensions
products = []
users = []
items = []
data = {}
words = ['hello', 'world']
numbers = [1, 2, 3, 4, 5]
allowed_categories = {'electronics', 'books'}

# Global comprehensions
squared = [x**2 for x in range(10)]
categories_set = {p.get('category', 'unknown') for p in products}
