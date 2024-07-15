// ProductDetails.js
import React, { useState, useEffect } from 'react';

const ProductDetails = ({ match }) => {
    const [product, setProduct] = useState(null);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchProduct = async () => {
            try {
                const response = await fetch(`/api/products/${match.params.productId}`);
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                const data = await response.json();
                setProduct(data);
            } catch (error) {
                setError(error.message);
            }
        };

        fetchProduct();
    }, [match.params.productId]);

    if (error) {
        return <div style={{ textAlign: 'center', color: 'red' }}>{error}</div>;
    }

    if (!product) {
        return <div style={{ textAlign: 'center' }}>Loading...</div>;
    }

    const styles = {
        productDetails: {
            display: 'flex',
            margin: '20px',
            padding: '20px',
            border: '1px solid #ccc',
            borderRadius: '5px',
            backgroundColor: '#fff'
        },
        productImage: {
            width: '300px',
            height: '300px',
            marginRight: '20px',
            objectFit: 'contain'
        },
        productInfo: {
            display: 'flex',
            flexDirection: 'column'
        },
        productName: {
            fontSize: '24px',
            marginBottom: '10px'
        },
        productDescription: {
            fontSize: '16px',
            color: '#555',
            marginBottom: '20px'
        },
        productPrice: {
            fontSize: '20px',
            fontWeight: 'bold',
            color: '#B12704'
        }
    };

    return (
        <div style={styles.productDetails}>
            <img src={product.imageUrl || "/logo192.png"} alt={product.name} style={styles.productImage} />
            <div style={styles.productInfo}>
                <h1 style={styles.productName}>{product.name}</h1>
                <p style={styles.productDescription}>{product.description}</p>
                <p style={styles.productPrice}>${product.price}</p>
            </div>
        </div>
    );
};

export default ProductDetails;
