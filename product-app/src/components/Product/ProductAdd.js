import React, { useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

function ProductAdd() {
    const { subcategoryId } = useParams();
    const [name, setName] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('/api/products', { subcategoryId, name });
            console.log('Product added:', response.data);
            setSuccess('Product added successfully.');
            setName(''); // Reset the input after successful addition
        } catch (error) {
            setError('Failed to add product');
            console.error(error);
        }
    };

    return (
        <div>
            <h1>Add Product</h1>
            {error && <p>{error}</p>}
            {success && <p>{success}</p>}
            <form onSubmit={handleSubmit}>
                <label>
                    Product Name:
                    <input
                        type="text"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                    />
                </label>
                <button type="submit">Add Product</button>
            </form>
        </div>
    );
}

export default ProductAdd;
