package com.jshain.dda.product.ws;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jshain.dda.product.handler.DdaProductHandler;
import com.jshain.dda.product.message.DdaProductAddRq;
import com.jshain.dda.product.message.DdaProductAddRs;
import com.jshain.dda.product.message.DdaProductDelRq;
import com.jshain.dda.product.message.DdaProductDelRs;
import com.jshain.dda.product.message.DdaProductInqRq;
import com.jshain.dda.product.message.DdaProductInqRs;
import com.jshain.gson.LocalDateAdapter;
import com.jshain.gson.LocalDateTimeAdapter;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class DdaProductServer {

	private static Gson gson = new GsonBuilder()
            // register custom JsonSerializer for LocalDate
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

	public static void main(String[] args) {

		Vertx vertx = Vertx.vertx();

		HttpServer server = vertx.createHttpServer();

		Router router = Router.router(vertx);

		router.route().handler(BodyHandler.create());

		router.post("/ddaproductadd").handler(ctx -> {

			// This handler will be called for every request
			HttpServerResponse response = ctx.response();

			Thread.ofVirtual().start(() -> {
				try {
					if (ctx.body() == null || ctx.body().asString() == null) {
						ctx.response().setStatusCode(400).putHeader("content-type", "application/json").end(MISSING_BODY);
					}

					String message = ctx.body().asString();
					
					System.out.println(message);

					DdaProductAddRq ddaProductAddRq = gson.fromJson(message, DdaProductAddRq.class);

					DdaProductAddRs ddaProductAddRs = DdaProductHandler.add(ddaProductAddRq);

					response.putHeader("content-type", "application/json").end(gson.toJson(ddaProductAddRs));
				} catch (Exception e) {
					e.printStackTrace();
					response.setStatusCode(500).putHeader("content-type", "application/json").end(INTERNAL_SERVER_ERROR);
				}
			});

		});

		router.post("/ddaproductinq").handler(ctx -> {

			// This handler will be called for every request
			HttpServerResponse response = ctx.response();

			Thread.ofVirtual().start(() -> {
				try {
					if (ctx.body() == null || ctx.body().asString() == null) {
						ctx.response().setStatusCode(400).putHeader("content-type", "application/json").end(MISSING_BODY);
					}

					String message = ctx.body().asString();

					DdaProductInqRq ddaProductInqRq = gson.fromJson(message, DdaProductInqRq.class);

					DdaProductHandler handler = new DdaProductHandler();
					DdaProductInqRs ddaProductInqRs = handler.inq(ddaProductInqRq);

					response.putHeader("content-type", "application/json").end(gson.toJson(ddaProductInqRs));
				} catch (Exception e) {
					e.printStackTrace();
					response.setStatusCode(500).putHeader("content-type", "application/json").end(INTERNAL_SERVER_ERROR);
				}
			});

		});

		router.post("/ddaproductdel").handler(ctx -> {

			// This handler will be called for every request
			HttpServerResponse response = ctx.response();

			Thread.ofVirtual().start(() -> {
				try {
					if (ctx.body() == null || ctx.body().asString() == null) {
						ctx.response().setStatusCode(400).putHeader("content-type", "application/json").end(MISSING_BODY);
					}

					String message = ctx.body().asString();

					DdaProductDelRq ddaProductDelRq = gson.fromJson(message, DdaProductDelRq.class);

					DdaProductHandler handler = new DdaProductHandler();
					DdaProductDelRs ddaProductDelRs = handler.del(ddaProductDelRq);

					response.putHeader("content-type", "application/json").end(gson.toJson(ddaProductDelRs));
				} catch (Exception e) {
					e.printStackTrace();
					response.setStatusCode(500).putHeader("content-type", "application/json").end(INTERNAL_SERVER_ERROR);
				}
			});

		});

		server.requestHandler(router).listen(8080);
		System.out.println("Server started.");
	}

	private static final String MISSING_BODY = """
			{ "status" : { "code" : "800", "severity" : "800", "description" : "Missing message body"}}
			""";
	private static final String INTERNAL_SERVER_ERROR = """
			{ "status" : { "code" : "999", "severity" : "999", "description" : "Internal server error. Contact support."}}
			""";
}
