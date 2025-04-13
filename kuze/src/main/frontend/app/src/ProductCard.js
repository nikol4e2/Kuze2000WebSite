import React from 'react';
import "./ProductCard.css";
const ProductCard = ({product}) => {
    return (
        <div className="card">
            <h2>{product.description}</h2>
            <p><strong>Шифра:</strong>{product.code}</p>
            <p><strong>Единица мерка:</strong>{product.unitOfMeasure}</p>
            <p><strong>Цена:</strong>{product.price.toFixed(2)}</p>
        </div>
    );
};

export default ProductCard;