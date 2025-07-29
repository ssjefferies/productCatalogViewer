import { Button } from 'bootstrap';
import React, { useEffect, useState } from 'react';
import { Container, Table } from 'reactstrap';
import ProductDetails from './ProductDetails';
import './productList.css';

const ProductList = () => {

    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);

    const [error, setError] = useState(null);

    const handleViewDetails = (product) => {
        // This function can be used to handle viewing product details
        // For now, it just alerts the product name
        alert(`Viewing details for ${product.product_name}`);
    }

    useEffect(() => {
        setLoading(true);
        fetch('/api/products')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Unable to fetch products');
                }
                return response.json();
            })
            .then(data => {
                setProducts(data);
                setLoading(false);
            })
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error.message}</div>;
    }

    const productsList = products.map(product => (
        <tr key={product.product_key}>
            <td>
                {product.product_name}
                <br />
                <ProductDetails product={product} />
            </td>
            <td>{product.brand}</td>
            <td>${product.price}</td>
            <td>{product.model}</td>
        </tr>
    ));

    return (
        <div>
            <Container fluid>
                <h1>Product List</h1>
                <Table striped>
                    <thead>
                        <tr>
                            <th>Product Name</th>
                            <th>Brand</th>
                            <th>Price</th>
                            <th>Model</th>
                        </tr>
                    </thead>
                    <tbody>
                        {productsList}
                    </tbody>
                </Table>
            </Container>
        </div>
    );

};

export default ProductList;
