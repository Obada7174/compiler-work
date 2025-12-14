from flask import Flask, render_template

app = Flask(__name__)

# Sample data
users = [
    {'name': 'Alice', 'email': 'alice@example.com'},
    {'name': 'Bob', 'email': 'bob@example.com'},
    {'name': 'Charlie', 'email': 'charlie@example.com'}
]

items = [
    {'title': 'Item 1', 'description': 'First item', 'color': '#ff0000','price':3443},
    {'title': 'Item 2', 'description': 'Second item', 'color': '#00ff00','price':3443},
    {'title': 'Item 3', 'description': 'Third item', 'color': '#0000ff','price':3443}
]

@app.route('/')
def index():
    user = {
        'name': 'John Doe',
        'is_logged_in': True
    }
    return render_template('index.html',
                         page_title='Home Page',
                         heading='Welcome',
                         user=user,
                         items=items,
                         theme_color='#007bff')

@app.route('/about')
def about():
    return render_template('about.html',
                         page_title='About Us',
                         heading='About Our Company')

@app.route('/users')
def user_list():
    return render_template('users.html',
                         page_title='Users',
                         users=users)

def calculate_total(items):
    total = 0
    for item in items:
        total = total + item['price']
    return total

class User:
    def __init__(self, name, email):
        self.name = name
        self.email = email

    def get_display_name(self):
        return f"{self.name} <{self.email}>"

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)


