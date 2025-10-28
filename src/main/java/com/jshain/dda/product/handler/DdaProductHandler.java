package com.jshain.dda.product.handler;

import com.jshain.database.Database;
import com.jshain.dda.product.database.DdaProductDb;
import com.jshain.dda.product.database.DdaProductDo;
import com.jshain.dda.product.message.DdaProductAddRq;
import com.jshain.dda.product.message.DdaProductAddRs;
import com.jshain.dda.product.message.DdaProductInqRq;
import com.jshain.dda.product.message.DdaProductInqRs;
import com.jshain.dda.product.message.DdaProductKey;

import java.sql.Connection;
import java.sql.SQLException;

public class DdaProductHandler {

	private DdaProductDb ddaProductDb = new DdaProductDb();

	public DdaProductAddRs add(DdaProductAddRq ddaProductRq) throws SQLException {
		DdaProductAddRs ddaProductAddRs = null;

		// Get database connection
		Connection connection = Database.getConnection();

		// TODO: Implement add logic using ddaProductDb.insert(connection, productDo)

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