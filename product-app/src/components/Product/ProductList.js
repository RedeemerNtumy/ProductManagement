import React, { useState, useEffect } from 'react';
import axios from 'axios';

function ProductList() {
    const [products, setProducts] = useState([]);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const response = await axios.get('/api/products');
                setProducts(response.data);
            } catch (error) {
                setError('Failed to fetch products');
                console.error(error);
            }
        };

        fetchProducts();
    }, []);

    return (
        <div>
            <h1>All Products</h1>
            {error && <p>{error}</p>}
            {products.length > 0 ? (
                <ul>
                    {products.map((product) => (
                        <li key={product.id}>{product.name}</li>
                    ))}
                </ul>
            ) : (
                <p>No products available.</p>
            )}
        </div>
    );
}

export default ProductList;
