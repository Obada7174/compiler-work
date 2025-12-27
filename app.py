from flask import Flask, render_template, request, redirect, url_for, flash
from datetime import datetime

app = Flask(__name__)
app.secret_key = 'your-secret-key-here'

products = [
    {
        'id': 1,
        'name': 'Wireless Bluetooth Headphones',
        'price': 79.99,
        'description': 'Premium quality wireless headphones with noise cancellation and 30-hour battery life. Perfect for music lovers and professionals.',
        'category': 'Electronics',
        'category_id': 1,
        'category_name': 'Electronics',
        'stock': 45,
        'image_url': '/static/images/headphones.jpg',
        'rating': 4,
        'reviews': 128,
        'review_count': 128,
        'sku': 'ELEC-HEAD-001',
        'brand': 'AudioTech',
        'warranty': '2 Years',
        'shipping_info': 'Free Shipping',
        'restock_date': '2025-01-15',
        'last_updated': '2025-12-20',
        'specifications': [
            {'name': 'Battery Life', 'value': '30 hours'},
            {'name': 'Bluetooth Version', 'value': '5.0'},
            {'name': 'Weight', 'value': '250g'},
            {'name': 'Color Options', 'value': 'Black, White, Blue'}
        ],
        'reviews': [
            {
                'author_name': 'John Smith',
                'date': '2025-12-01',
                'rating': 5,
                'content': 'Amazing sound quality! The noise cancellation is superb.',
                'verified_purchase': True
            },
            {
                'author_name': 'Sarah Johnson',
                'date': '2025-11-28',
                'rating': 4,
                'content': 'Great headphones, but a bit heavy for long sessions.',
                'verified_purchase': True
            }
        ]
    },
    {
        'id': 2,
        'name': 'Smart Fitness Watch',
        'price': 199.99,
        'description': 'Track your fitness goals with this advanced smartwatch featuring heart rate monitoring, GPS, and water resistance.',
        'category': 'Electronics',
        'category_id': 1,
        'category_name': 'Electronics',
        'stock': 23,
        'image_url': '/static/images/smartwatch.jpg',
        'rating': 5,
        'reviews': 89,
        'review_count': 89,
        'sku': 'ELEC-WATCH-002',
        'brand': 'FitPro',
        'warranty': '1 Year',
        'shipping_info': 'Free Shipping',
        'restock_date': '2025-01-10',
        'last_updated': '2025-12-18',
        'specifications': [
            {'name': 'Display', 'value': '1.4" AMOLED'},
            {'name': 'Battery Life', 'value': '7 days'},
            {'name': 'Water Resistance', 'value': '50m'},
            {'name': 'Compatibility', 'value': 'iOS & Android'}
        ],
        'reviews': [
            {
                'author_name': 'Mike Davis',
                'date': '2025-12-05',
                'rating': 5,
                'content': 'Perfect for tracking my workouts. Highly recommend!',
                'verified_purchase': True
            }
        ]
    },
    {
        'id': 3,
        'name': 'Ergonomic Office Chair',
        'price': 299.99,
        'description': 'Comfortable ergonomic office chair with lumbar support, adjustable armrests, and breathable mesh back.',
        'category': 'Furniture',
        'category_id': 2,
        'category_name': 'Furniture',
        'stock': 67,
        'image_url': '/static/images/office-chair.jpg',
        'rating': 4,
        'reviews': 245,
        'review_count': 245,
        'sku': 'FURN-CHAIR-003',
        'brand': 'ComfortSeating',
        'warranty': '5 Years',
        'shipping_info': '$15.00',
        'restock_date': '2025-01-05',
        'last_updated': '2025-12-22',
        'specifications': [
            {'name': 'Weight Capacity', 'value': '300 lbs'},
            {'name': 'Height Range', 'value': '17-21 inches'},
            {'name': 'Material', 'value': 'Mesh & Steel'},
            {'name': 'Assembly Required', 'value': 'Yes'}
        ],
        'reviews': [
            {
                'author_name': 'Emily Chen',
                'date': '2025-12-10',
                'rating': 4,
                'content': 'Very comfortable for long work hours. Assembly was easy.',
                'verified_purchase': True
            },
            {
                'author_name': 'Robert Wilson',
                'date': '2025-12-08',
                'rating': 5,
                'content': 'Best office chair I have ever owned!',
                'verified_purchase': True
            }
        ]
    },
    {
        'id': 4,
        'name': 'Stainless Steel Water Bottle',
        'price': 24.99,
        'description': 'Insulated stainless steel water bottle keeps drinks cold for 24 hours or hot for 12 hours. BPA-free and eco-friendly.',
        'category': 'Home & Kitchen',
        'category_id': 3,
        'category_name': 'Home & Kitchen',
        'stock': 156,
        'image_url': '/static/images/water-bottle.jpg',
        'rating': 5,
        'reviews': 312,
        'review_count': 312,
        'sku': 'HOME-BOTTLE-004',
        'brand': 'HydroMax',
        'warranty': 'Lifetime',
        'shipping_info': 'Free Shipping',
        'restock_date': '2025-01-20',
        'last_updated': '2025-12-25',
        'specifications': [
            {'name': 'Capacity', 'value': '32 oz'},
            {'name': 'Material', 'value': 'Stainless Steel'},
            {'name': 'Insulation', 'value': 'Double-walled'},
            {'name': 'Dishwasher Safe', 'value': 'No'}
        ],
        'reviews': [
            {
                'author_name': 'Lisa Anderson',
                'date': '2025-12-15',
                'rating': 5,
                'content': 'Keeps my water ice cold all day long!',
                'verified_purchase': True
            }
        ]
    },
    {
        'id': 5,
        'name': 'Professional Camera Kit',
        'price': 1299.99,
        'description': 'Complete camera kit with 24MP sensor, multiple lenses, tripod, and carrying case. Perfect for professional photographers.',
        'category': 'Electronics',
        'category_id': 1,
        'category_name': 'Electronics',
        'stock': 8,
        'image_url': '/static/images/camera.jpg',
        'rating': 5,
        'reviews': 67,
        'review_count': 67,
        'sku': 'ELEC-CAM-005',
        'brand': 'PhotoPro',
        'warranty': '3 Years',
        'shipping_info': 'Free Express Shipping',
        'restock_date': '2025-02-01',
        'last_updated': '2025-12-27',
        'specifications': [
            {'name': 'Sensor', 'value': '24MP CMOS'},
            {'name': 'Video', 'value': '4K @ 60fps'},
            {'name': 'ISO Range', 'value': '100-25600'},
            {'name': 'Included Lenses', 'value': '18-55mm, 55-200mm'}
        ],
        'reviews': [
            {
                'author_name': 'David Martinez',
                'date': '2025-12-12',
                'rating': 5,
                'content': 'Outstanding image quality and build. Worth every penny!',
                'verified_purchase': True
            }
        ]
    }
]

