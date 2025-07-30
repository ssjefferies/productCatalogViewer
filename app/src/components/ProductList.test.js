/**
 * Unit tests for ProductList component
 */
import React from 'react';
import { render, screen, fireEvent, waitFor, act } from '@testing-library/react';
import '@testing-library/jest-dom';
import ProductList from './ProductList';

// Mock data
const mockProducts = [
    {
        product_key: 1,
        retailer: 'Best Buy',
        brand: 'Apple',
        model: 'iPhone 15',
        product_name: 'iPhone 15 Pro',
        productDescription: 'Latest iPhone model',
        price: 999.99
    },
    {
        product_key: 2,
        retailer: 'Amazon',
        brand: 'Samsung',
        model: 'Galaxy S24',
        product_name: 'Samsung Galaxy S24',
        productDescription: 'Latest Samsung phone',
        price: 899.99
    }
];

// Mock fetch function
const mockFetch = (data, ok = true, status = 200) => {
    global.fetch = jest.fn(() =>
        Promise.resolve({
            ok,
            status,
            json: () => Promise.resolve(data),
        })
    );
};

const mockFetchPost = (data, ok = true, status = 201) => {
    global.fetch = jest.fn()
        .mockResolvedValueOnce({
            ok: true,
            status: 200,
            json: () => Promise.resolve(mockProducts),
        })
        .mockResolvedValueOnce({
            ok,
            status,
            json: () => Promise.resolve(data),
        });
};

// Mock child components
jest.mock('./ProductDetails', () => {
    return function MockProductDetails({ product }) {
        return <button data-testid={`product-details-${product.product_key}`}>View Details</button>;
    };
});

jest.mock('./AddProduct', () => {
    return function MockAddProduct({ onAddProduct, onClose }) {
        const handleSubmit = () => {
            onAddProduct({
                product_key: 3,
                product_name: 'Test Product',
                brand: 'Test Brand',
                price: 99.99,
                model: 'Test Model',
                retailer: 'Test Store'
            });
        };

        return (
            <div data-testid="add-product-form">
                <h3>Add Product Form</h3>
                <button onClick={handleSubmit} data-testid="submit-product">
                    Submit
                </button>
                <button onClick={onClose} data-testid="cancel-product">Cancel</button>
            </div>
        );
    };
});


describe('ProductList Component', () => {
    beforeEach(() => {
        jest.resetAllMocks();
    });

    // Test 1: Component renders products after successful fetch
    test('renders product list after successful data fetch', async () => {
        mockFetch(mockProducts);

        await act(async () => {
            render(<ProductList />);
        });

        // Wait for loading to finish
        await waitFor(() => {
            expect(screen.queryByText('Loading...')).not.toBeInTheDocument();
        });

        // Check if products are displayed
        expect(screen.getByText('iPhone 15 Pro')).toBeInTheDocument();
        expect(screen.getByText('Samsung Galaxy S24')).toBeInTheDocument();
        expect(screen.getByText('Apple')).toBeInTheDocument();
        expect(screen.getByText('Samsung')).toBeInTheDocument();
    });
});

