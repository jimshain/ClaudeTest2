# DDA Product Management Frontend

React frontend for the DDA Product Management System.

## Prerequisites

- Node.js (v14 or higher)
- npm (comes with Node.js)
- Backend server running on http://localhost:8080

## Installation

```bash
npm install
```

## Running the Application

```bash
npm start
```

This will start the development server on http://localhost:3000 and automatically open it in your browser.

The frontend is configured to proxy API requests to the backend server at http://localhost:8080.

## Features

- View all DDA products in a table format
- Refresh data with a button click
- Formatted display of currency, percentages, and dates
- Responsive design with hover effects
- Error handling with user-friendly messages

## API Integration

The frontend calls the `/ddaproductinq` endpoint with a null key to retrieve all products:

```json
{
  "rquid": "REQ-<timestamp>",
  "ddaProductKey": null
}
```

## Building for Production

```bash
npm run build
```

This creates an optimized production build in the `build/` directory.