# Categories for the product form
categories = [
    {'id': 1, 'name': 'Electronics'},
    {'id': 2, 'name': 'Furniture'},
    {'id': 3, 'name': 'Home & Kitchen'},
    {'id': 4, 'name': 'Sports & Outdoors'},
    {'id': 5, 'name': 'Books'}
]

# Store configuration
STORE_NAME = 'TechMart Store'

# Sample user data for demonstration
current_user = {
    'name': 'Admin User',
    'is_logged_in': True,
    'is_admin': True,
    'role': 'Administrator',
    'cart_items': 3
}


@app.route('/products')
def product_list():
    """
    Display all products in a grid layout.
    Template: test1_display_products.html
    """
    # Calculate total number of unique categories
    unique_categories = len(set(p['category'] for p in products))

    return render_template(
        'test1_display_products.html',
        page_title='Product Catalog - ' + STORE_NAME,
        store_name=STORE_NAME,
        products=products,
        product_count=len(products),
        category_count=unique_categories,
        user=current_user
    )



@app.route('/products/add', methods=['GET', 'POST'])
def add_product():
    """
    Add a new product to the catalog.
    GET: Display the add product form
    POST: Process form submission and add product
    Template: test2_add_product.html
    """
    if request.method == 'POST':

        try:

            next_id = max(p['id'] for p in products) + 1 if products else 1


            name = request.form.get('name', '').strip()
            category_id = int(request.form.get('category', 1))
            price = float(request.form.get('price', 0))
            description = request.form.get('description', '').strip()
            stock = int(request.form.get('stock', 0))
            sku = request.form.get('sku', f'PROD-{next_id:03d}').strip()

            category_name = next((c['name'] for c in categories if c['id'] == category_id), 'Uncategorized')

            new_product = {
                'id': next_id,
                'name': name,
                'price': price,
                'description': description,
                'category': category_name,
                'category_id': category_id,
                'category_name': category_name,
                'stock': stock,
                'image_url': '/static/images/default-product.jpg',
                'rating': 5,
                'reviews': 0,
                'review_count': 0,
                'sku': sku,
                'brand': 'Generic',
                'warranty': '1 Year',
                'shipping_info': 'Standard Shipping',
                'restock_date': '2025-02-01',
                'last_updated': datetime.now().strftime('%Y-%m-%d'),
                'specifications': [
                    {'name': 'SKU', 'value': sku},
                    {'name': 'Stock', 'value': str(stock)}
                ],
                'reviews': []
            }
            products.append(new_product)
            flash('Product added successfully!', 'success')
            return redirect(url_for('product_list'))

        except ValueError as e:
            flash(f'Error adding product: Invalid input. Please check your data.', 'error')
        except Exception as e:

            flash(f'Error adding product: {str(e)}', 'error')

      return render_template(
        'test2_add_product.html',
        page_title='Add New Product - ' + STORE_NAME,
        form_title='Add New Product',
        store_name=STORE_NAME,
        categories=categories,
        user=current_user
    )


