import React, { useState, useRef, useEffect } from 'react';
import { Button } from 'reactstrap';
import './productDetails.css';

const PrdouctDetails = ({ product }) => {
    const [isOpen, setIsOpen] = useState(false);
   
    const toggleDetails = () => {
        setIsOpen(!isOpen);
    }
    
    const price = product.price ? `$${product.price.toFixed(2)}` : 'N/A'; // Format price to 2 decimal places
    return (
        <div className="product-details-container">
            <Button color="primary" className="toggle-details" onClick={toggleDetails}>
                {isOpen ? 'Hide Details' : 'Details'}
            </Button>
            {
                
                // Render the popover with product details if isOpen is true
                isOpen ? (
                    <div className="product-details">
                        <h2>Product Details</h2>
                        <div><strong>ID:</strong> {product.product_key}</div>
                        <div><strong>Price:</strong> {price}</div>
                        <div><strong>Retailer:</strong> {product.retailer}</div>
                        <div><strong>Brand:</strong> {product.brand}</div>
                        <div><strong>Model:</strong> {product.model}</div>
                        <div><strong>Name:</strong> {product.product_name}</div>
                        <div><strong>Description:</strong> {product.product_description}</div>
                    </div>
                ) : null
            }
        </div>
    );
};

export default PrdouctDetails;