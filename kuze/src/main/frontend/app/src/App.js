import logo from './logo.svg';
import './App.css';
import {useEffect, useState} from "react";
import axios from "axios";
import ProductCard from "./ProductCard";

function App() {
    const [products,setProducts] =useState([])
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages]=useState(1);
    const [searchMode, setSearchMode]=useState(false);
    const [keyword, setKeyword]=useState("");

    const fetchProducts = ()=>{
        axios.get("http://localhost:8080/api/products/pagination",{
            params:{
                page: page,
                size: 18
            }
        }).then(response =>{
            setProducts(response.data.content)
            setTotalPages(response.data.totalPages)
        }).catch(error=>{
            console.error("Error fetching products:",error);
        })
    }

    const searchProducts = ()=>{
        if(keyword.trim() === ""){
            setSearchMode(false);
            fetchProducts();
            return
        }

        setSearchMode(true);
        axios.get("http://localhost:8080/api/products/search",{
            params:{keyword: keyword}
        }).then(response =>{setProducts(response.data)})
            .catch(error=>{console.log(error)})
    }

    useEffect(()=>{
        fetchProducts();
    },[page]);


    const goToPrevious=()=> setPage(prev=> Math.max(prev-1,0));
    const goToNext = () => setPage(prev => Math.min(prev+1,totalPages-1));
  return (
    <div className="container">
        <h1>Продукти</h1>
        <div className="search-bar">
            <input   type="text" value={keyword} onChange={(e) => setKeyword(e.target.value)}/>
            <button onClick={searchProducts}>🔍</button>
        </div>
        <div className="product-list">
            {products.length === 0 ? (<p>No products available.</p>) : (
                products.map(product => <ProductCard key={product.code} product={product}/> )
            )}
        </div>

        <div className="pagination">
            <button  onClick={goToPrevious} disabled={page===0}>Претходна</button>
            <span>Страница {page+1} од {totalPages}</span>
            <button  onClick={goToNext} disabled={page>= totalPages-1}>Следна</button>
        </div>

    </div>
  );
}

export default App;
