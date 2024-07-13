import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

function ProductList() {
    const [products, setProducts] = useState([]);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const response = await axios.get('/api/products');
                setProducts(response.data);
            } catch (err) {
                setError('Failed to fetch products.');
            }
        };
        fetchProducts();
    }, []);

    return (
        <div style={{ padding: '20px', maxWidth: '800px', margin: 'auto' }}>
            <h2>Product List</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {products.length > 0 ? (
                <ul>
                    {products.map((product) => (
                        <li key={product.id}>
                            {product.name}
                            <Link to={`/products/${product.id}/update`} style={{ marginLeft: '10px' }}>Edit</Link>
                            <Link to={`/products/${product.id}/delete`} style={{ marginLeft: '10px' }}>Delete</Link>
                        </li>
                    ))}
                </ul>
            ) : (
                <p>No products found. Please add some products.</p>
            )}
        </div>
    );
}

export default ProductList;
