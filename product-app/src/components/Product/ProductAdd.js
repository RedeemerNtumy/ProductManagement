import React, { useState, useEffect } from 'react';
import axios from 'axios';

function ProductAdd() {
    const [subcategories, setSubcategories] = useState([]);
    const [selectedSubcategory, setSelectedSubcategory] = useState('');
    const [productName, setProductName] = useState('');
    const [price, setPrice] = useState('');
    const [description, setDescription] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    useEffect(() => {
        const fetchSubcategories = async () => {
            try {
                const response = await axios.get('/api/categories');  // Assuming endpoint to fetch all subcategories
                setSubcategories(response.data.flatMap(category => category.subcategories));
            } catch (err) {
                setError('Failed to fetch subcategories.');
            }
        };
        fetchSubcategories();
    }, []);

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await axios.post('/api/products', {
                subcategoryId: selectedSubcategory,
                name: productName,
                price: parseFloat(price),
                description
            });
            if (response.status === 200) {
                setSuccess('Product added successfully!');
                setProductName('');
                setPrice('');
                setDescription('');
                setError('');
            } else {
                throw new Error('Failed to create product');
            }
        } catch (err) {
            setError('Failed to create product. Please try again.');
            setSuccess('');
        }
    };

    return (
        <div style={{ padding: '20px', maxWidth: '500px', margin: 'auto' }}>
            <h2>Add New Product</h2>
            <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
                <select
                    value={selectedSubcategory}
                    onChange={(e) => setSelectedSubcategory(e.target.value)}
                    style={{ padding: '10px', fontSize: '16px' }}
                >
                    <option value="">Select Subcategory</option>
                    {subcategories.map((sub) => (
                        <option key={sub.id} value={sub.id}>
                            {sub.name}
                        </option>
                    ))}
                </select>
                <input
                    type="text"
                    value={productName}
                    onChange={(e) => setProductName(e.target.value)}
                    placeholder="Enter product name"
                    style={{ padding: '10px', fontSize: '16px' }}
                />
                <input
                    type="number"
                    value={price}
                    onChange={(e) => setPrice(e.target.value)}
                    placeholder="Enter price"
                    style={{ padding: '10px', fontSize: '16px' }}
                />
                <textarea
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    placeholder="Enter product description"
                    style={{ padding: '10px', fontSize: '16px', height: '100px' }}
                />
                <button type="submit" style={{ padding: '10px 20px', backgroundColor: '#FF9900', border: 'none', color: 'white', fontWeight: 'bold', cursor: 'pointer' }}>
                    Add Product
                </button>
                {error && <p style={{ color: 'red' }}>{error}</p>}
                {success && <p style={{ color: 'green' }}>{success}</p>}
            </form>
        </div>
    );
}

export default ProductAdd;
