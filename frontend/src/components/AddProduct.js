import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './AddProduct.css';

const AddProduct = () => {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);

  const [formData, setFormData] = useState({
    holdingCompanyId: '',
    bankId: '',
    branchId: '',
    productId: '',
    effectiveDate: '',
    description: '',
    minimumOpeningDeposit: '',
    minimumBalance: '',
    overdraftLimit: '',
    overdraftFee: '',
    apy: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    setSuccess(false);

    try {
      // Convert form data to API format
      const ddaProductKey = {
        holdingCompanyId: parseInt(formData.holdingCompanyId),
        bankId: parseInt(formData.bankId),
        branchId: parseInt(formData.branchId),
        productId: formData.productId,
        effectiveDate: formData.effectiveDate
      };

      const ddaProduct = {
        ddaProductKey: ddaProductKey,
        description: formData.description,
        minimumOpeningDeposit: formData.minimumOpeningDeposit ? parseInt(formData.minimumOpeningDeposit) : null,
        minimumBalance: formData.minimumBalance ? parseInt(formData.minimumBalance) : null,
        overdraftLimit: formData.overdraftLimit ? parseInt(formData.overdraftLimit) : null,
        overdraftFee: formData.overdraftFee ? parseInt(formData.overdraftFee) : null,
        apy: formData.apy ? parseFloat(formData.apy) : null
      };

      const requestBody = {
        rquid: `ADD-${Date.now()}`,
        ddaProduct: ddaProduct
      };

      const apiUrl = process.env.REACT_APP_API_URL || 'http://localhost:8080';
      const response = await fetch(`${apiUrl}/ddaproductadd`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(requestBody),
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = await response.json();

      if (data.status && data.status.length > 0) {
        const firstStatus = data.status[0];
        if (firstStatus.code === '100') {
          setSuccess(true);
          // Reset form
          setFormData({
            holdingCompanyId: '',
            bankId: '',
            branchId: '',
            productId: '',
            effectiveDate: '',
            description: '',
            minimumOpeningDeposit: '',
            minimumBalance: '',
            overdraftLimit: '',
            overdraftFee: '',
            apy: ''
          });
          // Navigate back to list after 2 seconds
          setTimeout(() => {
            navigate('/');
          }, 2000);
        } else {
          throw new Error(firstStatus.message || 'Failed to add product');
        }
      }
    } catch (err) {
      setError(err.message);
      console.error('Error adding product:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleCancel = () => {
    navigate('/');
  };

  return (
    <div className="add-product-container">
      <div className="add-product-header">
        <h1>Add New DDA Product</h1>
        <button onClick={handleCancel} className="cancel-button">
          Back to List
        </button>
      </div>

      {error && (
        <div className="error-message">
          <strong>Error:</strong> {error}
        </div>
      )}

      {success && (
        <div className="success-message">
          <strong>Success!</strong> Product added successfully. Redirecting to list...
        </div>
      )}

      <form onSubmit={handleSubmit} className="add-product-form">
        <div className="form-section">
          <h2>Product Key Information</h2>

          <div className="form-group">
            <label htmlFor="holdingCompanyId">
              Holding Company ID <span className="required">*</span>
            </label>
            <input
              type="number"
              id="holdingCompanyId"
              name="holdingCompanyId"
              value={formData.holdingCompanyId}
              onChange={handleChange}
              required
              min="1"
            />
          </div>

          <div className="form-group">
            <label htmlFor="bankId">
              Bank ID <span className="required">*</span>
            </label>
            <input
              type="number"
              id="bankId"
              name="bankId"
              value={formData.bankId}
              onChange={handleChange}
              required
              min="1"
            />
          </div>

          <div className="form-group">
            <label htmlFor="branchId">
              Branch ID <span className="required">*</span>
            </label>
            <input
              type="number"
              id="branchId"
              name="branchId"
              value={formData.branchId}
              onChange={handleChange}
              required
              min="1"
            />
          </div>

          <div className="form-group">
            <label htmlFor="productId">
              Product ID <span className="required">*</span>
            </label>
            <input
              type="text"
              id="productId"
              name="productId"
              value={formData.productId}
              onChange={handleChange}
              required
              maxLength="4"
              placeholder="e.g., CHK1"
            />
          </div>

          <div className="form-group">
            <label htmlFor="effectiveDate">
              Effective Date <span className="required">*</span>
            </label>
            <input
              type="datetime-local"
              id="effectiveDate"
              name="effectiveDate"
              value={formData.effectiveDate}
              onChange={handleChange}
              required
            />
          </div>
        </div>

        <div className="form-section">
          <h2>Product Details</h2>

          <div className="form-group">
            <label htmlFor="description">
              Description
            </label>
            <input
              type="text"
              id="description"
              name="description"
              value={formData.description}
              onChange={handleChange}
              placeholder="e.g., Premium Checking Account"
            />
          </div>

          <div className="form-group">
            <label htmlFor="minimumOpeningDeposit">
              Minimum Opening Deposit (cents)
            </label>
            <input
              type="number"
              id="minimumOpeningDeposit"
              name="minimumOpeningDeposit"
              value={formData.minimumOpeningDeposit}
              onChange={handleChange}
              min="0"
              placeholder="e.g., 10000 for $100.00"
            />
          </div>

          <div className="form-group">
            <label htmlFor="minimumBalance">
              Minimum Balance (cents)
            </label>
            <input
              type="number"
              id="minimumBalance"
              name="minimumBalance"
              value={formData.minimumBalance}
              onChange={handleChange}
              min="0"
              placeholder="e.g., 2500 for $25.00"
            />
          </div>

          <div className="form-group">
            <label htmlFor="overdraftLimit">
              Overdraft Limit (cents)
            </label>
            <input
              type="number"
              id="overdraftLimit"
              name="overdraftLimit"
              value={formData.overdraftLimit}
              onChange={handleChange}
              min="0"
              placeholder="e.g., 50000 for $500.00"
            />
          </div>

          <div className="form-group">
            <label htmlFor="overdraftFee">
              Overdraft Fee (cents)
            </label>
            <input
              type="number"
              id="overdraftFee"
              name="overdraftFee"
              value={formData.overdraftFee}
              onChange={handleChange}
              min="0"
              placeholder="e.g., 3500 for $35.00"
            />
          </div>

          <div className="form-group">
            <label htmlFor="apy">
              APY (Annual Percentage Yield)
            </label>
            <input
              type="number"
              id="apy"
              name="apy"
              value={formData.apy}
              onChange={handleChange}
              step="0.0001"
              min="0"
              max="1"
              placeholder="e.g., 0.0125 for 1.25%"
            />
          </div>
        </div>

        <div className="form-actions">
          <button type="button" onClick={handleCancel} className="cancel-button">
            Cancel
          </button>
          <button type="submit" className="submit-button" disabled={loading}>
            {loading ? 'Adding...' : 'Add Product'}
          </button>
        </div>
      </form>
    </div>
  );
};

export default AddProduct;
