/*
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.helidon.examples.quickstart.mp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * A simple JAX-RS resource to greet you. Examples:
 *
 * Get default greeting message:
 * curl -X GET http://localhost:8080/greet
 *
 * Get greeting message for Joe:
 * curl -X GET http://localhost:8080/greet/Joe
 *
 * Change greeting
 * curl -X PUT http://localhost:8080/greet/greeting/Hola
 *
 * The message is returned as a JSON object.
 */
@Path("/movies")
@RequestScoped
public class MovieResource {
    @SuppressWarnings("checkstyle:designforextension")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getDefaultMessage() throws FileNotFoundException {
        InputStream fis = new FileInputStream("src/main/resources/movies.json");

        JsonReader reader = Json.createReader(fis);

        JsonArray movieArray = reader.readArray();
        return movieArray;
    }

    /**
     * Return a greeting message using the name that was provided.
     *
     * @param name the name to movie
     * @return {@link JsonObject}
     * @throws IOException
     */
    @SuppressWarnings("checkstyle:designforextension")
    @Path("/{title}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getMessage(@PathParam("title") String title) throws IOException {
        
        JsonObject movieObject = null;
        if(title.equals("Titanic") || title.equals("titanic")) {
            InputStream fis = new FileInputStream("src/main/resources/movie.json");

            JsonReader reader = Json.createReader(fis);
    
            movieObject = reader.readObject();

            reader.close();
            fis.close();
        } else {
            movieObject = Json.createObjectBuilder()
                .add("message", "Not found")
                .build();
        }

        return movieObject;
    }
}