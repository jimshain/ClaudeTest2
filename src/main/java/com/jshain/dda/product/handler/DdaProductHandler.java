package com.jshain.dda.product.handler;

import com.jshain.database.Database;
import com.jshain.dda.product.database.DdaProductDb;
import com.jshain.dda.product.database.DdaProductDo;
import com.jshain.dda.product.message.DdaProductAddRq;
import com.jshain.dda.product.message.DdaProductAddRs;
import com.jshain.dda.product.message.DdaProductDelRq;
import com.jshain.dda.product.message.DdaProductDelRs;
import com.jshain.dda.product.message.DdaProductInqRq;
import com.jshain.dda.product.message.DdaProductInqRs;
import com.jshain.dda.product.message.DdaProductKey;
import com.jshain.dda.product.message.DdaProductMo;
import com.jshain.dda.product.message.Status;

import java.sql.Connection;
import java.sql.SQLException;

public class DdaProductHandler {

	public static DdaProductAddRs add(DdaProductAddRq ddaProductRq) {
		DdaProductAddRs ddaProductAddRs = new DdaProductAddRs();

		// Set response rquid from request
		ddaProductAddRs.setRquid(ddaProductRq.getRquid());
		try {

		// Get database connection
		Connection connection = Database.getConnection();

		// Extract the product data from the request
		DdaProductMo productMo = ddaProductRq.getDdaProduct();

		if (productMo != null && productMo.getDdaProductKey() != null) {
			// Create DdaProductDo and map fields from the request
			DdaProductDo productDo = new DdaProductDo();

			// Map key fields
			DdaProductKey key = productMo.getDdaProductKey();
			productDo.setHoldingCompanyId(key.getHoldingCompanyId());
			productDo.setBankId(key.getBankId());
			productDo.setBranchId(key.getBranchId());
			productDo.setProductId(key.getProductId());

			// Map non-key fields
			productDo.setProductDescription(productMo.getDescription());
			productDo.setMinimumOpeningDeposit(productMo.getMinimumOpeningDeposit());
			productDo.setMinimumBalance(productMo.getMinimumBalance());
			productDo.setOverdraftLimit(productMo.getOverdraftLimit());
			productDo.setApy(productMo.getApy());

			// Set updated by field (using request ID as default)
			productDo.setUpdatedBy(ddaProductRq.getRquid());

			// Insert the product into the database
			DdaProductDb.insert(connection, productDo);
		}

		// Add success status
		ddaProductAddRs.getStatus().add(Status.getSuccess());
		} catch (Exception e) {
			e.printStackTrace();
			ddaProductAddRs.getStatus().add(Status.getFatalError());
		}

		return ddaProductAddRs;
	}

	public DdaProductInqRs inq(DdaProductInqRq ddaProductInqRq) throws SQLException {
		DdaProductInqRs ddaProductInqRs = null;

		// Extract the key from the request
		DdaProductKey key = ddaProductInqRq.getDdaProductKey();

		if (key != null) {
			// Get database connection
			Connection connection = Database.getConnection();

			// Query the database for the product
			DdaProductDo productDo = DdaProductDb.selectById(
				connection,
				key.getHoldingCompanyId(),
				key.getBankId(),
				key.getBranchId(),
				key.getProductId()
			);

			// Map productDo to productMo
			if (productDo != null) {
				ddaProductInqRs = new DdaProductInqRs();
				// Set response rquid from request
				ddaProductInqRs.setRquid(ddaProductInqRq.getRquid());

				// Create and populate DdaProductMo
				DdaProductMo productMo = new DdaProductMo();

				// Create and set key
				DdaProductKey productKey = new DdaProductKey();
				productKey.setHoldingCompanyId(productDo.getHoldingCompanyId());
				productKey.setBankId(productDo.getBankId());
				productKey.setBranchId(productDo.getBranchId());
				productKey.setProductId(productDo.getProductId());
				productMo.setDdaProductKey(productKey);

				// Map non-key fields
				productMo.setDescription(productDo.getProductDescription());
				productMo.setMinimumOpeningDeposit(productDo.getMinimumOpeningDeposit());
				productMo.setMinimumBalance(productDo.getMinimumBalance());
				productMo.setOverdraftLimit(productDo.getOverdraftLimit());
				productMo.setApy(productDo.getApy());

				// Set the product on the response
				ddaProductInqRs.setDdaProduct(productMo);

				// Add success status
				ddaProductInqRs.getStatus().add(Status.getSuccess());
			}
		}

		return ddaProductInqRs;
	}

	public DdaProductDelRs del(DdaProductDelRq ddaProductDelRq) throws SQLException {
		DdaProductDelRs ddaProductDelRs = new DdaProductDelRs();

		// Set response rquid from request
		ddaProductDelRs.setRquid(ddaProductDelRq.getRquid());

		// Extract the key from the request
		DdaProductKey key = ddaProductDelRq.getDdaProductKey();

		if (key != null) {
			// Get database connection
			Connection connection = Database.getConnection();

			// Create DdaProductDo with key fields for deletion
			DdaProductDo productDo = new DdaProductDo();
			productDo.setHoldingCompanyId(key.getHoldingCompanyId());
			productDo.setBankId(key.getBankId());
			productDo.setBranchId(key.getBranchId());
			productDo.setProductId(key.getProductId());

			// Set updated by field (using request ID)
			productDo.setUpdatedBy(ddaProductDelRq.getRquid());

			// Perform soft delete
			DdaProductDb.delete(connection, productDo);
		}

		// Add success status
		ddaProductDelRs.getStatus().add(Status.getSuccess());

		return ddaProductDelRs;
	}

} // Class end