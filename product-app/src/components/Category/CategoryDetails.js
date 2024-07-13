import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

function CategoryDetails() {
    const { categoryId } = useParams();
    const [category, setCategory] = useState(null);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchCategory = async () => {
            try {
                const response = await axios.get(`/api/categories/${categoryId}`);
                setCategory(response.data);
            } catch (error) {
                setError('Failed to fetch category');
                console.error(error);
            }
        };

        fetchCategory();
    }, [categoryId]);

    return (
        <div>
            <h1>Category Details</h1>
            {error && <p>{error}</p>}
            {category ? (
                <div>
                    <h2>{category.name}</h2>
                    <h3>Subcategories:</h3>
                    <ul>
                        {category.subcategories.map((subcategory) => (
                            <li key={subcategory.id}>{subcategory.name}</li>
                        ))}
                    </ul>
                </div>
            ) : (
                <p>Loading category details...</p>
            )}
        </div>
    );
}

export default CategoryDetails;
