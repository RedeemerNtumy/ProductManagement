import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import CategoryCreate from './Category/CategoryCreate';
import CategoryDetails from './Category/CategoryDetails';
import SubcategoryCreate from './Category/SubcategoryCreate';
import SubcategoryDelete from './Category/SubcategoryDelete';
import ProductAdd from './Product/ProductAdd';
import ProductUpdate from './Product/ProductUpdate';
import ProductDelete from './Product/ProductDelete';
import ProductsBySubcategory from './Product/ProductsBySubcategory';
import ProductsByCategory from './Product/ProductsByCategory';
import ProductList from './Product/ProductList';

function App() {
    return (
        <Router>
            <div>
                <h1>Product Management System</h1>
                <Routes>
                    <Route path="/" element={<ProductList />} />
                    <Route path="/categories/new" element={<CategoryCreate />} />
                    <Route path="/categories/:categoryId" element={<CategoryDetails />} />
                    <Route path="/categories/:categoryId/subcategories/new" element={<SubcategoryCreate />} />
                    <Route path="/categories/:categoryId/subcategories/:subcategoryId/delete" element={<SubcategoryDelete />} />
                    <Route path="/products/new" element={<ProductAdd />} />
                    <Route path="/products/:productId/update" element={<ProductUpdate />} />
                    <Route path="/products/:productId/delete" element={<ProductDelete />} />
                    <Route path="/products/subcategory/:subcategoryId" element={<ProductsBySubcategory />} />
                    <Route path="/products/category/:categoryId" element={<ProductsByCategory />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
