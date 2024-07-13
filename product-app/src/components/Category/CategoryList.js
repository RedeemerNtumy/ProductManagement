import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

function CategoryList() {
    const [categories, setCategories] = useState([]);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchCategories = async () => {
            try {
                const response = await axios.get('/api/categories');
                setCategories(response.data);
            } catch (err) {
                setError('Failed to fetch categories. Please try again later.');
            }
        };
        fetchCategories();
    }, []);

    return (
        <div style={{ padding: '20px', maxWidth: '800px', margin: 'auto' }}>
            <h2>Category List</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {categories.length > 0 ? (
                <ul>
                    {categories.map((category) => (
                        <li key={category.id}>
                            {category.name}
                            <Link to={`/categories/${category.id}/subcategories`} style={{ marginLeft: '10px' }}>Manage Subcategories</Link>
                        </li>
                    ))}
                </ul>
            ) : (
                <p>No categories found. Please add some categories.</p>
            )}
        </div>
    );
}

export default CategoryList;
