// build a form to add a product with id, price, retailer, brand, model, name, description
import React, { useState } from 'react';
import { Button, Form, FormGroup, Label, Input } from 'reactstrap';
import './addProduct.css';

const AddProduct = ({ onAddProduct, onClose }) => {
    const [product, setProduct] = useState({
        product_key: '',
        price: '',
        retailer: '',
        brand: '',
        model: '',
        product_name: '',
        product_description: ''
    });
    const handleChange = (e) => {
        const { name, value } = e.target;
        setProduct({ ...product, [name]: value });
    };
    const handleSubmit = (e) => {
        e.preventDefault();
        onAddProduct(product);
        setProduct({
            product_key: '',
            price: '',
            retailer: '',
            brand: '',
            model: '',
            product_name: '',
            product_description: ''
        });
    };
    return (
        <div className="add-product-container">
            <Form onSubmit={handleSubmit} className="add-product-form">
                <h2>Add Product</h2>
                <FormGroup>
                    <Label for="product_key">ID</Label>
                    <Input type="text" name="product_key" id="product_key" value={product.product_key} onChange={handleChange} required />
                </FormGroup>
                <FormGroup>
                    <Label for="price">Price</Label>
                    <Input type="number" name="price" id="price" value={product.price} onChange={handleChange} />
                </FormGroup>
                <FormGroup>
                    <Label for="retailer">Retailer</Label>
                    <Input type="text" name="retailer" id="retailer" value={product.retailer} onChange={handleChange} />
                </FormGroup>
                <FormGroup>
                    <Label for="brand">Brand</Label>
                    <Input type="text" name="brand" id="brand" value={product.brand} onChange={handleChange} />
                </FormGroup>
                <FormGroup>
                    <Label for="model">Model</Label>
                    <Input type="text" name="model" id="model" value={product.model} onChange={handleChange} />
                </FormGroup>
                <FormGroup>
                    <Label for="product_name">Name</Label>
                    <Input type="text" name="product_name" id="product_name" value={product.product_name} onChange={handleChange} />
                </FormGroup>
                <FormGroup>
                    <Label for="product_description">Description</Label>
                    <Input type="textarea" name="product_description" id="product_description" value={product.product_description} onChange={handleChange} />
                </FormGroup>
                <div className="form-actions">
                    <Button type="submit" color="primary">Add Product</Button>&nbsp;
                    <Button onClick={onClose} color="secondary">Close</Button>
                </div>
            </Form>
        </div>
    );
}
export default AddProduct;