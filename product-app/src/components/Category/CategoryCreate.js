import React, { useState } from 'react';
import axios from 'axios';

function CategoryCreate() {
    const [name, setName] = useState('');
    const [error, setError] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('/api/categories', { name });
            console.log('Category created:', response.data);
            // Optionally reset form or give user feedback
            setName('');
        } catch (error) {
            setError('Failed to create category');
            console.error(error);
        }
    };

    return (
        <div>
            <h1>Create Category</h1>
            {error && <p>{error}</p>}
            <form onSubmit={handleSubmit}>
                <label>
                    Category Name:
                    <input
                        type="text"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                    />
                </label>
                <button type="submit">Create</button>
            </form>
        </div>
    );
}

export default CategoryCreate;
