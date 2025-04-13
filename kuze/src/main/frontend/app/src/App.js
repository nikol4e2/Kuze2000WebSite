import logo from './logo.svg';
import './App.css';
import {useEffect, useState} from "react";
import axios from "axios";
import ProductCard from "./ProductCard";

function App() {
    const [products,setProducts] =useState([])

    useEffect(()=>{
        axios.get("http://localhost:8080/api/products").then(response =>{
            setProducts(response.data)
        }).catch(error=>{
            console.error("Error fetching products:",error);
        })
    },[]);
  return (
    <div className="container">
        <h1>Products</h1>
        <div className="product-list">
            {products.length === 0 ? (<p>No products available.</p>) : (
                products.map(product => <ProductCard key={product.code} product={product}/> )
            )}
        </div>

    </div>
  );
}

export default App;
