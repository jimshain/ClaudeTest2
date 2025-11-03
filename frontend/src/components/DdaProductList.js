import React, { useState, useEffect } from 'react';
import './DdaProductList.css';

const DdaProductList = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    try {
      setLoading(true);
      setError(null);

      const response = await fetch('/ddaproductinq', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          rquid: `REQ-${Date.now()}`,
          ddaProductKey: null // null key returns all products
        }),
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = await response.json();

      if (data.status && data.status.length > 0) {
        const firstStatus = data.status[0];
        if (firstStatus.code === '100') {
          // Success
          setProducts(data.ddaProducts || []);
        } else if (firstStatus.code === '404') {
          // No products found
          setProducts([]);
        } else {
          throw new Error(firstStatus.message || 'Unknown error occurred');
        }
      }
    } catch (err) {
      setError(err.message);
      console.error('Error fetching products:', err);
    } finally {
      setLoading(false);
    }
  };

  const formatDateTime = (dateTime) => {
    if (!dateTime) return 'N/A';
    try {
      return new Date(dateTime).toLocaleString();
    } catch {
      return dateTime;
    }
  };

  const formatCurrency = (amount) => {
    if (amount === null || amount === undefined) return 'N/A';
    return `$${amount.toLocaleString()}`;
  };

  const formatPercentage = (rate) => {
    if (rate === null || rate === undefined) return 'N/A';
    return `${(rate * 100).toFixed(2)}%`;
  };

  if (loading) {
    return <div className="loading">Loading DDA Products...</div>;
  }

  if (error) {
    return (
      <div className="error">
        <h3>Error Loading Products</h3>
        <p>{error}</p>
        <button onClick={fetchProducts}>Retry</button>
      </div>
    );
  }

  return (
    <div className="dda-product-list">
      <div className="header">
        <h1>DDA Product Management</h1>
        <button onClick={fetchProducts} className="refresh-btn">
          Refresh
        </button>
      </div>

      {products.length === 0 ? (
        <div className="no-products">
          <p>No products found.</p>
        </div>
      ) : (
        <div className="table-container">
          <table className="product-table">
            <thead>
              <tr>
                <th>Holding Company ID</th>
                <th>Bank ID</th>
                <th>Branch ID</th>
                <th>Product ID</th>
                <th>Effective Date</th>
                <th>Description</th>
                <th>Min Opening Deposit</th>
                <th>Min Balance</th>
                <th>Overdraft Limit</th>
                <th>Overdraft Fee</th>
                <th>APY</th>
              </tr>
            </thead>
            <tbody>
              {products.map((product, index) => (
                <tr key={index}>
                  <td>{product.ddaProductKey?.holdingCompanyId || 'N/A'}</td>
                  <td>{product.ddaProductKey?.bankId || 'N/A'}</td>
                  <td>{product.ddaProductKey?.branchId || 'N/A'}</td>
                  <td>{product.ddaProductKey?.productId || 'N/A'}</td>
                  <td>{formatDateTime(product.ddaProductKey?.effectiveDate)}</td>
                  <td>{product.description || 'N/A'}</td>
                  <td className="number">{formatCurrency(product.minimumOpeningDeposit)}</td>
                  <td className="number">{formatCurrency(product.minimumBalance)}</td>
                  <td className="number">{formatCurrency(product.overdraftLimit)}</td>
                  <td className="number">{formatCurrency(product.overdraftFee)}</td>
                  <td className="number">{formatPercentage(product.apy)}</td>
                </tr>
              ))}
            </tbody>
          </table>
          <div className="product-count">
            Total Products: {products.length}
          </div>
        </div>
      )}
    </div>
  );
};

export default DdaProductList;
