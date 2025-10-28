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

import java.sql.Connection;
import java.sql.SQLException;

public class DdaProductHandler {

	private DdaProductDb ddaProductDb = new DdaProductDb();

	public DdaProductAddRs add(DdaProductAddRq ddaProductRq) throws SQLException {
		DdaProductAddRs ddaProductAddRs = new DdaProductAddRs();

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
			ddaProductDb.insert(connection, productDo);
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

			// Map the key fields to the database query
			DdaProductDo productDo = ddaProductDb.selectById(
				connection,
				key.getHoldingCompanyId(),
				key.getBankId(),
				key.getBranchId(),
				key.getProductId()
			);

			// TODO: Map productDo to ddaProductInqRs when response structure is defined
			if (productDo != null) {
				ddaProductInqRs = new DdaProductInqRs();
			}
		}

		return ddaProductInqRs;
	}

	public DdaProductDelRs del(DdaProductDelRq ddaProductDelRq) throws SQLException {
		DdaProductDelRs ddaProductDelRs = new DdaProductDelRs();

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
			ddaProductDb.delete(connection, productDo);

			// Set response rquid
			ddaProductDelRs.setRquid(ddaProductDelRq.getRquid());
		}

		return ddaProductDelRs;
	}

} // Class end