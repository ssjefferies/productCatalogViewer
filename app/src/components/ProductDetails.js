import React, { useState, useRef, useEffect } from 'react';
import { Button } from 'reactstrap';
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
            <Button color="primary" className="toggle-details" onClick={toggleDetails}>
                {isOpen ? 'Hide Details' : 'Details'}
            </Button>
            {
                isOpen ? (
                    <div className="product-details">
                        <h2>Product Details</h2>
                        <div><strong>ID:</strong> {product.product_key}</div>
                        <div><strong>Price:</strong> ${product.price}</div>
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