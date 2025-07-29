import React, { useState, useRef, useEffect } from 'react';
import './productDetails.css';

const PrdouctDetails = ({ product }) => {
    const [isOpen, setIsOpen] = useState(false);
    const popoverRef = useRef(null); // Reference to the popover element
    const triggerRef = useRef(null); // Reference to the button element that triggers the popover

    const toggleDetails = () => {
        setIsOpen(!isOpen);
    }

    // close popover when clicking outside
    useEffect(() => {
        const handleClickOutside = (event) => {
            if (
                popoverRef.current &&
                !popoverRef.current.contains(event.target) &&
                !triggerRef.current.contains(event.target)
            ) {
                setIsOpen(false); // Close the popover if clicked outside
            }
        };

        document.addEventListener('mousedown', handleClickOutside);
        return () => {
            document.removeEventListener('mousedown', handleClickOutside);
        };
    }, []);

    return (
        <div className="product-details-container">
            <button className="toggle-details" onClick={toggleDetails}>
                {isOpen ? 'Hide Details' : 'View Details'}
            </button>
            {
                isOpen ? (
                    <div className="product-details">
                        <h2>Product Details</h2>
                        <p><strong>ID:</strong> {product.product_key}</p>
                        <p><strong>Price:</strong> ${product.price}</p>
                        <p><strong>Retailer:</strong> {product.retailer}</p>
                        <p><strong>Brand:</strong> {product.brand}</p>
                        <p><strong>Model:</strong> {product.model}</p>
                        <p><strong>Name:</strong> {product.product_name}</p>
                        <p><strong>Description:</strong> {product.product_description}</p>
                    </div>
                ) : null
            }
        </div>
    );
};

export default PrdouctDetails;