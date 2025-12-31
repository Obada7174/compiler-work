# Test file for dictionary and list literal parsing
# This should parse without errors in ANTLR Preview

# Simple list literals
empty_list = []
simple_list = [1, 2, 3]
list_with_trailing_comma = [1, 2, 3,]

# Simple dictionary literals
empty_dict = {}
simple_dict = {"a": 1, "b": 2}
dict_with_trailing_comma = {"a": 1, "b": 2,}

# Nested lists
nested_list = [1, [2, 3], 4]
deeply_nested_list = [1, [2, [3, [4, 5]]]]
matrix = [
    [1, 2, 3],
    [4, 5, 6],
    [7, 8, 9]
]

# Nested dictionaries
nested_dict = {"outer": {"inner": 1}}
deeply_nested_dict = {
    "level1": {
        "level2": {
            "level3": {
                "value": 42
            }
        }
    }
}

# Complex nested structures
config = {
    "database": {
        "host": "localhost",
        "port": 5432,
        "credentials": {
            "user": "admin",
            "pass": "secret",
        },
    },
    "cache": {
        "enabled": True,
        "ttl": 3600,
    },
}

# Lists containing dictionaries
list_of_dicts = [
    {"name": "Alice", "age": 30},
    {"name": "Bob", "age": 25},
    {"name": "Charlie", "age": 35,},
]

# Dictionaries containing lists
dict_of_lists = {
    "numbers": [1, 2, 3, 4, 5],
    "letters": ["a", "b", "c"],
    "empty": [],
}

# Multi-line list with trailing commas
shopping_list = [
    "apples",
    "bananas",
    "oranges",
    "grapes",
]

# Multi-line dict with trailing commas
user_data = {
    "username": "john_doe",
    "email": "john@example.com",
    "active": True,
    "roles": ["admin", "user"],
}

# Mixed nesting
complex_structure = {
    "users": [
        {
            "id": 1,
            "name": "Alice",
            "tags": ["admin", "active"],
        },
        {
            "id": 2,
            "name": "Bob",
            "tags": ["user"],
        },
    ],
    "settings": {
        "theme": "dark",
        "notifications": {
            "email": True,
            "sms": False,
        },
    },
}
