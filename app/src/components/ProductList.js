import React, { useEffect, useState } from 'react';
import { Button } from 'reactstrap';
import { Container, Table } from 'reactstrap';
import ProductDetails from './ProductDetails';
import AddProduct from './AddProduct';
import './productList.css';

const ProductList = () => {

    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [showAddProduct, setShowAddProduct] = useState(false);

    const [error, setError] = useState(null);


    useEffect(() => {
        setLoading(true);
        fetch('/api/products')
            .then(response => {
                if (!response.ok) {
                    setError("Unable to fetch products");
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

    const toggleAddProduct = () => {
        setShowAddProduct(!showAddProduct);
    }

    const handleAddProduct = (newProduct) => {
        fetch('/api/products', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(newProduct),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to add product');
                }
                return response.json();
            })
            .then(data => {
                setProducts([...products, data]);
                setShowAddProduct(false);
            })
            .catch(error => {
                setError(error);
            });
    }

    const handleCloseAddProduct = () => {
        setShowAddProduct(false);
    }

    const productsList = products.map(product => {
        // Format price to 2 decimal places
        const price = product.price ? `$${product.price.toFixed(2)}` : 'N/A'; 
        return (
            <tr key={product.product_key}>
                <td>{product.product_name}</td>
                <td>{product.brand}</td>
                <td>{price}</td>
                <td>{product.model}</td>
                <td><ProductDetails product={product} /></td>
            </tr>
        )
    });

    return (
        <div>
            <Container fluid>
                {error && <div className="alert alert-danger">{error.message}</div>}
                <h1>Product List</h1>
                {
                    !showAddProduct && 
                    <Button color="primary" onClick={toggleAddProduct}>
                        Add Product
                    </Button>
                }
                {showAddProduct && <AddProduct onAddProduct={handleAddProduct} onClose={handleCloseAddProduct} />}
                <Table striped>
                    <thead>
                        <tr>
                            <th>Product Name</th>
                            <th>Brand</th>
                            <th>Price</th>
                            <th>Model</th>
                            <th></th>
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
