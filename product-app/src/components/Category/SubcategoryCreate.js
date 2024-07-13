import React, { useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

function SubcategoryCreate() {
    const { categoryId } = useParams();
    const [name, setName] = useState('');
    const [error, setError] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post(`/api/categories/${categoryId}/subcategories`, { name });
            console.log('Subcategory added:', response.data);
            setName(''); // Reset the input after successful addition
        } catch (error) {
            setError('Failed to add subcategory');
            console.error(error);
        }
    };

    return (
        <div>
            <h1>Add Subcategory</h1>
            {error && <p>{error}</p>}
            <form onSubmit={handleSubmit}>
                <label>
                    Subcategory Name:
                    <input
                        type="text"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                    />
                </label>
                <button type="submit">Add</button>
            </form>
        </div>
    );
}

export default SubcategoryCreate;
