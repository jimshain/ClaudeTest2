import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import DdaProductList from './components/DdaProductList';
import AddProduct from './components/AddProduct';
import './App.css';

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<DdaProductList />} />
          <Route path="/add" element={<AddProduct />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
