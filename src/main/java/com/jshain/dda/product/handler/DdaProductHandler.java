package com.jshain.dda.product.handler;

import com.jshain.database.Database;
import com.jshain.dda.product.database.DdaProductDb;
import com.jshain.dda.product.database.DdaProductDo;
import com.jshain.dda.product.message.DdaProductAddRq;
import com.jshain.dda.product.message.DdaProductAddRs;
import com.jshain.dda.product.message.DdaProductInqRq;
import com.jshain.dda.product.message.DdaProductInqRs;
import com.jshain.dda.product.message.DdaProductKey;
import com.jshain.dda.product.message.DdaProductMo;

import java.sql.Connection;
import java.sql.SQLException;

public class DdaProductHandler {

	private DdaProductDb ddaProductDb = new DdaProductDb();

	public DdaProductAddRs add(Connection connection, DdaProductAddRq ddaProductRq) throws SQLException {
		DdaProductAddRs ddaProductAddRs = null;

		// Extract the product from the request
		DdaProductMo productMo = ddaProductRq.getDdaProduct();

		if (productMo != null && productMo.getDdaProductKey() != null) {
			// Create DdaProductDo and map fields
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
			productDo.setUpdatedBy(ddaProductRq.getRquid());

			// Insert into database
			int rowsAffected = ddaProductDb.insert(connection, productDo);

			// TODO: Create and populate DdaProductAddRs based on insert result
			if (rowsAffected > 0) {
				ddaProductAddRs = new DdaProductAddRs();
			}
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

} // Class end