@app.route('/products/<int:id>')
def product_detail(id):
    """
    Display detailed information about a specific product.
    Template: test3_product_details.html
    Error Handling: Returns 404 page if product not found
    """

    product = next((p for p in products if p['id'] == id), None)

    if product is None:
        return render_template(
            'error.html',
            page_title='Product Not Found',
            error_code=404,
            error_message='The product you are looking for does not exist.',
            store_name=STORE_NAME
        ), 404

    related_products = [
        p for p in products
        if p['category'] == product['category'] and p['id'] != product['id']
    ][:4]

    return render_template(
        'test3_product_details.html',
        page_title=f"{product['name']} - {STORE_NAME}",
        store_name=STORE_NAME,
        product=product,
        related_products=related_products
    )



@app.route('/products/delete/<int:id>')
def delete_product(id):
    """
    Delete a product from the catalog.
    Provides safe deletion with validation.
    """
    global products

    product = next((p for p in products if p['id'] == id), None)

    if product is None:
        flash('Product not found. Unable to delete.', 'error')
        return redirect(url_for('product_list'))

    products = [p for p in products if p['id'] != id]

    flash(f'Product "{product["name"]}" has been deleted successfully.', 'success')
    return redirect(url_for('product_list'))


@app.errorhandler(404)
def page_not_found(e):
    """Handle 404 errors"""
    return render_template(
        'error.html',
        page_title='Page Not Found',
        error_code=404,
        error_message='The page you are looking for does not exist.',
        store_name=STORE_NAME
    ), 404


@app.errorhandler(500)
def internal_server_error(e):
    """Handle 500 errors"""
    return render_template(
        'error.html',
        page_title='Server Error',
        error_code=500,
        error_message='An internal server error occurred. Please try again later.',
        store_name=STORE_NAME
    ), 500



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
