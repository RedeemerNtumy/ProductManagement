import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

function CategoryList() {
    const [categories, setCategories] = useState([]);
    const [error, setError] = useState('');
    const [editId, setEditId] = useState(null);
    const [editedName, setEditedName] = useState('');
    const [currentPage, setCurrentPage] = useState(1);
    const [categoriesPerPage] = useState(5); // Adjust number of categories per page as needed

    useEffect(() => {
        fetchCategories();
    }, []);

    const fetchCategories = async () => {
        try {
            const response = await axios.get('/api/categories');
            setCategories(response.data);
        } catch (err) {
            setError('Failed to fetch categories. Please try again later.');
        }
    };

    const indexOfLastCategory = currentPage * categoriesPerPage;
    const indexOfFirstCategory = indexOfLastCategory - categoriesPerPage;
    const currentCategories = categories.slice(indexOfFirstCategory, indexOfLastCategory);

    const handleEdit = (category) => {
        setEditId(category.id);
        setEditedName(category.name);
    };

    const saveEdit = async (id) => {
        try {
            await axios.put(`/api/categories/${id}`, { name: editedName });
            setEditId(null);
            fetchCategories(); // Refresh the list
        } catch (err) {
            setError('Failed to update category. Please try again later.');
        }
    };

    const cancelEdit = () => {
        setEditId(null);
    };

    const handleDelete = async (id) => {
        try {
            await axios.delete(`/api/categories/${id}`);
            fetchCategories(); // Refresh the list
        } catch (err) {
            setError('Failed to delete category. Please try again later.');
        }
    };

    const paginate = (pageNumber) => setCurrentPage(pageNumber);

    return (
        <div style={{ padding: '20px', maxWidth: '1200px', margin: 'auto', fontFamily: 'Arial, sans-serif' }}>
            <h2>Category List</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <div style={{ display: 'flex', flexWrap: 'wrap', justifyContent: 'space-around' }}>
                {currentCategories.map((category) => (
                    <div key={category.id} style={{ margin: '10px', border: '1px solid #ccc', padding: '20px', width: '220px', borderRadius: '4px' }}>
                        <img src="/logo512.png" alt="React Logo" style={{ width: '100%', marginBottom: '8px' }} />
                        <h3 style={{ margin: '8px 0' }}>{category.name}</h3>
                        {editId === category.id ? (
                            <>
                                <input
                                    type="text"
                                    value={editedName}
                                    onChange={(e) => setEditedName(e.target.value)}
                                    style={{ margin: '8px 0', padding: '10px', borderRadius: '4px', border: '1px solid #ccc', width: '100%' }}
                                />
                                <button onClick={() => saveEdit(category.id)} style={{ backgroundColor: '#FF9900', color: 'white', padding: '10px', borderRadius: '4px', border: 'none', cursor: 'pointer', width: '100%' }}>Save</button>
                                <button onClick={cancelEdit} style={{ backgroundColor: '#ccc', color: 'white', padding: '10px', borderRadius: '4px', border: 'none', cursor: 'pointer', width: '100%', marginTop: '8px' }}>Cancel</button>
                            </>
                        ) : (
                            <>
                                <button onClick={() => handleEdit(category)} style={{ backgroundColor: '#FF9900', color: 'white', padding: '10px', borderRadius: '4px', border: 'none', cursor: 'pointer', width: '100%' }}>Edit</button>
                                <button onClick={() => handleDelete(category.id)} style={{ backgroundColor: '#D9534F', color: 'white', padding: '10px', borderRadius: '4px', border: 'none', cursor: 'pointer', width: '100%', marginTop: '8px' }}>Delete</button>
                                <Link to={`/categories/${category.id}/subcategories`} style={{ display: 'block', backgroundColor: '#337AB7', color: 'white', padding: '10px', borderRadius: '4px', textAlign: 'center', textDecoration: 'none', marginTop: '8px' }}>Manage Subcategories</Link>
                            </>
                        )}
                    </div>
                ))}
            </div>
            <div style={{ textAlign: 'center', marginTop: '20px' }}>
                {[...Array(Math.ceil(categories.length / categoriesPerPage)).keys()].map(number => (
                    <button key={number + 1} onClick={() => paginate(number + 1)} style={{ backgroundColor: '#FF9900', color: 'white', padding: '10px', borderRadius: '4px', cursor: 'pointer', margin: '5px' }}>
                        {number + 1}
                    </button>
                ))}
            </div>
        </div>
    );
}

export default CategoryList;
